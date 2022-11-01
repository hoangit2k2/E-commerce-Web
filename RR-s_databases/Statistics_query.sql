USE RRS_DB
GO

/*
	LIST CODE CONTENT STATISTIC
	____________________________________ VIEWS
	1. VIEW_CS_RANGE: lấy khoảng thời gian đăng nội dung (ngày sớm nhất, ngày muộn nhất, số lượng dữ liệu)
	____________________________________ PROCEDURES
	1. PROC_CBA: Nội dung được thích theo tài khoản
	2. PROC_CBT: Nội dung được thích theo thời gian
*/

-- +++++++++++++++++++++++++++++++++++ SIZE - MỐC THỜI GIAN ĐĂNG TẢI
IF EXISTS (SELECT name FROM sys.views WHERE name = 'VIEW_CS_RANGE')
	DROP VIEW VIEW_CS_RANGE
GO
CREATE VIEW VIEW_CS_RANGE AS
	SELECT 
		COUNT(id) as 'length',
		CASE 
			WHEN MIN(regTime) IS NULL THEN GETDATE() 
			ELSE MIN(regTime)
		END as 'st',
		CASE 
			WHEN MAX(regTime) IS NULL THEN GETDATE() 
			ELSE MAX(regTime) 
			END as 'et'
	FROM CONTENTS INNER JOIN ACCOUNTS
	ON account_id=username
GO
SELECT * FROM VIEW_CS_RANGE





-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++ NỘI DUNG ĐĂNG THEO TÀI KHOẢN
/*
	PROC_CBA: CONTENT UPLOAD BY ACCOUNT
	
	PROC_CBA [@top], [@start], [@end], [@desc]
	@top: số lượng
	@start: thời gian bắt đầu
	@end: thời gian kết thúc
	@desc: sắp xếp tăng giảm số bài account này đã đăng
*/
IF EXISTS (SELECT name FROM sys.procedures WHERE name = 'PROC_CBA')
	DROP PROCEDURE PROC_CBA
GO
CREATE PROC PROC_CBA
	@top int, @start datetime, @end datetime, @desc bit 
AS BEGIN 
	IF @start IS NULL SET @start = (SELECT st FROM VIEW_CS_RANGE)
	IF @end IS NULL SET @end = (SELECT et FROM VIEW_CS_RANGE)
	
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

EXEC PROC_CBA 15, null, null, null





-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++ NỘI DUNG ĐĂNG THEO THỜI GIAN
/*
	PROC_CBT: CONTENT UPLOAD BY TIME
	
	PROC_CBT [@top], [@start], [@end], [@desc]
	@about: Chọn theo 1(YEAR) | 2(MONTH) | 3(DAY)
	@start: thời gian bắt đầu
	@end: thời gian kết thúc
*/
IF EXISTS (SELECT name FROM sys.procedures WHERE name = 'PROC_CBT')
	DROP PROCEDURE PROC_CBT
GO
-- @about = 1(YEAR) | 2(MONTH) | 3(DAY)
CREATE PROC PROC_CBT
	@about TINYINT, @start datetime, @end datetime
AS BEGIN
	IF(@about IS NULL OR @about < 1 OR 3 < @about)
		RAISERROR('Chỉ nhận giá trị đầu vào là 1 | 2 | 3', 20 , 1) with LOG
	-- CHECK DATE AND SET LENGTH SUBSTRING DATE
	IF @start IS NULL SET @start = (SELECT st FROM VIEW_CS_RANGE)
	IF @end IS NULL SET @end = (SELECT et FROM VIEW_CS_RANGE)

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
EXEC PROC_CBT 3, null, null
/*
	-- KIỂM TRA SỐ LƯỢNG THEO ...
	SELECT CONVERT(varchar(10), regTime, 111) FROM CONTENTS 
	WHERE CONVERT(varchar(10), regTime, 111) LIKE '2020%'
*/





------------------------------------------------------------------------------------------------





/*
	LIST CODE LIKE STATISTIC
	____________________________________ VIEWS
	1. VIEW_LS_RANGE: lấy khoảng thời gian thích nội dung (ngày sớm nhất, ngày muộn nhất, số lượng dữ liệu)
	____________________________________ PROCEDURES
	1. PROC_LBA: Thống kê lượt thích theo tài khoản
	2. PROC_LBC: Thống kê lượt thích theo nội dung
	3. PROC_LBT: Thống kê lượt thích theo thời gian
*/
-- ++++++++++++++++++++ SIZE - MỐC THỜI GIAN ĐÃ NỘI DUNG ĐƯỢC THÍCH
IF EXISTS (SELECT name FROM sys.views WHERE name = 'VIEW_LS_RANGE')
	DROP VIEW VIEW_LS_RANGE
