/*
Navicat MySQL Data Transfer

Source Server         : test
Source Server Version : 50553
Source Host           : localhost:3306
Source Database       : iotlab

Target Server Type    : MYSQL
Target Server Version : 50553
File Encoding         : 65001

Date: 2021-06-22 17:00:40
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for cert
-- ----------------------------
DROP TABLE IF EXISTS `cert`;
CREATE TABLE `cert` (
  `cert` varchar(255) NOT NULL,
  `authoruser` varchar(255) NOT NULL,
  `authorizeduser` varchar(255) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `accesstype` int(255) NOT NULL,
  `opeart_k` bigint(255) DEFAULT NULL,
  `aes_key` varchar(255) DEFAULT NULL,
  `rsa_key1` varchar(255) DEFAULT NULL,
  `rsa_key2` varchar(255) DEFAULT NULL,
  `cescmc_k` int(255) DEFAULT NULL,
  `cescmc_n` int(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=140 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cert
-- ----------------------------
INSERT INTO `cert` VALUES ('', '123', '1234', '1', '0', null, null, null, null, null, null);
INSERT INTO `cert` VALUES ('', '123', '1234555', '17', '0', null, null, null, null, null, null);
INSERT INTO `cert` VALUES ('', '123', '123455', '18', '1', null, null, null, null, null, null);
INSERT INTO `cert` VALUES ('', 'super_admin', 'super1', '138', '1', null, null, null, null, null, null);
INSERT INTO `cert` VALUES ('', 'super_admin', 'admin', '137', '0', null, null, null, null, null, null);
INSERT INTO `cert` VALUES ('', 'admin', 'www', '136', '0', null, null, null, null, null, null);
INSERT INTO `cert` VALUES ('', 'admin', '111', '135', '0', null, null, null, null, null, null);
INSERT INTO `cert` VALUES ('', 'admin', 'super333', '134', '1', null, null, null, null, null, null);
INSERT INTO `cert` VALUES ('', 'admin', 'super222', '133', '0', null, null, null, null, null, null);
INSERT INTO `cert` VALUES ('', 'admin', 'super10', '132', '0', null, null, null, null, null, null);
INSERT INTO `cert` VALUES ('', 'admin', 'super5', '131', '1', null, null, null, null, null, null);
INSERT INTO `cert` VALUES ('', 'admin', 'super2', '130', '1', null, null, null, null, null, null);
INSERT INTO `cert` VALUES ('', 'admin', 'super', '129', '0', null, null, null, null, null, null);
INSERT INTO `cert` VALUES ('ﾖￛ￦ﾛ#ﾮￛeﾦﾍcv ￼W$ﾾsﾑy￐ﾀﾹ�ﾞﾰy￥ﾚ/&￹ﾗ￡￴*ﾶ￢￣Rﾤ￁￯Rﾄﾖbￆﾈ~ￒﾔ￹ﾳ￫', 'admin', 'ssssssl', '139', '0', '2568941234', '123456', '123456', '123456', '1', '1');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', '123456', 'admin', 'https://avatars0.githubusercontent.com/u/20942571?s=460&v=4');
INSERT INTO `user` VALUES ('2', 'super_admin', '123', 'super_admin', 'https://avatars0.githubusercontent.com/u/20942571?s=460&v=4');
 INTO `cert` VALUES ('ﾖￛ￦ﾛ#ﾮￛeﾦﾍcv ￼W$ﾾsﾑy￐ﾀﾹ�ﾞﾰy￥ﾚ/&￹ﾗ￡￴*ﾶ￢￣Rﾤ￁￯Rﾄﾖbￆﾈ~ￒﾔ￹ﾳ￫', 'admin', 'ssssssl', '139', '0', '2568941234', '123456', '123456', '123456', '1', '1');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------