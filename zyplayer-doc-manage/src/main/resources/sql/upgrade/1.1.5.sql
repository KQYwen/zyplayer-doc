-- 每次发版时注意事项 ---
-- 1. datetime(0) 低版本不支持此语法，改为datetime
-- 2. utf8mb4 低版本不支持此字符集，改为utf8

-- ------------------------从1.1.5版本升级------------------------

CREATE TABLE `wiki_page_template`
(
    `id`             bigint(20)                         NOT NULL AUTO_INCREMENT COMMENT '模板信息逐渐',
    `space_id`       bigint(20)                         NULL     DEFAULT NULL COMMENT '空间id',
    `page_id`        bigint(20)                         NULL     DEFAULT NULL COMMENT '模板挂载文档id',
    `tag_name`       varchar(200) CHARACTER SET utf8mb4 NULL     DEFAULT NULL COMMENT '标签信息',
    `share_status`   tinyint(1)                         NULL     DEFAULT NULL COMMENT '模板公开状态（0-个人模板1-公共模板）',
    `created`        datetime                           NULL     DEFAULT NULL COMMENT '创建时间',
    `create_user_id` bigint(20)                         NULL     DEFAULT NULL COMMENT '创建人id',
    `create_user`    varchar(200) CHARACTER SET utf8mb4 NULL     DEFAULT NULL COMMENT '创建人名称',
    `yn`             tinyint(1)                         NOT NULL default '1',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4 COMMENT = '模板信息表';