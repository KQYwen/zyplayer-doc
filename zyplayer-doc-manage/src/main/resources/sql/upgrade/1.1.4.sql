-- 每次发版时注意事项 ---
-- 1. datetime(0) 低版本不支持此语法，改为datetime
-- 2. utf8mb4 低版本不支持此字符集，改为utf8
-- 3. 加字段后记得排查是否有insert语句，需要同步修改

-- ------------------------从1.1.3版本升级------------------------

CREATE TABLE `backup_log`  (
	`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
	`dbId` bigint(20) NULL DEFAULT NULL COMMENT '数据源ID',
	`category` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '来源(手动备份|自动备份)',
	`database_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '库名',
	`tables_name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '表名',
	`data_type` tinyint(1) NULL DEFAULT NULL COMMENT '备份方式',
	`is_compress` tinyint(1) NULL DEFAULT NULL COMMENT '是否压缩',
	`is_upload` tinyint(1) NULL DEFAULT NULL COMMENT '是否上传',
	`is_delete` tinyint(1) NULL DEFAULT NULL COMMENT '是否删除',
	`file_path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件路径',
	`file_size` bigint(20) NULL DEFAULT NULL COMMENT '文件大小',
	`status` tinyint(1) NULL DEFAULT NULL COMMENT '备份状态（-1-失败0-备份中1-上传中2-成功）',
	`msg` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '返回信息',
	`start_time` datetime(0) NULL DEFAULT NULL COMMENT '开始时间',
	`end_time` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
	`spend_time` bigint(20) NULL DEFAULT NULL COMMENT '耗时(ms)',
	`del_flag` tinyint(1) NULL DEFAULT NULL COMMENT '删除状态（0--未删除1--已删除）',
	PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT ='备份记录表';

CREATE TABLE `backup_task`  (
	`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '备份任务信息主键',
	`dbId` bigint(20) NULL DEFAULT NULL COMMENT '数据源ID',
	`cron` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'cron表达式',
	`param` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参数',
	`status` tinyint(1) NULL DEFAULT NULL COMMENT '状态（0-停止1-启动）',
	`database_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '库名',
	`tables_name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '表名',
	`data_type` tinyint(1) NULL DEFAULT NULL COMMENT '备份方式',
	`remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
	`create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
	`del_flag` tinyint(4) NULL DEFAULT NULL COMMENT '删除状态（0--未删除1--已删除）',
	PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '备份任务信息';
