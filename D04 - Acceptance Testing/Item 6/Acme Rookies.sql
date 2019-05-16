start transaction;

drop database if exists `acme-rookie`;

create database `acme-rookie`;

use `acme-rookie`;

create user 'acme-user'@'%' identified by password'*4F10007AADA9EE3DBB2CC36575DFC6F4FDE27577';

create user 'acme-manager'@'%' identified by password'*FDB8CD304EB2317D10C95D797A4BD7492560F55F';

grant select, insert, update, delete on `acme-handy-worker`.* to'acme-user'@'%';

grant select, insert, update, delete, create, drop, references,index, alter, create temporary tables, lock tables, create view, create routine, alter routine, execute, trigger, show view on `acme-handy-worker`.* to 'acme-manager'@'%';

-- MySQL dump 10.13  Distrib 5.5.29, for Win64 (x86)
--
-- Host: localhost    Database: Acme-Rookie
-- ------------------------------------------------------
-- Server version	5.5.29

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
-- Table structure for table `actor`
--

DROP TABLE IF EXISTS `actor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `actor` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `vat` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `cvv` int(11) DEFAULT NULL,
  `expiration_month` int(11) DEFAULT NULL,
  `expiration_year` int(11) DEFAULT NULL,
  `holder` varchar(255) DEFAULT NULL,
  `make` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `user_account` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_i7xei45auwq1f6vu25985riuh` (`user_account`),
  CONSTRAINT `FK_i7xei45auwq1f6vu25985riuh` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actor`
--

LOCK TABLES `actor` WRITE;
/*!40000 ALTER TABLE `actor` DISABLE KEYS */;
/*!40000 ALTER TABLE `actor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `administrator`
--

DROP TABLE IF EXISTS `administrator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `administrator` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `vat` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `cvv` int(11) DEFAULT NULL,
  `expiration_month` int(11) DEFAULT NULL,
  `expiration_year` int(11) DEFAULT NULL,
  `holder` varchar(255) DEFAULT NULL,
  `make` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `user_account` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_7ohwsa2usmvu0yxb44je2lge` (`user_account`),
  CONSTRAINT `FK_7ohwsa2usmvu0yxb44je2lge` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrator`
--

LOCK TABLES `administrator` WRITE;
/*!40000 ALTER TABLE `administrator` DISABLE KEYS */;
INSERT INTO `administrator` VALUES (4,0,'ES12345678','C/ Atlántica sex, 1 (Málaga)',123,7,22,'José Gamiz Redondo','VISA','1111222233334444','josgamred@mail.com','José','+34611987654','https://ih1.redbubble.net/image.376305132.3400/flat,550x550,075,f.u1.jpg','Gamiz Redondo',5);
/*!40000 ALTER TABLE `administrator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `application`
--

DROP TABLE IF EXISTS `application`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `application` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `application_moment` datetime DEFAULT NULL,
  `explanation` varchar(255) DEFAULT NULL,
  `link_code` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `submit_moment` datetime DEFAULT NULL,
  `copy_curricula` int(11) DEFAULT NULL,
  `position` int(11) NOT NULL,
  `problem` int(11) DEFAULT NULL,
  `rookie` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_n70iqhr3lgodxu46mt9mberko` (`copy_curricula`),
  KEY `FK_cqpb54jon3yjef814oj6g6o4n` (`position`),
  KEY `FK_7gn6fojv7rim6rxyglc6n9mt2` (`problem`),
  KEY `FK_dq1om37bx4hgli24rbkjo2n7` (`rookie`),
  CONSTRAINT `FK_dq1om37bx4hgli24rbkjo2n7` FOREIGN KEY (`rookie`) REFERENCES `rookie` (`id`),
  CONSTRAINT `FK_7gn6fojv7rim6rxyglc6n9mt2` FOREIGN KEY (`problem`) REFERENCES `problem` (`id`),
  CONSTRAINT `FK_cqpb54jon3yjef814oj6g6o4n` FOREIGN KEY (`position`) REFERENCES `position` (`id`),
  CONSTRAINT `FK_n70iqhr3lgodxu46mt9mberko` FOREIGN KEY (`copy_curricula`) REFERENCES `curricula` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `application`
--

LOCK TABLES `application` WRITE;
/*!40000 ALTER TABLE `application` DISABLE KEYS */;
/*!40000 ALTER TABLE `application` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `audit`
--

DROP TABLE IF EXISTS `audit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `audit` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `is_draft` bit(1) DEFAULT NULL,
  `moment` datetime DEFAULT NULL,
  `score` int(11) DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `auditor` int(11) DEFAULT NULL,
  `position` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_3m6p53wfvy7kcl4f4fvphkh9h` (`auditor`),
  KEY `FK_bumsxo4hc038y25pbfsinc38u` (`position`),
  CONSTRAINT `FK_bumsxo4hc038y25pbfsinc38u` FOREIGN KEY (`position`) REFERENCES `position` (`id`),
  CONSTRAINT `FK_3m6p53wfvy7kcl4f4fvphkh9h` FOREIGN KEY (`auditor`) REFERENCES `auditor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `audit`
