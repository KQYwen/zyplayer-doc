-- 每次发版时注意事项 ---
-- 1. datetime(0) 低版本不支持此语法，改为datetime
-- 2. utf8mb4 低版本不支持此字符集，改为utf8

-- ------------------------从1.1.5版本升级------------------------
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

ALTER TABLE `wiki_page_file` ADD COLUMN `file_source` TINYINT(4) NOT NULL DEFAULT '1';

