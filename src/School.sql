CREATE TABLE `student` (
  `StudentID` int(11) NOT NULL,
  `StudentFN` varchar(255) DEFAULT NULL,
  `StudentLN` varchar(255) DEFAULT NULL,
  `StudentCI` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`StudentID`)
);

INSERT INTO `student` VALUES (12345,'John','Doe','3015555555'),(54321,'Jane','Brown','5555555555'),(11111,'Femi','Oyenusi','1111111111'),(22222,'Jacob','Doiron','2222222222'),(33333,'Joe','Miller','3333333333'),(44444,'Benjamin','Dodson','4444444444'),(55555,'Amy','Whittington','5555555555'),(66666,'Caroline','Orlando','6666666666'),(77777,'Nicholas','Custodio','7777777777'),(88888,'Caleb','Jardeleza','9999999999'),(99999,'Mark','Hardesty','0000000000');

CREATE TABLE `stuschedule` (
  `StudentID` int(11) NOT NULL,
  `1Class` varchar(3) DEFAULT NULL,
  `2Class` varchar(3) DEFAULT NULL,
  `3Class` varchar(3) DEFAULT NULL,
  `4Class` varchar(3) DEFAULT NULL,
  `5Class` varchar(3) DEFAULT NULL,
  `6Class` varchar(3) DEFAULT NULL,
  PRIMARY KEY (`StudentID`)
);

INSERT INTO `stuschedule` VALUES (12345,'100','251','302','111','121','300'),(54321,'251','111','100','300','121','302'),(11111,'251','111','300','100','121','302'),(22222,'302','111','100','300','251','121'),(33333,'251','111','100','300','121','302'),(44444,'251','121','111','100','300','302'),(55555,'251','111','100','300','121','302'),(66666,'251','121','100','302','111','300'),(77777,'251','111','100','300','121','302'),(88888,'111','251','100','300','121','302'),(99999,'251','111','300','100','121','302');

CREATE TABLE `teacher` (
  `TeacherID` int(11) NOT NULL,
  `TeacherFN` varchar(255) DEFAULT NULL,
  `TeacherLN` varchar(255) DEFAULT NULL,
  `Subject` varchar(255) DEFAULT NULL,
  `Classroom` varchar(3) DEFAULT NULL,
  PRIMARY KEY (`TeacherID`)
);

INSERT INTO `teacher` VALUES (11223,'Bob','Smith','Mathematics','100'),(12123,'Jim','Johnson','English','251'),(22123,'Lindsay','Jamieson','Computer Science','111'),(11122,'Christopher','Black','Art','300'),(22223,'Matt','Jones','Chemistry','121'),(11112,'Bob','Smith','History','302');