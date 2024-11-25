  -- create "api_custom_node" table:
    CREATE TABLE "zyplayer_doc"."api_custom_node" (
         "id" serial NOT NULL,
         "doc_id" bigint NULL,
         "parent_id" bigint NULL,
         "node_type" integer NOT NULL,
         "node_name" character varying(250) NULL,
         "node_desc" text NULL,
         "seq_no" integer NULL,
         "create_user_id" bigint NULL,
         "create_user_name" character varying(20) NULL,
         "create_time" timestamp NULL,
         "yn" integer NULL,
         PRIMARY KEY ("id")
       );
  -- create index "idx_doc_id" to table: "api_custom_node":
    CREATE INDEX "api_custom_node_idx_doc_id" ON "zyplayer_doc"."api_custom_node" ("doc_id");
  -- set comment to table: "api_custom_node":
    COMMENT ON TABLE "zyplayer_doc"."api_custom_node" IS '自建接口文档节点';
  -- set comment to column: "id" on table: "api_custom_node":
    COMMENT ON COLUMN "zyplayer_doc"."api_custom_node"."id" IS '主键自增ID';
  -- set comment to column: "doc_id" on table: "api_custom_node":
    COMMENT ON COLUMN "zyplayer_doc"."api_custom_node"."doc_id" IS 'api_doc主键ID';
  -- set comment to column: "parent_id" on table: "api_custom_node":
    COMMENT ON COLUMN "zyplayer_doc"."api_custom_node"."parent_id" IS '父文件夹ID';
  -- set comment to column: "node_type" on table: "api_custom_node":
    COMMENT ON COLUMN "zyplayer_doc"."api_custom_node"."node_type" IS '节点类型 0=目录 1=接口';
  -- set comment to column: "node_name" on table: "api_custom_node":
    COMMENT ON COLUMN "zyplayer_doc"."api_custom_node"."node_name" IS '节点名称';
  -- set comment to column: "node_desc" on table: "api_custom_node":
    COMMENT ON COLUMN "zyplayer_doc"."api_custom_node"."node_desc" IS '节点说明';
  -- set comment to column: "seq_no" on table: "api_custom_node":
    COMMENT ON COLUMN "zyplayer_doc"."api_custom_node"."seq_no" IS '节点顺序';
  -- set comment to column: "create_user_id" on table: "api_custom_node":
    COMMENT ON COLUMN "zyplayer_doc"."api_custom_node"."create_user_id" IS '创建人ID';
  -- set comment to column: "create_user_name" on table: "api_custom_node":
    COMMENT ON COLUMN "zyplayer_doc"."api_custom_node"."create_user_name" IS '创建人名字';
  -- set comment to column: "create_time" on table: "api_custom_node":
    COMMENT ON COLUMN "zyplayer_doc"."api_custom_node"."create_time" IS '创建时间';
  -- set comment to column: "yn" on table: "api_custom_node":
    COMMENT ON COLUMN "zyplayer_doc"."api_custom_node"."yn" IS '是否有效 0=无效 1=有效';
  -- create "api_custom_params" table:
    CREATE TABLE "zyplayer_doc"."api_custom_params" (
         "id" serial NOT NULL,
         "doc_id" bigint NULL,
         "node_id" bigint NULL,
         "method" character varying(20) NULL,
         "api_url" text NULL,
         "form_data" text NULL,
         "body_data" text NULL,
         "header_data" text NULL,
         "cookie_data" text NULL,
         "create_user_id" bigint NULL,
         "create_user_name" character varying(20) NULL,
         "create_time" timestamp NULL,
         "yn" integer NULL,
         PRIMARY KEY ("id")
       );
  -- create index "idx_doc_id" to table: "api_custom_params":
    CREATE INDEX "api_custom_params_idx_doc_id" ON "zyplayer_doc"."api_custom_params" ("doc_id");
  -- create index "idx_node_id" to table: "api_custom_params":
    CREATE INDEX "api_custom_params_idx_node_id" ON "zyplayer_doc"."api_custom_params" ("node_id");
  -- set comment to table: "api_custom_params":
    COMMENT ON TABLE "zyplayer_doc"."api_custom_params" IS '自建接口参数';
  -- set comment to column: "id" on table: "api_custom_params":
    COMMENT ON COLUMN "zyplayer_doc"."api_custom_params"."id" IS '主键自增ID';
  -- set comment to column: "doc_id" on table: "api_custom_params":
    COMMENT ON COLUMN "zyplayer_doc"."api_custom_params"."doc_id" IS 'api_doc主键ID';
  -- set comment to column: "node_id" on table: "api_custom_params":
    COMMENT ON COLUMN "zyplayer_doc"."api_custom_params"."node_id" IS '节点ID';
  -- set comment to column: "method" on table: "api_custom_params":
    COMMENT ON COLUMN "zyplayer_doc"."api_custom_params"."method" IS '请求方式：get、head、post、put、patch、delete、options、trace';
  -- set comment to column: "api_url" on table: "api_custom_params":
    COMMENT ON COLUMN "zyplayer_doc"."api_custom_params"."api_url" IS '接口url';
  -- set comment to column: "form_data" on table: "api_custom_params":
    COMMENT ON COLUMN "zyplayer_doc"."api_custom_params"."form_data" IS 'form参数';
  -- set comment to column: "body_data" on table: "api_custom_params":
    COMMENT ON COLUMN "zyplayer_doc"."api_custom_params"."body_data" IS 'body参数';
  -- set comment to column: "header_data" on table: "api_custom_params":
    COMMENT ON COLUMN "zyplayer_doc"."api_custom_params"."header_data" IS 'header参数';
  -- set comment to column: "cookie_data" on table: "api_custom_params":
    COMMENT ON COLUMN "zyplayer_doc"."api_custom_params"."cookie_data" IS 'cookie参数';
  -- set comment to column: "create_user_id" on table: "api_custom_params":
    COMMENT ON COLUMN "zyplayer_doc"."api_custom_params"."create_user_id" IS '创建人ID';
  -- set comment to column: "create_user_name" on table: "api_custom_params":
    COMMENT ON COLUMN "zyplayer_doc"."api_custom_params"."create_user_name" IS '创建人名字';
  -- set comment to column: "create_time" on table: "api_custom_params":
    COMMENT ON COLUMN "zyplayer_doc"."api_custom_params"."create_time" IS '创建时间';
  -- set comment to column: "yn" on table: "api_custom_params":
    COMMENT ON COLUMN "zyplayer_doc"."api_custom_params"."yn" IS '是否有效 0=无效 1=有效';
  -- create "api_doc" table:
    CREATE TABLE "zyplayer_doc"."api_doc" (
         "id" serial NOT NULL,
         "name" character varying(100) NULL,
         "doc_type" integer NOT NULL DEFAULT 1,
         "doc_url" character varying(250) NULL,
         "json_content" text NULL,
         "rewrite_domain" character varying(100) NULL,
         "open_visit" integer NOT NULL DEFAULT 0,
         "doc_status" integer NOT NULL DEFAULT 1,
         "share_uuid" character varying(50) NULL,
         "share_instruction" text NULL,
         "create_user_id" bigint NULL,
         "create_user_name" character varying(20) NULL,
         "create_time" timestamp NULL,
         "yn" integer NULL,
         PRIMARY KEY ("id")
       );
  -- set comment to table: "api_doc":
    COMMENT ON TABLE "zyplayer_doc"."api_doc" IS 'api文档地址';
  -- set comment to column: "id" on table: "api_doc":
    COMMENT ON COLUMN "zyplayer_doc"."api_doc"."id" IS '主键自增ID';
  -- set comment to column: "name" on table: "api_doc":
    COMMENT ON COLUMN "zyplayer_doc"."api_doc"."name" IS '文档名称';
  -- set comment to column: "doc_type" on table: "api_doc":
    COMMENT ON COLUMN "zyplayer_doc"."api_doc"."doc_type" IS '文档类型 1=swagger url 2=swagger json 3=openapi url 4=openapi json 5=自建API分组';
  -- set comment to column: "doc_url" on table: "api_doc":
    COMMENT ON COLUMN "zyplayer_doc"."api_doc"."doc_url" IS '文档URL地址';
  -- set comment to column: "json_content" on table: "api_doc":
    COMMENT ON COLUMN "zyplayer_doc"."api_doc"."json_content" IS '文档json内容';
  -- set comment to column: "rewrite_domain" on table: "api_doc":
    COMMENT ON COLUMN "zyplayer_doc"."api_doc"."rewrite_domain" IS '重写的域名';
  -- set comment to column: "open_visit" on table: "api_doc":
    COMMENT ON COLUMN "zyplayer_doc"."api_doc"."open_visit" IS '是否开放访问 0=否 1=是';
  -- set comment to column: "doc_status" on table: "api_doc":
    COMMENT ON COLUMN "zyplayer_doc"."api_doc"."doc_status" IS '状态 1=启用 2=禁用';
  -- set comment to column: "share_uuid" on table: "api_doc":
    COMMENT ON COLUMN "zyplayer_doc"."api_doc"."share_uuid" IS '开放文档UUID';
  -- set comment to column: "share_instruction" on table: "api_doc":
    COMMENT ON COLUMN "zyplayer_doc"."api_doc"."share_instruction" IS '开放文档使用说明';
  -- set comment to column: "create_user_id" on table: "api_doc":
    COMMENT ON COLUMN "zyplayer_doc"."api_doc"."create_user_id" IS '创建人ID';
  -- set comment to column: "create_user_name" on table: "api_doc":
    COMMENT ON COLUMN "zyplayer_doc"."api_doc"."create_user_name" IS '创建人名字';
  -- set comment to column: "create_time" on table: "api_doc":
    COMMENT ON COLUMN "zyplayer_doc"."api_doc"."create_time" IS '创建时间';
  -- set comment to column: "yn" on table: "api_doc":
    COMMENT ON COLUMN "zyplayer_doc"."api_doc"."yn" IS '是否有效 0=无效 1=有效';
  -- create "api_global_param" table:
    CREATE TABLE "zyplayer_doc"."api_global_param" (
         "id" serial NOT NULL,
         "doc_id" bigint NULL,
         "param_type" integer NOT NULL DEFAULT 1,
         "param_key" character varying(100) NULL,
         "param_value" character varying(1024) NULL,
         "param_status" integer NOT NULL DEFAULT 1,
         "create_user_id" bigint NULL,
         "create_user_name" character varying(20) NULL,
         "create_time" timestamp NULL,
         "yn" integer NULL,
         PRIMARY KEY ("id")
       );
  -- set comment to table: "api_global_param":
    COMMENT ON TABLE "zyplayer_doc"."api_global_param" IS 'api文档全局参数记录';
  -- set comment to column: "id" on table: "api_global_param":
    COMMENT ON COLUMN "zyplayer_doc"."api_global_param"."id" IS '主键自增ID';
  -- set comment to column: "doc_id" on table: "api_global_param":
    COMMENT ON COLUMN "zyplayer_doc"."api_global_param"."doc_id" IS 'api_doc主键ID';
  -- set comment to column: "param_type" on table: "api_global_param":
    COMMENT ON COLUMN "zyplayer_doc"."api_global_param"."param_type" IS '参数类型 1=form 2=header 3=cookie';
  -- set comment to column: "param_key" on table: "api_global_param":
    COMMENT ON COLUMN "zyplayer_doc"."api_global_param"."param_key" IS '参数名';
  -- set comment to column: "param_value" on table: "api_global_param":
    COMMENT ON COLUMN "zyplayer_doc"."api_global_param"."param_value" IS '参数值';
  -- set comment to column: "param_status" on table: "api_global_param":
    COMMENT ON COLUMN "zyplayer_doc"."api_global_param"."param_status" IS '状态 1=启用 2=禁用';
  -- set comment to column: "create_user_id" on table: "api_global_param":
    COMMENT ON COLUMN "zyplayer_doc"."api_global_param"."create_user_id" IS '创建人ID';
  -- set comment to column: "create_user_name" on table: "api_global_param":
    COMMENT ON COLUMN "zyplayer_doc"."api_global_param"."create_user_name" IS '创建人名字';
  -- set comment to column: "create_time" on table: "api_global_param":
    COMMENT ON COLUMN "zyplayer_doc"."api_global_param"."create_time" IS '创建时间';
  -- set comment to column: "yn" on table: "api_global_param":
    COMMENT ON COLUMN "zyplayer_doc"."api_global_param"."yn" IS '是否有效 0=无效 1=有效';
  -- create "api_request_param" table:
    CREATE TABLE "zyplayer_doc"."api_request_param" (
         "id" serial NOT NULL,
         "doc_id" bigint NULL,
         "doc_url" character varying(250) NULL,
         "form_data" text NULL,
         "body_data" text NULL,
         "header_data" character varying(1024) NULL,
         "cookie_data" character varying(1024) NULL,
         "create_user_id" bigint NULL,
         "create_user_name" character varying(20) NULL,
         "create_time" timestamp NULL,
         "yn" integer NULL,
         PRIMARY KEY ("id")
       );
  -- set comment to table: "api_request_param":
    COMMENT ON TABLE "zyplayer_doc"."api_request_param" IS '文档请求参数记录';
  -- set comment to column: "id" on table: "api_request_param":
    COMMENT ON COLUMN "zyplayer_doc"."api_request_param"."id" IS '主键自增ID';
  -- set comment to column: "doc_id" on table: "api_request_param":
    COMMENT ON COLUMN "zyplayer_doc"."api_request_param"."doc_id" IS 'api_doc主键ID';
  -- set comment to column: "doc_url" on table: "api_request_param":
    COMMENT ON COLUMN "zyplayer_doc"."api_request_param"."doc_url" IS '文档url';
  -- set comment to column: "form_data" on table: "api_request_param":
    COMMENT ON COLUMN "zyplayer_doc"."api_request_param"."form_data" IS 'form参数';
  -- set comment to column: "body_data" on table: "api_request_param":
    COMMENT ON COLUMN "zyplayer_doc"."api_request_param"."body_data" IS 'body参数';
  -- set comment to column: "header_data" on table: "api_request_param":
    COMMENT ON COLUMN "zyplayer_doc"."api_request_param"."header_data" IS 'header参数';
  -- set comment to column: "cookie_data" on table: "api_request_param":
    COMMENT ON COLUMN "zyplayer_doc"."api_request_param"."cookie_data" IS 'cookie参数';
  -- set comment to column: "create_user_id" on table: "api_request_param":
    COMMENT ON COLUMN "zyplayer_doc"."api_request_param"."create_user_id" IS '创建人ID';
  -- set comment to column: "create_user_name" on table: "api_request_param":
    COMMENT ON COLUMN "zyplayer_doc"."api_request_param"."create_user_name" IS '创建人名字';
  -- set comment to column: "create_time" on table: "api_request_param":
    COMMENT ON COLUMN "zyplayer_doc"."api_request_param"."create_time" IS '创建时间';
  -- set comment to column: "yn" on table: "api_request_param":
    COMMENT ON COLUMN "zyplayer_doc"."api_request_param"."yn" IS '是否有效 0=无效 1=有效';
  -- create "auth_info" table:
    CREATE TABLE "zyplayer_doc"."auth_info" (
         "id" serial NOT NULL,
         "auth_name" character varying(50) NULL,
         "auth_desc" character varying(100) NULL,
         "can_edit" integer NULL DEFAULT 1,
         "create_uid" bigint NULL,
         "creation_time" timestamp NULL,
         "auth_type" integer NOT NULL DEFAULT 0,
         PRIMARY KEY ("id")
       );
  -- set comment to table: "auth_info":
    COMMENT ON TABLE "zyplayer_doc"."auth_info" IS '权限信息表';
  -- set comment to column: "id" on table: "auth_info":
    COMMENT ON COLUMN "zyplayer_doc"."auth_info"."id" IS '主键自增ID';
  -- set comment to column: "auth_name" on table: "auth_info":
    COMMENT ON COLUMN "zyplayer_doc"."auth_info"."auth_name" IS '权限名';
  -- set comment to column: "auth_desc" on table: "auth_info":
    COMMENT ON COLUMN "zyplayer_doc"."auth_info"."auth_desc" IS '权限说明';
  -- set comment to column: "can_edit" on table: "auth_info":
    COMMENT ON COLUMN "zyplayer_doc"."auth_info"."can_edit" IS '是否可编辑 0=否 1=是';
  -- set comment to column: "create_uid" on table: "auth_info":
    COMMENT ON COLUMN "zyplayer_doc"."auth_info"."create_uid" IS '创建人用户ID';
  -- set comment to column: "creation_time" on table: "auth_info":
    COMMENT ON COLUMN "zyplayer_doc"."auth_info"."creation_time" IS '创建时间';
  -- set comment to column: "auth_type" on table: "auth_info":
    COMMENT ON COLUMN "zyplayer_doc"."auth_info"."auth_type" IS '权限类型 0=隐藏权限 1=可使用';
  -- create "backup_log" table:
    CREATE TABLE "zyplayer_doc"."backup_log" (
         "id" serial NOT NULL,
         "dbId" bigint NULL,
         "category" character varying(10) NULL,
         "database_name" character varying(100) NULL,
         "tables_name" text NULL,
         "data_type" boolean NULL,
         "is_compress" boolean NULL,
         "is_upload" boolean NULL,
         "is_delete" boolean NULL,
         "file_path" character varying(200) NULL,
         "file_size" bigint NULL,
         "status" boolean NULL,
         "msg" character varying(200) NULL,
         "start_time" timestamp NULL,
         "end_time" timestamp NULL,
         "spend_time" bigint NULL,
         "del_flag" boolean NULL,
         PRIMARY KEY ("id")
       );
  -- set comment to table: "backup_log":
    COMMENT ON TABLE "zyplayer_doc"."backup_log" IS '备份记录表';
  -- set comment to column: "id" on table: "backup_log":
    COMMENT ON COLUMN "zyplayer_doc"."backup_log"."id" IS '主键自增ID';
  -- set comment to column: "dbid" on table: "backup_log":
    COMMENT ON COLUMN "zyplayer_doc"."backup_log"."dbId" IS '数据源ID';
  -- set comment to column: "category" on table: "backup_log":
    COMMENT ON COLUMN "zyplayer_doc"."backup_log"."category" IS '来源(手动备份|自动备份)';
  -- set comment to column: "database_name" on table: "backup_log":
    COMMENT ON COLUMN "zyplayer_doc"."backup_log"."database_name" IS '库名';
  -- set comment to column: "tables_name" on table: "backup_log":
    COMMENT ON COLUMN "zyplayer_doc"."backup_log"."tables_name" IS '表名';
  -- set comment to column: "data_type" on table: "backup_log":
    COMMENT ON COLUMN "zyplayer_doc"."backup_log"."data_type" IS '备份方式';
  -- set comment to column: "is_compress" on table: "backup_log":
    COMMENT ON COLUMN "zyplayer_doc"."backup_log"."is_compress" IS '是否压缩';
  -- set comment to column: "is_upload" on table: "backup_log":
    COMMENT ON COLUMN "zyplayer_doc"."backup_log"."is_upload" IS '是否上传';
  -- set comment to column: "is_delete" on table: "backup_log":
    COMMENT ON COLUMN "zyplayer_doc"."backup_log"."is_delete" IS '是否删除';
  -- set comment to column: "file_path" on table: "backup_log":
    COMMENT ON COLUMN "zyplayer_doc"."backup_log"."file_path" IS '文件路径';
  -- set comment to column: "file_size" on table: "backup_log":
    COMMENT ON COLUMN "zyplayer_doc"."backup_log"."file_size" IS '文件大小';
  -- set comment to column: "status" on table: "backup_log":
    COMMENT ON COLUMN "zyplayer_doc"."backup_log"."status" IS '备份状态（-1-失败0-备份中1-上传中2-成功）';
  -- set comment to column: "msg" on table: "backup_log":
    COMMENT ON COLUMN "zyplayer_doc"."backup_log"."msg" IS '返回信息';
  -- set comment to column: "start_time" on table: "backup_log":
    COMMENT ON COLUMN "zyplayer_doc"."backup_log"."start_time" IS '开始时间';
  -- set comment to column: "end_time" on table: "backup_log":
    COMMENT ON COLUMN "zyplayer_doc"."backup_log"."end_time" IS '结束时间';
  -- set comment to column: "spend_time" on table: "backup_log":
    COMMENT ON COLUMN "zyplayer_doc"."backup_log"."spend_time" IS '耗时(ms)';
  -- set comment to column: "del_flag" on table: "backup_log":
    COMMENT ON COLUMN "zyplayer_doc"."backup_log"."del_flag" IS '删除状态（0--未删除1--已删除）';
  -- create "backup_task" table:
    CREATE TABLE "zyplayer_doc"."backup_task" (
         "id" serial NOT NULL,
         "dbId" bigint NULL,
         "cron" character varying(200) NULL,
         "param" character varying(200) NULL,
         "status" boolean NULL,
         "database_name" character varying(200) NULL,
         "tables_name" text NULL,
         "data_type" boolean NULL,
         "remark" character varying(200) NULL,
         "create_time" timestamp NULL,
         "del_flag" integer NULL,
         PRIMARY KEY ("id")
       );
  -- set comment to table: "backup_task":
    COMMENT ON TABLE "zyplayer_doc"."backup_task" IS '备份任务信息';
  -- set comment to column: "id" on table: "backup_task":
    COMMENT ON COLUMN "zyplayer_doc"."backup_task"."id" IS '备份任务信息主键';
  -- set comment to column: "dbid" on table: "backup_task":
    COMMENT ON COLUMN "zyplayer_doc"."backup_task"."dbId" IS '数据源ID';
  -- set comment to column: "cron" on table: "backup_task":
    COMMENT ON COLUMN "zyplayer_doc"."backup_task"."cron" IS 'cron表达式';
  -- set comment to column: "param" on table: "backup_task":
    COMMENT ON COLUMN "zyplayer_doc"."backup_task"."param" IS '参数';
  -- set comment to column: "status" on table: "backup_task":
    COMMENT ON COLUMN "zyplayer_doc"."backup_task"."status" IS '状态（0-停止1-启动）';
  -- set comment to column: "database_name" on table: "backup_task":
    COMMENT ON COLUMN "zyplayer_doc"."backup_task"."database_name" IS '库名';
  -- set comment to column: "tables_name" on table: "backup_task":
    COMMENT ON COLUMN "zyplayer_doc"."backup_task"."tables_name" IS '表名';
  -- set comment to column: "data_type" on table: "backup_task":
    COMMENT ON COLUMN "zyplayer_doc"."backup_task"."data_type" IS '备份方式';
  -- set comment to column: "remark" on table: "backup_task":
    COMMENT ON COLUMN "zyplayer_doc"."backup_task"."remark" IS '备注';
  -- set comment to column: "create_time" on table: "backup_task":
    COMMENT ON COLUMN "zyplayer_doc"."backup_task"."create_time" IS '创建时间';
  -- set comment to column: "del_flag" on table: "backup_task":
    COMMENT ON COLUMN "zyplayer_doc"."backup_task"."del_flag" IS '删除状态（0--未删除1--已删除）';
  -- create "db_datasource" table:
    CREATE TABLE "zyplayer_doc"."db_datasource" (
         "id" serial NOT NULL,
         "driver_class_name" character varying(50) NULL,
         "source_url" character varying(512) NULL,
         "source_name" character varying(50) NULL,
         "source_password" character varying(50) NULL,
         "create_user_id" bigint NULL,
         "create_user_name" character varying(20) NULL,
         "create_time" timestamp NULL,
         "yn" integer NULL,
         "name" character varying(50) NULL,
         "group_name" character varying(50) NULL,
         PRIMARY KEY ("id")
       );
  -- set comment to column: "id" on table: "db_datasource":
    COMMENT ON COLUMN "zyplayer_doc"."db_datasource"."id" IS '主键自增ID';
  -- set comment to column: "driver_class_name" on table: "db_datasource":
    COMMENT ON COLUMN "zyplayer_doc"."db_datasource"."driver_class_name" IS '数据源驱动类';
  -- set comment to column: "source_url" on table: "db_datasource":
    COMMENT ON COLUMN "zyplayer_doc"."db_datasource"."source_url" IS '数据源地址';
  -- set comment to column: "source_name" on table: "db_datasource":
    COMMENT ON COLUMN "zyplayer_doc"."db_datasource"."source_name" IS '数据源用户名';
  -- set comment to column: "source_password" on table: "db_datasource":
    COMMENT ON COLUMN "zyplayer_doc"."db_datasource"."source_password" IS '数据源密码';
  -- set comment to column: "create_user_id" on table: "db_datasource":
    COMMENT ON COLUMN "zyplayer_doc"."db_datasource"."create_user_id" IS '创建人ID';
  -- set comment to column: "create_user_name" on table: "db_datasource":
    COMMENT ON COLUMN "zyplayer_doc"."db_datasource"."create_user_name" IS '创建人名字';
  -- set comment to column: "create_time" on table: "db_datasource":
    COMMENT ON COLUMN "zyplayer_doc"."db_datasource"."create_time" IS '创建时间';
  -- set comment to column: "yn" on table: "db_datasource":
    COMMENT ON COLUMN "zyplayer_doc"."db_datasource"."yn" IS '是否有效 0=无效 1=有效';
  -- set comment to column: "name" on table: "db_datasource":
    COMMENT ON COLUMN "zyplayer_doc"."db_datasource"."name" IS '数据源名称';
  -- set comment to column: "group_name" on table: "db_datasource":
    COMMENT ON COLUMN "zyplayer_doc"."db_datasource"."group_name" IS '数据源分组名';
  -- create "db_favorite" table:
    CREATE TABLE "zyplayer_doc"."db_favorite" (
         "id" serial NOT NULL,
         "name" character varying(50) NULL,
         "content" character varying(10000) NULL,
         "create_user_id" bigint NULL,
         "create_user_name" character varying(20) NULL,
         "create_time" timestamp NULL,
         "yn" integer NULL,
         "datasource_id" bigint NULL,
         "param_json" character varying(1024) NULL,
         PRIMARY KEY ("id")
       );
  -- set comment to column: "id" on table: "db_favorite":
    COMMENT ON COLUMN "zyplayer_doc"."db_favorite"."id" IS '主键自增ID';
  -- set comment to column: "name" on table: "db_favorite":
    COMMENT ON COLUMN "zyplayer_doc"."db_favorite"."name" IS '收藏标题';
  -- set comment to column: "content" on table: "db_favorite":
    COMMENT ON COLUMN "zyplayer_doc"."db_favorite"."content" IS '收藏内容';
  -- set comment to column: "create_user_id" on table: "db_favorite":
    COMMENT ON COLUMN "zyplayer_doc"."db_favorite"."create_user_id" IS '创建人ID';
  -- set comment to column: "create_user_name" on table: "db_favorite":
    COMMENT ON COLUMN "zyplayer_doc"."db_favorite"."create_user_name" IS '创建人名字';
  -- set comment to column: "create_time" on table: "db_favorite":
    COMMENT ON COLUMN "zyplayer_doc"."db_favorite"."create_time" IS '创建时间';
  -- set comment to column: "yn" on table: "db_favorite":
    COMMENT ON COLUMN "zyplayer_doc"."db_favorite"."yn" IS '是否有效 0=无效 1=有效';
  -- set comment to column: "datasource_id" on table: "db_favorite":
    COMMENT ON COLUMN "zyplayer_doc"."db_favorite"."datasource_id" IS '数据源ID';
  -- set comment to column: "param_json" on table: "db_favorite":
    COMMENT ON COLUMN "zyplayer_doc"."db_favorite"."param_json" IS '执行参数JSON';
  -- create "db_history" table:
    CREATE TABLE "zyplayer_doc"."db_history" (
         "id" serial NOT NULL,
         "content" character varying(10000) NULL,
         "create_user_id" bigint NULL,
         "create_user_name" character varying(20) NULL,
         "create_time" timestamp NULL,
         "yn" integer NULL,
         "datasource_id" bigint NULL,
         "param_json" character varying(1024) NULL,
         PRIMARY KEY ("id")
       );
  -- set comment to column: "id" on table: "db_history":
    COMMENT ON COLUMN "zyplayer_doc"."db_history"."id" IS '主键自增ID';
  -- set comment to column: "content" on table: "db_history":
    COMMENT ON COLUMN "zyplayer_doc"."db_history"."content" IS 'sql内容';
  -- set comment to column: "create_user_id" on table: "db_history":
    COMMENT ON COLUMN "zyplayer_doc"."db_history"."create_user_id" IS '创建人ID';
  -- set comment to column: "create_user_name" on table: "db_history":
    COMMENT ON COLUMN "zyplayer_doc"."db_history"."create_user_name" IS '创建人名字';
  -- set comment to column: "create_time" on table: "db_history":
    COMMENT ON COLUMN "zyplayer_doc"."db_history"."create_time" IS '创建时间';
  -- set comment to column: "yn" on table: "db_history":
    COMMENT ON COLUMN "zyplayer_doc"."db_history"."yn" IS '是否有效 0=无效 1=有效';
  -- set comment to column: "datasource_id" on table: "db_history":
    COMMENT ON COLUMN "zyplayer_doc"."db_history"."datasource_id" IS '数据源ID';
  -- set comment to column: "param_json" on table: "db_history":
    COMMENT ON COLUMN "zyplayer_doc"."db_history"."param_json" IS '执行参数JSON';
  -- create "db_proc_log" table:
    CREATE TABLE "zyplayer_doc"."db_proc_log" (
         "id" serial NOT NULL,
         "datasource_id" bigint NULL,
         "proc_db" character varying(100) NULL,
         "proc_name" character varying(100) NULL,
         "proc_type" character varying(50) NULL,
         "proc_body" bytea NULL,
         "status" integer NOT NULL DEFAULT 1,
         "create_user_id" bigint NULL,
         "create_user_name" character varying(20) NULL,
         "create_time" timestamp NULL,
         PRIMARY KEY ("id")
       );
  -- set comment to table: "db_proc_log":
    COMMENT ON TABLE "zyplayer_doc"."db_proc_log" IS '数据库函数修改日志';
  -- set comment to column: "id" on table: "db_proc_log":
    COMMENT ON COLUMN "zyplayer_doc"."db_proc_log"."id" IS '主键自增ID';
  -- set comment to column: "datasource_id" on table: "db_proc_log":
    COMMENT ON COLUMN "zyplayer_doc"."db_proc_log"."datasource_id" IS '数据源ID';
  -- set comment to column: "proc_db" on table: "db_proc_log":
    COMMENT ON COLUMN "zyplayer_doc"."db_proc_log"."proc_db" IS '所属数据库';
  -- set comment to column: "proc_name" on table: "db_proc_log":
    COMMENT ON COLUMN "zyplayer_doc"."db_proc_log"."proc_name" IS '名字';
  -- set comment to column: "proc_type" on table: "db_proc_log":
    COMMENT ON COLUMN "zyplayer_doc"."db_proc_log"."proc_type" IS '类型';
  -- set comment to column: "proc_body" on table: "db_proc_log":
    COMMENT ON COLUMN "zyplayer_doc"."db_proc_log"."proc_body" IS '函数创建SQL';
  -- set comment to column: "status" on table: "db_proc_log":
    COMMENT ON COLUMN "zyplayer_doc"."db_proc_log"."status" IS '保存状态 1=成功 2=失败';
  -- set comment to column: "create_user_id" on table: "db_proc_log":
    COMMENT ON COLUMN "zyplayer_doc"."db_proc_log"."create_user_id" IS '创建人ID';
  -- set comment to column: "create_user_name" on table: "db_proc_log":
    COMMENT ON COLUMN "zyplayer_doc"."db_proc_log"."create_user_name" IS '创建人名字';
  -- set comment to column: "create_time" on table: "db_proc_log":
    COMMENT ON COLUMN "zyplayer_doc"."db_proc_log"."create_time" IS '创建时间';
  -- create "db_table_relation" table:
    CREATE TABLE "zyplayer_doc"."db_table_relation" (
         "id" serial NOT NULL,
         "datasource_id" bigint NULL,
         "start_db_name" character varying(100) NULL,
         "start_table_name" character varying(100) NULL,
         "start_column_name" character varying(100) NULL,
         "end_db_name" character varying(100) NULL,
         "end_table_name" character varying(100) NULL,
         "end_column_name" character varying(100) NULL,
         "create_user_id" bigint NULL,
         "create_user_name" character varying(20) NULL,
         "create_time" timestamp NULL,
         PRIMARY KEY ("id")
       );
  -- set comment to table: "db_table_relation":
    COMMENT ON TABLE "zyplayer_doc"."db_table_relation" IS '表关系';
  -- set comment to column: "id" on table: "db_table_relation":
    COMMENT ON COLUMN "zyplayer_doc"."db_table_relation"."id" IS '主键自增ID';
  -- set comment to column: "datasource_id" on table: "db_table_relation":
    COMMENT ON COLUMN "zyplayer_doc"."db_table_relation"."datasource_id" IS '数据源ID';
  -- set comment to column: "start_db_name" on table: "db_table_relation":
    COMMENT ON COLUMN "zyplayer_doc"."db_table_relation"."start_db_name" IS '源库名';
  -- set comment to column: "start_table_name" on table: "db_table_relation":
    COMMENT ON COLUMN "zyplayer_doc"."db_table_relation"."start_table_name" IS '源表名';
  -- set comment to column: "start_column_name" on table: "db_table_relation":
    COMMENT ON COLUMN "zyplayer_doc"."db_table_relation"."start_column_name" IS '源字段名';
  -- set comment to column: "end_db_name" on table: "db_table_relation":
    COMMENT ON COLUMN "zyplayer_doc"."db_table_relation"."end_db_name" IS '目标库名';
  -- set comment to column: "end_table_name" on table: "db_table_relation":
    COMMENT ON COLUMN "zyplayer_doc"."db_table_relation"."end_table_name" IS '目标表名';
  -- set comment to column: "end_column_name" on table: "db_table_relation":
    COMMENT ON COLUMN "zyplayer_doc"."db_table_relation"."end_column_name" IS '目标字段名';
  -- set comment to column: "create_user_id" on table: "db_table_relation":
    COMMENT ON COLUMN "zyplayer_doc"."db_table_relation"."create_user_id" IS '创建人ID';
  -- set comment to column: "create_user_name" on table: "db_table_relation":
    COMMENT ON COLUMN "zyplayer_doc"."db_table_relation"."create_user_name" IS '创建人名字';
  -- set comment to column: "create_time" on table: "db_table_relation":
    COMMENT ON COLUMN "zyplayer_doc"."db_table_relation"."create_time" IS '创建时间';
  -- create "db_transfer_task" table:
    CREATE TABLE "zyplayer_doc"."db_transfer_task" (
         "id" serial NOT NULL,
         "name" character varying(100) NULL,
         "query_datasource_id" bigint NULL,
         "storage_datasource_id" bigint NULL,
         "query_sql" character varying(2048) NULL,
         "storage_sql" character varying(2048) NULL,
         "need_count" integer NOT NULL DEFAULT 0,
         "last_execute_status" integer NOT NULL DEFAULT 0,
         "last_execute_time" timestamp NULL,
         "last_execute_info" text NULL,
         "create_user_id" bigint NULL,
         "create_user_name" character varying(20) NULL,
         "create_time" timestamp NULL,
         "del_flag" integer NOT NULL DEFAULT 0,
         PRIMARY KEY ("id")
       );
  -- set comment to column: "id" on table: "db_transfer_task":
    COMMENT ON COLUMN "zyplayer_doc"."db_transfer_task"."id" IS '主键自增ID';
  -- set comment to column: "name" on table: "db_transfer_task":
    COMMENT ON COLUMN "zyplayer_doc"."db_transfer_task"."name" IS '任务名称';
  -- set comment to column: "query_datasource_id" on table: "db_transfer_task":
    COMMENT ON COLUMN "zyplayer_doc"."db_transfer_task"."query_datasource_id" IS '查询数据源ID';
  -- set comment to column: "storage_datasource_id" on table: "db_transfer_task":
    COMMENT ON COLUMN "zyplayer_doc"."db_transfer_task"."storage_datasource_id" IS '入库数据源ID';
  -- set comment to column: "query_sql" on table: "db_transfer_task":
    COMMENT ON COLUMN "zyplayer_doc"."db_transfer_task"."query_sql" IS '查询数据的sql';
  -- set comment to column: "storage_sql" on table: "db_transfer_task":
    COMMENT ON COLUMN "zyplayer_doc"."db_transfer_task"."storage_sql" IS '数据入库的sql';
  -- set comment to column: "need_count" on table: "db_transfer_task":
    COMMENT ON COLUMN "zyplayer_doc"."db_transfer_task"."need_count" IS '自动查询总条数 0=否 1=是';
  -- set comment to column: "last_execute_status" on table: "db_transfer_task":
    COMMENT ON COLUMN "zyplayer_doc"."db_transfer_task"."last_execute_status" IS '最后执行状态 0=未执行 1=执行中 2=执行成功 3=执行失败 4=取消执行';
  -- set comment to column: "last_execute_time" on table: "db_transfer_task":
    COMMENT ON COLUMN "zyplayer_doc"."db_transfer_task"."last_execute_time" IS '最后执行时间';
  -- set comment to column: "last_execute_info" on table: "db_transfer_task":
    COMMENT ON COLUMN "zyplayer_doc"."db_transfer_task"."last_execute_info" IS '最后执行信息';
  -- set comment to column: "create_user_id" on table: "db_transfer_task":
    COMMENT ON COLUMN "zyplayer_doc"."db_transfer_task"."create_user_id" IS '创建人ID';
  -- set comment to column: "create_user_name" on table: "db_transfer_task":
    COMMENT ON COLUMN "zyplayer_doc"."db_transfer_task"."create_user_name" IS '创建人名字';
  -- set comment to column: "create_time" on table: "db_transfer_task":
    COMMENT ON COLUMN "zyplayer_doc"."db_transfer_task"."create_time" IS '创建时间';
  -- set comment to column: "del_flag" on table: "db_transfer_task":
    COMMENT ON COLUMN "zyplayer_doc"."db_transfer_task"."del_flag" IS '删除标记 0=正常 1=已删除';
  -- create "system_config" table:
    CREATE TABLE "zyplayer_doc"."system_config" (
         "id" serial NOT NULL,
         "config_key" character varying(100) NULL,
         "config_value" character varying(500) NULL,
         "created" timestamp NULL,
         "create_user_id" bigint NULL,
         "create_user" character varying(50) NULL,
         "modified" timestamp NULL,
         "modify_user_id" bigint NULL,
         "modify_user" character varying(50) NULL,
         "yn" integer NOT NULL DEFAULT 1,
         PRIMARY KEY ("id")
       );
  -- create index "idx_config_key" to table: "system_config":
    CREATE UNIQUE INDEX "idx_config_key" ON "zyplayer_doc"."system_config" ("config_key");
  -- set comment to table: "system_config":
    COMMENT ON TABLE "zyplayer_doc"."system_config" IS '系统配置表';
  -- set comment to column: "id" on table: "system_config":
    COMMENT ON COLUMN "zyplayer_doc"."system_config"."id" IS '主键自增ID';
  -- set comment to column: "config_key" on table: "system_config":
    COMMENT ON COLUMN "zyplayer_doc"."system_config"."config_key" IS '配置Key';
  -- set comment to column: "config_value" on table: "system_config":
    COMMENT ON COLUMN "zyplayer_doc"."system_config"."config_value" IS '配置值';
  -- set comment to column: "created" on table: "system_config":
    COMMENT ON COLUMN "zyplayer_doc"."system_config"."created" IS '创建时间';
  -- set comment to column: "create_user_id" on table: "system_config":
    COMMENT ON COLUMN "zyplayer_doc"."system_config"."create_user_id" IS '创建用户ID';
  -- set comment to column: "create_user" on table: "system_config":
    COMMENT ON COLUMN "zyplayer_doc"."system_config"."create_user" IS '创建用户名字';
  -- set comment to column: "modified" on table: "system_config":
    COMMENT ON COLUMN "zyplayer_doc"."system_config"."modified" IS '修改时间';
  -- set comment to column: "modify_user_id" on table: "system_config":
    COMMENT ON COLUMN "zyplayer_doc"."system_config"."modify_user_id" IS '修改人ID';
  -- set comment to column: "modify_user" on table: "system_config":
    COMMENT ON COLUMN "zyplayer_doc"."system_config"."modify_user" IS '修改人姓名';
  -- set comment to column: "yn" on table: "system_config":
    COMMENT ON COLUMN "zyplayer_doc"."system_config"."yn" IS '是否有效 1=有效';
  -- create "user_auth" table:
    CREATE TABLE "zyplayer_doc"."user_auth" (
         "id" serial NOT NULL,
         "user_id" bigint NULL,
         "auth_id" bigint NULL,
         "create_uid" bigint NULL,
         "update_uid" bigint NULL,
         "del_flag" integer NULL DEFAULT 0,
         "creation_time" timestamp NULL,
         "update_time" timestamp NULL,
         "sys_type" integer NULL,
         "sys_module_type" integer NULL,
         "sys_module_id" bigint NULL,
         PRIMARY KEY ("id")
       );
  -- set comment to table: "user_auth":
    COMMENT ON TABLE "zyplayer_doc"."user_auth" IS '用户权限表';
  -- set comment to column: "id" on table: "user_auth":
    COMMENT ON COLUMN "zyplayer_doc"."user_auth"."id" IS '主键自增ID';
  -- set comment to column: "user_id" on table: "user_auth":
    COMMENT ON COLUMN "zyplayer_doc"."user_auth"."user_id" IS '用户ID';
  -- set comment to column: "auth_id" on table: "user_auth":
    COMMENT ON COLUMN "zyplayer_doc"."user_auth"."auth_id" IS '权限ID';
  -- set comment to column: "create_uid" on table: "user_auth":
    COMMENT ON COLUMN "zyplayer_doc"."user_auth"."create_uid" IS '创建用户ID';
  -- set comment to column: "update_uid" on table: "user_auth":
    COMMENT ON COLUMN "zyplayer_doc"."user_auth"."update_uid" IS '更新用户ID';
  -- set comment to column: "del_flag" on table: "user_auth":
    COMMENT ON COLUMN "zyplayer_doc"."user_auth"."del_flag" IS '是否删除 0=未删除 1=已删除';
  -- set comment to column: "creation_time" on table: "user_auth":
    COMMENT ON COLUMN "zyplayer_doc"."user_auth"."creation_time" IS '创建时间';
  -- set comment to column: "update_time" on table: "user_auth":
    COMMENT ON COLUMN "zyplayer_doc"."user_auth"."update_time" IS '更新时间';
  -- set comment to column: "sys_type" on table: "user_auth":
    COMMENT ON COLUMN "zyplayer_doc"."user_auth"."sys_type" IS '系统类型，DocSysType';
  -- set comment to column: "sys_module_type" on table: "user_auth":
    COMMENT ON COLUMN "zyplayer_doc"."user_auth"."sys_module_type" IS '系统模块类型，DocSysModuleType';
  -- set comment to column: "sys_module_id" on table: "user_auth":
    COMMENT ON COLUMN "zyplayer_doc"."user_auth"."sys_module_id" IS '系统模块ID';
  -- create "user_group" table:
    CREATE TABLE "zyplayer_doc"."user_group" (
         "id" serial NOT NULL,
         "name" character varying(50) NULL,
         "create_user_id" bigint NULL,
         "create_user_name" character varying(20) NULL,
         "create_time" timestamp NULL,
         "del_flag" integer NOT NULL DEFAULT 0,
         PRIMARY KEY ("id")
       );
  -- set comment to table: "user_group":
    COMMENT ON TABLE "zyplayer_doc"."user_group" IS '用户组';
  -- set comment to column: "id" on table: "user_group":
    COMMENT ON COLUMN "zyplayer_doc"."user_group"."id" IS '主键自增ID';
  -- set comment to column: "name" on table: "user_group":
    COMMENT ON COLUMN "zyplayer_doc"."user_group"."name" IS '分组名';
  -- set comment to column: "create_user_id" on table: "user_group":
    COMMENT ON COLUMN "zyplayer_doc"."user_group"."create_user_id" IS '创建人ID';
  -- set comment to column: "create_user_name" on table: "user_group":
    COMMENT ON COLUMN "zyplayer_doc"."user_group"."create_user_name" IS '创建人名字';
  -- set comment to column: "create_time" on table: "user_group":
    COMMENT ON COLUMN "zyplayer_doc"."user_group"."create_time" IS '创建时间';
  -- set comment to column: "del_flag" on table: "user_group":
    COMMENT ON COLUMN "zyplayer_doc"."user_group"."del_flag" IS '删除标记 0=正常 1=已删除';
  -- create "user_group_auth" table:
    CREATE TABLE "zyplayer_doc"."user_group_auth" (
         "id" serial NOT NULL,
         "group_id" bigint NULL,
         "data_id" bigint NULL,
         "auth_type" integer NULL,
         "project_type" integer NULL,
         "create_user_id" bigint NULL,
         "create_user_name" character varying(20) NULL,
         "create_time" timestamp NULL,
         "del_flag" integer NOT NULL DEFAULT 0,
         PRIMARY KEY ("id")
       );
  -- set comment to table: "user_group_auth":
    COMMENT ON TABLE "zyplayer_doc"."user_group_auth" IS '用户组在各项目内的授权关系';
  -- set comment to column: "id" on table: "user_group_auth":
    COMMENT ON COLUMN "zyplayer_doc"."user_group_auth"."id" IS '主键自增ID';
  -- set comment to column: "group_id" on table: "user_group_auth":
    COMMENT ON COLUMN "zyplayer_doc"."user_group_auth"."group_id" IS '群ID';
  -- set comment to column: "data_id" on table: "user_group_auth":
    COMMENT ON COLUMN "zyplayer_doc"."user_group_auth"."data_id" IS '授权数据的ID';
  -- set comment to column: "auth_type" on table: "user_group_auth":
    COMMENT ON COLUMN "zyplayer_doc"."user_group_auth"."auth_type" IS '授权类型，依据各系统自己定义';
  -- set comment to column: "project_type" on table: "user_group_auth":
    COMMENT ON COLUMN "zyplayer_doc"."user_group_auth"."project_type" IS '项目类型 1=manage 2=wiki 3=db';
  -- set comment to column: "create_user_id" on table: "user_group_auth":
    COMMENT ON COLUMN "zyplayer_doc"."user_group_auth"."create_user_id" IS '创建人ID';
  -- set comment to column: "create_user_name" on table: "user_group_auth":
    COMMENT ON COLUMN "zyplayer_doc"."user_group_auth"."create_user_name" IS '创建人名字';
  -- set comment to column: "create_time" on table: "user_group_auth":
    COMMENT ON COLUMN "zyplayer_doc"."user_group_auth"."create_time" IS '创建时间';
  -- set comment to column: "del_flag" on table: "user_group_auth":
    COMMENT ON COLUMN "zyplayer_doc"."user_group_auth"."del_flag" IS '删除标记 0=正常 1=已删除';
  -- create "user_group_relation" table:
    CREATE TABLE "zyplayer_doc"."user_group_relation" (
         "id" serial NOT NULL,
         "group_id" bigint NULL,
         "user_id" bigint NULL,
         "create_user_id" bigint NULL,
         "create_user_name" character varying(20) NULL,
         "create_time" timestamp NULL,
         "del_flag" integer NOT NULL DEFAULT 0,
         PRIMARY KEY ("id")
       );
  -- set comment to table: "user_group_relation":
    COMMENT ON TABLE "zyplayer_doc"."user_group_relation" IS '用户和用户组关系表';
  -- set comment to column: "id" on table: "user_group_relation":
    COMMENT ON COLUMN "zyplayer_doc"."user_group_relation"."id" IS '主键自增ID';
  -- set comment to column: "group_id" on table: "user_group_relation":
    COMMENT ON COLUMN "zyplayer_doc"."user_group_relation"."group_id" IS '群ID';
  -- set comment to column: "user_id" on table: "user_group_relation":
    COMMENT ON COLUMN "zyplayer_doc"."user_group_relation"."user_id" IS '用户ID';
  -- set comment to column: "create_user_id" on table: "user_group_relation":
    COMMENT ON COLUMN "zyplayer_doc"."user_group_relation"."create_user_id" IS '创建人ID';
  -- set comment to column: "create_user_name" on table: "user_group_relation":
    COMMENT ON COLUMN "zyplayer_doc"."user_group_relation"."create_user_name" IS '创建人名字';
  -- set comment to column: "create_time" on table: "user_group_relation":
    COMMENT ON COLUMN "zyplayer_doc"."user_group_relation"."create_time" IS '创建时间';
  -- set comment to column: "del_flag" on table: "user_group_relation":
    COMMENT ON COLUMN "zyplayer_doc"."user_group_relation"."del_flag" IS '删除标记 0=正常 1=已删除';
  -- create "user_info" table:
    CREATE TABLE "zyplayer_doc"."user_info" (
         "id" serial NOT NULL,
         "user_no" character varying(20) NULL,
         "password" character varying(50) NULL,
         "user_name" character varying(50) NULL,
         "email" character varying(50) NULL,
         "avatar" character varying(100) NULL,
         "del_flag" integer NULL DEFAULT 0,
         "creation_time" timestamp NULL,
         "create_uid" bigint NULL,
         "update_time" timestamp NULL,
         "phone" character varying(20) NULL,
         "sex" integer NOT NULL DEFAULT 0,
         PRIMARY KEY ("id")
       );
  -- create index "idx_userno" to table: "user_info":
    CREATE UNIQUE INDEX "idx_userNo" ON "zyplayer_doc"."user_info" ("user_no");
  -- set comment to table: "user_info":
    COMMENT ON TABLE "zyplayer_doc"."user_info" IS '用户信息表';
  -- set comment to column: "id" on table: "user_info":
    COMMENT ON COLUMN "zyplayer_doc"."user_info"."id" IS '主键自增ID';
  -- set comment to column: "user_no" on table: "user_info":
    COMMENT ON COLUMN "zyplayer_doc"."user_info"."user_no" IS '用户编号，用于登录等';
  -- set comment to column: "password" on table: "user_info":
    COMMENT ON COLUMN "zyplayer_doc"."user_info"."password" IS '密码';
  -- set comment to column: "user_name" on table: "user_info":
    COMMENT ON COLUMN "zyplayer_doc"."user_info"."user_name" IS '用户名';
  -- set comment to column: "email" on table: "user_info":
    COMMENT ON COLUMN "zyplayer_doc"."user_info"."email" IS '邮箱';
  -- set comment to column: "avatar" on table: "user_info":
    COMMENT ON COLUMN "zyplayer_doc"."user_info"."avatar" IS '头像';
  -- set comment to column: "del_flag" on table: "user_info":
    COMMENT ON COLUMN "zyplayer_doc"."user_info"."del_flag" IS '是否删除 0=未删除 1=已删除 2=已停用';
  -- set comment to column: "creation_time" on table: "user_info":
    COMMENT ON COLUMN "zyplayer_doc"."user_info"."creation_time" IS '创建时间';
  -- set comment to column: "create_uid" on table: "user_info":
    COMMENT ON COLUMN "zyplayer_doc"."user_info"."create_uid" IS '创建人用户ID';
  -- set comment to column: "update_time" on table: "user_info":
    COMMENT ON COLUMN "zyplayer_doc"."user_info"."update_time" IS '更新时间';
  -- set comment to column: "phone" on table: "user_info":
    COMMENT ON COLUMN "zyplayer_doc"."user_info"."phone" IS '手机号';
  -- set comment to column: "sex" on table: "user_info":
    COMMENT ON COLUMN "zyplayer_doc"."user_info"."sex" IS '性别 0=女 1=男';
  -- set comment to index: "idx_userno" on table: "user_info":
    COMMENT ON INDEX "zyplayer_doc"."idx_userNo" IS '登录用户名';
  -- create "user_message" table:
    CREATE TABLE "zyplayer_doc"."user_message" (
         "id" serial NOT NULL,
         "sys_type" integer NOT NULL DEFAULT 1,
         "msg_type" integer NOT NULL DEFAULT 1,
         "data_id" bigint NULL,
         "data_desc" character varying(100) NULL,
         "msg_content" character varying(255) NULL,
         "msg_status" integer NOT NULL DEFAULT 0,
         "operator_user_id" bigint NULL,
         "operator_user_name" character varying(20) NULL,
         "affect_user_id" bigint NULL,
         "affect_user_name" character varying(20) NULL,
         "accept_user_id" bigint NULL,
         "creation_time" timestamp NULL,
         PRIMARY KEY ("id")
       );
  -- set comment to table: "user_message":
    COMMENT ON TABLE "zyplayer_doc"."user_message" IS '用户消息表';
  -- set comment to column: "id" on table: "user_message":
    COMMENT ON COLUMN "zyplayer_doc"."user_message"."id" IS '主键自增ID';
  -- set comment to column: "sys_type" on table: "user_message":
    COMMENT ON COLUMN "zyplayer_doc"."user_message"."sys_type" IS '系统类型 1=manage 2=wiki 3=db';
  -- set comment to column: "msg_type" on table: "user_message":
    COMMENT ON COLUMN "zyplayer_doc"."user_message"."msg_type" IS '消息类型 1=普通文本消息 2=wiki文档创建 3=wiki文档删除 4=wiki文档编辑 5=wiki文档权限修改 6=wiki文档评论 7=wiki文档删除评论 8=wiki文档上传附件';
  -- set comment to column: "data_id" on table: "user_message":
    COMMENT ON COLUMN "zyplayer_doc"."user_message"."data_id" IS '消息关联的数据ID';
  -- set comment to column: "data_desc" on table: "user_message":
    COMMENT ON COLUMN "zyplayer_doc"."user_message"."data_desc" IS '消息关联的数据说明';
  -- set comment to column: "msg_content" on table: "user_message":
    COMMENT ON COLUMN "zyplayer_doc"."user_message"."msg_content" IS '消息内容';
  -- set comment to column: "msg_status" on table: "user_message":
    COMMENT ON COLUMN "zyplayer_doc"."user_message"."msg_status" IS '消息状态 0=未读 1=已读 2=已删除';
  -- set comment to column: "operator_user_id" on table: "user_message":
    COMMENT ON COLUMN "zyplayer_doc"."user_message"."operator_user_id" IS '操作人用户ID';
  -- set comment to column: "operator_user_name" on table: "user_message":
    COMMENT ON COLUMN "zyplayer_doc"."user_message"."operator_user_name" IS '操作人用户名';
  -- set comment to column: "affect_user_id" on table: "user_message":
    COMMENT ON COLUMN "zyplayer_doc"."user_message"."affect_user_id" IS '影响人用户ID';
  -- set comment to column: "affect_user_name" on table: "user_message":
    COMMENT ON COLUMN "zyplayer_doc"."user_message"."affect_user_name" IS '影响人用户名';
  -- set comment to column: "accept_user_id" on table: "user_message":
    COMMENT ON COLUMN "zyplayer_doc"."user_message"."accept_user_id" IS '接收人用户ID';
  -- set comment to column: "creation_time" on table: "user_message":
    COMMENT ON COLUMN "zyplayer_doc"."user_message"."creation_time" IS '创建时间';
  -- create "user_setting" table:
    CREATE TABLE "zyplayer_doc"."user_setting" (
         "id" serial NOT NULL,
         "user_id" bigint NULL,
         "name" character varying(100) NULL,
         "value" character varying(100) NULL,
         "create_time" timestamp NULL,
         "del_flag" integer NOT NULL DEFAULT 0,
         PRIMARY KEY ("id")
       );
  -- set comment to table: "user_setting":
    COMMENT ON TABLE "zyplayer_doc"."user_setting" IS '用户设置表';
  -- set comment to column: "id" on table: "user_setting":
    COMMENT ON COLUMN "zyplayer_doc"."user_setting"."id" IS '主键自增ID';
  -- set comment to column: "user_id" on table: "user_setting":
    COMMENT ON COLUMN "zyplayer_doc"."user_setting"."user_id" IS '用户ID';
  -- set comment to column: "name" on table: "user_setting":
    COMMENT ON COLUMN "zyplayer_doc"."user_setting"."name" IS '设置的名字';
  -- set comment to column: "value" on table: "user_setting":
    COMMENT ON COLUMN "zyplayer_doc"."user_setting"."value" IS '设置的值';
  -- set comment to column: "create_time" on table: "user_setting":
    COMMENT ON COLUMN "zyplayer_doc"."user_setting"."create_time" IS '创建时间';
  -- set comment to column: "del_flag" on table: "user_setting":
    COMMENT ON COLUMN "zyplayer_doc"."user_setting"."del_flag" IS '删除标记 0=正常 1=已删除';
  -- create "wiki_page" table:
    CREATE TABLE "zyplayer_doc"."wiki_page" (
         "id" serial NOT NULL,
         "space_id" bigint NULL,
         "name" character varying(50) NULL,
         "parent_id" bigint NOT NULL DEFAULT 0,
         "node_type" integer NULL DEFAULT 0,
         "zan_num" integer NOT NULL DEFAULT 0,
         "edit_type" integer NOT NULL DEFAULT 0,
         "create_user_id" bigint NULL,
         "create_user_name" character varying(20) NULL,
         "create_time" timestamp NULL,
         "update_user_id" bigint NULL,
         "update_user_name" character varying(20) NULL,
         "update_time" timestamp NULL,
         "del_flag" integer NOT NULL DEFAULT 0,
         "view_num" integer NOT NULL DEFAULT 0,
         "seq_no" integer NOT NULL DEFAULT 0,
         "editor_type" integer NOT NULL DEFAULT 1,
         PRIMARY KEY ("id")
       );
  -- set comment to column: "id" on table: "wiki_page":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page"."id" IS '主键自增ID';
  -- set comment to column: "space_id" on table: "wiki_page":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page"."space_id" IS '空间ID';
  -- set comment to column: "name" on table: "wiki_page":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page"."name" IS '名字';
  -- set comment to column: "parent_id" on table: "wiki_page":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page"."parent_id" IS '父ID';
  -- set comment to column: "node_type" on table: "wiki_page":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page"."node_type" IS '节点类型 0=有子节点 1=终节点';
  -- set comment to column: "zan_num" on table: "wiki_page":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page"."zan_num" IS '赞的数量';
  -- set comment to column: "edit_type" on table: "wiki_page":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page"."edit_type" IS '编辑类型 0=可编辑 1=不允许编辑';
  -- set comment to column: "create_user_id" on table: "wiki_page":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page"."create_user_id" IS '创建人ID';
  -- set comment to column: "create_user_name" on table: "wiki_page":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page"."create_user_name" IS '创建人名字';
  -- set comment to column: "create_time" on table: "wiki_page":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page"."create_time" IS '创建时间';
  -- set comment to column: "update_user_id" on table: "wiki_page":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page"."update_user_id" IS '修改人ID';
  -- set comment to column: "update_user_name" on table: "wiki_page":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page"."update_user_name" IS '修改人名字';
  -- set comment to column: "update_time" on table: "wiki_page":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page"."update_time" IS '修改时间';
  -- set comment to column: "del_flag" on table: "wiki_page":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page"."del_flag" IS '0=有效 1=删除';
  -- set comment to column: "view_num" on table: "wiki_page":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page"."view_num" IS '阅读数';
  -- set comment to column: "seq_no" on table: "wiki_page":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page"."seq_no" IS '顺序';
  -- set comment to column: "editor_type" on table: "wiki_page":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page"."editor_type" IS '编辑框类型 1=HTML 2=Markdown';
  -- create "wiki_page_comment" table:
    CREATE TABLE "zyplayer_doc"."wiki_page_comment" (
         "id" serial NOT NULL,
         "page_id" bigint NULL,
         "parent_id" bigint NULL,
         "content" character varying(512) NULL,
         "zan_num" integer NOT NULL DEFAULT 0,
         "create_user_id" bigint NULL,
         "create_user_name" character varying(20) NULL,
         "create_time" timestamp NULL,
         "del_flag" integer NULL DEFAULT 0,
         PRIMARY KEY ("id")
       );
  -- set comment to column: "id" on table: "wiki_page_comment":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page_comment"."id" IS '主键自增ID';
  -- set comment to column: "page_id" on table: "wiki_page_comment":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page_comment"."page_id" IS '页面ID';
  -- set comment to column: "parent_id" on table: "wiki_page_comment":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page_comment"."parent_id" IS '父评论ID';
  -- set comment to column: "content" on table: "wiki_page_comment":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page_comment"."content" IS '评论内容';
  -- set comment to column: "zan_num" on table: "wiki_page_comment":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page_comment"."zan_num" IS '赞的数量';
  -- set comment to column: "create_user_id" on table: "wiki_page_comment":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page_comment"."create_user_id" IS '创建人ID';
  -- set comment to column: "create_user_name" on table: "wiki_page_comment":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page_comment"."create_user_name" IS '创建人名字';
  -- set comment to column: "create_time" on table: "wiki_page_comment":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page_comment"."create_time" IS '创建时间';
  -- set comment to column: "del_flag" on table: "wiki_page_comment":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page_comment"."del_flag" IS '0=有效 1=删除';
  -- create "wiki_page_content" table:
    CREATE TABLE "zyplayer_doc"."wiki_page_content" (
         "id" serial NOT NULL,
         "page_id" bigint NULL,
         "content" text NULL,
         "create_user_id" bigint NULL,
         "create_user_name" character varying(20) NULL,
         "create_time" timestamp NULL,
         "update_user_id" bigint NULL,
         "update_user_name" character varying(20) NULL,
         "update_time" timestamp NULL,
         "preview" character varying(16000) NULL,
         PRIMARY KEY ("id")
       );
  -- create index "uk_page_id" to table: "wiki_page_content":
    CREATE UNIQUE INDEX "uk_page_id" ON "zyplayer_doc"."wiki_page_content" ("page_id");
  -- set comment to column: "id" on table: "wiki_page_content":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page_content"."id" IS '主键自增ID';
  -- set comment to column: "page_id" on table: "wiki_page_content":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page_content"."page_id" IS '页面ID';
  -- set comment to column: "content" on table: "wiki_page_content":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page_content"."content" IS '内容';
  -- set comment to column: "create_user_id" on table: "wiki_page_content":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page_content"."create_user_id" IS '创建人ID';
  -- set comment to column: "create_user_name" on table: "wiki_page_content":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page_content"."create_user_name" IS '创建人名字';
  -- set comment to column: "create_time" on table: "wiki_page_content":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page_content"."create_time" IS '创建时间';
  -- set comment to column: "update_user_id" on table: "wiki_page_content":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page_content"."update_user_id" IS '修改人ID';
  -- set comment to column: "update_user_name" on table: "wiki_page_content":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page_content"."update_user_name" IS '修改人名字';
  -- set comment to column: "update_time" on table: "wiki_page_content":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page_content"."update_time" IS '修改时间';
  -- set comment to column: "preview" on table: "wiki_page_content":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page_content"."preview" IS '预览内容';
  -- set comment to index: "uk_page_id" on table: "wiki_page_content":
    COMMENT ON INDEX "zyplayer_doc"."uk_page_id" IS '页面ID';
  -- create "wiki_page_file" table:
    CREATE TABLE "zyplayer_doc"."wiki_page_file" (
         "id" serial NOT NULL,
         "page_id" bigint NULL,
         "file_name" character varying(256) NULL,
         "file_url" character varying(256) NULL,
         "uuid" character varying(40) NULL,
         "create_user_id" bigint NULL,
         "create_user_name" character varying(20) NULL,
         "create_time" timestamp NULL,
         "update_user_id" bigint NULL,
         "update_user_name" character varying(20) NULL,
         "update_time" timestamp NULL,
         "del_flag" integer NULL DEFAULT 0,
         "download_num" integer NOT NULL DEFAULT 0,
         "file_size" bigint NULL,
         "file_source" integer NOT NULL DEFAULT 1,
         PRIMARY KEY ("id")
       );
  -- create index "uk_uuid" to table: "wiki_page_file":
    CREATE UNIQUE INDEX "uk_uuid" ON "zyplayer_doc"."wiki_page_file" ("uuid");
  -- set comment to column: "id" on table: "wiki_page_file":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page_file"."id" IS '主键自增ID';
  -- set comment to column: "page_id" on table: "wiki_page_file":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page_file"."page_id" IS '页面ID';
  -- set comment to column: "file_name" on table: "wiki_page_file":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page_file"."file_name" IS '文件名';
  -- set comment to column: "file_url" on table: "wiki_page_file":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page_file"."file_url" IS '文件URL';
  -- set comment to column: "uuid" on table: "wiki_page_file":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page_file"."uuid" IS '文件UUID';
  -- set comment to column: "create_user_id" on table: "wiki_page_file":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page_file"."create_user_id" IS '创建人ID';
  -- set comment to column: "create_user_name" on table: "wiki_page_file":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page_file"."create_user_name" IS '创建人名字';
  -- set comment to column: "create_time" on table: "wiki_page_file":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page_file"."create_time" IS '创建时间';
  -- set comment to column: "update_user_id" on table: "wiki_page_file":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page_file"."update_user_id" IS '修改人ID';
  -- set comment to column: "update_user_name" on table: "wiki_page_file":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page_file"."update_user_name" IS '修改人名字';
  -- set comment to column: "update_time" on table: "wiki_page_file":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page_file"."update_time" IS '修改时间';
  -- set comment to column: "del_flag" on table: "wiki_page_file":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page_file"."del_flag" IS '0=有效 1=删除';
  -- set comment to column: "download_num" on table: "wiki_page_file":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page_file"."download_num" IS '下载次数';
  -- set comment to column: "file_size" on table: "wiki_page_file":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page_file"."file_size" IS '文件大小';
  -- set comment to index: "uk_uuid" on table: "wiki_page_file":
    COMMENT ON INDEX "zyplayer_doc"."uk_uuid" IS '文件ID';
  -- create "wiki_page_history" table:
    CREATE TABLE "zyplayer_doc"."wiki_page_history" (
         "id" serial NOT NULL,
         "page_id" bigint NULL,
         "create_user_id" bigint NULL,
         "create_user_name" character varying(20) NULL,
         "create_time" timestamp NULL,
         "del_flag" integer NOT NULL DEFAULT 0,
         "content" bytea NULL,
         PRIMARY KEY ("id")
       );
  -- create index "idx_page_id" to table: "wiki_page_history":
    CREATE INDEX "idx_page_id" ON "zyplayer_doc"."wiki_page_history" ("page_id");
  -- set comment to column: "id" on table: "wiki_page_history":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page_history"."id" IS '主键自增ID';
  -- set comment to column: "page_id" on table: "wiki_page_history":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page_history"."page_id" IS '页面ID';
  -- set comment to column: "create_user_id" on table: "wiki_page_history":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page_history"."create_user_id" IS '创建人ID';
  -- set comment to column: "create_user_name" on table: "wiki_page_history":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page_history"."create_user_name" IS '创建人名字';
  -- set comment to column: "create_time" on table: "wiki_page_history":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page_history"."create_time" IS '创建时间';
  -- set comment to column: "del_flag" on table: "wiki_page_history":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page_history"."del_flag" IS '删除标记 0=正常 1=已删除';
  -- set comment to index: "idx_page_id" on table: "wiki_page_history":
    COMMENT ON INDEX "zyplayer_doc"."idx_page_id" IS '页面ID索引';
  -- create "wiki_page_template" table:
    CREATE TABLE "zyplayer_doc"."wiki_page_template" (
         "id" serial NOT NULL,
         "space_id" bigint NULL,
         "page_id" bigint NULL,
         "tag_name" character varying(50) NULL,
         "share_status" integer NOT NULL DEFAULT 0,
         "created" timestamp NULL,
         "create_user_id" bigint NULL,
         "create_user" character varying(50) NULL,
         "modified" timestamp NULL,
         "modify_user_id" bigint NULL,
         "modify_user" character varying(50) NULL,
         "yn" integer NOT NULL DEFAULT 1,
         PRIMARY KEY ("id")
       );
  -- set comment to table: "wiki_page_template":
    COMMENT ON TABLE "zyplayer_doc"."wiki_page_template" IS '模板信息表';
  -- set comment to column: "id" on table: "wiki_page_template":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page_template"."id" IS '模板信息主键';
  -- set comment to column: "space_id" on table: "wiki_page_template":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page_template"."space_id" IS '空间id';
  -- set comment to column: "page_id" on table: "wiki_page_template":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page_template"."page_id" IS '模板挂载文档id';
  -- set comment to column: "tag_name" on table: "wiki_page_template":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page_template"."tag_name" IS '标签信息';
  -- set comment to column: "share_status" on table: "wiki_page_template":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page_template"."share_status" IS '模板公开状态（0-个人模板1-公共模板）';
  -- set comment to column: "created" on table: "wiki_page_template":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page_template"."created" IS '创建时间';
  -- set comment to column: "create_user_id" on table: "wiki_page_template":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page_template"."create_user_id" IS '创建人id';
  -- set comment to column: "create_user" on table: "wiki_page_template":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page_template"."create_user" IS '创建人名称';
  -- set comment to column: "modified" on table: "wiki_page_template":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page_template"."modified" IS '修改时间';
  -- set comment to column: "modify_user_id" on table: "wiki_page_template":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page_template"."modify_user_id" IS '修改人ID';
  -- set comment to column: "modify_user" on table: "wiki_page_template":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page_template"."modify_user" IS '修改人姓名';
  -- set comment to column: "yn" on table: "wiki_page_template":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page_template"."yn" IS '是否有效 1=有效';
  -- create "wiki_page_zan" table:
    CREATE TABLE "zyplayer_doc"."wiki_page_zan" (
         "id" serial NOT NULL,
         "page_id" bigint NULL,
         "comment_id" bigint NULL,
         "create_user_id" bigint NULL,
         "create_user_name" character varying(20) NULL,
         "create_time" timestamp NULL,
         "yn" integer NULL,
         PRIMARY KEY ("id")
       );
  -- set comment to column: "id" on table: "wiki_page_zan":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page_zan"."id" IS '主键自增ID';
  -- set comment to column: "page_id" on table: "wiki_page_zan":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page_zan"."page_id" IS '页面ID';
  -- set comment to column: "comment_id" on table: "wiki_page_zan":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page_zan"."comment_id" IS '评论ID';
  -- set comment to column: "create_user_id" on table: "wiki_page_zan":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page_zan"."create_user_id" IS '创建人ID';
  -- set comment to column: "create_user_name" on table: "wiki_page_zan":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page_zan"."create_user_name" IS '创建人名字';
  -- set comment to column: "create_time" on table: "wiki_page_zan":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page_zan"."create_time" IS '创建时间';
  -- set comment to column: "yn" on table: "wiki_page_zan":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_page_zan"."yn" IS '是否有效 0=无效 1=有效';
  -- create "wiki_space" table:
    CREATE TABLE "zyplayer_doc"."wiki_space" (
         "id" serial NOT NULL,
         "name" character varying(50) NULL,
         "type" integer NULL DEFAULT 1,
         "space_explain" character varying(255) NULL,
         "edit_type" integer NOT NULL DEFAULT 0,
         "tree_lazy_load" integer NOT NULL DEFAULT 0,
         "open_doc" integer NOT NULL DEFAULT 0,
         "uuid" character varying(40) NULL,
         "create_user_id" bigint NULL,
         "create_user_name" character varying(20) NULL,
         "create_time" timestamp NULL,
         "del_flag" integer NOT NULL DEFAULT 0,
         PRIMARY KEY ("id")
       );
  -- set comment to column: "id" on table: "wiki_space":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_space"."id" IS '主键自增ID';
  -- set comment to column: "name" on table: "wiki_space":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_space"."name" IS '空间名';
  -- set comment to column: "type" on table: "wiki_space":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_space"."type" IS '空间类型 1=公司 2=个人 3=私人';
  -- set comment to column: "space_explain" on table: "wiki_space":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_space"."space_explain" IS '描述';
  -- set comment to column: "edit_type" on table: "wiki_space":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_space"."edit_type" IS '编辑类型 0=可编辑 1=不允许编辑';
  -- set comment to column: "tree_lazy_load" on table: "wiki_space":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_space"."tree_lazy_load" IS '目录延迟加载 0=否 1=是';
  -- set comment to column: "open_doc" on table: "wiki_space":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_space"."open_doc" IS '是否是开放文档 0=否 1=是';
  -- set comment to column: "uuid" on table: "wiki_space":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_space"."uuid" IS '唯一UUID';
  -- set comment to column: "create_user_id" on table: "wiki_space":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_space"."create_user_id" IS '创建人ID';
  -- set comment to column: "create_user_name" on table: "wiki_space":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_space"."create_user_name" IS '创建人名字';
  -- set comment to column: "create_time" on table: "wiki_space":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_space"."create_time" IS '创建时间';
  -- set comment to column: "del_flag" on table: "wiki_space":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_space"."del_flag" IS '删除标记 0=正常 1=已删除';
  -- create "wiki_space_favorite" table:
    CREATE TABLE "zyplayer_doc"."wiki_space_favorite" (
         "id" serial NOT NULL,
         "space_id" bigint NULL,
         "user_id" bigint NULL,
         "create_time" timestamp NULL,
         "del_flag" integer NOT NULL DEFAULT 0,
         PRIMARY KEY ("id")
       );
  -- set comment to table: "wiki_space_favorite":
    COMMENT ON TABLE "zyplayer_doc"."wiki_space_favorite" IS '用户空间收藏记录表';
  -- set comment to column: "id" on table: "wiki_space_favorite":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_space_favorite"."id" IS '主键自增ID';
  -- set comment to column: "space_id" on table: "wiki_space_favorite":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_space_favorite"."space_id" IS '空间ID';
  -- set comment to column: "user_id" on table: "wiki_space_favorite":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_space_favorite"."user_id" IS '用户ID';
  -- set comment to column: "create_time" on table: "wiki_space_favorite":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_space_favorite"."create_time" IS '创建时间';
  -- set comment to column: "del_flag" on table: "wiki_space_favorite":
    COMMENT ON COLUMN "zyplayer_doc"."wiki_space_favorite"."del_flag" IS '删除标记 0=正常 1=已删除';




