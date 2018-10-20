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
-- Table structure for table `tbl_location`
--

DROP TABLE IF EXISTS `tbl_location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_location` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `location` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `is_activated` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_location`
--

LOCK TABLES `tbl_location` WRITE;
/*!40000 ALTER TABLE `tbl_location` DISABLE KEYS */;
INSERT INTO `tbl_location` VALUES (1,'Quang Trung',NULL,'');
/*!40000 ALTER TABLE `tbl_location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_location_has_tbl_policy`
--

DROP TABLE IF EXISTS `tbl_location_has_tbl_policy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_location_has_tbl_policy` (
  `tbl_location_id` int(11) NOT NULL,
  `tbl_policy_id` int(11) NOT NULL,
  PRIMARY KEY (`tbl_location_id`,`tbl_policy_id`),
  KEY `fk_tbl_location_has_tbl_policy_tbl_policy1_idx` (`tbl_policy_id`),
  KEY `fk_tbl_location_has_tbl_policy_tbl_location_idx` (`tbl_location_id`),
  CONSTRAINT `fk_tbl_location_has_tbl_policy_tbl_location` FOREIGN KEY (`tbl_location_id`) REFERENCES `tbl_location` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tbl_location_has_tbl_policy_tbl_policy1` FOREIGN KEY (`tbl_policy_id`) REFERENCES `tbl_policy` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_location_has_tbl_policy`
--

LOCK TABLES `tbl_location_has_tbl_policy` WRITE;
/*!40000 ALTER TABLE `tbl_location_has_tbl_policy` DISABLE KEYS */;
INSERT INTO `tbl_location_has_tbl_policy` VALUES (1,1);
/*!40000 ALTER TABLE `tbl_location_has_tbl_policy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_order`
--

DROP TABLE IF EXISTS `tbl_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `total` double DEFAULT NULL,
  `check_in_date` bigint(20) NOT NULL,
  `check_out_date` bigint(20) DEFAULT NULL,
  `duration` bigint(20) DEFAULT NULL,
  `allowed_parking_from` bigint(20) DEFAULT NULL,
  `allowed_parking_to` bigint(20) DEFAULT NULL,
  `min_hour` int(11) DEFAULT '0',
  `tbl_order_status_id` int(11) NOT NULL,
  `tbl_user_id` int(11) NOT NULL,
  `tbl_location_id` int(11) NOT NULL,
  `tbl_vehicle_type_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tbl_order_tbl_location1_idx` (`tbl_location_id`),
  KEY `FKcew8wssc7f6nr5jev20ltjwu8` (`tbl_order_status_id`),
  KEY `FKpewslmsot0yad4neixn3v2qqc` (`tbl_user_id`),
  KEY `fk_tbl_order_tbl_vehicle_type_idx` (`tbl_vehicle_type_id`),
  CONSTRAINT `FKcew8wssc7f6nr5jev20ltjwu8` FOREIGN KEY (`tbl_order_status_id`) REFERENCES `tbl_order_status` (`id`),
  CONSTRAINT `FKpewslmsot0yad4neixn3v2qqc` FOREIGN KEY (`tbl_user_id`) REFERENCES `tbl_user` (`id`),
  CONSTRAINT `fk_tbl_order_tbl_location1` FOREIGN KEY (`tbl_location_id`) REFERENCES `tbl_location` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tbl_order_tbl_vehicle_type` FOREIGN KEY (`tbl_vehicle_type_id`) REFERENCES `tbl_vehicle_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_order`
--

LOCK TABLES `tbl_order` WRITE;
/*!40000 ALTER TABLE `tbl_order` DISABLE KEYS */;
INSERT INTO `tbl_order` VALUES (1,1000,10,10,10,10,10,0,2,1,1,1),(2,NULL,0,NULL,NULL,NULL,NULL,0,2,1,1,1),(3,NULL,0,NULL,NULL,123,123,0,2,1,1,1),(4,NULL,0,NULL,NULL,123,123,0,2,1,1,1),(7,NULL,0,NULL,NULL,123,123,0,2,1,1,1),(8,NULL,0,NULL,NULL,123,123,0,2,1,1,1),(9,NULL,0,NULL,NULL,123,123,0,2,1,1,1),(10,NULL,0,NULL,NULL,123,123,0,2,1,1,1),(11,NULL,0,NULL,NULL,123,123,0,2,1,1,1),(12,NULL,0,NULL,NULL,123,123,0,2,1,1,1),(13,NULL,0,NULL,NULL,123,123,0,2,1,1,1),(14,NULL,0,NULL,NULL,123,123,0,2,1,1,1),(15,NULL,0,NULL,NULL,123,123,0,2,2,1,1),(16,NULL,0,NULL,NULL,123,123,0,2,2,1,1),(17,205,1539016763487,1539057672657,NULL,123,123,0,2,2,1,1),(18,0,1539058248033,1539058333723,NULL,123,123,0,2,2,1,1),(19,0,1539058529562,1539058595305,NULL,123,123,0,2,2,1,1),(20,225,1539016763487,1539061537016,NULL,123,123,0,2,2,1,1),(23,0,1539062094351,1539062369980,NULL,123,123,0,2,2,1,1),(24,0,1539062437046,1539062462513,NULL,123,123,0,2,2,1,1),(25,0,1539063511109,1539063532709,NULL,123,123,0,2,2,1,1),(26,0,1539064679991,1539064739012,NULL,123,123,0,2,2,1,1),(27,NULL,1539227936618,NULL,NULL,123,123,0,1,2,1,1),(28,15,1539230257491,1539234893572,4560000,123,123,0,2,1,1,1),(29,0,1539236128304,1539236145779,1020000,123,123,0,2,1,1,1),(30,NULL,1539353153438,NULL,NULL,123,123,0,2,1,1,1),(31,1,1539353281978,1539353345058,180000,123,123,0,2,1,1,1),(32,8.666666666666668,1539353374616,1539353400620,1560000,123,123,0,2,1,1,1),(33,6,1539399271021,1539399409612,1080000,123,123,0,2,1,1,1),(34,0,1539399448410,1539399569141,0,123,123,0,2,1,1,1),(35,6,1539399621414,1539399641149,1140000,123,123,0,2,1,1,1),(36,13,1539400091347,1539401512089,2400000,123,123,0,2,1,1,1),(37,6,1539401560602,1539401577871,1020000,123,123,0,2,1,1,1),(38,10,1539401637978,1539401726985,1740000,123,123,0,2,1,1,1),(39,NULL,1539401761829,NULL,NULL,123,123,0,2,1,1,1),(40,NULL,1539401986725,NULL,NULL,123,123,0,2,1,1,1),(41,NULL,1539402328919,NULL,NULL,123,123,0,2,1,1,1),(42,8,1539402415723,1539402439578,1380000,123,123,0,2,1,1,1),(43,NULL,1539402526186,NULL,NULL,123,123,0,2,1,1,1),(44,NULL,1539402620577,NULL,NULL,123,123,0,2,1,1,1),(45,NULL,1539402793457,NULL,NULL,123,123,0,2,1,1,1),(46,NULL,1539402967574,NULL,NULL,123,123,0,2,1,1,1),(47,NULL,1539403482151,NULL,NULL,123,123,0,2,1,1,1),(48,NULL,1539404705972,NULL,NULL,123,123,0,2,1,1,1),(49,NULL,1539404848857,NULL,NULL,123,123,0,2,2,1,1),(50,1403,1539405198967,1539657431975,255180000,123,123,1,1,2,1,1),(53,15,1539658083294,1539658147011,180000,1539402328919,1559402328919,1,2,1,1,1),(54,15,1539667968346,1539668214716,360000,1539402328919,1559402328919,1,2,1,1,1),(55,15,1539669919119,1539669980836,60000,1539402328919,1559402328919,1,2,1,1,1);
/*!40000 ALTER TABLE `tbl_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_order_pricing`
--

DROP TABLE IF EXISTS `tbl_order_pricing`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_order_pricing` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `from_hour` int(11) NOT NULL,
  `price_per_hour` double NOT NULL,
  `late_fee_per_hour` int(11) DEFAULT NULL,
  `tbl_order_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1t04nclcvnhje9954ib04bnng` (`tbl_order_id`),
  CONSTRAINT `FK1t04nclcvnhje9954ib04bnng` FOREIGN KEY (`tbl_order_id`) REFERENCES `tbl_order` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_order_pricing`
--

LOCK TABLES `tbl_order_pricing` WRITE;
/*!40000 ALTER TABLE `tbl_order_pricing` DISABLE KEYS */;
INSERT INTO `tbl_order_pricing` VALUES (1,1,10,24,1),(2,3,25,33,1),(3,1,15,30,7),(4,3,20,25,7),(5,1,15,30,8),(6,3,20,25,8),(7,1,15,30,9),(8,3,20,25,9),(9,1,15,30,10),(10,3,20,25,10),(11,1,15,30,12),(12,3,20,25,12),(13,1,15,30,13),(14,3,20,25,13),(15,1,15,30,14),(16,3,20,25,14),(17,1,15,30,15),(18,3,20,25,15),(19,1,15,30,16),(20,3,20,25,16),(21,0,15,30,20),(22,3,20,25,20),(23,0,15,30,23),(24,3,20,25,23),(25,0,15,30,24),(26,3,20,25,24),(27,0,15,30,25),(28,3,20,25,25),(29,0,15,30,26),(30,3,20,25,26),(31,0,15,30,27),(32,3,20,25,27),(33,0,15,30,28),(34,3,20,25,28),(35,0,15,30,29),(36,3,20,25,29),(37,0,15,30,30),(38,3,20,25,30),(39,0,15,30,31),(40,3,20,25,31),(41,0,15,30,32),(42,3,20,25,32),(43,0,15,30,33),(44,3,20,25,33),(45,0,15,30,34),(46,3,20,25,34),(47,0,15,30,35),(48,3,20,25,35),(49,0,15,30,36),(50,3,20,25,36),(51,0,15,30,37),(52,3,20,25,37),(53,0,15,30,38),(54,3,20,25,38),(55,0,15,30,39),(56,3,20,25,39),(57,0,15,30,40),(58,3,20,25,40),(59,0,15,30,41),(60,3,20,25,41),(61,0,15,30,42),(62,3,20,25,42),(63,0,15,30,43),(64,3,20,25,43),(65,0,15,30,44),(66,3,20,25,44),(67,0,15,30,45),(68,3,20,25,45),(69,0,15,30,46),(70,3,20,25,46),(71,0,15,30,47),(72,3,20,25,47),(73,0,15,30,48),(74,3,20,25,48),(75,0,15,30,49),(76,3,20,25,49),(77,0,15,30,50),(78,3,20,25,50),(79,0,15,30,53),(80,3,20,25,53),(81,0,15,30,54),(82,3,20,25,54),(83,0,15,30,55),(84,3,20,25,55);
/*!40000 ALTER TABLE `tbl_order_pricing` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_order_status`
--

