-- ------------------------------------------------
-- 这是一个处于开发中的版本，变化快，中途变更不兼容
-- ------------------------------------------------

-- 每次发版时注意事项 ---
-- 1. datetime(0) 低版本不支持此语法，改为datetime
-- 2. utf8mb4 低版本不支持此字符集，改为utf8
-- 3. 加字段后记得排查是否有insert语句，需要同步修改
--
-- ------------------------从1.0.9版本升级------------------------

DROP TABLE IF EXISTS `api_doc`;
CREATE TABLE `api_doc` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
  `name` varchar(100) DEFAULT NULL COMMENT '文档名称',
  `doc_type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '文档类型 1=swagger url 2=swagger json 3=openapi url 4=openapi json 5=自建API分组',
  `doc_url` varchar(250) DEFAULT NULL COMMENT '文档URL地址',
  `json_content` mediumtext DEFAULT NULL COMMENT '文档json内容',
  `rewrite_domain` varchar(100) DEFAULT NULL COMMENT '重写的域名',
  `open_visit` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否开放访问 0=否 1=是',
  `doc_status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态 1=启用 2=禁用',
  `share_uuid` varchar(50) DEFAULT NULL COMMENT '开放文档UUID',
  `share_instruction` mediumtext DEFAULT NULL COMMENT '开放文档使用说明',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `create_user_name` varchar(20) DEFAULT NULL COMMENT '创建人名字',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `yn` tinyint(4) DEFAULT NULL COMMENT '是否有效 0=无效 1=有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='api文档地址';

DROP TABLE IF EXISTS `api_request_param`;
CREATE TABLE `api_request_param` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
  `doc_id` bigint(20) DEFAULT NULL COMMENT 'api_doc主键ID',
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
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='文档请求参数记录';

DROP TABLE IF EXISTS `api_global_param`;
CREATE TABLE `api_global_param` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
  `param_type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '参数类型 1=form 2=header 3=cookie',
  `param_key` varchar(100) DEFAULT NULL COMMENT '参数名',
  `param_value` varchar(1024) DEFAULT NULL COMMENT '参数值',
  `param_status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态 1=启用 2=禁用',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `create_user_name` varchar(20) DEFAULT NULL COMMENT '创建人名字',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `yn` tinyint(4) DEFAULT NULL COMMENT '是否有效 0=无效 1=有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='api文档全局参数记录';

-- 权限表优化
ALTER TABLE `user_auth` ADD COLUMN `sys_type` int NULL COMMENT '系统类型，DocSysType',
ADD COLUMN `sys_module_type` int NULL COMMENT '系统模块类型，DocSysModuleType',
ADD COLUMN `sys_module_id` bigint(20) NULL COMMENT '系统模块ID';
-- 历史数据处理
update user_auth set sys_type=3, sys_module_type=1, sys_module_id=replace(auth_custom_suffix, 'DB_', '') where auth_custom_suffix like 'DB_%';
update user_auth set sys_type=2, sys_module_type=1, sys_module_id=replace(auth_custom_suffix, 'WIKI_', '') where auth_custom_suffix like 'WIKI_%';


INSERT INTO `auth_info`(auth_name, auth_desc, can_edit, create_uid, creation_time, auth_type) VALUES
('API_DOC_MANAGE', 'api文档管理权', 0, 1, '2021-12-12 23:25:17', 0),
('API_DOC_DEVELOPER', 'api文档编辑权', 0, 1, '2021-12-12 23:25:17', 0);

