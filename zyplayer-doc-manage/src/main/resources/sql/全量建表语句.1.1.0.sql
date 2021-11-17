-- ------------------------------------------------
-- 这是一个处于开发中的版本，变化快，中途变更不兼容
-- ------------------------------------------------

/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : 127.0.0.1:3306
 Source Schema         : zyplayer_doc_manage

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

*/

SET NAMES utf8;
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
  `creation_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `auth_type` tinyint(4) NOT NULL DEFAULT 0 COMMENT '权限类型 0=隐藏权限 1=可使用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '权限信息表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of auth_info
-- ----------------------------
INSERT INTO `auth_info` VALUES (3, 'AUTH_ASSIGN', '权限分配权', 0, 1, '2018-12-01 11:40:42', 1);
INSERT INTO `auth_info` VALUES (4, 'USER_MANAGE', '用户管理权', 0, 1, '2018-12-01 11:40:42', 1);
INSERT INTO `auth_info` VALUES (5, 'WIKI_EDIT_PAGE_', '编辑wiki文档', 0, 1, '2019-06-04 13:01:20', 0);
INSERT INTO `auth_info` VALUES (6, 'WIKI_VIEW_PAGE_', '查看wiki文档', 0, 1, '2019-06-04 13:01:20', 0);
INSERT INTO `auth_info` VALUES (7, 'WIKI_COMMENT_PAGE_', '评论wiki文档', 0, 1, '2019-06-04 13:01:20', 0);
INSERT INTO `auth_info` VALUES (8, 'WIKI_DELETE_PAGE_', '删除wiki文档', 0, 1, '2019-06-04 13:01:20', 0);
INSERT INTO `auth_info` VALUES (9, 'WIKI_PAGE_FILE_UPLOAD_', '上传wiki文档附件', 0, 1, '2019-06-04 13:01:20', 0);
INSERT INTO `auth_info` VALUES (10, 'WIKI_PAGE_FILE_DELETE_', '删除wiki文档附件', 0, 1, '2019-06-04 13:01:20', 0);
INSERT INTO `auth_info` VALUES (11, 'WIKI_PAGE_AUTH_MANAGE_', 'wiki权限管理', 0, 1, '2019-06-04 13:01:20', 0);
INSERT INTO `auth_info` VALUES (12, 'DB_DATASOURCE_MANAGE', 'DB数据源管理权', 0, 1, '2019-06-29 13:01:20', 1);
INSERT INTO `auth_info` VALUES (13, 'ES_DATASOURCE_MANAGE', 'ES数据源管理权', 0, 1, '2019-07-27 00:39:20', 1);
INSERT INTO `auth_info` VALUES (14, 'DB_VIEW_', '数据源查看权', 0, 1, '2019-08-18 23:25:17', 0);
INSERT INTO `auth_info` VALUES (15, 'DB_SELECT_', '数据源查询权', 0, 1, '2019-08-18 23:25:17', 0);
INSERT INTO `auth_info` VALUES (16, 'DB_UPDATE_', '数据源增删改查权', 0, 1, '2019-08-18 23:25:17', 0);
INSERT INTO `auth_info` VALUES (17, 'DB_DESC_EDIT_', '表字段注释修改权', 0, 1, '2019-08-18 23:25:17', 0);
INSERT INTO `auth_info` VALUES (18, 'DB_PROC_EDIT_', '存储过程修改权', 0, 1, '2021-04-24 23:25:17', 0);

-- ----------------------------
-- Table structure for db_datasource
-- ----------------------------
DROP TABLE IF EXISTS `db_datasource`;
CREATE TABLE `db_datasource`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
  `driver_class_name` varchar(50) NULL DEFAULT NULL COMMENT '数据源驱动类',
  `source_url` varchar(512) NULL DEFAULT NULL COMMENT '数据源地址',
  `source_name` varchar(50) NULL DEFAULT NULL COMMENT '数据源用户名',
  `source_password` varchar(50) NULL DEFAULT NULL COMMENT '数据源密码',
  `create_user_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人ID',
  `create_user_name` varchar(20) NULL DEFAULT NULL COMMENT '创建人名字',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `yn` tinyint(4) NULL DEFAULT NULL COMMENT '是否有效 0=无效 1=有效',
  `name` varchar(50) NULL DEFAULT NULL COMMENT '数据源名称',
  `group_name` varchar(50) DEFAULT NULL COMMENT '数据源分组名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of db_datasource
-- ----------------------------

