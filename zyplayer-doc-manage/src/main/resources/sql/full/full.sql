SET NAMES utf8mb4;

CREATE TABLE `auth_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
  `auth_name` varchar(50) NULL DEFAULT NULL COMMENT '权限名',
  `auth_desc` varchar(100) NULL DEFAULT NULL COMMENT '权限说明',
  `can_edit` tinyint(4) NULL DEFAULT 1 COMMENT '是否可编辑 0=否 1=是',
  `create_uid` bigint(20) NULL DEFAULT NULL COMMENT '创建人用户ID',
  `creation_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `auth_type` tinyint(4) NOT NULL DEFAULT 0 COMMENT '权限类型 0=隐藏权限 1=可使用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '权限信息表';

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
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

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
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

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
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

CREATE TABLE `user_auth`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户ID',
  `auth_id` bigint(20) NULL DEFAULT NULL COMMENT '权限ID',
  `create_uid` bigint(20) NULL DEFAULT NULL COMMENT '创建用户ID',
  `update_uid` bigint(20) NULL DEFAULT NULL COMMENT '更新用户ID',
  `del_flag` tinyint(4) NULL DEFAULT 0 COMMENT '是否删除 0=未删除 1=已删除',
  `creation_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `sys_type` int NULL COMMENT '系统类型，DocSysType',
  `sys_module_type` int NULL COMMENT '系统模块类型，DocSysModuleType',
  `sys_module_id` bigint(20) NULL COMMENT '系统模块ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户权限表';

CREATE TABLE `user_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
  `user_no` varchar(20) NULL DEFAULT NULL COMMENT '用户编号，用于登录等',
  `password` varchar(50) NULL DEFAULT NULL COMMENT '密码',
  `user_name` varchar(50) NULL DEFAULT NULL COMMENT '用户名',
  `email` varchar(50) NULL DEFAULT NULL COMMENT '邮箱',
  `avatar` varchar(100) NULL DEFAULT NULL COMMENT '头像',
  `del_flag` tinyint(4) NULL DEFAULT 0 COMMENT '是否删除 0=未删除 1=已删除 2=已停用',
  `creation_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_uid` bigint(20) NULL DEFAULT NULL COMMENT '创建人用户ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `phone` varchar(20) NULL DEFAULT NULL COMMENT '手机号',
  `sex` tinyint(4) NOT NULL DEFAULT 0 COMMENT '性别 0=女 1=男',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_userNo`(`user_no`) USING BTREE COMMENT '登录用户名'
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户信息表';

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
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

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
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

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
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE `wiki_page_file`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
  `page_id` bigint(20) NULL DEFAULT NULL COMMENT '页面ID',
  `file_name` varchar(256) NULL DEFAULT NULL COMMENT '文件名',
  `file_url` varchar(256) NULL DEFAULT NULL COMMENT '文件URL',
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
  `file_source` TINYINT(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_uuid`(`uuid`) USING BTREE COMMENT '文件ID'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

CREATE TABLE `wiki_page_history`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
  `page_id` bigint(20) NULL DEFAULT NULL COMMENT '页面ID',
  `create_user_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人ID',
  `create_user_name` varchar(20) NULL DEFAULT NULL COMMENT '创建人名字',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `del_flag` tinyint(4) NOT NULL DEFAULT 0 COMMENT '删除标记 0=正常 1=已删除',
  `content` mediumblob null,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_page_id`(`page_id`) USING BTREE COMMENT '页面ID索引'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

CREATE TABLE `wiki_page_zan`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
  `page_id` bigint(20) NULL DEFAULT NULL COMMENT '页面ID',
  `comment_id` bigint(20) NULL DEFAULT NULL COMMENT '评论ID',
  `create_user_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人ID',
  `create_user_name` varchar(20) NULL DEFAULT NULL COMMENT '创建人名字',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `yn` tinyint(4) NULL DEFAULT NULL COMMENT '是否有效 0=无效 1=有效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

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
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

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
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

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
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户消息表';

CREATE TABLE `user_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
  `name` varchar(50) DEFAULT NULL COMMENT '分组名',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `create_user_name` varchar(20) DEFAULT NULL COMMENT '创建人名字',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `del_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标记 0=正常 1=已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT='用户组';

CREATE TABLE `user_group_relation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
  `group_id` bigint(20) DEFAULT NULL COMMENT '群ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `create_user_name` varchar(20) DEFAULT NULL COMMENT '创建人名字',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `del_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标记 0=正常 1=已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT='用户和用户组关系表';

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
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT='用户组在各项目内的授权关系';

