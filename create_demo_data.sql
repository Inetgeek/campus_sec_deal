# 校园二手交易平台------campus_sec_deal测试脚本--------------------------------------
# 作者: Colyn         日期: 2022-09-12
# 数据库: MySQL       编码: utf8mb4      引擎: InnoDB      排序: utf8mb4_0900_ai_ci
# ------------------------------------------------------------------------------
# _________
# \_   ___ \______    _____ ______  __  __   ______
#  /    \  \/\__  \  /     \ \___ \ |  ||  \/  ___/
#  \     \____/ __ \|  Y Y  \  |_\ \|  |_|  /\___ \
#   \______  (____  /__|_|  /|   __/|_____//____/ /
#          \/     \/      \/ |__|               \/
# ------------------------------------------------------------------------------

-- DATABASE DEMO TEST --
USE campus_sec_deal;

# #添加campus_user表数据------------------------------------------------------------
INSERT INTO `campus_user`
VALUES  ('20220921112424', '汪汪队睡大觉', default, '18282828282', 'sdj@foxmail.com', default, default, 'Admin123'),
        ('20220922034526', '汪汪队离大谱', default, '18080808080', 'ldp@foxmail.com', default, default, 'Admin123'),
        ('20220922154423', '汪汪队立大功', default, '18181818181', 'ldg@foxmail.com', default, default, 'Admin123'),
        ('20220923080814', '汪汪队吹大牛', default, '17979797979', 'cdn@foxmail.com', default, default, 'Admin123'),
        ('20220924120845', '汪汪队熬大夜', default, '18383838383', 'ady@foxmail.com', default, default, 'Admin123');
SELECT * FROM `campus_user`;

# #添加campus_publish表数据------------------------------------------------------------
INSERT INTO `campus_publish`
VALUES  ('20220924114523', '20220921112424', '18282828282', default, 0, default, '二手华为P50', default, 0, 6999.10, 3999.20, default, '/imgs/0/37c92cf4-6a25-46d0-633c-46e7e2fa1bcb.png'),
        ('20220921112424', '20220922034526', '18080808080', default, 1, default, '二手联想拯救者', default, 0, 11999.00, 5999.00, default, '/imgs/0/37c92cf4-6a25-46d0-633c-46e7e2fa1bcb.png');
SELECT * FROM `campus_publish`;

# #添加campus_order表数据------------------------------------------------------------
INSERT INTO `campus_order`
VALUES  ('f1c92cf46a2546d0633c46e7e2fa1bf7', '20220924114523', default, '20220923080814', '17979797979', '美利坚合众国华盛顿特区宾夕法尼亚大道1600号', default),
        ('14fa4a97f9d547b780cbd375d400a6ee', '20220921112424', default, '20220924120845', '18383838383', '美利坚合众国华盛顿特区宾夕法尼亚大道1600号', default);
SELECT * FROM `campus_order`;
#
#添加campus_chat表数据------------------------------------------------------------
INSERT INTO `campus_chat`
VALUES (default, default, '20220921112424', '抓你抓你咯！', default, '20220922154423');
SELECT * FROM `campus_chat`;