# campus_sec_deal
校园二手交易平台 SpringBoot后端及数据库

# 简介
该项目诞生于软件课程设计。一个轻量且功能完备的校园二手交易平台后端及数据库。
# 食用
使用时请将upload文件夹拷贝到windows系统的D盘，若部署到服务器，则需要修改项目的application配置文件。

具体介绍见【[开发文档WiKi](https://github.com/Inetgeek/campus_sec_deal/wiki/%E5%BC%80%E5%8F%91WiKi)】

# 功能
| 序号 |         功能         |                             描述                             |
| :--: | :------------------: | :----------------------------------------------------------: |
|  1   |         注册         |                      用户可注册平台账户                      |
|  2   | 登录、注销、找回密码 | 未登录状态下不能操作功能、登陆后可手动注销登录状态、忘记密码可以找回密码 |
|  3   |     修改个人信息     |                      用户可修改个人信息                      |
|  4   |       发布商品       |                      用户可发布二手商品                      |
|  5   |       发布征品       |                      用户可发布二手征品                      |
|  6   |       编辑商品       |              用户可修改发布的商品信息或删除商品              |
|  7   |       编辑征品       |              用户可修改发布的征品信息或删除征品              |
|  8   |       交易私聊       |                  买卖双方可在交易前进行私聊                  |
|  9   |         下单         |                     用户下单自动生成订单                     |
|  10  |       支付订单       |                  订单待付款时用户可支付订单                  |
|  11  |       取消订单       |                       下单者可取消订单                       |
|  12  |       完成订单       |                     交易完成可以完成订单                     |
|  13  |     订单超时取消     |            待支付的订单或待交易的订单超时自动取消            |
|  14  |       订单提醒       |                   当有新订单时会提醒发布者                   |
|  15  |       物品搜索       |                  二手商城可以搜索商品或征品                  |
|  16  |       订单搜索       |                用户后台可以搜索与之相关的订单                |
|  17  |       账户钱包       |                      提现和充值账户余额                      |