CREATE TABLE `wiki_space_favorite` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
  `space_id` bigint(20) DEFAULT NULL COMMENT '空间ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `del_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标记 0=正常 1=已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT='用户空间收藏记录表';

CREATE TABLE `user_setting` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `name` varchar(100) DEFAULT NULL COMMENT '设置的名字',
  `value` varchar(100) DEFAULT NULL COMMENT '设置的值',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `del_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标记 0=正常 1=已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT='用户设置表';

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
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT='数据库函数修改日志';

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
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT='表关系';

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
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT='api文档地址';

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
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT='文档请求参数记录';

CREATE TABLE `api_global_param` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
  `doc_id` bigint(20) DEFAULT NULL COMMENT 'api_doc主键ID',
  `param_type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '参数类型 1=form 2=header 3=cookie',
  `param_key` varchar(100) DEFAULT NULL COMMENT '参数名',
  `param_value` varchar(1024) DEFAULT NULL COMMENT '参数值',
  `param_status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态 1=启用 2=禁用',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `create_user_name` varchar(20) DEFAULT NULL COMMENT '创建人名字',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `yn` tinyint(4) DEFAULT NULL COMMENT '是否有效 0=无效 1=有效',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT='api文档全局参数记录';

CREATE TABLE `api_custom_node` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
  `doc_id` bigint(20) DEFAULT NULL COMMENT 'api_doc主键ID',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父文件夹ID',
  `node_type` tinyint(4) NOT NULL COMMENT '节点类型 0=目录 1=接口',
  `node_name` varchar(250) DEFAULT NULL COMMENT '节点名称',
  `node_desc` text DEFAULT NULL COMMENT '节点说明',
  `seq_no` int(11) DEFAULT NULL COMMENT '节点顺序',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `create_user_name` varchar(20) DEFAULT NULL COMMENT '创建人名字',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `yn` tinyint(4) DEFAULT NULL COMMENT '是否有效 0=无效 1=有效',
  PRIMARY KEY (`id`),
  KEY `idx_doc_id` (`doc_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT='自建接口文档节点';

