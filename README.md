# zyplayer-doc

#### 项目介绍
定位为所有文档的管理项目，swagger文档、dubbo文档、数据库文档....等，提供一整套的解决方案，为开发者服务，欢迎有想法的一起来写，给个Star鼓励下呗！（您的一个Star是快速迭代的动力源泉！）
欢迎加群讨论，QQ群号：466363173

体验地址：[http://doc.zyplayer.com](http://doc.zyplayer.com/zyplayer-doc-manage/static/manage/home.html) 需登录 账号：zyplayer 密码：123456 （未运行dubbo服务，不能体验dubbo文档调试）

开放文档地址：[http://doc.zyplayer.com/zyplayer-doc-manage/open-wiki.html?pageId=1&space=23f3f59a60824d21af9f7c3bbc9bc3cb](http://doc.zyplayer.com/zyplayer-doc-manage/open-wiki.html?pageId=1&space=23f3f59a60824d21af9f7c3bbc9bc3cb) （无需登录）

#### 软件架构
##### 一、zyplayer-doc-core 一些核心、公用的类

##### 二、zyplayer-doc-db 数据库文档
> 原[zyplayer-doc-db](https://gitee.com/zyplayer/zyplayer-doc-db)，具有数据库文档的查看、管理、导出等功能

##### 三、zyplayer-doc-manage 可以单独部署的文档管理后台
> 后端使用spring-boot、mybatis-plus、springfox-swagger等框架，前端使用[zui](http://zui.sexy/)、[Vue](https://cn.vuejs.org/)、[element-ui](http://element-cn.eleme.io)等框架

> 已集成三套优秀swagger文档前端：[zyplayer-doc-swagger](https://gitee.com/zyplayer/zyplayer-doc)、[swagger-bootstrap-ui](https://gitee.com/xiaoym/swagger-bootstrap-ui)、[springfox-swagger-ui](https://github.com/springfox/springfox/tree/master/springfox-swagger-ui)

> 已集成[zyplayer-doc-db](https://gitee.com/zyplayer/zyplayer-doc)数据库文档

##### 四、zyplayer-doc-swagger 原[swagger-mg-ui](https://gitee.com/zyplayer/swagger-mg-ui)，swagger的UI及整套解决方案
> 具有 后端存储、代理请求、模拟返回、所有文档管理 等一系列原创功能，不止UI

##### 五、zyplayer-doc-dubbo 将dubbo的文档在线化管理
> 支持zookeeper、nacos的注册中心文档获取，支持在线调试接口

##### 六、zyplayer-doc-wiki wiki文档工具
> 支持文档创建、展示，文件上传、下载，空间隔离，开放文档访问等

##### 七、zyplayer-doc-grpc grpc文档工具
> 用比较变态的方式实现了grpc的文档和在线调试功能，通过http的方式来请求grpc的接口

> 默认未开启此功能，如需使用需要在zyplayer-doc-manage项目中开启@EnableDocGrpc注解

#### 运行方式

1. 创建数据库：zyplayer_doc_manage，执行脚本：[zyplayer_doc_manage.1.0.2.sql](https://gitee.com/zyplayer/zyplayer-doc/blob/master/zyplayer-doc-manage/src/main/resources/sql/zyplayer_doc_manage.1.0.2.sql)

2. 修改zyplayer-doc-manage项目的application.yml配置文件里面的数据库账号密码

3. 启动zyplayer-doc-manage项目，访问地址：
http://127.0.0.1:8082/zyplayer-doc-manage/static/manage/home.html
未登录会进入登录页面，登陆后自动跳回，默认账号：zyplayer 密码:123456
> 注意！！项目页面全是静态的html，如果使用idea启动，有可能访问不了静态页面，需要在这里配置下工作目录，然后重新启动即可
![](https://images.gitee.com/uploads/images/2019/0127/222951_4ce343fe_596905.png "配置工作目录")

#### 功能介绍

##### 一、zyplayer-doc-swagger
1. 在 `zyplayer-doc-swagger`->`文档管理`->`文档地址管理` 页可以管理`任意地址`的文档，在集成的三套UI中都可以直接查看和调试这里配置的文档，不用对UI做任何调整，只要标准的UI集成进来也可以直接用

2. 文档展示配置，`自动填充请求参数`：可配置填充级别，`强制重写域名`：文档在本地想调试线上接口的好帮手，`自动保存请求参数`：不用每次输入

3. 可配置`全局参数`，放在header或param里的

4. 可对`调试数据`进行管理

5. `模拟返回`：前端自行调试接口的好帮手

6. 私人强迫症级定制化文档展示，清晰明了，参数支持批量编辑，每一条线的间距都考量了半天，支持`文件上传`的调试，需要指定dataType = `File`或`MultipartFile`，例：

@ApiImplicitParam(name = "file", value = "文件", dataType = "File", allowMultiple = true)

7. 后端代理请求，后端存储，不会存在任何跨域问题

##### 二、zyplayer-doc-db
1. 支持Mysql、SQLserver的数据库表、字段文档查看修改，导出，展示关系图等功能

2. 支持多数据源，在`zyplayer-doc-manage` -> `application.yml` -> `zyplayer.doc.db.dbConfigList` 处配置多个数据库连接即可

##### 三、zyplayer-doc-dubbo
1. `zyplayer-doc-manage` -> `application.yml` -> `zyplayer.dubbo.zookeeper.url` 配置zookeeper地址

2. `zyplayer-doc-manage` -> `application.yml` -> `zyplayer.dubbo.nacos.url` 配置nacos服务地址

3. 默认找zookeeper，未配置再找nacos的配置，访问地址：http://127.0.0.1:8082/zyplayer-doc-manage/doc-dubbo.html

4. 支持文档查找、文档编辑、在线调试接口

5. 自动获取参数列表需要指定的类存在，所以请在 pom.xml -> dependencies 最后加上服务所在的包，后端才能通过Class.forName("xx");来找到参数列表，减少录入成本

6. 数组或List参数，调试参数录入格式例：[1,2] ，后端通过JSON工具转成指定类型，有不支持的类型时欢迎反馈

##### 四、zyplayer-doc-manage
1. 整合了上面两个功能到此项目，较少熟悉成本，`git clone`下来即可运行

2. 具有简单的`权限管理`、`人员管理`功能（还没想好具体怎么控制，没需求）

3. 使用最新的一些技术框架，很简单，初学者拿来学习也是很不错的

如果觉得此项目对您有帮助，就捐赠一个吧！无关金额，主要是对作者的认可。

#### 界面展示
权限和用户管理：
![](https://images.gitee.com/uploads/images/2018/1216/224050_3f93dd4a_596905.png "屏幕截图.png")
数据库文档：
![](https://images.gitee.com/uploads/images/2018/1219/231332_0a95e458_596905.png "屏幕截图.png")
dubbo文档：
![](https://images.gitee.com/uploads/images/2019/0214/224530_1cac5a99_596905.png "屏幕截图.png")
dubbo接口调试：
![](https://images.gitee.com/uploads/images/2019/0214/224600_8dfeb0c2_596905.png "屏幕截图.png")
增加文档处：
![](https://images.gitee.com/uploads/images/2019/0108/225208_6f4a9a64_596905.png "屏幕截图.png")
使用springfox-swagger-ui访问增加的文档：
![](https://images.gitee.com/uploads/images/2019/0108/225313_76ab6962_596905.png "屏幕截图.png")
文档展示：
![](https://images.gitee.com/uploads/images/2019/0108/224850_6940f92e_596905.png "屏幕截图.png")
在线调试：
![](https://images.gitee.com/uploads/images/2019/0108/224934_cb4cedea_596905.png "屏幕截图.png")
模拟返回：
![](https://images.gitee.com/uploads/images/2019/0108/224958_1f20b45d_596905.png "屏幕截图.png")
文档配置：
![](https://images.gitee.com/uploads/images/2019/0108/225024_1c8b2526_596905.png "屏幕截图.png")
全局参数管理：
![](https://images.gitee.com/uploads/images/2019/0108/225056_d2e861df_596905.png "屏幕截图.png")




