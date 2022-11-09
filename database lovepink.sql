use master
create database LovePink1
use LovePink1
create table users(
	username varchar(100) primary key,
	password varchar(100),
	name nvarchar(100),
	address nvarchar(100),
	phone varchar(15),
	email varchar(100),
	image varchar(200),
	role varchar(5)
)
create table category(
	id int identity primary key,
	name nvarchar(50),
	image varchar(100)
)
create table contents(
	id int identity primary key,
	usernameid varchar(100),
	categoryid int,
	namecontent nvarchar(100),
	subject nvarchar(500),
	price int,
	email varchar(100),
	phone varchar(15),
	address nvarchar(100),
	datetime datetime,
	status bit,
	foreign key (usernameid) references users(username),
	foreign key (categoryid) references category(id)
)
create table image (
	contentid int ,
	image varchar(100),
	foreign key (contentid) references contents(id)
)
create table about(
	id int identity primary key,
	logo varchar(100),
)
create table banner(
	aboutid int,
	image varchar(100),
	foreign key (aboutid) references about(id)
)

create table likes (
	usernameid varchar(100),
	contentid int
	foreign key (usernameid) references users(username),
	foreign key (contentid) references contents(id)
)

/**Create databse
insert into users values('hoang','123')
insert into users values('hoa','123')
insert into users values('thinh','123')
insert into users values('quoc','123')
insert into users values('nhan','123')

insert into customer values(1,'Nguyen ba hoang', '321 lê đức thọ, gò vấp, TPHCM', '084859124','hoang@gmail.com','hoang.jpg')
insert into customer values(2,'Ngo Duy Hoa', '321 phan van trị, bình thạnh, TPHCM', '084459124','hoa@gmail.com','hoa.jpg')
insert into customer values(3,'Hồ Nguyên Quốc', '321 trường chinh, Bình tân, TPHCM', '084259124','quoc@gmail.com','quoc.jpg')
insert into customer values(4,'Bùi quốc Thịnh', '123 Phạm văn đồng, Thủ đức, TPHCM', '084859124','thinh@gmail.com','thinh.jpg')
insert into customer values(5,'Ca Thành Nhân', '11 lê đức thọ, quận 12, TPHCM', '084859124','nhan@gmail.com','nhan.jpg')
**/

insert into users values('hoang','123','Nguyen ba hoang', '321 lê đức thọ, gò vấp, TPHCM', '084859124','hoang@gmail.com','hoang.jpg','admin')
insert into category values('điện thoại','phone.jpg')

INSERT [dbo].[contents] ([usernameid], [categoryid], [namecontent], [subject], [price], [email], [phone], [address], [datetime], [status]) VALUES ( 'hoang', 1, N'Dư Note 10', N'Máy mới mua về xài thử không hợp nên bán', 1000, N'hoangx3qt@gmail.com', N'+84972373145', N'123', CAST(N'2022-11-05T15:35:28.083' AS DateTime), 0)
INSERT [dbo].[contents] ([usernameid], [categoryid], [namecontent], [subject], [price], [email], [phone], [address], [datetime], [status]) VALUES ( 'hoang', 1, N'Macbook Air 2022', N'laptop giá rẻ', 10000, N'hoang@gmail.com', N'093818341', N'Phạm văn chiêu, Gò Vấp, TP. Hồ Chí Minh', CAST(N'2022-11-05T16:44:37.053' AS DateTime), 0)
INSERT [dbo].[contents] ([usernameid], [categoryid], [namecontent], [subject], [price], [email], [phone], [address], [datetime], [status]) VALUES ( 'hoang', 1, N'Macbook AIR 2022', N'Bán mac giá rẻ', 100000, N'nguyenhoang2002it@gmail.com', N'0972373145', N'123', CAST(N'2022-11-05T16:47:16.700' AS DateTime), 0)
INSERT [dbo].[contents] ([usernameid], [categoryid], [namecontent], [subject], [price], [email], [phone], [address], [datetime], [status]) VALUES ( 'hoang', 1, N'Laptop Delll Express', N'Laptop đẹp', 1000000, N'nguyenhoang2002it@gmail.com', N'0972373145', N'123', CAST(N'2022-11-05T16:58:32.650' AS DateTime), 0)
INSERT [dbo].[contents] ([usernameid], [categoryid], [namecontent], [subject], [price], [email], [phone], [address], [datetime], [status]) VALUES ( 'hoang', 1, N'Iphone Chất Lượng', N'Iphone Giá rê', 10000000, N'hoangx3qt@gmail.com', N'+84972373145', N'123', CAST(N'2022-11-05T16:59:33.787' AS DateTime), 0)
INSERT [dbo].[contents] ([usernameid], [categoryid], [namecontent], [subject], [price], [email], [phone], [address], [datetime], [status]) VALUES ( 'hoang', 1, N'LapTop Gamming', N'Laptop Cày game', 1000000000, N'nguyenhoang2002it@gmail.com', N'0972373145', N'123', CAST(N'2022-11-05T17:02:19.017' AS DateTime), 0)
INSERT [dbo].[contents] ([usernameid], [categoryid], [namecontent], [subject], [price], [email], [phone], [address], [datetime], [status]) VALUES ( 'hoang', 1, N'LapTop Văn Phònh', N'Giá rẻ uy tín', 100000, N'nguyenhoang2002it@gmail.com', N'0972373145', N'123', CAST(N'2022-11-05T17:04:42.753' AS DateTime), 0)
INSERT [dbo].[contents] ([usernameid], [categoryid], [namecontent], [subject], [price], [email], [phone], [address], [datetime], [status]) VALUES ( 'hoang', 1, N'MacBook', N'Giá rẻ uy tín', 100000, N'nguyenhoang2002it@gmail.com', N'0972373145', N'123', CAST(N'2022-11-05T17:05:31.677' AS DateTime), 0)
INSERT [dbo].[contents] ([usernameid], [categoryid], [namecontent], [subject], [price], [email], [phone], [address], [datetime], [status]) VALUES ( 'hoang', 1, N'Macbook cao cấp', N'Cao cấp giá phù hợp', 10000000, N'nguyenhoang2002it@gmail.com', N'0972373145', N'123', CAST(N'2022-11-05T17:06:57.450' AS DateTime), 0)
INSERT [dbo].[contents] ([usernameid], [categoryid], [namecontent], [subject], [price], [email], [phone], [address], [datetime], [status]) VALUES ( 'hoang', 1, N'Laptop Màn đẹp', N'Màn đẹp', 100000, N'nguyenhoang2002it@gmail.com', N'0972373145', N'123', CAST(N'2022-11-05T17:08:03.190' AS DateTime), 0)

