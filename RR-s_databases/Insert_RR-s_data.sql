USE [RRS_DB]
GO
	DELETE FROM [dbo].[APIs]
	DELETE FROM [dbo].[LIKES]

	DELETE FROM [dbo].[CONTENT_TYPES]
	DELETE FROM [dbo].[CATEGORIES]
	DELETE FROM [dbo].[CONTENT_IMAGES]
	DELETE FROM [dbo].[CONTENTS]
	
	DELETE FROM [dbo].[ACCOUNTS]
	DELETE FROM [dbo].[STAFFS]

	DBCC CHECKIDENT ('[CONTENTS]', RESEED, 0);
GO
INSERT [dbo].[STAFFS] ([username], [password], [email], [role], [image]) VALUES (N'admin', N'123', N'adminsystem@gmail.com', 1, N'admin.png')
GO
INSERT [dbo].[ACCOUNTS] ([username], [password], [name], [email], [image], [regDate]) VALUES (N'guest', N'123', N'Guest System', N'guestsys@gmail.com', N'guest.png', CAST(N'2022-08-20' AS Date))
INSERT [dbo].[ACCOUNTS] ([username], [password], [name], [email], [image], [regDate]) VALUES (N'user', N'123', N'User System', N'usersys@gmail.com', N'user.png', CAST(N'2022-08-22' AS Date))
GO
SET IDENTITY_INSERT [dbo].[CONTENTS] ON 