GO
CREATE VIEW VIEW_LS_RANGE AS
	SELECT 
		COUNT(*) as 'length',
		CASE 
			WHEN MIN(exeTime) IS NULL THEN GETDATE() 
			ELSE MIN(exeTime)
		END as 'st',
		CASE 
			WHEN MAX(exeTime) IS NULL THEN GETDATE() 
			ELSE MAX(exeTime) 
			END as 'et'
	FROM LIKES
GO
SELECT * FROM VIEW_LS_RANGE





-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++ LIKE THEO TÀI KHOẢN
/*
	PROC_LBA: LIKE BY ACCOUNT
	
	PROC_LBA [@top], [@start], [@end]
	@top: số lượng muốn lấy
	@start: thời gian bắt đầu
	@end: thời gian kết thúc
*/
IF EXISTS (SELECT name FROM sys.procedures WHERE name = 'PROC_LBA')
	DROP PROCEDURE PROC_LBA
GO
CREATE PROC PROC_LBA
	@top int, @start datetime, @end datetime
AS BEGIN 
	IF @start IS NULL SET @start = (SELECT st FROM VIEW_LS_RANGE)
	IF @end IS NULL SET @end = (SELECT et FROM VIEW_LS_RANGE)
	IF @top IS NULL SET @top = (SELECT length FROM VIEW_LS_RANGE)
	
	SELECT TOP(@top) account_id, name, COUNT(content_id) as 'quantity'
	FROM LIKES INNER JOIN ACCOUNTS ON username = account_id
	WHERE exeTime BETWEEN @start and @end
	GROUP BY account_id, name
END
GO
EXEC PROC_LBA 10, null, null





-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++ LIKE THEO NỘI DUNG
/*
	PROC_LBC: LIKE BY CONTENT
	
	PROC_LBC [@top], [@start], [@end]
	@top: số lượng muốn lấy
	@start: thời gian bắt đầu
	@end: thời gian kết thúc
*/
IF EXISTS (SELECT name FROM sys.procedures WHERE name = 'PROC_LBC')
	DROP PROCEDURE PROC_LBC
GO
CREATE PROC PROC_LBC
	@top int, @start datetime, @end datetime
AS BEGIN 
	IF @start IS NULL SET @start = (SELECT st FROM VIEW_LS_RANGE)
	IF @end IS NULL SET @end = (SELECT et FROM VIEW_LS_RANGE)
	IF @top IS NULL SET @top = (SELECT length FROM VIEW_LS_RANGE)
	
	SELECT TOP(@top) content_id, subject, COUNT(content_id) as 'quantity'
	FROM LIKES INNER JOIN CONTENTS ON id = content_id
	WHERE exeTime BETWEEN @start and @end
	GROUP BY content_id, subject
END
GO
EXEC PROC_LBC 10, null, null





-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++ LIKE THEO THỜI GIAN
/*
	PROC_LBT: CONTENT UPLOAD BY TIME
	
	PROC_LBT [@top], [@start], [@end], [@desc]
	@about: Chọn theo 1(YEAR) | 2(MONTH) | 3(DAY)
	@start: thời gian bắt đầu
	@end: thời gian kết thúc
*/
IF EXISTS (SELECT name FROM sys.procedures WHERE name = 'PROC_LBT')
	DROP PROCEDURE PROC_LBT
GO
-- @about = 1(YEAR) | 2(MONTH) | 3(DAY)
CREATE PROC PROC_LBT
	@about TINYINT, @start datetime, @end datetime
AS BEGIN
	IF(@about IS NULL OR @about < 1 OR 3 < @about)
		RAISERROR('Chỉ nhận giá trị đầu vào là 1 | 2 | 3', 20 , 1) with LOG
	-- CHECK DATE AND SET LENGTH SUBSTRING DATE
	IF @start IS NULL SET @start = (SELECT st FROM VIEW_LS_RANGE)
	IF @end IS NULL SET @end = (SELECT et FROM VIEW_LS_RANGE)

	DECLARE @CUT_AT TINYINT = 3*@about
	-- SELECT QUERY
	SELECT 
		SUBSTRING(CONVERT(varchar(8), exeTime, 2), 0 , @CUT_AT) as about,
		COUNT(*) as 'quantity'
	FROM LIKES
	WHERE exeTime BETWEEN @start AND @end
	GROUP BY SUBSTRING(CONVERT(varchar(8), exeTime, 2), 0 , @CUT_AT)
	ORDER BY about asc
END
GO
EXEC PROC_LBT 2, '2020-1-1', '2020-12-31'





GO
	USE MASTER
GO