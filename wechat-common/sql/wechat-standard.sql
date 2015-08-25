-- Dumping database structure for wechat-standard
CREATE DATABASE IF NOT EXISTS `wechat-standard` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `wechat-standard`;


-- Dumping structure for table wechat-standard.sys_tenant_config
CREATE TABLE IF NOT EXISTS `sys_tenant_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `tenant_id` varchar(20) NOT NULL COMMENT '广场ID',
  `tenant_name` varchar(50) NOT NULL COMMENT '广场名称',
  `pay_id` varchar(50) NOT NULL COMMENT '支付ID',
  `style` varchar(32) NOT NULL COMMENT '模板风格',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `tenant_id` (`tenant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COMMENT='广场配置信息';


-- Dumping structure for table wechat-standard.sys_wx_config
CREATE TABLE IF NOT EXISTS `sys_wx_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `tenant_id` varchar(20) NOT NULL COMMENT '广场id',
  `wx_no` varchar(50) NOT NULL COMMENT '微信号',
  `name` varchar(50) NOT NULL COMMENT '微信名称',
  `app_secret` varchar(50) NOT NULL COMMENT '密钥',
  `app_id` varchar(50) NOT NULL COMMENT 'appId',
  `aes_key` varchar(64) NOT NULL COMMENT '消息加解密密钥',
  `domain` varchar(200) NOT NULL COMMENT '微信请求域名',
  `token` varchar(256) NOT NULL COMMENT '微信token，可设置策略，2小时更换一次',
  `origin_id` varchar(32) NOT NULL COMMENT '微信原始ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `wx_no` (`wx_no`),
  UNIQUE KEY `tenant_id` (`tenant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COMMENT='微信配置';