-- ----------------------------
-- Table structure for db_favorite
-- ----------------------------
DROP TABLE IF EXISTS `db_favorite`;
CREATE TABLE `db_favorite`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
  `name` varchar(50) NULL DEFAULT NULL COMMENT '收藏标题',
  `content` varchar(10000) NULL DEFAULT NULL COMMENT '收藏内容',
  `create_user_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人ID',
  `create_user_name` varchar(20) NULL DEFAULT NULL COMMENT '创建人名字',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `yn` tinyint(4) NULL DEFAULT NULL COMMENT '是否有效 0=无效 1=有效',
  `datasource_id` bigint(20) NULL DEFAULT NULL COMMENT '数据源ID',
  `param_json` varchar(1024) DEFAULT NULL COMMENT '执行参数JSON',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of db_favorite
-- ----------------------------

-- ----------------------------
-- Table structure for db_history
-- ----------------------------
DROP TABLE IF EXISTS `db_history`;
CREATE TABLE `db_history`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
  `content` varchar(10000) NULL DEFAULT NULL COMMENT 'sql内容',
  `create_user_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人ID',
  `create_user_name` varchar(20) NULL DEFAULT NULL COMMENT '创建人名字',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `yn` tinyint(4) NULL DEFAULT NULL COMMENT '是否有效 0=无效 1=有效',
  `datasource_id` bigint(20) NULL DEFAULT NULL COMMENT '数据源ID',
  `param_json` varchar(1024) DEFAULT NULL COMMENT '执行参数JSON',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 135 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of db_history
-- ----------------------------

-- ----------------------------
-- Table structure for es_datasource
-- ----------------------------
DROP TABLE IF EXISTS `es_datasource`;
CREATE TABLE `es_datasource`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
  `name` varchar(50) NULL DEFAULT NULL COMMENT '数据源名称',
  `host_port` varchar(512) NULL DEFAULT NULL COMMENT '地址和端口',
  `scheme` varchar(512) NULL DEFAULT NULL COMMENT 'scheme，http或其他',
  `create_user_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人ID',
  `create_user_name` varchar(20) NULL DEFAULT NULL COMMENT '创建人名字',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `yn` tinyint(4) NULL DEFAULT NULL COMMENT '是否有效 0=无效 1=有效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of es_datasource
-- ----------------------------

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
  `creation_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `auth_custom_suffix` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '自定义权限结尾',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 73 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户权限表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user_auth
-- ----------------------------
INSERT INTO `user_auth` VALUES (9, 2, 1, 1, NULL, 0, '2018-12-15 22:19:59', NULL, NULL);
INSERT INTO `user_auth` VALUES (10, 2, 2, 1, NULL, 0, '2018-12-15 22:19:59', NULL, NULL);
INSERT INTO `user_auth` VALUES (11, 2, 3, 1, NULL, 0, '2018-12-15 22:19:59', NULL, NULL);
INSERT INTO `user_auth` VALUES (12, 2, 4, 1, NULL, 0, '2018-12-15 22:19:59', NULL, NULL);
INSERT INTO `user_auth` VALUES (31, 3, 1, 1, NULL, 0, '2019-06-21 15:19:51', NULL, NULL);
INSERT INTO `user_auth` VALUES (32, 3, 2, 1, NULL, 0, '2019-06-21 15:19:51', NULL, NULL);
INSERT INTO `user_auth` VALUES (33, 3, 3, 1, NULL, 0, '2019-06-21 15:19:51', NULL, NULL);
INSERT INTO `user_auth` VALUES (34, 3, 4, 1, NULL, 0, '2019-06-21 15:19:51', NULL, NULL);
INSERT INTO `user_auth` VALUES (44, 1, 3, 1, NULL, 0, '2019-08-12 13:10:11', NULL, NULL);
INSERT INTO `user_auth` VALUES (45, 1, 4, 1, NULL, 0, '2019-08-12 13:10:11', NULL, NULL);
INSERT INTO `user_auth` VALUES (46, 1, 12, 1, NULL, 0, '2019-08-12 13:10:11', NULL, NULL);
INSERT INTO `user_auth` VALUES (47, 1, 13, 1, NULL, 0, '2019-08-12 13:10:11', NULL, NULL);

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
  `del_flag` tinyint(4) NULL DEFAULT 0 COMMENT '是否删除 0=未删除 1=已删除 2=已停用',
  `creation_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_uid` bigint(20) NULL DEFAULT NULL COMMENT '创建人用户ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `sex` tinyint(4) NOT NULL DEFAULT 0 COMMENT '性别 0=女 1=男',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_userNo`(`user_no`) USING BTREE COMMENT '登录用户名'
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户信息表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES (1, 'zyplayer', 'e10adc3949ba59abbe56e057f20f883e', '暮光：城中城', '806783409@qq.com', NULL, 0, '2018-12-01 11:37:39', NULL, '2018-12-15 20:32:08', NULL, 0);

