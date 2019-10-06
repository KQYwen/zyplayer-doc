
--
-- ！！重要说明！！
-- 1、本sql文件分为：从1.0.4版本升级 和 全新的库，即增量和全量的区分，请选择性执行
-- 2、建议数据库版本：5.7.25
--

-- ------------------------从1.0.4版本升级：------------------------

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
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;


-- ------------------------全新的库：------------------------
