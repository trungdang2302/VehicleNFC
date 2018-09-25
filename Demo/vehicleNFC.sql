CREATE DATABASE  IF NOT EXISTS `vehiclenfc` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `vehiclenfc`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: vehiclenfc
-- ------------------------------------------------------
-- Server version	5.7.21-log

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
-- Table structure for table `tbl_meter`
--

DROP TABLE IF EXISTS `tbl_meter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_meter` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `location` varchar(255) NOT NULL,
  `price` int(11) DEFAULT NULL,
  `tbl_meter_status_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tbl_meter_tbl_meter_status1_idx` (`tbl_meter_status_id`),
  CONSTRAINT `fk_tbl_meter_tbl_meter_status1` FOREIGN KEY (`tbl_meter_status_id`) REFERENCES `tbl_meter_status` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_meter`
--

LOCK TABLES `tbl_meter` WRITE;
/*!40000 ALTER TABLE `tbl_meter` DISABLE KEYS */;
INSERT INTO `tbl_meter` VALUES (1,'A4_Quang Trung',10,1);
/*!40000 ALTER TABLE `tbl_meter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_meter_has_tbl_vehicle_type`
--

DROP TABLE IF EXISTS `tbl_meter_has_tbl_vehicle_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_meter_has_tbl_vehicle_type` (
  `tbl_meter_id` int(11) NOT NULL,
  `tbl_vehicle_type_id` int(11) NOT NULL,
  PRIMARY KEY (`tbl_meter_id`,`tbl_vehicle_type_id`),
  KEY `fk_tbl_meter_has_tbl_vehicle_type_tbl_vehicle_type1_idx` (`tbl_vehicle_type_id`),
  KEY `fk_tbl_meter_has_tbl_vehicle_type_tbl_meter1_idx` (`tbl_meter_id`),
  CONSTRAINT `fk_tbl_meter_has_tbl_vehicle_type_tbl_meter1` FOREIGN KEY (`tbl_meter_id`) REFERENCES `tbl_meter` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tbl_meter_has_tbl_vehicle_type_tbl_vehicle_type1` FOREIGN KEY (`tbl_vehicle_type_id`) REFERENCES `tbl_vehicle_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_meter_has_tbl_vehicle_type`
--

LOCK TABLES `tbl_meter_has_tbl_vehicle_type` WRITE;
/*!40000 ALTER TABLE `tbl_meter_has_tbl_vehicle_type` DISABLE KEYS */;
INSERT INTO `tbl_meter_has_tbl_vehicle_type` VALUES (1,1),(1,2);
/*!40000 ALTER TABLE `tbl_meter_has_tbl_vehicle_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_meter_status`
--

DROP TABLE IF EXISTS `tbl_meter_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_meter_status` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_meter_status`
--

LOCK TABLES `tbl_meter_status` WRITE;
/*!40000 ALTER TABLE `tbl_meter_status` DISABLE KEYS */;
INSERT INTO `tbl_meter_status` VALUES (1,'Active'),(2,'De-active');
/*!40000 ALTER TABLE `tbl_meter_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_transaction`
--

DROP TABLE IF EXISTS `tbl_transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_transaction` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date_check_in` bigint(20) NOT NULL,
  `date_check_out` bigint(20) DEFAULT NULL,
  `date_ended` bigint(20) NOT NULL,
  `price` int(11) NOT NULL,
  `tbl_meter_id` int(11) NOT NULL,
  `tbl_transaction_status_id` int(11) NOT NULL,
  `tbl_user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tbl_transaction_tbl_meter1_idx` (`tbl_meter_id`),
  KEY `fk_tbl_transaction_tbl_transaction_status1_idx` (`tbl_transaction_status_id`),
  KEY `fk_tbl_transaction_tbl_user1_idx` (`tbl_user_id`),
  CONSTRAINT `fk_tbl_transaction_tbl_meter1` FOREIGN KEY (`tbl_meter_id`) REFERENCES `tbl_meter` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tbl_transaction_tbl_transaction_status1` FOREIGN KEY (`tbl_transaction_status_id`) REFERENCES `tbl_transaction_status` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tbl_transaction_tbl_user1` FOREIGN KEY (`tbl_user_id`) REFERENCES `tbl_user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_transaction`
--

LOCK TABLES `tbl_transaction` WRITE;
/*!40000 ALTER TABLE `tbl_transaction` DISABLE KEYS */;
INSERT INTO `tbl_transaction` VALUES (1,20180924000000,20180924000000,20180924000000,177,1,1,1),(22,1537803359567,NULL,1537803359567,10,1,1,1),(23,1537803443443,NULL,1537803443443,10,1,1,1),(24,1537803937279,NULL,1537803937279,10,1,1,1),(25,1537804894117,NULL,1537804894117,10,1,1,1),(26,1537848270219,NULL,1537848270219,10,1,1,1),(27,1537849808029,NULL,1537849808029,10,1,1,1),(28,1537854238743,NULL,1537854238743,10,1,1,1);
/*!40000 ALTER TABLE `tbl_transaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_transaction_status`
--

DROP TABLE IF EXISTS `tbl_transaction_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_transaction_status` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_transaction_status`
--

LOCK TABLES `tbl_transaction_status` WRITE;
/*!40000 ALTER TABLE `tbl_transaction_status` DISABLE KEYS */;
INSERT INTO `tbl_transaction_status` VALUES (1,'Open'),(2,'Close'),(3,'Late');
/*!40000 ALTER TABLE `tbl_transaction_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_user`
--

DROP TABLE IF EXISTS `tbl_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `phone_number` varchar(15) NOT NULL,
  `password` varchar(60) NOT NULL,
  `money` int(11) NOT NULL,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `vehicle_number` varchar(10) NOT NULL,
  `license_plate_id` varchar(10) NOT NULL,
  `tbl_vehicle_type_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `phone_number_UNIQUE` (`phone_number`),
  KEY `fk_tbl_user_tbl_vehicle_type1_idx` (`tbl_vehicle_type_id`),
  CONSTRAINT `fk_tbl_user_tbl_vehicle_type1` FOREIGN KEY (`tbl_vehicle_type_id`) REFERENCES `tbl_vehicle_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_user`
--

LOCK TABLES `tbl_user` WRITE;
/*!40000 ALTER TABLE `tbl_user` DISABLE KEYS */;
INSERT INTO `tbl_user` VALUES (1,'123456789','123',1000,'fn','ln','54A21441','88888888',1);
/*!40000 ALTER TABLE `tbl_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_vehicle_type`
--

DROP TABLE IF EXISTS `tbl_vehicle_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_vehicle_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_vehicle_type`
--

LOCK TABLES `tbl_vehicle_type` WRITE;
/*!40000 ALTER TABLE `tbl_vehicle_type` DISABLE KEYS */;
INSERT INTO `tbl_vehicle_type` VALUES (1,'Ô tô con'),(2,'Ô tô bán tải'),(3,'Xe tải');
/*!40000 ALTER TABLE `tbl_vehicle_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'vehiclenfc'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-09-25 21:49:08
