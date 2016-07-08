/*
Navicat MySQL Data Transfer

Source Server         : MariaDB-aliyun
Source Server Version : 50544
Source Host           : 121.42.197.31:3306
Source Database       : SkyDrive

Target Server Type    : MYSQL
Target Server Version : 50544
File Encoding         : 65001

Date: 2016-07-08 13:30:26
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for Files
-- ----------------------------
DROP TABLE IF EXISTS `Files`;
CREATE TABLE `Files` (
  `FileNo` int(11) NOT NULL AUTO_INCREMENT,
  `FileName` varchar(100) DEFAULT NULL,
  `UserNo` int(11) DEFAULT NULL,
  `FileFormat` varchar(10) DEFAULT NULL,
  `FileType` int(11) DEFAULT NULL,
  `FileMD5` varchar(40) DEFAULT NULL,
  `SupFolder` int(11) DEFAULT NULL,
  `FilePath` varchar(150) DEFAULT NULL,
  `DownloadPath` varchar(150) DEFAULT NULL,
  `CreatTime` datetime DEFAULT NULL,
  `FileStatus` int(11) DEFAULT NULL,
  `ShareStatus` int(11) DEFAULT NULL,
  PRIMARY KEY (`FileNo`)
) ENGINE=InnoDB AUTO_INCREMENT=189 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of Files
-- ----------------------------
INSERT INTO `Files` VALUES ('150', '我的文档', '1', null, '0', null, '0', '../1', null, '2016-07-06 00:00:00', '1', null);
INSERT INTO `Files` VALUES ('151', '测试', '1', null, '0', null, '0', '../1', null, '2016-07-06 00:00:00', '1', null);
INSERT INTO `Files` VALUES ('152', '合理怀疑BD', '1', 'rmvb', '1', '6bd7a42821583f71b684d1bea3315565', '150', '../1/150', '/upload/6bd7a42821583f71b684d1bea3315565.rmvb', '2016-07-06 00:00:00', '1', '0');
INSERT INTO `Files` VALUES ('153', '示例文档', '1', 'docx', '1', 'e21f97e9fcaddc97d5d266264b1b03ae', '150', '../1/150', '/upload/e21f97e9fcaddc97d5d266264b1b03ae.docx', '2016-07-06 00:00:00', '1', '0');
INSERT INTO `Files` VALUES ('154', '示例图片', '1', 'jpg', '1', 'db3a9afc3ed7313845be58c07a50d5e5', '150', '../1/150', '/upload/db3a9afc3ed7313845be58c07a50d5e5.jpg', '2016-07-06 00:00:00', '1', '0');
INSERT INTO `Files` VALUES ('155', '示例音乐1', '1', 'mp3', '1', '6e07b6cb100ec7acfdcca580a7b42999', '150', '../1/150', '/upload/6e07b6cb100ec7acfdcca580a7b42999.mp3', '2016-07-06 00:00:00', '1', '0');
INSERT INTO `Files` VALUES ('156', '示例音乐1', '1', 'mp3', '1', '6e07b6cb100ec7acfdcca580a7b42999', '0', '../1', '/upload/6e07b6cb100ec7acfdcca580a7b42999.mp3', '2016-07-06 00:00:00', '1', '0');
INSERT INTO `Files` VALUES ('157', '示例音乐2', '1', 'mp3', '1', 'bf7fb181e616fda3aaf74f6441299177', '0', '../1', '/upload/bf7fb181e616fda3aaf74f6441299177.mp3', '2016-07-06 00:00:00', '1', '0');
INSERT INTO `Files` VALUES ('158', '指南', '1', 'pdf', '1', '63297013520fdbe22bb4bd0d41867255', '0', '../1', '/upload/63297013520fdbe22bb4bd0d41867255.pdf', '2016-07-06 00:00:00', '1', '0');
INSERT INTO `Files` VALUES ('159', '啊啊', '1', 'xlsx', '1', 'f5503cf25846252d945bba4c18ed7398', '0', '../1', '/upload/f5503cf25846252d945bba4c18ed7398.xlsx', '2016-07-06 00:00:00', '0', '0');
INSERT INTO `Files` VALUES ('160', '示例图片', '1', 'jpg', '1', 'db3a9afc3ed7313845be58c07a50d5e5', '0', '../1', '/upload/db3a9afc3ed7313845be58c07a50d5e5.jpg', '2016-07-06 00:00:00', '1', '0');
INSERT INTO `Files` VALUES ('161', '示例表格', '1', 'xlsx', '1', 'f5503cf25846252d945bba4c18ed7398', '0', '../1', '/upload/f5503cf25846252d945bba4c18ed7398.xlsx', '2016-07-06 00:00:00', '1', '0');
INSERT INTO `Files` VALUES ('162', '独家记忆', '1', 'mp3', '1', '41def0ca717521b2918bbbcae15166fe', '151', '../1/151', '/upload/41def0ca717521b2918bbbcae15166fe.mp3', '2016-07-06 00:00:00', '0', '0');
INSERT INTO `Files` VALUES ('163', '示例音乐1', '1', 'mp3', '1', '6e07b6cb100ec7acfdcca580a7b42999', '151', '../1/151', '/upload/6e07b6cb100ec7acfdcca580a7b42999.mp3', '2016-07-06 00:00:00', '1', '0');
INSERT INTO `Files` VALUES ('164', '啊啊', '1', 'jpg', '1', 'db3a9afc3ed7313845be58c07a50d5e5', '151', '../1/151', '/upload/db3a9afc3ed7313845be58c07a50d5e5.jpg', '2016-07-06 00:00:00', '1', '1');
INSERT INTO `Files` VALUES ('165', 'aa', '1', null, '0', null, '0', '../1', null, '2016-07-06 00:00:00', '0', null);
INSERT INTO `Files` VALUES ('166', '示例文档', '1', 'docx', '1', 'e21f97e9fcaddc97d5d266264b1b03ae', '165', '../1/165', '/upload/e21f97e9fcaddc97d5d266264b1b03ae.docx', '2016-07-06 00:00:00', '0', '0');
INSERT INTO `Files` VALUES ('167', '合理怀疑BD', '1', 'rmvb', '1', '6bd7a42821583f71b684d1bea3315565', '151', '../1/151', '/upload/6bd7a42821583f71b684d1bea3315565.rmvb', '2016-07-06 00:00:00', '1', '0');
INSERT INTO `Files` VALUES ('168', '示例TEXT', '1', 'txt', '1', '6bb066b493a3f3a803c3ef170017b7ab', '151', '../1/151', '/upload/6bb066b493a3f3a803c3ef170017b7ab.txt', '2016-07-06 00:00:00', '0', '0');
INSERT INTO `Files` VALUES ('169', '指南', '1', 'pdf', '1', '63297013520fdbe22bb4bd0d41867255', '151', '../1/151', '/upload/63297013520fdbe22bb4bd0d41867255.pdf', '2016-07-06 00:00:00', '0', '0');
INSERT INTO `Files` VALUES ('187', '新建文件夹', '1', null, '0', null, '0', '../1', null, '2016-07-06 00:00:00', '0', null);
INSERT INTO `Files` VALUES ('188', '示例图片', '1', 'jpg', '1', 'db3a9afc3ed7313845be58c07a50d5e5', '187', '../1/187', '/upload/db3a9afc3ed7313845be58c07a50d5e5.jpg', '2016-07-06 00:00:00', '0', '0');

-- ----------------------------
-- Table structure for User
-- ----------------------------
DROP TABLE IF EXISTS `User`;
CREATE TABLE `User` (
  `UserNo` int(11) NOT NULL AUTO_INCREMENT,
  `UserName` varchar(20) DEFAULT NULL,
  `UserPwd` varchar(20) DEFAULT NULL,
  `UserStatus` int(11) DEFAULT NULL,
  PRIMARY KEY (`UserNo`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of User
-- ----------------------------
INSERT INTO `User` VALUES ('1', 'aaa', '1234', null);