--

LOCK TABLES `audit` WRITE;
/*!40000 ALTER TABLE `audit` DISABLE KEYS */;
/*!40000 ALTER TABLE `audit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auditor`
--

DROP TABLE IF EXISTS `auditor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `auditor` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `vat` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `cvv` int(11) DEFAULT NULL,
  `expiration_month` int(11) DEFAULT NULL,
  `expiration_year` int(11) DEFAULT NULL,
  `holder` varchar(255) DEFAULT NULL,
  `make` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `user_account` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_1hfceldjralkadedlv9lg1tl8` (`user_account`),
  CONSTRAINT `FK_1hfceldjralkadedlv9lg1tl8` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auditor`
--

LOCK TABLES `auditor` WRITE;
/*!40000 ALTER TABLE `auditor` DISABLE KEYS */;
/*!40000 ALTER TABLE `auditor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `company`
--

DROP TABLE IF EXISTS `company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `company` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `vat` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `cvv` int(11) DEFAULT NULL,
  `expiration_month` int(11) DEFAULT NULL,
  `expiration_year` int(11) DEFAULT NULL,
  `holder` varchar(255) DEFAULT NULL,
  `make` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `user_account` int(11) NOT NULL,
  `commercial_name` varchar(255) DEFAULT NULL,
  `score` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_pno7oguspp7fxv0y2twgplt0s` (`user_account`),
  CONSTRAINT `FK_pno7oguspp7fxv0y2twgplt0s` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company`
--

LOCK TABLES `company` WRITE;
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
/*!40000 ALTER TABLE `company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `curricula`
--

DROP TABLE IF EXISTS `curricula`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `curricula` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `is_copy` bit(1) DEFAULT NULL,
  `personal_data` int(11) NOT NULL,
  `rookie` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_67ossaqcrfgcegesi91m20iik` (`personal_data`),
  KEY `FK_lq4kfcvf5vufwsng4apko2wd` (`rookie`),
  CONSTRAINT `FK_lq4kfcvf5vufwsng4apko2wd` FOREIGN KEY (`rookie`) REFERENCES `rookie` (`id`),
  CONSTRAINT `FK_67ossaqcrfgcegesi91m20iik` FOREIGN KEY (`personal_data`) REFERENCES `personal_data` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curricula`
--

LOCK TABLES `curricula` WRITE;
/*!40000 ALTER TABLE `curricula` DISABLE KEYS */;
/*!40000 ALTER TABLE `curricula` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `curricula_education_data`
--

DROP TABLE IF EXISTS `curricula_education_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `curricula_education_data` (
  `curricula` int(11) NOT NULL,
  `education_data` int(11) NOT NULL,
  UNIQUE KEY `UK_r552kg3pwybsy7igk77depn9l` (`education_data`),
  KEY `FK_a133bnrmd36opa9yi2dvx0rly` (`curricula`),
  CONSTRAINT `FK_a133bnrmd36opa9yi2dvx0rly` FOREIGN KEY (`curricula`) REFERENCES `curricula` (`id`),
  CONSTRAINT `FK_r552kg3pwybsy7igk77depn9l` FOREIGN KEY (`education_data`) REFERENCES `education_data` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curricula_education_data`
--

LOCK TABLES `curricula_education_data` WRITE;
/*!40000 ALTER TABLE `curricula_education_data` DISABLE KEYS */;
/*!40000 ALTER TABLE `curricula_education_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `curricula_miscellaneous_data`
--

DROP TABLE IF EXISTS `curricula_miscellaneous_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `curricula_miscellaneous_data` (
  `curricula` int(11) NOT NULL,
  `miscellaneous_data` int(11) NOT NULL,
  UNIQUE KEY `UK_12gfi193l4ocrk6r69ckn9ee5` (`miscellaneous_data`),
  KEY `FK_dfsfu2upc4v3tudsfcpnu9whf` (`curricula`),
  CONSTRAINT `FK_dfsfu2upc4v3tudsfcpnu9whf` FOREIGN KEY (`curricula`) REFERENCES `curricula` (`id`),
  CONSTRAINT `FK_12gfi193l4ocrk6r69ckn9ee5` FOREIGN KEY (`miscellaneous_data`) REFERENCES `miscellaneous_data` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curricula_miscellaneous_data`
--

LOCK TABLES `curricula_miscellaneous_data` WRITE;
/*!40000 ALTER TABLE `curricula_miscellaneous_data` DISABLE KEYS */;
/*!40000 ALTER TABLE `curricula_miscellaneous_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `curricula_position_data`
--

DROP TABLE IF EXISTS `curricula_position_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `curricula_position_data` (
  `curricula` int(11) NOT NULL,
  `position_data` int(11) NOT NULL,
  UNIQUE KEY `UK_drj10dtgcblo3nirh2se0c0su` (`position_data`),
  KEY `FK_mns5pfwnlroqw4udu760ye28v` (`curricula`),
  CONSTRAINT `FK_mns5pfwnlroqw4udu760ye28v` FOREIGN KEY (`curricula`) REFERENCES `curricula` (`id`),
  CONSTRAINT `FK_drj10dtgcblo3nirh2se0c0su` FOREIGN KEY (`position_data`) REFERENCES `position_data` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curricula_position_data`
--

LOCK TABLES `curricula_position_data` WRITE;
/*!40000 ALTER TABLE `curricula_position_data` DISABLE KEYS */;
/*!40000 ALTER TABLE `curricula_position_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `education_data`
--

DROP TABLE IF EXISTS `education_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `education_data` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `degree` varchar(255) DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `institution` varchar(255) DEFAULT NULL,
  `mark` double DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `education_data`
--

LOCK TABLES `education_data` WRITE;
/*!40000 ALTER TABLE `education_data` DISABLE KEYS */;
/*!40000 ALTER TABLE `education_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `finder`
--

DROP TABLE IF EXISTS `finder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `finder` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `deadline` datetime DEFAULT NULL,
  `key_word` varchar(255) DEFAULT NULL,
  `maximum_deadline` datetime DEFAULT NULL,
  `minimum_salary` double DEFAULT NULL,
  `search_moment` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `finder`
--

LOCK TABLES `finder` WRITE;
/*!40000 ALTER TABLE `finder` DISABLE KEYS */;
/*!40000 ALTER TABLE `finder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `finder_results`
--

DROP TABLE IF EXISTS `finder_results`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `finder_results` (
  `finder` int(11) NOT NULL,
  `results` int(11) NOT NULL,
  KEY `FK_a376m4iqwc9bvh4fqwqbt7f0i` (`results`),
  KEY `FK_bo0nlx958mxty3aio68pb9bsq` (`finder`),
  CONSTRAINT `FK_bo0nlx958mxty3aio68pb9bsq` FOREIGN KEY (`finder`) REFERENCES `finder` (`id`),
  CONSTRAINT `FK_a376m4iqwc9bvh4fqwqbt7f0i` FOREIGN KEY (`results`) REFERENCES `position` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `finder_results`
--

LOCK TABLES `finder_results` WRITE;
/*!40000 ALTER TABLE `finder_results` DISABLE KEYS */;
/*!40000 ALTER TABLE `finder_results` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequences`
--

DROP TABLE IF EXISTS `hibernate_sequences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequences` (
  `sequence_name` varchar(255) DEFAULT NULL,
  `sequence_next_hi_value` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequences`
--

LOCK TABLES `hibernate_sequences` WRITE;
/*!40000 ALTER TABLE `hibernate_sequences` DISABLE KEYS */;
INSERT INTO `hibernate_sequences` VALUES ('domain_entity',1);
/*!40000 ALTER TABLE `hibernate_sequences` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item`
--

DROP TABLE IF EXISTS `item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `links` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `pictures` varchar(255) DEFAULT NULL,
  `provider` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_isojc9iaj7goju6s26847atbn` (`provider`),
  CONSTRAINT `FK_isojc9iaj7goju6s26847atbn` FOREIGN KEY (`provider`) REFERENCES `provider` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item`
--

LOCK TABLES `item` WRITE;
/*!40000 ALTER TABLE `item` DISABLE KEYS */;
/*!40000 ALTER TABLE `item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `miscellaneous_data`
--

DROP TABLE IF EXISTS `miscellaneous_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `miscellaneous_data` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `attachements` varchar(255) DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `miscellaneous_data`
--

LOCK TABLES `miscellaneous_data` WRITE;
/*!40000 ALTER TABLE `miscellaneous_data` DISABLE KEYS */;
/*!40000 ALTER TABLE `miscellaneous_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `personal_data`
--

DROP TABLE IF EXISTS `personal_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `personal_data` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `github_profile` varchar(255) DEFAULT NULL,
  `linked_in` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `statement` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `personal_data`
--

LOCK TABLES `personal_data` WRITE;
/*!40000 ALTER TABLE `personal_data` DISABLE KEYS */;
/*!40000 ALTER TABLE `personal_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `position`
--

DROP TABLE IF EXISTS `position`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `position` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `deadline` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `is_cancelled` bit(1) DEFAULT NULL,
  `is_draft` bit(1) DEFAULT NULL,
  `profile_required` varchar(255) DEFAULT NULL,
  `salary` double DEFAULT NULL,
  `skills_required` varchar(255) DEFAULT NULL,
  `technologies_required` varchar(255) DEFAULT NULL,
  `ticker` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `company` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_7qlfn4nye234rrm4w83nms1g8` (`company`),
  CONSTRAINT `FK_7qlfn4nye234rrm4w83nms1g8` FOREIGN KEY (`company`) REFERENCES `company` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `position`
--

LOCK TABLES `position` WRITE;
/*!40000 ALTER TABLE `position` DISABLE KEYS */;
/*!40000 ALTER TABLE `position` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `position_data`
--

DROP TABLE IF EXISTS `position_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `position_data` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `position_data`
--

LOCK TABLES `position_data` WRITE;
/*!40000 ALTER TABLE `position_data` DISABLE KEYS */;
/*!40000 ALTER TABLE `position_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `position_problems`
--

DROP TABLE IF EXISTS `position_problems`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `position_problems` (
  `position` int(11) NOT NULL,
  `problems` int(11) NOT NULL,
  KEY `FK_7pe330ganri24wsftsajlm4l9` (`problems`),
  KEY `FK_iji6l3ytrjgytbgo6oi061elj` (`position`),
  CONSTRAINT `FK_iji6l3ytrjgytbgo6oi061elj` FOREIGN KEY (`position`) REFERENCES `position` (`id`),
  CONSTRAINT `FK_7pe330ganri24wsftsajlm4l9` FOREIGN KEY (`problems`) REFERENCES `problem` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `position_problems`
--

LOCK TABLES `position_problems` WRITE;
/*!40000 ALTER TABLE `position_problems` DISABLE KEYS */;
/*!40000 ALTER TABLE `position_problems` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `position_sponsorships`
--

DROP TABLE IF EXISTS `position_sponsorships`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `position_sponsorships` (
  `position` int(11) NOT NULL,
  `sponsorships` int(11) NOT NULL,
  UNIQUE KEY `UK_fmvof6u1m1xgturga7op19xpc` (`sponsorships`),
  KEY `FK_i6jvissx3c6drcnkqok4blj8f` (`position`),
  CONSTRAINT `FK_i6jvissx3c6drcnkqok4blj8f` FOREIGN KEY (`position`) REFERENCES `position` (`id`),
  CONSTRAINT `FK_fmvof6u1m1xgturga7op19xpc` FOREIGN KEY (`sponsorships`) REFERENCES `sponsorship` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `position_sponsorships`
--

LOCK TABLES `position_sponsorships` WRITE;
/*!40000 ALTER TABLE `position_sponsorships` DISABLE KEYS */;
/*!40000 ALTER TABLE `position_sponsorships` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `problem`
--

DROP TABLE IF EXISTS `problem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `problem` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `attachments` varchar(255) DEFAULT NULL,
  `is_draft` bit(1) DEFAULT NULL,
  `optional_hint` varchar(255) DEFAULT NULL,
  `statement` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `company` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_1dla8eoii1nn6emoo4yv86pgb` (`company`),
  CONSTRAINT `FK_1dla8eoii1nn6emoo4yv86pgb` FOREIGN KEY (`company`) REFERENCES `company` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `problem`
--

LOCK TABLES `problem` WRITE;
/*!40000 ALTER TABLE `problem` DISABLE KEYS */;
/*!40000 ALTER TABLE `problem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `provider`
--

DROP TABLE IF EXISTS `provider`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `provider` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `vat` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `cvv` int(11) DEFAULT NULL,
  `expiration_month` int(11) DEFAULT NULL,
  `expiration_year` int(11) DEFAULT NULL,
  `holder` varchar(255) DEFAULT NULL,
  `make` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `user_account` int(11) NOT NULL,
  `provider_make` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_pj40m1p8m3lcs2fkdl1n3f3lq` (`user_account`),
  CONSTRAINT `FK_pj40m1p8m3lcs2fkdl1n3f3lq` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `provider`
--

LOCK TABLES `provider` WRITE;
/*!40000 ALTER TABLE `provider` DISABLE KEYS */;
/*!40000 ALTER TABLE `provider` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rookie`
--

DROP TABLE IF EXISTS `rookie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rookie` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `vat` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `cvv` int(11) DEFAULT NULL,
  `expiration_month` int(11) DEFAULT NULL,
  `expiration_year` int(11) DEFAULT NULL,
  `holder` varchar(255) DEFAULT NULL,
  `make` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `user_account` int(11) NOT NULL,
  `finder` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_2n8nv9qsl5pnxhnosngfkkm4i` (`user_account`),
  KEY `FK_n7rveqfq9lm0cwcxhwyvtyi1g` (`finder`),
  CONSTRAINT `FK_2n8nv9qsl5pnxhnosngfkkm4i` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`),
  CONSTRAINT `FK_n7rveqfq9lm0cwcxhwyvtyi1g` FOREIGN KEY (`finder`) REFERENCES `finder` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rookie`
--

LOCK TABLES `rookie` WRITE;
/*!40000 ALTER TABLE `rookie` DISABLE KEYS */;
/*!40000 ALTER TABLE `rookie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sponsorship`
--

DROP TABLE IF EXISTS `sponsorship`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sponsorship` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `banner` varchar(255) DEFAULT NULL,
  `cvv` int(11) DEFAULT NULL,
  `expiration_month` int(11) DEFAULT NULL,
  `expiration_year` int(11) DEFAULT NULL,
  `holder` varchar(255) DEFAULT NULL,
  `make` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `target` varchar(255) DEFAULT NULL,
  `provider` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_dwk5ymekhnr143u957f7gtns6` (`provider`),
  CONSTRAINT `FK_dwk5ymekhnr143u957f7gtns6` FOREIGN KEY (`provider`) REFERENCES `provider` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sponsorship`
--

LOCK TABLES `sponsorship` WRITE;
/*!40000 ALTER TABLE `sponsorship` DISABLE KEYS */;
/*!40000 ALTER TABLE `sponsorship` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_configuration`
--

DROP TABLE IF EXISTS `system_configuration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system_configuration` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `vattax` double DEFAULT NULL,
  `already_rebranded` bit(1) DEFAULT NULL,
  `banner` varchar(255) DEFAULT NULL,
  `country_code` varchar(255) DEFAULT NULL,
  `flat_rate` double DEFAULT NULL,
  `max_results` int(11) DEFAULT NULL,
  `system_name` varchar(255) DEFAULT NULL,
  `time_results_cached` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_configuration`
--

LOCK TABLES `system_configuration` WRITE;
/*!40000 ALTER TABLE `system_configuration` DISABLE KEYS */;
INSERT INTO `system_configuration` VALUES (6,0,NULL,NULL,'https://i.imgur.com/7b8lu4b.png','+034',NULL,10,'Acme Rookie',1);
/*!40000 ALTER TABLE `system_configuration` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_configuration_breach_notification`
--

DROP TABLE IF EXISTS `system_configuration_breach_notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system_configuration_breach_notification` (
  `system_configuration` int(11) NOT NULL,
  `breach_notification` varchar(255) DEFAULT NULL,
  `breach_notification_key` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`system_configuration`,`breach_notification_key`),
  CONSTRAINT `FK_ayrxtmu4hqnpdg16dr66ftfun` FOREIGN KEY (`system_configuration`) REFERENCES `system_configuration` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_configuration_breach_notification`
--

LOCK TABLES `system_configuration_breach_notification` WRITE;
/*!40000 ALTER TABLE `system_configuration_breach_notification` DISABLE KEYS */;
INSERT INTO `system_configuration_breach_notification` VALUES (6,'','English'),(6,'','Español');
/*!40000 ALTER TABLE `system_configuration_breach_notification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_configuration_welcome_message`
--

DROP TABLE IF EXISTS `system_configuration_welcome_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system_configuration_welcome_message` (
  `system_configuration` int(11) NOT NULL,
  `welcome_message` varchar(255) DEFAULT NULL,
  `welcome_message_key` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`system_configuration`,`welcome_message_key`),
  CONSTRAINT `FK_afo2sg9l0wrwhjs6s7qjxwgjg` FOREIGN KEY (`system_configuration`) REFERENCES `system_configuration` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_configuration_welcome_message`
--

LOCK TABLES `system_configuration_welcome_message` WRITE;
/*!40000 ALTER TABLE `system_configuration_welcome_message` DISABLE KEYS */;
INSERT INTO `system_configuration_welcome_message` VALUES (6,'Welcome to Acme Rookie! We\'re IT rookie’s favourite job marketplace!','English'),(6,'“¡Bien-venidos a Acme Rookie!¡Somos el mercado de trabajo favorito de los profesionales de las TICs!','Español');
/*!40000 ALTER TABLE `system_configuration_welcome_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_account`
--

DROP TABLE IF EXISTS `user_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_account` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_castjbvpeeus0r8lbpehiu0e4` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_account`
--

LOCK TABLES `user_account` WRITE;
/*!40000 ALTER TABLE `user_account` DISABLE KEYS */;
INSERT INTO `user_account` VALUES (5,0,'21232f297a57a5a743894a0e4a801fc3','admin');
/*!40000 ALTER TABLE `user_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_account_authorities`
--

DROP TABLE IF EXISTS `user_account_authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_account_authorities` (
  `user_account` int(11) NOT NULL,
  `authority` varchar(255) DEFAULT NULL,
  KEY `FK_pao8cwh93fpccb0bx6ilq6gsl` (`user_account`),
  CONSTRAINT `FK_pao8cwh93fpccb0bx6ilq6gsl` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_account_authorities`
--

LOCK TABLES `user_account_authorities` WRITE;
/*!40000 ALTER TABLE `user_account_authorities` DISABLE KEYS */;
INSERT INTO `user_account_authorities` VALUES (5,'ADMIN');
/*!40000 ALTER TABLE `user_account_authorities` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-05-16 17:21:57

commit;