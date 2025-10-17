/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 80406 (8.4.6)
 Source Host           : localhost:3306
 Source Schema         : web1

 Target Server Type    : MySQL
 Target Server Version : 80406 (8.4.6)
 File Encoding         : 65001

 Date: 17/10/2025 16:38:49
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for office
-- ----------------------------
DROP TABLE IF EXISTS `office`;
CREATE TABLE `office`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '科室',
  `description` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '说明',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '科室表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of office
-- ----------------------------
INSERT INTO `office` VALUES (1, '发热门诊', '发热');
INSERT INTO `office` VALUES (2, '儿科', '儿科');
INSERT INTO `office` VALUES (3, '骨科', '骨科');
INSERT INTO `office` VALUES (4, '肛肠外科', '肛肠外科');
INSERT INTO `office` VALUES (5, '呼吸内科', '呼吸内科');
INSERT INTO `office` VALUES (6, '普通外科', '普通外科');

-- ----------------------------
-- Table structure for physician
-- ----------------------------
DROP TABLE IF EXISTS `physician`;
CREATE TABLE `physician`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '账号',
  `password` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `age` int NULL DEFAULT NULL COMMENT '年龄',
  `gender` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性别【F:女;M:男】',
  `positionId` int NULL DEFAULT NULL,
  `officeId` int UNSIGNED NOT NULL COMMENT '科室ID',
  `deletedAt` datetime NULL DEFAULT NULL COMMENT '移除于',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `serve` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '在职【Y:在职;N:离职】',
  `registeredAt` datetime NULL DEFAULT NULL COMMENT '注册于',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '医师表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of physician
-- ----------------------------
INSERT INTO `physician` VALUES (1, 'user1', '123', '尤雨溪', 19, '男', 10, 5, NULL, '12888888888', '在职', NULL);
INSERT INTO `physician` VALUES (2, 'user2', NULL, '王进喜', 46, '男', 9, 4, NULL, '18778995678', '离职', NULL);
INSERT INTO `physician` VALUES (3, 'user3', NULL, '王乐', 67, '男', 0, 3, NULL, '15729009999', '在职', NULL);
INSERT INTO `physician` VALUES (4, 'user4', NULL, '李商英', 29, '女', 0, 5, NULL, '17833456788', '离职', NULL);
INSERT INTO `physician` VALUES (5, 'user5', '113', '莫奇权', 45, '男', 0, 1, NULL, '18926789546', '在职', NULL);
INSERT INTO `physician` VALUES (8, 'user6', '{bcrypt}$2a$10$RTyM8j/k8446VHqq0FvRTeyB1xu9uORK8eWaJER7zNL1x2hDWeN6W', '重明', 26, '男', 0, 6, NULL, '18867769990', '在职', '2025-09-30 21:07:26');
INSERT INTO `physician` VALUES (9, 'user7', '{bcrypt}$2a$10$c7xFTFdSdZ8mxivqWoZw9u32v1n6CoL.TEtrY/IlOvpVK4E7mTryi', '伊宁', 20, '女', 11, 1, NULL, '17878966667', '在职', '2025-10-02 22:06:52');
INSERT INTO `physician` VALUES (11, 'user8', '{bcrypt}$2a$10$hlUImHnGhWKDqPR6.X2vy.t.yEURxNpnyHNvqK0Pznq0YIgbHq/3a', '向阳', 57, '男', NULL, 1, NULL, '17685982228', '在职', '2025-10-13 14:57:53');

-- ----------------------------
-- Table structure for physician_position
-- ----------------------------
DROP TABLE IF EXISTS `physician_position`;
CREATE TABLE `physician_position`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `physicianId` int UNSIGNED NOT NULL COMMENT '医师ID',
  `positionId` int UNSIGNED NOT NULL COMMENT '职位ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 114 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '医师职位表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of physician_position
-- ----------------------------
INSERT INTO `physician_position` VALUES (28, 8, 11);
INSERT INTO `physician_position` VALUES (49, 9, 10);
INSERT INTO `physician_position` VALUES (68, 4, 10);
INSERT INTO `physician_position` VALUES (69, 4, 11);
INSERT INTO `physician_position` VALUES (70, 3, 9);
INSERT INTO `physician_position` VALUES (71, 3, 10);
INSERT INTO `physician_position` VALUES (72, 5, 10);
INSERT INTO `physician_position` VALUES (73, 5, 11);
INSERT INTO `physician_position` VALUES (74, 2, 9);
INSERT INTO `physician_position` VALUES (75, 2, 10);
INSERT INTO `physician_position` VALUES (76, 2, 11);
INSERT INTO `physician_position` VALUES (77, 2, 12);
INSERT INTO `physician_position` VALUES (109, 1, 8);
INSERT INTO `physician_position` VALUES (110, 1, 9);
INSERT INTO `physician_position` VALUES (111, 1, 10);
INSERT INTO `physician_position` VALUES (112, 1, 11);
INSERT INTO `physician_position` VALUES (113, 1, 12);

-- ----------------------------
-- Table structure for position
-- ----------------------------
DROP TABLE IF EXISTS `position`;
CREATE TABLE `position`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '职位',
  `description` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '说明',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '职位表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of position
-- ----------------------------
INSERT INTO `position` VALUES (8, '主治医师', '12');
INSERT INTO `position` VALUES (9, '主任医师', '12');
INSERT INTO `position` VALUES (10, '副主任医师', '23');
INSERT INTO `position` VALUES (11, '住院医师', '12');
INSERT INTO `position` VALUES (12, '医士', '33');

-- ----------------------------
-- Table structure for pysician_office
-- ----------------------------
DROP TABLE IF EXISTS `pysician_office`;
CREATE TABLE `pysician_office`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `physicianId` int UNSIGNED NOT NULL COMMENT '医师ID',
  `officeId` int UNSIGNED NOT NULL COMMENT '科室ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '医师科室表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pysician_office
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `name` datetime NULL DEFAULT NULL COMMENT '姓名',
  `password` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像路径',
  `deletedAt` datetime NULL DEFAULT NULL COMMENT '移除于',
  `registeredAt` datetime NULL DEFAULT NULL COMMENT '注册于',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (20, '123', NULL, '123456', '/images/98c247fe-ea2d-4fbc-af4b-c9be7b95adeb黑色背景.png.png', NULL, '2025-10-17 16:37:25');
INSERT INTO `user` VALUES (21, 'admin', NULL, '123456', '/images/1d33421b-e0de-4ab6-87fa-74579762a803雪花.png.png', NULL, '2025-10-17 16:37:57');

SET FOREIGN_KEY_CHECKS = 1;
