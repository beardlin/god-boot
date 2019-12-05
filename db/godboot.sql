/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50718
Source Host           : localhost:3306
Source Database       : godboot

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2019-12-05 15:29:51
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for act_flow
-- ----------------------------
DROP TABLE IF EXISTS `act_flow`;
CREATE TABLE `act_flow` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `flow_name` varchar(64) NOT NULL DEFAULT '' COMMENT '流程名称',
  `flow_code` varchar(512) DEFAULT '' COMMENT '编码规则',
  `flow_type` int(1) DEFAULT NULL COMMENT '流程分类（字典）',
  `flow_type_name` varchar(32) DEFAULT NULL COMMENT '流程分类名称',
  `flow_desc` varchar(255) DEFAULT NULL COMMENT '流程描述',
  `flow_node_type` int(1) NOT NULL COMMENT '流程类型（0自由1固定节点）',
  `flow_node_type_name` varchar(12) DEFAULT NULL COMMENT '流程类型名称',
  `form_type` int(1) DEFAULT NULL COMMENT '流程表单类型(0固定表单默认1非关系型表单)',
  `flow_scope` int(1) DEFAULT NULL COMMENT '流程可见范围（0全体1指定人可用）（当为1时flow_user表后期维护）',
  `from_json` longtext COMMENT '自定义表单（当formType为1时使用  类型text）',
  `table_name` varchar(64) DEFAULT NULL COMMENT '业务表单名称(当formType为0时使用）',
  `router_url` varchar(255) DEFAULT NULL COMMENT '路由地址(针对固定表单)',
  `flow_status` int(1) DEFAULT NULL COMMENT '是否发布（0未发布1发布）',
  `publish_date` datetime DEFAULT NULL COMMENT '发布时间',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新人',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `stand1` varchar(64) DEFAULT NULL COMMENT '预留1',
  `stand2` varchar(64) DEFAULT NULL COMMENT '预留2',
  `stand3` varchar(64) DEFAULT NULL COMMENT '预留3',
  `stand4` varchar(64) DEFAULT NULL COMMENT '预留4',
  `stand5` varchar(64) DEFAULT NULL COMMENT '预留5',
  `stand6` varchar(64) DEFAULT NULL COMMENT '预留6',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='流程定义表';

-- ----------------------------
-- Records of act_flow
-- ----------------------------
INSERT INTO `act_flow` VALUES ('1', '请假申请', '[{\"codeType\":\"constant\",\"codeValue\":\"QJ\"},{\"codeType\":\"date\",\"codeValue\":\"yyyyMMdd\"}]', '1', '考勤', '考勤', '1', '固定节点', '0', '0', '', 'work_leave_apply', null, '1', '2019-11-10 19:37:20', null, null, null, '2019-11-10 22:00:34', '', '', '', '', '', '');

-- ----------------------------
-- Table structure for act_flow_instence
-- ----------------------------
DROP TABLE IF EXISTS `act_flow_instence`;
CREATE TABLE `act_flow_instence` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '实例id',
  `flow_id` int(11) NOT NULL COMMENT '流程定义id',
  `code_rule` varchar(64) DEFAULT NULL COMMENT '编码规则',
  `code_aoto` int(11) DEFAULT NULL COMMENT '递增编号',
  `flow_code` varchar(64) DEFAULT '' COMMENT '编码',
  `data_id` varchar(64) DEFAULT NULL COMMENT '数据id',
  `table_name` varchar(64) DEFAULT NULL COMMENT '表单名称(针对固定表单)',
  `table_json` longtext COMMENT '自定义表单内容Json(针对非固定表单)',
  `flow_status` int(1) DEFAULT NULL COMMENT '流程状态（0草稿1审批中2结束3撤回）',
  `approver_type` int(2) DEFAULT NULL COMMENT '审批人类型（1人员2职务）',
  `approver_id` varchar(256) DEFAULT NULL COMMENT '当前节点审批人id(多个用逗号“,”分割)',
  `approver_office` bigint(20) DEFAULT NULL COMMENT '待审批部门id',
  `node_record_id` int(11) DEFAULT NULL COMMENT '审批记录id（快速定位）',
  `apply_date` datetime DEFAULT NULL COMMENT '申请时间',
  `apply_by` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '申请人id',
  `apply_by_name` varchar(64) DEFAULT NULL COMMENT '申请人名称',
  `apply_dep` varchar(64) DEFAULT NULL COMMENT '申请人部门id',
  `apply_dep_name` varchar(64) DEFAULT NULL COMMENT '申请人部门名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='流程实例表';

