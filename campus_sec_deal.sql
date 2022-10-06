# 校园二手交易平台------campus_sec_deal--------------------------------------------
# 作者: Colyn         日期: 2022-09-12
# 数据库: MySQL       编码: utf8mb4      引擎: InnoDB      排序: utf8mb4_0900_ai_ci
# ------------------------------------------------------------------------------
# _________
# \_   ___ \______    _____ ______  __  __   ______
#  /    \  \/\__  \  /     \ \___ \ |  ||  \/  ___/
#  \     \____/ __ \|  Y Y  \  |_\ \|  |_|  /\___ \
#   \______  (____  /__|_|  /|   __/|_____//____/ /
#          \/     \/      \/ |__|               \/
# -----------------------------------------------------------------------------

# 创建数据库
DROP DATABASE IF EXISTS campus_sec_deal;
CREATE DATABASE campus_sec_deal DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
show databases;

# 创建数据表
USE campus_sec_deal;

# 创建campus_user表
DROP TABLE IF EXISTS `campus_user`;
CREATE TABLE `campus_user`
(
    `user_id`   CHAR(14)     NOT NULL DEFAULT (CURRENT_TIMESTAMP + 0) COMMENT '用户信息唯一标识符',
    `user_name` VARCHAR(30)  NOT NULL COMMENT '用户名',
    `user_time` DATE         NOT NULL DEFAULT (CURRENT_DATE) COMMENT '用户信息唯一标识符',
    `user_tel`  CHAR(11)     NOT NULL COMMENT '用户电话号码',
    `user_mail` VARCHAR(20)  NOT NULL COMMENT '用户邮箱号',
    `user_sign` VARCHAR(100) NULL     DEFAULT ('这个人很懒，什么都没写') COMMENT '用户个性签名',
    `img_url`   VARCHAR(100) NULL     DEFAULT ('/imgs/0/default.jpg') COMMENT '用户头像本地相对路径',
    `user_pwd`  VARCHAR(12)  NOT NULL COMMENT '用户登录密码',
    UNIQUE KEY (`user_tel`),
    UNIQUE KEY (`user_mail`),
    PRIMARY KEY (`user_id`)
) COMMENT = '用户信息表', DEFAULT CHARSET = utf8;

# ---------------------------------------------------------------------------------------
# 创建campus_publish表
DROP TABLE IF EXISTS `campus_publish`;
CREATE TABLE `campus_publish`
(
    `publish_id`       CHAR(14)     NOT NULL DEFAULT (CURRENT_TIMESTAMP + 0) COMMENT '发布信息唯一标识符',
    `publisher_id`     CHAR(14)     NOT NULL COMMENT '发布者信息唯一标识符',
    `publisher_tel`    CHAR(11)     NOT NULL COMMENT '发布者联系电话',
    `publish_time`     TIMESTAMP    NOT NULL DEFAULT (CURRENT_TIMESTAMP) COMMENT '信息发布时间戳',
    `publish_type`     INT(1)       NOT NULL COMMENT '发布信息类别',
    `publish_status`   BOOLEAN      NULL     DEFAULT (true) COMMENT '信息是否显示',
    `publish_name`     VARCHAR(20)  NOT NULL COMMENT '物品名称',
    `publish_describe` VARCHAR(500) NULL     DEFAULT ('暂无描述信息') COMMENT '物品描述',
    `publish_cat`      INT(1)       NOT NULL COMMENT '物品分类',
    `publish_Oprice`   FLOAT        NOT NULL COMMENT '物品原价',
    `publish_Nprice`   FLOAT        NOT NULL COMMENT '物品现价',
    `publish_degree`   INT(2)       NOT NULL DEFAULT (9) COMMENT '物品新旧程度',
    `img_url`          VARCHAR(100) NOT NULL COMMENT '物品图片本地相对路径',
    PRIMARY KEY (`publish_id`),
    FOREIGN KEY (`publisher_id`) REFERENCES `campus_user` (`user_id`)
) COMMENT = '发布信息表', DEFAULT CHARSET = utf8;