-- ----------------------------
-- Table structure for wiki_page
-- ----------------------------
DROP TABLE IF EXISTS `wiki_page`;
CREATE TABLE `wiki_page`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
  `space_id` bigint(20) NULL DEFAULT NULL COMMENT '空间ID',
  `name` varchar(50) NULL DEFAULT NULL COMMENT '名字',
  `parent_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '父ID',
  `node_type` tinyint(4) NULL DEFAULT 0 COMMENT '节点类型 0=有子节点 1=终节点',
  `zan_num` int(11) NOT NULL DEFAULT 0 COMMENT '赞的数量',
  `edit_type` tinyint(4) NOT NULL DEFAULT 0 COMMENT '编辑类型 0=可编辑 1=不允许编辑',
  `create_user_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人ID',
  `create_user_name` varchar(20) NULL DEFAULT NULL COMMENT '创建人名字',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint(20) NULL DEFAULT NULL COMMENT '修改人ID',
  `update_user_name` varchar(20) NULL DEFAULT NULL COMMENT '修改人名字',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(4) NOT NULL DEFAULT 0 COMMENT '0=有效 1=删除',
  `view_num` int(11) NOT NULL DEFAULT 0 COMMENT '阅读数',
  `seq_no` int(11) NOT NULL DEFAULT 0 COMMENT '顺序',
  `editor_type` tinyint(4) NOT NULL DEFAULT 1 COMMENT '编辑框类型 1=HTML 2=Markdown',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wiki_page
-- ----------------------------
INSERT INTO `wiki_page` VALUES (1, 1, '关于zyplayer-doc工具', 0, 0, 0, 0, 1, '暮光：城中城', '2019-03-09 14:01:41', 1, '暮光：城中城', '2019-07-16 21:00:00', 0, 42, 1, 1);
INSERT INTO `wiki_page` VALUES (2, 1, '开发规划', 0, 0, 0, 0, 1, '暮光：城中城', '2019-03-09 14:14:02', 1, '暮光：城中城', '2019-06-14 13:30:22', 0, 122, 4, 1);
INSERT INTO `wiki_page` VALUES (3, 1, '升级日志', 0, 0, 0, 0, 1, '暮光：城中城', '2019-03-09 14:16:20', 1, '暮光：城中城', '2019-06-14 16:49:30', 0, 29, 5, 1);
INSERT INTO `wiki_page` VALUES (4, 1, '贡献人员列表', 0, 0, 0, 0, 1, '暮光：城中城', '2019-03-09 15:16:15', 1, '暮光：城中城', '2019-06-14 13:20:43', 0, 13, 7, 1);
INSERT INTO `wiki_page` VALUES (5, 1, 'zyplayer-doc-swagger', 0, 0, 1, 0, 1, '暮光：城中城', '2019-03-09 15:33:14', NULL, NULL, NULL, 0, 20, 9, 1);
INSERT INTO `wiki_page` VALUES (6, 1, '如何使用', 5, 0, 0, 0, 1, '暮光：城中城', '2019-03-09 15:33:33', 1, '暮光：城中城', '2019-03-09 15:33:46', 0, 3, 14, 1);
INSERT INTO `wiki_page` VALUES (7, 2, '所有格式测试', 0, 0, 0, 0, 1, '暮光：城中城', '2019-03-12 12:21:26', NULL, NULL, NULL, 0, 13, 13, 1);