-- ----------------------------
-- Records of act_flow_instence
-- ----------------------------

-- ----------------------------
-- Table structure for act_flow_node
-- ----------------------------
DROP TABLE IF EXISTS `act_flow_node`;
CREATE TABLE `act_flow_node` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `flow_id` int(11) NOT NULL COMMENT '所属流程id',
  `node_name` varchar(64) NOT NULL COMMENT '节点名称',
  `approver_type` int(2) DEFAULT NULL COMMENT '审批人类型（1人员2职务）,如果当前部门下没有该角色人员则默认部门负责人审批',
  `approver_id` varchar(255) DEFAULT NULL COMMENT '当前节点审批人id(多个用逗号“,”分割)',
  `cros_offcie` int(2) DEFAULT NULL COMMENT '0本部门1跨部门（当approver_id为职务类型时才启用）',
  `approver_office` bigint(20) DEFAULT NULL COMMENT '待审批部门id',
  `node_sort` int(4) DEFAULT NULL COMMENT '多级审批节点排序',
  `node_type` int(1) DEFAULT NULL COMMENT '节点类型(0普通1或签2并签8开始9结束)（0普通只可选择一个审批人 或签和并签可选择多个审批人）',
  `end_flow` int(1) DEFAULT NULL COMMENT '是否可以终止流程（0不显示1显示）',
  `return_to` int(1) DEFAULT NULL COMMENT '退回节点（0发起人1上一级节点2之前任意节点）',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '修改人',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  `stand1` varchar(64) DEFAULT NULL COMMENT '预留1',
  `stand2` varchar(64) DEFAULT NULL COMMENT '预留2',
  `stand3` varchar(64) DEFAULT NULL COMMENT '预留3',
  `stand4` varchar(64) DEFAULT NULL COMMENT '预留4',
  `stand5` varchar(64) DEFAULT NULL COMMENT '预留5',
  `stand6` varchar(64) DEFAULT NULL COMMENT '预留6',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='审批节点表';

-- ----------------------------
-- Records of act_flow_node
-- ----------------------------
INSERT INTO `act_flow_node` VALUES ('1', '1', '开始', '0', '1', '0', '0', '1', '8', '0', '1', '1', '2019-11-22 22:56:21', '1', '2019-11-10 21:06:51', null, null, null, null, null, null);
INSERT INTO `act_flow_node` VALUES ('2', '1', '请假审批', '2', '2', '1', '1', '3', '0', '0', '1', '1', '2019-11-22 22:56:27', '1', '2019-11-22 22:56:32', null, null, null, null, null, null);
INSERT INTO `act_flow_node` VALUES ('3', '1', '结束', '2', '2', '1', '1', '4', '9', '0', '1', '1', '2019-11-22 22:56:27', '1', '2019-11-22 22:56:32', null, null, null, null, null, null);

