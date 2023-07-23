/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 80016
 Source Host           : 127.0.0.1:3306
 Source Schema         : authentication-server

 Target Server Type    : MySQL
 Target Server Version : 80016
 File Encoding         : 65001

 Date: 22/07/2023 19:42:36
*/

SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for base_app
-- ----------------------------
DROP TABLE IF EXISTS `base_app`;
CREATE TABLE `base_app`
(
    `id`                 bigint(20) NOT NULL AUTO_INCREMENT,
    `secret`             varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '应用密钥',
    `name`               varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '应用名称',
    `enabled`            bit(1)                                                 NOT NULL DEFAULT b'0' COMMENT '是否激活',
    `remark`             varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
    `created_date`       datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `last_modified_date` datetime(0) NULL DEFAULT NULL COMMENT '最后修改时间',
    `created_by`         varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `last_modified_by`   varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最后修改人',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `uk_app_name`(`name`) USING BTREE COMMENT '应用名称唯一'
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '应用表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_menu
-- ----------------------------
DROP TABLE IF EXISTS `base_menu`;
CREATE TABLE `base_menu`
(
    `id`                 bigint(20) NOT NULL AUTO_INCREMENT,
    `parent_id`          bigint(20) NULL DEFAULT NULL COMMENT '父级主键id',
    `app_id`             bigint(20) NOT NULL COMMENT '应用id',
    `permission_id`      varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限标识',
    `name`               varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单名称',
    `type`               int(11) NOT NULL COMMENT '菜单类型（1：菜单；2：按钮；3：接口）',
    `path`               varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '路由或接口地址',
    `method`             varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求方式',
    `sort_num`           int(11) NULL DEFAULT NULL COMMENT '排序字段（值越小越靠前，仅仅针对前端路由）',
    `hide`               bit(1)                                                 NOT NULL COMMENT '是否是隐藏菜单',
    `meta`               varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '前端菜单元数据',
    `remark`             varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
    `created_date`       datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `last_modified_date` datetime(0) NULL DEFAULT NULL COMMENT '最后修改时间',
    `created_by`         varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `last_modified_by`   varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最后修改人',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_role
-- ----------------------------
DROP TABLE IF EXISTS `base_role`;
CREATE TABLE `base_role`
(
    `id`                 bigint(20) NOT NULL AUTO_INCREMENT,
    `app_id`             bigint(20) NOT NULL COMMENT '应用id',
    `name`               varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
    `remark`             varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
    `created_date`       datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `last_modified_date` datetime(0) NULL DEFAULT NULL COMMENT '最后修改时间',
    `created_by`         varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `last_modified_by`   varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最后修改人',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `base_role_menu`;
CREATE TABLE `base_role_menu`
(
    `id`                 bigint(20) NOT NULL AUTO_INCREMENT,
    `role_id`            bigint(20) NULL DEFAULT NULL,
    `menu_id`            bigint(20) NULL DEFAULT NULL,
    `created_by`         varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `created_date`       datetime(6) NULL DEFAULT NULL,
    `last_modified_by`   varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `last_modified_date` datetime(6) NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX                `fk_base_role_menu_role_id`(`role_id`) USING BTREE,
    INDEX                `fk_base_role_menu_menu_id`(`menu_id`) USING BTREE,
    CONSTRAINT `fk_base_role_menu_menu_id` FOREIGN KEY (`menu_id`) REFERENCES `base_menu` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `fk_base_role_menu_role_id` FOREIGN KEY (`role_id`) REFERENCES `base_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单角色中间表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_user
-- ----------------------------
DROP TABLE IF EXISTS `base_user`;
CREATE TABLE `base_user`
(
    `id`                 bigint(20) NOT NULL AUTO_INCREMENT,
    `app_id`             bigint(20) NOT NULL COMMENT '应用id',
    `username`           varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '用户名',
    `password`           varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
    `valid_time`         datetime(6) DEFAULT NULL COMMENT '过期时间，不填永久有效',
    `enabled`            bit(1)                                                  NOT NULL COMMENT '激活状态：true 激活；false 未激活',
    `locked`             bit(1)                                                  NOT NULL COMMENT '锁定状态：true 锁定；false 未锁定',
    `remark`             varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
    `created_date`       datetime                                                DEFAULT NULL COMMENT '创建时间',
    `last_modified_date` datetime                                                DEFAULT NULL COMMENT '最后修改时间',
    `created_by`         varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人',
    `last_modified_by`   varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '最后修改人',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_app_id_username` (`app_id`,`username`) USING BTREE COMMENT '用户名应用下唯一'
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='基础用户';
-- ----------------------------
-- Table structure for base_user_role
-- ----------------------------
DROP TABLE IF EXISTS `base_user_role`;
CREATE TABLE `base_user_role`
(
    `id`                 bigint(20) NOT NULL AUTO_INCREMENT,
    `user_id`            bigint(20) NULL DEFAULT NULL,
    `role_id`            bigint(20) NULL DEFAULT NULL,
    `created_by`         varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `created_date`       datetime(6) NULL DEFAULT NULL,
    `last_modified_by`   varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `last_modified_date` datetime(6) NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX                `fk_base_user_role_user_id`(`user_id`) USING BTREE,
    INDEX                `fk_base_user_role_role_id`(`role_id`) USING BTREE,
    CONSTRAINT `fk_base_user_role_role_id` FOREIGN KEY (`role_id`) REFERENCES `base_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `fk_base_user_role_user_id` FOREIGN KEY (`user_id`) REFERENCES `base_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户角色中间表' ROW_FORMAT = Dynamic;

SET
FOREIGN_KEY_CHECKS = 1;
