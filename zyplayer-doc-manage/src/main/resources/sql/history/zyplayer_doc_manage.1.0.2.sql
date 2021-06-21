
--
-- ！！重要说明！！
-- 1、本sql文件分为：从1.0.1版本升级 和 全新的库，即增量和全量的区分，请选择性执行
-- 2、建议数据库版本：5.7.25
--

-- 从1.0.1版本升级：
ALTER TABLE `user_auth` ADD COLUMN `auth_custom_suffix` varchar(100) NULL COMMENT '自定义权限结尾';

INSERT INTO `auth_info`(`auth_name`, `auth_desc`, `can_edit`, `create_uid`, `creation_time`)
VALUES
( 'WIKI_CREATE_PAGE_', '创建wiki文档', 0, 1, '2019-06-04 13:01:20')
,('WIKI_EDIT_PAGE_', '编辑wiki文档', 0, 1, '2019-06-04 13:01:20')
,('WIKI_COMMENT_PAGE_', '评论wiki文档', 0, 1, '2019-06-04 13:01:20')
,('WIKI_DELETE_PAGE_', '删除wiki文档', 0, 1, '2019-06-04 13:01:20')
,('WIKI_PAGE_FILE_UPLOAD_', '上传wiki文档附件', 0, 1, '2019-06-04 13:01:20')
,('WIKI_PAGE_FILE_DELETE_', '删除wiki文档附件', 0, 1, '2019-06-04 13:01:20')
,('WIKI_PAGE_AUTH_MANAGE_', 'wiki权限管理', 0, 1, '2019-06-04 13:01:20')
;

ALTER TABLE `wiki_page` ADD COLUMN `view_num` int NOT NULL DEFAULT 0 COMMENT '阅读数';
ALTER TABLE `wiki_page` ADD COLUMN `seq_no` int NOT NULL DEFAULT 0 COMMENT '顺序';
-- 初始化seq
UPDATE wiki_page SET seq_no=id WHERE del_flag=0;

ALTER TABLE `wiki_page_content` ADD COLUMN `preview` varchar(1024) NULL COMMENT '预览内容';
ALTER TABLE `wiki_page_file` ADD COLUMN `download_num` int NOT NULL DEFAULT 0 COMMENT '下载次数';

CREATE TABLE `wiki_page_history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
  `page_id` bigint(20) DEFAULT NULL COMMENT '页面ID',
  `content` mediumtext COMMENT '本次文章内容',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `create_user_name` varchar(20) DEFAULT NULL COMMENT '创建人名字',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `del_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标记 0=正常 1=已删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_page_id` (`page_id`) USING BTREE COMMENT '页面ID索引'
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

ALTER TABLE `user_info` ADD COLUMN `phone` varchar(20) NULL COMMENT '手机号';
ALTER TABLE `user_info` ADD COLUMN `sex` tinyint NOT NULL DEFAULT 0 COMMENT '性别 0=女 1=男';
ALTER TABLE `user_info` MODIFY COLUMN `del_flag` tinyint(4) NULL DEFAULT 0 COMMENT '是否删除 0=未删除 1=已删除 2=已停用';

ALTER TABLE `auth_info` ADD COLUMN `auth_type` tinyint(4) NOT NULL DEFAULT 0 COMMENT '权限类型 0=隐藏权限 1=可使用';
update auth_info set auth_type=1 where auth_name in('DOC_ALL', 'AUTH_MANAGE', 'AUTH_ASSIGN', 'USER_MANAGE');
-- 不需要
delete from auth_info where auth_name in('DOC_ALL', 'AUTH_MANAGE');
