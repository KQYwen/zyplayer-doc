
--
-- ！！重要说明！！
-- 1、本sql文件分为：从1.0.5版本升级 和 全新的库，即增量和全量的区分，请选择性执行
-- 2、建议数据库版本：5.7.25
--
-- ==导出注意==
-- datetime(0) 低版本不支持此语法，改为datetime
-- utf8mb4 低版本不支持此字符集，改为utf8
-- 加字段后记得排查是否有insert语句，需要同步修改
--
-- ------------------------从1.0.5版本升级：------------------------
ALTER TABLE `db_datasource`
ADD COLUMN `group_name` varchar(50) NULL COMMENT '数据源分组名';
ALTER TABLE `wiki_page`
ADD COLUMN `editor_type` tinyint(4) NOT NULL DEFAULT 1 COMMENT '编辑框类型 1=HTML 2=Markdown';

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
