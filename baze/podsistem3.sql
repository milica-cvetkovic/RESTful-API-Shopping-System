CREATE DATABASE  IF NOT EXISTS `podsistem3` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `podsistem3`;
-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: localhost    Database: podsistem3
-- ------------------------------------------------------
-- Server version	8.0.32

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
-- Table structure for table `artikal`
--

DROP TABLE IF EXISTS `artikal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `artikal` (
  `idartikal` int NOT NULL AUTO_INCREMENT,
  `naziv` varchar(45) NOT NULL,
  `cena` float NOT NULL,
  `opis` varchar(45) NOT NULL,
  `popust` int NOT NULL,
  PRIMARY KEY (`idartikal`),
  UNIQUE KEY `idartikal_UNIQUE` (`idartikal`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `artikal`
--

LOCK TABLES `artikal` WRITE;
/*!40000 ALTER TABLE `artikal` DISABLE KEYS */;
INSERT INTO `artikal` VALUES (13,'Laptop',25000,'uredjaj',10),(14,'Telefon',10000,'uredjaj',0),(15,'Bombone',300,'slatko',0),(16,'Cips',500,'slano',5);
/*!40000 ALTER TABLE `artikal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grad`
--

DROP TABLE IF EXISTS `grad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `grad` (
  `idgrad` int NOT NULL AUTO_INCREMENT,
  `naziv` varchar(45) NOT NULL,
  `drzava` varchar(45) NOT NULL,
  PRIMARY KEY (`idgrad`),
  UNIQUE KEY `idgrad_UNIQUE` (`idgrad`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grad`
--

LOCK TABLES `grad` WRITE;
/*!40000 ALTER TABLE `grad` DISABLE KEYS */;
INSERT INTO `grad` VALUES (10,'Paracin','Srbija'),(11,'Beograd','Srbija'),(12,'Nis','Srbija'),(13,'Jagodina','Srbija');
/*!40000 ALTER TABLE `grad` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `korisnik`
--

DROP TABLE IF EXISTS `korisnik`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `korisnik` (
  `username` varchar(45) NOT NULL,
  `ime` varchar(45) NOT NULL,
  `prezime` varchar(45) NOT NULL,
  `novac` float NOT NULL,
  `adresa` varchar(45) NOT NULL,
  `idgrad` int NOT NULL,
  PRIMARY KEY (`username`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  KEY `fk_idgrad4_idx` (`idgrad`),
  CONSTRAINT `fk_idgrad4` FOREIGN KEY (`idgrad`) REFERENCES `grad` (`idgrad`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `korisnik`
--

LOCK TABLES `korisnik` WRITE;
/*!40000 ALTER TABLE `korisnik` DISABLE KEYS */;
INSERT INTO `korisnik` VALUES ('lidija','Lidija','Milanovic',13800,'Marije Bursac 35',11),('luka','Luka','Cvetkovic',1000,'Paje Jovanovica 1',12),('milica','Milica','Cvetkovic',46075,'Vojvode Misica 74',10),('milos','Milos','Jovanovic',1000,'Nikole Pasica 5',13);
/*!40000 ALTER TABLE `korisnik` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `korpa`
--

DROP TABLE IF EXISTS `korpa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `korpa` (
  `idkorpa` int NOT NULL AUTO_INCREMENT,
  `ukupnacena` float NOT NULL,
  `idkorisnika` varchar(45) NOT NULL,
  PRIMARY KEY (`idkorpa`),
  KEY `fk_idkorisnika33_idx` (`idkorisnika`),
  CONSTRAINT `fk_idkorisnika33` FOREIGN KEY (`idkorisnika`) REFERENCES `korisnik` (`username`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `korpa`
--

LOCK TABLES `korpa` WRITE;
/*!40000 ALTER TABLE `korpa` DISABLE KEYS */;
INSERT INTO `korpa` VALUES (20,10000,'luka'),(22,32500,'milica'),(23,1500,'lidija');
/*!40000 ALTER TABLE `korpa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `narudzbina`
--

DROP TABLE IF EXISTS `narudzbina`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `narudzbina` (
  `idnarudzbina` int NOT NULL AUTO_INCREMENT,
  `ukupnacena` float NOT NULL,
  `vreme` datetime NOT NULL,
  `idgrad` int NOT NULL,
  `adresa` varchar(45) NOT NULL,
  `idkorisnika` varchar(45) NOT NULL,
  PRIMARY KEY (`idnarudzbina`),
  UNIQUE KEY `idnarudzbina_UNIQUE` (`idnarudzbina`),
  KEY `fk_idgrad_idx` (`idgrad`),
  KEY `fk_idkorisnika4_idx` (`idkorisnika`),
  CONSTRAINT `fk_idgrad` FOREIGN KEY (`idgrad`) REFERENCES `grad` (`idgrad`) ON UPDATE CASCADE,
  CONSTRAINT `fk_idkorisnika4` FOREIGN KEY (`idkorisnika`) REFERENCES `korisnik` (`username`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `narudzbina`
--

LOCK TABLES `narudzbina` WRITE;
/*!40000 ALTER TABLE `narudzbina` DISABLE KEYS */;
INSERT INTO `narudzbina` VALUES (8,23925,'2023-01-29 13:40:30',10,'Vojvode Misica 74','milica'),(9,21200,'2023-01-29 13:41:29',11,'Marije Bursac 35','lidija');
/*!40000 ALTER TABLE `narudzbina` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sadrzi`
--

DROP TABLE IF EXISTS `sadrzi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sadrzi` (
  `idkorpa` int NOT NULL,
  `idartikal` int NOT NULL,
  `kolicina` int NOT NULL,
  PRIMARY KEY (`idkorpa`,`idartikal`),
  KEY `fk_idartikal31_idx` (`idartikal`),
  CONSTRAINT `fk_idartikal31` FOREIGN KEY (`idartikal`) REFERENCES `artikal` (`idartikal`),
  CONSTRAINT `fk_idkorpa31` FOREIGN KEY (`idkorpa`) REFERENCES `korpa` (`idkorpa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sadrzi`
--

LOCK TABLES `sadrzi` WRITE;
/*!40000 ALTER TABLE `sadrzi` DISABLE KEYS */;
INSERT INTO `sadrzi` VALUES (20,14,1),(22,13,1),(22,14,1),(23,15,5);
/*!40000 ALTER TABLE `sadrzi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stavka`
--

DROP TABLE IF EXISTS `stavka`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stavka` (
  `idstavka` int NOT NULL AUTO_INCREMENT,
  `idnarudzbina` int NOT NULL,
  `cena` float NOT NULL,
  `idartikal` int NOT NULL,
  `kolicina` int NOT NULL,
  PRIMARY KEY (`idstavka`),
  UNIQUE KEY `idstavka_UNIQUE` (`idstavka`),
  KEY `fk_idnarudzbina_idx` (`idnarudzbina`),
  KEY `fk_idartikal_1_idx` (`idartikal`),
  CONSTRAINT `fk_idartikal_1` FOREIGN KEY (`idartikal`) REFERENCES `artikal` (`idartikal`) ON UPDATE CASCADE,
  CONSTRAINT `fk_idnarudzbina_1` FOREIGN KEY (`idnarudzbina`) REFERENCES `narudzbina` (`idnarudzbina`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stavka`
--

LOCK TABLES `stavka` WRITE;
/*!40000 ALTER TABLE `stavka` DISABLE KEYS */;
INSERT INTO `stavka` VALUES (9,8,25000,13,1),(10,8,500,16,3),(11,9,10000,14,2),(12,9,300,15,4);
/*!40000 ALTER TABLE `stavka` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transakcija`
--

DROP TABLE IF EXISTS `transakcija`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transakcija` (
  `idtransakcija` int NOT NULL AUTO_INCREMENT,
  `suma` float NOT NULL,
  `vreme` datetime NOT NULL,
  `idnarudzbina` int NOT NULL,
  PRIMARY KEY (`idtransakcija`),
  KEY `fk_idnarudzbina_idx` (`idnarudzbina`),
  CONSTRAINT `fk_idnarudzbina` FOREIGN KEY (`idnarudzbina`) REFERENCES `narudzbina` (`idnarudzbina`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transakcija`
--

LOCK TABLES `transakcija` WRITE;
/*!40000 ALTER TABLE `transakcija` DISABLE KEYS */;
INSERT INTO `transakcija` VALUES (8,23925,'2023-01-29 13:40:30',8),(9,21200,'2023-01-29 13:41:29',9);
/*!40000 ALTER TABLE `transakcija` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-01-30 12:12:13
