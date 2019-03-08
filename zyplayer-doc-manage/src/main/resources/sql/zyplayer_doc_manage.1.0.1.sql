/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50725
 Source Host           : localhost:3306
 Source Schema         : zyplayer_doc_manage

 Target Server Type    : MySQL
 Target Server Version : 50725
 File Encoding         : 65001

 Date: 08/03/2019 22:38:59
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
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户信息表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES (1, 'zyplayer', 'e10adc3949ba59abbe56e057f20f883e', '暮光：城中城', '806783409@qq.com', NULL, 0, '2018-12-01 11:37:39', NULL, '2018-12-15 20:32:08');
INSERT INTO `user_info` VALUES (2, '1', 'e10adc3949ba59abbe56e057f20f883e', '1111', NULL, NULL, 0, '2018-12-15 20:16:10', 1, '2018-12-15 20:19:50');

-- ----------------------------
-- Table structure for wiki_page
-- ----------------------------
DROP TABLE IF EXISTS `wiki_page`;
CREATE TABLE `wiki_page`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
  `space_id` bigint(20) NULL DEFAULT NULL COMMENT '空间ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名字',
  `parent_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '父ID',
  `node_type` tinyint(4) NULL DEFAULT 0 COMMENT '节点类型 0=有子节点 1=终节点',
  `zan_num` int(11) NOT NULL DEFAULT 0 COMMENT '赞的数量',
  `create_user_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人ID',
  `create_user_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人名字',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint(20) NULL DEFAULT NULL COMMENT '修改人ID',
  `update_user_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名字',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(4) NOT NULL DEFAULT 0 COMMENT '0=有效 1=删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wiki_page
-- ----------------------------
INSERT INTO `wiki_page` VALUES (7, 1, '测试1', 0, 0, 1, 1, NULL, '2019-02-24 17:51:56', 1, NULL, '2019-02-24 17:51:56', 0);
INSERT INTO `wiki_page` VALUES (8, 1, '测试2', 0, 0, 4, 1, NULL, '2019-02-24 17:51:56', 1, '暮光：城中城', '2019-03-08 22:12:36', 0);
INSERT INTO `wiki_page` VALUES (9, 1, '测试3', 7, 0, 0, 1, NULL, '2019-02-24 17:51:56', 1, '暮光：城中城', '2019-03-08 22:12:51', 0);
INSERT INTO `wiki_page` VALUES (10, 1, '测试4', 7, 0, 0, 1, NULL, '2019-02-24 17:51:56', 1, NULL, '2019-02-24 17:51:56', 0);
INSERT INTO `wiki_page` VALUES (11, 1, '测试5', 9, 0, 0, 1, NULL, '2019-02-24 17:51:56', 1, NULL, '2019-02-24 17:51:56', 0);
INSERT INTO `wiki_page` VALUES (12, 1, '测试6', 7, 0, 0, 1, NULL, '2019-02-24 17:51:56', 1, NULL, '2019-02-24 17:51:56', 0);
INSERT INTO `wiki_page` VALUES (14, 1, '测试7', 0, 0, 2, 1, NULL, '2019-02-24 17:51:56', 1, NULL, '2019-02-24 17:51:56', 0);
INSERT INTO `wiki_page` VALUES (17, 2, '数据库团队介绍', 0, 0, 0, 1, '暮光：城中城', '2019-03-07 22:07:10', NULL, NULL, NULL, 0);
INSERT INTO `wiki_page` VALUES (18, 2, '人员列表', 17, 0, 1, 1, '暮光：城中城', '2019-03-07 22:28:47', NULL, NULL, NULL, 0);

-- ----------------------------
-- Table structure for wiki_page_comment
-- ----------------------------
DROP TABLE IF EXISTS `wiki_page_comment`;
CREATE TABLE `wiki_page_comment`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
  `page_id` bigint(20) NULL DEFAULT NULL COMMENT '页面ID',
  `parent_id` bigint(20) NULL DEFAULT NULL COMMENT '父评论ID',
  `content` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '评论内容',
  `zan_num` int(11) NOT NULL DEFAULT 0 COMMENT '赞的数量',
  `create_user_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人ID',
  `create_user_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人名字',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `del_flag` tinyint(4) NULL DEFAULT 0 COMMENT '0=有效 1=删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wiki_page_comment
