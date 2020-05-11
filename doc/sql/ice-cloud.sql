/*
 Navicat Premium Data Transfer

 Source Server         : MySQL
 Source Server Type    : MySQL
 Source Server Version : 50727
 Source Host           : localhost:3306
 Source Schema         : ice-cloud

 Target Server Type    : MySQL
 Target Server Version : 50727
 File Encoding         : 65001

 Date: 11/05/2020 21:34:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details`  (
  `client_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `resource_ids` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `client_secret` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `scope` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `authorized_grant_types` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `web_server_redirect_uri` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `authorities` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `access_token_validity` int(11) NULL DEFAULT NULL,
  `refresh_token_validity` int(11) NULL DEFAULT NULL,
  `additional_information` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `autoapprove` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------
INSERT INTO `oauth_client_details` VALUES ('app', NULL, '$2a$10$9bEpZ/hWRQxyr5hn5wHUj.jxFpIrnOmBcWlE/g/0Zp3uNxt9QTh/S', 'app', 'password', NULL, NULL, NULL, NULL, NULL, 'true');

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`  (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `gmt_create` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order
-- ----------------------------

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `pid` bigint(20) NOT NULL DEFAULT 0 COMMENT '父ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部门名称',
  `sort` smallint(6) NULL DEFAULT NULL COMMENT '排序号',
  `description` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `gmt_create` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'sys_dept 部门表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (1187427997544611842, 0, 'ice-cloud', 0, 'ice-cloud', '2019-10-25 01:57:50', '2019-10-25 01:57:50');
INSERT INTO `sys_dept` VALUES (1200845109315297281, 1187427997544611842, 'IceDevCloud', 0, '', '2019-12-01 02:32:39', '2019-12-30 22:57:07');

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`  (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `pid` bigint(20) NOT NULL DEFAULT 0 COMMENT '父ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典名称',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '类型',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典编码',
  `value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '值',
  `sort` smallint(6) NULL DEFAULT NULL COMMENT '排序',
  `status` smallint(6) NOT NULL DEFAULT 1 COMMENT '状态',
  `description` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `gmt_create` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES (1200965702366846977, 0, 'miniapp', 'miniapp', NULL, NULL, NULL, 1, 'miniapp', '2019-12-01 10:31:50', '2019-12-01 10:31:50');
INSERT INTO `sys_dict` VALUES (1200965747455614977, 1200965702366846977, 'miniapp', 'miniapp', 'miniapp', 'miniapp1', 0, 1, 'miniapp', '2019-12-01 10:32:01', '2019-12-01 10:46:05');

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`  (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `pid` bigint(20) NULL DEFAULT 0 COMMENT '父ID',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限名称',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '路由名称',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '前端URL',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组件名称',
  `icon` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图标',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '网页链接',
  `perm_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限编码',
  `status` smallint(6) NULL DEFAULT 1 COMMENT '状态',
  `sort` decimal(10, 2) NULL DEFAULT 1.00 COMMENT '排序号',
  `type` smallint(6) NULL DEFAULT NULL COMMENT '类型 0.菜单 1 按钮',
  `hidden` tinyint(1) NULL DEFAULT NULL COMMENT '设为true后在左侧菜单不会显示该页面选项',
  `cache` tinyint(1) NULL DEFAULT NULL COMMENT '设为true后页面缓存',
  `description` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `gmt_create` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'sys_permission 权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES (39, 0, '系统管理', 'system', 'system', 'PageView', 'setting', NULL, '', 1, 99.00, 0, 0, 1, '', '2019-01-30 17:20:25', '2019-11-11 16:13:04');
INSERT INTO `sys_permission` VALUES (40, 39, '用户管理', 'userList', 'userList', 'SysUserList', 'user', NULL, '', 1, 1.10, 0, 0, 1, '', '2019-01-30 17:24:06', '2019-10-18 01:21:33');
INSERT INTO `sys_permission` VALUES (41, 39, '角色管理', 'roleList', 'roleList', 'SysRoleList', 'team', NULL, '', 1, 1.20, 0, 0, 1, '', '2019-01-30 17:28:50', '2019-10-18 01:21:50');
INSERT INTO `sys_permission` VALUES (42, 39, '权限管理', 'permissionList', 'permissionList', 'SysPermissionList', 'bars', NULL, '', 1, 1.40, 0, 0, 1, '', '2019-01-30 17:32:15', '2019-10-18 01:22:52');
INSERT INTO `sys_permission` VALUES (43, 39, '部门管理', 'dept', 'dept', 'SysDept', 'cluster', NULL, '', 1, 1.30, 0, 0, 1, '', '2019-01-30 17:34:17', '2019-10-23 21:35:58');
INSERT INTO `sys_permission` VALUES (44, 39, '字典管理', 'dict', 'SysDict', 'SysDict', 'read', NULL, '', 1, 1.50, 0, 0, 1, '', '2019-01-30 17:36:02', '2019-10-18 00:53:04');
INSERT INTO `sys_permission` VALUES (9999, 0, '首页', 'dashboard', 'dashboard', 'analysis', 'home', NULL, '', 0, 1.00, 0, 0, 1, '', '2019-01-30 17:20:25', '2019-10-21 00:20:31');
INSERT INTO `sys_permission` VALUES (1193803641346068482, 0, '积分小程序', 'integral', 'integral', 'PageView', 'gift', NULL, NULL, 1, 0.00, 0, 0, 1, NULL, '2019-11-11 16:12:22', '2019-11-11 16:14:09');
INSERT INTO `sys_permission` VALUES (1193804684154900481, 1193803641346068482, '积分任务管理', 'integralTask', 'integralTask', 'IntegralTask', 'bars', NULL, NULL, 1, 2.00, 0, 0, 1, NULL, '2019-11-11 16:16:31', '2019-12-02 09:34:00');
INSERT INTO `sys_permission` VALUES (1194866878137360385, 1193803641346068482, '用户管理', 'miniappUser', 'miniappUser', 'MiniappUser', 'user', NULL, NULL, 1, 0.00, 0, 0, 1, NULL, '2019-11-14 14:37:17', '2019-11-14 16:21:08');
INSERT INTO `sys_permission` VALUES (1194893558365151233, 1193803641346068482, '用户积分管理', 'userIntegral', 'userIntegral', 'UserIntegral', 'dollar', NULL, NULL, 1, 1.00, 0, 0, 1, NULL, '2019-11-14 16:23:19', '2019-12-02 09:33:50');
INSERT INTO `sys_permission` VALUES (1199207643911147521, 1193803641346068482, '邀请码管理', 'inviteCode', 'inviteCode', 'InviteCode', 'lock', NULL, NULL, 1, 3.00, 0, 0, 1, NULL, '2019-11-26 14:05:57', '2019-12-02 09:34:09');
INSERT INTO `sys_permission` VALUES (1211654385738805250, 40, '删除用户', NULL, NULL, NULL, 'delete', NULL, 'user:delUser', 1, 0.00, 1, 0, 1, NULL, '2019-12-30 22:24:51', '2019-12-30 22:24:51');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色名',
  `sort` smallint(6) NULL DEFAULT NULL COMMENT '排序号',
  `role_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色编码',
  `default_role` tinyint(1) NULL DEFAULT NULL COMMENT '默认角色',
  `description` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `status` smallint(1) NOT NULL DEFAULT 1 COMMENT '状态',
  `gmt_create` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'sys_role 角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (8, '管理员', 1, 'ROLE_ADMIN', NULL, '123', 1, '2018-12-05 09:23:49', '2020-04-05 04:45:52');

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`  (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色ID',
  `permission_id` bigint(20) NULL DEFAULT NULL COMMENT '权限ID',
  `gmt_create` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'sys_role_permission 角色 权限关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES (1321, 1, 9999, '2019-09-22 18:45:44', '2019-09-22 21:02:34');
INSERT INTO `sys_role_permission` VALUES (1211654484875374594, 8, 39, '2019-12-30 22:25:15', '2019-12-30 22:25:15');
INSERT INTO `sys_role_permission` VALUES (1211654484883763201, 8, 40, '2019-12-30 22:25:15', '2019-12-30 22:25:15');
INSERT INTO `sys_role_permission` VALUES (1211654484892151810, 8, 41, '2019-12-30 22:25:15', '2019-12-30 22:25:15');
INSERT INTO `sys_role_permission` VALUES (1211654484892151811, 8, 42, '2019-12-30 22:25:15', '2019-12-30 22:25:15');
INSERT INTO `sys_role_permission` VALUES (1211654484892151812, 8, 44, '2019-12-30 22:25:15', '2019-12-30 22:25:15');
INSERT INTO `sys_role_permission` VALUES (1211654484900540418, 8, 43, '2019-12-30 22:25:15', '2019-12-30 22:25:15');
INSERT INTO `sys_role_permission` VALUES (1211654484900540419, 8, 1211654385738805250, '2019-12-30 22:25:15', '2019-12-30 22:25:15');

-- ----------------------------
-- Table structure for sys_tenant
-- ----------------------------
DROP TABLE IF EXISTS `sys_tenant`;
CREATE TABLE `sys_tenant`  (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户名称',
  `status` smallint(6) NULL DEFAULT NULL COMMENT '租户状态',
  `gmt_create` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'sys_tenant 多租户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_tenant
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `status` smallint(6) NULL DEFAULT 1 COMMENT '状态',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '手机号',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '邮箱',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `gmt_create` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username_unique`(`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'sys_user 用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1192, 'Admin', '$2a$10$s5mSC1Fmp373zDriazuf2eMv.xBCexcMFIJFM5vxINEvVme6.IjV2', 'XiaoBingBy', 'https://file.iviewui.com/dist/a0e88e83800f138b94d2414621bd9704.png', 1, '18888888888', 'XiaoBingBy@qq.com', 1200845109315297281, '2018-12-05 09:24:50', '2020-05-10 18:44:38');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色ID',
  `gmt_create` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'sys_user_role 用户 角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1138476677047816194, NULL, 8, '2019-06-12 00:02:46', '2019-06-12 00:02:46');
INSERT INTO `sys_user_role` VALUES (1259434167017406466, 1192, 8, '2020-05-10 18:44:39', '2020-05-10 18:44:39');

SET FOREIGN_KEY_CHECKS = 1;
