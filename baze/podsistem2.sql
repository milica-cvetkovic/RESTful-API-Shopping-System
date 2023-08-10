CREATE DATABASE  IF NOT EXISTS `podsistem2` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `podsistem2`;
-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: localhost    Database: podsistem2
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
  `opis` varchar(45) NOT NULL,
  `cena` float NOT NULL,
  `popust` int NOT NULL,
  `idkategorija` int NOT NULL,
  PRIMARY KEY (`idartikal`),
  UNIQUE KEY `idnew_table_UNIQUE` (`idartikal`),
  KEY `fk_idkategorija_idx` (`idkategorija`),
  CONSTRAINT `fk_idkategorija` FOREIGN KEY (`idkategorija`) REFERENCES `kategorija` (`idkategorija`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `artikal`
--

LOCK TABLES `artikal` WRITE;
/*!40000 ALTER TABLE `artikal` DISABLE KEYS */;
INSERT INTO `artikal` VALUES (17,'Laptop','uredjaj',25000,10,6),(18,'Telefon','uredjaj',10000,0,6),(19,'Bombone','slatko',300,0,8),(20,'Cips','slano',500,5,8);
/*!40000 ALTER TABLE `artikal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kategorija`
--

DROP TABLE IF EXISTS `kategorija`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `kategorija` (
  `idkategorija` int NOT NULL AUTO_INCREMENT,
  `naziv` varchar(45) NOT NULL,
  `idnadkategorija` int DEFAULT NULL,
  PRIMARY KEY (`idkategorija`),
  UNIQUE KEY `idkategorija_UNIQUE` (`idkategorija`),
  KEY `fk_idnadkat_idx` (`idnadkategorija`),
  CONSTRAINT `fk_idnadkat` FOREIGN KEY (`idnadkategorija`) REFERENCES `kategorija` (`idkategorija`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kategorija`
--

LOCK TABLES `kategorija` WRITE;
/*!40000 ALTER TABLE `kategorija` DISABLE KEYS */;
INSERT INTO `kategorija` VALUES (6,'Tehnologija',7),(7,'Oprema',NULL),(8,'Slatkisi',NULL);
/*!40000 ALTER TABLE `kategorija` ENABLE KEYS */;
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
  PRIMARY KEY (`username`),
  UNIQUE KEY `idkorisnik_UNIQUE` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `korisnik`
--

LOCK TABLES `korisnik` WRITE;
/*!40000 ALTER TABLE `korisnik` DISABLE KEYS */;
INSERT INTO `korisnik` VALUES ('lidija','Lidija','Milanovic',13800),('luka','Luka','Cvetkovic',1000),('milica','Milica','Cvetkovic',46075),('milos','Milos','Jovanovic',1000);
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
  `idkorisnik` varchar(45) NOT NULL,
  PRIMARY KEY (`idkorpa`),
  UNIQUE KEY `idkorpa_UNIQUE` (`idkorpa`),
  KEY `fk_idkorisnik_idx` (`idkorisnik`),
  CONSTRAINT `fk_idkorisnik` FOREIGN KEY (`idkorisnik`) REFERENCES `korisnik` (`username`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `korpa`
--

LOCK TABLES `korpa` WRITE;
/*!40000 ALTER TABLE `korpa` DISABLE KEYS */;
INSERT INTO `korpa` VALUES (18,10000,'luka'),(20,32500,'milica'),(21,1500,'lidija');
/*!40000 ALTER TABLE `korpa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prodaje`
--

DROP TABLE IF EXISTS `prodaje`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prodaje` (
  `idartikal` int NOT NULL,
  `idkorisnik` varchar(45) NOT NULL,
  PRIMARY KEY (`idartikal`),
  KEY `fk_idkorisnik2_idx` (`idkorisnik`),
  CONSTRAINT `fk_idartikal2` FOREIGN KEY (`idartikal`) REFERENCES `artikal` (`idartikal`),
  CONSTRAINT `fk_idkorisnik2` FOREIGN KEY (`idkorisnik`) REFERENCES `korisnik` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prodaje`
--

LOCK TABLES `prodaje` WRITE;
/*!40000 ALTER TABLE `prodaje` DISABLE KEYS */;
INSERT INTO `prodaje` VALUES (19,'lidija'),(20,'lidija'),(17,'milica'),(18,'milica');
/*!40000 ALTER TABLE `prodaje` ENABLE KEYS */;
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
  KEY `fk_idartikal_idx` (`idartikal`),
  CONSTRAINT `fk_idartikal` FOREIGN KEY (`idartikal`) REFERENCES `artikal` (`idartikal`) ON UPDATE CASCADE,
  CONSTRAINT `fk_idkorpa` FOREIGN KEY (`idkorpa`) REFERENCES `korpa` (`idkorpa`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sadrzi`
--

LOCK TABLES `sadrzi` WRITE;
/*!40000 ALTER TABLE `sadrzi` DISABLE KEYS */;
INSERT INTO `sadrzi` VALUES (18,18,1),(20,17,1),(20,18,1),(21,19,5);
/*!40000 ALTER TABLE `sadrzi` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-01-30 12:11:43
