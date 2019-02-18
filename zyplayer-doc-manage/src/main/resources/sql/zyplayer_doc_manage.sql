/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80012
 Source Host           : localhost:3306
 Source Schema         : zyplayer_doc_manage

 Target Server Type    : MySQL
 Target Server Version : 80012
 File Encoding         : 65001

 Date: 22/12/2018 22:58:00
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for auth_info
-- ----------------------------
DROP TABLE IF EXISTS `auth_info`;
CREATE TABLE `auth_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
  `auth_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限名',
  `auth_desc` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限说明',
  `can_edit` tinyint(4) NULL DEFAULT 1 COMMENT '是否可编辑 0=否 1=是',
  `create_uid` bigint(20) NULL DEFAULT NULL COMMENT '创建人用户ID',
  `creation_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '权限信息表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of auth_info
-- ----------------------------
INSERT INTO `auth_info` VALUES (1, 'DOC_ALL', '文档查看权', 0, 1, '2018-12-01 11:40:42');
INSERT INTO `auth_info` VALUES (2, 'AUTH_MANAGE', '权限管理权', 0, 1, '2018-12-01 11:40:42');
INSERT INTO `auth_info` VALUES (3, 'AUTH_ASSIGN', '权限分配权', 0, 1, '2018-12-01 11:40:42');
INSERT INTO `auth_info` VALUES (4, 'USER_MANAGE', '用户管理权', 0, 1, '2018-12-01 11:40:42');

-- ----------------------------
-- Table structure for user_auth
-- ----------------------------
DROP TABLE IF EXISTS `user_auth`;
CREATE TABLE `user_auth`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户ID',
  `auth_id` bigint(20) NULL DEFAULT NULL COMMENT '权限ID',
  `create_uid` bigint(20) NULL DEFAULT NULL COMMENT '创建用户ID',
  `update_uid` bigint(20) NULL DEFAULT NULL COMMENT '更新用户ID',
  `del_flag` tinyint(4) NULL DEFAULT 0 COMMENT '是否删除 0=未删除 1=已删除',
  `creation_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户权限表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user_auth
-- ----------------------------
INSERT INTO `user_auth` VALUES (9, 2, 1, 1, NULL, 0, '2018-12-15 22:19:59', NULL);
INSERT INTO `user_auth` VALUES (10, 2, 2, 1, NULL, 0, '2018-12-15 22:19:59', NULL);
INSERT INTO `user_auth` VALUES (11, 2, 3, 1, NULL, 0, '2018-12-15 22:19:59', NULL);
INSERT INTO `user_auth` VALUES (12, 2, 4, 1, NULL, 0, '2018-12-15 22:19:59', NULL);
INSERT INTO `user_auth` VALUES (13, 3, 1, 1, NULL, 0, '2018-12-15 22:19:59', NULL);
INSERT INTO `user_auth` VALUES (14, 3, 2, 1, NULL, 0, '2018-12-15 22:19:59', NULL);
INSERT INTO `user_auth` VALUES (15, 3, 3, 1, NULL, 0, '2018-12-15 22:19:59', NULL);
INSERT INTO `user_auth` VALUES (16, 3, 4, 1, NULL, 0, '2018-12-15 22:19:59', NULL);
INSERT INTO `user_auth` VALUES (24, 1, 1, 1, NULL, 0, '2018-12-16 21:41:01', NULL);
INSERT INTO `user_auth` VALUES (25, 1, 2, 1, NULL, 0, '2018-12-16 21:41:01', NULL);
INSERT INTO `user_auth` VALUES (26, 1, 3, 1, NULL, 0, '2018-12-16 21:41:01', NULL);
INSERT INTO `user_auth` VALUES (27, 1, 4, 1, NULL, 0, '2018-12-16 21:41:01', NULL);

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
  `user_no` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户编号，用于登录等',
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `avatar` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `del_flag` tinyint(4) NULL DEFAULT 0 COMMENT '是否删除 0=未删除 1=已删除',
  `creation_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_uid` bigint(20) NULL DEFAULT NULL COMMENT '创建人用户ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_userNo`(`user_no`) USING BTREE COMMENT '登录用户名'
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户信息表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES (1, 'zyplayer', 'e10adc3949ba59abbe56e057f20f883e', '暮光：城中城', '806783409@qq.com', NULL, 0, '2018-12-01 11:37:39', NULL, '2018-12-15 20:32:08');
INSERT INTO `user_info` VALUES (2, '1', NULL, '1111', NULL, NULL, 0, '2018-12-15 20:16:10', 1, '2018-12-15 20:19:50');
INSERT INTO `user_info` VALUES (3, '2', NULL, '11', '11', NULL, 0, '2018-12-15 20:21:24', 1, NULL);

-- ----------------------------
-- Table structure for zyplayer_storage
-- ----------------------------
DROP TABLE IF EXISTS `zyplayer_storage`;
CREATE TABLE `zyplayer_storage`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
  `doc_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参数名字',
  `doc_value` mediumtext COMMENT '参数值',
  `creation_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `key`(`doc_key`) USING BTREE COMMENT 'key唯一索引'
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '存储网页上相关的数据' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of zyplayer_storage
-- ----------------------------
SET FOREIGN_KEY_CHECKS = 1;