DROP TABLE IF EXISTS `tbl_order_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_order_status` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_order_status`
--

LOCK TABLES `tbl_order_status` WRITE;
/*!40000 ALTER TABLE `tbl_order_status` DISABLE KEYS */;
INSERT INTO `tbl_order_status` VALUES (1,'open',NULL),(2,'close',NULL);
/*!40000 ALTER TABLE `tbl_order_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_policy`
--

DROP TABLE IF EXISTS `tbl_policy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_policy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `allowed_parking_from` bigint(20) NOT NULL,
  `allowed_parking_to` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_policy`
--

LOCK TABLES `tbl_policy` WRITE;
/*!40000 ALTER TABLE `tbl_policy` DISABLE KEYS */;
INSERT INTO `tbl_policy` VALUES (1,1539402328919,1559402328919);
/*!40000 ALTER TABLE `tbl_policy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_policy_has_tbl_vehicle_type`
--

DROP TABLE IF EXISTS `tbl_policy_has_tbl_vehicle_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_policy_has_tbl_vehicle_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tbl_policy_id` int(11) NOT NULL,
  `tbl_vehicle_type_id` int(11) NOT NULL,
  `min_hour` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_tbl_policy_has_tbl_vehicle_type_tbl_policy1_idx` (`tbl_policy_id`),
  KEY `fk_tbl_policy_has_tbl_vehicle_type_tbl_vehicle_type1_idx` (`tbl_vehicle_type_id`),
  CONSTRAINT `fk_tbl_policy_has_tbl_vehicle_type_tbl_policy1` FOREIGN KEY (`tbl_policy_id`) REFERENCES `tbl_policy` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tbl_policy_has_tbl_vehicle_type_tbl_vehicle_type1` FOREIGN KEY (`tbl_vehicle_type_id`) REFERENCES `tbl_vehicle_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_policy_has_tbl_vehicle_type`
--

LOCK TABLES `tbl_policy_has_tbl_vehicle_type` WRITE;
/*!40000 ALTER TABLE `tbl_policy_has_tbl_vehicle_type` DISABLE KEYS */;
INSERT INTO `tbl_policy_has_tbl_vehicle_type` VALUES (1,1,1,1);
/*!40000 ALTER TABLE `tbl_policy_has_tbl_vehicle_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_pricing`
--

DROP TABLE IF EXISTS `tbl_pricing`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_pricing` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `from_hour` int(11) NOT NULL,
  `price_per_hour` double NOT NULL,
  `late_fee_per_hour` int(11) DEFAULT NULL,
  `tbl_policy_has_tbl_vehicle_type_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tbl_pricing_tbl_policy_has_tbl_vehicle_type1_idx` (`tbl_policy_has_tbl_vehicle_type_id`),
  CONSTRAINT `fk_tbl_pricing_tbl_policy_has_tbl_vehicle_type1` FOREIGN KEY (`tbl_policy_has_tbl_vehicle_type_id`) REFERENCES `tbl_policy_has_tbl_vehicle_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_pricing`
