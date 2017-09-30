-- phpMyAdmin SQL Dump
-- version 4.5.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Sep 30, 2017 at 02:40 AM
-- Server version: 5.7.11
-- PHP Version: 5.6.19

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `dkj`
--

-- --------------------------------------------------------

--
-- Table structure for table `asset`
--

CREATE TABLE `asset` (
  `regNo` char(10) NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  `type` varchar(20) DEFAULT NULL,
  `cost` double DEFAULT NULL,
  `boughtDate` date DEFAULT NULL,
  `lifeTime` int(11) DEFAULT NULL,
  `condition` varchar(10) DEFAULT NULL,
  `depPercent` float DEFAULT NULL,
  `currentDep` double DEFAULT NULL,
  `totalDep` double DEFAULT NULL,
  `currentValue` double DEFAULT NULL,
  `balLifeTime` int(11) DEFAULT NULL,
  `availability` varchar(10) DEFAULT 'available'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `asset`
--

INSERT INTO `asset` (`regNo`, `name`, `type`, `cost`, `boughtDate`, `lifeTime`, `condition`, `depPercent`, `currentDep`, `totalDep`, `currentValue`, `balLifeTime`, `availability`) VALUES
('fd33', 'asdas', 'Heavy', 2342342, '2017-06-05', 23, 'wefd', NULL, NULL, NULL, NULL, NULL, 'available'),
('kt3102', 'montero', 'Light', 50000, '2017-03-05', 10, 'Brandnew', NULL, NULL, NULL, NULL, NULL, 'available'),
('t132', 'sdff', 'Light', 23232, '2017-06-08', 23, 'fwdefc', NULL, NULL, NULL, NULL, NULL, 'available');

-- --------------------------------------------------------

--
-- Table structure for table `assettender`
--

CREATE TABLE `assettender` (
  `tenderId` char(5) NOT NULL,
  `regNo` char(10) NOT NULL,
  `date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

CREATE TABLE `employee` (
  `empId` int(5) NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  `address` varchar(50) DEFAULT NULL,
  `nic` char(10) DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `contactNo` char(10) DEFAULT NULL,
  `position` varchar(20) DEFAULT NULL,
  `empType` varchar(20) DEFAULT NULL,
  `basicSalary` double DEFAULT NULL,
  `availability` varchar(20) NOT NULL DEFAULT 'available'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `employee`
--

INSERT INTO `employee` (`empId`, `name`, `address`, `nic`, `dob`, `gender`, `contactNo`, `position`, `empType`, `basicSalary`, `availability`) VALUES
(1, 'peter', 'colombo', '988557462v', '2017-09-05', 'male', '0336554788', 'supervisor', 'permanent', 23000, 'available');

-- --------------------------------------------------------

--
-- Table structure for table `emptender`
--

CREATE TABLE `emptender` (
  `tenderId` char(5) NOT NULL,
  `empId` int(11) NOT NULL,
  `date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `equipment`
--

CREATE TABLE `equipment` (
  `equipID` int(10) NOT NULL,
  `name` varchar(20) NOT NULL,
  `count` int(20) NOT NULL,
  `cost` double NOT NULL,
  `availability` varchar(10) NOT NULL DEFAULT 'available'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `equiptender`
--

CREATE TABLE `equiptender` (
  `tenderId` char(5) NOT NULL,
  `equipID` int(11) NOT NULL,
  `count` int(11) NOT NULL,
  `assignCount` int(11) NOT NULL DEFAULT '0',
  `date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `jobasset`
--

CREATE TABLE `jobasset` (
  `tenderId` char(5) NOT NULL,
  `assetType` varchar(20) NOT NULL,
  `assetCount` int(11) NOT NULL,
  `assignCount` int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `jobemployee`
--

CREATE TABLE `jobemployee` (
  `tenderId` varchar(5) NOT NULL,
  `noOfEmployee` int(11) NOT NULL,
  `assignCount` int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `materialtender`
--

CREATE TABLE `materialtender` (
  `tenderId` char(5) NOT NULL,
  `materialType` varchar(20) NOT NULL,
  `materialCount` int(11) NOT NULL,
  `assignCount` int(11) NOT NULL DEFAULT '0',
  `date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `rawmaterial`
--

CREATE TABLE `rawmaterial` (
  `type` varchar(20) NOT NULL,
  `price` double NOT NULL,
  `quantity` int(11) NOT NULL,
  `quantity measurement` varchar(20) NOT NULL,
  `supplierId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `rawmaterial`
--

INSERT INTO `rawmaterial` (`type`, `price`, `quantity`, `quantity measurement`, `supplierId`) VALUES
('Asphalt', 1250, 25, 'kilograms', 1),
('bricks', 50, 100, 'units', 2),
('Cement', 650, 15, '25kg cement bags', 3),
('drdt', 656, 656, 'dfe', 4),
('Gravel', 450, 120, 'kilograms', 4),
('sand', 1200, 10, 'kilograms', 4),
('steelbar', 4000, 25, '1M steel bars', 1);

-- --------------------------------------------------------

--
-- Table structure for table `supplier`
--

CREATE TABLE `supplier` (
  `supplierId` int(100) NOT NULL,
  `name` varchar(20) NOT NULL,
  `nic` char(10) NOT NULL,
  `contact` char(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `supplier`
--

INSERT INTO `supplier` (`supplierId`, `name`, `nic`, `contact`) VALUES
(1, 'perera', '544556754v', '0776545378'),
(2, 'ramboindustries', '551818885v', '5484844848'),
(3, 'johnimports', '589658875v', '9595959599'),
(4, 'george', '963354878v', '5458484848'),
(5, 'king', '788825558v', '4848484848'),
(6, 'buddy', '1234567890', '0123456789');

-- --------------------------------------------------------

--
-- Table structure for table `tender`
--

CREATE TABLE `tender` (
  `tenderId` char(5) NOT NULL,
  `tenderName` varchar(25) DEFAULT NULL,
  `grade` varchar(5) DEFAULT NULL,
  `workType` varchar(20) DEFAULT NULL,
  `category` varchar(20) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `workingPlace` varchar(20) DEFAULT NULL,
  `period` int(11) DEFAULT NULL,
  `bidValidity` int(11) DEFAULT NULL,
  `estimatedCost` double DEFAULT NULL,
  `companyName` varchar(20) DEFAULT NULL,
  `cost` double DEFAULT NULL,
  `status` varchar(20) NOT NULL DEFAULT 'pending'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tender`
--

INSERT INTO `tender` (`tenderId`, `tenderName`, `grade`, `workType`, `category`, `date`, `workingPlace`, `period`, `bidValidity`, `estimatedCost`, `companyName`, `cost`, `status`) VALUES
('a0006', 'RDA pavement refill', 'C7', 'Re-fill', 'Asphalt', '2017-08-29', 'Trincomalee', 40, 30, 1123125642, 'RDA Department', NULL, 'Closed'),
('At0a1', 'RDA pavement', 'C6', 'Construct', 'Asphalt', '2017-09-14', 'Batticaloa', 12, 11, 1121211111, 'RDA Department', NULL, 'Pending'),
('At0a2', 'RDA pavement 2', 'C6', 'Construct', 'Asphalt', '2017-09-14', 'Batticaloa', 12, 11, 1231323, 'RDA Department', NULL, 'Pending'),
('T0001', 'blue ocean bat', 'C6', 'Re-fill', 'Concrete', '2017-08-04', 'Trincomalee', 30, 25, 30, 'Blue ocean', NULL, 'Pending'),
('T0002', 'Royal trade trinco', 'C7', 'Cleaning', 'Concrete', '2013-09-04', 'Trincomalee', 32, 22, 12312337123, 'Blue ocean', NULL, 'Pending'),
('T0003', 'Royal line trinco', 'C7', 'Cleaning', 'Concrete', '2013-09-04', 'Trincomalee', 45, 40, 12312414, 'Blue line', NULL, 'Pending'),
('T0004', 'RDA pavement 3', 'C6', 'Construct', 'Asphalt', '2017-08-04', 'Batticaloa', 30, 25, 24214122, 'RDA Department', NULL, 'Pending'),
('T0005', 'RDA pavement 4', 'C6', 'Cleaning', 'Gravel', '2017-08-04', 'Batticaloa', 32, 25, 12311231, 'RDA Department', NULL, 'Pending'),
('T0006', 'RDA pavement refillv', 'C7', 'Re-fill', 'Asphalt', '2017-08-29', 'Trincomalee', 40, 30, 11231, 'RDA Department', NULL, 'Pending'),
('T0007', 'RDA refill highway', 'C6', 'Re-fill', 'Asphalt', '2017-08-04', 'Kandy', 20, 18, 3444667, 'RDA Department', NULL, 'Pending'),
('T0008', 'RDA refill highway 22', 'C7', 'Re-fill', 'Asphalt', '2017-08-04', 'Trincomalee', 20, 18, 343434236, 'RDA Department', NULL, 'On progress'),
('T0009', 'station road refill 2', 'C6', 'Re-fill', 'Gravel', '2015-04-15', 'Batticaloa', 30, 25, 34343116, 'RDA Department', NULL, 'On progress'),
('T0010', 'kovil road ', 'C6', 'Construct', 'Gravel', '2017-08-04', 'Batticaloa', 45, 30, 34343116, 'RDA Department', NULL, 'Pending'),
('T0011', 'Beachside tender', 'C7', 'Construct', 'Gravel', '2017-08-04', 'Trincomalee', 15, 13, 0, 'HK Constructions', NULL, 'Pending'),
('T00a1', 'RDA refill highway 2', 'C7', 'Cleaning', 'Asphalt', '2017-08-30', 'Batticaloa', 20, 18, 34467, 'RDA', NULL, 'Pending');

-- --------------------------------------------------------

--
-- Table structure for table `transport`
--

CREATE TABLE `transport` (
  `tripId` bigint(20) NOT NULL,
  `regNo` char(10) NOT NULL,
  `tenderId` char(5) NOT NULL,
  `destination` varchar(20) NOT NULL,
  `date` date NOT NULL,
  `cost` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `userId` int(11) NOT NULL,
  `userType` varchar(15) NOT NULL,
  `dateAssigned` date NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`userId`, `userType`, `dateAssigned`, `username`, `password`) VALUES
(1, 'supervisor', '2017-09-09', 'a', '1');

-- --------------------------------------------------------

--
-- Table structure for table `utilities`
--

CREATE TABLE `utilities` (
  `billNo` varchar(20) NOT NULL,
  `billType` varchar(25) NOT NULL,
  `paidAmount` double NOT NULL,
  `billDate` date NOT NULL,
  `paidDate` date NOT NULL,
  `paymentMethod` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `utilities`
--

INSERT INTO `utilities` (`billNo`, `billType`, `paidAmount`, `billDate`, `paidDate`, `paymentMethod`) VALUES
('D4565', 'Internet', 4565, '2017-09-19', '2017-09-21', 'Card'),
('D544', 'Phone', 2544.56, '2017-09-13', '2017-09-14', 'Card'),
('D545', 'Phone', 2544.56, '2017-09-13', '2017-09-14', 'Card'),
('EB001', 'Electricity', 450, '2017-09-01', '2017-09-02', 'Cash'),
('EB002', 'Electricity', 944.44, '2017-09-03', '2017-09-12', 'Cash'),
('IA8987', 'Internet', 3200, '2017-09-19', '2017-09-21', 'Card'),
('P5645', 'Phone', 840, '2017-09-12', '2017-09-14', 'Card'),
('WB002', 'Water', 312.2, '2017-09-05', '2017-09-12', 'Cash'),
('WB005', 'Water', 1255.44, '2017-09-06', '2017-09-13', 'Cash');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `asset`
--
ALTER TABLE `asset`
  ADD PRIMARY KEY (`regNo`);

--
-- Indexes for table `assettender`
--
ALTER TABLE `assettender`
  ADD PRIMARY KEY (`tenderId`,`regNo`),
  ADD KEY `regNo` (`regNo`),
  ADD KEY `tenderId` (`tenderId`);

--
-- Indexes for table `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`empId`),
  ADD UNIQUE KEY `nic` (`nic`);

--
-- Indexes for table `emptender`
--
ALTER TABLE `emptender`
  ADD PRIMARY KEY (`tenderId`,`empId`),
  ADD KEY `tenderId` (`tenderId`),
  ADD KEY `empId` (`empId`);

--
-- Indexes for table `equipment`
--
ALTER TABLE `equipment`
  ADD PRIMARY KEY (`equipID`);

--
-- Indexes for table `equiptender`
--
ALTER TABLE `equiptender`
  ADD PRIMARY KEY (`tenderId`,`equipID`),
  ADD UNIQUE KEY `equipID` (`equipID`),
  ADD KEY `tenderId` (`tenderId`);

--
-- Indexes for table `jobasset`
--
ALTER TABLE `jobasset`
  ADD PRIMARY KEY (`tenderId`),
  ADD KEY `tenderId` (`tenderId`);

--
-- Indexes for table `jobemployee`
--
ALTER TABLE `jobemployee`
  ADD PRIMARY KEY (`tenderId`);

--
-- Indexes for table `materialtender`
--
ALTER TABLE `materialtender`
  ADD PRIMARY KEY (`tenderId`,`materialType`),
  ADD KEY `materialType` (`materialType`),
  ADD KEY `tenderId` (`tenderId`);

--
-- Indexes for table `rawmaterial`
--
ALTER TABLE `rawmaterial`
  ADD PRIMARY KEY (`type`),
  ADD KEY `supplierId` (`supplierId`);

--
-- Indexes for table `supplier`
--
ALTER TABLE `supplier`
  ADD PRIMARY KEY (`supplierId`),
  ADD UNIQUE KEY `supplierId` (`supplierId`),
  ADD UNIQUE KEY `nic` (`nic`);

--
-- Indexes for table `tender`
--
ALTER TABLE `tender`
  ADD PRIMARY KEY (`tenderId`),
  ADD UNIQUE KEY `tenderName` (`tenderName`);

--
-- Indexes for table `transport`
--
ALTER TABLE `transport`
  ADD PRIMARY KEY (`tripId`),
  ADD KEY `regNo` (`regNo`),
  ADD KEY `tenderId` (`tenderId`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`userId`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Indexes for table `utilities`
--
ALTER TABLE `utilities`
  ADD PRIMARY KEY (`billNo`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `employee`
--
ALTER TABLE `employee`
  MODIFY `empId` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `equipment`
--
ALTER TABLE `equipment`
  MODIFY `equipID` int(10) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `supplier`
--
ALTER TABLE `supplier`
  MODIFY `supplierId` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `transport`
--
ALTER TABLE `transport`
  MODIFY `tripId` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `assettender`
--
ALTER TABLE `assettender`
  ADD CONSTRAINT `assettender_ibfk_1` FOREIGN KEY (`regNo`) REFERENCES `asset` (`regNo`),
  ADD CONSTRAINT `assettender_ibfk_2` FOREIGN KEY (`tenderId`) REFERENCES `tender` (`tenderId`);

--
-- Constraints for table `emptender`
--
ALTER TABLE `emptender`
  ADD CONSTRAINT `emptender_ibfk_1` FOREIGN KEY (`tenderId`) REFERENCES `tender` (`tenderId`),
  ADD CONSTRAINT `emptender_ibfk_2` FOREIGN KEY (`empId`) REFERENCES `employee` (`empId`);

--
-- Constraints for table `equiptender`
--
ALTER TABLE `equiptender`
  ADD CONSTRAINT `equiptender_ibfk_1` FOREIGN KEY (`tenderId`) REFERENCES `tender` (`tenderId`),
  ADD CONSTRAINT `equiptender_ibfk_2` FOREIGN KEY (`equipID`) REFERENCES `equipment` (`equipID`);

--
-- Constraints for table `jobasset`
--
ALTER TABLE `jobasset`
  ADD CONSTRAINT `jobasset_ibfk_1` FOREIGN KEY (`tenderId`) REFERENCES `tender` (`tenderId`);

--
-- Constraints for table `jobemployee`
--
ALTER TABLE `jobemployee`
  ADD CONSTRAINT `jobemployee_ibfk_1` FOREIGN KEY (`tenderId`) REFERENCES `tender` (`tenderId`);

--
-- Constraints for table `materialtender`
--
ALTER TABLE `materialtender`
  ADD CONSTRAINT `materialtender_ibfk_1` FOREIGN KEY (`tenderId`) REFERENCES `tender` (`tenderId`),
  ADD CONSTRAINT `materialtender_ibfk_2` FOREIGN KEY (`materialType`) REFERENCES `rawmaterial` (`type`);

--
-- Constraints for table `rawmaterial`
--
ALTER TABLE `rawmaterial`
  ADD CONSTRAINT `rawmaterial_ibfk_1` FOREIGN KEY (`supplierId`) REFERENCES `supplier` (`supplierId`);

--
-- Constraints for table `transport`
--
ALTER TABLE `transport`
  ADD CONSTRAINT `transport_ibfk_1` FOREIGN KEY (`regNo`) REFERENCES `asset` (`regNo`),
  ADD CONSTRAINT `transport_ibfk_2` FOREIGN KEY (`tenderId`) REFERENCES `tender` (`tenderId`);

--
-- Constraints for table `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `user_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `employee` (`empId`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
