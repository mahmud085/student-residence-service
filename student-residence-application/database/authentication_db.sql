create database if not exists `student_residence_authentication`;

use `student_residence_authentication`;

CREATE TABLE IF NOT EXISTS authentication (
  id INT NOT NULL AUTO_INCREMENT,
  userId VARCHAR(100) NOT NULL,
  generatedTime DATETIME NOT NULL,
  expiryTime DATE NOT NULL,
  accessToken VARCHAR(1000) NOT NULL,
  PRIMARY KEY (id));



CREATE TABLE IF NOT EXISTS user (
  id INT NOT NULL AUTO_INCREMENT,
  userId VARCHAR(100) NOT NULL,
  userName VARCHAR(100) NOT NULL,
  password VARCHAR(200) NOT NULL,
  userEmail VARCHAR(100) NOT NULL,
  userType ENUM('Admin','Caretaker','Resident') NOT NULL,
  PRIMARY KEY (id),
  unique key (userId)
  );
  
insert into user (userId, userName, userEmail, password, userType)
values 
('AU1', 'Admin User 1', 'admin.user1@domain.com', '123456', 'Admin'),
('AU2', 'Admin User 2', 'admin.user2@domain.com', '123456', 'Admin'),
('CU1', 'Caretaker User 1', 'caretaker.user1@domain.com', '123456', 'Caretaker'),
('CU2', 'Caretaker User 2', 'caretaker.user2@domain.com', '123456', 'Caretaker'),
('RU1', 'Resident User 1', 'resident.user1@domain.com', '123456', 'Resident'),
('RU2', 'Resident User 2', 'resident.user2@domain.com', '123456', 'Resident');