-- ----------------------------
-- Table structure for wiki_page_comment
-- ----------------------------
DROP TABLE IF EXISTS `wiki_page_comment`;
CREATE TABLE `wiki_page_comment`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
  `page_id` bigint(20) NULL DEFAULT NULL COMMENT '页面ID',
  `parent_id` bigint(20) NULL DEFAULT NULL COMMENT '父评论ID',
  `content` varchar(512) NULL DEFAULT NULL COMMENT '评论内容',
  `zan_num` int(11) NOT NULL DEFAULT 0 COMMENT '赞的数量',
  `create_user_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人ID',
  `create_user_name` varchar(20) NULL DEFAULT NULL COMMENT '创建人名字',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `del_flag` tinyint(4) NULL DEFAULT 0 COMMENT '0=有效 1=删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wiki_page_comment
-- ----------------------------

-- ----------------------------
-- Table structure for wiki_page_content
-- ----------------------------
DROP TABLE IF EXISTS `wiki_page_content`;
CREATE TABLE `wiki_page_content`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
  `page_id` bigint(20) NULL DEFAULT NULL COMMENT '页面ID',
  `content` mediumtext NULL COMMENT '内容',
  `create_user_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人ID',
  `create_user_name` varchar(20) NULL DEFAULT NULL COMMENT '创建人名字',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint(20) NULL DEFAULT NULL COMMENT '修改人ID',
  `update_user_name` varchar(20) NULL DEFAULT NULL COMMENT '修改人名字',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `preview` varchar(16000) NULL DEFAULT NULL COMMENT '预览内容',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_page_id`(`page_id`) USING BTREE COMMENT '页面ID'
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wiki_page_content
-- ----------------------------
INSERT INTO `wiki_page_content` VALUES (1, 1, '<p>一入开源深似海</p><br>源码地址：<a href=\"https://gitee.com/zyplayer/zyplayer-doc\" target=\"_blank\">https://gitee.com/zyplayer/zyplayer-doc</a><p>体验地址：<a href=\"http://doc.zyplayer.com/zyplayer-doc-manage/static/manage/home.html\" target=\"_blank\">http://doc.zyplayer.com/zyplayer-doc-manage/static/manage/home.html</a>&nbsp;账号：zyplayer 密码：123456</p><p><br></p><p>更多实用功能请提交评论或加群交流！谢谢！<br></p><p>QQ群：466363173</p>tips：想要编辑本文请修改表：wiki_page的edit_type字段值为0才行哦~<p><br></p><p>有什么问题可以来评论本文章哦！当然，如果你是部署在你们自己本地的我肯定看不到此文章的评论</p><p>可以到本工具官网下评论此文章，我会随时去查看的！</p><p>唯一官网地址：<a href=\"http://doc.zyplayer.com\" target=\"_blank\">http://doc.zyplayer.com</a></p><p><br></p><p>欢迎提交各种变态无理的要求~</p>', 1, '暮光：城中城', '2019-03-09 14:01:41', 1, '暮光：城中城', '2019-07-12 18:11:44', '一入开源深似海源码地址：https://gitee.com/zyplayer/zyplayer-doc体验地址：http://doc.zyplayer.com/zyplayer-doc-manage/static/manage/home.html&nbsp;账号：zyplayer 密码：123456更多实用功能请提交评论或加群交流！谢谢！QQ群：466363173tips：想要编辑本文请修改表：wiki_page的edit_type字段值为0才行哦~有什么问题可以来评论本文章哦！当然，如果你是部署在你们自己本地的我肯定看不到此文章的评论可以到本工具官网下评论此文章，我会随时去查看的！唯一官网地址：http://doc.zyplayer.com欢迎提交各种变态无理的要求~');
INSERT INTO `wiki_page_content` VALUES (2, 2, '<p>zyplayer-doc-manage：</p><p>1、首页做大的调整优化，希望使用element重构一下</p><p>2、人员导入方案，权限优化，人员管理细化</p><p>&nbsp;</p><p>zyplayer-doc-wiki：</p><p><prefira code\';font-size:10.5pt;\"=\"\">1<spancourier new\';\"=\"\">、支持页面权限控制，包括查看、编辑、删除、评论的权限</spancourier></prefira></p><p><prefira code\';font-size:10.5pt;\"=\"\"><spancourier new\';\"=\"\"><spancourier new\';\"=\"\">2<spancourier new\';\"=\"\">、</spancourier></spancourier></spancourier></prefira>支持把一个空间里的文档一键同步到git<spancourier new\';\"=\"\">的wiki上，统一管理文档</spancourier></p><p><spancourier new\';\"=\"\">3、</spancourier>支持微信文章拉取保存</p><p>4、支持历史记录查看</p><p>5、支持提供多种编辑器选择，更大的选择空间</p><p>6、支持全局搜索、文章内搜索等，当前只支持标题搜索，尴尬</p><p>7、支持开放一个空间里的文档无需登录即可访问</p><p>8、url动态变动，这样就可以复制指定文章的地址了</p><p>9、参考https://www.kancloud.cn，取其精华</p><p><br></p><p>欢迎加入开发！</p><p>如果您觉得哪里不好用也可以拉取源码下来改了后提交PR</p><p>源码地址：<a href=\"https://gitee.com/zyplayer/zyplayer-doc\" target=\"_blank\">https://gitee.com/zyplayer/zyplayer-doc</a></p><p><br></p><p>更多实用功能请提交评论或加群交流！谢谢！</p><p>QQ群：466363173</p>', 1, '暮光：城中城', '2019-03-09 14:14:02', 1, '暮光：城中城', '2019-06-14 13:30:22', 'zyplayer-doc-manage：1、首页做大的调整优化，希望使用element重构一下2、人员导入方案，权限优化，人员管理细化&nbsp;zyplayer-doc-wiki：1、支持页面权限控制，包括查看、编辑、删除、评论的权限2、支持把一个空间里的文档一键同步到git的wiki上，统一管理文档3、支持微信文章拉取保存4、支持历史记录查看5、支持提供多种编辑器选择，更大的选择空间6、支持全...');
INSERT INTO `wiki_page_content` VALUES (3, 3, '<p><span style=\"color: rgb(249, 150, 59);\">V1.0.1　2019-03-09</span></p><p>1、增加zyplayer-doc-wiki模块</p><ul><li>支持空间隔离<br></li><li>支持附件上传<br></li><li>拖动可以改变wiki顺序<br></li><li>基本的文档创建、编辑、评论、删除、搜索等功能</li></ul><p><prefira code\';font-size:10.5pt;\"=\"\">2<spancourier new\';\"=\"\">、dubbo<spancourier new\';\"=\"\">文档支持使用元数据生成参数和返回值，dubbo2.7.0新特性</spancourier></spancourier></prefira></p><p><prefira code\';font-size:10.5pt;\"=\"\"><spancourier new\';\"=\"\"><spancourier new\';\"=\"\">3<spancourier new\';\"=\"\">、框架进行了大的拆分，表修改较多</spancourier></spancourier></spancourier></prefira></p><p><prefira code\';font-size:10.5pt;\"=\"\"><spancourier new\';\"=\"\"><spancourier new\';\"=\"\"><spancourier new\';\"=\"\">4<spancourier new\';\"=\"\">、增加升级通知</spancourier></spancourier></spancourier></spancourier></prefira></p><p><prefira code\';font-size:10.5pt;\"=\"\"><spancourier new\';\"=\"\"><spancourier new\';\"=\"\"><spancourier new\';\"=\"\"><spancourier new\';\"=\"\">5<spancourier new\';\"=\"\">、细节优化</spancourier></spancourier></spancourier></spancourier></spancourier></prefira></p><p><prefira code\';font-size:10.5pt;\"=\"\"><spancourier new\';\"=\"\"><spancourier new\';\"=\"\"><spancourier new\';\"=\"\"><spancourier new\';\"=\"\"><spancourier new\';\"=\"\"><br></spancourier></spancourier></spancourier></spancourier></spancourier></prefira></p><p><span style=\"color: rgb(249, 150, 59);\">V1.0.0　2019-02-15</span><br></p><p>第一个版本发布</p><p>1、zyplayer-doc-swagger，swagger接口文档展示方案，在之前的项目中有许多改进，坚持文档生成和展示分离，有较多需要服务端支持的功能，不对各项目做过多的入侵即可使用，可动态对文档进行开放访问<br>2、zyplayer-doc-dubbo，支持对dubbo服务的自动扫描，直观展示所有的服务，文档的展示、文档编辑和在线接口调试，不需要对已有的服务做任何改动，支持zookeeper、nacos注册中心的服务扫描<br>3、zyplayer-doc-db，数据库文档工具，具有数据库表、字段文档的查看/修改，文档导出等功能，支持mysql和sqlserver数据库<br>4、zyplayer-doc-manage，管理以上几个服务，spring boot项目，spring security做权限管理，可直接运行</p>', 1, '暮光：城中城', '2019-03-09 14:16:20', 1, '暮光：城中城', '2019-06-14 16:49:30', 'V1.0.1　2019-03-091、增加zyplayer-doc-wiki模块支持空间隔离支持附件上传拖动可以改变wiki顺序基本的文档创建、编辑、评论、删除、搜索等功能2、dubbo文档支持使用元数据生成参数和返回值，dubbo2.7.0新特性3、框架进行了大的拆分，表修改较多4、增加升级通知5、细节优化V1.0.0　2019-02-15第一个版本发布1、zyplayer-doc-swagge...');
INSERT INTO `wiki_page_content` VALUES (4, 4, '<p>开发人员列表：</p><p><a href=\"http://www.zyplayer.com\" target=\"_blank\">暮光：城中城</a><br></p>', 1, '暮光：城中城', '2019-03-09 15:16:15', 1, '暮光：城中城', '2019-06-14 13:20:43', '开发人员列表：暮光：城中城');
INSERT INTO `wiki_page_content` VALUES (5, 5, '<p>zyplayer-doc-swagger 使用文档目录</p>', 1, '暮光：城中城', '2019-03-09 15:33:14', NULL, NULL, NULL, NULL);
INSERT INTO `wiki_page_content` VALUES (6, 6, '<p>最不喜欢写文档了。。。</p>', 1, '暮光：城中城', '2019-03-09 15:33:33', 1, '暮光：城中城', '2019-03-09 15:33:46', NULL);
INSERT INTO `wiki_page_content` VALUES (7, 7, '<h2>H2</h2><p><span style=\"font-weight: bold;\">加粗</span></p><p><span style=\"font-size: xx-large;\">字体大小</span><span style=\"font-weight: bold;\"><br></span></p><p><span style=\"font-family: 宋体;\">宋体宋体宋体宋体宋体</span>&nbsp;</p><p><span style=\"font-style: italic;\">斜体斜体斜体</span></p><p><span style=\"text-decoration-line: underline;\">下划线下划线下划线</span><span style=\"font-style: italic;\"><br></span></p><p><span style=\"text-decoration-line: line-through;\">删除线删除线删除线</span><span style=\"text-decoration-line: underline;\"><br></span></p><p><span style=\"color: rgb(249, 150, 59);\">字体颜色</span><span style=\"text-decoration-line: line-through;\"><br></span></p><p><span style=\"background-color: rgb(194, 79, 74);\">背景颜色</span></p><p><a href=\"http://www.baidu.com/\" target=\"_blank\">链接：百度一下</a><br></p><ol><li>有序列表</li><li>xxx</li><li>xxx</li></ol><ul><li>无序列表</li><li>xxx</li><li>xxx</li></ul><p style=\"text-align: center;\">文字居中</p><p style=\"text-align: right;\">文字靠右</p><blockquote style=\"font-size: medium;\">xxxxx</blockquote><p>表情：<img src=\"http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/50/pcmoren_huaixiao_org.png\" alt=\"[坏笑]\"><br></p><p>表格：</p><table border=\"0\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"><tbody><tr><th>&nbsp;ID</th><th>&nbsp;名字</th><th>&nbsp;邮箱</th><th>&nbsp;说明</th><th>&nbsp;时间</th></tr><tr><td>&nbsp;xx</td><td>&nbsp;xx</td><td>&nbsp;xx</td><td>&nbsp;xx</td><td>&nbsp;xx</td></tr><tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr></tbody></table><p>代码：<br></p><pre><code>public static void main(String[] args) {<br>	System.out.println(\"hello world\");<br>}</code></pre><p><br></p>', 1, '暮光：城中城', '2019-03-12 12:21:26', NULL, NULL, NULL, NULL);


-- ----------------------------
-- Table structure for wiki_page_file
-- ----------------------------
DROP TABLE IF EXISTS `wiki_page_file`;
CREATE TABLE `wiki_page_file`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
  `page_id` bigint(20) NULL DEFAULT NULL COMMENT '页面ID',
  `file_name` varchar(50) NULL DEFAULT NULL COMMENT '文件名',
  `file_url` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件URL',
  `uuid` varchar(40) NULL DEFAULT NULL COMMENT '文件UUID',
  `create_user_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人ID',
  `create_user_name` varchar(20) NULL DEFAULT NULL COMMENT '创建人名字',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint(20) NULL DEFAULT NULL COMMENT '修改人ID',
  `update_user_name` varchar(20) NULL DEFAULT NULL COMMENT '修改人名字',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(4) NULL DEFAULT 0 COMMENT '0=有效 1=删除',
  `download_num` int(11) NOT NULL DEFAULT 0 COMMENT '下载次数',
  `file_size` bigint NULL COMMENT '文件大小',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_uuid`(`uuid`) USING BTREE COMMENT '文件ID'
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wiki_page_file
-- ----------------------------

-- ----------------------------
-- Table structure for wiki_page_history
-- ----------------------------
DROP TABLE IF EXISTS `wiki_page_history`;
CREATE TABLE `wiki_page_history`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
  `page_id` bigint(20) NULL DEFAULT NULL COMMENT '页面ID',
  `create_user_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人ID',
  `create_user_name` varchar(20) NULL DEFAULT NULL COMMENT '创建人名字',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `del_flag` tinyint(4) NOT NULL DEFAULT 0 COMMENT '删除标记 0=正常 1=已删除',
  `git_commit_id` varchar(50) NULL COMMENT 'git提交记录ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_page_id`(`page_id`) USING BTREE COMMENT '页面ID索引'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for wiki_page_zan
