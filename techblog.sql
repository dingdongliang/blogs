/*
Navicat MySQL Data Transfer

Source Server         : 本地mysql
Source Server Version : 50721
Source Host           : localhost:3306
Source Database       : techblog

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-08-23 18:01:23
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for blog_article
-- ----------------------------
DROP TABLE IF EXISTS `blog_article`;
CREATE TABLE `blog_article` (
  `article_id` varchar(32) NOT NULL COMMENT '文章表主键',
  `article_title` varchar(255) NOT NULL COMMENT '文章标题',
  `creater` varchar(32) DEFAULT NULL COMMENT '作者ID，外键',
  `article_info` longtext COMMENT '文章正文',
  `sort_id` varchar(32) DEFAULT NULL COMMENT '文章分类ID，外键',
  `article_views` int(11) DEFAULT NULL COMMENT '访问量',
  `created` datetime DEFAULT NULL COMMENT '提交时间',
  `modifyer` varchar(32) DEFAULT NULL COMMENT '修改人',
  `lastmod` datetime DEFAULT NULL COMMENT '修改时间',
  `status` char(1) NOT NULL DEFAULT 'E' COMMENT '当前状态,E:有效的,I:无效的',
  `article_summary` varchar(255) DEFAULT NULL COMMENT '摘要',
  PRIMARY KEY (`article_id`),
  KEY `FK_article_user` (`creater`),
  KEY `FK_article_sort` (`sort_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for blog_sort
-- ----------------------------
DROP TABLE IF EXISTS `blog_sort`;
CREATE TABLE `blog_sort` (
  `sort_id` varchar(32) NOT NULL COMMENT '文章分类表主键',
  `sort_name` varchar(255) DEFAULT NULL COMMENT '分类名称',
  PRIMARY KEY (`sort_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `PMSN_ID` varchar(32) NOT NULL COMMENT '权限ID',
  `MENU_NAME` varchar(100) NOT NULL COMMENT '菜单名称',
  `MENU_CODE` varchar(100) NOT NULL COMMENT '菜单代码',
  `PRNT_ID` varchar(32) DEFAULT NULL COMMENT '父权限ID',
  `PRNT_NAME` varchar(100) DEFAULT NULL COMMENT '父权限名称',
  `PMSN_CODE` varchar(100) NOT NULL COMMENT '权限代码',
  `PMSN_TYPE` varchar(100) NOT NULL DEFAULT 'menu' COMMENT '权限资源类型(menu为菜单、button为按钮)',
  `STATUS` char(1) NOT NULL DEFAULT 'E' COMMENT '当前状态,E:有效的,I:无效的',
  `PMSN_URL` varchar(500) DEFAULT NULL COMMENT '权限对应URL',
  `PMSN_DESC` varchar(500) DEFAULT NULL COMMENT '权限描述',
  `REQUIRED` char(1) DEFAULT 'N' COMMENT '是否必选权限，Y是N否',
  `CREATED` datetime DEFAULT NULL COMMENT '创造日期',
  `LASTMOD` datetime DEFAULT NULL COMMENT '修改日期',
  `CREATER` varchar(32) DEFAULT NULL COMMENT '创建人',
  `MODIFYER` varchar(32) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`PMSN_ID`),
  UNIQUE KEY `PMSN_CODE` (`PMSN_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限表';

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `ROLE_ID` varchar(32) NOT NULL COMMENT '角色ID',
  `ROLE_NAME` varchar(100) NOT NULL COMMENT '角色名称',
  `ROLE_DESC` varchar(500) DEFAULT NULL COMMENT '角色描述',
  `STATUS` char(1) NOT NULL DEFAULT 'E' COMMENT '当前状态,E:有效的,I:无效的',
  `CREATED` datetime DEFAULT NULL COMMENT '创造日期',
  `LASTMOD` datetime DEFAULT NULL COMMENT '修改日期',
  `CREATER` varchar(32) DEFAULT NULL COMMENT '创建人',
  `MODIFYER` varchar(32) DEFAULT NULL COMMENT '修改人',
  `IS_DEFAULT` char(1) DEFAULT 'N' COMMENT '是否默认角色',
  PRIMARY KEY (`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Table structure for sys_role_pmsn
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_pmsn`;
CREATE TABLE `sys_role_pmsn` (
  `RP_ID` varchar(32) NOT NULL COMMENT '权限角色配置ID',
  `ROLE_ID` varchar(32) NOT NULL COMMENT '角色ID',
  `PMSN_ID` varchar(32) NOT NULL COMMENT '权限ID',
  `STATUS` char(1) NOT NULL DEFAULT 'E' COMMENT '当前状态,E:有效的,I:无效的',
  `CREATED` datetime DEFAULT NULL COMMENT '创造日期',
  `LASTMOD` datetime DEFAULT NULL COMMENT '修改日期',
  `CREATER` varchar(32) DEFAULT NULL COMMENT '创建人',
  `MODIFYER` varchar(32) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`RP_ID`),
  KEY `FK_RP_ROLE` (`ROLE_ID`),
  KEY `FK_RP_PMSN` (`PMSN_ID`),
  CONSTRAINT `sys_role_pmsn_ibfk_1` FOREIGN KEY (`PMSN_ID`) REFERENCES `sys_permission` (`PMSN_ID`),
  CONSTRAINT `sys_role_pmsn_ibfk_2` FOREIGN KEY (`ROLE_ID`) REFERENCES `sys_role` (`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限角色表';

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` varchar(32) NOT NULL COMMENT '用户表主键',
  `user_name` varchar(255) DEFAULT NULL COMMENT '用户名',
  `created` datetime DEFAULT NULL COMMENT '注册时间',
  `account` varchar(255) DEFAULT NULL COMMENT '登录帐号',
  `user_pwd` varchar(50) DEFAULT NULL,
  `phone` varchar(11) DEFAULT NULL COMMENT '联系方式',
  `status` char(1) DEFAULT 'E' COMMENT '当前状态,E:有效的,I:无效的',
  `creater` varchar(32) DEFAULT NULL COMMENT '操作人',
  `lastmod` datetime DEFAULT NULL COMMENT '修改时间',
  `modifyer` varchar(32) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_user_pmsn
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_pmsn`;
CREATE TABLE `sys_user_pmsn` (
  `UPM_ID` varchar(32) NOT NULL COMMENT '用户权限配置ID',
  `USER_ID` varchar(32) NOT NULL COMMENT '用户ID',
  `PMSN_ID` varchar(32) NOT NULL COMMENT '权限ID',
  `STATUS` char(1) NOT NULL DEFAULT 'E' COMMENT '当前状态,E:有效的,I:无效的',
  `CREATED` datetime DEFAULT NULL COMMENT '创造日期',
  `LASTMOD` datetime DEFAULT NULL COMMENT '修改日期',
  `CREATER` varchar(32) DEFAULT NULL COMMENT '创建人',
  `MODIFYER` varchar(32) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`UPM_ID`),
  KEY `FK_UPM_USER` (`USER_ID`),
  KEY `FK_UPM_PMSN` (`PMSN_ID`),
  CONSTRAINT `sys_user_pmsn_ibfk_1` FOREIGN KEY (`PMSN_ID`) REFERENCES `sys_permission` (`PMSN_ID`),
  CONSTRAINT `sys_user_pmsn_ibfk_2` FOREIGN KEY (`USER_ID`) REFERENCES `sys_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户权限表';

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `UR_ID` varchar(32) NOT NULL COMMENT '用户角色配置ID',
  `ROLE_ID` varchar(32) NOT NULL COMMENT '角色ID',
  `USER_ID` varchar(32) NOT NULL COMMENT '用户ID',
  `STATUS` char(1) NOT NULL DEFAULT 'E' COMMENT '当前状态,E:有效的,I:无效的',
  `CREATED` datetime DEFAULT NULL COMMENT '创造日期',
  `LASTMOD` datetime DEFAULT NULL COMMENT '修改日期',
  `CREATER` varchar(32) DEFAULT NULL COMMENT '创建人',
  `MODIFYER` varchar(32) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`UR_ID`),
  KEY `FK_UR_ROLE` (`ROLE_ID`),
  KEY `FK_UR_USER` (`USER_ID`),
  CONSTRAINT `sys_user_role_ibfk_1` FOREIGN KEY (`ROLE_ID`) REFERENCES `sys_role` (`ROLE_ID`),
  CONSTRAINT `sys_user_role_ibfk_2` FOREIGN KEY (`USER_ID`) REFERENCES `sys_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色表';
