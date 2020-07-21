CREATE DATABASE  IF NOT EXISTS `jfinance_new` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `jfinance_new`;
-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: jfinance
-- ------------------------------------------------------
-- Server version	8.0.19

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
-- Table structure for table `balancelist`
--

DROP TABLE IF EXISTS `balancelist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `balancelist` (
                               `balanceId` char(36) NOT NULL,
                               `ownBudget` char(36) NOT NULL,
                               `name` varchar(255) NOT NULL,
                               `description` varchar(1024) DEFAULT NULL,
                               `currentValue` double NOT NULL,
                               `creationDate` date NOT NULL,
                               `isAvailable` bit(1) DEFAULT b'1',
                               PRIMARY KEY (`balanceId`),
                               KEY `ownBudget` (`ownBudget`),
                               CONSTRAINT `balancelist_ibfk_1` FOREIGN KEY (`ownBudget`) REFERENCES `userbudget` (`budgetId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `balancelist`
--

LOCK TABLES `balancelist` WRITE;
/*!40000 ALTER TABLE `balancelist` DISABLE KEYS */;
INSERT INTO `balancelist` VALUES ('1fc3ede7-4ca2-4acd-8d1b-42df080a8986','38f21bc4-89e1-4f61-b468-2bcaec308145','Wallet','My wallet',2180000,'2020-07-19',_binary ''),('5c8c1e03-b995-4507-80f7-f355bf3526b4','a52db9b6-5486-4e40-88d1-3ceda47c9f55','Wallet','My wallet',490000,'2020-07-19',_binary ''),('a3bc59af-2957-4174-9848-e933d458f4fd','a52db9b6-5486-4e40-88d1-3ceda47c9f55','Debit card','My debit card',50000,'2020-07-21',_binary '');
/*!40000 ALTER TABLE `balancelist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `financialgoal`
--

DROP TABLE IF EXISTS `financialgoal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `financialgoal` (
                                 `goalId` char(36) NOT NULL,
                                 `ownBudget` char(36) NOT NULL,
                                 `description` varchar(1023) DEFAULT NULL,
                                 `type` int DEFAULT '1',
                                 `checkBalanceId` char(36) DEFAULT NULL,
                                 `threshold` double NOT NULL,
                                 `startDate` date NOT NULL,
                                 `expireDate` date NOT NULL,
                                 `isActive` bit(1) DEFAULT b'1',
                                 PRIMARY KEY (`goalId`),
                                 KEY `ownBudget` (`ownBudget`),
                                 CONSTRAINT `financialgoal_ibfk_1` FOREIGN KEY (`ownBudget`) REFERENCES `userbudget` (`budgetId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `financialgoal`
--

LOCK TABLES `financialgoal` WRITE;
/*!40000 ALTER TABLE `financialgoal` DISABLE KEYS */;
INSERT INTO `financialgoal` VALUES ('415627ca-3087-4812-9c92-f44cf12cff17','a52db9b6-5486-4e40-88d1-3ceda47c9f55','Monthly allowance',2,NULL,2500000,'2020-07-01','2020-07-31',_binary ''),('899869cf-7700-4e98-9f8f-76efb8c3ad86','a52db9b6-5486-4e40-88d1-3ceda47c9f55','Millionare :v',3,'5c8c1e03-b995-4507-80f7-f355bf3526b4',1000000,'2020-07-01','2020-07-30',_binary ''),('ab605dff-79e6-4d19-8eac-e253d5564262','a52db9b6-5486-4e40-88d1-3ceda47c9f55','Reach minimum income',1,NULL,3000000,'2020-07-01','2020-07-31',_binary '');
/*!40000 ALTER TABLE `financialgoal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loanhistory`
--

DROP TABLE IF EXISTS `loanhistory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `loanhistory` (
                               `loanId` char(36) NOT NULL,
                               `ownBudget` char(36) NOT NULL,
                               `name` varchar(255) NOT NULL,
                               `description` varchar(1024) DEFAULT NULL,
                               `isActive` int DEFAULT '1',
                               `creationDate` date NOT NULL,
                               `activeTimeSpan` int DEFAULT '0',
                               `baseValue` double NOT NULL,
                               `currentValue` double NOT NULL,
                               `interestRate` double DEFAULT '0',
                               `interestInterval` enum('DAILY','WEEKLY','MONTHLY','QUARTERLY','YEARLY') DEFAULT 'MONTHLY',
                               `paymentInterval` enum('DAILY','WEEKLY','MONTHLY','QUARTERLY','YEARLY','ONE_TIME') DEFAULT 'MONTHLY',
                               `lastCheckedDate` date NOT NULL,
                               PRIMARY KEY (`loanId`),
                               KEY `ownBudget` (`ownBudget`),
                               CONSTRAINT `loanhistory_ibfk_1` FOREIGN KEY (`ownBudget`) REFERENCES `userbudget` (`budgetId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loanhistory`
--

LOCK TABLES `loanhistory` WRITE;
/*!40000 ALTER TABLE `loanhistory` DISABLE KEYS */;
INSERT INTO `loanhistory` VALUES ('1ac12a65-72ab-4917-aa69-8e135022d1c1','a52db9b6-5486-4e40-88d1-3ceda47c9f55','Friendly borrow','-',0,'2020-07-21',30,0,0,0,'WEEKLY','MONTHLY','2020-07-21'),('40738a92-1669-441b-8edf-3428c4cd7ed6','a52db9b6-5486-4e40-88d1-3ceda47c9f55','Friend borrow','-',1,'2020-07-21',30,0,100000,0,'MONTHLY','ONE_TIME','2020-07-21'),('ead1c191-feba-40ab-8adc-8faaad3caade','38f21bc4-89e1-4f61-b468-2bcaec308145','Friendly borrow','just borrow some money bruh',1,'2020-07-20',100,0,100000,0,'MONTHLY','YEARLY','2020-07-20');
/*!40000 ALTER TABLE `loanhistory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loggeduser`
--

DROP TABLE IF EXISTS `loggeduser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `loggeduser` (
                              `userId` char(36) NOT NULL,
                              `username` varchar(255) NOT NULL,
                              `password` varchar(255) NOT NULL,
                              `email` varchar(255) DEFAULT NULL,
                              `birthday` date DEFAULT NULL,
                              PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loggeduser`
--

LOCK TABLES `loggeduser` WRITE;
/*!40000 ALTER TABLE `loggeduser` DISABLE KEYS */;
INSERT INTO `loggeduser` VALUES ('03ac8ba4-333f-4e0a-b5fe-00188d996ef2','abcd1234','abcd1234','a@b.c','2004-11-23'),('150fba31-70af-4412-831f-8c8af464b4f4','guest_001','24010909','guess@gmail.com','2000-07-05'),('3b76d236-f2d6-4a78-a8f3-25a987bed11c','Dang241','24010909','abc@gm.vn','2000-01-24'),('5f0c9340-4022-4f85-871b-7a93980482fc','Luxray241','24010909','a@b.c','2004-11-23'),('7e4e1330-9beb-4940-bf26-1c72a72c6e53','neroyuki','24010909','aaaa@aaa.aaa','2001-07-10');
/*!40000 ALTER TABLE `loggeduser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `savinghistory`
--

DROP TABLE IF EXISTS `savinghistory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `savinghistory` (
                                 `savingId` char(36) NOT NULL,
                                 `ownBudget` char(36) NOT NULL,
                                 `name` varchar(255) NOT NULL,
                                 `description` varchar(1024) DEFAULT NULL,
                                 `isActive` int DEFAULT '1',
                                 `creationDate` date NOT NULL,
                                 `activeTimeSpan` int DEFAULT '0',
                                 `baseValue` double NOT NULL,
                                 `currentValue` double NOT NULL,
                                 `interestRate` double DEFAULT '0',
                                 `interestInterval` enum('DAILY','WEEKLY','MONTHLY','QUARTERLY','YEARLY') DEFAULT 'MONTHLY',
                                 `lastCheckedDate` date NOT NULL,
                                 PRIMARY KEY (`savingId`),
                                 KEY `ownBudget` (`ownBudget`),
                                 CONSTRAINT `savinghistory_ibfk_1` FOREIGN KEY (`ownBudget`) REFERENCES `userbudget` (`budgetId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `savinghistory`
--

LOCK TABLES `savinghistory` WRITE;
/*!40000 ALTER TABLE `savinghistory` DISABLE KEYS */;
INSERT INTO `savinghistory` VALUES ('6927e37a-16b5-4762-ac49-0413088aac51','38f21bc4-89e1-4f61-b468-2bcaec308145','Bank Saving','Bank Saving',1,'2020-07-20',180,0,2000000,3,'MONTHLY','2020-07-19'),('9e52a5b9-543e-4810-a4d4-9e67a89437b3','a52db9b6-5486-4e40-88d1-3ceda47c9f55','Small saving','Bank saving',1,'2020-07-21',180,0,500000,3,'MONTHLY','2020-07-21'),('d45ea8df-33f1-43cf-bc3d-b3c27b0c7458','a52db9b6-5486-4e40-88d1-3ceda47c9f55','Bank','Bank saving',0,'2020-06-18',180,0,153000,3,'MONTHLY','2020-07-21');
/*!40000 ALTER TABLE `savinghistory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transcategory`
--

DROP TABLE IF EXISTS `transcategory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transcategory` (
                                 `transCategoryId` char(36) NOT NULL,
                                 `transType` int NOT NULL,
                                 `name` varchar(255) NOT NULL,
                                 `description` varchar(1023) DEFAULT NULL,
                                 `iconPath` varchar(1023) DEFAULT NULL,
                                 `isAvailable` bit(1) DEFAULT b'1',
                                 PRIMARY KEY (`transCategoryId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transcategory`
--

LOCK TABLES `transcategory` WRITE;
/*!40000 ALTER TABLE `transcategory` DISABLE KEYS */;
INSERT INTO `transcategory` VALUES ('34b6ecfc-af81-4150-97d1-c9943e060555',2,'Groceries','Daily groceries','file:/F:/IdeaProj/another_javafx_test/src/main/resources/img/icon/expense/groceries.png',_binary ''),('5295bd4c-e759-4bfd-aeb6-f4164355ef07',1,'Bonus','Bonus money','file:/F:/IdeaProj/another_javafx_test/src/main/resources/img/icon/add-more/check.png',_binary ''),('54b386d6-1754-4f83-a71c-28caed129b53',2,'Ultility','Electricity, water, trash','file:/F:/IdeaProj/another_javafx_test/src/main/resources/img/icon/expense/electricity.png',_binary ''),('64c60ee7-3319-4166-a2dc-720861eb4aa5',1,'Salary','Constant Salary','file:/F:/IdeaProj/another_javafx_test/src/main/resources/img/icon/income/salary.png',_binary ''),('6e4198f4-d1ee-4fc7-b07b-ff55fdf47db2',1,'General','nothing special','file:/F:/IdeaProj/another_javafx_test/src/main/resources/img/self-icon.png',_binary ''),('83dd30b6-5ddd-45ba-915b-0319f014d18d',1,'Investment','Investment money','file:/F:/IdeaProj/another_javafx_test/src/main/resources/img/icon/income/investment.png',_binary ''),('b1004b92-7316-44da-9fb8-c7cb888f086c',2,'Entertainment','Game, manga, film and stuff','file:/F:/IdeaProj/another_javafx_test/src/main/resources/img/icon/expense/entertainment.png',_binary ''),('b3f89fd9-23be-4ee1-8733-ea9ec49a2e1a',2,'General','nothing special','file:/F:/IdeaProj/another_javafx_test/src/main/resources/img/self-icon.png',_binary ''),('cb167baa-666c-4297-b918-1f6062320a20',2,'Food','Restaurant outing','file:/F:/IdeaProj/another_javafx_test/src/main/resources/img/icon/expense/food.png',_binary ''),('fc4e9c2a-1ff8-454e-a598-e0c8f4f6e5f1',1,'Gift','Friendly gift','file:/F:/IdeaProj/another_javafx_test/src/main/resources/img/icon/income/gift.png',_binary '');
/*!40000 ALTER TABLE `transcategory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transhistory`
--

DROP TABLE IF EXISTS `transhistory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transhistory` (
                                `transId` char(36) NOT NULL,
                                `applyBalance` char(36) NOT NULL,
                                `description` varchar(1023) DEFAULT NULL,
                                `value` double NOT NULL,
                                `transType` int NOT NULL,
                                `transCategoryId` char(36) NOT NULL,
                                `occurDate` date NOT NULL,
                                `isAvailable` bit(1) DEFAULT b'1',
                                PRIMARY KEY (`transId`),
                                KEY `applyBalance` (`applyBalance`),
                                KEY `transCategoryId` (`transCategoryId`),
                                CONSTRAINT `transhistory_ibfk_1` FOREIGN KEY (`applyBalance`) REFERENCES `balancelist` (`balanceId`),
                                CONSTRAINT `transhistory_ibfk_2` FOREIGN KEY (`transCategoryId`) REFERENCES `transcategory` (`transCategoryId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transhistory`
--

LOCK TABLES `transhistory` WRITE;
/*!40000 ALTER TABLE `transhistory` DISABLE KEYS */;
INSERT INTO `transhistory` VALUES ('045b971b-d5d5-422b-b0d1-2b7c458e828e','5c8c1e03-b995-4507-80f7-f355bf3526b4','In-game transaction',105000,2,'b1004b92-7316-44da-9fb8-c7cb888f086c','2020-07-19',_binary ''),('0acb18a2-f3ab-421b-b935-ce788ff77bf7','5c8c1e03-b995-4507-80f7-f355bf3526b4','Part-time salary',170000,1,'64c60ee7-3319-4166-a2dc-720861eb4aa5','2020-07-16',_binary ''),('0f0d446d-2c44-463c-9f61-3204d52306ca','5c8c1e03-b995-4507-80f7-f355bf3526b4','Part-time salary',170000,1,'64c60ee7-3319-4166-a2dc-720861eb4aa5','2020-07-21',_binary ''),('1164e09f-0ce8-419a-a838-7c67ed16ea2d','5c8c1e03-b995-4507-80f7-f355bf3526b4','Initial gain from Friend borrow',100000,1,'6e4198f4-d1ee-4fc7-b07b-ff55fdf47db2','2020-07-21',_binary ''),('18060986-f877-4361-9e39-880ce6e06330','5c8c1e03-b995-4507-80f7-f355bf3526b4','Part-time salary',170000,1,'64c60ee7-3319-4166-a2dc-720861eb4aa5','2020-07-15',_binary ''),('185f143e-ccf5-4640-9fb3-50249ae3b3b8','5c8c1e03-b995-4507-80f7-f355bf3526b4','Part-time salary (overtime)',250000,1,'64c60ee7-3319-4166-a2dc-720861eb4aa5','2020-07-06',_binary ''),('1d9de623-25fd-4edf-8e9e-9338a3a204e2','1fc3ede7-4ca2-4acd-8d1b-42df080a8986','Gacha money :v',100000,2,'b3f89fd9-23be-4ee1-8733-ea9ec49a2e1a','2020-07-17',_binary ''),('2717b6a3-7421-4a4d-96e9-dca812ab6e6e','5c8c1e03-b995-4507-80f7-f355bf3526b4','Part-time salary',170000,1,'64c60ee7-3319-4166-a2dc-720861eb4aa5','2020-07-14',_binary ''),('2f5db0a0-d31b-4d43-a5f6-6b0e4266041c','5c8c1e03-b995-4507-80f7-f355bf3526b4','Part-time salary',200000,1,'64c60ee7-3319-4166-a2dc-720861eb4aa5','2020-07-03',_binary ''),('3990c8ca-9c27-4ee5-ad34-a331f10966e4','5c8c1e03-b995-4507-80f7-f355bf3526b4','Part-time salary',200000,1,'64c60ee7-3319-4166-a2dc-720861eb4aa5','2020-07-02',_binary ''),('4a75afe1-9848-4bf1-8b6e-ad9e5441c9c1','5c8c1e03-b995-4507-80f7-f355bf3526b4','Daily Groceries',70000,2,'34b6ecfc-af81-4150-97d1-c9943e060555','2020-07-14',_binary ''),('4f1a90a9-daa5-4218-ab48-4a82b5f32703','5c8c1e03-b995-4507-80f7-f355bf3526b4','Part-time salary',200000,1,'64c60ee7-3319-4166-a2dc-720861eb4aa5','2020-07-08',_binary ''),('54d85f0c-7e1b-4d08-bf12-f52f2484f66f','5c8c1e03-b995-4507-80f7-f355bf3526b4','Part-time salary (overtime)',200000,1,'64c60ee7-3319-4166-a2dc-720861eb4aa5','2020-07-20',_binary ''),('57ca0a6a-9fd8-4b5f-986f-a700e6f82e6f','5c8c1e03-b995-4507-80f7-f355bf3526b4','Daily Groceries',70000,2,'34b6ecfc-af81-4150-97d1-c9943e060555','2020-07-02',_binary ''),('5b881098-a93d-4f91-bf5f-1c09eb145f7d','5c8c1e03-b995-4507-80f7-f355bf3526b4','Daily Groceries',71000,2,'34b6ecfc-af81-4150-97d1-c9943e060555','2020-07-09',_binary ''),('5e42aa73-484e-4f12-bb96-7a5cda8e6f9e','5c8c1e03-b995-4507-80f7-f355bf3526b4','Part-time salary',200000,1,'64c60ee7-3319-4166-a2dc-720861eb4aa5','2020-07-07',_binary ''),('6d652e43-7ea3-402b-b3d6-659d92a532e5','1fc3ede7-4ca2-4acd-8d1b-42df080a8986','Pick up',50000,1,'6e4198f4-d1ee-4fc7-b07b-ff55fdf47db2','2020-07-18',_binary ''),('7037ab63-991b-442c-88b3-04da59f3d28e','5c8c1e03-b995-4507-80f7-f355bf3526b4','Daily Groceries',70000,2,'34b6ecfc-af81-4150-97d1-c9943e060555','2020-07-04',_binary ''),('7298960a-c0c8-458d-950d-ff13b8d4e06a','5c8c1e03-b995-4507-80f7-f355bf3526b4','Daily Groceries (for 2 days)',128000,2,'34b6ecfc-af81-4150-97d1-c9943e060555','2020-07-10',_binary ''),('756c1d87-e17a-4336-b189-f1e95bb61dea','5c8c1e03-b995-4507-80f7-f355bf3526b4','Daily Groceries',70000,2,'34b6ecfc-af81-4150-97d1-c9943e060555','2020-07-18',_binary ''),('76c68d93-025d-4275-9875-15668192dd25','5c8c1e03-b995-4507-80f7-f355bf3526b4','Daily Groceries',70000,2,'34b6ecfc-af81-4150-97d1-c9943e060555','2020-07-08',_binary ''),('79439c26-ab7e-4d84-8c7f-98ac9d877c70','5c8c1e03-b995-4507-80f7-f355bf3526b4','Daily Groceries',65000,2,'34b6ecfc-af81-4150-97d1-c9943e060555','2020-07-03',_binary ''),('8aed278d-fc2d-41a6-ba2c-a17d03585d29','5c8c1e03-b995-4507-80f7-f355bf3526b4','Daily Groceries',70000,2,'34b6ecfc-af81-4150-97d1-c9943e060555','2020-07-07',_binary ''),('8c76b2fe-906c-42e7-89e4-41927c5aeb0e','5c8c1e03-b995-4507-80f7-f355bf3526b4','Part-time salary (overtime)',250000,1,'64c60ee7-3319-4166-a2dc-720861eb4aa5','2020-07-10',_binary ''),('8d551ea6-0620-4108-a7fd-86ceb3185294','1fc3ede7-4ca2-4acd-8d1b-42df080a8986','Initial deposit for Bank Saving',2000000,2,'b3f89fd9-23be-4ee1-8733-ea9ec49a2e1a','2020-07-20',_binary ''),('8e659458-ce03-44e4-81fe-1e35905d00fc','5c8c1e03-b995-4507-80f7-f355bf3526b4','Daily Groceries',72000,2,'34b6ecfc-af81-4150-97d1-c9943e060555','2020-07-05',_binary ''),('8ff6b514-4f18-4311-ab4a-c61a773fdbdc','5c8c1e03-b995-4507-80f7-f355bf3526b4','Part-time salary',170000,1,'64c60ee7-3319-4166-a2dc-720861eb4aa5','2020-07-17',_binary ''),('949cd7bd-bf8a-4b4c-aebd-a4b18f1ee864','5c8c1e03-b995-4507-80f7-f355bf3526b4','Forex margin',100000,1,'83dd30b6-5ddd-45ba-915b-0319f014d18d','2020-07-16',_binary ''),('9719e887-5c25-4fa5-9683-43b8d0abd548','5c8c1e03-b995-4507-80f7-f355bf3526b4','Eating with friends',125000,2,'cb167baa-666c-4297-b918-1f6062320a20','2020-07-13',_binary ''),('9969416e-3160-42e3-a469-17fd223236d1','5c8c1e03-b995-4507-80f7-f355bf3526b4','Daily Groceries',71000,2,'34b6ecfc-af81-4150-97d1-c9943e060555','2020-07-15',_binary ''),('9a2e010a-7480-42b3-8e4c-afc825f4e3d6','5c8c1e03-b995-4507-80f7-f355bf3526b4','Netflix subscription',200000,2,'b1004b92-7316-44da-9fb8-c7cb888f086c','2020-07-07',_binary ''),('a096231d-61ad-41e9-912c-f703ad4baa0c','5c8c1e03-b995-4507-80f7-f355bf3526b4','Daily Groceries',71000,2,'34b6ecfc-af81-4150-97d1-c9943e060555','2020-07-17',_binary ''),('b68afc17-eab4-4197-8c22-7546f8f4344c','5c8c1e03-b995-4507-80f7-f355bf3526b4','Part-time salary (new job)',170000,1,'64c60ee7-3319-4166-a2dc-720861eb4aa5','2020-07-13',_binary ''),('b9093e42-6085-4e4c-8fe7-0ea323105872','1fc3ede7-4ca2-4acd-8d1b-42df080a8986','Initial gain from Friendly borrow',100000,1,'6e4198f4-d1ee-4fc7-b07b-ff55fdf47db2','2020-07-20',_binary ''),('c1d14128-3e85-449a-b3bf-7abb2cdcb584','5c8c1e03-b995-4507-80f7-f355bf3526b4','Part-time salary',200000,1,'64c60ee7-3319-4166-a2dc-720861eb4aa5','2020-07-01',_binary ''),('c3faeeb6-9262-4b0f-938d-6db3176da51f','5c8c1e03-b995-4507-80f7-f355bf3526b4','Daily Groceries',73000,2,'34b6ecfc-af81-4150-97d1-c9943e060555','2020-07-19',_binary ''),('c406330a-2822-439b-84dd-eda767d80b4d','5c8c1e03-b995-4507-80f7-f355bf3526b4','Initial deposit for Small saving',500000,2,'b3f89fd9-23be-4ee1-8733-ea9ec49a2e1a','2020-07-21',_binary ''),('d22d4018-1670-4139-8f3d-702771b5bbf1','5c8c1e03-b995-4507-80f7-f355bf3526b4','Daily Groceries',60000,2,'34b6ecfc-af81-4150-97d1-c9943e060555','2020-07-06',_binary ''),('d2e84bfb-15eb-4a7a-9dea-5bcb53f43b1b','1fc3ede7-4ca2-4acd-8d1b-42df080a8986','Nothing special',30000,1,'6e4198f4-d1ee-4fc7-b07b-ff55fdf47db2','2020-06-03',_binary ''),('e13a57f8-e7db-49d8-a9e5-a8a913873c38','5c8c1e03-b995-4507-80f7-f355bf3526b4','Daily Groceries',70000,2,'34b6ecfc-af81-4150-97d1-c9943e060555','2020-07-16',_binary ''),('e4675841-008c-4158-92a4-1a249d8d375c','5c8c1e03-b995-4507-80f7-f355bf3526b4','Daily Groceries',75000,2,'34b6ecfc-af81-4150-97d1-c9943e060555','2020-07-01',_binary ''),('e79409ca-d4b6-4b17-92c4-5acf16a806a3','5c8c1e03-b995-4507-80f7-f355bf3526b4','Electricity',180000,2,'54b386d6-1754-4f83-a71c-28caed129b53','2020-07-05',_binary ''),('e99b78d5-3810-4bbd-8ca7-c60e7bf50290','1fc3ede7-4ca2-4acd-8d1b-42df080a8986','Scholarship',4000000,1,'6e4198f4-d1ee-4fc7-b07b-ff55fdf47db2','2020-07-19',_binary ''),('ef26a4ee-3796-42bf-9946-a104137ea76c','5c8c1e03-b995-4507-80f7-f355bf3526b4','Daily Groceries',70000,2,'34b6ecfc-af81-4150-97d1-c9943e060555','2020-07-20',_binary ''),('f643fdcf-f9b6-46c2-8561-197c8a3a59dd','5c8c1e03-b995-4507-80f7-f355bf3526b4','Daily Groceries',72000,2,'34b6ecfc-af81-4150-97d1-c9943e060555','2020-07-12',_binary '');
/*!40000 ALTER TABLE `transhistory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userbudget`
--

DROP TABLE IF EXISTS `userbudget`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `userbudget` (
                              `budgetId` char(36) NOT NULL,
                              `ownUser` char(36) NOT NULL,
                              `isAvailable` bit(1) DEFAULT b'1',
                              PRIMARY KEY (`budgetId`),
                              KEY `ownUser` (`ownUser`),
                              CONSTRAINT `userbudget_ibfk_1` FOREIGN KEY (`ownUser`) REFERENCES `loggeduser` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userbudget`
--

LOCK TABLES `userbudget` WRITE;
/*!40000 ALTER TABLE `userbudget` DISABLE KEYS */;
INSERT INTO `userbudget` VALUES ('319f06b5-3a2d-490b-8cc8-140f31680a00','7e4e1330-9beb-4940-bf26-1c72a72c6e53',_binary ''),('38f21bc4-89e1-4f61-b468-2bcaec308145','3b76d236-f2d6-4a78-a8f3-25a987bed11c',_binary ''),('81da88e1-4cee-44f7-b23a-bcbe9a245937','03ac8ba4-333f-4e0a-b5fe-00188d996ef2',_binary ''),('84d1fae5-2b4e-43a8-9a6e-cf57611f5557','5f0c9340-4022-4f85-871b-7a93980482fc',_binary ''),('a52db9b6-5486-4e40-88d1-3ceda47c9f55','150fba31-70af-4412-831f-8c8af464b4f4',_binary '');
/*!40000 ALTER TABLE `userbudget` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-07-21 23:52:51