-- ----------------------------
INSERT INTO `wiki_page_comment` VALUES (11, 14, NULL, '阿斯顿撒多撒多撒多撒大所多', 0, 1, '暮光：城中城', '2019-03-07 10:47:05', 0);
INSERT INTO `wiki_page_comment` VALUES (12, 14, NULL, '阿斯顿撒多撒多撒多撒大所多阿斯顿撒多撒多撒多撒大所多阿斯顿撒多撒多撒多撒大所多', 0, 1, '暮光：城中城', '2019-03-07 12:53:07', 0);
INSERT INTO `wiki_page_comment` VALUES (13, 14, 12, '大声道撒大所大所', 0, 1, '暮光：城中城', '2019-03-07 13:01:49', 0);
INSERT INTO `wiki_page_comment` VALUES (14, 14, NULL, '大声道撒大所大所\n大声道撒大所大所\n大声道撒大所大所', 0, 1, '暮光：城中城', '2019-03-07 13:11:02', 0);
INSERT INTO `wiki_page_comment` VALUES (15, 14, 12, '大声道撒大所大所\n大声道撒大所大所\n大声道撒大所大所', 0, 1, '暮光：城中城', '2019-03-07 13:11:12', 0);
INSERT INTO `wiki_page_comment` VALUES (16, 14, 12, '阿斯顿撒多撒多撒多撒大所多阿斯顿撒多撒多撒多撒大所多阿斯顿撒多撒多撒多撒大所多阿斯顿撒多撒多撒多撒大所多阿斯顿撒多撒多撒多撒大所多阿斯顿撒多撒多撒多撒大所多阿斯顿撒多撒多撒多撒大所多阿斯顿撒多撒多撒多撒大所多阿斯顿撒多撒多撒多撒大所多', 0, 1, '暮光：城中城', '2019-03-07 13:11:27', 0);

