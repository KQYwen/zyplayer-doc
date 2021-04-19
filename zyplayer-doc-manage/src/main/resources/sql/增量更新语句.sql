-- 每次发版时注意事项 ---
-- 1. datetime(0) 低版本不支持此语法，改为datetime
-- 2. utf8mb4 低版本不支持此字符集，改为utf8
-- 3. 加字段后记得排查是否有insert语句，需要同步修改
--
-- ------------------------从1.0.7版本升级------------------------

CREATE TABLE `user_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
  `name` varchar(50) DEFAULT NULL COMMENT '分组名',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `create_user_name` varchar(20) DEFAULT NULL COMMENT '创建人名字',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `del_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标记 0=正常 1=已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='用户组';

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

CREATE TABLE `wiki_space_favorite` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
  `space_id` bigint(20) DEFAULT NULL COMMENT '空间ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `del_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标记 0=正常 1=已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='用户空间收藏记录表';

CREATE TABLE `user_setting` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `name` varchar(100) DEFAULT NULL COMMENT '设置的名字',
  `value` varchar(100) DEFAULT NULL COMMENT '设置的值',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `del_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标记 0=正常 1=已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='用户设置表';


