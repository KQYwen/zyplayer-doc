--
-- ！！重要说明！！
-- 1、本sql文件分为：从1.0.6版本升级 和 全新的库，即增量和全量的区分，请选择性执行
-- 2、建议数据库版本：5.7.25
--
-- ==导出注意==
-- 1. datetime(0) 低版本不支持此语法，改为datetime
-- 2. utf8mb4 低版本不支持此字符集，改为utf8
-- 3. 加字段后记得排查是否有insert语句，需要同步修改
--
-- ------------------------从1.0.6版本升级------------------------
ALTER TABLE `wiki_page_history`
DROP COLUMN `content`,
ADD COLUMN `git_commit_id` varchar(50) NULL COMMENT 'git提交记录ID';

ALTER TABLE `wiki_page_file`
ADD COLUMN `file_size` bigint NULL COMMENT '文件大小';
