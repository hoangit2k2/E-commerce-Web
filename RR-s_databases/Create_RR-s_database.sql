USE MASTER
GO
IF EXISTS (SELECT name FROM sys.databases WHERE name = 'RRS_DB')
    DROP DATABASE RRS_DB
GO
	CREATE DATABASE RRS_DB
GO
	USE RRS_DB
GO
IF EXISTS (SELECT name FROM sys.tables WHERE name = 'ACCOUNTS')
    DROP TABLE ACCOUNTS
CREATE TABLE ACCOUNTS (
    username varchar(20) primary key,
    password varchar(30),
    name nvarchar(50),
    email varchar(50) unique,
    image nvarchar(255),
    regDate date
);

IF EXISTS (SELECT name FROM sys.tables WHERE name = 'STAFFS')
    DROP TABLE STAFFS
CREATE TABLE STAFFS (
    username varchar(20) primary key,
    password varchar(30),
    email varchar(50) unique,
    role bit,
    image nvarchar(255)
);

IF EXISTS (SELECT name FROM sys.tables WHERE name = 'CONTENTS')
    DROP TABLE CONTENTS 
CREATE TABLE CONTENTS (
    id int identity primary key,
    subject nvarchar(80),
    content nvarchar(255),
    regTime datetime,
    views int default 0,
    active bit default 0,
    account_id varchar(20) foreign key references ACCOUNTS(username) on update cascade on delete cascade
);

IF EXISTS (SELECT name FROM sys.tables WHERE name = 'CONTENT_IMAGES')
    DROP TABLE CONTENT_IMAGES 
CREATE TABLE CONTENT_IMAGES (
    image nvarchar(255) not null,
    content_id int foreign key references CONTENTS(id) on update cascade on delete cascade,
	primary key(image, content_id)
);

IF EXISTS (SELECT name FROM sys.tables WHERE name = 'CATEGORIES')
    DROP TABLE CATEGORIES
GO
CREATE TABLE CATEGORIES (
    id char(8) primary key,
    name nvarchar(30)
);

IF EXISTS (SELECT name FROM sys.tables WHERE name = 'CONTENT_TYPES')
    DROP TABLE CONTENT_TYPES 
CREATE TABLE CONTENT_TYPES (
    content_id int foreign key references CONTENTS(id) on update cascade on delete cascade,
    category_id char(8) foreign key references CATEGORIES(id) on update cascade on delete no action,
    primary key (content_id, category_id)
); 

IF EXISTS (SELECT name FROM sys.tables WHERE name = 'LIKES')
    DROP TABLE LIKES 
CREATE TABLE LIKES (
    account_id varchar(20) foreign key references ACCOUNTS(username) on delete cascade,
    content_id int foreign key references CONTENTS(id) on update cascade,
	exeTime datetime default GETDATE(),
	unique(account_id, content_id)
);
GO