-- Dumping structure for table wechat-standard.wc_event_response
CREATE TABLE IF NOT EXISTS `wc_event_response` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `tenant_id` varchar(20) NOT NULL COMMENT '广场ID',
  `key` varchar(32) NOT NULL COMMENT '事件KEY值，当不是图文时，值为media的id',
  `content_type` tinyint(4) NOT NULL COMMENT '回复内容类型 0 一般信息 1图片 2图文',
  `content` varchar(1024) NOT NULL COMMENT '响应内容',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `tenant_id_key` (`tenant_id`,`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='微信事件响应，暂时支持菜单点击事件';

-- Dumping structure for table wechat-standard.wc_mass_result
CREATE TABLE IF NOT EXISTS `wc_mass_result` (
  `id` int(11) NOT NULL,
  `tenant_id` varchar(20) NOT NULL COMMENT '广场ID',
  `content_type` varchar(32) NOT NULL COMMENT '群发内容类型',
  `content` varchar(4096) NOT NULL COMMENT '群发内容',
  `to_users` text NOT NULL COMMENT '接受者',
  `msg_id` varchar(32) NOT NULL COMMENT '群发的消息ID（微信群发id）',
  `status` tinyint(4) NOT NULL COMMENT '消息状态 1 成功 2 失败 3 部分失败',
  `total_count` int(11) NOT NULL DEFAULT '0' COMMENT 'group_id下粉丝数；或者openid_list中的粉丝数，-1表示所有',
  `filter_count` int(11) NOT NULL DEFAULT '0' COMMENT '过滤（过滤是指特定地区、性别的过滤、用户设置拒收的过滤，用户接收已超4条的过滤）后，准备发送的粉丝数，原则上，FilterCount = SentCount + ErrorCount',
  `send_count` int(11) NOT NULL DEFAULT '0' COMMENT '发送成功的粉丝数',
  `error_count` int(11) NOT NULL DEFAULT '0' COMMENT '发送失败的粉丝数',
  `send_time` datetime NOT NULL COMMENT '微信发送消息的时间',
  `create_time` datetime NOT NULL COMMENT '万江龙平台发送消息时间',
  PRIMARY KEY (`id`),
  KEY `tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='群发结果';

-- Dumping structure for table wechat-standard.wc_media
CREATE TABLE IF NOT EXISTS `wc_media` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增序列',
  `tenant_id` varchar(20) NOT NULL COMMENT '广场ID',
  `media_id` varchar(100) NOT NULL COMMENT '媒体文件上传后，获取时的唯一标识',
  `type` varchar(10) NOT NULL COMMENT '图片image、语音voice、视频video和缩略图thumb',
  `expires_time` datetime DEFAULT NULL COMMENT '过期时间，null表示永久',
  `url` varchar(400) DEFAULT NULL COMMENT 'url，用于显示',
  `create_time` datetime NOT NULL COMMENT '媒体文件上传时间戳',
  PRIMARY KEY (`id`),
  UNIQUE KEY `tenant_id_media_id` (`tenant_id`,`media_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='微信媒体素材';

-- Dumping structure for table wechat-standard.wc_menu
CREATE TABLE IF NOT EXISTS `wc_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tenant_id` varchar(20) NOT NULL COMMENT '广场id',
  `parent_id` int(11) DEFAULT NULL COMMENT '父菜单Id',
  `weight` tinyint(4) NOT NULL COMMENT '权重，值与位置对应',
  `title` varchar(16) NOT NULL COMMENT '菜单标题',
  `type` varchar(32) DEFAULT NULL COMMENT '菜单类型（view，click，media_id，view_limited），父菜单null',
  `value` varchar(256) DEFAULT NULL COMMENT '回复内容（link，media_id，news_key)，父菜单null',
  `html_tag` varchar(1024) DEFAULT NULL COMMENT '显示内容，父菜单null',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `tenant_id` (`tenant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COMMENT='微信菜单';

-- Dumping structure for table wechat-standard.wc_msg_receive
CREATE TABLE IF NOT EXISTS `wc_msg_receive` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `tenant_id` varchar(20) NOT NULL COMMENT '广场ID',
  `from_user` varchar(100) NOT NULL COMMENT '消息发送方，用户发送的消息，存储openid，回复的消息，存储微信号',
  `to_user` varchar(100) NOT NULL COMMENT '消息接收方，用户发送的消息，存储微信号，回复的消息，存储openid',
  `msg_id` varchar(64) DEFAULT NULL COMMENT '消息id',
  `msg_type` varchar(20) NOT NULL COMMENT '消息类型',
  `msg_content` varchar(2000) NOT NULL COMMENT '消息内容',
  `replied` tinyint(4) NOT NULL COMMENT '是否已回复 0 未回复 1 已回复',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `tenant_id` (`tenant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9954 DEFAULT CHARSET=utf8mb4 COMMENT='微信消息';

-- Dumping structure for table wechat-standard.wc_msg_reply
CREATE TABLE IF NOT EXISTS `wc_msg_reply` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增序列',
  `tenant_id` varchar(20) NOT NULL COMMENT '广场ID',
  `name` varchar(32) NOT NULL COMMENT '规则名称',
  `type` tinyint(4) NOT NULL COMMENT '回复类型 0 关键字 1 关注',
  `reply_method` tinyint(4) NOT NULL COMMENT '回复方式 0 随机 1全部',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='微信被动回复';

-- Dumping structure for table wechat-standard.wc_msg_reply_content
CREATE TABLE IF NOT EXISTS `wc_msg_reply_content` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `reply_id` int(11) NOT NULL COMMENT '关键词ID',
  `type` varchar(20) NOT NULL COMMENT '内容类型',
  `content` varchar(1024) DEFAULT NULL COMMENT '文本内容',
  `media_id` int(11) DEFAULT NULL COMMENT '媒体内容ID',
  PRIMARY KEY (`id`),
  KEY `reply_id` (`reply_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='微信自动回复内容';

-- Dumping structure for table wechat-standard.wc_msg_reply_rule
CREATE TABLE IF NOT EXISTS `wc_msg_reply_rule` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `reply_id` int(11) NOT NULL COMMENT '回复ID',
  `keyword` varchar(32) NOT NULL COMMENT '关键词',
  `pattern` tinyint(4) NOT NULL COMMENT '关键词匹配方式 0 模糊 1 全匹配',
  PRIMARY KEY (`id`),
  KEY `reply_id` (`reply_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='微信自动回复';

-- Dumping structure for table wechat-standard.wc_news
CREATE TABLE IF NOT EXISTS `wc_news` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增序列',
  `tenant_id` varchar(20) NOT NULL COMMENT '广场ID',
  `type` varchar(10) NOT NULL COMMENT '类型 mpnews 微信图文 news 高级图文（客服、被动回复）',
  `is_multiple` tinyint(1) NOT NULL COMMENT '是否是多图文 1是 0否',
  `media_id` varchar(100) DEFAULT NULL COMMENT '微信图文素材的media_id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  KEY `tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='图文素材主表';

-- Dumping structure for table wechat-standard.wc_news_article
CREATE TABLE IF NOT EXISTS `wc_news_article` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `news_id` int(11) NOT NULL COMMENT '消息主表id',
  `title` varchar(50) NOT NULL COMMENT '标题',
  `digest` varchar(500) DEFAULT NULL COMMENT '图文消息的摘要，仅有单图文消息才有摘要，多图文此处为空',
  `author` varchar(32) DEFAULT NULL COMMENT '作者（微信图文）',
  `pic_url` varchar(200) DEFAULT NULL COMMENT '图片地址（高级图文）',
  `thumb_media_id` varchar(32) DEFAULT NULL COMMENT '图文消息的封面图片素材id（必须是永久mediaID）（微信图文）',
  `show_cover_pic` tinyint(1) DEFAULT NULL COMMENT '是否显示封面，0为false，即不显示，1为true，即显示（微信图文）',
  `cover_pic_url` varchar(200) DEFAULT NULL COMMENT '封面图片地址',
  `content` text COMMENT '图文消息的具体内容，支持HTML标签，必须少于2万字符，小于1M，且此处会去除JS',
  `content_source_url` varchar(200) DEFAULT NULL COMMENT '图文页面（微信图文可为null）',
  `weight` tinyint(4) NOT NULL COMMENT '权重',
  PRIMARY KEY (`id`),
  KEY `news_id` (`news_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='图文素材子表';

-- Dumping structure for table wechat-standard.wc_news_content_img
CREATE TABLE IF NOT EXISTS `wc_news_content_img` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增序列',
  `news_article_id` int(11) NOT NULL COMMENT '图文子表序号',
  `wx_img_url` varchar(200) NOT NULL COMMENT '微信图片地址，腾讯系域名外无法显示',
  `out_url` varchar(200) NOT NULL COMMENT '图片外链地址，用于显示',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `news_article_id` (`news_article_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='公众平台新增图文内容中图片上传接口，并过滤外链图片';

-- Dumping structure for table wechat-standard.wc_token
CREATE TABLE IF NOT EXISTS `wc_token` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tenant_id` varchar(200) NOT NULL,
  `token` varchar(256) NOT NULL,
  `expires_time` datetime NOT NULL COMMENT '过期时间',
  PRIMARY KEY (`id`),
  KEY `tenant_id` (`tenant_id`(191))
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4;

-- Dumping structure for table wechat-standard.wx_attachment
CREATE TABLE IF NOT EXISTS `wx_attachment` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(128) DEFAULT NULL COMMENT '标题',
  `file_name` varchar(128) DEFAULT NULL COMMENT '文件名',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `url` varchar(200) NOT NULL COMMENT '资源保存路径',
  `extension` varchar(12) DEFAULT NULL COMMENT '文件扩展名',
  `size` varchar(32) DEFAULT NULL COMMENT '资源大小',
  `type` varchar(12) DEFAULT NULL COMMENT '资源类型',
  `remark` varchar(1000) DEFAULT NULL COMMENT '描述',
  `tenant_id` varchar(20) DEFAULT NULL COMMENT '广场ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  KEY `tenant_id` (`tenant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COMMENT='上传的资源文件';

-- Dumping structure for table wechat-standard.wx_auto_reply
CREATE TABLE IF NOT EXISTS `wx_auto_reply` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `tenant_id` int(11) NOT NULL COMMENT '广场ID',
  `name` varchar(32) NOT NULL COMMENT '自动回复名称',
  `key` varchar(32) NOT NULL COMMENT '关键词',
  `key_pattern` tinyint(4) NOT NULL COMMENT '关键词匹配方式 0 模糊 1 全匹配',
  `content_id` int(11) NOT NULL COMMENT '回复内容ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='微信自动回复';

-- Dumping structure for table wechat-standard.wx_event_config
CREATE TABLE IF NOT EXISTS `wx_event_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `event_type` tinyint(4) NOT NULL COMMENT '事件类型 1 视图 2 点击 3.…',
  `content` varchar(1024) NOT NULL COMMENT '回复内容',
  `reply_type` tinyint(4) NOT NULL COMMENT '回复类型 1 文字 2 图片 3 图文 4 视频',
  `content_keys` varchar(1024) DEFAULT NULL COMMENT '内容关键字',
  `status` tinyint(4) NOT NULL COMMENT '状态:1正常 0 删除',
  `tenant_id` varchar(20) NOT NULL COMMENT '广场',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COMMENT='微信事件配置';

-- Dumping structure for table wechat-standard.wx_fans
CREATE TABLE IF NOT EXISTS `wx_fans` (
  `id` varchar(100) NOT NULL COMMENT 'openid',
  `nickname` varchar(100) DEFAULT NULL COMMENT '昵称',
  `sex` varchar(50) DEFAULT NULL COMMENT '性别',
  `language` varchar(20) DEFAULT NULL COMMENT '语言',
  `city` varchar(40) DEFAULT NULL COMMENT '城市',
  `province` varchar(40) DEFAULT NULL COMMENT '省',
  `country` varchar(50) DEFAULT NULL COMMENT '国家',
  `headimgurl` varchar(200) DEFAULT NULL COMMENT '头像',
  `subscribe_time` datetime DEFAULT NULL COMMENT '关注时间',
  `subscribe` tinyint(4) NOT NULL DEFAULT '1' COMMENT '关注状态，1、关注，0、取消关注',
  `tenant_id` varchar(200) DEFAULT NULL,
  `wx_no` varchar(32) NOT NULL COMMENT '公众号微信号',
  `wjl_openid` varchar(100) DEFAULT NULL COMMENT '商圈圈openid',
  PRIMARY KEY (`id`),
  KEY `wx_no` (`wx_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='微信关注用户';


-- Dumping structure for table wechat-standard.wx_history_msg
CREATE TABLE IF NOT EXISTS `wx_history_msg` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `msg_id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='历时消息，内容有图片、图文、语音';

-- Dumping structure for table wechat-standard.wx_menu
CREATE TABLE IF NOT EXISTS `wx_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `menu_json` varchar(2048) NOT NULL COMMENT '菜单json',
  `status` int(11) NOT NULL COMMENT '状态',
  `tenant_id` varchar(20) NOT NULL COMMENT '广场id',
  `create_time` datetime NOT NULL COMMENT '时间',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=153 DEFAULT CHARSET=utf8mb4 COMMENT='微信菜单表';

-- Dumping structure for table wechat-standard.wx_msg
CREATE TABLE IF NOT EXISTS `wx_msg` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `fromuser` varchar(100) NOT NULL COMMENT '消息发送方，用户发送的消息，存储openid，回复的消息，存储微信号',
  `touser` varchar(100) NOT NULL COMMENT '消息接收方，用户发送的消息，存储微信号，回复的消息，存储openid',
  `msg_id` varchar(64) DEFAULT NULL COMMENT '消息id',
  `tenant_id` varchar(20) NOT NULL COMMENT '广场ID',
  `msg_type` varchar(20) NOT NULL COMMENT '消息类型 1 文字 2 图片 3 图文 4 视频',
  `msg_content` varchar(2000) NOT NULL COMMENT '消息内容',
  `replied` tinyint(4) NOT NULL COMMENT '是否已回复 0 未回复 1 已回复',
  `create_time` datetime NOT NULL COMMENT '发送时间',
  PRIMARY KEY (`id`),
  KEY `tenant_id` (`tenant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9954 DEFAULT CHARSET=utf8mb4 COMMENT='微信消息';

-- Dumping structure for table wechat-standard.wx_news
CREATE TABLE IF NOT EXISTS `wx_news` (
  `id` varchar(32) NOT NULL,
  `status` tinyint(4) NOT NULL COMMENT '状态 1 可用 2 已删除',
  `type` tinyint(4) NOT NULL COMMENT '类型 1 文字 2 图片 3 图文 4 视频',
  `tenant_id` varchar(20) NOT NULL COMMENT '广场',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  KEY `tenant_id_status` (`tenant_id`,`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='微信群发消息的主表，子表为news_messages';


-- Dumping structure for table wechat-standard.wx_news_messages
CREATE TABLE IF NOT EXISTS `wx_news_messages` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(1000) NOT NULL COMMENT '标题',
  `news_id` varchar(30) NOT NULL COMMENT '消息主表id',
  `cover_pic_id` int(11) DEFAULT NULL COMMENT '图片id',
  `cover_pic_url` varchar(200) DEFAULT NULL COMMENT '图片url',
  `type` varchar(20) DEFAULT NULL COMMENT '类型',
  `link_type` varchar(4) DEFAULT NULL COMMENT '链接类型 1 优惠券 2 活动',
  `url` varchar(200) DEFAULT NULL COMMENT '链接地址',
  `content` text COMMENT '图文内容',
  `html` text COMMENT '图文页面',
  `digest` varchar(2000) DEFAULT NULL COMMENT '摘要信息',
  `msg_seq` int(11) NOT NULL COMMENT '排序',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  KEY `news_id` (`news_id`)
) ENGINE=InnoDB AUTO_INCREMENT=110 DEFAULT CHARSET=utf8mb4 COMMENT='消息子表';

-- Dumping structure for table wechat-standard.wx_news_send
CREATE TABLE IF NOT EXISTS `wx_news_send` (
  `id` int(11) NOT NULL,
  `news_id` varchar(32) NOT NULL COMMENT '消息模板id',
  `tenant_id` varchar(20) NOT NULL COMMENT '广场ID',
  `from_user_name` varchar(64) DEFAULT NULL COMMENT '公众号群发助手的微信号，为mphelper',
  `msg_id` varchar(32) NOT NULL COMMENT '群发的消息ID（微信群发id）',
  `to_user_name` varchar(64) DEFAULT NULL COMMENT '公众号的微信号',
  `status` tinyint(4) NOT NULL COMMENT '消息状态 1 成功 2 失败 3 部分失败',
  `total_count` int(11) NOT NULL DEFAULT '0' COMMENT 'group_id下粉丝数；或者openid_list中的粉丝数',
  `filter_count` int(11) NOT NULL DEFAULT '0' COMMENT '过滤（过滤是指特定地区、性别的过滤、用户设置拒收的过滤，用户接收已超4条的过滤）后，准备发送的粉丝数，原则上，FilterCount = SentCount + ErrorCount',
  `send_count` int(11) NOT NULL DEFAULT '0' COMMENT '发送成功的粉丝数',
  `error_count` int(11) NOT NULL DEFAULT '0' COMMENT '发送失败的粉丝数',
  `send_time` datetime DEFAULT NULL COMMENT '微信发送消息的时间',
  `create_time` datetime DEFAULT NULL COMMENT '万江龙平台发送消息时间',
  PRIMARY KEY (`id`),
  KEY `tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='群发结果';


-- Dumping structure for table wechat-standard.wx_template_msg
CREATE TABLE IF NOT EXISTS `wx_template_msg` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tenant_id` varchar(20) NOT NULL COMMENT '广场id',
  `original_id` varchar(200) NOT NULL COMMENT '微信提供消息模板的原始id',
  `template_id` varchar(200) NOT NULL COMMENT '每个广场对应该模板的消息id',
  `description` varchar(1024) DEFAULT NULL COMMENT '对该模板消息的描述方便查看（可以不要）',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `tenant_id` (`tenant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COMMENT='模板消息';


-- Dumping structure for table wechat-standard.wx_token
CREATE TABLE IF NOT EXISTS `wx_token` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `token` varchar(256) NOT NULL,
  `tenant_id` varchar(200) NOT NULL,
  `create_time` datetime NOT NULL COMMENT '过期时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4;


-- Dumping structure for table wechat-standard.wx_user
CREATE TABLE IF NOT EXISTS `wx_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `member_id` varchar(32) NOT NULL COMMENT '北京会员id',
  `tenant_id` varchar(20) NOT NULL COMMENT '广场ID',
  `partner_uid` varchar(64) NOT NULL COMMENT '第三方用户id',
  `user_source` tinyint(4) NOT NULL COMMENT '用户来源 1 微信用户',
  `ext_info` varchar(1024) DEFAULT NULL COMMENT '扩展信息',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `partner_uid_user_source` (`tenant_id`,`partner_uid`,`user_source`),
  KEY `member_id` (`member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=183 DEFAULT CHARSET=utf8mb4 COMMENT='微信用户表';


-- Dumping structure for table wechat-standard.wx_user_checkin_config
CREATE TABLE IF NOT EXISTS `wx_user_checkin_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tenant_id` varchar(32) NOT NULL COMMENT '广场ID',
  `type` int(11) NOT NULL COMMENT '类型',
  `integral_value` int(11) NOT NULL COMMENT '积分值',
  `continued_day` int(11) NOT NULL COMMENT '连续签到天数',
  `desc` varchar(32) NOT NULL COMMENT '描述 用于积分流水',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COMMENT='用户签到配置表';

-- Dumping structure for table wechat-standard.wx_user_info
CREATE TABLE IF NOT EXISTS `wx_user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `member_id` varchar(32) NOT NULL COMMENT '会员ID',
  `mobile` varchar(12) NOT NULL COMMENT '手机号',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `mobile` (`mobile`),
  KEY `member_id` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户信息表';