INSERT [dbo].[image] ([contentid], [image]) VALUES (11, N'25.jpg')
INSERT [dbo].[image] ([contentid], [image]) VALUES (11, N'26.jpg')
INSERT [dbo].[image] ([contentid], [image]) VALUES (11, N'27.jpg')
INSERT [dbo].[image] ([contentid], [image]) VALUES (2, N'31.jpg')
INSERT [dbo].[image] ([contentid], [image]) VALUES (2, N'28.jpg')
INSERT [dbo].[image] ([contentid], [image]) VALUES (2, N'29.jpg')
INSERT [dbo].[image] ([contentid], [image]) VALUES (3, N'35.jpg')
INSERT [dbo].[image] ([contentid], [image]) VALUES (3, N'34.jpg')
INSERT [dbo].[image] ([contentid], [image]) VALUES (3, N'1568434178299_8049307.jpg')
INSERT [dbo].[image] ([contentid], [image]) VALUES (4, N'10-laptop-man-hinh-4k-chan-thuc-song-dong-nhat-2020.jpg')
INSERT [dbo].[image] ([contentid], [image]) VALUES (4, N'Dell-XPS-15-1024x572.jpg')
INSERT [dbo].[image] ([contentid], [image]) VALUES (4, N'photo-1-15706463929181755249740.jpg')
INSERT [dbo].[image] ([contentid], [image]) VALUES (5, N'Dell-XPS-15-1024x572.jpg')
INSERT [dbo].[image] ([contentid], [image]) VALUES (5, N'thumb_800x501.jpg')
INSERT [dbo].[image] ([contentid], [image]) VALUES (5, N'img_20200707105325.jpg')
INSERT [dbo].[image] ([contentid], [image]) VALUES (6, N'Laptop-cu-cau-hinh-cao-2.jpg')
INSERT [dbo].[image] ([contentid], [image]) VALUES (6, N'laptop.jpg')
INSERT [dbo].[image] ([contentid], [image]) VALUES (6, N'36.jfif')
INSERT [dbo].[image] ([contentid], [image]) VALUES (7, N'laptop.jpg')
INSERT [dbo].[image] ([contentid], [image]) VALUES (7, N'36.jfif')
INSERT [dbo].[image] ([contentid], [image]) VALUES (7, N'35.jpg')
INSERT [dbo].[image] ([contentid], [image]) VALUES (8, N'28.jpg')
INSERT [dbo].[image] ([contentid], [image]) VALUES (8, N'35.jpg')
INSERT [dbo].[image] ([contentid], [image]) VALUES (8, N'img_20200707105325.jpg')
INSERT [dbo].[image] ([contentid], [image]) VALUES (9, N'laptop-vien-man-hinh-mong-co-nhung-uu-va-nhuoc-diem-gi--4.jpg')
INSERT [dbo].[image] ([contentid], [image]) VALUES (9, N'34.jpg')
INSERT [dbo].[image] ([contentid], [image]) VALUES (9, N'34.jpg')
INSERT [dbo].[image] ([contentid], [image]) VALUES (10, N'img_20200707105325.jpg')
INSERT [dbo].[image] ([contentid], [image]) VALUES (10, N'34.jpg')
INSERT [dbo].[image] ([contentid], [image]) VALUES (10, N'34.jpg')
INSERT [dbo].[image] ([contentid], [image]) VALUES (12, N'28.jpg')
INSERT [dbo].[image] ([contentid], [image]) VALUES (12, N'34.jpg')
INSERT [dbo].[image] ([contentid], [image]) VALUES (12, N'34.jpg')


delete from contents where id = 1