
-- 从1.0.1版本升级：
-- 直接存权限名，不搞中间表了
ALTER TABLE `user_auth` ADD COLUMN `auth_custom_suffix` varchar(100) NULL COMMENT '自定义权限结尾';


-- 权限的库：

