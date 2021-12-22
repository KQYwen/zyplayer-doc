-- ------------------------------------------------
-- 这是一个处于开发中的版本，变化快，中途变更不兼容
-- ------------------------------------------------

-- 每次发版时注意事项 ---
-- 1. datetime(0) 低版本不支持此语法，改为datetime
-- 2. utf8mb4 低版本不支持此字符集，改为utf8
-- 3. 加字段后记得排查是否有insert语句，需要同步修改
--
-- ------------------------从1.1.0版本升级------------------------

DROP TABLE IF EXISTS `api_custom_request`;
CREATE TABLE `api_custom_request` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
  `doc_id` bigint(20) DEFAULT NULL COMMENT 'api_doc主键ID',
  `folder_id` bigint(20) DEFAULT NULL COMMENT '文件夹ID',
  `api_name` varchar(250) DEFAULT NULL COMMENT '接口名称',
  `api_url` text DEFAULT NULL COMMENT '接口url',
  `form_data` text DEFAULT NULL COMMENT 'form参数',
  `body_data` text DEFAULT NULL COMMENT 'body参数',
  `header_data` text DEFAULT NULL COMMENT 'header参数',
  `cookie_data` text DEFAULT NULL COMMENT 'cookie参数',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `create_user_name` varchar(20) DEFAULT NULL COMMENT '创建人名字',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `yn` tinyint(4) DEFAULT NULL COMMENT '是否有效 0=无效 1=有效',
  PRIMARY KEY (`id`),
  KEY `idx_doc_id` (`doc_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='自建接口文档';

DROP TABLE IF EXISTS `api_custom_folder`;
CREATE TABLE `api_custom_folder` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
  `doc_id` bigint(20) DEFAULT NULL COMMENT 'api_doc主键ID',
  `parent_folder_id` bigint(20) DEFAULT NULL COMMENT '父文件夹ID',
  `folder_name` varchar(250) DEFAULT NULL COMMENT '文件夹名称',
  `folder_desc` text DEFAULT NULL COMMENT '文件夹说明',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `create_user_name` varchar(20) DEFAULT NULL COMMENT '创建人名字',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `yn` tinyint(4) DEFAULT NULL COMMENT '是否有效 0=无效 1=有效',
  PRIMARY KEY (`id`),
  KEY `idx_doc_id` (`doc_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='自建接口文档文件夹';


