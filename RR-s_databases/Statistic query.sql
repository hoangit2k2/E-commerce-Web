USE RRS_DB
GO

IF EXISTS (SELECT name FROM sys.views WHERE name = 'VIEW_AC_RANGE')
	DROP VIEW VIEW_AC_RANGE
GO
CREATE VIEW VIEW_AC_RANGE AS
	SELECT 
		MIN(regTime) as 'start', 
		MAX(regTime) as 'end', 
		COUNT(id) as 'length'
	FROM CONTENTS
GO
SELECT * FROM VIEW_AC_RANGE
/*
	ACCOUNT LIKE UPLOAD CONTENTS
	
	PROC_AC [@top], [@start], [@end], [@desc]
	@top: số lượng
	@start: thời gian bắt đầu
	@end: thời gian kết thúc
	@desc: sắp xếp tăng giảm số bài account này đã đăng
*/
IF EXISTS (SELECT name FROM sys.procedures WHERE name = 'PROC_AC')
	DROP PROCEDURE PROC_AC
GO
CREATE PROC PROC_AC 
	@top int, @start datetime, @end datetime, @desc bit 
AS BEGIN 
	IF @start IS NULL SET @start = (SELECT MIN(regTime) FROM CONTENTS)
	IF @end IS NULL SET @end = (SELECT MAX(regTime) FROM CONTENTS)
	
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
EXEC PROC_AC 15,'2020-1-25 02:57:35', '2022-10-25 02:57:35', null