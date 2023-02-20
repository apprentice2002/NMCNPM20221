-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 20, 2023 at 03:19 AM
-- Server version: 10.4.25-MariaDB
-- PHP Version: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `csdl4`
--

-- --------------------------------------------------------

--
-- Table structure for table `chung_minh_thu`
--

CREATE TABLE `chung_minh_thu` (
  `ID` int(11) NOT NULL,
  `idNhanKhau` int(11) DEFAULT NULL,
  `soCMT` varchar(12) COLLATE utf8_unicode_ci NOT NULL,
  `ngayCap` date DEFAULT NULL,
  `noiCap` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `chung_minh_thu`
--

INSERT INTO `chung_minh_thu` (`ID`, `idNhanKhau`, `soCMT`, `ngayCap`, `noiCap`) VALUES
(13, 26, '000000000001', NULL, NULL),
(14, 27, '000000000002', NULL, NULL),
(15, 28, '000000000003', NULL, NULL),
(16, 29, '000000000004', NULL, NULL),
(17, 30, '000000000005', NULL, NULL),
(18, 31, '000000000006', NULL, NULL),
(19, 32, '000000000007', NULL, NULL),
(20, 33, '000000000008', NULL, NULL),
(21, 34, '000000000009', NULL, NULL),
(22, 35, '100000000001', NULL, NULL),
(23, 36, '100000000002', NULL, NULL),
(24, 37, '000000000010', NULL, NULL),
(25, 38, '000000000011', NULL, NULL),
(13, 26, '000000000001', NULL, NULL),
(14, 27, '000000000002', NULL, NULL),
(15, 28, '000000000003', NULL, NULL),
(16, 29, '000000000004', NULL, NULL),
(17, 30, '000000000005', NULL, NULL),
(18, 31, '000000000006', NULL, NULL),
(19, 32, '000000000007', NULL, NULL),
(20, 33, '000000000008', NULL, NULL),
(21, 34, '000000000009', NULL, NULL),
(22, 35, '100000000001', NULL, NULL),
(23, 36, '100000000002', NULL, NULL),
(24, 37, '000000000010', NULL, NULL),
(25, 38, '000000000011', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `dinh_chinh`
--

CREATE TABLE `dinh_chinh` (
  `ID` int(11) NOT NULL,
  `idHoKhau` int(11) DEFAULT NULL,
  `thongTinThayDoi` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `thayDoiTu` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `thayDoiThanh` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ngayThayDoi` date DEFAULT NULL,
  `nguoiThayDoi` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `dot_phat`
--

CREATE TABLE `dot_phat` (
  `idDotPhat` int(11) NOT NULL,
  `tenDotPhat` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `thoiGianPhat` date DEFAULT NULL,
  `ghiChu` text COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `dot_phat`
--

INSERT INTO `dot_phat` (`idDotPhat`, `tenDotPhat`, `thoiGianPhat`, `ghiChu`) VALUES
(1, 'PT thanh tich hoc tap 2023', '2023-06-01', 'phatthuonghocsinh'),
(2, 'PQ trung thu 2023', '2023-08-15', 'phatquatrenho'),
(3, 'PQ tet 2023', '2023-12-28', 'phatquatrenho');

-- --------------------------------------------------------

--
-- Table structure for table `gia_dinh`
--

CREATE TABLE `gia_dinh` (
  `ID` int(11) NOT NULL,
  `idNhanKhau` int(11) DEFAULT NULL,
  `hoTen` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `namSinh` date DEFAULT NULL,
  `gioiTinh` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `quanHeVoiNhanKhau` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ngheNghiep` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `diaChiHienTai` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `gia_dinh`
--

INSERT INTO `gia_dinh` (`ID`, `idNhanKhau`, `hoTen`, `namSinh`, `gioiTinh`, `quanHeVoiNhanKhau`, `ngheNghiep`, `diaChiHienTai`) VALUES
(2, 27, 'Nguyễn Minh Quân', '1995-05-31', 'Nam', 'Chồng', 'Kỹ sư', 'Số 2 Tạ Quang Bửu, Hai Bà Trưng, Hà Nội'),
(3, 28, 'Trần Thanh Duyên', '1997-12-23', 'Nữ', 'Vợ', 'Nhân viên văn phòng', 'Số 2 Tạ Quang Bửu, quận Hai Bà Trưng, Hà Nội'),
(4, 29, 'Vũ Mỹ Linh', '1965-12-06', 'Nữ', 'Vợ', 'Nội trợ', 'Số 3 Tạ Quang Bửu, quận Hai Bà Trưng, Hà Nội'),
(5, 29, 'Nguyễn Tiến Đạt', '1990-09-09', 'Nam', 'Con trai', 'Kỹ sư điện', 'Số 3 Tạ Quang Bửu, quận Hai Bà Trưng, Hà Nội'),
(6, 29, 'Nguyễn Trà My', '1997-12-12', 'Nữ', 'Con gái', 'Luật sư', 'Số 3 Tạ Quang Bửu, quận Hai Bà Trưng, Hà Nội'),
(7, 30, 'Nguyễn Tiến Dũng', '1964-06-03', 'Nam', 'Chồng', 'Phó giám đốc', 'Số 3 Tạ Quang Bửu, quận Hai Bà Trưng, Hà Nội'),
(8, 30, 'Nguyễn Tiến Đạt', '1990-09-09', 'Nam', 'Con trai', 'Kỹ sư điện', 'Số 3 Tạ Quang Bửu, quận Hai Bà Trưng, Hà Nội'),
(9, 30, 'Nguyễn Trà My', '1997-12-12', 'Nữ', 'Con Gái', 'Luật sư', ''),
(10, 31, 'Nguyễn Tiến Dũng', '1964-06-03', 'Nam', 'Bố', 'Phó giám đốc', 'Số 3 Tạ Quang Bửu, quận Hai Bà Trưng, Hà Nội'),
(11, 31, 'Vũ Mỹ Linh', '1965-12-06', 'Nữ', 'Mẹ', 'Nội trợ', 'Số 3 Tạ Quang Bửu, quận Hai Bà Trưng, Hà Nội'),
(12, 31, 'Nguyễn Trà My', '1997-12-12', 'Nữ', 'Em gái', 'Luật sư', 'Số 3 Tạ Quang Bửu, quận Hai Bà Trưng, Hà Nội'),
(13, 32, 'Nguyễn Tiến Dũng', '1964-06-03', 'Nam', 'Bố', 'Phó giám đốc', 'Số 3 Tạ Quang Bửu, quận Hai Bà Trưng, Hà Nội'),
(14, 32, 'Vũ Mỹ Linh', '1965-12-06', 'Nữ', 'Mẹ', 'Nội trợ', 'Số 3 Tạ Quang Bửu, quận Hai Bà Trưng, Hà Nội'),
(15, 32, 'Nguyễn Tiến Đạt', '1990-09-09', 'Nam', 'Anh trai', 'Kỹ sư điện', 'Số 3 Tạ Quang Bửu, quận Hai Bà Trưng, Hà Nội'),
(16, 33, 'Nguyễn Minh Tuyết', '1985-09-02', 'Nữ', 'Vợ', 'Bác sĩ', 'Số 4 Tạ Quang Bửu, quận Hai Bà Trưng, Hà Nội'),
(17, 33, 'Trần Trung Kiên', '2008-12-25', 'Nam', 'Con trai', 'Học sinh', 'Số 4 Tạ Quang Bửu, quận Hai Bà Trưng, Hà Nội'),
(18, 33, 'Trần Thúy Ngọc', '2013-01-15', 'Nữ', 'Con gái', 'Học sinh', 'Số 4 Tạ Quang Bửu, quận Hai Bà Trưng, Hà Nội'),
(19, 34, 'Trần Trung Kiên', '2008-12-25', 'Nam', 'Con trai', 'Học sinh', 'Số 4 Tạ Quang Bửu, quận Hai Bà Trưng, Hà Nội'),
(20, 34, 'Trần Thúy Ngọc', '2013-01-15', 'Nữ', 'Con gái', 'Học sinh', 'Số 4 Tạ Quang Bửu, quận Hai Bà Trưng, Hà Nội'),
(21, 34, 'Trần Văn Nam', '1980-07-09', 'Nam', 'Chồng', 'Giảng viên đại học', 'Số 4 Tạ Quang Bửu, quận Hai Bà Trưng, Hà Nội'),
(22, 35, 'Trần Văn Nam', '1980-07-09', 'Nam', 'Bố', 'Giảng viên đại học', 'Số 4 Tạ Quang Bửu, quận Hai Bà Trưng, Hà Nội'),
(23, 35, 'Nguyễn Minh Tuyết', '1985-09-02', 'Nữ', 'Mẹ', 'Bác sĩ', 'Số 4 Tạ Quang Bửu, quận Hai Bà Trưng, Hà Nội'),
(24, 35, 'Trần Thúy Ngọc', '2013-01-15', 'Nữ', 'Em gái', 'Học sinh', 'Số 4 Tạ Quang Bửu, quận Hai Bà Trưng, Hà Nội'),
(25, 36, 'Trần Văn Nam', '1980-07-09', 'Nam', 'Bố', 'Giảng viên đại học', 'Số 4 Tạ Quang Bửu, quận Hai Bà Trưng, Hà Nội'),
(26, 36, 'Nguyễn Minh Tuyết', '1985-09-02', 'Nữ ', 'Mẹ', 'Bác sĩ', 'Số 4 Tạ Quang Bửu, quận Hai Bà Trưng, Hà Nội'),
(27, 36, 'Trần Trung Kiên', '2008-12-25', 'Nam', 'Anh trai', 'Học sinh', 'Số 4 Tạ Quang Bửu, quận Hai Bà Trưng, Hà Nội'),
(28, 37, 'Bùi Thị Hà', '1948-02-03', 'Nữ', 'Vợ', 'Nội trợ', 'Số 5 Tạ Quang Bửu, quận Hai Bà Trưng, Hà Nội'),
(29, 37, 'Lý Thành Nam', '1968-06-12', 'Nam', 'Con Trai', 'Công nhân', 'Số 89, ngõ 98 Trường Chinh, Hà Nội'),
(30, 37, 'Lý Thu Thủy', '1971-03-05', 'Nữ', 'Con Gái', 'Nhân viên văn phòng', 'Số 3, ngõ 568 Đường Láng, Hà Nội'),
(31, 38, 'Lý Văn Công', '1945-06-04', 'Nam', 'Chồng', 'Bộ đội về hưu', 'Số 5 Tạ Quang Bửu, quận Hai Bà Trưng, Hà Nội'),
(32, 38, 'Lý Thành Nam', '1968-06-12', 'Nam', 'Con trai', 'Công nhân', 'Số 89, ngõ 98 Trường Chinh, Hà Nội'),
(33, 38, 'Lý Thu Thủy', '1971-03-05', 'Nữ', 'Con gái', 'Nhân viên văn phòng', 'Số 3, ngõ 568 Đường Láng, Hà Nội');

-- --------------------------------------------------------

--
-- Table structure for table `ho_khau`
--

CREATE TABLE `ho_khau` (
  `ID` int(11) NOT NULL,
  `maHoKhau` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `idChuHo` int(11) DEFAULT NULL,
  `maKhuVuc` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `diaChi` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ngayLap` date DEFAULT NULL,
  `ngayChuyenDi` date DEFAULT NULL,
  `lyDoChuyen` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `nguoiThucHien` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `daXoa` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `ho_khau`
--

INSERT INTO `ho_khau` (`ID`, `maHoKhau`, `idChuHo`, `maKhuVuc`, `diaChi`, `ngayLap`, `ngayChuyenDi`, `lyDoChuyen`, `nguoiThucHien`, `daXoa`) VALUES
(15, 'TQB003', 32, 'HN03', 'Số 3 Tạ Quang Bửu, quận Hai Bà Trưng, Hà Nội', '2019-12-08', NULL, NULL, NULL, NULL),
(16, 'TQB004', 33, 'HN03', 'Số 4 Tạ Quang Bửu, quận Hai Bà Trưng, Hà Nội', '2019-12-08', NULL, NULL, NULL, NULL),
(17, 'TDN075', 38, 'HN03', '75 Trần Đại Nghĩa', '2023-02-07', NULL, NULL, NULL, NULL),
(18, 'TDN076', 35, 'HN03', '76 Trần Đại Nghĩa', '2023-02-07', '2023-02-10', 'Li dị', 'admin', NULL),
(23, 'TDN077', 34, 'HN03', '77 Trần Đại Nghĩa', '2023-02-09', NULL, NULL, NULL, 0),
(26, 'TDN078', 26, 'HN03', '78 Trần Đại Nghĩa', '2023-02-09', NULL, NULL, NULL, 1),
(27, 'TDN079', 30, 'HN03', '79 Trần Đại Nghĩa', '2023-02-11', NULL, NULL, NULL, NULL),
(28, '*****', 28, '****', '****', '2023-02-12', NULL, NULL, NULL, 1);

-- --------------------------------------------------------

--
-- Table structure for table `khai_tu`
--

CREATE TABLE `khai_tu` (
  `ID` int(11) NOT NULL,
  `soGiayKhaiTu` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `idNguoiKhai` int(11) DEFAULT NULL,
  `idNguoiChet` int(11) DEFAULT NULL,
  `ngayKhai` date DEFAULT NULL,
  `ngayChet` date DEFAULT NULL,
  `lyDoChet` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `lich_su_thay_doi`
--

CREATE TABLE `lich_su_thay_doi` (
  `ID` int(11) NOT NULL,
  `NgayThayDoi` date NOT NULL,
  `GhiChu` varchar(200) CHARACTER SET utf8 DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `lich_su_thay_doi`
--

INSERT INTO `lich_su_thay_doi` (`ID`, `NgayThayDoi`, `GhiChu`) VALUES
(2, '2023-02-07', 'Tách hộ khẩu'),
(3, '2023-02-07', 'Tách hộ khẩu'),
(4, '2023-02-07', 'Tách hộ khẩu'),
(5, '2023-02-09', 'Thêm hộ khẩu'),
(6, '2023-02-09', 'Thêm hộ khẩu'),
(7, '2023-02-09', 'Thêm hộ khẩu'),
(8, '2023-02-09', 'Thêm hộ khẩu'),
(9, '2023-02-09', 'Thêm hộ khẩu'),
(10, '2023-02-09', 'Thêm hộ khẩu'),
(11, '2023-02-11', 'Thêm hộ khẩu'),
(12, '2023-02-11', 'Thêm hộ khẩu'),
(13, '2023-02-12', 'Tách hộ khẩu'),
(14, '2023-02-12', 'Tách hộ khẩu');

-- --------------------------------------------------------

--
-- Table structure for table `lich_su_thay_doi_ho_khau`
--

CREATE TABLE `lich_su_thay_doi_ho_khau` (
  `ThayDoiChuHo` tinyint(1) NOT NULL,
  `ThemNhanKhau` tinyint(1) NOT NULL,
  `XoaNhanKhau` tinyint(1) NOT NULL,
  `IdHoKhau` int(11) NOT NULL,
  `IdNhanKhau` int(11) NOT NULL,
  `IdLSTD` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `lich_su_thay_doi_ho_khau`
--

INSERT INTO `lich_su_thay_doi_ho_khau` (`ThayDoiChuHo`, `ThemNhanKhau`, `XoaNhanKhau`, `IdHoKhau`, `IdNhanKhau`, `IdLSTD`) VALUES
(0, 1, 0, 18, 34, 3),
(0, 1, 0, 18, 35, 4),
(0, 1, 0, 26, 26, 8),
(0, 1, 0, 26, 28, 9),
(0, 1, 0, 26, 37, 10),
(0, 1, 0, 27, 27, 11),
(0, 1, 0, 27, 30, 12),
(0, 1, 0, 28, 28, 13),
(0, 1, 0, 28, 37, 14);

-- --------------------------------------------------------

--
-- Table structure for table `minh_chung`
--

CREATE TABLE `minh_chung` (
  `idMinhChung` int(11) NOT NULL,
  `ma_nhan_khau` int(11) DEFAULT NULL,
  `truong` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `lop` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `thanhTichHocTap` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ngayKhaiBao` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `minh_chung`
--

INSERT INTO `minh_chung` (`idMinhChung`, `ma_nhan_khau`, `truong`, `lop`, `thanhTichHocTap`, `ngayKhaiBao`) VALUES
(1, 35, 'chuVanAn', '1B', 'HSG', '2023-02-10'),
(2, 36, 'chuVanAn', '2B', 'HSG', '2023-02-10'),
(3, 34, 'chu', '4b', 'HSG', '2023-02-08'),
(5, 32, 'CHU', '7B', 'HSG', '2023-02-23');

-- --------------------------------------------------------

--
-- Table structure for table `nhan_khau`
--

CREATE TABLE `nhan_khau` (
  `ID` int(11) NOT NULL,
  `maNhanKhau` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `hoTen` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bietDanh` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `namSinh` date DEFAULT NULL,
  `gioiTinh` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `noiSinh` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `nguyenQuan` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `danToc` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `tonGiao` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `quocTich` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `soHoChieu` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `noiThuongTru` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `diaChiHienNay` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `trinhDoHocVan` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `TrinhDoChuyenMon` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bietTiengDanToc` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `trinhDoNgoaiNgu` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ngheNghiep` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `noiLamViec` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `tienAn` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `soDienThoai` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `ngayChuyenDen` date DEFAULT NULL,
  `lyDoChuyenDen` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ngayChuyenDi` date DEFAULT NULL,
  `lyDoChuyenDi` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `diaChiMoi` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ngayTao` date DEFAULT NULL,
  `idNguoiTao` int(11) DEFAULT NULL,
  `daXoa` tinyint(1) DEFAULT NULL,
  `ngayXoa` date DEFAULT NULL,
  `idNguoiXoa` int(11) DEFAULT NULL,
  `lyDoXoa` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ghiChu` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `nhan_khau`
--

INSERT INTO `nhan_khau` (`ID`, `maNhanKhau`, `hoTen`, `bietDanh`, `namSinh`, `gioiTinh`, `noiSinh`, `nguyenQuan`, `danToc`, `tonGiao`, `quocTich`, `soHoChieu`, `noiThuongTru`, `diaChiHienNay`, `trinhDoHocVan`, `TrinhDoChuyenMon`, `bietTiengDanToc`, `trinhDoNgoaiNgu`, `ngheNghiep`, `noiLamViec`, `tienAn`, `soDienThoai`, `ngayChuyenDen`, `lyDoChuyenDen`, `ngayChuyenDi`, `lyDoChuyenDi`, `diaChiMoi`, `ngayTao`, `idNguoiTao`, `daXoa`, `ngayXoa`, `idNguoiXoa`, `lyDoXoa`, `ghiChu`) VALUES
(26, NULL, 'Trinh Văn An', '', '1990-12-07', 'Nam', NULL, 'Hà Nội', 'Kinh', 'Không', 'Việt Nam', '', 'Số 1 Tạ Quang Bưu, Hai Bà Trưng, Hà Nội', 'Số 1 Tạ Quang Bưu, Hai Bà Trưng, Hà Nội', '12/12 chính quy', 'Thạc sĩ', 'Không', 'Anh trình đọ B', 'Giáo Viên', 'Trường THCS Chu Văn An', NULL, '', NULL, NULL, NULL, NULL, NULL, '2019-12-08', 1, NULL, NULL, NULL, NULL, NULL),
(27, NULL, 'Trần Thanh Duyên', '', '1997-12-23', 'Nữ', NULL, 'Hải Phòng', 'Kinh', 'Không', 'Việt Nam', '', 'Số 3, đường Đình Đông, phường Đình Đông, quận Ngô Quyền, Hải Phòng', 'Số 2 Tạ Quang Bửu, quận Hai Bà Trưng, Hà Nội', '12/12 chính quy', 'Thạc sĩ', 'Không', 'Anh trình độ D', 'Nhân viên văn phòng', 'Công ty ABC', NULL, '', NULL, NULL, NULL, NULL, NULL, '2019-12-08', 1, NULL, NULL, NULL, NULL, NULL),
(28, NULL, 'Nguyễn Minh Quân', '', '1995-05-31', 'Nam', NULL, 'Hà Nội', 'Kinh', 'Không', 'Việt Nam', '', 'Số 2 Tạ Quang Bửu, quận Hai Bà Trưng, Hà Nội', 'Số 2 Tạ Quang Bửu, quận Hai Bà Trưng, Hà Nội', '12/12 chính quy', 'Thạc sĩ', 'Không', 'Anh trình độ D', 'Kỹ sư', 'Viettel', NULL, '', NULL, NULL, NULL, NULL, NULL, '2019-12-08', 1, NULL, NULL, NULL, NULL, NULL),
(29, NULL, 'Nguyễn Tiến Dũng', '', '1964-06-03', 'Nam', NULL, 'Hải Dương', 'Kinh', 'Thiên chúa giáo', 'Việt Nam', '', 'Số 3 Tạ Quang Bửu, quận Hai Bà Trưng, Hà Nội', 'Số 4 Tạ Quang Bửu, quận Hai Bà Trưng, Hà Nội', '12/12 chính quy', 'Kỹ sư', 'Không', 'Không', 'Phó giám đốc', 'Công ty EXE', NULL, '', NULL, NULL, NULL, NULL, NULL, '2019-12-08', 1, NULL, NULL, NULL, NULL, NULL),
(30, NULL, 'Vũ Mỹ Linh', '', '1965-12-06', 'Nữ', NULL, 'Hà Nội', 'Kinh', 'Không', 'Việt Nam', '', 'Số 3 Tạ Quang Bửu, quận Hai Bà Trưng, Hà Nội', 'Số 3 Tạ Quang Bửu, quận Hai Bà Trưng, Hà Nội', '12/12', 'Cử Nhân', 'Không', 'Không', 'Nội trợ', 'Tại nhà', NULL, '', NULL, NULL, NULL, NULL, NULL, '2019-12-08', 1, NULL, NULL, NULL, NULL, NULL),
(31, NULL, 'Nguyễn Tiến Đạt', '', '1990-09-09', 'Nam', NULL, 'Hải Dương', 'Kinh', 'Thiên chúa giáo', 'Việt Nam', '', 'Số 3 Tạ Quang Bửu, quận Hai Bà Trưng, Hà Nội', 'Số 3 Tạ Quang Bửu, quận Hai Bà Trưng, Hà Nội', '12/12 chính quy', 'Kỹ sư', 'không', 'Anh trình độ C', 'Kỹ sư điện', 'Công ty điện EVN', NULL, '', NULL, NULL, NULL, NULL, NULL, '2019-12-08', 1, NULL, NULL, NULL, NULL, NULL),
(32, NULL, 'Nguyễn Trà My', '', '1997-12-12', 'Nữ', NULL, 'Hải Dương', 'Kinh', 'Thiên chúa giáo', 'Việt Nam', '', 'Số 3 Tạ Quang Bửu, quận Hai Bà Trưng, Hà Nội', 'Số 3 Tạ Quang Bửu, quận Hai Bà Trưng, Hà Nội', '12/12 chính quy', 'Thạc sĩ', 'không', 'Anh trình đố D', 'Luật sư', 'Văn phòng luật sư 123', NULL, '', NULL, NULL, NULL, NULL, NULL, '2019-12-08', 1, NULL, NULL, NULL, NULL, NULL),
(33, NULL, 'Trần Văn Nam', '', '1980-07-09', 'Nam', NULL, 'Hà Nội', 'Kinh', 'Không', 'Việt Nam', '', 'Số 4 Tạ Quang Bửu, quận Hai Bà Trưng, Hà Nội', 'Số 4 Tạ Quang Bửu, quận Hai Bà Trưng, Hà Nội', '12/12 chính quy', 'Tiến sĩ', 'Không', 'Anh trình độ D', 'Giảng viên đại học', 'Đại học Bách khoa Hà Nội', NULL, '', NULL, NULL, NULL, NULL, NULL, '2019-12-08', 1, NULL, NULL, NULL, NULL, NULL),
(34, NULL, 'Nguyễn Minh Tuyết', '', '1985-09-02', 'Nữ', NULL, 'Nam Định', 'Kinh', 'Không', 'Việt Nam', '', 'Số 4 Tạ Quang Bửu, quận Hai Bà Trưng, Hà Nội', 'Số 4 Tạ Quang Bửu, quận Hai Bà Trưng, Hà Nội', '12/12 chính quy', 'Thạc sĩ', 'Không', 'Anh trình độ D', 'Bác sĩ', 'Bệnh viện quốc tế HJK', NULL, '', NULL, NULL, NULL, NULL, NULL, '2019-12-08', 1, NULL, NULL, NULL, NULL, NULL),
(35, NULL, 'Trần Trung Kiên', '', '2008-12-25', 'Nam', NULL, 'Hà Nội', 'Kinh', 'Không', 'Việt Nam', '', 'Số 4 Tạ Quang Bửu, quận Hai Bà Trưng, Hà Nội', '76 Trần Đại Nghĩa', '6/12 chính quy', 'Không', 'Không', 'Không', 'Học sinh', 'Trường THCS Chu Văn An', NULL, '', NULL, NULL, '2023-02-10', 'Li dị', NULL, '2019-12-08', 1, NULL, NULL, NULL, NULL, NULL),
(36, NULL, 'Trần Thúy Ngọc', '', '2013-01-15', 'Nữ', NULL, 'Hà Nội', 'Kinh', 'Không', 'Việt Nam', '', 'Số 4 Tạ Quang Bửu, quận Hai Bà Trưng, Hà Nội', 'Số 4 Tạ Quang Bửu, quận Hai Bà Trưng, Hà Nội', '1/12 chính quy', 'Không', 'Không', 'Không', 'Học sinh', 'Trường tiểu học Chu Văn An', NULL, '', NULL, NULL, NULL, NULL, NULL, '2019-12-08', 1, NULL, NULL, NULL, NULL, NULL),
(37, NULL, 'Lý Văn Công', '', '1945-06-04', 'Nam', NULL, 'Hà Nội', 'Kinh', 'Không', 'Việt Nam', '', 'Số 5 Tạ Quang Bửu, quận Hai Bà Trưng, Hà Nội', 'Số 5 Tạ Quang Bửu, quận Hai Bà Trưng, Hà Nội', '10/12 chính quy', 'Không', 'Không', 'Không', 'Về hưu', 'Không', NULL, '', NULL, NULL, NULL, NULL, NULL, '2019-12-08', 1, NULL, NULL, NULL, NULL, NULL),
(38, NULL, 'Bùi Thị Hà', '', '1948-02-03', 'Nữ', NULL, 'Hải Phòng', 'Kinh', 'Không', 'Việt Nam', '', 'Số 5 Tạ Quang Bửu, quận Hai Bà Trưng, Hà Nội', 'Số 5 Tạ Quang Bửu, quận Hai Bà Trưng, Hà Nội', '10/12', 'Không', 'Không', 'Không', 'Nội trợ', 'Tại nhà', NULL, '', NULL, NULL, NULL, NULL, NULL, '2019-12-08', 1, NULL, NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `phat_qua`
--

CREATE TABLE `phat_qua` (
  `idPhatQua` int(11) NOT NULL,
  `idDotPhat` int(11) DEFAULT NULL,
  `idQua` int(11) DEFAULT NULL,
  `idNhanKhau` int(11) DEFAULT NULL,
  `daDuyet` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `phat_qua`
--

INSERT INTO `phat_qua` (`idPhatQua`, `idDotPhat`, `idQua`, `idNhanKhau`, `daDuyet`) VALUES
(1, 2, 5, 35, 1),
(2, 2, 1, 37, 1),
(3, 3, 5, 35, 0);

-- --------------------------------------------------------

--
-- Table structure for table `phat_thuong`
--

CREATE TABLE `phat_thuong` (
  `idPhatThuong` int(11) NOT NULL,
  `idDotPhat` int(11) NOT NULL,
  `idQua` int(11) NOT NULL,
  `idMinhChung` int(11) NOT NULL,
  `daDuyet` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `phat_thuong`
--

INSERT INTO `phat_thuong` (`idPhatThuong`, `idDotPhat`, `idQua`, `idMinhChung`, `daDuyet`) VALUES
(1, 1, 1, 1, 0),
(2, 1, 1, 2, 1),
(3, 1, 1, 5, 1);

-- --------------------------------------------------------

--
-- Table structure for table `qua`
--

CREATE TABLE `qua` (
  `idQua` int(11) NOT NULL,
  `tenQua` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `moTa` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `giaTri` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `qua`
--

INSERT INTO `qua` (`idQua`, `tenQua`, `moTa`, `giaTri`) VALUES
(1, 'QuaHSG', 'Tien', 100000),
(2, 'QuaHSTT', 'Tien', 50000),
(3, 'QuaHSGH', 'Tien', 150000),
(4, 'QuaHSGT', 'Tien', 300000),
(5, 'QuaTreNho', 'VatPham', 50000);

-- --------------------------------------------------------

--
-- Table structure for table `tam_tru`
--

CREATE TABLE `tam_tru` (
  `ID` int(11) NOT NULL,
  `idNhanKhau` int(11) DEFAULT NULL,
  `maGiayTamtru` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `soDienThoaiNguoiDangKy` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `tuNgay` date NOT NULL,
  `denNgay` date NOT NULL,
  `lyDo` text COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tam_vang`
--

CREATE TABLE `tam_vang` (
  `ID` int(11) NOT NULL,
  `idNhanKhau` int(11) DEFAULT NULL,
  `maGiayTamVang` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `noiTamtru` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `tuNgay` date DEFAULT NULL,
  `denNgay` date DEFAULT NULL,
  `lyDo` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `tam_vang`
--

INSERT INTO `tam_vang` (`ID`, `idNhanKhau`, `maGiayTamVang`, `noiTamtru`, `tuNgay`, `denNgay`, `lyDo`) VALUES
(3, 26, '', '12', '2023-02-02', '2023-02-24', 'ưdqưqd');

-- --------------------------------------------------------

--
-- Table structure for table `thanh_vien_cua_ho`
--

CREATE TABLE `thanh_vien_cua_ho` (
  `idNhanKhau` int(11) NOT NULL,
  `idHoKhau` int(11) NOT NULL,
  `quanHeVoiChuHo` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `thanh_vien_cua_ho`
--

INSERT INTO `thanh_vien_cua_ho` (`idNhanKhau`, `idHoKhau`, `quanHeVoiChuHo`) VALUES
(27, 27, 'Mẹ'),
(29, 15, 'Chồng'),
(30, 27, ''),
(31, 17, 'Con trai'),
(32, 15, ''),
(33, 16, ''),
(35, 18, ''),
(36, 16, 'Mẹ'),
(38, 17, '');

-- --------------------------------------------------------

--
-- Table structure for table `tieu_su`
--

CREATE TABLE `tieu_su` (
  `ID` int(11) NOT NULL,
  `idNhanKhau` int(11) DEFAULT NULL,
  `tuNgay` date DEFAULT NULL,
  `denNgay` date DEFAULT NULL,
  `diaChi` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ngheNghiep` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `noiLamViec` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `tieu_su`
--

INSERT INTO `tieu_su` (`ID`, `idNhanKhau`, `tuNgay`, `denNgay`, `diaChi`, `ngheNghiep`, `noiLamViec`) VALUES
(2, 27, '2015-09-05', '2015-09-05', 'Số 45, ngõ 56, Nguyễn Khang, Cầu Giấy, Hà Nội', 'Sinh Viên', 'Đại học Thương mại'),
(3, 28, '2012-09-05', '2017-09-05', '556 La Thành, Hà Nội', 'Sinh Viên', 'Đại học Bách khoa Hà Nội'),
(4, 29, '1989-05-10', '2000-08-25', 'Số 5 Nguyễn Khuyến, Hà Nội', 'Quản lý nhận sự', 'Công ty BCC'),
(5, 30, '1987-05-23', '1997-03-01', 'Số 3 Tạ Quang Bửu, quận Hai Bà Trưng, Hà Nội', 'Nhân viên văn phòng', 'Công ty Zezs'),
(6, 31, '2008-09-05', '2013-09-05', 'Số 3 Tạ Quang Bửu, quận Hai Bà Trưng, Hà Nội', 'Sinh viên', 'Đại học Bách khoa Hà Nội'),
(7, 32, '2015-09-05', '2019-09-05', 'Số 3 Tạ Quang Bửu, quận Hai Bà Trưng, Hà Nội', 'Sinh viên', 'Đại học Luật Hà Nội'),
(8, 33, '1998-09-05', '2003-09-05', 'Số 66, ngõ 445 Nguyễn Khang, Cầu Giấy, Hà Nội', 'Sinh Vvên', 'Đại học Bách khoa Hà Nội'),
(9, 33, '2003-10-03', '2018-08-06', 'Số 4 Tạ Quang Bửu, quận Hai Bà Trưng, Hà Nội', 'Giảng viên', 'Đại học Công ngiệp Hà Nội'),
(10, 34, '2003-09-05', '2011-09-05', 'Số 8 Tôn Thất Tùng, Hà Nội', 'Sinh viên', 'Đại học Y Hà Nội'),
(11, 34, '2011-10-03', '2015-08-09', 'Số 8 Tôn Thất Tùng, Hà Nội', 'Bác sĩ nội trú', 'Bệnh viện Bạch Mai'),
(12, 37, '1961-01-01', '1963-01-01', 'Không rõ', 'Bộ đội', 'Hà Nội'),
(2, 27, '2015-09-05', '2015-09-05', 'Số 45, ngõ 56, Nguyễn Khang, Cầu Giấy, Hà Nội', 'Sinh Viên', 'Đại học Thương mại'),
(3, 28, '2012-09-05', '2017-09-05', '556 La Thành, Hà Nội', 'Sinh Viên', 'Đại học Bách khoa Hà Nội'),
(4, 29, '1989-05-10', '2000-08-25', 'Số 5 Nguyễn Khuyến, Hà Nội', 'Quản lý nhận sự', 'Công ty BCC'),
(5, 30, '1987-05-23', '1997-03-01', 'Số 3 Tạ Quang Bửu, quận Hai Bà Trưng, Hà Nội', 'Nhân viên văn phòng', 'Công ty Zezs'),
(6, 31, '2008-09-05', '2013-09-05', 'Số 3 Tạ Quang Bửu, quận Hai Bà Trưng, Hà Nội', 'Sinh viên', 'Đại học Bách khoa Hà Nội'),
(7, 32, '2015-09-05', '2019-09-05', 'Số 3 Tạ Quang Bửu, quận Hai Bà Trưng, Hà Nội', 'Sinh viên', 'Đại học Luật Hà Nội'),
(8, 33, '1998-09-05', '2003-09-05', 'Số 66, ngõ 445 Nguyễn Khang, Cầu Giấy, Hà Nội', 'Sinh Vvên', 'Đại học Bách khoa Hà Nội'),
(9, 33, '2003-10-03', '2018-08-06', 'Số 4 Tạ Quang Bửu, quận Hai Bà Trưng, Hà Nội', 'Giảng viên', 'Đại học Công ngiệp Hà Nội'),
(10, 34, '2003-09-05', '2011-09-05', 'Số 8 Tôn Thất Tùng, Hà Nội', 'Sinh viên', 'Đại học Y Hà Nội'),
(11, 34, '2011-10-03', '2015-08-09', 'Số 8 Tôn Thất Tùng, Hà Nội', 'Bác sĩ nội trú', 'Bệnh viện Bạch Mai'),
(12, 37, '1961-01-01', '1963-01-01', 'Không rõ', 'Bộ đội', 'Hà Nội');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `ID` int(11) NOT NULL,
  `userName` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `passwd` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`ID`, `userName`, `passwd`) VALUES
(1, 'admin', '1'),
(2, 'admin1', '1'),
(3, 'admin2', '1');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `chung_minh_thu`
--
ALTER TABLE `chung_minh_thu`
  ADD KEY `chung_minh_thu_ibfk_1` (`idNhanKhau`);

--
-- Indexes for table `dinh_chinh`
--
ALTER TABLE `dinh_chinh`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `idHoKhau` (`idHoKhau`),
  ADD KEY `nguoiThayDoi` (`nguoiThayDoi`);

--
-- Indexes for table `dot_phat`
--
ALTER TABLE `dot_phat`
  ADD PRIMARY KEY (`idDotPhat`);

--
-- Indexes for table `gia_dinh`
--
ALTER TABLE `gia_dinh`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `idNhanKhau` (`idNhanKhau`);

--
-- Indexes for table `ho_khau`
--
ALTER TABLE `ho_khau`
  ADD PRIMARY KEY (`ID`),
  ADD UNIQUE KEY `maHoKhau` (`maHoKhau`),
  ADD KEY `idChuHo` (`idChuHo`);

--
-- Indexes for table `khai_tu`
--
ALTER TABLE `khai_tu`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `idNguoiKhai` (`idNguoiKhai`),
  ADD KEY `idNguoiChet` (`idNguoiChet`);

--
-- Indexes for table `lich_su_thay_doi`
--
ALTER TABLE `lich_su_thay_doi`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `lich_su_thay_doi_ho_khau`
--
ALTER TABLE `lich_su_thay_doi_ho_khau`
  ADD KEY `IdNhanKhau` (`IdNhanKhau`),
  ADD KEY `IdHoKhau` (`IdHoKhau`),
  ADD KEY `IdLSTD` (`IdLSTD`);

--
-- Indexes for table `minh_chung`
--
ALTER TABLE `minh_chung`
  ADD PRIMARY KEY (`idMinhChung`),
  ADD KEY `lien_ket_1` (`ma_nhan_khau`);

--
-- Indexes for table `nhan_khau`
--
ALTER TABLE `nhan_khau`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `idNguoiTao` (`idNguoiTao`),
  ADD KEY `idNguoiXoa` (`idNguoiXoa`);

--
-- Indexes for table `phat_qua`
--
ALTER TABLE `phat_qua`
  ADD PRIMARY KEY (`idPhatQua`),
  ADD KEY `lien_ket_500` (`idNhanKhau`),
  ADD KEY `lien_ket_600` (`idDotPhat`),
  ADD KEY `lien_ket_700` (`idQua`);

--
-- Indexes for table `phat_thuong`
--
ALTER TABLE `phat_thuong`
  ADD PRIMARY KEY (`idPhatThuong`),
  ADD KEY `lien_ket_200` (`idMinhChung`),
  ADD KEY `lien_ket_300` (`idDotPhat`),
  ADD KEY `lien_ket_400` (`idQua`);

--
-- Indexes for table `qua`
--
ALTER TABLE `qua`
  ADD PRIMARY KEY (`idQua`);

--
-- Indexes for table `tam_tru`
--
ALTER TABLE `tam_tru`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `idNhanKhau` (`idNhanKhau`);

--
-- Indexes for table `tam_vang`
--
ALTER TABLE `tam_vang`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `idNhanKhau` (`idNhanKhau`);

--
-- Indexes for table `thanh_vien_cua_ho`
--
ALTER TABLE `thanh_vien_cua_ho`
  ADD PRIMARY KEY (`idNhanKhau`,`idHoKhau`),
  ADD KEY `idHoKhau` (`idHoKhau`);

--
-- Indexes for table `tieu_su`
--
ALTER TABLE `tieu_su`
  ADD KEY `tieu_su_ibfk_1` (`idNhanKhau`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `dinh_chinh`
--
ALTER TABLE `dinh_chinh`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `dot_phat`
--
ALTER TABLE `dot_phat`
  MODIFY `idDotPhat` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `gia_dinh`
--
ALTER TABLE `gia_dinh`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;

--
-- AUTO_INCREMENT for table `ho_khau`
--
ALTER TABLE `ho_khau`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT for table `khai_tu`
--
ALTER TABLE `khai_tu`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `lich_su_thay_doi`
--
ALTER TABLE `lich_su_thay_doi`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `minh_chung`
--
ALTER TABLE `minh_chung`
  MODIFY `idMinhChung` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT for table `nhan_khau`
--
ALTER TABLE `nhan_khau`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=39;

--
-- AUTO_INCREMENT for table `phat_qua`
--
ALTER TABLE `phat_qua`
  MODIFY `idPhatQua` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `phat_thuong`
--
ALTER TABLE `phat_thuong`
  MODIFY `idPhatThuong` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `tam_tru`
--
ALTER TABLE `tam_tru`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `tam_vang`
--
ALTER TABLE `tam_vang`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `chung_minh_thu`
--
ALTER TABLE `chung_minh_thu`
  ADD CONSTRAINT `chung_minh_thu_ibfk_1` FOREIGN KEY (`idNhanKhau`) REFERENCES `nhan_khau` (`ID`);

--
-- Constraints for table `dinh_chinh`
--
ALTER TABLE `dinh_chinh`
  ADD CONSTRAINT `dinh_chinh_ibfk_1` FOREIGN KEY (`idHoKhau`) REFERENCES `ho_khau` (`ID`),
  ADD CONSTRAINT `dinh_chinh_ibfk_2` FOREIGN KEY (`nguoiThayDoi`) REFERENCES `users` (`ID`);

--
-- Constraints for table `gia_dinh`
--
ALTER TABLE `gia_dinh`
  ADD CONSTRAINT `gia_dinh_ibfk_1` FOREIGN KEY (`idNhanKhau`) REFERENCES `nhan_khau` (`ID`);

--
-- Constraints for table `ho_khau`
--
ALTER TABLE `ho_khau`
  ADD CONSTRAINT `ho_khau_ibfk_1` FOREIGN KEY (`idChuHo`) REFERENCES `nhan_khau` (`ID`);

--
-- Constraints for table `khai_tu`
--
ALTER TABLE `khai_tu`
  ADD CONSTRAINT `khai_tu_ibfk_1` FOREIGN KEY (`idNguoiKhai`) REFERENCES `nhan_khau` (`ID`),
  ADD CONSTRAINT `khai_tu_ibfk_2` FOREIGN KEY (`idNguoiChet`) REFERENCES `nhan_khau` (`ID`);

--
-- Constraints for table `lich_su_thay_doi_ho_khau`
--
ALTER TABLE `lich_su_thay_doi_ho_khau`
  ADD CONSTRAINT `lich_su_thay_doi_ho_khau_ibfk_1` FOREIGN KEY (`IdNhanKhau`) REFERENCES `nhan_khau` (`ID`),
  ADD CONSTRAINT `lich_su_thay_doi_ho_khau_ibfk_2` FOREIGN KEY (`IdHoKhau`) REFERENCES `ho_khau` (`ID`),
  ADD CONSTRAINT `lich_su_thay_doi_ho_khau_ibfk_3` FOREIGN KEY (`IdLSTD`) REFERENCES `lich_su_thay_doi` (`ID`);

--
-- Constraints for table `minh_chung`
--
ALTER TABLE `minh_chung`
  ADD CONSTRAINT `lien_ket_1` FOREIGN KEY (`ma_nhan_khau`) REFERENCES `nhan_khau` (`ID`);

--
-- Constraints for table `nhan_khau`
--
ALTER TABLE `nhan_khau`
  ADD CONSTRAINT `nhan_khau_ibfk_1` FOREIGN KEY (`idNguoiTao`) REFERENCES `users` (`ID`),
  ADD CONSTRAINT `nhan_khau_ibfk_2` FOREIGN KEY (`idNguoiXoa`) REFERENCES `users` (`ID`);

--
-- Constraints for table `phat_qua`
--
ALTER TABLE `phat_qua`
  ADD CONSTRAINT `lien_ket_500` FOREIGN KEY (`idNhanKhau`) REFERENCES `nhan_khau` (`ID`),
  ADD CONSTRAINT `lien_ket_600` FOREIGN KEY (`idDotPhat`) REFERENCES `dot_phat` (`idDotPhat`),
  ADD CONSTRAINT `lien_ket_700` FOREIGN KEY (`idQua`) REFERENCES `qua` (`idQua`);

--
-- Constraints for table `phat_thuong`
--
ALTER TABLE `phat_thuong`
  ADD CONSTRAINT `lien_ket_200` FOREIGN KEY (`idMinhChung`) REFERENCES `minh_chung` (`idMinhChung`),
  ADD CONSTRAINT `lien_ket_300` FOREIGN KEY (`idDotPhat`) REFERENCES `dot_phat` (`idDotPhat`),
  ADD CONSTRAINT `lien_ket_400` FOREIGN KEY (`idQua`) REFERENCES `qua` (`idQua`);

--
-- Constraints for table `tam_tru`
--
ALTER TABLE `tam_tru`
  ADD CONSTRAINT `tam_tru_ibfk_1` FOREIGN KEY (`idNhanKhau`) REFERENCES `nhan_khau` (`ID`);

--
-- Constraints for table `tam_vang`
--
ALTER TABLE `tam_vang`
  ADD CONSTRAINT `tam_vang_ibfk_1` FOREIGN KEY (`idNhanKhau`) REFERENCES `nhan_khau` (`ID`);

--
-- Constraints for table `thanh_vien_cua_ho`
--
ALTER TABLE `thanh_vien_cua_ho`
  ADD CONSTRAINT `thanh_vien_cua_ho_ibfk_1` FOREIGN KEY (`idNhanKhau`) REFERENCES `nhan_khau` (`ID`),
  ADD CONSTRAINT `thanh_vien_cua_ho_ibfk_2` FOREIGN KEY (`idHoKhau`) REFERENCES `ho_khau` (`ID`);

--
-- Constraints for table `tieu_su`
--
ALTER TABLE `tieu_su`
  ADD CONSTRAINT `tieu_su_ibfk_1` FOREIGN KEY (`idNhanKhau`) REFERENCES `nhan_khau` (`ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
