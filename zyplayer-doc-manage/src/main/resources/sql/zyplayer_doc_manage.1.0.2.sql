
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

-- 全新的库：

