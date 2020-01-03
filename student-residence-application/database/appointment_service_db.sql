use projectRestApi;

CREATE TABLE IF NOT EXISTS appointment (
 Id INT NOT NULL auto_increment,
  appointmentId VARCHAR(100) NOT NULL,
  contractorsName VARCHAR(45) NULL,
  contractId VARCHAR(45) NULL,
  roomNumber VARCHAR(45) NULL,
  appointmentType ENUM('MoveIn', 'MoveOut', 'Miscellaneous') NULL,
  issue VARCHAR(250) NULL,
  desiredDate DATE NULL,
  status ENUM('Accepted', 'Denied', 'Received') NULL,
  priority ENUM('Low', 'Normal', 'High') NULL,
  createdBy VARCHAR(45) NULL,
  createdOn DATE NULL,
 
  PRIMARY KEY (Id),
   unique key (appointmentId)
  )