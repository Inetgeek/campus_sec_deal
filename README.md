# campus_sec_deal
校园二手交易平台 SpringBoot后端及数据库
<div align="center">
<img src="https://user-images.githubusercontent.com/42563262/194395085-1bcad0f8-b826-4130-9bf6-27093716f4e1.png" width="35%" height="35%"/>
</div>

# 简介
该项目诞生于软件课程设计。一个轻量且功能完备的校园二手交易平台后端及数据库。
# 食用
## 必要文件
```
campus_sec_deal.7z			            后端项目源码
upload					                    后端静态文件
campus_sec_deal-1.3.0.RELEASE.jar		后端项目发布包
../数据库脚本/campus_sec_deal.sql		数据库生成脚本
../数据库脚本/create_demo_data.sql		数据库测试数据生成脚本
```

## 配置环境与运行
**项目可部署到Linux或Windows上，已发布的jar包仅适配了Windows系统，要适配Linux系统则需要更改项目的application配置文件里的文件参数（有注释）。**

Windows的使用方式如下：

1.将campus_sec_deal.sql导入数据库（例如执行：`ource campus_sec_deal.sql`

2.将create_demo_data.sql导入数据库（例如执行：`ource create_demo_data.sql`)

3.将upload拷贝到Windows系统的D盘根目录

4.在campus_sec_deal-1.3.0.RELEASE.jar所在目录下打开终端（Terminal），执行如下指令：
```bash
	java -jar campus_sec_deal-1.3.0.RELEASE.jar
```

5.使用Apifox等工具根据开发文档wiki说明编写测试数据请求即可。

使用时请将upload文件夹拷贝到windows系统的D盘，若部署到服务器，则需要修改项目的application配置文件。

# 开发
超详细说明见【[开发文档WiKi](https://github.com/Inetgeek/campus_sec_deal/wiki/%E5%BC%80%E5%8F%91WiKi)】

# 部署
1.项目打包发布部署教程：【[CSDN-InetGeek: 使用IDEA打包发布SpringBoot并部署到云服务器](https://blog.csdn.net/qq_34532102/article/details/127018858)】

2.服务器解决MySQL报错教程：【[CSDN-InetGeek: MySQL导入含有触发器的sql脚本报错解决方案](https://blog.csdn.net/qq_34532102/article/details/127018488)】

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

![image](https://user-images.githubusercontent.com/42563262/194394912-b099714a-1f13-48ff-bbd6-fc74174204a6.png)


Copyright © 2022 InetGeek(Colyn), All Rights Reserved. 
