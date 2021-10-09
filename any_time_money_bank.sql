-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sep 26, 2021 at 08:12 PM
-- Server version: 10.4.21-MariaDB
-- PHP Version: 8.0.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `any_time_money_bank`
--

-- --------------------------------------------------------

--
-- Table structure for table `atm_cards`
--

CREATE TABLE `atm_cards` (
  `id` int(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `mobile` bigint(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `account_number` bigint(255) NOT NULL,
  `atm_number` bigint(255) NOT NULL,
  `atm_pin` int(255) NOT NULL,
  `balance` bigint(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `atm_cards`
--

INSERT INTO `atm_cards` (`id`, `name`, `email`, `mobile`, `address`, `account_number`, `atm_number`, `atm_pin`, `balance`) VALUES
(1, 'Neeraj Suman', 'neerajsuman95@gmail.com', 8079088484, 'D-15, Indra Colony, Vigyan Nagar, Kota', 61198197007, 5596010124219831, 1234, 38892),
(2, 'Abhishek Rishishwar', 'abhishekrishishwar63@gmail.com', 7597060414, 'Talwandi, Kota', 59517531239, 4123698526547569, 3215, 21139);

-- --------------------------------------------------------

--
-- Table structure for table `id1`
--

CREATE TABLE `id1` (
  `id` int(255) NOT NULL,
  `date` varchar(255) NOT NULL,
  `credit` bigint(255) DEFAULT NULL,
  `debit` bigint(255) DEFAULT NULL,
  `balance` bigint(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `id1`
--

INSERT INTO `id1` (`id`, `date`, `credit`, `debit`, `balance`) VALUES
(1, 'Sun Sep 26 17:03:52 IST 2021', NULL, 1234, 34136),
(2, 'Sun Sep 26 17:10:42 IST 2021', NULL, 1000, 33136),
(3, 'Sun Sep 26 17:10:51 IST 2021', NULL, 5000, 28136),
(4, 'Sun Sep 26 17:11:00 IST 2021', NULL, 1500, 26636),
(5, 'Sun Sep 26 17:16:03 IST 2021', NULL, 800, 26336),
(6, 'Sun Sep 26 17:16:15 IST 2021', NULL, 332, 26604),
(7, 'Sun Sep 26 17:16:20 IST 2021', NULL, 4000, 22604),
(8, 'Sun Sep 26 17:17:50 IST 2021', 3000, NULL, 38892);

-- --------------------------------------------------------

--
-- Table structure for table `id2`
--

CREATE TABLE `id2` (
  `id` int(255) NOT NULL,
  `date` varchar(255) NOT NULL,
  `credit` bigint(255) DEFAULT NULL,
  `debit` bigint(255) DEFAULT NULL,
  `balance` bigint(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `id2`
--

INSERT INTO `id2` (`id`, `date`, `credit`, `debit`, `balance`) VALUES
(1, 'Sun Sep 26 17:29:33 IST 2021', 30000, NULL, 31000),
(2, 'Sun Sep 26 17:30:04 IST 2021', NULL, 10000, 21000),
(3, 'Sun Sep 26 17:30:11 IST 2021', NULL, 123, 20877),
(4, 'Sun Sep 26 17:30:11 IST 2021', NULL, 123, 20754),
(5, 'Sun Sep 26 17:30:11 IST 2021', NULL, 123, 20631),
(6, 'Sun Sep 26 17:30:11 IST 2021', NULL, 123, 20508),
(7, 'Sun Sep 26 17:30:12 IST 2021', NULL, 123, 20385),
(8, 'Sun Sep 26 17:30:12 IST 2021', NULL, 123, 20262),
(9, 'Sun Sep 26 17:30:12 IST 2021', NULL, 123, 20139),
(10, 'Sun Sep 26 17:30:18 IST 2021', 1000, NULL, 21139);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `atm_cards`
--
ALTER TABLE `atm_cards`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `id1`
--
ALTER TABLE `id1`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `id2`
--
ALTER TABLE `id2`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `atm_cards`
--
ALTER TABLE `atm_cards`
  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `id1`
--
ALTER TABLE `id1`
  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `id2`
--
ALTER TABLE `id2`
  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
