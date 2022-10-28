USE RRS_DB
GO

IF EXISTS (SELECT name FROM sys.views WHERE name = 'VIEW_AC_RANGE')
	DROP VIEW VIEW_AC_RANGE
GO
CREATE VIEW VIEW_AC_RANGE AS
	SELECT 
		MIN(regTime) as 'st', 
		MAX(regTime) as 'et', 
		COUNT(id) as 'length'
	FROM CONTENTS INNER JOIN ACCOUNTS 
	ON account_id=username
GO

SELECT * FROM VIEW_AC_RANGE
-- +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ ACCOUNT STATISTICS
/*
	ACCOUNT LIKE UPLOAD CONTENTS
	
	PROC_AC [@top], [@start], [@end], [@desc]
	@top: số lượng
	@start: thời gian bắt đầu
	@end: thời gian kết thúc
	@desc: sắp xếp tăng giảm số bài account này đã đăng
*/
IF EXISTS (SELECT name FROM sys.procedures WHERE name = 'PROC_AS')
	DROP PROCEDURE PROC_AS
GO
CREATE PROC PROC_AS
	@top int, @start datetime, @end datetime, @desc bit 
AS BEGIN 
	IF @start IS NULL SET @start = (SELECT st FROM VIEW_AC_RANGE)
	IF @end IS NULL SET @end = (SELECT et FROM VIEW_AC_RANGE)
	
	-- SELECT INTO TEMPORARY TABLE
	SELECT a.name, a.username, COUNT(c.account_id) as quantity 
		INTO #TEMP
	FROM ACCOUNTS a 
		INNER JOIN CONTENTS c on a.username = c.account_id
		WHERE c.regTime BETWEEN @start AND @end
	GROUP BY a.name, a.username

	-- SELECT DATA TO RETURN
	IF @desc IS NULL SELECT TOP(ISNULL(@top, 100)) * FROM #TEMP
	ELSE IF @desc=0 SELECT  TOP(ISNULL(@top, 100)) * FROM #TEMP o ORDER BY o.quantity
	ELSE SELECT TOP(ISNULL(@top, 100)) * FROM #TEMP o ORDER BY o.quantity DESC
END
GO
EXEC PROC_AS 15, null, null, null





-- +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ CONTENT STATISTICS
/*
	CONTENT UPLOADED IN ABOUT TIME
	
	PROC_AC [@top], [@start], [@end], [@desc]
	@about: Chọn theo 1(YEAR) | 2(MONTH) | 3(DAY)
	@start: thời gian bắt đầu
	@end: thời gian kết thúc
*/
IF EXISTS (SELECT name FROM sys.procedures WHERE name = 'PROC_CS')
	DROP PROCEDURE PROC_CS
GO
-- @about = 1(YEAR) | 2(MONTH) | 3(DAY)
CREATE PROC PROC_CS
	@about TINYINT, @start date, @end date
AS BEGIN
	IF(@about IS NULL OR @about < 1 OR 3 < @about)
		RAISERROR('Chỉ nhận giá trị đầu vào là 1 | 2 | 3', 20 , 1) with LOG
	-- CHECK DATE AND SET LENGTH SUBSTRING DATE
	IF @start IS NULL SET @start = (SELECT st FROM VIEW_AC_RANGE)
	IF @end IS NULL SET @end = (SELECT et FROM VIEW_AC_RANGE)
	DECLARE @CUT_AT TINYINT = 3*@about
	-- SELECT QUERY
	SELECT 
		SUBSTRING(CONVERT(varchar(8), regTime, 2), 0 , @CUT_AT) as about,
		COUNT(*) as 'quantity'
	FROM CONTENTS
	WHERE regTime BETWEEN @start AND @end
	GROUP BY SUBSTRING(CONVERT(varchar(8), regTime, 2), 0 , @CUT_AT)
	ORDER BY about asc
END
GO
EXEC PROC_CS null, null, null
/*
	-- KIỂM TRA SỐ LƯỢNG THEO ...
	SELECT CONVERT(varchar(10), regTime, 111) FROM CONTENTS 
	WHERE CONVERT(varchar(10), regTime, 111) LIKE '2022/01%'
*/