INSERT [dbo].[CONTENTS] ([id], [subject], [content], [regTime], [views], [active], [account_id]) VALUES (1, N'Cần người sửa nhà', N'Nhà bị hư nặng nề cần người sửa giúp, thanh toán nhanh gọn lẹ', CAST(N'2022-07-28T04:14:25.000' AS DateTime), 0, 1, N'guest')
INSERT [dbo].[CONTENTS] ([id], [subject], [content], [regTime], [views], [active], [account_id]) VALUES (2, N'Cần mua điện thoại cũ', N'Mình đang thu gom các loại điện thoai cũ về để lấy linh kiện, ai có liên hệ với mình, cảm ơn.', CAST(N'2020-10-04T17:19:21.000' AS DateTime), 0, 1, N'user')
INSERT [dbo].[CONTENTS] ([id], [subject], [content], [regTime], [views], [active], [account_id]) VALUES (3, N'Cần tìm việc làm thời vụ', N'Tui rảnh từ giờ tới cuối tháng, ai có công việc gì cho tui theo làm với. Có sức khỏe và kinh nghiệm làm sales', CAST(N'2020-10-13T08:21:02.000' AS DateTime), 0, 1, N'guest')
INSERT [dbo].[CONTENTS] ([id], [subject], [content], [regTime], [views], [active], [account_id]) VALUES (4, N'Tuyển nhân viên phục vụ', N'Quán sắp mở cần tìm nhân viên chính thức, lương trả theo tuần và có chỗ nghỉ lại cho nhân viên', CAST(N'2021-12-06T16:44:00.000' AS DateTime), 0, 1, N'guest')
INSERT [dbo].[CONTENTS] ([id], [subject], [content], [regTime], [views], [active], [account_id]) VALUES (5, N'Dư một con cho ai lấy ib', N'Hiện tại mình dư con VGA để rẻ cho ae nào cần. Giảm mạnh cho ae thiện lành 🤣', CAST(N'2022-02-25T16:06:27.000' AS DateTime), 0, 1, N'guest')
SET IDENTITY_INSERT [dbo].[CONTENTS] OFF
GO
INSERT [dbo].[LIKES] ([account_id], [content_id]) VALUES (N'guest', 1)
INSERT [dbo].[LIKES] ([account_id], [content_id]) VALUES (N'guest', 2)
INSERT [dbo].[LIKES] ([account_id], [content_id]) VALUES (N'guest', 3)
INSERT [dbo].[LIKES] ([account_id], [content_id]) VALUES (N'user', 1)
INSERT [dbo].[LIKES] ([account_id], [content_id]) VALUES (N'user', 2)
INSERT [dbo].[LIKES] ([account_id], [content_id]) VALUES (N'user', 4)
GO
INSERT [dbo].[CONTENT_IMAGES] ([image], [content_id]) VALUES (N'image1.jpg', 1)
INSERT [dbo].[CONTENT_IMAGES] ([image], [content_id]) VALUES (N'image2.jpg', 1)
INSERT [dbo].[CONTENT_IMAGES] ([image], [content_id]) VALUES (N'image3.jpg', 1)
INSERT [dbo].[CONTENT_IMAGES] ([image], [content_id]) VALUES (N'image4.jpg', 2)
INSERT [dbo].[CONTENT_IMAGES] ([image], [content_id]) VALUES (N'image5.jpg', 2)
INSERT [dbo].[CONTENT_IMAGES] ([image], [content_id]) VALUES (N'image6.jpg', 3)
INSERT [dbo].[CONTENT_IMAGES] ([image], [content_id]) VALUES (N'image7.jpg', 3)
INSERT [dbo].[CONTENT_IMAGES] ([image], [content_id]) VALUES (N'image8.jpg', 3)
INSERT [dbo].[CONTENT_IMAGES] ([image], [content_id]) VALUES (N'image9.jpg', 3)
INSERT [dbo].[CONTENT_IMAGES] ([image], [content_id]) VALUES (N'image10.jpg', 4)
INSERT [dbo].[CONTENT_IMAGES] ([image], [content_id]) VALUES (N'image11.jpg', 5)
INSERT [dbo].[CONTENT_IMAGES] ([image], [content_id]) VALUES (N'image12.jpg', 5)
GO
INSERT [dbo].[CATEGORIES] ([id], [name]) VALUES (N'aDaRBOaQ', N'Người cấp')
INSERT [dbo].[CATEGORIES] ([id], [name]) VALUES (N'EIHOmZSO', N'Tuyển dụng')
INSERT [dbo].[CATEGORIES] ([id], [name]) VALUES (N'HkMDmYcC', N'Mua bán')
INSERT [dbo].[CATEGORIES] ([id], [name]) VALUES (N'KPodRYkR', N'Tổng hợp')
INSERT [dbo].[CATEGORIES] ([id], [name]) VALUES (N'qbUgTVKh', N'Giúp đỡ')
INSERT [dbo].[CATEGORIES] ([id], [name]) VALUES (N'ThdwQdWH', N'Việc làm')
INSERT [dbo].[CATEGORIES] ([id], [name]) VALUES (N'xiUuqERM', N'Người tìm')
GO
INSERT [dbo].[CONTENT_TYPES] ([content_id], [category_id]) VALUES (1, N'HkMDmYcC')
INSERT [dbo].[CONTENT_TYPES] ([content_id], [category_id]) VALUES (1, N'xiUuqERM')
INSERT [dbo].[CONTENT_TYPES] ([content_id], [category_id]) VALUES (2, N'qbUgTVKh')
INSERT [dbo].[CONTENT_TYPES] ([content_id], [category_id]) VALUES (2, N'ThdwQdWH')
INSERT [dbo].[CONTENT_TYPES] ([content_id], [category_id]) VALUES (3, N'EIHOmZSO')
INSERT [dbo].[CONTENT_TYPES] ([content_id], [category_id]) VALUES (4, N'KPodRYkR')
INSERT [dbo].[CONTENT_TYPES] ([content_id], [category_id]) VALUES (5, N'aDaRBOaQ')
INSERT [dbo].[CONTENT_TYPES] ([content_id], [category_id]) VALUES (5, N'KPodRYkR')
INSERT [dbo].[CONTENT_TYPES] ([content_id], [category_id]) VALUES (5, N'qbUgTVKh')
GO
INSERT [dbo].[APIs] ([id], [value]) VALUES (N'path_img_account', N'/dir/images/account')
INSERT [dbo].[APIs] ([id], [value]) VALUES (N'path_img_content', N'/dir/images/content')
GO
