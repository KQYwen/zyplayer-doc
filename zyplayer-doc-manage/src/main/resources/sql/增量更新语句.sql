-- 每次发版时注意事项 ---
-- 1. datetime(0) 低版本不支持此语法，改为datetime
-- 2. utf8mb4 低版本不支持此字符集，改为utf8
-- 3. 加字段后记得排查是否有insert语句，需要同步修改
--
-- ------------------------从1.0.8版本升级------------------------

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



