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
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-01-29 13:53:14