CREATE TABLE `api_custom_params` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
  `doc_id` bigint(20) DEFAULT NULL COMMENT 'api_doc主键ID',
  `node_id` bigint(20) DEFAULT NULL COMMENT '节点ID',
  `method` varchar(20) DEFAULT NULL COMMENT '请求方式：get、head、post、put、patch、delete、options、trace',
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
  KEY `idx_doc_id` (`doc_id`),
  KEY `idx_node_id` (`node_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT='自建接口参数';

CREATE TABLE `system_config` (
	id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
	config_key varchar(100) null comment '配置Key',
	config_value varchar(500) null comment '配置值',

	created datetime null comment '创建时间',
	create_user_id bigint null comment '创建用户ID',
	create_user varchar(50) null comment '创建用户名字',
	modified datetime null comment '修改时间',
	modify_user_id bigint null comment '修改人ID',
	modify_user varchar(50) null comment '修改人姓名',
	yn tinyint default 1 not null comment '是否有效 1=有效',
	UNIQUE INDEX idx_config_key(config_key),
	PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT ='系统配置表';

CREATE TABLE `backup_log`  (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
    `dbId` bigint(20) NULL DEFAULT NULL COMMENT '数据源ID',
    `category` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '来源(手动备份|自动备份)',
    `database_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '库名',
    `tables_name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '表名',
    `data_type` tinyint(1) NULL DEFAULT NULL COMMENT '备份方式',
    `is_compress` tinyint(1) NULL DEFAULT NULL COMMENT '是否压缩',
    `is_upload` tinyint(1) NULL DEFAULT NULL COMMENT '是否上传',
    `is_delete` tinyint(1) NULL DEFAULT NULL COMMENT '是否删除',
    `file_path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件路径',
    `file_size` bigint(20) NULL DEFAULT NULL COMMENT '文件大小',
    `status` tinyint(1) NULL DEFAULT NULL COMMENT '备份状态（-1-失败0-备份中1-上传中2-成功）',
    `msg` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '返回信息',
    `start_time` datetime(0) NULL DEFAULT NULL COMMENT '开始时间',
    `end_time` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
    `spend_time` bigint(20) NULL DEFAULT NULL COMMENT '耗时(ms)',
    `del_flag` tinyint(1) NULL DEFAULT NULL COMMENT '删除状态（0--未删除1--已删除）',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT ='备份记录表';

CREATE TABLE `backup_task`  (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '备份任务信息主键',
    `dbId` bigint(20) NULL DEFAULT NULL COMMENT '数据源ID',
    `cron` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'cron表达式',
    `param` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参数',
    `status` tinyint(1) NULL DEFAULT NULL COMMENT '状态（0-停止1-启动）',
    `database_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '库名',
    `tables_name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '表名',
    `data_type` tinyint(1) NULL DEFAULT NULL COMMENT '备份方式',
    `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `del_flag` tinyint(4) NULL DEFAULT NULL COMMENT '删除状态（0--未删除1--已删除）',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '备份任务信息';

CREATE TABLE `wiki_page_template` (
	`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '模板信息主键',
	`space_id` bigint null COMMENT '空间id',
	`page_id` bigint null COMMENT '模板挂载文档id',
	`tag_name` varchar(50) null COMMENT '标签信息',
	`share_status` tinyint default 0 not null COMMENT '模板公开状态（0-个人模板1-公共模板）',

	`created` datetime null COMMENT '创建时间',
	`create_user_id` bigint null COMMENT '创建人id',
	`create_user` varchar(50) null COMMENT '创建人名称',
	`modified` datetime null comment '修改时间',
	`modify_user_id` bigint null comment '修改人ID',
	`modify_user` varchar(50) null comment '修改人姓名',
	`yn` tinyint default 1 not null comment '是否有效 1=有效',
	PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COMMENT = '模板信息表';
-- -------------------插入必要的数据-------------------
-- 用户信息
INSERT INTO `user_info` (id, user_no, password, user_name, email, del_flag, creation_time, update_time, sex)
VALUES (1, 'zyplayer', 'e10adc3949ba59abbe56e057f20f883e', '系统管理员', 'admin@zyplayer.com', 0, '2018-12-01 11:37:39', '2018-12-01 11:37:39', 1);
-- 权限列表
INSERT INTO `auth_info`(id, auth_name, auth_desc, can_edit, create_uid, creation_time, auth_type)
VALUES (1, 'AUTH_ASSIGN', '权限分配权', 0, 1, '2018-12-01 11:40:42', 1),
       (2, 'USER_MANAGE', '用户管理权', 0, 1, '2018-12-01 11:40:42', 1),
       (3, 'WIKI_EDIT_PAGE_', '编辑wiki文档', 0, 1, '2019-06-04 13:01:20', 0),
       (4, 'WIKI_VIEW_PAGE_', '查看wiki文档', 0, 1, '2019-06-04 13:01:20', 0),
       (5, 'WIKI_COMMENT_PAGE_', '评论wiki文档', 0, 1, '2019-06-04 13:01:20', 0),
       (6, 'WIKI_DELETE_PAGE_', '删除wiki文档', 0, 1, '2019-06-04 13:01:20', 0),
       (7, 'WIKI_PAGE_FILE_UPLOAD_', '上传wiki文档附件', 0, 1, '2019-06-04 13:01:20', 0),
       (8, 'WIKI_PAGE_FILE_DELETE_', '删除wiki文档附件', 0, 1, '2019-06-04 13:01:20', 0),
       (9, 'WIKI_PAGE_AUTH_MANAGE_', 'wiki权限管理', 0, 1, '2019-06-04 13:01:20', 0),
       (10, 'DB_DATASOURCE_MANAGE', 'DB数据源管理权', 0, 1, '2019-06-29 13:01:20', 1),
       (11, 'DB_VIEW_', '数据源查看权', 0, 1, '2019-08-18 23:25:17', 0),
       (12, 'DB_SELECT_', '数据源查询权', 0, 1, '2019-08-18 23:25:17', 0),
       (13, 'DB_UPDATE_', '数据源增删改查权', 0, 1, '2019-08-18 23:25:17', 0),
       (14, 'DB_DESC_EDIT_', '表字段注释修改权', 0, 1, '2019-08-18 23:25:17', 0),
       (15, 'DB_PROC_EDIT_', '存储过程修改权', 0, 1, '2021-04-24 23:25:17', 0),
       (16, 'API_DOC_MANAGE', 'api文档管理权', 0, 1, '2021-12-12 23:25:17', 0),
       (17, 'API_DOC_DEVELOPER', 'api文档编辑权', 0, 1, '2021-12-12 23:25:17', 0);
-- 用户权限
INSERT INTO `user_auth` (id, user_id, auth_id, create_uid, update_uid, del_flag, creation_time)
VALUES (1, 1, 1, 1, 1, 0, '2018-12-01 11:37:39'),
       (2, 1, 2, 1, 1, 0, '2018-12-01 11:37:39'),
       (3, 1, 10, 1, 1, 0, '2018-12-01 11:37:39');