-- ----------------------------
DROP TABLE IF EXISTS `wiki_page_zan`;
CREATE TABLE `wiki_page_zan`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
  `page_id` bigint(20) NULL DEFAULT NULL COMMENT '页面ID',
  `comment_id` bigint(20) NULL DEFAULT NULL COMMENT '评论ID',
  `create_user_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人ID',
  `create_user_name` varchar(20) NULL DEFAULT NULL COMMENT '创建人名字',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `yn` tinyint(4) NULL DEFAULT NULL COMMENT '是否有效 0=无效 1=有效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for wiki_space
-- ----------------------------
DROP TABLE IF EXISTS `wiki_space`;
CREATE TABLE `wiki_space`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
  `name` varchar(50) NULL DEFAULT NULL COMMENT '空间名',
  `type` tinyint(4) NULL DEFAULT 1 COMMENT '空间类型 1=公司 2=个人 3=私人',
  `space_explain` varchar(255) NULL DEFAULT NULL COMMENT '描述',
  `edit_type` tinyint(4) NOT NULL DEFAULT 0 COMMENT '编辑类型 0=可编辑 1=不允许编辑',
  `tree_lazy_load` tinyint(4) NOT NULL DEFAULT 0 COMMENT '目录延迟加载 0=否 1=是',
  `open_doc` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否是开放文档 0=否 1=是',
  `uuid` varchar(40) NULL DEFAULT NULL COMMENT '唯一UUID',
  `create_user_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人ID',
  `create_user_name` varchar(20) NULL DEFAULT NULL COMMENT '创建人名字',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `del_flag` tinyint(4) NOT NULL DEFAULT 0 COMMENT '删除标记 0=正常 1=已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wiki_space