--

LOCK TABLES `tbl_pricing` WRITE;
/*!40000 ALTER TABLE `tbl_pricing` DISABLE KEYS */;
INSERT INTO `tbl_pricing` VALUES (1,0,15,30,1),(2,3,20,25,1);
/*!40000 ALTER TABLE `tbl_pricing` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_staft`
--

DROP TABLE IF EXISTS `tbl_staft`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_staft` (
  `username` varchar(25) NOT NULL,
  `password` varchar(45) DEFAULT NULL,
  `is_deactivated` bit(1) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_staft`
--

LOCK TABLES `tbl_staft` WRITE;
/*!40000 ALTER TABLE `tbl_staft` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_staft` ENABLE KEYS */;
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
  `money` double NOT NULL,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `vehicle_number` varchar(10) NOT NULL,
  `license_plate_id` varchar(10) NOT NULL,
  `sms_noti` bit(1) DEFAULT b'0',
  `is_activated` bit(1) DEFAULT b'0',
  `tbl_vehicle_type_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `phone_number_UNIQUE` (`phone_number`),
  KEY `FKk1daya3o19oojbshkdwfslewl` (`tbl_vehicle_type_id`),
  CONSTRAINT `FKk1daya3o19oojbshkdwfslewl` FOREIGN KEY (`tbl_vehicle_type_id`) REFERENCES `tbl_vehicle_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_user`
--

LOCK TABLES `tbl_user` WRITE;
/*!40000 ALTER TABLE `tbl_user` DISABLE KEYS */;
INSERT INTO `tbl_user` VALUES (1,'+84899168485','123',77000,'cuong','mai','85A-43788','3245687','',NULL,1),(2,'1324658','123',1500,'bao','quoc','21A-11421','3214959',NULL,NULL,1),(3,'123456000','123',0,'Trung','Ne','123ABC-A','123213',NULL,NULL,1);
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_vehicle_type`
--

LOCK TABLES `tbl_vehicle_type` WRITE;
/*!40000 ALTER TABLE `tbl_vehicle_type` DISABLE KEYS */;
INSERT INTO `tbl_vehicle_type` VALUES (1,'4 chỗ');
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

-- Dump completed on 2018-10-16 20:39:55
