CREATE TABLE `user` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `account` varchar(50) DEFAULT NULL COMMENT '账号',
  `name` varchar(100) DEFAULT NULL COMMENT '姓名',
  `pwd` varchar(50) DEFAULT NULL COMMENT '密码',
  `status` int(1) DEFAULT '1' COMMENT '是否禁用',
  `add_time` datetime DEFAULT NULL COMMENT '新增时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='后台账号';


CREATE TABLE `carousel` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `home1` varchar(255) DEFAULT NULL COMMENT '首页轮播图',
  `level1` varchar(255) DEFAULT NULL COMMENT '二级轮播图',
  `home2` varchar(255) DEFAULT NULL COMMENT '首页轮播图',
  `level2` varchar(255) DEFAULT NULL COMMENT '二级轮播图',
  `open` int(1) DEFAULT NULL COMMENT '是否开启',
  `add_time` datetime DEFAULT NULL COMMENT '新增时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='轮播图';


CREATE TABLE `list_award` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `code` varchar(10) DEFAULT NULL COMMENT 'WEEK:周榜 MONTH-月榜',
  `open` int(1) DEFAULT NULL COMMENT '是否开启',
  `type` int(1) DEFAULT NULL COMMENT '1-后援金 2-小程序开屏 3-首页轮播 4-户外大屏',
  `img` varchar(255) DEFAULT NULL COMMENT '宣传页',
  `open_min` int(1) DEFAULT NULL COMMENT '是否开启最低热力值',
  `min_val` int(10) DEFAULT NULL COMMENT '最低热力值',
  `add_time` datetime DEFAULT NULL COMMENT '新增时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE `resources` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `type` int(1) DEFAULT NULL COMMENT '资源类型(1-后援金 2-小程序开屏 3-首页轮播 4-户外大屏)',
  `mark` varchar(50) DEFAULT NULL COMMENT '金额和大屏名称',
  `target` int(10) DEFAULT NULL COMMENT '目标人数',
  `begin_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `tags` varchar(500) DEFAULT NULL COMMENT '明星标签',
  `add_time` datetime DEFAULT NULL COMMENT '新增时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='资源表';


CREATE TABLE `star_resources_rel` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `resources_id` bigint(20) DEFAULT NULL COMMENT '资源id',
  `star_id` bigint(20) DEFAULT NULL COMMENT '明星id',
  `join_num` int(10) DEFAULT '0' COMMENT '参与人数',
  `reach_num` int(10) DEFAULT '0' COMMENT '达成人数',
  `status` int(1) DEFAULT '0' COMMENT '任务是否完成',
  `add_time` datetime DEFAULT NULL COMMENT '新增时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='明星资源关系表';


CREATE TABLE `fens` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `fens_id` bigint(20) DEFAULT NULL COMMENT 'fens_id',
  `open_id` varchar(50) DEFAULT NULL COMMENT 'open_id',
  `nick_name` varchar(100) DEFAULT NULL COMMENT '网名',
  `phone` varchar(20) DEFAULT NULL COMMENT '电话',
  `last_visit_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `session_key` varchar(255) DEFAULT NULL COMMENT 'session_key',
  `city` varchar(30) DEFAULT NULL COMMENT '市',
  `province` varchar(30) DEFAULT NULL COMMENT '省',
  `country` varchar(30) DEFAULT NULL COMMENT '国',
  `avatar_url` varchar(500) DEFAULT NULL COMMENT '头像',
  `gender` int(1) DEFAULT NULL COMMENT '性别',
  `vigour_val` int(10) DEFAULT '0' COMMENT '当前活力值',
  `total_vigour_val` int(10) DEFAULT '0' COMMENT '累计活力值',
  `consume_vigour_val` int(10) DEFAULT '0' COMMENT '消耗活力值',
  `slogan` varchar(20) DEFAULT NULL COMMENT '个人标语',
  `slogan_open` int(1) DEFAULT '0' COMMENT '是否开启',
  `draw_num` int(10) DEFAULT '0' COMMENT '抽奖次数',
  `add_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='微信用户信息';


CREATE TABLE `fens_join` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `resources_rel_id` bigint(20) DEFAULT NULL COMMENT '明星资源关联id',
  `fens_id` bigint(20) DEFAULT NULL COMMENT '粉丝id',
  `complete_num` int(10) DEFAULT '0' COMMENT '完成次数',
  `add_time` datetime DEFAULT NULL COMMENT '新增时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='粉丝资源参与表';


CREATE TABLE `fens_vigour_log` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `fens_id` bigint(20) DEFAULT NULL COMMENT '粉丝id',
  `star_id` bigint(20) DEFAULT NULL COMMENT '明星id',
  `type` int(1) DEFAULT NULL COMMENT '热力值类型(1-签到 2-抽奖 3-看视频 4-分享 5-赠送)',
  `vigour_val` int(10) DEFAULT NULL COMMENT '热力值',
  `vig_time` date DEFAULT NULL COMMENT '获取热力值日期 年月日',
  `add_user` varchar(50) DEFAULT NULL COMMENT '赠送人',
  `add_time` datetime DEFAULT NULL COMMENT '新增时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='粉丝热力获取记录';


CREATE TABLE `guard` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `fens_id` bigint(20) DEFAULT NULL COMMENT '粉丝id',
  `star_id` bigint(20) DEFAULT NULL COMMENT '明星id',
  `status` int(1) DEFAULT '0' COMMENT '是否属于守护者(打榜999) 0否1是',
  `add_time` datetime DEFAULT NULL COMMENT '新增时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='守护表';


CREATE TABLE `function` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父id',
  `code` varchar(50) DEFAULT NULL COMMENT 'code',
  `name` varchar(100) DEFAULT NULL COMMENT '菜单名称',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `uri` varchar(255) DEFAULT NULL COMMENT '地址',
  `http_method` varchar(10) DEFAULT NULL COMMENT '请求方式',
  `sort_val` int(3) DEFAULT NULL COMMENT '排序',
  `status` int(1) DEFAULT NULL COMMENT '状态',
  `icon` varchar(50) DEFAULT NULL COMMENT '图标',
  `add_time` datetime DEFAULT NULL COMMENT '新增时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单表';


CREATE TABLE `star` (
  `id` bigint(20) NOT NULL,
  `star_id` bigint(20) DEFAULT NULL COMMENT 'star_id',
  `name` varchar(64) DEFAULT NULL COMMENT '姓名',
  `avatar` varchar(512) DEFAULT NULL COMMENT '明星头像',
  `picture` varchar(512) DEFAULT NULL COMMENT '明星照片',
  `tags` varchar(512) DEFAULT NULL COMMENT '标签',
  `hot_search` tinyint(1) DEFAULT NULL COMMENT '是否热门搜索 0否；1是；',
  `open_img` varchar(512) DEFAULT NULL COMMENT '所属开屏图',
  `hot_nums` int(11) DEFAULT NULL COMMENT '明星热力值',
  `home_img` varchar(512) DEFAULT NULL COMMENT '首页轮播图',
  `detail_img` varchar(512) DEFAULT NULL COMMENT '详情页',
  `hit_popup_img` varchar(512) DEFAULT NULL COMMENT '打榜弹窗图',
  `rank_week_champion_num` int(11) DEFAULT '0' COMMENT '周冠军次数',
  `rank_month_champion_num` int(11) DEFAULT '0' COMMENT '月冠军次数',
  `rank_week_second_num` int(11) DEFAULT '0' COMMENT '周亚军次数',
  `rank_month_second_num` int(11) DEFAULT '0' COMMENT '月亚军次数',
  `rank_week_third_num` int(11) DEFAULT NULL COMMENT '周季军次数',
  `rank_month_third_num` int(11) DEFAULT NULL COMMENT '月季军次数',
  `this_week_rank` int(11) DEFAULT '0' COMMENT '本周排名',
  `this_month_rank` int(11) DEFAULT '0' COMMENT '本月排名',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='明星表';


CREATE TABLE `hit_list` (
  `id` bigint(20) NOT NULL,
  `star_id` bigint(20) DEFAULT NULL COMMENT '打榜明星id',
  `vigour_val` int(10) DEFAULT NULL COMMENT '本次消耗活力值',
  `fens_id` bigint(20) DEFAULT NULL COMMENT '打榜粉丝id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='打榜记录表';


CREATE TABLE `tags` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `addTime` date DEFAULT NULL COMMENT '新增时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='标签表';


CREATE TABLE `star_tags` (
  `id` bigint(20) NOT NULL,
  `star_id` bigint(20) DEFAULT NULL COMMENT '明星id',
  `tags_id` bigint(20) DEFAULT NULL COMMENT '标签id',
  `tags_name` varchar(255) DEFAULT NULL COMMENT '标签名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='明星标签表';


CREATE TABLE `hit_settings` (
  `id` bigint(20) NOT NULL,
  `draw_field_nums` varchar(32) DEFAULT NULL COMMENT '热力抽奖8栏位数值 例：11,13,43,55,22',
  `deaw_max_num` int(11) DEFAULT '0' COMMENT '每日抽奖最高次数',
  `score_strategy_flag` int(11) DEFAULT '0' COMMENT '高分值策略开关 0：关闭；1开启；',
  `strategy_deaw_min_num` int(11) DEFAULT '0' COMMENT '单个粉丝累计抽奖次数，超过后随机赠送热力值',
  `vigour_send_type` int(11) DEFAULT '0' COMMENT '随机热力值赠送方式 0：超过（含）具体值；1：超过（含）当前八档数值；',
  `vigour_send_num` int(11) DEFAULT '0' COMMENT '具体赠送热力值',
  `sign_max_num` int(11) DEFAULT '0' COMMENT '每日最高签到次数',
  `vigour_sign_num` int(11) DEFAULT '0' COMMENT '每次签到获热力值数',
  `video_max_num` int(11) DEFAULT '0' COMMENT '每日看视频次数',
  `vigour_video_num` int(11) DEFAULT '0' COMMENT '每次看视频获得热力值数',
  `share_max_num` int(11) DEFAULT '0' COMMENT '每日最高分享微信次数',
  `vigour_share_num` int(11) DEFAULT NULL COMMENT '每次分享微信获得热力值数',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='热力设置表';


INSERT INTO `star`.`function`(`id`, `parent_id`, `code`, `name`, `description`, `uri`, `http_method`, `sort_val`, `status`, `icon`, `add_time`, `update_time`) VALUES (1, 0, NULL, '明星列表', NULL, NULL, NULL, 1, 1, NULL, NULL, NULL);
INSERT INTO `star`.`function`(`id`, `parent_id`, `code`, `name`, `description`, `uri`, `http_method`, `sort_val`, `status`, `icon`, `add_time`, `update_time`) VALUES (2, 0, NULL, '粉丝管理', NULL, NULL, NULL, 2, 1, NULL, NULL, NULL);
INSERT INTO `star`.`function`(`id`, `parent_id`, `code`, `name`, `description`, `uri`, `http_method`, `sort_val`, `status`, `icon`, `add_time`, `update_time`) VALUES (3, 0, NULL, '热力设置', NULL, NULL, NULL, 3, 1, NULL, NULL, NULL);
INSERT INTO `star`.`function`(`id`, `parent_id`, `code`, `name`, `description`, `uri`, `http_method`, `sort_val`, `status`, `icon`, `add_time`, `update_time`) VALUES (4, 0, NULL, '轮播设置', NULL, NULL, NULL, 4, 1, NULL, NULL, NULL);
INSERT INTO `star`.`function`(`id`, `parent_id`, `code`, `name`, `description`, `uri`, `http_method`, `sort_val`, `status`, `icon`, `add_time`, `update_time`) VALUES (5, 0, NULL, '轮播设置', NULL, NULL, NULL, 5, 1, NULL, NULL, NULL);
INSERT INTO `star`.`function`(`id`, `parent_id`, `code`, `name`, `description`, `uri`, `http_method`, `sort_val`, `status`, `icon`, `add_time`, `update_time`) VALUES (6, 0, NULL, '账号管理', NULL, NULL, NULL, 6, 1, NULL, NULL, NULL);


INSERT INTO `star`.`user`(`id`, `account`, `name`, `pwd`, `status`, `add_time`, `update_time`) VALUES (1, 'admin', '管理员', 'e10adc3949ba59abbe56e057f20f883e', 1, '2020-12-18 16:25:04', '2020-12-18 16:25:08');
