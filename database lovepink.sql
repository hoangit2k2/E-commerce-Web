use master
create database LovePink
use LovePink
create table users(
	id int identity primary key,
	username varchar(100),
	password varchar(200),
)
create table staff(
	id int identity,
	userid int,
	name nvarchar(100),
	addres nvarchar(100),
	email varchar(100),
	phone varchar(100),
	image varchar(500),
	role bit,
	foreign key (userid) references users(id)
)
create table customer(
	id int identity primary key,
	userid int,
	name nvarchar(100),
	address nvarchar(100),
	phone varchar(15),
	email varchar(100),
	image varchar(200),
	foreign key (userid) references users(id)
)
create table category(
	id int identity primary key,
	name nvarchar(50),
	image varchar(100)
)
create table contents(
	id int identity primary key,
	customerid int,
	categoryid int,
	namecontent nvarchar(100),
	subject nvarchar(500),
	price int,
	email varchar(100),
	phone varchar(15),
	address nvarchar(100),
	datetime datetime,
	status bit,
	foreign key (customerid) references customer(id),
	foreign key (categoryid) references category(id)
)
create table detailcontent(
	id int identity,
	contentid int,
	origin nvarchar(100),
	companyname nvarchar(100),
	statusproduct nvarchar(50),
	yearmanufacture nvarchar(100),
	parameter nvarchar(50),
	foreign key (contentid) references contents(id)
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



/**Create databse**/
use lovepink
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

insert into category values('điện thoại','phone.jpg')
insert into contents values(1, 1, 'Dư điện thoại Iphone 12','Hiện tại không dùng đến nữa nên muốn bán đi với giá rẻ',12000000,'094124662', 'hoang@gmail.com','213, Lê Đức Thọ, gò vấp, HCM', '2020-10-30 10:07:11', 'true')

delete from contents where id = 1