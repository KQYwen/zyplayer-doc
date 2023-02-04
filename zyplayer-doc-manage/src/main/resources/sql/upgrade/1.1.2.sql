-- 每次发版时注意事项 ---
-- 1. datetime(0) 低版本不支持此语法，改为datetime
-- 2. utf8mb4 低版本不支持此字符集，改为utf8
-- 3. 加字段后记得排查是否有insert语句，需要同步修改

-- ------------------------从1.1.1版本升级------------------------
CREATE TABLE `system_config` (
	id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
	config_key varchar(100) null comment '配置Key',
	config_value varchar(500) null comment '配置值',

	created datetime null comment '创建时间',
	create_user_id bigint null comment '创建用户ID',
	create_user varchar(50) null comment '创建用户名字',
	modified datetime null comment '修改时间',
	modify_user_id bigint null comment '修改人ID',
	modify_user varchar(50) null comment '修改人姓名',
	yn tinyint default 1 not null comment '是否有效 1=有效',
	UNIQUE INDEX idx_config_key(config_key),
	PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT ='系统配置表';

ALTER TABLE wiki_page_history add COLUMN content mediumblob null;
ALTER TABLE wiki_page_content convert to character SET utf8mb4 COLLATE utf8mb4_unicode_ci;
alter table wiki_page_file modify column `file_name` varchar(256) NULL DEFAULT NULL COMMENT '文件名';
