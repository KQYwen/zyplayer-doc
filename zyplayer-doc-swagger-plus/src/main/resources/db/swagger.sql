
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



