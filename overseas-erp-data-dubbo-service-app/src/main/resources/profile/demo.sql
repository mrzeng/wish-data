CREATE TABLE `task_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号-自增',
  `task_title` varchar(32) NOT NULL DEFAULT '' COMMENT '任务标题',
  `task_description` varchar(128) NOT NULL DEFAULT '' COMMENT '任务描述',
  `task_type` varchar(12) NOT NULL COMMENT '任务类型',
  `time_type` varchar(12) DEFAULT 'single' COMMENT '循环类型',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modify_time` timestamp NOT NULL DEFAULT '2000-01-01 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `execute_time` datetime NOT NULL DEFAULT '2000-01-01 00:00:00' COMMENT '执行时间',
  `task_state` varchar(12) NOT NULL DEFAULT '' COMMENT '任务状态',
  `flow_state` varchar(12) NOT NULL DEFAULT '' COMMENT '流程状态',
  `user_id` varchar(64) NOT NULL,
  `user_name` varchar(32) NOT NULL,
  `loop_num` int(10) DEFAULT '-1' COMMENT '循环数值',
  `loop_type` varchar(32) DEFAULT '' COMMENT '循环单位',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE `task_detail` (
  `id` bigint(20) NOT NULL,
  `task_id` bigint(20) NOT NULL COMMENT '任务Id',
  `user_name` varchar(32) NOT NULL,
  `create_time` datetime NOT NULL,
  `modify_time` timestamp NOT NULL DEFAULT '2000-01-01 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `task_config` mediumtext NOT NULL,
  `task_log` mediumtext NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;