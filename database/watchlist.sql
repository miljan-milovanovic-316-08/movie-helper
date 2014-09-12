-- phpMyAdmin SQL Dump
-- version 3.4.5
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Sep 15, 2013 at 09:44 AM
-- Server version: 5.5.16
-- PHP Version: 5.3.8

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `watchlist`
--
CREATE DATABASE `watchlist` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `watchlist`;

-- --------------------------------------------------------

--
-- Table structure for table `flick`
--

CREATE TABLE IF NOT EXISTS `flick` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `Name` varchar(50) COLLATE latin1_bin NOT NULL,
  `Producer` varchar(50) COLLATE latin1_bin DEFAULT NULL,
  `SERIES` int(10) DEFAULT NULL,
  `DURATION` int(10) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 COLLATE=latin1_bin AUTO_INCREMENT=14 ;

--
-- Dumping data for table `flick`
--

INSERT INTO `flick` (`ID`, `Name`, `Producer`, `SERIES`, `DURATION`) VALUES
(2, 'Orphanage', 'Guilermo Del Toro', 1, 110);

-- --------------------------------------------------------

--
-- Table structure for table `series`
--

CREATE TABLE IF NOT EXISTS `series` (
  `SERIES_ID` int(5) NOT NULL AUTO_INCREMENT,
  `SERIES_name` varchar(150) DEFAULT NULL,
  `SERIES_Production` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`Series_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `series`
--

INSERT INTO `series` (`SERIES_ID`, `SERIES_NAME`, `SERIES_Production`) VALUES
(1, 'Alien', '20 Century Fox'),
(2, 'Scream', 'Miramax Production');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