-- ----------------------------
INSERT INTO `wiki_space` VALUES (1, 'zyplayer-doc交流专用', 1, '', 0, 0, 1, '23f3f59a60824d21af9f7c3bbc9bc3cb', 1, '暮光：城中城', '2019-03-09 13:59:14', 0);
INSERT INTO `wiki_space` VALUES (2, '体验专用空间', 2, '', 0, 0, 0, '91995a9a67bf45db9b5e58266517393e', 1, '暮光：城中城', '2019-03-09 14:24:30', 0);

-- ----------------------------
-- Table structure for zyplayer_storage
-- ----------------------------
DROP TABLE IF EXISTS `zyplayer_storage`;
CREATE TABLE `zyplayer_storage`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
  `doc_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参数名字',
  `doc_value` mediumtext NULL COMMENT '参数值',
  `creation_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `key`(`doc_key`) USING BTREE COMMENT 'key唯一索引'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '存储网页上相关的数据' ROW_FORMAT = Compact;

DROP TABLE IF EXISTS `db_transfer_task`;
CREATE TABLE `db_transfer_task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
  `name` varchar(100) DEFAULT NULL COMMENT '任务名称',
  `query_datasource_id` bigint(20) DEFAULT NULL COMMENT '查询数据源ID',
  `storage_datasource_id` bigint(20) DEFAULT NULL COMMENT '入库数据源ID',
  `query_sql` varchar(2048) DEFAULT NULL COMMENT '查询数据的sql',
  `storage_sql` varchar(2048) DEFAULT NULL COMMENT '数据入库的sql',
  `need_count` tinyint(4) NOT NULL DEFAULT '0' COMMENT '自动查询总条数 0=否 1=是',
  `last_execute_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '最后执行状态 0=未执行 1=执行中 2=执行成功 3=执行失败 4=取消执行',
  `last_execute_time` datetime DEFAULT NULL COMMENT '最后执行时间',
  `last_execute_info` text DEFAULT NULL COMMENT '最后执行信息',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `create_user_name` varchar(20) DEFAULT NULL COMMENT '创建人名字',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `del_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标记 0=正常 1=已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

DROP TABLE IF EXISTS `user_message`;
CREATE TABLE `user_message`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
  `sys_type` tinyint(4) NOT NULL DEFAULT 1 COMMENT '系统类型 1=manage 2=wiki 3=db',
  `msg_type` int NOT NULL DEFAULT 1 COMMENT '消息类型 1=普通文本消息 2=wiki文档创建 3=wiki文档删除 4=wiki文档编辑 5=wiki文档权限修改 6=wiki文档评论 7=wiki文档删除评论 8=wiki文档上传附件',
  `data_id` bigint(20) NULL DEFAULT NULL COMMENT '消息关联的数据ID',
  `data_desc` varchar(100) NULL DEFAULT NULL COMMENT '消息关联的数据说明',
  `msg_content` varchar(255) NULL DEFAULT NULL COMMENT '消息内容',
  `msg_status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '消息状态 0=未读 1=已读 2=已删除',
  `operator_user_id` bigint(20) NULL DEFAULT NULL COMMENT '操作人用户ID',
  `operator_user_name` varchar(20) NULL DEFAULT NULL COMMENT '操作人用户名',
  `affect_user_id` bigint(20) NULL DEFAULT NULL COMMENT '影响人用户ID',
  `affect_user_name` varchar(20) NULL DEFAULT NULL COMMENT '影响人用户名',
  `accept_user_id` bigint(20) NULL DEFAULT NULL COMMENT '接收人用户ID',
  `creation_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户消息表' ROW_FORMAT = Compact;

DROP TABLE IF EXISTS `user_group`;
CREATE TABLE `user_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
  `name` varchar(50) DEFAULT NULL COMMENT '分组名',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `create_user_name` varchar(20) DEFAULT NULL COMMENT '创建人名字',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `del_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标记 0=正常 1=已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='用户组';

DROP TABLE IF EXISTS `user_group_relation`;
CREATE TABLE `user_group_relation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
  `group_id` bigint(20) DEFAULT NULL COMMENT '群ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `create_user_name` varchar(20) DEFAULT NULL COMMENT '创建人名字',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `del_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标记 0=正常 1=已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='用户和用户组关系表';

