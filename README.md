# zyplayer-doc

## 项目介绍
zyplayer-doc是一款在线文档工具，现有swagger 文档、dubbo文档、数据库文档、WIKI文档等，管理端具有人员管理、权限管理功能等功能。项目后端使用spring-boot、mybatis-plus等框架，前端使用zui、Vue、element-ui等框架。为开发者服务，欢迎有想法的一起来写，给个Star鼓励下呗！（您的一个Star是快速迭代的动力源泉！）
欢迎加群讨论，QQ群号：466363173

体验地址：[http://doc.zyplayer.com](http://doc.zyplayer.com/zyplayer-doc-manage/) 账号：zyplayer 密码：123456

各模块的详细使用文档地址，部署必看：
[详细部署文档](http://doc.zyplayer.com/zyplayer-doc-manage/doc-wiki#/page/share/view?pageId=1&space=23f3f59a60824d21af9f7c3bbc9bc3cb) 

## 功能介绍
### 一、zyplayer-doc-manage 文档管理后台
1. 具有项目模块导航，人员及权限管理功能，分组管理等功能。
2. 集成了本项目内的各个子模块功能，是各模块的协调管理模块。

### 二、zyplayer-doc-db 数据库文档
一款在线管理数据库的工具，你可以将所有的数据源统一管理到这里面，团队间的各成员就不必每人在自己电脑上装一个数据库管理软件，
然后再添加数据源，修改账号密码也可以统一进行修改，新员工进入后对他关心的数据源进行授权即可查看。

1. 支持MySQL、Doris（MySQL协议）、SQLServer、Oracle、PostgreSQL、Hive、Impala数据源。
2. 支持数据库表、字段文档查看修改，表文档导出、建表语句DDL导出、表数据导出。
3. 支持SQL执行、表数据预览、不同数据库之间的数据互导，支持多数据源管理。
4. 支持按人员、按数据源对用户授权，可给用户 库表注释查看、注释修改、SQL执行、函数修改等粒度的授权。
5. 支持库函数和存储过程的增删改查，修改记录查询等。
6. 目标是取代Navicat，做一个小而精的开源免费的在线数据库管理工具。

### 三、zyplayer-doc-wiki wiki文档工具
1. 在线管理公司、项目及任意形式的文档
2. 文档支持按空间划分，按人员分组授权，支持空间收藏和空间内的文档开放访问。
3. 文档支持编辑、查看、评论、上传附件、历史版本查看、页面权限控制、文档搜索等功能。
4. 文档编辑支持html富文本方式编辑和markdown的方式编辑。
5. 本工具使用的开源工具有：vue、element-ui、mavon-editor、wangeditor等。
6. 参考学习了Atlassian Confluence文档工具进行开发，争取作为该软件的开源免费替代产品，同时作为内部文档管理工具最好的存在。

### 四、zyplayer-doc-swagger swagger的UI及整套解决方案
1. 支持swagger的文档展示，接口调试，文档导出等，解析速度快，界面设计走心。
2. 支持将所有的swagger文档进行统一管理，支持全局参数设置，请求参数缓存，下次自动填充等。
3. 目标是解决swagger官方文档查看及接口调试不方便的问题。

### 五、zyplayer-doc-dubbo 将dubbo的文档在线化管理
1. 支持zookeeper、nacos的注册中心文档获取，支持在线调试接口

### 六、其他
1. zyplayer-doc-ui 前面各模块的前端UI源码
2. zyplayer-doc-core 一些核心、公用的类
3. zyplayer-doc-data 数据库层面的交互
4. zyplayer-doc-grpc grpc文档工具
5. zyplayer-doc-other 一些测试

## 运行方式

1. 创建数据库：zyplayer_doc_manage，执行脚本：[全量建表语句.sql](https://gitee.com/zyplayer/zyplayer-doc/blob/master/zyplayer-doc-manage/src/main/resources/sql/全量建表语句.sql)

2. 修改zyplayer-doc-manage项目的application.yml配置文件里面的数据库账号密码

3. 启动zyplayer-doc-manage项目，访问地址：http://127.0.0.1:8083/zyplayer-doc-manage/

更多详细的使用部署文档：http://doc.zyplayer.com/zyplayer-doc-manage/doc-wiki#/page/share/view?pageId=1&space=23f3f59a60824d21af9f7c3bbc9bc3cb

#### 界面展示
![主页面](https://images.gitee.com/uploads/images/2020/0516/125840_d6284954_596905.png "主页面.png")
![数据库文档](https://images.gitee.com/uploads/images/2020/0516/130017_254f9559_596905.png "数据库文档.png")
![wiki文档](https://images.gitee.com/uploads/images/2020/0516/130119_bc2f5021_596905.png "wiki文档.png")