-- -------------------插入必要的数据-------------------
-- 用户信息
INSERT INTO zyplayer_doc.user_info (user_no, password, user_name, email, del_flag, creation_time, update_time, sex)
VALUES ('zyplayer', 'e10adc3949ba59abbe56e057f20f883e', '系统管理员', 'admin@zyplayer.com', 0, '2018-12-01 11:37:39', '2018-12-01 11:37:39', 1);
-- 权限列表
INSERT INTO zyplayer_doc.auth_info(id, auth_name, auth_desc, can_edit, create_uid, creation_time, auth_type)
VALUES (1, 'AUTH_ASSIGN', '权限分配权', 0, 1, '2018-12-01 11:40:42', 1),
       (2, 'USER_MANAGE', '用户管理权', 0, 1, '2018-12-01 11:40:42', 1),
       (3, 'WIKI_EDIT_PAGE_', '编辑wiki文档', 0, 1, '2019-06-04 13:01:20', 0),
       (4, 'WIKI_VIEW_PAGE_', '查看wiki文档', 0, 1, '2019-06-04 13:01:20', 0),
       (5, 'WIKI_COMMENT_PAGE_', '评论wiki文档', 0, 1, '2019-06-04 13:01:20', 0),
       (6, 'WIKI_DELETE_PAGE_', '删除wiki文档', 0, 1, '2019-06-04 13:01:20', 0),
       (7, 'WIKI_PAGE_FILE_UPLOAD_', '上传wiki文档附件', 0, 1, '2019-06-04 13:01:20', 0),
       (8, 'WIKI_PAGE_FILE_DELETE_', '删除wiki文档附件', 0, 1, '2019-06-04 13:01:20', 0),
       (9, 'WIKI_PAGE_AUTH_MANAGE_', 'wiki权限管理', 0, 1, '2019-06-04 13:01:20', 0),
       (10, 'DB_DATASOURCE_MANAGE', 'DB数据源管理权', 0, 1, '2019-06-29 13:01:20', 1),
       (11, 'DB_VIEW_', '数据源查看权', 0, 1, '2019-08-18 23:25:17', 0),
       (12, 'DB_SELECT_', '数据源查询权', 0, 1, '2019-08-18 23:25:17', 0),
       (13, 'DB_UPDATE_', '数据源增删改查权', 0, 1, '2019-08-18 23:25:17', 0),
       (14, 'DB_DESC_EDIT_', '表字段注释修改权', 0, 1, '2019-08-18 23:25:17', 0),
       (15, 'DB_PROC_EDIT_', '存储过程修改权', 0, 1, '2021-04-24 23:25:17', 0),
       (16, 'API_DOC_MANAGE', 'api文档管理权', 0, 1, '2021-12-12 23:25:17', 0),
       (17, 'API_DOC_DEVELOPER', 'api文档编辑权', 0, 1, '2021-12-12 23:25:17', 0);
-- 用户权限
INSERT INTO zyplayer_doc.user_auth (id, user_id, auth_id, create_uid, update_uid, del_flag, creation_time)
VALUES (1, 1, 1, 1, 1, 0, '2018-12-01 11:37:39'),
       (2, 1, 2, 1, 1, 0, '2018-12-01 11:37:39'),
       (3, 1, 10, 1, 1, 0, '2018-12-01 11:37:39');