DROP TABLE IF EXISTS `user_group_auth`;
CREATE TABLE `user_group_auth` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
  `group_id` bigint(20) DEFAULT NULL COMMENT '群ID',
  `data_id` bigint(20) DEFAULT NULL COMMENT '授权数据的ID',
  `auth_type` tinyint(4) DEFAULT NULL COMMENT '授权类型，依据各系统自己定义',
  `project_type` tinyint(4) DEFAULT NULL COMMENT '项目类型 1=manage 2=wiki 3=db',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `create_user_name` varchar(20) DEFAULT NULL COMMENT '创建人名字',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `del_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标记 0=正常 1=已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='用户组在各项目内的授权关系';

DROP TABLE IF EXISTS `wiki_space_favorite`;
CREATE TABLE `wiki_space_favorite` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
  `space_id` bigint(20) DEFAULT NULL COMMENT '空间ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `del_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标记 0=正常 1=已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='用户空间收藏记录表';

DROP TABLE IF EXISTS `user_setting`;
CREATE TABLE `user_setting` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `name` varchar(100) DEFAULT NULL COMMENT '设置的名字',
  `value` varchar(100) DEFAULT NULL COMMENT '设置的值',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `del_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标记 0=正常 1=已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='用户设置表';

DROP TABLE IF EXISTS `db_proc_log`;
CREATE TABLE `db_proc_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
  `datasource_id` bigint(20) DEFAULT NULL COMMENT '数据源ID',
  `proc_db` varchar(100) DEFAULT NULL COMMENT '所属数据库',
  `proc_name` varchar(100) DEFAULT NULL COMMENT '名字',
  `proc_type` varchar(50) DEFAULT NULL COMMENT '类型',
  `proc_body` longblob DEFAULT NULL COMMENT '函数创建SQL',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '保存状态 1=成功 2=失败',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `create_user_name` varchar(20) DEFAULT NULL COMMENT '创建人名字',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='数据库函数修改日志';

