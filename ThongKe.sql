

USE LovePink1
GO


-- +++++++++++++++++++++++++++++++++++ SIZE - MỐC THỜI GIAN ĐĂNG TẢI
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
	FROM ORDERS
GO
SELECT * FROM VIEW_SO_RANGE

GO
-- ++++++++++++++++++++++++++++++++++++++++++++++ SELECT QUANTITY AND TOTAL OF PRODUCT BY STATUS
/*
	PROC_SS_TIME: STATISTIC ORDER SOLD
	
	@Top: số lượng rows | mặc định 1000
	@at: GROUP THEO (1 - YEAR | 2 - MONTH | 3 - DAY)
	@status: trạng thái đơn hàng (1 | 2 | 3 | 4)
	@start: thời gian bắt đầu | mặc định min datetime
	@end: thời gian kết thúc | mặc định max datetime
	@desc: sắp xếp theo số lượng
*/
IF EXISTS (SELECT name FROM sys.procedures WHERE name = 'PROC_SS_TIME')
	DROP PROCEDURE PROC_SS_TIME
GO
CREATE PROC PROC_SS_TIME
	@Top int, @at int, @status int, @start datetime, @end datetime, @desc bit
AS BEGIN 
	IF @start IS NULL SET @start = (SELECT st FROM VIEW_SO_RANGE)
	IF @end IS NULL SET @end = (SELECT et FROM VIEW_SO_RANGE)
	IF @status IS NULL SET @status = 3
	IF @at IS NULL SET @at = 2
	SET @at = @at*3

	-- SELECT INTO TEMPORARY TABLE
	SELECT 
		SUBSTRING(CONVERT(varchar(8), datetime, 2), 0 , @at) as 'month',
		SUM(d.quantity) as 'quantity',
		SUM(d.price) as 'total' INTO #TEMP
	FROM orderdetails d
		INNER JOIN ORDERS o ON o.id = d.orderid
	GROUP BY SUBSTRING(CONVERT(varchar(8), datetime, 2), 0 , @at)


	-- SELECT DATA TO RETURN
	IF @desc IS NULL SELECT TOP(ISNULL(@top, 1000)) * FROM #TEMP
	ELSE IF @desc=0 SELECT  TOP(ISNULL(@top, 1000)) * FROM #TEMP o ORDER BY o.total
	ELSE SELECT TOP(ISNULL(@top, 1000)) * FROM #TEMP o ORDER BY o.total DESC
END
GO
	-- @at IS NULL THEN SET @at default = 2
	-- @status IS NULL THEN SET @status default = 3
	EXEC PROC_SS_TIME NULL, 2, 2, '2021-6-1', NULL, NULL
GO


-- ++++++++++++++++++++++++++++++++++++++++++++++ SELECT ORDER BY TIME
/*
	PROC_SO_TIME: CONTENT UPLOAD BY TIME
	
	@Top: số lượng rows | mặc định 1000
	@start: thời gian bắt đầu | mặc định min datetime
	@end: thời gian kết thúc | mặc định max datetime
	@at: Chọn theo 1(YEAR) | 2(MONTH) | 3(DAY)
	@desc: sắp xếp theo số lượng
*/
IF EXISTS (SELECT name FROM sys.procedures WHERE name = 'PROC_SO_TIME')
	DROP PROCEDURE PROC_SO_TIME
GO
CREATE PROC PROC_SO_TIME
	@Top int, @start datetime, @end datetime, @at int, @desc bit 
AS BEGIN 
	IF @start IS NULL SET @start = (SELECT st FROM VIEW_SO_RANGE)
	IF @end IS NULL SET @end = (SELECT et FROM VIEW_SO_RANGE)
	DECLARE @CUT_AT TINYINT = 3*@at

	-- SELECT INTO TEMPORARY TABLE
	SELECT 
		SUBSTRING(CONVERT(varchar(8), datetime, 2), 0 , @CUT_AT) as 'month',
		SUM(d.quantity) as quantity INTO #TEMP
	FROM ORDERS o
		INNER JOIN orderdetails d ON o.id=d.orderid
	WHERE datetime BETWEEN @start AND @end
	GROUP BY SUBSTRING(CONVERT(varchar(8), datetime, 2), 0 , @CUT_AT)

	-- SELECT DATA TO RETURN
	IF @desc IS NULL SELECT TOP(ISNULL(@top, 1000)) * FROM #TEMP
	ELSE IF @desc=0 SELECT  TOP(ISNULL(@top, 1000)) * FROM #TEMP o ORDER BY o.quantity
	ELSE SELECT TOP(ISNULL(@top, 1000)) * FROM #TEMP o ORDER BY o.quantity DESC
END
GO
EXEC PROC_SO_TIME 12, null, '2022', 2, null
GO

GO
-- ++++++++++++++++++++++++++++++++++++++++++++++ SELECT ACCOUNT BY ROLE ID
IF EXISTS (SELECT name FROM sys.procedures WHERE name = 'PROC_ACCOUNT_BY_ROLE')
	DROP PROC PROC_ACCOUNT_BY_ROLE
GO
CREATE PROCEDURE PROC_ACCOUNT_BY_ROLE
	@role_id varchar(10)
AS BEGIN 
	SELECT * FROM users 
	WHERE username = @role_id
END
GO
EXEC PROC_ACCOUNT_BY_ROLE 'cust'

-- ++++++++++++++++++++++++++++++++++++++++++++++ SELECT TOP PRODUCT
IF EXISTS (SELECT name FROM sys.procedures WHERE name = 'PROC_TOP_PRODUCT')
	DROP PROC PROC_TOP_PRODUCT
GO
CREATE PROCEDURE PROC_TOP_PRODUCT
	@top int
AS BEGIN 
	SELECT TOP(ISNULL(@top, 1000)) p.*, COUNT(*) as 'quantity' FROM orderdetails d
		INNER JOIN ORDERS o ON o.id = d.orderid
		INNER JOIN contents p ON p.id = d.contentid
	GROUP BY p.id, p.address, p.categoryid, p.datetime, p.email, p.namecontent, p.phone,
	p.price, p.status, p.subject, p.usernameid
	ORDER BY COUNT(*) DESC
END
GO
EXEC PROC_TOP_PRODUCT 10















