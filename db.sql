CREATE DATABASE  IF NOT EXISTS `takmicenje` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `takmicenje`;
-- MySQL dump 10.13  Distrib 8.0.45, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: takmicenje
-- ------------------------------------------------------
-- Server version	8.0.45

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `competition`
--

DROP TABLE IF EXISTS `competition`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `competition` (
  `competition_id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  `restaurants_id` int unsigned NOT NULL,
  PRIMARY KEY (`competition_id`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  KEY `fk_competition_restaurants_id_idx` (`restaurants_id`),
  CONSTRAINT `fk_competition_restaurants_id` FOREIGN KEY (`restaurants_id`) REFERENCES `restaurants` (`restaurants_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `competition`
--

LOCK TABLES `competition` WRITE;
/*!40000 ALTER TABLE `competition` DISABLE KEYS */;
INSERT INTO `competition` VALUES (4,'Global Flavor Showdown','2026-04-21 20:01:02',NULL,NULL,4),(5,'World Kitchen Challenge','2026-04-21 20:01:02',NULL,NULL,4),(6,'Culinary Passport 2026','2026-04-21 20:01:02',NULL,NULL,6);
/*!40000 ALTER TABLE `competition` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `competition_schedules`
--

DROP TABLE IF EXISTS `competition_schedules`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `competition_schedules` (
  `competition_schedules_id` int unsigned NOT NULL AUTO_INCREMENT,
  `time_start` time NOT NULL,
  `recipe_id` int unsigned NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  `restaurants_id` int unsigned NOT NULL,
  PRIMARY KEY (`competition_schedules_id`),
  UNIQUE KEY `uq_restaurants_recipe_id` (`restaurants_id`,`recipe_id`),
  UNIQUE KEY `uq_rest_recipe_unique` (`restaurants_id`,`recipe_id`),
  UNIQUE KEY `uq_rest_time_unique` (`restaurants_id`,`time_start`),
  KEY `fk_competition_schedules_restaurants_id_idx` (`restaurants_id`),
  CONSTRAINT `fk_competition_schedules_restaurants_id` FOREIGN KEY (`restaurants_id`) REFERENCES `restaurants` (`restaurants_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `competition_schedules`
--

LOCK TABLES `competition_schedules` WRITE;
/*!40000 ALTER TABLE `competition_schedules` DISABLE KEYS */;
INSERT INTO `competition_schedules` VALUES (1,'12:00:00',53086,'2026-04-26 20:48:15',NULL,NULL,4),(3,'13:00:00',53086,'2026-04-26 20:58:50',NULL,NULL,6),(4,'14:00:00',53122,'2026-04-26 20:59:39',NULL,NULL,4),(5,'17:00:00',53122,'2026-04-26 20:59:39',NULL,NULL,5);
/*!40000 ALTER TABLE `competition_schedules` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `restaurants`
--

DROP TABLE IF EXISTS `restaurants`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `restaurants` (
  `restaurants_id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `address` varchar(45) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`restaurants_id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `restaurants`
--

LOCK TABLES `restaurants` WRITE;
/*!40000 ALTER TABLE `restaurants` DISABLE KEYS */;
INSERT INTO `restaurants` VALUES (4,'Manufaktura','Kralja Petra 13, Beograd','2026-04-21 19:59:36',NULL,'2026-05-21 14:03:22'),(5,'Tri šešira','Skadarska 29, Beograd','2026-04-21 19:59:36',NULL,'2026-05-21 14:03:10'),(6,'McDonald\'s Bulevar','Булевар краља Александра 62, Београд 11000','2026-04-21 19:59:36',NULL,'2026-05-21 14:03:20');
/*!40000 ALTER TABLE `restaurants` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-05-21 14:19:18
