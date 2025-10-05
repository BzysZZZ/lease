# 项目说明

## 项目概述
本项目为使用SSM框架实现的相关项目，技术栈:Spring、SpringMVC、Mybatis、MySQL、RocketMQ等。以下是项目相关服务的访问地址及配置信息。

## 接口文档
Knife4j 接口文档访问地址：[http://localhost:8080/doc.html](http://localhost:8080/doc.html)

## 虚拟机环境
- 系统：CentOS 7
- IP地址：`192.168.199.100`


## 中间件配置
### RocketMQ
- RocketMQ 地址：`192.168.199.100:9876`
- 启动RocketMQ：$ROCKETMQ_HOME/bin下启动命令
> nohup sh mqnamesrv > nohup.namesrv.out 2>&1 &
- 启动RocketMQ Broker：$ROCKETMQ_HOME/bin下启动命令
> nohup sh mqbroker -n 192.168.199.100:9876 > nohup.broker.out 2>&1 &

### 数据库
- 数据库地址：`192.168.199.100:3306`
- 数据库用户名：`root`
- 数据库密码：`Password.123`

## redis 配置
- redis 地址：`192.168.199.100:6379`
- redis 密码：无

### minio 配置
- minio 地址：`192.168.199.100:9000`
- minio 用户名：`minioadmin`
- minio 密码：`minioadmin`


## 注意事项
以上配置信息中的部分内容需根据实际生产或测试环境进行调整，实际使用时请确保各服务正常运行。
