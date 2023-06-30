-- ----------------------------
-- 事件预警(华为推送数据记录表)
-- ----------------------------
DROP TABLE IF EXISTS `event_warning`;
CREATE TABLE `event_warning`
(
    `id`           bigint(19) NOT NULL AUTO_INCREMENT,
    `stream_id`    varchar(255) DEFAULT NULL COMMENT '摄像头编号',
    `event_type`   bigint(11)   DEFAULT NULL COMMENT '占道经营检测算法其值固定为1114112,垃圾检测算法其值固定为1310720',
    `timestamp`    bigint(11)   DEFAULT NULL COMMENT '触发告警时间点的时间戳',
    `image_base64` longtext COMMENT '告警时刻输入的视频图像的Base64编码结果',
    `json`         longtext COMMENT '推送所有的参数',
    `create_time`  datetime     DEFAULT NULL COMMENT '创建时间',
    `warn_type`    varchar(2)   DEFAULT '0' COMMENT '0-占道经营 1-垃圾检测 2-重点上访人员 3-救助人群 4-空巢老人',
    `is_warning`   varchar(2)   DEFAULT '0' COMMENT '是否满足预警  0-不满足 1-满足',
    `is_start`     varchar(2)   DEFAULT '0' COMMENT '满足预警后流程启动  0-不 1-是',
    `reason`       varchar(128) DEFAULT NULL COMMENT '启动失败原因',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8 COMMENT ='事件预警';



DROP TABLE IF EXISTS `event_warning_detail`;
CREATE TABLE `event_warning_detail`
(
    `id`               bigint(11) NOT NULL AUTO_INCREMENT,
    `event_warning_id` bigint(11)   DEFAULT NULL COMMENT '事件预警ID',
    `username`         varchar(50)  DEFAULT NULL COMMENT '人员姓名',
    `picture`          varchar(60)  DEFAULT NULL COMMENT '人员图片',
    `sex`              int(2)       DEFAULT NULL COMMENT '性别，男-1，女-2',
    `phone`            varchar(11)  DEFAULT NULL COMMENT '手机',
    `tag`              varchar(50)  DEFAULT NULL COMMENT '标签',
    `address`          varchar(128) DEFAULT NULL COMMENT '地址',
    `track`            int(2)       DEFAULT NULL COMMENT '轨迹，进-1，出-2',
    `capture`          varchar(60)  DEFAULT NULL COMMENT '抓拍图片',
    `similarity`       varchar(60)  DEFAULT NULL COMMENT '相似度',
    `creator`          varchar(100) DEFAULT NULL COMMENT '创建人ID',
    `create_time`      datetime     DEFAULT NULL COMMENT '创建人时间',
    `updator`          varchar(100) DEFAULT NULL COMMENT '修改人ID',
    `update_time`      datetime     DEFAULT NULL COMMENT '修改时间',
    `del`              tinyint(1)   DEFAULT '0',
    `pic_left`	       bigint(11)	DEFAULT NULL COMMENT '人脸坐标-左',
    `pic_top`	       bigint(11)	DEFAULT NULL COMMENT '人脸坐标-上',
    `pic_right`	       bigint(11)	DEFAULT NULL COMMENT '人脸坐标-右',
    `pic_bottom`	   bigint(11)	DEFAULT NULL COMMENT '人脸坐标-下',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8 COMMENT ='事件预警详情表';

-- ----------------------------
--       设备表
-- ----------------------------
DROP TABLE IF EXISTS `device`;
CREATE TABLE `device`
(
    `id`            bigint(11)   NOT NULL AUTO_INCREMENT,
    `stream_id`     varchar(64)  NOT NULL COMMENT '华为摄像头唯一编码',
    `grid_id`       bigint(32)   NOT NULL COMMENT '归属网格id',
    `street_id`     bigint(19)   NOT NULL COMMENT '街道id',
    `social_id`     bigint(19)   NOT NULL COMMENT '社区id',
    `area_id`       bigint(19)   NOT NULL COMMENT '区id',
    `name`          varchar(255)          DEFAULT NULL COMMENT '名称',
    `address`       varchar(255)          DEFAULT NULL COMMENT '地址',
    `longitude`     varchar(256)          DEFAULT NULL COMMENT '经度',
    `latitude`      varchar(256)          DEFAULT NULL COMMENT '纬度',
    `platform`      varchar(64)           DEFAULT NULL COMMENT '接口调用平台',
    `warn_platform` varchar(64)           DEFAULT NULL COMMENT '预警推送平台',
    -- 状态此字段是否不要,需要实时请求华为的接口,还是华为实时能推送
    `status`        int(2)       NOT NULL DEFAULT '0' COMMENT '状态，不在线-0,在线-1',
    `algorithm`     varchar(100) NOT NULL COMMENT '算法类型',
    `direction`     int(2)                DEFAULT '0' COMMENT '卡口方向 1-入口,2-出口,0-非卡口置空',
    `creator`       varchar(100)          DEFAULT NULL COMMENT '创建人ID',
    `create_time`   datetime              DEFAULT NULL COMMENT '创建人时间',
    `updator`       varchar(100)          DEFAULT NULL COMMENT '修改人ID',
    `update_time`   datetime              DEFAULT NULL COMMENT '修改时间',
    `del`           tinyint(1)            DEFAULT '0',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8 COMMENT ='设备表';



DROP TABLE IF EXISTS `warning_config`;
CREATE TABLE `warning_config`
(
    `id`          bigint(11)   NOT NULL AUTO_INCREMENT,
    `warn_no`     varchar(100) NOT NULL COMMENT '预警编号',
    `name`        varchar(100) NOT NULL COMMENT '名称',
    `description` varchar(100) NOT NULL COMMENT '描述',
    `warn_type` varchar(100) NOT NULL COMMENT '0-通道占用 1-垃圾堆栈 2-帮扶人员 3-重点人员 4-空巢老人 5-人群密集 6-污水跑冒 7-机动车违停 8-非机动车违停',
    `status`      int(2)       NOT NULL DEFAULT '0' COMMENT '状态，生效-0,暂停-1',
    `creator`     varchar(100)          DEFAULT NULL COMMENT '创建人ID',
    `create_time` datetime              DEFAULT NULL COMMENT '创建人时间',
    `updator`     varchar(100)          DEFAULT NULL COMMENT '修改人ID',
    `update_time` datetime              DEFAULT NULL COMMENT '修改时间',
    `del`         tinyint(1)            DEFAULT '0',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8 COMMENT ='预警配置表';


DROP TABLE IF EXISTS `warning_policy`;
CREATE TABLE `warning_policy`
(
    `id`                bigint(11)   NOT NULL AUTO_INCREMENT,
    `config_id`         bigint(11)   DEFAULT NULL COMMENT '预警配置id',
    `policy_type`       varchar(100) NOT NULL COMMENT '0-实时预警,1-未发现预警,2-出入预警,3-拦截预警',
    `effect_start_time` datetime     DEFAULT NULL COMMENT '生效开始时间',
    `effect_end_time`   datetime     DEFAULT NULL COMMENT '生效结束时间',
    `warn_start_time`   varchar(100) DEFAULT NULL COMMENT '预警时段开始时间',
    `warn_end_time`     varchar(100) DEFAULT NULL COMMENT '预警时段结束时间',
    `remind`            varchar(100) DEFAULT NULL COMMENT '预警等级提醒',
    `warning`           varchar(100) DEFAULT NULL COMMENT '预警等级预警',
    `creator`           varchar(100) DEFAULT NULL COMMENT '创建人ID',
    `create_time`       datetime     DEFAULT NULL COMMENT '创建人时间',
    `updator`           varchar(100) DEFAULT NULL COMMENT '修改人ID',
    `update_time`       datetime     DEFAULT NULL COMMENT '修改时间',
    `del`               tinyint(1)   DEFAULT '0',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8 COMMENT ='预警策略表';


DROP TABLE IF EXISTS `device_grid_member`;
CREATE TABLE `device_grid_member`
(
    `id`             bigint(11) NOT NULL AUTO_INCREMENT,
    `device_id`      bigint(11)   DEFAULT NULL COMMENT '设备id',
    `grid_member_id` bigint(11)   DEFAULT NULL COMMENT '网格员id',
    `creator`        varchar(100) DEFAULT NULL COMMENT '创建人ID',
    `create_time`    datetime     DEFAULT NULL COMMENT '创建人时间',
    `updator`        varchar(100) DEFAULT NULL COMMENT '修改人ID',
    `update_time`    datetime     DEFAULT NULL COMMENT '修改时间',
    `del`            tinyint(1)   DEFAULT '0',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8 COMMENT ='设备关联网格员';



DROP TABLE IF EXISTS `workflow`;
CREATE TABLE `workflow`
(
    `id`                  bigint(11)  NOT NULL AUTO_INCREMENT,
    `event_no`            varchar(64)          DEFAULT NULL COMMENT '事件编号',
    `event_warning_id`    bigint(19)           DEFAULT NULL COMMENT '事件预警ID',
    `area_name`           varchar(64)          DEFAULT NULL COMMENT '所属区域名称',
    `event_type`          varchar(64) NOT NULL COMMENT '事件类型',
    `stream_id`           varchar(64) NOT NULL COMMENT '华为摄像头唯一编码',
    `device_name`         varchar(255)         DEFAULT NULL COMMENT '设备名称',
    `address`             varchar(255)         DEFAULT NULL COMMENT '地址',
    `image`               longtext COMMENT '告警时刻输入的视频图像的Base64编码结果',
    `handle_id`           bigint(11)           DEFAULT NULL COMMENT '处置部门的id',
    `assign_grid_id`      bigint(11)           DEFAULT NULL COMMENT '指派网格员id',
    `assign_grid_name`    varchar(64)          DEFAULT NULL COMMENT '指派网格员名称',
    `grid_user_id`        bigint(32)           DEFAULT NULL COMMENT '网格员id',
    `grid_user_name`      varchar(64)          DEFAULT NULL COMMENT '网格员名称',
    `grid_lead_user_id`   bigint(32)           DEFAULT NULL COMMENT '网格长id',
    `grid_lead_user_name` varchar(64)          DEFAULT NULL COMMENT '网格长名称',
    `grid_id`             bigint(32)  NOT NULL COMMENT '归属网格id',
    `street_id`           bigint(19)  NOT NULL COMMENT '街道id',
    `social_id`           bigint(19)  NOT NULL COMMENT '社区id',
    `area_id`             bigint(19)  NOT NULL COMMENT '区id',
    `version`             int(2)      NOT NULL DEFAULT '0' COMMENT '版本',
    `status`              int(2)      NOT NULL DEFAULT '0' COMMENT '状态  0-网格员  1-网格员上报处置部门2-处置部门退回给网格员 3-网格员上报街道 4-街道转处置部门 5-处置部门转街道  6-作废 7-完结',
    `warn_time`           datetime             DEFAULT NULL COMMENT '预警时间',
    `creator`             varchar(100)         DEFAULT NULL COMMENT '创建人ID',
    `create_time`         datetime             DEFAULT NULL COMMENT '创建人时间',
    `updator`             varchar(100)         DEFAULT NULL COMMENT '修改人ID',
    `update_time`         datetime             DEFAULT NULL COMMENT '修改时间',
    `del`                 tinyint(1)           DEFAULT '0',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8 COMMENT ='流程主表';


DROP TABLE IF EXISTS `workflow_file`;
CREATE TABLE `workflow_file`
(
    `id`          bigint(11) NOT NULL AUTO_INCREMENT,
    `workflow_id` bigint(11)   DEFAULT NULL COMMENT '流程主表id',
    `image`       varchar(255) DEFAULT NULL COMMENT '核查图片',
    `capture_time`       varchar(255) DEFAULT NULL COMMENT '核查时间',
    `creator`     varchar(100) DEFAULT NULL COMMENT '创建人ID',
    `create_time` datetime     DEFAULT NULL COMMENT '创建人时间',
    `updator`     varchar(100) DEFAULT NULL COMMENT '修改人ID',
    `update_time` datetime     DEFAULT NULL COMMENT '修改时间',
    `del`         tinyint(1)   DEFAULT '0',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8 COMMENT ='流程文件表';

DROP TABLE IF EXISTS `workflow_step`;
CREATE TABLE `workflow_step`
(
    `id`          bigint(11) NOT NULL AUTO_INCREMENT,
    `workflow_id` bigint(11)   DEFAULT NULL COMMENT '流程主表id',
    `role_name`   varchar(100) DEFAULT NULL COMMENT '角色名称',
    `role_code`   varchar(100) DEFAULT NULL COMMENT '角色编码',
    `user_id`     bigint(11)   DEFAULT NULL COMMENT '用户id',
    `name`        varchar(100) DEFAULT NULL COMMENT '用户名称',
    `self_type`   varchar(100) DEFAULT NULL COMMENT '自行处置类型 0-正常 1-核实后无情况 2-误报',
    `operation`   varchar(128) DEFAULT NULL COMMENT '操作',
    `remark`      varchar(128) DEFAULT NULL COMMENT '备注',
    `image`       varchar(255) DEFAULT NULL COMMENT '核查图片',
    `creator`     varchar(100) DEFAULT NULL COMMENT '创建人ID',
    `create_time` datetime     DEFAULT NULL COMMENT '创建人时间',
    `updator`     varchar(100) DEFAULT NULL COMMENT '修改人ID',
    `update_time` datetime     DEFAULT NULL COMMENT '修改时间',
    `del`         tinyint(1)   DEFAULT '0',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8 COMMENT ='流程步骤表';

DROP TABLE IF EXISTS `workflow_news`;
CREATE TABLE `workflow_news`
(
    `id`            bigint(11) NOT NULL AUTO_INCREMENT,
    `bussiness_key` varchar(100) DEFAULT NULL COMMENT '业务唯一标识',
    `new_type`      varchar(100) DEFAULT NULL COMMENT '消息类型 0-提醒 1-预警网格员 2-预警网格长 3-网格长督办  4-社区网格中心督办  5-网格长指派 6-处置部门退回 7-网格员上报 8-街道上报',
    `is_read`       tinyint(1)   DEFAULT '0' COMMENT '0-未读 1-已读',
    `title`         varchar(100) DEFAULT NULL COMMENT '标题',
    `content`       varchar(100) DEFAULT NULL COMMENT '内容',
    `user_id`       bigint(19)   DEFAULT NULL COMMENT '接收人',
    `creator`       varchar(100) DEFAULT NULL COMMENT '创建人ID',
    `create_time`   datetime     DEFAULT NULL COMMENT '创建人时间',
    `updator`       varchar(100) DEFAULT NULL COMMENT '修改人ID',
    `update_time`   datetime     DEFAULT NULL COMMENT '修改时间',
    `social_id`     bigint(19)   DEFAULT NULL COMMENT '社区id',
    `del`           tinyint(1)   DEFAULT '0',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8 COMMENT ='流程消息表';