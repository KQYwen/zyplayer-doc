# zyplayer-doc

#### 项目介绍
zyplayer-doc是一款在线文档工具，现有swagger 文档、dubbo文档、数据库文档、WIKI文档、ElasticSearch文档等，管理端具有人员管理、权限管理功能等功能。项目后端使用spring-boot、mybatis-plus等框架，前端使用zui、Vue、element-ui等框架。为开发者服务，欢迎有想法的一起来写，给个Star鼓励下呗！（您的一个Star是快速迭代的动力源泉！）
欢迎加群讨论，QQ群号：466363173

体验地址：[http://doc.zyplayer.com](http://doc.zyplayer.com/zyplayer-doc-manage/) 需登录 账号：zyplayer 密码：123456 （未运行dubbo服务，不能体验dubbo文档调试）

各模块的详细使用文档地址，部署必看：
[http://doc.zyplayer.com/zyplayer-doc-manage/doc-wiki#/page/share/view?pageId=1&space=23f3f59a60824d21af9f7c3bbc9bc3cb](http://doc.zyplayer.com/zyplayer-doc-manage/doc-wiki#/page/share/view?pageId=1&space=23f3f59a60824d21af9f7c3bbc9bc3cb) 


#### 模块介绍
##### 一、zyplayer-doc-core 
> 一些核心、公用的类

##### 二、zyplayer-doc-manage 可以单独部署的文档管理后台
> 集成了本项目内的各子模块，具有人员及权限管理功能，是各模块的协调模块

##### 三、zyplayer-doc-db 数据库文档
> 支持MySQL、SQLServer、Oracle数据源，支持数据库表、字段文档查看修改，表文档导出，SQL执行、表数据预览、不同数据库之间的数据互导，支持多数据源管理。支持按人员、按数据源对用户授权，可给用户 库表注释查看、注释修改、SQL执行 粒度的授权。目标是取代Navicat，做一个开源免费的在线数据库管理工具。

##### 四、zyplayer-doc-wiki wiki文档工具
> 在线管理公司、项目及任意形式的文档，文档支持按空间划分，在线编辑查看，支持附件上传下载，支持评论、点赞及权限设置等。参考学习了Atlassian Confluence文档工具进行开发，争取作为该软件的开源免费替代产品，同时作为内部文档管理工具最好的存在。

##### 五、zyplayer-doc-swagger，swagger的UI及整套解决方案
> 支持swagger的文档展示，接口调试，文档导出等，解析速度快，界面设计走心。支持将所有的swagger文档进行统一管理，支持全局参数设置，请求参数缓存，下次自动填充等。目标是解决swagger官方文档查看及接口调试不方便的问题。

##### 六、zyplayer-doc-dubbo 将dubbo的文档在线化管理
> 支持zookeeper、nacos的注册中心文档获取，支持在线调试接口

##### 七、zyplayer-doc-grpc grpc文档工具
> 用比较变态的方式实现了grpc的文档和在线调试功能，通过http的方式来请求grpc的接口

> 默认未开启此功能，如需使用需要在zyplayer-doc-manage项目中开启@EnableDocGrpc注解

##### 八、zyplayer-doc-es ElasticSearch文档工具
> 支持ElasticSearch的文档查看和执行DSL查询的功能

##### 九、zyplayer-doc-ui
> 前面各模块的前端UI源码

#### 运行方式

1. 创建数据库：zyplayer_doc_manage，执行脚本：[zyplayer_doc_manage.1.0.6.sql](https://gitee.com/zyplayer/zyplayer-doc/blob/master/zyplayer-doc-manage/src/main/resources/sql/zyplayer_doc_manage.1.0.6.sql)

2. 修改zyplayer-doc-manage项目的application.yml配置文件里面的数据库账号密码

3. 启动zyplayer-doc-manage项目，访问地址：http://127.0.0.1:8083/zyplayer-doc-manage/

更多详细的使用部署文档：http://doc.zyplayer.com/zyplayer-doc-manage/doc-wiki#/page/share/view?pageId=1&space=23f3f59a60824d21af9f7c3bbc9bc3cb

#### 界面展示
![主页面](https://images.gitee.com/uploads/images/2020/0516/125840_d6284954_596905.png "主页面.png")
![数据库文档](https://images.gitee.com/uploads/images/2020/0516/130017_254f9559_596905.png "数据库文档.png")
![wiki文档](https://images.gitee.com/uploads/images/2020/0516/130119_bc2f5021_596905.png "wiki文档.png")
