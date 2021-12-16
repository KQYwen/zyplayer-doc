# zyplayer-doc

# 项目介绍
zyplayer-doc是一款前后端完全开源的在线文档工具，现有API接口文档（Swagger、OpenApi、自建接口）、WIKI文档、数据库文档（数据库表结构查看管理、SQL执行）、Dubbo文档。

体验地址：[http://doc.zyplayer.com](http://doc.zyplayer.com/zyplayer-doc-manage/) 账号：zyplayer 密码：123456

各模块的详细使用文档地址，可参考：
[详细部署文档](http://doc.zyplayer.com/zyplayer-doc-manage/doc-wiki#/page/share/view?pageId=1&space=23f3f59a60824d21af9f7c3bbc9bc3cb) 

欢迎有想法的一起来完善，如果觉得不错就给个Star鼓励下呗！作为给作者快速更新的动力！

欢迎加群讨论，QQ群号：466363173

# 快速启动
## 数据库依赖
你得有一个MySQL数据库，建议版本号为：5.7.25，建库：zyplayer_doc_manage，再执行脚本建表：[全量建表语句.sql](https://gitee.com/zyplayer/zyplayer-doc/blob/master/zyplayer-doc-manage/src/main/resources/sql/全量建表语句.sql)

## main方法启动
1. 修改 zyplayer-doc/zyplayer-doc-manage/src/main/resources/application.yml 配置文件里面的数据库账号密码
2. 执行 com.zyplayer.doc.manage.Application.main 方法启动项目，启动后访问：http://127.0.0.1:8083/zyplayer-doc-manage/

## jar方式启动
1. 直接下载：直接下载编译好的jar打包文件，下载地址：https://pan.baidu.com/s/1yMmnle01XR4TDjo2hfvw-Q 提取码: 3adf，下载最新版后解压
2. 自行编译：也可以自己动手编译，双击执行：zyplayer-doc\build.bat，将使用maven编译整个项目为可执行的jar文件，编译结果文件放在：zyplayer-doc\dist\version 文件夹下，如果电脑没有安装maven则需要先安装
3. 修改第一步或第二步结果文件夹下的 application.yml 文件里面数据库帐号密码
4. 双击第一步或第二步结果文件夹下的 startup.bat 启动项目，启动后访问：http://127.0.0.1:8083/zyplayer-doc-manage/

## Tomcat容器启动
1. 直接下载编译好的war打包文件，下载地址：https://pan.baidu.com/s/1yMmnle01XR4TDjo2hfvw-Q 提取码: 3adf，下载最新版后解压
2. 修改配置文件：zyplayer-doc.zip\apache-tomcat\webapps\zyplayer-doc-manage\WEB-INF\classes\application.yml 配置文件里面的数据库账号密码
3. 双击tomcat\bin\startup.bat启动即可

默认登录账号：zyplayer 密码：123456

# 各模块介绍
## zyplayer-doc-manage 文档管理后台
1. 具有项目模块导航，人员及权限管理功能，分组管理等功能。
2. 集成了本项目内的各个子模块功能，是各模块的协调管理模块。

## zyplayer-doc-db 数据库文档
一款在线管理数据库的工具，你可以将所有的数据源统一管理到这里面，团队间的各成员就不必每人在自己电脑上装一个数据库管理软件，
然后再添加数据源，可以统一修改数据库账号密码而不用群广播通知，新员工进入后对他使用到的数据源进行授权即可使用。

1. 支持MySQL、Doris（MySQL协议）、SQLServer、Oracle、PostgreSQL、Hive、Impala数据源。
2. 支持数据库表、字段文档查看修改，表文档导出、建表语句DDL导出、表数据导出。
3. 支持SQL执行、表数据预览、不同数据库之间的数据互导，支持多数据源管理。
4. 支持按人员、按数据源对用户授权，可给用户 库表注释查看、注释修改、SQL执行、函数修改等粒度的授权。
5. 支持库函数和存储过程的增删改查，修改记录查询等。
6. 目标是取代Navicat，做一个小而精的开源免费的在线数据库管理工具。

## zyplayer-doc-wiki wiki文档工具
1. 在线管理公司、项目及任意形式的文档
2. 文档支持按空间划分，按人员分组授权，支持空间收藏和空间内的文档开放访问。
3. 文档支持编辑、查看、评论、上传附件、历史版本查看、页面权限控制、文档搜索等功能。
4. 文档编辑支持html富文本方式编辑和markdown的方式编辑。
5. 本工具使用的开源工具有：vue、element-ui、mavon-editor、wangeditor等。
6. 参考学习了Atlassian Confluence文档工具进行开发，争取作为该软件的开源免费替代产品，同时作为内部文档管理工具最好的存在。

## zyplayer-doc-api API接口文档管理工具
一款支持统一管理Swagger文档、OpenApi文档、自建接口文档的管理工具，具有文档查看、接口请求、全局参数管理等功能，设计走心，前端代码使用最新技术构建，每一行代码都是全新手动敲出来的，超级简单明了，代码简洁美观可读性好、易维护。

1. 支持Swagger的文档展示，接口调试，解析速度快，界面设计走心。
2. 支持将所有的Swagger文档、OpenApi文档、自建接口文档进行统一管理，支持全局参数设置，请求参数缓存，下次自动填充等。
3. 目标是实现一个平台解决所有项目的接口文档统一管理。

## zyplayer-doc-dubbo 将dubbo的文档在线化管理
1. 支持zookeeper、nacos的注册中心文档获取，支持在线调试接口

## 其他
1. zyplayer-doc-ui 前面各模块的前端UI源码
2. zyplayer-doc-core 一些核心、公用的类
3. zyplayer-doc-data 数据库层面的交互
4. zyplayer-doc-grpc grpc文档工具
5. zyplayer-doc-other 一些测试

# 用爱发电
如果您正在使用这个项目并感觉良好，或者是想支持我继续开发，您可以通过如下任意方式支持我：
1. Star并分享 [zyplayer-doc](https://gitee.com/zyplayer/zyplayer-doc)
2. 保留关于页面的项目链接
3. 通过以下二维码一次性捐赠，我会去买一杯咖啡或攒着去植发

![微信捐赠](%E5%BE%AE%E4%BF%A1%E6%8D%90%E8%B5%A0.png)

# 界面展示
控制台页面
![主页面](https://images.gitee.com/uploads/images/2020/0516/125840_d6284954_596905.png "主页面.png")

数据库文档页面
![数据库文档](https://images.gitee.com/uploads/images/2020/0516/130017_254f9559_596905.png "数据库文档.png")

WIKI文档页面
![wiki文档](https://images.gitee.com/uploads/images/2020/0516/130119_bc2f5021_596905.png "wiki文档.png")

API文档主页面
![主页面](https://images.gitee.com/uploads/images/2021/1120/181101_87903c1f_596905.png "主页面.png")

API文档查看页面
![文档查看页面](https://images.gitee.com/uploads/images/2021/1120/181135_0b6034e4_596905.png "文档查看页面.png")

API文档在线调试页面
![在线调试页面](https://images.gitee.com/uploads/images/2021/1120/181205_462cb4aa_596905.png "在线调试页面.png")
