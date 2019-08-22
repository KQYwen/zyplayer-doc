
--
-- ！！重要说明！！
-- 1、本sql文件分为：从1.0.3版本升级 和 全新的库，即增量和全量的区分，请选择性执行
-- 2、建议数据库版本：5.7.25
--

-- ------------------------从1.0.3版本升级：------------------------

CREATE TABLE `db_favorite` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
  `name` varchar(50) DEFAULT NULL COMMENT '收藏标题',
  `content` varchar(10000) DEFAULT NULL COMMENT '收藏内容',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `create_user_name` varchar(20) DEFAULT NULL COMMENT '创建人名字',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `yn` tinyint(4) DEFAULT NULL COMMENT '是否有效 0=无效 1=有效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

CREATE TABLE `db_history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
  `content` varchar(10000) DEFAULT NULL COMMENT 'sql内容',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `create_user_name` varchar(20) DEFAULT NULL COMMENT '创建人名字',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `yn` tinyint(4) DEFAULT NULL COMMENT '是否有效 0=无效 1=有效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

INSERT INTO `auth_info`(`auth_name`, `auth_desc`, `can_edit`, `create_uid`, `creation_time`, `auth_type`)
VALUES ( 'DB_VIEW_', '数据源查看权', 0, 1, '2019-08-18 23:25:17', 0);
INSERT INTO `auth_info`(`auth_name`, `auth_desc`, `can_edit`, `create_uid`, `creation_time`, `auth_type`)
VALUES ( 'DB_SELECT_', '数据源查询权', 0, 1, '2019-08-18 23:25:17', 0);
INSERT INTO `auth_info`(`auth_name`, `auth_desc`, `can_edit`, `create_uid`, `creation_time`, `auth_type`)
VALUES ( 'DB_UPDATE_', '数据源增删改查权', 0, 1, '2019-08-18 23:25:17', 0);
INSERT INTO `auth_info`(`auth_name`, `auth_desc`, `can_edit`, `create_uid`, `creation_time`, `auth_type`)
VALUES ( 'DB_DESC_EDIT_', '表字段注释修改权', 0, 1, '2019-08-18 23:25:17', 0);


-- ------------------------全新的库：------------------------