-- ----------------------------
-- Table structure for wiki_page_content
-- ----------------------------
DROP TABLE IF EXISTS `wiki_page_content`;
CREATE TABLE `wiki_page_content`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
  `page_id` bigint(20) NULL DEFAULT NULL COMMENT '页面ID',
  `content` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '内容',
  `create_user_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人ID',
  `create_user_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人名字',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint(20) NULL DEFAULT NULL COMMENT '修改人ID',
  `update_user_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名字',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_page_id`(`page_id`) USING BTREE COMMENT '页面ID'
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wiki_page_content
-- ----------------------------
INSERT INTO `wiki_page_content` VALUES (1, 7, '<p>测试1测试1测试1测试1测试1</p>', 1, NULL, '2019-02-24 17:51:56', 1, NULL, '2019-02-24 17:51:56');
INSERT INTO `wiki_page_content` VALUES (2, 8, '<p>测试2测试2测试2测试2fasfafafdsafasfxxxxxxxxxxxxdsadsadsadasdasd</p>', 1, NULL, '2019-02-24 17:51:56', 1, '暮光：城中城', '2019-03-08 22:12:36');
INSERT INTO `wiki_page_content` VALUES (3, 9, '<p>测试3测试3测试3测试3测试3</p>', 1, NULL, '2019-02-24 17:51:56', 1, '暮光：城中城', '2019-03-08 22:12:51');
INSERT INTO `wiki_page_content` VALUES (4, 10, '<p>测试4测试4测试4测试4测试4</p>', 1, NULL, '2019-02-24 17:51:56', 1, NULL, '2019-02-24 17:51:56');
INSERT INTO `wiki_page_content` VALUES (5, 11, '<p>测试5测试5测试5测试5</p>', 1, NULL, '2019-02-24 17:51:56', 1, NULL, '2019-02-24 17:51:56');
INSERT INTO `wiki_page_content` VALUES (6, 12, '<p>测试6测试6测试6测试6测试6</p>', 1, NULL, '2019-02-24 17:51:56', 1, NULL, '2019-02-24 17:51:56');
INSERT INTO `wiki_page_content` VALUES (7, 13, '<p>测试7测试7测试7测试7</p>', 1, NULL, '2019-02-24 17:51:56', NULL, NULL, '2019-02-24 17:51:56');
INSERT INTO `wiki_page_content` VALUES (8, 14, '<p>测试7 测试7测试7测试7&nbsp;</p>', 1, NULL, '2019-02-24 17:51:56', 1, NULL, '2019-02-24 17:51:56');
INSERT INTO `wiki_page_content` VALUES (9, 15, '<p>啦啦啦啦，我是数据库团队介绍</p><p>啦啦啦啦，我是数据库团队介绍&nbsp;</p><p>啦啦啦啦，我是数据库团队介绍</p><p>啦啦啦啦，我是数据库团队介绍</p><p>啦啦啦啦，我是数据库团队介绍</p><p>啦啦啦啦，我是数据库团队介绍</p><p>啦啦啦啦，我是数据库团队介绍&nbsp;<br></p>', 1, NULL, '2019-03-07 22:00:13', NULL, NULL, NULL);
INSERT INTO `wiki_page_content` VALUES (10, 16, '<p>啦啦啦啦，我是数据库团队介绍&nbsp;&nbsp;<br></p><p>啦啦啦啦，我是数据库团队介绍&nbsp;&nbsp;&nbsp;</p><p>啦啦啦啦，我是数据库团队介绍&nbsp;&nbsp;</p><p>啦啦啦啦，我是数据库团队介绍&nbsp;&nbsp;</p><p>啦啦啦啦，我是数据库团队介绍&nbsp; &nbsp;<br></p>', 1, '暮光：城中城', '2019-03-07 22:05:34', NULL, NULL, NULL);
INSERT INTO `wiki_page_content` VALUES (11, 17, '<p>啦啦啦啦，我是数据库团队介绍&nbsp;</p><p>啦啦啦啦，我是数据库团队介绍</p><p>啦啦啦啦，我是数据库团队介绍</p><p>啦啦啦啦，我是数据库团队介绍</p><p>啦啦啦啦，我是数据库团队介绍&nbsp;<br></p>', 1, '暮光：城中城', '2019-03-07 22:07:10', NULL, NULL, NULL);
INSERT INTO `wiki_page_content` VALUES (12, 18, '<p>我的人员列表&nbsp;</p><p>我的人员列表</p><p>我的人员列表</p><p>我的人员列表&nbsp;<br></p>', 1, '暮光：城中城', '2019-03-07 22:28:47', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for wiki_page_file
-- ----------------------------
DROP TABLE IF EXISTS `wiki_page_file`;
CREATE TABLE `wiki_page_file`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
  `page_id` bigint(20) NULL DEFAULT NULL COMMENT '页面ID',
  `file_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件名',
  `file_url` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件URL',
  `uuid` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件UUID',
  `create_user_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人ID',
  `create_user_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人名字',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint(20) NULL DEFAULT NULL COMMENT '修改人ID',
  `update_user_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人名字',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(4) NULL DEFAULT 0 COMMENT '0=有效 1=删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_uuid`(`uuid`) USING BTREE COMMENT '文件ID'
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wiki_page_file
-- ----------------------------
INSERT INTO `wiki_page_file` VALUES (22, 17, 'loading_mask.png', 'e:/tmp/wikiFiles/2019/03/07/aac61c98a8464eefb38969e3e5e432ee.png', 'aac61c98a8464eefb38969e3e5e432ee', 1, '暮光：城中城', '2019-03-07 22:21:08', NULL, NULL, NULL, 0);

-- ----------------------------
-- Table structure for wiki_page_zan
-- ----------------------------
DROP TABLE IF EXISTS `wiki_page_zan`;
CREATE TABLE `wiki_page_zan`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
  `page_id` bigint(20) NULL DEFAULT NULL COMMENT '页面ID',
  `comment_id` bigint(20) NULL DEFAULT NULL COMMENT '评论ID',
  `create_user_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人ID',
  `create_user_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人名字',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `yn` tinyint(4) NULL DEFAULT NULL COMMENT '是否有效 0=无效 1=有效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wiki_page_zan
-- ----------------------------
INSERT INTO `wiki_page_zan` VALUES (7, 8, NULL, 1, '暮光：城中城', '2019-03-06 19:04:26', 1);
INSERT INTO `wiki_page_zan` VALUES (8, 14, NULL, 1, '暮光：城中城', '2019-03-07 10:32:18', 1);
INSERT INTO `wiki_page_zan` VALUES (9, 17, NULL, 1, '暮光：城中城', '2019-03-07 22:10:56', 0);
INSERT INTO `wiki_page_zan` VALUES (10, 18, NULL, 1, '暮光：城中城', '2019-03-07 22:53:23', 1);

-- ----------------------------
-- Table structure for wiki_space
-- ----------------------------
DROP TABLE IF EXISTS `wiki_space`;
CREATE TABLE `wiki_space`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '空间名',
  `type` tinyint(4) NULL DEFAULT 1 COMMENT '空间类型 1=公司 2=个人 3=私人',
  `space_explain` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `create_user_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人ID',
  `create_user_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人名字',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `del_flag` tinyint(4) NOT NULL DEFAULT 0 COMMENT '删除标记 0=正常 1=已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wiki_space
-- ----------------------------
INSERT INTO `wiki_space` VALUES (1, '研发中心', 1, 'xxxxx', 1, NULL, '2019-02-24 17:51:56', 0);
INSERT INTO `wiki_space` VALUES (2, '数据库团队', 1, '数据库、redis、kafka相关', 1, '暮光：城中城', '2019-03-07 21:49:49', 0);
INSERT INTO `wiki_space` VALUES (3, '测试空间', 1, '', 1, '暮光：城中城', '2019-03-07 21:53:51', 1);
INSERT INTO `wiki_space` VALUES (4, '测试2', 1, '', 1, '暮光：城中城', '2019-03-07 21:54:25', 1);
INSERT INTO `wiki_space` VALUES (5, '研发中心', 1, 'dasdasdasdasd', 1, '暮光：城中城', '2019-03-08 22:27:30', 1);

-- ----------------------------
-- Table structure for zyplayer_storage
-- ----------------------------
DROP TABLE IF EXISTS `zyplayer_storage`;
CREATE TABLE `zyplayer_storage`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
  `doc_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参数名字',
  `doc_value` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参数值',
  `creation_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `key`(`doc_key`) USING BTREE COMMENT 'key唯一索引'
) ENGINE = InnoDB AUTO_INCREMENT = 48 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '存储网页上相关的数据' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of zyplayer_storage
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
