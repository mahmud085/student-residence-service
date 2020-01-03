create database if not exists `student_residence_contract`;

use `student_residence_contract`;

create table if not exists `room` (
  `id` int not null auto_increment,
  `roomId` varchar(50) not null,
  primary key (`id`),
  unique key (`roomId`)
);

create table if not exists `contract` (
  `id` int not null auto_increment,
  `contractId` varchar(50) not null,
  `contractorsUserId` varchar(50) not null,
  `contractorsName` varchar(50) not null,
  `contractorsEmail` varchar(50) not null,
  `contractorsPhone` varchar(50) not null,
  `roomNumber` varchar(50) not null,
  `startDate` date not null,
  `endDate` date not null,
  `status` enum('Confirmed', 'Unconfirmed') not null,
  `createdBy` varchar(50) not null,
  `createdOn` date not null,
  primary key (`id`),
  unique key (`contractId`),
  foreign key (`roomNumber`) references room (`roomId`)
);

insert ignore into room (roomId)
values
('Room_0'),
('Room_1'),
('Room_2'),
('Room_3'),
('Room_4'),
('Room_5'),
('Room_6'),
('Room_7'),
('Room_8'),
('Room_9');
