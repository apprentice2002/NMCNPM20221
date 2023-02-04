-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: localhost    Database: csdl
-- ------------------------------------------------------
-- Server version	8.0.31

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
GRANT ALL PRIVILEGES ON *.* TO 'root'@'localhost' IDENTIFIED BY '1' WITH GRANT OPTION;
FLUSH PRIVILEGES;
--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
                         `id` int NOT NULL AUTO_INCREMENT,
                         `tai_khoan` varchar(45) NOT NULL,
                         `mat_khau` varchar(45) NOT NULL,
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES (1,'admin1','1'),(2,'admin2','1'),(3,'admin3','1');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `can_cuoc_cong_dan`
--

DROP TABLE IF EXISTS `can_cuoc_cong_dan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `can_cuoc_cong_dan` (
                                     `SoCCCD` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                                     `NgayCap` date NOT NULL,
                                     `NoiCap` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                                     `MaNhanKhau` int NOT NULL,
                                     PRIMARY KEY (`SoCCCD`),
                                     KEY `MaNhanKhau` (`MaNhanKhau`),
                                     CONSTRAINT `can_cuoc_cong_dan_ibfk_1` FOREIGN KEY (`MaNhanKhau`) REFERENCES `nhan_khau` (`MaNhanKhau`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `can_cuoc_cong_dan`
--

LOCK TABLES `can_cuoc_cong_dan` WRITE;
/*!40000 ALTER TABLE `can_cuoc_cong_dan` DISABLE KEYS */;
INSERT INTO `can_cuoc_cong_dan` VALUES ('033367890999','2016-04-12','Hà Nội',7),('033367891111','2015-02-24','Nam Định',1),('033367892222','2015-02-24','Nam Định',2),('033367895555','2018-06-07','Nam Định',4),('033367898888','2018-03-11','Nam Định',6),('033367899999','2017-02-11','Nam Định',3);
/*!40000 ALTER TABLE `can_cuoc_cong_dan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ho_khau`
--

DROP TABLE IF EXISTS `ho_khau`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ho_khau` (
                           `MaHoKhau` int NOT NULL AUTO_INCREMENT,
                           `NgayTao` date NOT NULL,
                           `MaChuHo` int NOT NULL,
                           `DiaChiHoKhau` varchar(5) NOT NULL,
                           `SoThanhVien` int NOT NULL ,
                           PRIMARY KEY (`MaHoKhau`),
                           KEY `MaChuHo` (`MaChuHo`),
                           KEY `DiaChiHoKhau` (`DiaChiHoKhau`),
                           CONSTRAINT `ho_khau_ibfk_1` FOREIGN KEY (`MaChuHo`) REFERENCES `nhan_khau` (`MaNhanKhau`),
                           CONSTRAINT `ho_khau_ibfk_2` FOREIGN KEY (`DiaChiHoKhau`) REFERENCES `thon` (`MaThon`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ho_khau`
--

LOCK TABLES `ho_khau` WRITE;
/*!40000 ALTER TABLE `ho_khau` DISABLE KEYS */;
INSERT INTO `ho_khau` VALUES (1,'1998-08-08',1,'4'),(2,'2022-04-05',3,'4');
/*!40000 ALTER TABLE `ho_khau` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `huyen`
--

DROP TABLE IF EXISTS `huyen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `huyen` (
                         `MaHuyen` varchar(5) NOT NULL,
                         `TenHuyen` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                         `MaTinh` varchar(5) NOT NULL,
                         PRIMARY KEY (`MaHuyen`),
                         KEY `MaTinh` (`MaTinh`),
                         CONSTRAINT `huyen_ibfk_1` FOREIGN KEY (`MaTinh`) REFERENCES `tinh` (`MaTinh`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `huyen`
--

LOCK TABLES `huyen` WRITE;
/*!40000 ALTER TABLE `huyen` DISABLE KEYS */;
INSERT INTO `huyen` VALUES ('1','Cầu Giấy','1'),('2','Hoàng Mai','1'),('3','Hoàn Kiếm','1'),('4','Ý Yên','2'),('5','Nghĩa Hưng','2');
/*!40000 ALTER TABLE `huyen` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lich_su_nhan_khau_chuyen_di`
--

DROP TABLE IF EXISTS `lich_su_nhan_khau_chuyen_di`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lich_su_nhan_khau_chuyen_di` (
                                               `MaLSNKCD` int NOT NULL AUTO_INCREMENT,
                                               `NgayChuyenDi` date NOT NULL,
                                               `GhiChu` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
                                               `DiaChiChuyenDi` varchar(5) NOT NULL,
                                               `MaNhanKhau` int NOT NULL,
                                               PRIMARY KEY (`MaLSNKCD`),
                                               KEY `MaNhanKhau` (`MaNhanKhau`),
                                               KEY `DiaChiChuyenDi` (`DiaChiChuyenDi`),
                                               CONSTRAINT `lich_su_nhan_khau_chuyen_di_ibfk_1` FOREIGN KEY (`MaNhanKhau`) REFERENCES `nhan_khau` (`MaNhanKhau`),
                                               CONSTRAINT `lich_su_nhan_khau_chuyen_di_ibfk_2` FOREIGN KEY (`DiaChiChuyenDi`) REFERENCES `thon` (`MaThon`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lich_su_nhan_khau_chuyen_di`
--

LOCK TABLES `lich_su_nhan_khau_chuyen_di` WRITE;
/*!40000 ALTER TABLE `lich_su_nhan_khau_chuyen_di` DISABLE KEYS */;
INSERT INTO `lich_su_nhan_khau_chuyen_di` VALUES (1,'2022-05-06','Đã chuyển đi','1',4);
/*!40000 ALTER TABLE `lich_su_nhan_khau_chuyen_di` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lich_su_thay_doi`
--

DROP TABLE IF EXISTS `lich_su_thay_doi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lich_su_thay_doi` (
                                    `MaLSTD` int NOT NULL AUTO_INCREMENT,
                                    `NgayThayDoi` date NOT NULL,
                                    `GhiChu` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
                                    PRIMARY KEY (`MaLSTD`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lich_su_thay_doi`
--

LOCK TABLES `lich_su_thay_doi` WRITE;
/*!40000 ALTER TABLE `lich_su_thay_doi` DISABLE KEYS */;
INSERT INTO `lich_su_thay_doi` VALUES (1,'2022-04-05','Tách hộ khẩu');
/*!40000 ALTER TABLE `lich_su_thay_doi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lich_su_thay_doi_ho_khau`
--

DROP TABLE IF EXISTS `lich_su_thay_doi_ho_khau`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lich_su_thay_doi_ho_khau` (
                                            `ThayDoiChuHo` tinyint(1) NOT NULL,
                                            `ThemNhanKhau` tinyint(1) NOT NULL,
                                            `XoaNhanKhau` tinyint(1) NOT NULL,
                                            `MaHoKhau` int NOT NULL,
                                            `MaNhanKhau` int NOT NULL,
                                            `MaLSTD` int NOT NULL,
                                            KEY `MaNhanKhau` (`MaNhanKhau`),
                                            KEY `MaHoKhau` (`MaHoKhau`),
                                            KEY `MaLSTD` (`MaLSTD`),
                                            CONSTRAINT `lich_su_thay_doi_ho_khau_ibfk_1` FOREIGN KEY (`MaNhanKhau`) REFERENCES `nhan_khau` (`MaNhanKhau`),
                                            CONSTRAINT `lich_su_thay_doi_ho_khau_ibfk_2` FOREIGN KEY (`MaHoKhau`) REFERENCES `ho_khau` (`MaHoKhau`),
                                            CONSTRAINT `lich_su_thay_doi_ho_khau_ibfk_3` FOREIGN KEY (`MaLSTD`) REFERENCES `lich_su_thay_doi` (`MaLSTD`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lich_su_thay_doi_ho_khau`
--

LOCK TABLES `lich_su_thay_doi_ho_khau` WRITE;
/*!40000 ALTER TABLE `lich_su_thay_doi_ho_khau` DISABLE KEYS */;
INSERT INTO `lich_su_thay_doi_ho_khau` VALUES (0,0,1,1,3,1);
/*!40000 ALTER TABLE `lich_su_thay_doi_ho_khau` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nhan_khau`
--

DROP TABLE IF EXISTS `nhan_khau`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nhan_khau` (
                             `MaNhanKhau` int NOT NULL AUTO_INCREMENT,
                             `HoTen` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                             `BiDanh` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
                             `NgaySinh` date NOT NULL,
                             `NoiSinh` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
                             `GioiTinh` bit(1) DEFAULT NULL,
                             `DanToc` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
                             `QuocTich` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
                             `TonGiao` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
                             `NguyenQuan` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
                             `NgheNghiep` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
                             `NoiLamViec` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
                             `SoDienThoai` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
                             `NgayTao` date DEFAULT NULL,
                             `MaHo` int NOT NULL,
                             PRIMARY KEY (`MaNhanKhau`),
                             KEY(`MaHo`),
                             CONSTRAINT `ho_khau_ibfk_10` FOREIGN KEY (`MaHo`) REFERENCES `ho_khau` (`MaHoKhau`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nhan_khau`
--

LOCK TABLES `nhan_khau` WRITE;
/*!40000 ALTER TABLE `nhan_khau` DISABLE KEYS */;
INSERT INTO `nhan_khau` VALUES (1,'Hoàng Văn Sơn','Sơn','1977-06-15','Nghĩa Minh, Nam Định',_binary '','Kinh','Việt Nam','Không','Mghĩa Minh, Nghĩa Hưng, Nam Định','Làm ruộng',NULL,'0987654333','1977-06-15')
                             ,(2,'Nguyễn Thị Hương','Hương','1980-03-03','Nghĩa Minh, Nam Định',_binary '\0','Kinh','Việt Nam','Không','Mghĩa Minh, Nghĩa Hưng, Nam Định','Làm ruộng',NULL,'0987654444','1980-03-03')
                             ,(3,'Hoàng Văn Vinh','Vinh','1999-01-02','Bệnh viện Nghĩa Minh, Nam Định',_binary '','Việt Nam','Kinh','Không','Mghĩa Minh, Nghĩa Hưng, Nam Định','Giáo viên','Trường cấp THPT Nghĩa Minh','0987654322','1999-01-02')
                             ,(4,'Hoàng Văn Nam','Nam','2002-03-04','Bệnh viện Nghĩa Minh, Nam Định',_binary '','Kinh','Việt Nam','Không','Mghĩa Minh, Nghĩa Hưng, Nam Định','Công nhân','Công ty TNHH Nghĩa Minh','0987654322','2000-01-02')
                             ,(5,'Hoàng Thu Lan','Lan','2010-05-04','Bệnh viện Nghĩa Minh, Nam Định',_binary '\0','Kinh','Việt Nam','Không','Mghĩa Minh, Nghĩa Hưng, Nam Định','Học sinh',NULL,NULL,'2010-05-04')
                             ,(6,'Ngô Văn Sơn','Sơn','2000-11-24','Bệnh viện Ý Yên, Nam Định',_binary '','Kinh','Việt Nam','Không','Yên Lộc, Ý Yên, Nam Định','Công nhân','Công ty TNHH Nghĩa Minh','0987654321','2020-12-19')
                             ,(7,'Nguyễn Hoàng Linh','Linh','1999-03-26','Cầu Giấy, Hà Nội',_binary '\0','Kinh','Việt Nam','Thiên Chúa giáo','Nghĩa Tân, Cầu Giấy, Hà Nội','Giáo viên','Trường cấp THPT Nghĩa Minh','0987654333','2019-12-19');
/*!40000 ALTER TABLE `nhan_khau` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tam_tru`
--

DROP TABLE IF EXISTS `tam_tru`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tam_tru` (
                           `MaTamTru` int NOT NULL AUTO_INCREMENT,
                           `CoQuanKhaiBao` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                           `NgayBatDau` date NOT NULL,
                           `NgayKetThuc` date NOT NULL,
                           `GhiChu` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
                           `DiaChiTamTru` varchar(5) NOT NULL,
                           `MaNhanKhau` int NOT NULL,
                           PRIMARY KEY (`MaTamTru`),
                           KEY `DiaChiTamTru` (`DiaChiTamTru`),
                           KEY `MaNhanKhau` (`MaNhanKhau`),
                           CONSTRAINT `tam_tru_ibfk_1` FOREIGN KEY (`DiaChiTamTru`) REFERENCES `thon` (`MaThon`),
                           CONSTRAINT `tam_tru_ibfk_2` FOREIGN KEY (`MaNhanKhau`) REFERENCES `nhan_khau` (`MaNhanKhau`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tam_tru`
--

LOCK TABLES `tam_tru` WRITE;
/*!40000 ALTER TABLE `tam_tru` DISABLE KEYS */;
INSERT INTO `tam_tru` VALUES (1,'Công an xã Nghĩa Minh','2020-12-19','2020-12-30','Đã kết thúc','4',6),(2,'Công an xã Nghĩa Minh','2021-01-02','2021-01-30','Đã kết thúc','4',7),(3,'Công an xã Nghĩa Minh','2021-01-02','2021-01-20','Đã kết thúc','4',6),(4,'Công an xã Nghĩa Minh','2021-01-31','2021-02-15','Đã kết thúc','4',7),(5,'Công an xã Nghĩa Minh','2021-02-16','2021-02-28','Đã kết thúc','4',7);
/*!40000 ALTER TABLE `tam_tru` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tam_vang`
--

DROP TABLE IF EXISTS `tam_vang`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tam_vang` (
                            `MaTamVang` int NOT NULL AUTO_INCREMENT,
                            `CoQuanKhaiBao` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                            `NgayBatDau` date NOT NULL,
                            `NgayKetThuc` date DEFAULT NULL,
                            `GhiChu` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
                            `MaNhanKhau` int NOT NULL,
                            PRIMARY KEY (`MaTamVang`),
                            KEY `MaNhanKhau` (`MaNhanKhau`),
                            CONSTRAINT `tam_vang_ibfk_1` FOREIGN KEY (`MaNhanKhau`) REFERENCES `nhan_khau` (`MaNhanKhau`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tam_vang`
--

LOCK TABLES `tam_vang` WRITE;
/*!40000 ALTER TABLE `tam_vang` DISABLE KEYS */;
INSERT INTO `tam_vang` VALUES (1,'Công an xã Nghĩa Minh','2000-08-21','2000-10-20','Đã kết thúc',1),(2,'Công an xã Nghĩa Minh','2000-11-02','2000-11-30','Đã kết thúc',1),(3,'Công an xã Nghĩa Minh','2010-04-05','2010-01-20','Đã kết thúc',2),(4,'Công an xã Nghĩa Minh','2011-05-06','2010-06-10','Đã kết thúc',1),(5,'Công an xã Nghĩa Minh','2015-05-09','2015-07-01','Đã kết thúc',2);
/*!40000 ALTER TABLE `tam_vang` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `thanh_vien_cua_ho`
--

DROP TABLE IF EXISTS `thanh_vien_cua_ho`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `thanh_vien_cua_ho` (
                                     `QuanHeVoiChuHo` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                                     `MaNhanKhau` int NOT NULL,
                                     `MaHoKhau` int NOT NULL,
                                     KEY `MaNhanKhau` (`MaNhanKhau`),
                                     KEY `MaHoKhau` (`MaHoKhau`),
                                     CONSTRAINT `thanh_vien_cua_ho_ibfk_1` FOREIGN KEY (`MaNhanKhau`) REFERENCES `nhan_khau` (`MaNhanKhau`),
                                     CONSTRAINT `thanh_vien_cua_ho_ibfk_2` FOREIGN KEY (`MaHoKhau`) REFERENCES `ho_khau` (`MaHoKhau`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `thanh_vien_cua_ho`
--

LOCK TABLES `thanh_vien_cua_ho` WRITE;
/*!40000 ALTER TABLE `thanh_vien_cua_ho` DISABLE KEYS */;
INSERT INTO `thanh_vien_cua_ho` VALUES ('Vợ',2,1),('Con',3,1),('Con',4,1),('Con',5,1),('Vợ',7,2);
/*!40000 ALTER TABLE `thanh_vien_cua_ho` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `thon`
--
select * from admin;


DROP TABLE IF EXISTS `thon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `thon` (
                        `MaThon` varchar(5) NOT NULL,
                        `TenThon` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                        `MaXa` varchar(5) NOT NULL,
                        PRIMARY KEY (`MaThon`),
                        KEY `MaXa` (`MaXa`),
                        CONSTRAINT `thon_ibfk_1` FOREIGN KEY (`MaXa`) REFERENCES `xa` (`MaXa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `thon`
--

LOCK TABLES `thon` WRITE;
/*!40000 ALTER TABLE `thon` DISABLE KEYS */;
INSERT INTO `thon` VALUES ('1','Phố Chùa Hà','1'),('2','Đông Cao','4'),('3','Tiền Phong','4'),('4','Hoàng An','5'),('5','Sơn Nam','5');
/*!40000 ALTER TABLE `thon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `thuong_tru`
--

DROP TABLE IF EXISTS `thuong_tru`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `thuong_tru` (
                              `MaThuongTru` int NOT NULL AUTO_INCREMENT,
                              `NgayKhaiBao` date NOT NULL,
                              `CoQuanKhaiBao` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                              `DiaChiThuongTruTruocDo` varchar(5) NOT NULL,
                              `DiaChiThuongTruHienTai` varchar(5) NOT NULL,
                              `MaNhanKhau` int NOT NULL,
                              PRIMARY KEY (`MaThuongTru`),
                              KEY `DiaChiThuongTruTruocDo` (`DiaChiThuongTruTruocDo`),
                              KEY `DiaChiThuongTruHienTai` (`DiaChiThuongTruHienTai`),
                              KEY `MaNhanKhau` (`MaNhanKhau`),
                              CONSTRAINT `thuong_tru_ibfk_1` FOREIGN KEY (`DiaChiThuongTruTruocDo`) REFERENCES `thon` (`MaThon`),
                              CONSTRAINT `thuong_tru_ibfk_2` FOREIGN KEY (`DiaChiThuongTruHienTai`) REFERENCES `thon` (`MaThon`),
                              CONSTRAINT `thuong_tru_ibfk_3` FOREIGN KEY (`MaNhanKhau`) REFERENCES `nhan_khau` (`MaNhanKhau`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `thuong_tru`
--

LOCK TABLES `thuong_tru` WRITE;
/*!40000 ALTER TABLE `thuong_tru` DISABLE KEYS */;
INSERT INTO `thuong_tru` VALUES (1,'2021-02-20','Công an xã Nghĩa Minh','3','4',6),(2,'2021-05-10','Công an xã Nghĩa Minh','1','4',7);
/*!40000 ALTER TABLE `thuong_tru` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tinh`
--

DROP TABLE IF EXISTS `tinh`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tinh` (
                        `MaTinh` varchar(5) NOT NULL,
                        `TenTinh` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                        PRIMARY KEY (`MaTinh`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tinh`
--

LOCK TABLES `tinh` WRITE;
/*!40000 ALTER TABLE `tinh` DISABLE KEYS */;
INSERT INTO `tinh` VALUES ('1','Hà Nội'),('2','Nam Định'),('3','TP Hồ Chí Minh'),('4','Đà Nẵng'),('5','Cần Thơ');
/*!40000 ALTER TABLE `tinh` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `xa`
--

DROP TABLE IF EXISTS `xa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `xa` (
                      `MaXa` varchar(5) NOT NULL,
                      `TenXa` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                      `MaHuyen` varchar(5) NOT NULL,
                      PRIMARY KEY (`MaXa`),
                      KEY `MaHuyen` (`MaHuyen`),
                      CONSTRAINT `xa_ibfk_1` FOREIGN KEY (`MaHuyen`) REFERENCES `huyen` (`MaHuyen`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `xa`
--

LOCK TABLES `xa` WRITE;
/*!40000 ALTER TABLE `xa` DISABLE KEYS */;
INSERT INTO `xa` VALUES ('1','Nghĩa Tân','1'),('2','Tân Mai','2'),('3','Trần Phú','2'),('4','Yên Lộc','4'),('5','Nghĩa Minh','5');
/*!40000 ALTER TABLE `xa` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-12-26 15:02:32
