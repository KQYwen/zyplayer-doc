
--
-- ！！重要说明！！
-- 1、本sql文件分为：从1.0.2版本升级 和 全新的库，即增量和全量的区分，请选择性执行
-- 2、建议数据库版本：5.7.25
--

-- 从1.0.2版本升级：
CREATE TABLE `db_datasource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
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

insert into user_auth(`user_id`, `auth_id`, `create_uid`, `del_flag`, `creation_time`)
select a.id, b.id, a.id, 0, now() from user_info a,auth_info b
where a.user_no='zyplayer' and b.auth_name='DB_DATASOURCE_MANAGE';

ALTER TABLE `wiki_page_content` MODIFY COLUMN `preview` varchar(16000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '预览内容';

-- ------------------------全新的库：------------------------