-- ----------------------------
-- Table structure for act_node_record
-- ----------------------------
DROP TABLE IF EXISTS `act_node_record`;
CREATE TABLE `act_node_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '数据id',
  `flow_id` int(11) NOT NULL COMMENT '所属流程id',
  `flow_instance_id` int(11) NOT NULL COMMENT '所属流程实例id',
  `node_id` int(11) NOT NULL COMMENT '节点id',
  `node_name` varchar(64) DEFAULT NULL COMMENT '节点名称',
  `node_type` int(1) DEFAULT NULL,
  `approver_type` int(2) DEFAULT NULL COMMENT '审批人类型（1人员2职务）',
  `approver_id` varchar(64) DEFAULT NULL COMMENT '当前节点审批人id',
  `approver_office` bigint(20) DEFAULT NULL COMMENT '待审批部门id',
  `approver_name` varchar(64) DEFAULT NULL COMMENT '当前节点审批人姓名',
  `approve_result` int(1) DEFAULT NULL COMMENT '审批结果（0:待处理,1,通过,2:不通过,3终止）',
  `approve_comments` varchar(255) DEFAULT NULL COMMENT '审批意见',
  `approve_date` datetime DEFAULT NULL COMMENT '审批时间',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `stand1` varchar(64) DEFAULT NULL,
  `stand2` varchar(64) DEFAULT NULL,
  `stand3` varchar(64) DEFAULT NULL,
  `stand4` varchar(64) DEFAULT NULL,
  `stand5` varchar(64) DEFAULT NULL,
  `stand6` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='节点审批记录';

-- ----------------------------
-- Records of act_node_record
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_blob_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_calendars
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(120) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_cron_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`),
  KEY `IDX_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`,`INSTANCE_NAME`),
  KEY `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`,`INSTANCE_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_FT_J_G` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_T_G` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_FT_TG` (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_fired_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_J_GRP` (`SCHED_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_job_details
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_locks
-- ----------------------------
INSERT INTO `qrtz_locks` VALUES ('SystemScheduler', 'STATE_ACCESS');

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_paused_trigger_grps
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_scheduler_state
-- ----------------------------
INSERT INTO `qrtz_scheduler_state` VALUES ('SystemScheduler', 'LIN1575514398065', '1575515210087', '15000');

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_simple_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_simprop_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_J` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_C` (`SCHED_NAME`,`CALENDAR_NAME`),
  KEY `IDX_QRTZ_T_G` (`SCHED_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_STATE` (`SCHED_NAME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_STATE` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_G_STATE` (`SCHED_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST` (`SCHED_NAME`,`TRIGGER_STATE`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for schedule_job
-- ----------------------------
DROP TABLE IF EXISTS `schedule_job`;
CREATE TABLE `schedule_job` (
  `job_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务id',
  `bean_name` varchar(200) DEFAULT NULL COMMENT 'spring bean名称',
  `params` varchar(2000) DEFAULT NULL COMMENT '参数',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT 'cron表达式',
  `status` tinyint(4) DEFAULT NULL COMMENT '任务状态  0：正常  1：暂停',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`job_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='定时任务';

-- ----------------------------
-- Records of schedule_job
-- ----------------------------

-- ----------------------------
-- Table structure for schedule_job_log
-- ----------------------------
DROP TABLE IF EXISTS `schedule_job_log`;
CREATE TABLE `schedule_job_log` (
  `log_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务日志id',
  `job_id` bigint(20) NOT NULL COMMENT '任务id',
  `bean_name` varchar(200) DEFAULT NULL COMMENT 'spring bean名称',
  `params` varchar(2000) DEFAULT NULL COMMENT '参数',
  `status` tinyint(4) NOT NULL COMMENT '任务状态    0：成功    1：失败',
  `error` varchar(2000) DEFAULT NULL COMMENT '失败信息',
  `times` int(11) NOT NULL COMMENT '耗时(单位：毫秒)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`log_id`),
  KEY `job_id` (`job_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='定时任务日志';

-- ----------------------------
-- Records of schedule_job_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `param_key` varchar(50) DEFAULT NULL COMMENT 'key',
  `param_value` varchar(2000) DEFAULT NULL COMMENT 'value',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态   0：隐藏   1：显示',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `param_key` (`param_key`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='系统配置信息表';

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES ('1', 'CLOUD_STORAGE_CONFIG_KEY', '{\"aliyunAccessKeyId\":\"\",\"aliyunAccessKeySecret\":\"\",\"aliyunBucketName\":\"\",\"aliyunDomain\":\"\",\"aliyunEndPoint\":\"\",\"aliyunPrefix\":\"\",\"qcloudBucketName\":\"\",\"qcloudDomain\":\"\",\"qcloudPrefix\":\"\",\"qcloudSecretId\":\"\",\"qcloudSecretKey\":\"\",\"qiniuAccessKey\":\"NrgMfABZxWLo5B-YYSjoE8-AZ1EISdi1Z3ubLOeZ\",\"qiniuBucketName\":\"ios-app\",\"qiniuDomain\":\"http://7xqbwh.dl1.z0.glb.clouddn.com\",\"qiniuPrefix\":\"upload\",\"qiniuSecretKey\":\"uIwJHevMRWU0VLxFvgy0tAcOdGqasdtVlJkdy6vV\",\"type\":1}', '0', '云存储配置信息');

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `d_key` varchar(64) DEFAULT NULL COMMENT '字典key',
  `d_val` varchar(128) DEFAULT NULL COMMENT '字典value',
  `d_type` varchar(64) DEFAULT NULL COMMENT '字典分类',
  `d_lable` varchar(128) DEFAULT NULL COMMENT '分类描述',
  `d_sort` int(4) DEFAULT NULL COMMENT '字典排序',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` int(2) DEFAULT '0' COMMENT '删除标识（0否1是）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES ('1', '1', '集团', 'officeType', '机构类型', '1', 'admin', '2019-11-17 13:00:08', 'admin', '2019-11-17 13:00:13', '0');
INSERT INTO `sys_dict` VALUES ('2', '2', '区域', 'officeType', '机构类型', '2', 'admin', '2019-11-17 13:00:08', 'admin', '2019-11-17 13:00:13', '0');
INSERT INTO `sys_dict` VALUES ('3', '3', '公司', 'officeType', '机构类型', '3', 'admin', '2019-11-17 13:00:08', 'admin', '2019-11-17 13:00:13', '0');
INSERT INTO `sys_dict` VALUES ('4', '4', '部门', 'officeType', '机构类型', '4', 'admin', '2019-11-17 13:00:08', 'admin', '2019-11-17 13:00:13', '0');
INSERT INTO `sys_dict` VALUES ('5', '5', '小组', 'officeType', '机构类型', '5', 'admin', '2019-11-17 13:00:08', 'admin', '2019-11-17 13:00:13', '0');
INSERT INTO `sys_dict` VALUES ('6', '1', '人事', 'flowType', '流程分类', '1', 'admin', '2019-11-24 22:10:51', 'admin', '2019-11-24 22:10:56', '0');
INSERT INTO `sys_dict` VALUES ('7', '2', '财务', 'flowType', '流程分类', '2', 'admin', '2019-11-24 22:12:02', 'admin', '2019-11-24 22:12:08', '0');
INSERT INTO `sys_dict` VALUES ('8', '3', '采购', 'flowType', '流程分类', '3', 'admin', '2019-11-24 22:12:55', 'admin', '2019-11-24 22:13:00', '0');
INSERT INTO `sys_dict` VALUES ('9', '4', '领用', 'flowType', '流程分类', '4', 'admin', '2019-11-24 22:16:54', 'admin', '2019-11-24 22:16:56', '0');
INSERT INTO `sys_dict` VALUES ('10', '5', '考勤', 'flowType', '流程分类', '5', 'admin', '2019-11-24 22:17:38', 'admin', '2019-11-24 22:17:40', '0');
INSERT INTO `sys_dict` VALUES ('11', '1', '事假', 'leaveType', '请假类型', '1', 'admin', '2019-12-03 12:21:17', 'admin', '2019-12-03 12:21:22', '0');
INSERT INTO `sys_dict` VALUES ('12', '2', '病假', 'leaveType', '请假类型', '2', 'admin', '2019-12-03 12:21:17', 'admin', '2019-12-03 12:21:22', '0');
INSERT INTO `sys_dict` VALUES ('13', '3', '婚假', 'leaveType', '请假类型', '3', 'admin', '2019-12-03 12:21:17', 'admin', '2019-12-03 12:21:22', '0');
INSERT INTO `sys_dict` VALUES ('14', '4', '年假', 'leaveType', '请假类型', '4', 'admin', '2019-12-03 12:21:17', 'admin', '2019-12-03 12:21:22', '0');
INSERT INTO `sys_dict` VALUES ('15', '5', '调休', 'leaveType', '请假类型', '5', 'admin', '2019-12-03 12:21:17', 'admin', '2019-12-03 12:21:22', '0');
INSERT INTO `sys_dict` VALUES ('16', '6', '丧假', 'leaveType', '请假类型', '6', 'admin', '2019-12-03 12:21:17', 'admin', '2019-12-03 12:21:22', '0');
INSERT INTO `sys_dict` VALUES ('17', '7', '其他', 'leaveType', '请假类型', '7', 'admin', '2019-12-03 12:21:17', 'admin', '2019-12-03 12:21:22', '0');

-- ----------------------------
-- Table structure for sys_duty
-- ----------------------------
DROP TABLE IF EXISTS `sys_duty`;
CREATE TABLE `sys_duty` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `d_name` varchar(64) DEFAULT NULL COMMENT '名称',
  `d_status` int(2) DEFAULT NULL COMMENT '状态（0正餐1停用）',
  `d_sort` int(4) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_duty
-- ----------------------------

-- ----------------------------
-- Table structure for sys_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `file_name` varchar(128) DEFAULT NULL COMMENT '文件名称',
  `old_name` varchar(256) DEFAULT '' COMMENT '原始名称',
  `file_path` varchar(256) DEFAULT NULL COMMENT '存放路径',
  `use_status` int(2) DEFAULT '0' COMMENT '使用状态（0垃圾1已挂载）',
  `file_type` varchar(32) DEFAULT NULL COMMENT '文件类型',
  `file_size` bigint(11) DEFAULT NULL COMMENT '文件大小（byte）',
  `upload_time` datetime DEFAULT NULL COMMENT '上传时间',
  `upload_by` varchar(64) DEFAULT NULL COMMENT '上传人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统附件表';

-- ----------------------------
-- Records of sys_file
-- ----------------------------

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `operation` varchar(50) DEFAULT NULL COMMENT '用户操作',
  `method` varchar(200) DEFAULT NULL COMMENT '请求方法',
  `params` varchar(5000) DEFAULT NULL COMMENT '请求参数',
  `time` bigint(20) NOT NULL COMMENT '执行时长(毫秒)',
  `ip` varchar(64) DEFAULT NULL COMMENT 'IP地址',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统日志';

-- ----------------------------
-- Records of sys_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(200) DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(500) DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` int(11) DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) DEFAULT NULL COMMENT '菜单图标',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8 COMMENT='菜单管理';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', '0', '系统管理', null, null, '0', 'system', '0');
INSERT INTO `sys_menu` VALUES ('2', '1', '管理员列表', 'sys/user', null, '1', 'admin', '1');
INSERT INTO `sys_menu` VALUES ('3', '1', '角色管理', 'sys/role', null, '1', 'role', '2');
INSERT INTO `sys_menu` VALUES ('4', '1', '菜单管理', 'sys/menu', null, '1', 'menu', '3');
INSERT INTO `sys_menu` VALUES ('5', '1', 'SQL监控', 'http://localhost:8080/godboot/druid/sql.html', null, '1', 'sql', '4');
INSERT INTO `sys_menu` VALUES ('6', '1', '定时任务', 'job/schedule', null, '1', 'job', '5');
INSERT INTO `sys_menu` VALUES ('7', '6', '查看', null, 'sys:schedule:list,sys:schedule:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('8', '6', '新增', null, 'sys:schedule:save', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('9', '6', '修改', null, 'sys:schedule:update', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('10', '6', '删除', null, 'sys:schedule:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('11', '6', '暂停', null, 'sys:schedule:pause', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('12', '6', '恢复', null, 'sys:schedule:resume', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('13', '6', '立即执行', null, 'sys:schedule:run', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('14', '6', '日志列表', null, 'sys:schedule:log', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('15', '2', '查看', null, 'sys:user:list,sys:user:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('16', '2', '新增', null, 'sys:user:save,sys:role:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('17', '2', '修改', null, 'sys:user:update,sys:role:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('18', '2', '删除', null, 'sys:user:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('19', '3', '查看', null, 'sys:role:list,sys:role:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('20', '3', '新增', null, 'sys:role:save,sys:menu:list', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('21', '3', '修改', null, 'sys:role:update,sys:menu:list', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('22', '3', '删除', null, 'sys:role:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('23', '4', '查看', null, 'sys:menu:list,sys:menu:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('24', '4', '新增', null, 'sys:menu:save,sys:menu:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('25', '4', '修改', null, 'sys:menu:update,sys:menu:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('26', '4', '删除', null, 'sys:menu:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('27', '1', '参数管理', 'sys/config', 'sys:config:list,sys:config:info,sys:config:save,sys:config:update,sys:config:delete', '1', 'config', '6');
INSERT INTO `sys_menu` VALUES ('29', '1', '系统日志', 'sys/log', 'sys:log:list', '1', 'log', '7');
INSERT INTO `sys_menu` VALUES ('30', '1', '文件上传', 'oss/oss', 'sys:oss:all', '1', 'oss', '6');
INSERT INTO `sys_menu` VALUES ('31', '1', 'aaa', 'sys/test1', 'sys:test1:list', '1', '', '3');

-- ----------------------------
-- Table structure for sys_office
-- ----------------------------
DROP TABLE IF EXISTS `sys_office`;
CREATE TABLE `sys_office` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '树id',
  `p_id` bigint(20) DEFAULT NULL COMMENT '父id',
  `o_name` varchar(128) DEFAULT NULL COMMENT '名称',
  `o_sort` int(4) DEFAULT NULL COMMENT '排序',
  `o_type` int(2) DEFAULT NULL COMMENT '类型（1集团2区域3公司4部门5小组）',
  `o_type_name` varchar(64) DEFAULT NULL COMMENT '类型',
  `o_master_id` varchar(64) DEFAULT NULL COMMENT '负责人id',
  `o_master_name` varchar(64) DEFAULT NULL COMMENT '负责人',
  `o_email` varchar(64) DEFAULT NULL COMMENT '邮箱',
  `o_phone` varchar(64) DEFAULT NULL COMMENT '电话',
  `o_address` varchar(128) DEFAULT NULL COMMENT '地址',
  `o_status` int(2) DEFAULT NULL COMMENT '状态（0正常1停用）',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(512) DEFAULT NULL COMMENT '备注',
  `del_flag` int(2) DEFAULT '0' COMMENT '删除标记（0否1是）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='组织机构表';

-- ----------------------------
-- Records of sys_office
-- ----------------------------
INSERT INTO `sys_office` VALUES ('1', null, '集团', '1', '1', '集团', null, null, null, null, null, null, null, null, null, null, null, '0');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `role_type_id` int(11) DEFAULT NULL COMMENT '角色分类id',
  `permission` longtext COMMENT '权限标识符集合',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='角色';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('2', '超级管理员', '1', 'all', '我是超级管理员', '1', '2019-11-19 22:33:25');
INSERT INTO `sys_role` VALUES ('3', '管理员', '1', '12312312312312321', '管理员', '1', '2019-11-19 22:35:18');
INSERT INTO `sys_role` VALUES ('4', '管理员1', '1', 'sys:user:list,sys:user:save', '管理员1', '1', '2019-11-19 22:35:42');
INSERT INTO `sys_role` VALUES ('5', '角色1', '2', 'aadasd', '角色1', '1', '2019-11-19 22:38:26');
INSERT INTO `sys_role` VALUES ('6', '角色2', '2', 'aadasd', '角色2', '1', '2019-11-19 22:38:55');
INSERT INTO `sys_role` VALUES ('7', '角色2', '3', 'aadasd', '角色2', '1', '2019-11-19 22:39:01');
INSERT INTO `sys_role` VALUES ('8', '角色2', '3', 'aadasd', '角色2', '1', '2019-11-19 22:39:02');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='角色与菜单对应关系';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('1', '2', '1');
INSERT INTO `sys_role_menu` VALUES ('2', '2', '2');
INSERT INTO `sys_role_menu` VALUES ('3', '2', '3');
INSERT INTO `sys_role_menu` VALUES ('4', '2', '4');
INSERT INTO `sys_role_menu` VALUES ('5', '3', '1');
INSERT INTO `sys_role_menu` VALUES ('6', '3', '2');
INSERT INTO `sys_role_menu` VALUES ('7', '3', '3');
INSERT INTO `sys_role_menu` VALUES ('8', '3', '4');

-- ----------------------------
-- Table structure for sys_role_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_type`;
CREATE TABLE `sys_role_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type_name` varchar(64) DEFAULT NULL COMMENT '角色分类名称',
  `type_sort` int(11) DEFAULT NULL,
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_type
-- ----------------------------
INSERT INTO `sys_role_type` VALUES ('1', '管理员角色', '1', 'admin', '2019-11-19 21:46:36');
INSERT INTO `sys_role_type` VALUES ('2', '行政角色', '2', 'admin', '2019-11-19 21:46:41');
INSERT INTO `sys_role_type` VALUES ('3', '财务角色', '3', 'admin', '2019-11-19 21:46:44');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `real_name` varchar(64) DEFAULT NULL COMMENT '真实姓名',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `salt` varchar(20) DEFAULT NULL COMMENT '盐',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(100) DEFAULT NULL COMMENT '手机号',
  `sex` int(2) DEFAULT NULL COMMENT '性别(0女1男）',
  `birthday` date DEFAULT NULL COMMENT '出生日期',
  `id_card` varchar(32) DEFAULT NULL COMMENT '身份证号',
  `qq_num` varchar(32) DEFAULT NULL COMMENT 'QQ',
  `wei_chart` varchar(32) DEFAULT NULL COMMENT '微信号',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态  0：禁用   1：正常2锁定',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '身份证号\r\n',
  `user_code` varchar(64) DEFAULT NULL COMMENT '工号',
  `duty_ids` varchar(128) DEFAULT NULL COMMENT '职务ids',
  `duty_names` varchar(512) DEFAULT NULL COMMENT '职务',
  `office_id` bigint(20) DEFAULT NULL COMMENT '所属机构',
  `office_name` varchar(128) DEFAULT NULL COMMENT '所属机构',
  `err_num` int(2) DEFAULT '0' COMMENT '登录失败次数',
  `unlock_time` datetime DEFAULT NULL COMMENT '解锁时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='系统用户';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', '管理员', 'admin', 'c2774b203e153855396a6baabff6b1d39e5ffccd5d875ad7a4b48e5939a485b0', 'YzcmCZNvbXocrsz9dm8e', 'admin@13.com', '18596874598', '1', '2018-09-12', '37598666656452664', '1452955869', '12564', '1', '1', '2016-11-11 11:11:11', '', '', '', '1', '集团', '0', null);
INSERT INTO `sys_user` VALUES ('2', '测试', 'test', '63dc0046a3383b2ddeba0dbe094d37cc5b08af40899f3cb088d038488171974b', 'sHjdNjjGWJBQqOFU2QeB', 'test@qq.com', '18685478964', null, null, null, null, null, '1', '1', '2019-11-18 14:19:56', '123', '1', '1', '1', '集团', '0', null);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='用户与角色对应关系';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('2', '1', '2');
INSERT INTO `sys_user_role` VALUES ('3', '1', '3');
INSERT INTO `sys_user_role` VALUES ('5', '2', '2');
INSERT INTO `sys_user_role` VALUES ('6', '2', '3');

-- ----------------------------
-- Table structure for sys_user_token
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_token`;
CREATE TABLE `sys_user_token` (
  `user_id` bigint(20) NOT NULL,
  `token` varchar(100) NOT NULL COMMENT 'token',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `token` (`token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统用户Token';

-- ----------------------------
-- Records of sys_user_token
-- ----------------------------
INSERT INTO `sys_user_token` VALUES ('1', '1_7e27659dd64b47838afbeb6590883b0a', '2019-12-31 14:54:13', '2019-11-18 14:54:13');
INSERT INTO `sys_user_token` VALUES ('2', '2_edaa62fe7df9426e97e6ac03a30d5962', '2019-12-25 12:34:02', '2019-11-19 12:34:02');

-- ----------------------------
-- Table structure for work_leave_apply
-- ----------------------------
DROP TABLE IF EXISTS `work_leave_apply`;
CREATE TABLE `work_leave_apply` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `dic_leave_type` varchar(64) DEFAULT NULL COMMENT '请假类型',
  `dic_leave_type_name` varchar(64) DEFAULT NULL COMMENT '请假类型名称',
  `start_time` datetime DEFAULT NULL COMMENT '请假开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '请假结束时间',
  `hours` varchar(10) DEFAULT NULL COMMENT '请假小时数量',
  `days` varchar(128) DEFAULT NULL COMMENT '请假天数',
  `reason` varchar(128) DEFAULT NULL COMMENT '请假原因',
  `description` varchar(512) DEFAULT NULL COMMENT '备注',
  `file_ids` varchar(128) DEFAULT NULL COMMENT '附件id',
  `flow_instance_id` int(12) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建人员',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新人员',
  `office_id` bigint(20) DEFAULT NULL COMMENT '部门id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='W_请假申请';

-- ----------------------------
-- Records of work_leave_apply
-- ----------------------------