# ---------------------------------------------------------------------------------------
# 创建campus_order表
DROP TABLE IF EXISTS `campus_order`;
CREATE TABLE `campus_order`
(
    `order_id`     CHAR(32)     NOT NULL COMMENT '订单唯一标识符',
    `goods_id`     CHAR(14)     NOT NULL COMMENT '物品唯一标识符',
    `order_time`   TIMESTAMP    NOT NULL DEFAULT (CURRENT_TIMESTAMP) COMMENT '订单生成时间戳',
    `receiver_id`  CHAR(14)     NOT NULL COMMENT '物品唯一标识符',
    `receiver_tel` CHAR(11)     NOT NULL COMMENT '下单者联系电话',
    `deal_addr`    VARCHAR(200) NOT NULL COMMENT '交易地点',
    `order_status` INT(1)       NOT NULL DEFAULT (3) COMMENT '订单状态',
    PRIMARY KEY (`order_id`),
    FOREIGN KEY (`goods_id`) REFERENCES `campus_publish` (`publish_id`),
    FOREIGN KEY (`receiver_id`) REFERENCES `campus_user` (`user_id`)
) COMMENT = '订单信息表', DEFAULT CHARSET = utf8;

# ---------------------------------------------------------------------------------------
# 创建campus_wallet表
DROP TABLE IF EXISTS `campus_wallet`;
CREATE TABLE `campus_wallet`
(
    `wallet_id`      CHAR(14) NOT NULL COMMENT '用户钱包唯一标识符',
    `user_id`        CHAR(14) NOT NULL COMMENT '用户信息唯一标识符',
    `wallet_balance` FLOAT    NOT NULL DEFAULT (0.00) COMMENT '用户的钱包账户余额',
    `wallet_pwd`     INT(6)   NOT NULL DEFAULT (888888) COMMENT '用户钱包支付密码',
    PRIMARY KEY (`wallet_id`),
    FOREIGN KEY (`user_id`) REFERENCES `campus_user` (`user_id`)
) COMMENT = '账户钱包表', DEFAULT CHARSET = utf8;
-- 触发器 --
CREATE TRIGGER trig_insert_user
    AFTER INSERT
    ON `campus_user`
    FOR EACH ROW
    INSERT INTO `campus_wallet`
    values (NEW.user_id, NEW.user_id, default, default);

# ---------------------------------------------------------------------------------------
# 创建campus_chat表
DROP TABLE IF EXISTS `campus_chat`;
CREATE TABLE `campus_chat`
(
    `chat_id`      CHAR(14)     NOT NULL DEFAULT (CURRENT_TIMESTAMP + 0) COMMENT '聊天房间号',
    `msg_id`       INT          NOT NULL AUTO_INCREMENT COMMENT '消息ID',
    `from_id`      CHAR(14)     NOT NULL COMMENT '发消息者ID',
    `chat_content` VARCHAR(500) NULL COMMENT '消息内容',
    `send_time`    TIMESTAMP    NOT NULL DEFAULT (CURRENT_TIMESTAMP) COMMENT '消息发送时间戳',
    `to_id`        CHAR(14)     NOT NULL COMMENT '收消息者ID',
    UNIQUE KEY (`msg_id`),
    PRIMARY KEY (`chat_id`),
    FOREIGN KEY (`from_id`) REFERENCES `campus_user` (`user_id`),
    FOREIGN KEY (`to_id`) REFERENCES `campus_user` (`user_id`)
) COMMENT = '交易私聊表', DEFAULT CHARSET = utf8;

# ---------------------------------------------------------------------------------------
# 创建campus_notice表
DROP TABLE IF EXISTS `campus_notice`;
CREATE TABLE `campus_notice`
(
    `order_id`     CHAR(32)  NOT NULL COMMENT '订单唯一标识符',
    `opt_time`     TIMESTAMP NOT NULL DEFAULT (CURRENT_TIMESTAMP) COMMENT '操作时间戳',
    `publisher_id` CHAR(14)  NOT NULL COMMENT '发布者信息唯一标识符',
    `receiver_id`  CHAR(14)  NOT NULL COMMENT '物品唯一标识符',
    `order_status` INT(1)    NOT NULL DEFAULT (3) COMMENT '订单状态',
    PRIMARY KEY (`order_id`),
    FOREIGN KEY (`order_id`) REFERENCES `campus_order` (`order_id`),
    FOREIGN KEY (`publisher_id`) REFERENCES `campus_user` (`user_id`),
    FOREIGN KEY (`receiver_id`) REFERENCES `campus_user` (`user_id`)
) COMMENT = '订单通知表', DEFAULT CHARSET = utf8;