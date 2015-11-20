-- MySQL dump 10.13  Distrib 5.6.24, for Win32 (x86)
--
-- Host: localhost    Database: school
-- ------------------------------------------------------
-- Server version	5.6.26-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student` (
  `StudentID` int(11) NOT NULL,
  `StudentFN` varchar(255) DEFAULT NULL,
  `StudentLN` varchar(255) DEFAULT NULL,
  `StudentCI` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`StudentID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES (12345,'John','Doe','3015555555'),(54321,'Jane','Brown','5555555555'),(11111,'Femi','Oyenusi','1111111111'),(22222,'Jacob','Doiron','2222222222'),(33333,'Joe','Miller','3333333333'),(44444,'Benjamin','Dodson','4444444444'),(55555,'Amy','Whittington','5555555555'),(66666,'Caroline','Orlando','6666666666'),(77777,'Nicholas','Custodio','7777777777'),(88888,'Caleb','Jardeleza','9999999999'),(99999,'Mark','Hardesty','0000000000');
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stuschedule`
--

DROP TABLE IF EXISTS `stuschedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stuschedule` (
  `StudentID` int(11) NOT NULL,
  `1Class` varchar(3) DEFAULT NULL,
  `2Class` varchar(3) DEFAULT NULL,
  `3Class` varchar(3) DEFAULT NULL,
  `4Class` varchar(3) DEFAULT NULL,
  `5Class` varchar(3) DEFAULT NULL,
  `6Class` varchar(3) DEFAULT NULL,
  PRIMARY KEY (`StudentID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stuschedule`
--

LOCK TABLES `stuschedule` WRITE;
/*!40000 ALTER TABLE `stuschedule` DISABLE KEYS */;
INSERT INTO `stuschedule` VALUES (12345,'100','251','302','111','121','300'),(54321,'251','111','100','300','121','302'),(11111,'251','111','300','100','121','302'),(22222,'302','111','100','300','251','121'),(33333,'251','111','100','300','121','302'),(44444,'251','121','111','100','300','302'),(55555,'251','111','100','300','121','302'),(66666,'251','121','100','302','111','300'),(77777,'251','111','100','300','121','302'),(88888,'111','251','100','300','121','302'),(99999,'251','111','300','100','121','302');
/*!40000 ALTER TABLE `stuschedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teacher`
--

DROP TABLE IF EXISTS `teacher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teacher` (
  `TeacherID` int(11) NOT NULL,
  `TeacherFN` varchar(255) DEFAULT NULL,
  `TeacherLN` varchar(255) DEFAULT NULL,
  `Subject` varchar(255) DEFAULT NULL,
  `Classroom` varchar(3) DEFAULT NULL,
  PRIMARY KEY (`TeacherID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teacher`
--

LOCK TABLES `teacher` WRITE;
/*!40000 ALTER TABLE `teacher` DISABLE KEYS */;
INSERT INTO `teacher` VALUES (11223,'Bob','Smith','Mathematics','100'),(12123,'Jim','Johnson','English','251'),(22123,'Lindsay','Jamieson','Computer Science','111'),(11122,'Christopher','Black','Art','300'),(22223,'Matt','Jones','Chemistry','121'),(11112,'Bob','Smith','History','302');
/*!40000 ALTER TABLE `teacher` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-10-14 23:47:10
