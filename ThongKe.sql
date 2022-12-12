USE LovePink1
GO

-- ++++++++++++++++++++++++++++++++++++++++++++++ LƯỢT THÍCH
IF EXISTS(SELECT name FROM sys.procedures WHERE name = 'S_LIKE') DROP PROC S_LIKE
GO
CREATE PROCEDURE S_LIKE
	@Top int
AS
	SELECT TOP(ISNULL(@Top, 1000))
		c.id, c.namecontent, c.subject, c.datetime,
		COUNT(*) as 'quantity'
	FROM dbo.likes l
	JOIN contents c ON c.id = l.contentid
	GROUP BY c.id, c.namecontent, c.subject, c.datetime
GO
	EXEC S_LIKE 5
GO

-- ++++++++++++++++++++++++++++++++++++++++++++++ DOANH THU
IF EXISTS(SELECT name FROM sys.procedures WHERE name = 'S_TURNOVER') DROP PROC S_TURNOVER
GO
CREATE PROCEDURE S_TURNOVER
AS
	SELECT
		c.id, c.namecontent, c.subject, c.datetime, c.status,
		SUM(quantity) as 'quantity', SUM(d.price * quantity) as amount
	FROM dbo.orderdetails d
		INNER JOIN contents c on d.contentid = c.id
	GROUP BY c.id, c.namecontent, c.subject, c.datetime, c.status
GO
	EXEC S_TURNOVER
GO


-- +++++++++++++++++++++++++++++++++++ SIZE - MỐC THỜI GIAN ĐĂNG TẢI NỘI DUNG
IF EXISTS (SELECT name FROM sys.views WHERE name = 'VIEW_SO_RANGE')
	DROP VIEW VIEW_SO_RANGE
GO
CREATE VIEW VIEW_SO_RANGE AS
	SELECT 
		COUNT(id) as 'length',
		CASE 
			WHEN MIN(datetime) IS NULL THEN GETDATE() 
			ELSE MIN(datetime)
		END as 'st',
		CASE 
			WHEN MAX(datetime) IS NULL THEN GETDATE() 
			ELSE MAX(datetime) 
			END as 'et'
	FROM contents
GO
SELECT * FROM VIEW_SO_RANGE

-- ++++++++++++++++++++++++++++++++++++++++++++++ LƯỢT TẢI NỘI DUNG THEO THỜI GIAN
/*
	PROC_SS_TIME: STATISTIC ORDER SOLD
	
	@Top: số lượng rows | mặc định 1000
	@at: GROUP THEO (1 - YEAR | 2 - MONTH | 3 - DAY)
	@start: thời gian bắt đầu | mặc định min datetime
	@end: thời gian kết thúc | mặc định max datetime
	@desc: sắp xếp theo số lượng
*/
IF EXISTS (SELECT name FROM sys.procedures WHERE name = 'PROC_SS_TIME')
	DROP PROCEDURE PROC_SS_TIME
GO
CREATE PROC PROC_SS_TIME
	@Top int, @at int, @start datetime, @end datetime
AS BEGIN 
	IF @start IS NULL SET @start = (SELECT st FROM VIEW_SO_RANGE)
	IF @end IS NULL SET @end = (SELECT et FROM VIEW_SO_RANGE)
	IF @at IS NULL SET @at = 2
	SET @at = @at*3

	-- SELECT INTO TEMPORARY TABLE
	SELECT 
		SUBSTRING(CONVERT(varchar(8), datetime, 2), 0 , @at) as 'month',
		COUNT(*) as 'quantity'
	FROM contents WHERE datetime BETWEEN @start AND @end
	GROUP BY SUBSTRING(CONVERT(varchar(8), datetime, 2), 0 , @at)
END
GO
	-- @at IS NULL THEN SET @at default = 2
	-- @status IS NULL THEN SET @status default = 3
	EXEC PROC_SS_TIME 10, 3, NULL, NULL
GO
