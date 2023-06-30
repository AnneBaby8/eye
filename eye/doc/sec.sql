-- ----------------------------
-- Table structure for sec_user
-- ----------------------------
DROP TABLE IF EXISTS `sec_user`;
CREATE TABLE `sec_user`
(
  `id`         bigint(11)  NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username`    varchar(50) NOT NULL COMMENT '用户名',
  `password`    varchar(60) NOT NULL COMMENT '密码',
  `nickname`    varchar(255)         DEFAULT NULL COMMENT '昵称',
  `phone`       varchar(11)          DEFAULT NULL COMMENT '手机',
  `email`       varchar(50)          DEFAULT NULL COMMENT '邮箱',
  `birthday`    datetime           DEFAULT NULL COMMENT '生日',
  `sex`         int(2)               DEFAULT NULL COMMENT '性别，男-1，女-2',
  `status`      int(2)      NOT NULL DEFAULT '1' COMMENT '状态，启用-1，禁用-0',
  `creator` varchar(100) DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建人时间',
  `updator` varchar(100) DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='用户表';

-- ----------------------------
-- Records of sec_user
-- ----------------------------
BEGIN;
INSERT INTO `sec_user`
VALUES (1, 'admin', '$2a$10$64iuSLkKNhpTN19jGHs7xePvFsub7ZCcCmBqEYw8fbACGTE3XetYq', '管理员',
        '17300000000', 'admin@163.com', null, 1, 1,null,null,null,null,0);
INSERT INTO `sec_user`
VALUES (2, 'user', '$2a$10$OUDl4thpcHfs7WZ1kMUOb.ZO5eD4QANW5E.cexBLiKDIzDNt87QbO', '普通用户',
        '17300001111', 'user@163.com', null, 1, 1,null,null,null,null,0);
COMMIT;


DROP TABLE IF EXISTS `sec_department`;
CREATE TABLE `sec_department`
(
  `id`         bigint(32)  NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name`    varchar(50) NOT NULL COMMENT '部门名称',
  `parent_id`   bigint(32) NOT NULL COMMENT '上级部门id',
  `is_grid`  int(1) DEFAULT '0' NULL COMMENT '是否是网格 0-否 1-是',
  `creator` varchar(100) DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建人时间',
  `updator` varchar(100) DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='部门表';


DROP TABLE IF EXISTS `sec_user_department`;
CREATE TABLE `sec_user_department`
(
  `id`  bigint(11)  NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id`bigint(11) NOT NULL COMMENT '用户主键',
  `department_id`bigint() NOT NULL COMMENT '部门主键',
  `creator` varchar(100) DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建人时间',
  `updator` varchar(100) DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del` tinyint(1) DEFAULT '0',
   PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='用户部门关系表';


-- ----------------------------
-- Table structure for sec_role
-- ----------------------------
DROP TABLE IF EXISTS `sec_role`;
CREATE TABLE `sec_role`
(
  `id`         bigint(11)  NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name`        varchar(50) NOT NULL COMMENT '角色名',
  `description` varchar(100) DEFAULT NULL COMMENT '描述',
  `code` varchar(100) DEFAULT NULL COMMENT '编码',
  `creator` varchar(100) DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建人时间',
  `updator` varchar(100) DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='角色表';

-- ----------------------------
-- Records of sec_role
-- ----------------------------
BEGIN;
INSERT INTO `sec_role`
VALUES (1, '管理员', '超级管理员', null, null, null,null,null,0);
COMMIT;


-- ----------------------------
-- Table structure for sec_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sec_user_role`;
CREATE TABLE `sec_user_role`
(
  `id`  bigint(11)  NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id`bigint(11) NOT NULL COMMENT '用户主键',
  `role_id`bigint(11) NOT NULL COMMENT '角色主键',
  `creator` varchar(100) DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建人时间',
  `updator` varchar(100) DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del` tinyint(1) DEFAULT '0',
  PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='用户角色关系表';

-- ----------------------------
-- Records of sec_user_role
-- ----------------------------
BEGIN;
INSERT INTO `sec_user_role`
VALUES (1,1, 1,null,null,null,null,0);
COMMIT;




-- ----------------------------
-- Table structure for sec_permission
-- ----------------------------
DROP TABLE IF EXISTS `sec_permission`;
CREATE TABLE `sec_permission`
(
  `id`        bigint(11)  NOT NULL AUTO_INCREMENT,
  `name`       varchar(50) NOT NULL COMMENT '权限名',
  `url`        varchar(1000) DEFAULT NULL COMMENT '类型为页面时，代表前端路由地址，类型为按钮时，代表后端接口地址',
  `type`       int(2)      NOT NULL COMMENT '权限类型，页面-1，按钮-2',
  `permission` varchar(50)   DEFAULT NULL COMMENT '权限表达式',
  `method`     varchar(50)   DEFAULT NULL COMMENT '后端接口访问方式',
  `sort`       int(11)     NOT NULL COMMENT '排序',
  `parent_id`  bigint(11)  NOT NULL COMMENT '父级id',
  `creator` varchar(100) DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建人时间',
  `updator` varchar(100) DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='权限表';




-- ----------------------------
-- Table structure for sec_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sec_role_permission`;
CREATE TABLE `sec_role_permission`
(
  `id`        bigint(11)  NOT NULL AUTO_INCREMENT,
  `role_id`      bigint(11) NOT NULL COMMENT '角色主键',
  `permission_id`bigint(11) NOT NULL COMMENT '权限主键',
  `creator` varchar(100) DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建人时间',
  `updator` varchar(100) DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del` tinyint(1) DEFAULT '0',
  PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='角色权限关系表';


ALTER TABLE `sec_user`
ADD COLUMN `out_user_id` varchar(36) NULL COMMENT '外部用户id' AFTER `del`;


