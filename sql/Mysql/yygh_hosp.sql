/*
 Navicat Premium Data Transfer

 Source Server         : Mysql
 Source Server Type    : MySQL
 Source Server Version : 50731
 Source Host           : localhost:3306
 Source Schema         : yygh_hosp

 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : 65001

 Date: 08/09/2023 15:45:25
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for hospital_set
-- ----------------------------
DROP TABLE IF EXISTS `hospital_set`;
CREATE TABLE `hospital_set`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `hosname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '医院名称',
  `hoscode` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '医院编号',
  `api_url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'api基础路径',
  `sign_key` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '签名秘钥',
  `contacts_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系人',
  `contacts_phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系人手机',
  `status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '状态(1:可用，0:不可用)',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_hoscode`(`hoscode`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '医院设置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of hospital_set
-- ----------------------------
INSERT INTO `hospital_set` VALUES (10, '北京协和医院', '1000_0', 'http://localhost:8201', '999', '测试', '120', 1, '2022-09-06 07:56:10', '2023-08-20 00:43:53', 0);
INSERT INTO `hospital_set` VALUES (11, '中南湘雅附二医院', '1000_1', '', '8d33c9d3ef1e1dd0adfa909dec1fc3bd', 'test', '15674815919', 1, '2023-08-11 17:11:07', '2023-08-20 15:49:01', 1);
INSERT INTO `hospital_set` VALUES (12, '中南湘雅附三医院', '1000_2', NULL, 'sjfaohf', 'test', '13874732143', 1, '2023-08-14 16:11:40', '2023-08-20 15:49:01', 1);
INSERT INTO `hospital_set` VALUES (13, '中国人民解放军总医院', '1000_3', NULL, 'sfafa', '军医院', '10010', 1, '2023-08-15 10:28:18', '2023-08-15 10:58:13', 0);
INSERT INTO `hospital_set` VALUES (14, '济南华夏医院', '1000_4', 'sfaf', 'fasfanhlfna', '华夏2', '119', 1, '2023-08-15 10:28:45', '2023-08-15 10:58:13', 0);
INSERT INTO `hospital_set` VALUES (15, '医院', '1000_5', NULL, 'fsasuofw', 'abc', '19919', 1, '2023-08-15 10:29:23', '2023-09-08 15:38:23', 0);
INSERT INTO `hospital_set` VALUES (17, 'sdfasf', 'wfwaf', 'safa', '9fd59443ad200739a387ef9f2e86e604', 'afsa', 'sf234234', 1, '2023-08-15 15:41:44', '2023-08-21 17:42:06', 1);
INSERT INTO `hospital_set` VALUES (18, '胡总', '1000_8', NULL, '3914eabe2ad05be7935f58085928170e', 'abc', '15674815919', 1, '2023-08-15 16:01:09', '2023-09-08 15:38:26', 0);
INSERT INTO `hospital_set` VALUES (19, '熊出没诊所', '100', 'http://localhost', 'd667f359571255c10ab8bd75886a3c1e', '熊大熊二', '110', 1, '2023-08-15 17:51:34', '2023-08-15 17:51:34', 0);

SET FOREIGN_KEY_CHECKS = 1;