DROP TABLE IF EXISTS `db_table_relation`;
CREATE TABLE `db_table_relation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
  `datasource_id` bigint(20) DEFAULT NULL COMMENT '数据源ID',
  `start_db_name` varchar(100) DEFAULT NULL COMMENT '源库名',
  `start_table_name` varchar(100) DEFAULT NULL COMMENT '源表名',
  `start_column_name` varchar(100) DEFAULT NULL COMMENT '源字段名',
  `end_db_name` varchar(100) DEFAULT NULL COMMENT '目标库名',
  `end_table_name` varchar(100) DEFAULT NULL COMMENT '目标表名',
  `end_column_name` varchar(100) DEFAULT NULL COMMENT '目标字段名',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `create_user_name` varchar(20) DEFAULT NULL COMMENT '创建人名字',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='表关系';

DROP TABLE IF EXISTS `swagger_doc`;
CREATE TABLE `swagger_doc` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
  `name` varchar(100) DEFAULT NULL COMMENT '文档名称',
  `doc_type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '文档类型 1=url 2=swagger文档json',
  `doc_url` varchar(250) DEFAULT NULL COMMENT '文档URL地址',
  `json_content` mediumtext DEFAULT NULL COMMENT 'swagger文档json内容',
  `rewrite_domain` varchar(100) DEFAULT NULL COMMENT '重写的域名',
  `open_visit` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否开放访问 0=否 1=是',
  `doc_status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态 1=启用 2=禁用',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `create_user_name` varchar(20) DEFAULT NULL COMMENT '创建人名字',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `yn` tinyint(4) DEFAULT NULL COMMENT '是否有效 0=无效 1=有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='swagger文档地址';

DROP TABLE IF EXISTS `swagger_request_param`;
CREATE TABLE `swagger_request_param` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
  `doc_url` varchar(250) DEFAULT NULL COMMENT '文档url',
  `form_data` text DEFAULT NULL COMMENT 'form参数',
  `body_data` text DEFAULT NULL COMMENT 'body参数',
  `header_data` varchar(1024) DEFAULT NULL COMMENT 'header参数',
  `cookie_data` varchar(1024) DEFAULT NULL COMMENT 'cookie参数',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `create_user_name` varchar(20) DEFAULT NULL COMMENT '创建人名字',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `yn` tinyint(4) DEFAULT NULL COMMENT '是否有效 0=无效 1=有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='swagger文档请求参数记录';

DROP TABLE IF EXISTS `swagger_global_param`;
CREATE TABLE `swagger_global_param` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
  `param_type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '参数类型 1=form 2=header 3=cookie',
  `param_key` varchar(100) DEFAULT NULL COMMENT '参数名',
  `param_value` varchar(1024) DEFAULT NULL COMMENT '参数值',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `create_user_name` varchar(20) DEFAULT NULL COMMENT '创建人名字',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `yn` tinyint(4) DEFAULT NULL COMMENT '是否有效 0=无效 1=有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='swagger文档全局参数记录';


SET FOREIGN_KEY_CHECKS = 1;
