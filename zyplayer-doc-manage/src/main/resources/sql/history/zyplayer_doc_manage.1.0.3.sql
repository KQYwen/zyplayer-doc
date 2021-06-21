
--
-- ！！重要说明！！
-- 1、本sql文件分为：从1.0.2版本升级 和 全新的库，即增量和全量的区分，请选择性执行
-- 2、建议数据库版本：5.7.25
--

-- ------------------------从1.0.2版本升级：------------------------

CREATE TABLE `db_datasource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
  `name` varchar(50) DEFAULT NULL COMMENT '数据源名称',
  `driver_class_name` varchar(50) DEFAULT NULL COMMENT '数据源驱动类',
  `source_url` varchar(512) DEFAULT NULL COMMENT '数据源地址',
  `source_name` varchar(50) DEFAULT NULL COMMENT '数据源用户名',
  `source_password` varchar(50) DEFAULT NULL COMMENT '数据源密码',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `create_user_name` varchar(20) DEFAULT NULL COMMENT '创建人名字',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `yn` tinyint(4) DEFAULT NULL COMMENT '是否有效 0=无效 1=有效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

INSERT INTO `auth_info`(`auth_name`, `auth_desc`, `can_edit`, `create_uid`, `creation_time`, `auth_type`)
VALUES ( 'DB_DATASOURCE_MANAGE', '数据源管理权', 0, 1, '2019-06-29 13:01:20', 1);

INSERT INTO `auth_info`(`auth_name`, `auth_desc`, `can_edit`, `create_uid`, `creation_time`, `auth_type`)
VALUES ( 'ES_DATASOURCE_MANAGE', 'ES数据源管理权', 0, 1, '2019-07-27 00:39:20', 1);

ALTER TABLE `wiki_page_content` MODIFY COLUMN `preview` varchar(16000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '预览内容';

-- alter table `db_datasource` add `name` varchar(50) DEFAULT NULL COMMENT '数据源名称';

CREATE TABLE `es_datasource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
  `name` varchar(50) DEFAULT NULL COMMENT '数据源名称',
  `host_port` varchar(512) DEFAULT NULL COMMENT '地址和端口',
  `scheme` varchar(512) DEFAULT NULL COMMENT 'scheme，http或其他',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `create_user_name` varchar(20) DEFAULT NULL COMMENT '创建人名字',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `yn` tinyint(4) DEFAULT NULL COMMENT '是否有效 0=无效 1=有效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;


