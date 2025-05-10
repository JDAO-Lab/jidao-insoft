/*
 Navicat Premium Data Transfer

 Source Server         : mysqlLocal
 Source Server Type    : MySQL
 Source Server Version : 50738 (5.7.38-log)
 Source Host           : localhost:3306
 Source Schema         : in_soft

 Target Server Type    : MySQL
 Target Server Version : 50738 (5.7.38-log)
 File Encoding         : 65001

 Date: 11/05/2025 01:50:29
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for app_cat
-- ----------------------------
DROP TABLE IF EXISTS `app_cat`;
CREATE TABLE `app_cat`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '客户端名称',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '客户端简介',
  `created_at` datetime NULL DEFAULT NULL,
  `updated_at` datetime NULL DEFAULT NULL,
  `is_deleted` tinyint(2) NOT NULL DEFAULT 0 COMMENT '0未删除 1已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '客户端应用类型' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of app_cat
-- ----------------------------
INSERT INTO `app_cat` VALUES (1, '安卓', '安卓系列应用版本。', '2024-05-12 13:34:27', '2024-05-12 13:39:34', 0);
INSERT INTO `app_cat` VALUES (2, 'IOS', '苹果手机系统版本应用。', '2024-05-12 13:35:16', '2024-05-12 13:39:20', 0);

-- ----------------------------
-- Table structure for app_version
-- ----------------------------
DROP TABLE IF EXISTS `app_version`;
CREATE TABLE `app_version`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '版本标题',
  `up_log` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '更新日志',
  `down_url` varchar(666) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '下载地址',
  `down_pwd` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '提取码',
  `down_type` tinyint(2) NOT NULL DEFAULT 0 COMMENT '下载方式：0本地OSS 1阿里网盘 2蓝奏云 3小鸟云 4百度网盘',
  `created_at` datetime NULL DEFAULT NULL COMMENT '创建日期',
  `updated_at` datetime NULL DEFAULT NULL COMMENT '更新日期',
  `cid` int(11) NULL DEFAULT NULL COMMENT '客户端类型',
  `version` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '0.0.1' COMMENT '版本号',
  `is_deleted` tinyint(2) NOT NULL DEFAULT 0 COMMENT '是否删除默认为0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '客户端应用版本管理' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of app_version
-- ----------------------------

-- ----------------------------
-- Table structure for sms_code
-- ----------------------------
DROP TABLE IF EXISTS `sms_code`;
CREATE TABLE `sms_code`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '接收验证码的手机号',
  `code` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '验证码',
  `send_time` datetime NULL DEFAULT NULL COMMENT '发送时间',
  `type` tinyint(2) NULL DEFAULT NULL COMMENT '1注册 2登录 3找回密码 4实名验证',
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `status` tinyint(2) NOT NULL DEFAULT 0 COMMENT '0待使用 1已使用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '手机验证码' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sms_code
-- ----------------------------

-- ----------------------------
-- Table structure for sys_admin
-- ----------------------------
DROP TABLE IF EXISTS `sys_admin`;
CREATE TABLE `sys_admin`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `created_at` datetime NULL DEFAULT NULL,
  `updated_at` datetime NULL DEFAULT NULL,
  `is_deleted` tinyint(2) NULL DEFAULT 0 COMMENT '0未删除 1已删除',
  `rule_id` int(11) NULL DEFAULT NULL COMMENT '权限id',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `enable` tinyint(2) NOT NULL DEFAULT 1 COMMENT '0禁用 1启用',
  `login_at` datetime NULL DEFAULT NULL COMMENT '登录时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '管理员用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_admin
-- ----------------------------
INSERT INTO `sys_admin` VALUES (1, 'admin', '7fef6171469e80d32c0559f88b377245', '947c0fed1ec609fe1edd9bf663006582', '2024-02-07 20:10:10', '2024-05-04 14:59:57', 0, 1, 'https://oss.youqiong.net/2a8038416840457e8edd92d939ccf5ae/184b1aadd7d744babadc54ae69bfa143.jpg', 1, '2025-05-10 22:40:04');
INSERT INTO `sys_admin` VALUES (2, 'love01', '787cdff80ef7221b9924a8c12f9dc449', NULL, '2024-04-10 14:15:52', '2024-04-10 15:43:42', 0, 1, 'https://oss.youqiong.net/2fab3fcb6ab744e8b76971926f68cf11/6ecf51be0b004bd9941083d036a4ecfb.jpg', 1, NULL);
INSERT INTO `sys_admin` VALUES (3, 'xixi', '470423f5957f4532be4aa4ec3a5e1837', NULL, '2024-04-10 14:22:49', '2024-04-10 15:43:39', 0, 1, 'https://oss.youqiong.net/784660db283e49fda7bf25330f8a91fc/3d5a73efd6f14af899bfa8836e5c7955.jpg', 1, NULL);
INSERT INTO `sys_admin` VALUES (4, 'hhhh', 'd41d8cd98f00b204e9800998ecf8427e', NULL, '2024-04-10 14:30:12', '2024-04-10 15:14:10', 0, 2, 'https://oss.youqiong.net/8acae46133c944328d1b205637b06dde/3e5b92ba5f234263b3321ff9e71d67ec.jpg', 1, NULL);
INSERT INTO `sys_admin` VALUES (5, 'admin02', '7fef6171469e80d32c0559f88b377245', 'a3347f6ed9cba0dfd5bea01dffeedb2f', '2024-04-10 14:53:00', '2024-04-10 15:11:59', 0, 2, 'https://oss.youqiong.net/c48ce868aaba4a6d868bf399d7a05e5b/d063ccb2d822495dbf34ae945ff980c2.jpg', 1, '2024-04-10 15:02:18');
INSERT INTO `sys_admin` VALUES (6, 'admin3', 'd41d8cd98f00b204e9800998ecf8427e', NULL, '2024-04-10 14:45:57', '2024-04-30 16:05:07', 0, 1, 'https://oss.youqiong.net/eadd8f2ca4e14e2b922401db6f30dffd/0283249f2c1344ea9bf70e305e2b0258.jpg', 1, NULL);
INSERT INTO `sys_admin` VALUES (7, 'admin022', '4618c83e52f9f147618caf97c502b996', NULL, '2024-04-10 15:14:01', '2024-04-10 15:15:18', 1, 1, '/default/avatar.jpg', 1, NULL);

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL,
  `created_at` datetime NULL DEFAULT NULL,
  `updated_at` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '系统配置表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES (1, 'web_site', 'InSoft 软件后台管理系统', '2024-03-05 21:32:49', '2024-03-05 21:32:54');
INSERT INTO `sys_config` VALUES (2, 'short_title', 'InSoft', '2024-03-05 21:34:27', '2024-03-05 21:34:29');
INSERT INTO `sys_config` VALUES (3, 'author', '任聪聪', '2024-03-18 20:35:42', NULL);
INSERT INTO `sys_config` VALUES (4, 'admin_key', '', '2024-03-18 20:35:45', NULL);
INSERT INTO `sys_config` VALUES (5, 'theme_config', '{\"logo\":{\"title\":\"InSoft Admin\",\"image\":\"https://oss.youqiong.net/2a8038416840457e8edd92d939ccf5ae/184b1aadd7d744babadc54ae69bfa143.jpg\"},\"menu\":{\"data\":\"admin/get_menu\",\"method\":\"GET\",\"accordion\":true,\"collapse\":false,\"control\":false,\"controlWidth\":500,\"select\":\"24\",\"async\":true},\"tab\":{\"enable\":true,\"keepState\":true,\"session\":true,\"preload\":false,\"max\":\"30\",\"index\":{\"id\":\"24\",\"href\":\"/admin/console\",\"title\":\"控制台\"}},\"theme\":{\"defaultColor\":\"2\",\"defaultMenu\":\"dark-theme\",\"defaultHeader\":\"light-theme\",\"allowCustom\":true,\"banner\":false},\"colors\":[{\"id\":\"1\",\"color\":\"#2d8cf0\",\"second\":\"#ecf5ff\"},{\"id\":\"2\",\"color\":\"#36b368\",\"second\":\"#f0f9eb\"},{\"id\":\"3\",\"color\":\"#f6ad55\",\"second\":\"#fdf6ec\"},{\"id\":\"4\",\"color\":\"#f56c6c\",\"second\":\"#fef0f0\"},{\"id\":\"5\",\"color\":\"#3963bc\",\"second\":\"#ecf5ff\"}],\"other\":{\"keepLoad\":\"200\",\"autoHead\":\"true\",\"footer\":\"true\"}}', '2024-03-18 20:35:45', NULL);
INSERT INTO `sys_config` VALUES (6, 'prohibited_words', 'admin,rcc,admin888,root,admin,adm,manage\n', '2024-03-18 20:35:45', NULL);
INSERT INTO `sys_config` VALUES (7, 'web_ico', 'https://oss.youqiong.net/8ff91f19496c4ec199959ecfa88efe3b/73d8b9e36a7e4ab694cd852a01c37ba4.png', '2024-03-18 20:35:45', NULL);
INSERT INTO `sys_config` VALUES (8, 'web_keywords', 'InSoft,后台管理系统', '2024-03-18 20:35:45', NULL);
INSERT INTO `sys_config` VALUES (9, 'web_description', '一款好用的基线后台开发系统，能够帮助你快速进入java项目的开发工作当中。', '2024-03-18 20:35:45', NULL);
INSERT INTO `sys_config` VALUES (10, 'icp_number', '苏ICP备24001089号-1', '2024-03-18 20:35:45', NULL);
INSERT INTO `sys_config` VALUES (11, 'icp_href', 'https://beian.miit.gov.cn/#/Integrated/index', '2024-03-18 20:35:45', NULL);
INSERT INTO `sys_config` VALUES (12, 'static_script', '\n', '2024-03-18 20:35:45', NULL);
INSERT INTO `sys_config` VALUES (13, 'characteristic', '便捷、快速', NULL, NULL);
INSERT INTO `sys_config` VALUES (14, 'characteristic_description', 'InSoft是一款用于任何付费软件场景的闭源基线系统。', NULL, NULL);
INSERT INTO `sys_config` VALUES (15, 'customer_service', '', NULL, NULL);

-- ----------------------------
-- Table structure for sys_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `file_name` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '文件原始名',
  `file_path` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '上传后的文件路径 调用它即可',
  `file_md5` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '唯一MD5 uuid+文件名转换而来',
  `created_at` datetime NULL DEFAULT NULL COMMENT '上传时间',
  `updated_at` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '上传者ip',
  `size` int(11) NULL DEFAULT NULL COMMENT '文件大小',
  `ext` varchar(56) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '文件类型 例：.jpg',
  `local` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '文件存储方式',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '文件缓存表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_file
-- ----------------------------

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '访问ip',
  `path` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '访问路径',
  `remarks` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注：一般登录用户会增加用户名的字眼开头',
  `result` tinyint(2) NOT NULL DEFAULT 0 COMMENT '结果：0未知、1成功、2失败',
  `created_at` datetime NULL DEFAULT NULL COMMENT '创建日期',
  `method` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '请求方式',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '系统日志' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_orders
-- ----------------------------
DROP TABLE IF EXISTS `sys_orders`;
CREATE TABLE `sys_orders`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `remarks` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '订单备注信息',
  `trade_no` varchar(66) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '订单编号 唯一的系统生成',
  `amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '支付金额',
  `status` tinyint(2) NULL DEFAULT NULL COMMENT '0未支付 1已支付 2已取消 3已退款',
  `created_at` datetime NULL DEFAULT NULL,
  `updated_at` datetime NULL DEFAULT NULL,
  `uid` int(11) NULL DEFAULT NULL COMMENT '购买人id',
  `pay_type` tinyint(2) NULL DEFAULT NULL COMMENT '支付方式 1微信 2支付宝',
  `out_trade_no` varchar(66) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '平台的支付订单编号 外部订单号',
  `income_id` tinyint(2) NULL DEFAULT NULL COMMENT '对应增值服务id',
  `pay_at` datetime NULL DEFAULT NULL COMMENT '支付时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '订单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_orders
-- ----------------------------

-- ----------------------------
-- Table structure for sys_rules
-- ----------------------------
DROP TABLE IF EXISTS `sys_rules`;
CREATE TABLE `sys_rules`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '权限名称：管理员、普通用户',
  `rules` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '菜单id，以，号隔开。',
  `created_at` datetime NULL DEFAULT NULL,
  `updated_at` datetime NULL DEFAULT NULL,
  `is_deleted` tinyint(2) NULL DEFAULT 0 COMMENT '0正常 1删除',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '系统权限配置表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_rules
-- ----------------------------
INSERT INTO `sys_rules` VALUES (1, '超级管理员', '10,15,7,21,22,45,46,47,48,49,11,2,3,37,4,40,29,30,31,12,5,20,28,44,36,6,17,18,19,27,33,41,42,43,16,13,14,26,38,39,23,8,9,24,25', '2024-03-06 22:17:00', '2024-07-01 11:06:56', 0, '系统默认权限组，所有路径均可访问。');
INSERT INTO `sys_rules` VALUES (2, '管理员', '10,15,7,21,22,45,46,47,48,49,11,2,3,37,4,40,29,30,31,12,5,20,28,44,36,6,17,18,19,27,33,41,42,43,16,13,14,26,38,39,23,8,9,24,25', '2024-04-10 14:29:40', '2024-07-01 11:07:04', 0, '普通管理员权限');

-- ----------------------------
-- Table structure for sys_rules_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_rules_menu`;
CREATE TABLE `sys_rules_menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '菜单的名称',
  `href` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '菜单的路径',
  `pid` int(11) NOT NULL DEFAULT 0 COMMENT '菜单的父节点',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '菜单的描述',
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '菜单的ifon图标',
  `created_at` datetime NULL DEFAULT NULL,
  `updated_at` datetime NULL DEFAULT NULL,
  `is_deleted` tinyint(2) NULL DEFAULT 0 COMMENT '0正常 1删除',
  `target` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '打开方式',
  `allow` tinyint(2) NOT NULL DEFAULT 0 COMMENT '0为需验证进行访问 1为无需验证即可访问',
  `sort` int(11) NOT NULL DEFAULT 0 COMMENT '序号',
  `hide` tinyint(2) NOT NULL DEFAULT 0 COMMENT '0为显示 1为隐藏',
  `enable` tinyint(2) NOT NULL DEFAULT 1 COMMENT '0禁用 1启用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 50 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '权限菜单表，包含了系统中的相关菜单信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_rules_menu
-- ----------------------------
INSERT INTO `sys_rules_menu` VALUES (1, '前往前台', '/', 23, '', 'layui-icon-home', '2024-03-06 21:46:09', '2024-04-05 14:37:42', 1, '_target', 0, 1, 1, 1);
INSERT INTO `sys_rules_menu` VALUES (2, '菜单管理', '/admin/menus/list', 11, '编辑和调整菜单信息越详细越好。', 'layui-icon layui-icon-menu-fill', '2024-03-06 21:47:00', '2024-04-10 21:27:46', 0, '_self', 0, 1, 0, 1);
INSERT INTO `sys_rules_menu` VALUES (3, '权限配置', '/admin/rules/list', 11, '配置权限信息选择越详细越好。', 'layui-icon layui-icon-key', '2024-03-06 21:48:29', '2024-04-10 21:28:51', 0, '_self', 0, 2, 0, 1);
INSERT INTO `sys_rules_menu` VALUES (4, '系统管理员', '/admin/sys_admin/list', 11, '除超级管理员admin外其余用户均可删除。', 'layui-icon layui-icon layui-icon-user', '2024-03-06 21:54:05', '2024-04-10 15:56:09', 0, '_self', 0, 3, 0, 1);
INSERT INTO `sys_rules_menu` VALUES (5, '用户列表', '/admin/users/list', 12, NULL, 'layui-icon-water', '2024-03-06 21:54:08', NULL, 0, '_self', 0, 0, 0, 1);
INSERT INTO `sys_rules_menu` VALUES (6, '数据统计', '#', 0, '', 'layui-icon layui-icon-test', '2024-03-06 21:54:11', '2024-04-05 14:48:17', 0, '_self', 0, 4, 0, 1);
INSERT INTO `sys_rules_menu` VALUES (7, '系统配置', '/admin/setting', 10, '系统配置界面信息', 'layui-icon layui-icon-slider', '2024-03-06 21:54:13', '2024-04-05 13:07:32', 0, '_self', 0, 2, 0, 1);
INSERT INTO `sys_rules_menu` VALUES (8, '资料修改', '/admin/sys_admins/profile', 23, NULL, 'layui-icon-tabs', '2024-03-06 21:54:15', '2024-04-05 14:37:45', 0, '_self', 0, 2, 1, 1);
INSERT INTO `sys_rules_menu` VALUES (9, '安全退出', '/admin/logout', 23, NULL, 'layui-icon-logout', '2024-03-06 21:54:17', '2024-04-05 14:37:47', 0, '_self', 0, 3, 1, 1);
INSERT INTO `sys_rules_menu` VALUES (10, '系统管理', '#', 0, '', 'layui-icon layui-icon layui-icon-slider', '2024-03-07 19:52:04', '2024-10-05 19:30:49', 0, '_self', 0, 0, 0, 1);
INSERT INTO `sys_rules_menu` VALUES (11, '权限管理', '#', 0, '', 'layui-icon layui-icon-password', '2024-03-07 19:52:06', '2024-04-05 14:47:20', 0, '_self', 0, 1, 0, 1);
INSERT INTO `sys_rules_menu` VALUES (12, '用户管理', '#', 0, '', 'layui-icon layui-icon-username', '2024-03-07 19:52:09', '2024-04-05 14:47:56', 0, '_self', 0, 3, 0, 1);
INSERT INTO `sys_rules_menu` VALUES (13, '客户端类型', '/admin/app_cat/list', 16, '', 'layui-icon layui-icon layui-icon-face-smile', '2024-03-07 19:52:11', '2024-05-04 18:44:07', 0, '_self', 0, 0, 0, 1);
INSERT INTO `sys_rules_menu` VALUES (14, '版本管理', '/admin/app_version/list', 16, '', 'layui-icon layui-icon-face-smile', '2024-03-07 19:52:13', '2024-05-04 18:44:25', 0, '_self', 0, 0, 0, 1);
INSERT INTO `sys_rules_menu` VALUES (15, '主题配置', '/admin/theme_setting', 10, '主题配置信息', 'layui-icon layui-icon-heart', '2024-03-07 19:52:15', '2024-04-05 14:47:07', 0, '_self', 0, 1, 0, 1);
INSERT INTO `sys_rules_menu` VALUES (16, '客户端管理', '#', 0, '', 'layui-icon layui-icon-app', '2024-03-07 19:52:17', '2024-04-05 14:48:44', 0, '_self', 0, 6, 0, 1);
INSERT INTO `sys_rules_menu` VALUES (17, '综合分析', '/admin/statics/all', 6, NULL, 'layui-icon-face-smile', '2024-03-07 19:52:19', NULL, 0, '_self', 0, 0, 0, 1);
INSERT INTO `sys_rules_menu` VALUES (18, '收入分析', '/admin/statics/order', 6, NULL, 'layui-icon-face-smile', '2024-03-07 19:52:21', NULL, 0, '_self', 0, 0, 0, 1);
INSERT INTO `sys_rules_menu` VALUES (19, '用户分析', '/admin/statics/user', 6, NULL, 'layui-icon-face-smile', '2024-03-07 19:52:24', NULL, 0, '_self', 0, 0, 0, 1);
INSERT INTO `sys_rules_menu` VALUES (20, '获取数据', '/admin/users/data', 5, '', 'layui-icon layui-icon-face-smile', '2024-03-07 19:52:26', '2024-04-11 17:34:45', 0, '_self', 0, 0, 1, 1);
INSERT INTO `sys_rules_menu` VALUES (21, '系统日志', '/admin/log/sys_log', 10, '此页面展示从系统创建至今的所有用户操作信息记录，可以通过数据库进行删除！', 'layui-icon-face-smile', '2024-03-07 19:52:28', NULL, 0, '_self', 0, 3, 0, 1);
INSERT INTO `sys_rules_menu` VALUES (22, '违禁词', '/admin/prohibited_words', 10, NULL, 'layui-icon-face-smile', '2024-03-07 19:52:31', NULL, 0, '_self', 0, 4, 0, 1);
INSERT INTO `sys_rules_menu` VALUES (23, '用户行为', NULL, 0, NULL, 'layui-icon-face-smile', '2024-03-07 19:52:33', '2024-04-05 14:35:48', 0, '_self', 0, 100, 1, 1);
INSERT INTO `sys_rules_menu` VALUES (24, '控制台', '/admin/console', 23, NULL, 'layui-icon-face-smile', '2024-03-08 21:56:37', '2024-04-05 14:37:49', 0, '_self', 0, 4, 1, 1);
INSERT INTO `sys_rules_menu` VALUES (25, '登录', '/admin/login', 23, NULL, 'layui-icon-face-smile', '2024-03-13 18:36:21', '2024-04-05 14:37:52', 0, '_self', 0, 5, 1, 1);
INSERT INTO `sys_rules_menu` VALUES (26, '其他菜单', NULL, 0, NULL, 'layui-icon-face-smile', '2024-03-13 18:39:27', '2024-04-05 14:34:02', 0, '_self', 0, 8, 1, 1);
INSERT INTO `sys_rules_menu` VALUES (27, '管理中心', '/admin', 0, '', 'layui-icon layui-icon-face-smile', '2024-03-13 18:40:01', '2024-04-05 14:39:04', 0, '_self', 0, 5, 1, 1);
INSERT INTO `sys_rules_menu` VALUES (28, '意见反馈', '/admin/opinion/list', 12, '', 'layui-icon layui-icon-gift', '2024-04-04 23:45:57', '2024-05-12 15:57:49', 0, '_self', 0, 0, 0, 1);
INSERT INTO `sys_rules_menu` VALUES (29, '前台导航', '#', 0, '', 'layui-icon layui-icon-website', '2024-04-04 23:45:55', '2024-04-05 14:47:40', 0, '_self', 0, 2, 0, 1);
INSERT INTO `sys_rules_menu` VALUES (30, '位置管理', '/admin/nav_cat/list', 29, '', 'layui-icon ', '2024-04-04 23:45:53', '2024-05-03 18:21:26', 0, '_self', 0, 0, 0, 1);
INSERT INTO `sys_rules_menu` VALUES (31, '导航列表', '/admin/nav/list', 29, '', 'layui-icon ', '2024-04-04 23:45:48', '2024-05-03 18:21:45', 0, '_self', 0, 0, 0, 1);
INSERT INTO `sys_rules_menu` VALUES (32, '列表', '/admin/app/category/list', 13, '客户端类型包括安卓、ios、mac、linux、win等，可自定义前端下载页显示。', 'layui-icon layui-icon-circle-dot', '2024-04-04 23:39:04', NULL, 1, '_self', 0, 0, 1, 1);
INSERT INTO `sys_rules_menu` VALUES (33, '图文管理', '#', 0, '文章管理功能顶级菜单', 'layui-icon layui-icon layui-icon-carousel', '2024-04-05 00:02:14', '2024-04-05 14:39:11', 0, '_self', 0, 5, 0, 1);
INSERT INTO `sys_rules_menu` VALUES (34, '测试', '/test', 0, '测试页面', 'layui-icon layui-icon-home', '2024-04-05 13:34:41', NULL, 1, '_self', 0, 0, 0, 1);
INSERT INTO `sys_rules_menu` VALUES (35, '测试子级', '/test', 34, '子级删除测试', 'layui-icon layui-icon-home', '2024-04-05 13:44:05', NULL, 1, '_self', 0, 0, 0, 1);
INSERT INTO `sys_rules_menu` VALUES (36, '增值服务', '/admin/income/list', 12, '增值服务管理配置功能', 'layui-icon layui-icon layui-icon layui-icon layui-icon-light', '2024-04-05 14:45:55', '2024-04-30 16:52:44', 0, '_self', 0, 8, 0, 1);
INSERT INTO `sys_rules_menu` VALUES (37, '获取数据', '/admin/rules/data', 3, '', 'layui-icon layui-icon layui-icon-home', '2024-04-09 23:34:22', '2024-05-04 00:11:40', 0, '_self', 0, 0, 1, 1);
INSERT INTO `sys_rules_menu` VALUES (38, '获取主题', '/admin/get_the_theme', 26, '', 'layui-icon layui-icon-link', '2024-04-10 15:03:05', NULL, 0, '_self', 0, 0, 1, 1);
INSERT INTO `sys_rules_menu` VALUES (39, '获取菜单', '/admin/get_menu', 26, '', 'layui-icon layui-icon-link', '2024-04-10 15:04:17', NULL, 0, '_self', 0, 0, 1, 1);
INSERT INTO `sys_rules_menu` VALUES (40, '获取数据', '/admin/sys_admin/data', 4, '', 'layui-icon layui-icon-link', '2024-04-10 15:05:18', NULL, 0, '_self', 0, 0, 1, 1);
INSERT INTO `sys_rules_menu` VALUES (41, '图文分类', '/admin/post_cat/list', 33, '', 'layui-icon layui-icon layui-icon layui-icon-link', '2024-04-11 17:38:05', '2024-05-04 18:23:24', 0, '_self', 0, 0, 0, 1);
INSERT INTO `sys_rules_menu` VALUES (42, '图文列表', '/admin/post/list', 33, '', 'layui-icon layui-icon layui-icon layui-icon-link', '2024-04-11 17:38:22', '2024-05-04 18:23:33', 0, '_self', 0, 1, 0, 1);
INSERT INTO `sys_rules_menu` VALUES (43, '回收站', '/admin/post_recovery/list', 33, '此页面均为删除状态为1的数据，在此页面删除的内容会转为2，留存在数据库中，但不会显示在任何页面。', 'layui-icon layui-icon layui-icon layui-icon-link', '2024-04-30 19:53:31', '2024-05-04 18:39:58', 0, '_self', 0, 3, 0, 1);
INSERT INTO `sys_rules_menu` VALUES (44, '订单记录', '/admin/orders/list', 12, '购买增值服务的订单数据信息', 'layui-icon layui-icon-link', '2024-05-03 14:51:24', NULL, 0, '_self', 0, 4, 0, 1);
INSERT INTO `sys_rules_menu` VALUES (45, '推荐应用', '/admin/push_product/list', 10, '', 'layui-icon layui-icon layui-icon-link', '2024-06-30 23:37:19', '2024-07-01 00:11:03', 0, '_self', 0, 5, 0, 1);
INSERT INTO `sys_rules_menu` VALUES (46, '添加', '/admin/push_product/add', 45, '', 'layui-icon layui-icon layui-icon layui-icon-link', '2024-07-01 00:09:22', '2024-07-01 11:08:01', 0, '_self', 0, 0, 1, 1);
INSERT INTO `sys_rules_menu` VALUES (47, '数据', '/admin/push_product/data', 45, '', 'layui-icon layui-icon layui-icon-link', '2024-07-01 00:09:45', '2024-07-01 11:08:06', 0, '_self', 0, 0, 1, 1);
INSERT INTO `sys_rules_menu` VALUES (48, '特性配置', '/admin/characteristic/list', 10, '', 'layui-icon layui-icon layui-icon layui-icon-link', '2024-07-01 11:05:37', '2024-07-01 11:06:41', 0, '_self', 0, 6, 0, 1);
INSERT INTO `sys_rules_menu` VALUES (49, '数据', '/admin/characteristic/data', 48, '', 'layui-icon layui-icon layui-icon-link', '2024-07-01 11:05:56', '2024-07-01 11:08:13', 0, '_self', 0, 0, 1, 1);

-- ----------------------------
-- Table structure for sys_static
-- ----------------------------
DROP TABLE IF EXISTS `sys_static`;
CREATE TABLE `sys_static`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '统计项名称：中文',
  `value` double(15, 2) NULL DEFAULT 0.00 COMMENT '统计数，保留两位小数点',
  `created_at` datetime NULL DEFAULT NULL COMMENT '统计日期',
  `updated_at` datetime NULL DEFAULT NULL COMMENT '统计更新时间',
  `type` tinyint(2) NOT NULL DEFAULT 0 COMMENT '定义类型在枚举类StaticTypeEnum中',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 57 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '系统数据统计' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_static
-- ----------------------------

-- ----------------------------
-- Table structure for web_characteristic
-- ----------------------------
DROP TABLE IF EXISTS `web_characteristic`;
CREATE TABLE `web_characteristic`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `thumb` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `short_description` varchar(227) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `sort` int(11) NULL DEFAULT NULL,
  `created_at` datetime NULL DEFAULT NULL,
  `updated_at` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '软件特性配置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of web_characteristic
-- ----------------------------
INSERT INTO `web_characteristic` VALUES (1, '客户端类型', 'https://oss.youqiong.net/383931fd8d984afa858d95af8f925ed3/ed0ea694247b415ea9c5f3fefb5d0c50.png', '通过InSoft您可以快速管理您的客户端，不同版本的下载包推送及更新。', '优秀的集中管理能力', 0, '2024-07-01 11:13:16', NULL);
INSERT INTO `web_characteristic` VALUES (2, '数据统计', 'https://oss.youqiong.net/52917000717b4d159fd93de56c65be39/5dc94c884fd845c2a1b8488212bf4a8f.png', '搭载了数据统计模块，您可以快速在您的项目中进行埋点统计。', '优秀数据统计能力', 0, '2024-07-01 11:15:35', NULL);
INSERT INTO `web_characteristic` VALUES (3, '用户管理', 'https://oss.youqiong.net/9bbf4c9d78aa49e5a7b453422f7a3679/37fc08db6ad54bf2a6654b1d24bf083d.png', '流畅且支持多个客户端登录的用户管理及注册能力，帮助您快速开发短信登录，邮箱登录功能。', '优秀的用户管理能力', 0, '2024-07-01 11:16:22', NULL);
INSERT INTO `web_characteristic` VALUES (4, '推荐应用', 'https://oss.youqiong.net/c95559869c8b4838aef8769f2acbd58a/4f51aa8bcdac44f1a288dcd0d37f7873.png', '当您的产品众多时，您可以使用本功能，进行对产品的推荐和宣传。', '众多关系的开始', 0, '2024-07-01 11:16:57', NULL);
INSERT INTO `web_characteristic` VALUES (5, '特性配置', 'https://oss.youqiong.net/38af37d1dc434299825262090ba6e858/d124865e31d84f6aa2a43120051503be.png', '您无需进行首页面的开发，我们的系统进行了更为便捷的功能的应用，您可以快速的操作并编辑显示首页特性，以介绍软件。', '便捷灵活的配置能力', 0, '2024-07-01 11:18:00', NULL);

-- ----------------------------
-- Table structure for web_income
-- ----------------------------
DROP TABLE IF EXISTS `web_income`;
CREATE TABLE `web_income`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '增值服务名称',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '单价',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '描述信息',
  `discount` int(3) NULL DEFAULT NULL COMMENT '折扣',
  `is_deleted` tinyint(2) NOT NULL DEFAULT 0 COMMENT '是否删除0未删除 1已删除',
  `created_at` datetime NULL DEFAULT NULL,
  `updated_at` datetime NULL DEFAULT NULL,
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '会员标识图标',
  `duration` int(11) NULL DEFAULT NULL COMMENT '开通时长 单位天 -1为永久',
  `privilege` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '服务特权内容：多个以，号隔开 单个中以：隔开0或1 来判断权限情况',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序 越小越靠前',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '增值服务信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of web_income
-- ----------------------------

-- ----------------------------
-- Table structure for web_nav
-- ----------------------------
DROP TABLE IF EXISTS `web_nav`;
CREATE TABLE `web_nav`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '导航名称',
  `keywords` varchar(666) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '关键词',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '图文内容',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `pid` int(11) NOT NULL DEFAULT 0 COMMENT '上级id',
  `path` varchar(666) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '路径',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '1' COMMENT '1图文 2连接',
  `created_at` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `enable` tinyint(4) NOT NULL DEFAULT 0 COMMENT '启用状态',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '描述信息',
  `cid` int(11) NULL DEFAULT NULL COMMENT '类型id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '前台导航表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of web_nav
-- ----------------------------
INSERT INTO `web_nav` VALUES (1, '介绍', '关键词1,关键词2', '', 0, 0, '/', '2', '2024-05-03 21:04:38', '2024-06-30 17:06:23', 1, '', 1);
INSERT INTO `web_nav` VALUES (2, '定价', '购买界面', '', 1, 0, '/price', '2', '2024-05-03 21:05:36', '2024-06-30 20:45:34', 1, '', 1);
INSERT INTO `web_nav` VALUES (3, '下载', '下载', '', 2, 0, '/edition', '2', '2024-05-03 21:05:39', '2024-06-30 20:45:54', 1, '', 1);
INSERT INTO `web_nav` VALUES (4, '帮助', '帮助中心', '', 3, 0, '/help', '2', '2024-05-03 21:05:41', '2024-06-30 21:39:01', 1, '', 1);
INSERT INTO `web_nav` VALUES (22, '密码管理', '密码修改', '', 1, 0, '/users/change_pwd', '2', '2024-05-04 10:22:06', '2024-10-06 20:52:20', 1, '修改密码及设置登录方式。', 2);
INSERT INTO `web_nav` VALUES (23, '会员信息', '会员信息', '', 0, 0, '/users/info', '2', '2024-05-04 10:22:38', '2024-10-06 21:36:55', 1, '', 2);
INSERT INTO `web_nav` VALUES (24, '充值记录', '充值记录', '', 3, 0, '/users/recharge', '2', '2024-05-04 10:23:19', '2024-09-22 13:30:08', 1, '', 2);
INSERT INTO `web_nav` VALUES (25, '隐私政策', '隐私', '> 本隐私政策适用于您访问、使用本网站或应用程序（包括但不限于移动端、小程序、utools、接口、浏览器插件等其他客户端）服务时（以下简称“本站”），我们收集、使用和披露您的个人信息。在访问、使用本站之前，请仔细阅读并理解本隐私政策。如果您不同意本隐私政策的任何内容，请立即停止访问本站及使用相关服务。当您继续访问、使用本站时，即表示您已充分理解并同意接受本隐私政策的约束。\n\n## 一、信息收集\n我们可能会收集以下类型的信息：\n1.1 注册信息：使用本站并不需要注册账号，因此我们不需要收集您的手机、姓名、邮箱等信息。\n1.2 设备信息：在您使用本站时我们可能会收集您使用的设备类型、操作系统、浏览器版本等信息。\n1.3 日志信息：当您访问本站时，我们可能会自动收集与您的访问相关的日志信息，包括IP地址、访问记录、访问时间、浏览器类型等。\n\n## 二、信息使用\n我们可能会将收集到的信息用于以下目的：\n2.1 提供服务：我们可能会使用您输入的信息来向您及其他用户提供更加便捷和优质的服务，因此您输入信息使用我们服务所生成的结果会被公开。\n2.2 内部分析：我们可能会使用您的信息来了解本站如何被使用，以便我们可以改进本站。\n\n## 三、信息披露\n3.1 本站不会收集您敏感的个人信息。\n3.2 在您使用本站时，我们会收集您输入的变量信息用于提供变量名的生成服务，但您需要提前自审，确保这些信息不违反任何法律及任何三方合法权益后输入。\n3.3 根据法律的有关规定，或者行政或司法机构的要求，向第三方或者行政、司法机构披露；\n3.4 如您出现违反中国有关法律、法规或者相关规则的情况，需要您主动配合向第三方披露；\n\n## 四、数据保留\n请悉知本站会对您输入的对应信息进行存储和展示，如不同意我们存储和展示您输入的信息，请勿继续使用，如继续使用本站则默认认为您已授权我们存储相应的生成结果，同时表明您已自审相应信息不存在任何违反法律及任何三方合法权益的内容。\n\n## 五、协议更改和更新\n我们可能会不时更新本隐私政策。在更改生效后，您继续访问、使用本站将被视为您接受新的隐私政策。\n\n# 该内容为演示内容~', 1, 0, '', '1', '2024-05-04 10:36:24', '2024-06-30 21:42:28', 1, '', 3);
INSERT INTO `web_nav` VALUES (26, '用户协议', '协议', '\n本协议是您与本网站（以下简称“本站”）之间的法律协议。在使用本站之前，请仔细阅读本协议。如果您不同意本协议的任何条款，请不要使用本站。\n\n# 1. 服务内容\n\n1.1 本站提供的信息和服务仅供参考，不构成任何形式的建议或推荐。\n\n1.2 本站不对信息的及时性、准确性、完整性和可靠性作出任何保证。\n\n1.3 本站有权随时修改、更新或删除信息和服务，无需事先通知用户。\n\n# 2. 用户行为\n\n2.1 用户应遵守国家法律法规，不得利用本站进行违法活动。\n\n2.2 用户应遵守社会公德，不得发布侮辱、诽谤、淫秽、暴力等不良信息。\n\n2.3 用户应尊重知识产权，不得侵犯他人版权、商标权、专利权等合法权益。\n\n2.4 用户应尊重他人隐私，不得收集、使用、泄露他人个人信息。\n\n# 3. 版权声明\n\n3.1 本站的所有内容（包括但不限于文字、图片、音频、视频、软件等）的知识产权归本站所有，未经本站书面许可，任何人不得复制、转载、引用、摘编、改编或以其他方式使用。\n\n3.2 用户在本站发布的原创内容，其知识产权归用户所有，但用户授权本站在全球范围内免费、非独家、可转让、可转许可地使用该内容。\n\n# 4. 免责声明\n\n4.1 用户明确同意其使用本站的风险由其自行承担。本站不对用户因使用本站而遭受的损失负责。\n\n4.2 本站对用户因使用本站而遭受的直接、间接、偶然、特殊及后续的损害不承担责任。\n\n# 5. 法律适用和争议解决\n\n5.1 本协议的签订地为中华人民共和国。\n\n5.2 本协议的订立、执行和解释及争议的解决均适用中华人民共和国法律。\n\n5.3 若用户与本站之间发生任何争议，应首先友好协商解决；协商不成的，任何一方均有权向本站所在地人民法院提起诉讼。\n\n# 6. 其他条款\n\n6.1 本协议的解释权归本站所有。\n\n6.2 本站有权随时修改本协议，修改后的协议一经公布即生效。\n\n6.3 用户在使用本站时，视为已阅读并接受本协议的所有条款。\n\n# 该内容为演示内容~', 2, 0, '', '1', '2024-05-04 10:37:52', '2024-06-30 21:44:08', 1, '', 3);
INSERT INTO `web_nav` VALUES (29, '关于我们', '关于我们', '## 介绍\nInSoft 是一款基于 Spring Boot 的开源系统，具有高度的扩展性，能够适应任何软件场景的需求。它可以帮助用户快速构建后台管理系统，涵盖了常用的授权、用户注册、个人中心、支付、对象存储等众多依赖。\n\n## 特点\n便捷快速：InSoft 能够帮助您快速完成相应能力的部署，节省时间和精力。\n超丰富的功能：InSoft 提供了丰富的功能模块，包括授权、用户注册、个人中心、支付、对象存储等，满足各种需求。\n强大的扩展性：InSoft 具有高度的扩展性，能够适应任何软件场景的需求，让您的系统更加灵活和可定制。\n应用案例\nInSoft 已经成功应用于多个场景，包括有穹、51pwd 和 chtml 等。这些应用案例证明了 InSoft 的可靠性和实用性。\n\n## 安装和使用\nInSoft 的安装非常简单，只需按照以下步骤操作即可：\n\n下载 InSoft 的安装包；\n解压安装包到合适的目录；\n运行安装脚本，按照提示进行配置；\n启动 InSoft，开始使用。\n## 总结\nInSoft 是一款功能强大、易于使用的后台管理系统构建工具。它具有便捷快速、超丰富的功能和强大的扩展性等特点，能够满足各种软件场景的需求。如果您正在寻找一款可靠的后台管理系统构建工具，InSoft 绝对是您的首选！\n\n# 该内容为演示内容~', 0, 0, '', '1', '2024-06-30 19:02:28', '2024-06-30 21:44:01', 1, '', 3);
INSERT INTO `web_nav` VALUES (30, '资料设置', '个人资料', '', 4, 0, '/users/setting', '2', '2024-07-07 12:59:07', '2024-10-06 20:52:36', 1, '', 2);
INSERT INTO `web_nav` VALUES (31, '头像管理', '设置头像', '', 2, 0, '/users/avatar', '2', '2024-07-07 13:00:58', '2024-10-06 21:36:58', 1, '', 2);
INSERT INTO `web_nav` VALUES (33, '意见反馈', '意见反馈', '', 9, 0, '/feedback', '2', '2024-09-22 16:38:25', NULL, 1, '请给出您的宝贵建议和反馈，这将对我们的产品建设起到更好的作用。', 3);

-- ----------------------------
-- Table structure for web_nav_cat
-- ----------------------------
DROP TABLE IF EXISTS `web_nav_cat`;
CREATE TABLE `web_nav_cat`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(166) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '类型名称',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '描述信息',
  `created_at` datetime NULL DEFAULT NULL COMMENT '创建日期',
  `updated_at` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint(2) NOT NULL DEFAULT 0 COMMENT '是否删除 0未删除 1已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '前端导航分类表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of web_nav_cat
-- ----------------------------
INSERT INTO `web_nav_cat` VALUES (1, '主导航', '用于官网的导航显示，删除后将不再显示！！', '2024-05-03 19:13:53', '2024-05-12 13:40:17', 0);
INSERT INTO `web_nav_cat` VALUES (2, '个人中心', '个人中心部分显示的菜单信息。', '2024-05-03 19:14:00', '2024-05-12 13:40:20', 0);
INSERT INTO `web_nav_cat` VALUES (3, '页脚导航', '在页脚调用的导航信息，包含隐私政策等。', '2024-05-03 19:14:07', '2024-05-12 13:40:14', 0);

-- ----------------------------
-- Table structure for web_opinion
-- ----------------------------
DROP TABLE IF EXISTS `web_opinion`;
CREATE TABLE `web_opinion`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '意见反馈说明',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '意见反馈内容',
  `is_deleted` tinyint(2) NULL DEFAULT NULL COMMENT '0未删除 1已删除',
  `ip` varchar(66) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'ip地址信息',
  `created_at` datetime NULL DEFAULT NULL,
  `updated_at` datetime NULL DEFAULT NULL,
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '页面路径',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '意见反馈表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of web_opinion
-- ----------------------------
INSERT INTO `web_opinion` VALUES (1, 'kkkk', 'kk', 1, '0:0:0:0:0:0:0:1', '2024-09-22 17:21:21', NULL, 'kkk');
INSERT INTO `web_opinion` VALUES (2, 'kkkk', 'kk', 1, '0:0:0:0:0:0:0:1', '2024-09-22 17:23:00', NULL, 'kkk');
INSERT INTO `web_opinion` VALUES (3, 'jjjj', 'jjj', 1, '0:0:0:0:0:0:0:1', '2024-09-22 17:23:15', NULL, 'jj');
INSERT INTO `web_opinion` VALUES (4, '跳转测试', '跳转测试', 1, '0:0:0:0:0:0:0:1', '2024-09-22 17:25:01', NULL, '/ccJumper');
INSERT INTO `web_opinion` VALUES (5, 'aaa', 'aaa', 1, '0:0:0:0:0:0:0:1', '2024-09-22 17:26:26', NULL, 'aaa');

-- ----------------------------
-- Table structure for web_post
-- ----------------------------
DROP TABLE IF EXISTS `web_post`;
CREATE TABLE `web_post`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '文章标题',
  `keywords` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '文章关键词',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '文章描述',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '文章内容',
  `views` int(11) NOT NULL DEFAULT 0 COMMENT '浏览人数',
  `is_deleted` tinyint(2) NOT NULL DEFAULT 0 COMMENT '0未删除 1已删除 2彻底删除',
  `created_at` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `cid` int(11) NULL DEFAULT NULL COMMENT '类型id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '图文信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of web_post
-- ----------------------------
INSERT INTO `web_post` VALUES (1, '用户注册说明', '注册用户信息', '帮助您快速注册个人账号的教程。', '# 注册账户说明\n\n# 步骤一、点击右上角注册按钮\n\n# 步骤二、进入注册界面，填写信息\n\n# 步骤三、接收验证码并提交请求\n\n# end：打开登录界面进行登录', 900, 0, '2024-05-12 10:50:26', '2024-05-12 10:59:51', 1);
INSERT INTO `web_post` VALUES (2, '测试添加', '测试添加', '', '	测试添加', 90, 2, '2024-05-12 11:01:27', '2024-05-12 11:03:55', 0);
INSERT INTO `web_post` VALUES (3, 'jkkkk', 'kkk', 'kkkkk\n', 'mjjj', 100, 1, '2024-05-12 11:08:24', '2024-05-12 11:09:23', 1);
INSERT INTO `web_post` VALUES (4, '测试1', '测试1', '测试1', '测试1', 100, 0, '2024-05-12 11:09:49', NULL, 1);
INSERT INTO `web_post` VALUES (5, '测试2', '测试2', '测试2', '# 人类一塌糊涂', 100, 0, '2024-05-12 11:10:03', '2024-09-22 13:08:12', 1);
INSERT INTO `web_post` VALUES (6, '测试图文详情', '测试图文详情显示', '图文详情显示', '# 显示图片\n![](https://oss.youqiong.net/dd263dacefd645319567e9532d003c6d/8e7d324622d94f2a8df6fc580c226a1f.png)\n\n# 显示代码\n`print(\"888\");`\n\n# 显示多行代码\n       // 获取select元素\n       const selectElement = document.getElementById(\'mySelect\');\n    \n       // 添加change事件监听器\n       selectElement.addEventListener(\'change\', function() {\n           console.log(\'Select value changed:\', this.value);\n       });\n       \n	   \n\n# 显示代码块\n	 12321\n\n# 显示多行代码风格2\n```\n   // 获取select元素\n   const selectElement = document.getElementById(\'mySelect\');\n\n   // 添加change事件监听器\n   selectElement.addEventListener(\'change\', function() {\n       console.log(\'Select value changed:\', this.value);\n   });\n   \n```\n\n# 显示表格\n|  序号 | 标题  |\n| ------------ | ------------ |\n| 1  | 事业有成  |\n|  2 |  顺顺利利 |\n\n# 显示序号\n1. 你好\n2. 世界\n\n###科学公式 TeX(KaTeX)\n                    \n$$E=mc^2$$\n\n行内的公式$$E=mc^2$$行内的公式，行内的$$E=mc^2$$公式。\n\n$$\\(\\sqrt{3x-1}+(1+x)^2\\)$$\n                    \n$$\\sin(\\alpha)^{\\theta}=\\sum_{i=0}^{n}(x^i + \\cos(f))$$\n\n$$X^2 > Y$$\n\n#####上标和下标\n\n上标：X&lt;sup&gt;2&lt;/sup&gt;\n\n下标：O&lt;sub&gt;2&lt;/sub&gt;\n\n##### 代码块里包含的过滤标签及属性不会被过滤\n\n```html\n&lt;style type=\"text/style\"&gt;\nbody{background:red;}\n&lt;/style&gt;\n\n&lt;script type=\"text/javscript\"&gt;\nalert(\"script\");\n&lt;/script&gt;\n\n&lt;iframe height=498 width=510 src=\"http://player.youku.com/embed/XMzA0MzIwMDgw\" frameborder=0 allowfullscreen&gt;&lt;/iframe&gt;\n```\n\n[========]\n\n#####Style\n\n```&lt;style&gt;\nbody{background:red;}\n&lt;/style&gt;\n\n&lt;style type=\"text/style\"&gt;\nbody{background:red;}\n&lt;/style&gt;```\n\n#####Script\n\n```&lt;script&gt;\nalert(\"script\");\n&lt;/script&gt;\n\n&lt;script type=\"text/javscript\"&gt;\nalert(\"script\");\n&lt;/script&gt;```', 100, 0, '2024-05-12 11:14:38', '2024-09-22 13:05:22', 2);

-- ----------------------------
-- Table structure for web_post_cat
-- ----------------------------
DROP TABLE IF EXISTS `web_post_cat`;
CREATE TABLE `web_post_cat`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `created_at` datetime NULL DEFAULT NULL,
  `updated_at` datetime NULL DEFAULT NULL,
  `is_deleted` tinyint(2) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '图文类型' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of web_post_cat
-- ----------------------------
INSERT INTO `web_post_cat` VALUES (1, '常见问题', '产品使用中常见疑难解答内容。\n', '2024-05-04 18:33:55', '2024-05-04 18:36:00', 0);
INSERT INTO `web_post_cat` VALUES (2, '使用说明', '常规操作使用说明，让使用没有烦恼。', '2024-05-04 18:34:02', '2024-05-04 18:36:20', 0);

-- ----------------------------
-- Table structure for web_push_product
-- ----------------------------
DROP TABLE IF EXISTS `web_push_product`;
CREATE TABLE `web_push_product`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `logo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `href` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `sort` int(11) NULL DEFAULT NULL,
  `created_at` datetime NULL DEFAULT NULL,
  `updated_at` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '推荐产品' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of web_push_product
-- ----------------------------
INSERT INTO `web_push_product` VALUES (1, '有穹', 'https://oss.youqiong.net/521932b0f54c4e86bc9a857d547b4b51/bb343015a9e0472d9c44d09e63173307.png', 'https://www.youqiong.net', 1, '2024-07-01 00:17:53', '2024-07-01 00:17:57');
INSERT INTO `web_push_product` VALUES (5, 'CHTML', 'https://oss.youqiong.net/9c9f3edc6583492fa5f238d289ac98b8/cb5c3102d9a541eb94b8b511720c35fe.png', 'http://www.chtml.cn', 1, '2024-07-01 00:23:41', '2024-07-01 00:25:28');
INSERT INTO `web_push_product` VALUES (6, '51Powand在线工具', 'https://oss.youqiong.net/20299631bb1e4aa797fb46884db8bfd4/9539c79243f94fada6b8176d19892b2e.png', 'http://51pwd.cn', 2, '2024-07-01 00:23:56', '2024-07-01 00:26:53');

-- ----------------------------
-- Table structure for web_users
-- ----------------------------
DROP TABLE IF EXISTS `web_users`;
CREATE TABLE `web_users`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nickname` varchar(56) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '昵称',
  `email` varchar(56) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '邮箱',
  `username` varchar(56) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '用户名 默认为自动生成的数字和英文',
  `phone` varchar(56) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '手机号',
  `password` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '密码',
  `created_at` datetime NULL DEFAULT NULL COMMENT '注册时间',
  `updated_at` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '头像',
  `login_at` datetime NULL DEFAULT NULL COMMENT '登录时间',
  `sex` tinyint(2) NULL DEFAULT NULL COMMENT '0保密 1男 2女',
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '最后登录ip',
  `enable` tinyint(2) NOT NULL DEFAULT 1 COMMENT '0禁用 1启用',
  `token` varchar(66) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '令牌凭据',
  `login_count` int(11) NULL DEFAULT NULL COMMENT '登录次数',
  `address` varchar(556) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '地理信息',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '前台用户数据表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of web_users
-- ----------------------------
INSERT INTO `web_users` VALUES (14, '大红貔貅', '861157525@qq.com', 'user-18511092960', '18511092960', '7fef6171469e80d32c0559f88b377245', '2024-10-05 19:47:59', '2024-10-05 19:47:59', 'https://oss.youqiong.net/fe41c846c8be43998f06cf1041a4e540/9ccc9fd22ee34152ba65bd901a7a6f05.png', '2025-05-08 22:07:40', 0, '0:0:0:0:0:0:0:1', 1, '9378bc461f2078542c02d41cd2b1d56d', 41, '江苏常州');
INSERT INTO `web_users` VALUES (15, '用户5305', '', 'user-15151965305', '15151965305', 'e10adc3949ba59abbe56e057f20f883e', '2024-10-07 13:47:49', '2024-10-07 13:47:49', '/default/avatar.jpg', '2024-10-07 14:21:12', 0, '0:0:0:0:0:0:0:1', 1, '785dcedd7194c147387e3438f59309bd', 3, '暂未填写~');

-- ----------------------------
-- Table structure for web_users_vipser
-- ----------------------------
DROP TABLE IF EXISTS `web_users_vipser`;
CREATE TABLE `web_users_vipser`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NULL DEFAULT NULL,
  `status` tinyint(2) NULL DEFAULT NULL COMMENT '开通状态: 激活(1)、未激活(0)、已过期(2)',
  `start_time` datetime NULL DEFAULT NULL COMMENT '最初冲会员的时间，不做更改',
  `end_time` datetime NULL DEFAULT NULL COMMENT '用于停止会员服务使用，每次充值后更新此时间，即失效时间',
  `renewals` int(11) NOT NULL DEFAULT 0 COMMENT '续费次数 单位次 仅供统计使用',
  `total_duration` int(11) NOT NULL DEFAULT 0 COMMENT '累计时长 单位秒 仅供统计使用',
  `last_renewal_time` datetime NULL DEFAULT NULL COMMENT '最近的续费时间，用于统计活跃付费用户',
  `income_id` int(11) NULL DEFAULT NULL COMMENT '增值服务id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '充值会员数据表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of web_users_vipser
-- ----------------------------
INSERT INTO `web_users_vipser` VALUES (2, 15, 1, '2024-04-30 16:15:28', '2025-05-01 16:15:31', 1, 31536000, '2024-04-30 16:15:53', 1);
INSERT INTO `web_users_vipser` VALUES (3, 14, 1, '2024-11-17 12:42:33', '2026-11-17 12:42:33', 2, 63072000, '2024-11-17 12:42:59', 1);

SET FOREIGN_KEY_CHECKS = 1;
