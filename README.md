# zyplayer-doc

#### 说明
1. 本项目暂时只有swagger文档（服务端的接口文档）、数据库文档（数据库表、字段文档的查看修改）功能
2. 如果需要类似文件夹、目录类的在线文档、文件管理，访问权限控制，暂时还没有，可以提交Issues或评论说明具体需求，整理需求后开发，已有这方面打算（最近接到比较多的这种需求）

#### 项目介绍
定位为所有文档的管理项目，swagger文档、数据库文档....等，提供一整套的解决方案，欢迎有想法的一起来写

#### 软件架构
##### 一、zyplayer-doc-core 一些核心、公用的类

##### 二、zyplayer-doc-db 数据库文档
> 原[zyplayer-doc-db](https://gitee.com/zyplayer/zyplayer-doc-db)，具有数据库文档的查看、管理、导出等功能

##### 三、zyplayer-doc-manage 可以单独部署的文档管理后台
> 后端使用spring-boot、mybatis-plus、springfox-swagger等框架，前端使用[zui](http://zui.sexy/)、[Vue](https://cn.vuejs.org/)等框架

> 已集成三套优秀swagger文档前端：[zyplayer-doc-swagger](https://gitee.com/zyplayer/zyplayer-doc)、[swagger-bootstrap-ui](https://gitee.com/xiaoym/swagger-bootstrap-ui)、[springfox-swagger-ui](https://github.com/springfox/springfox/tree/master/springfox-swagger-ui)

> 已集成[zyplayer-doc-db](https://gitee.com/zyplayer/zyplayer-doc)数据库文档

##### 四、zyplayer-doc-swagger 原[swagger-mg-ui](https://gitee.com/zyplayer/swagger-mg-ui)，swagger的UI及整套解决方案
> 具有 后端存储、代理请求、模拟返回、所有文档管理 等一系列原创功能，不止UI

#### 运行方式

1. 创建数据库：zyplayer_doc_manage，执行脚本：[zyplayer_doc_manage.sql](https://gitee.com/zyplayer/zyplayer-doc/blob/master/zyplayer-doc-manage/src/main/resources/sql/zyplayer_doc_manage.sql)

2. 修改zyplayer-doc-manage项目的application.yml配置文件里面的数据库账号密码

3. 启动zyplayer-doc-manage项目，访问地址：
http://127.0.0.1:8082/zyplayer-doc-manage/static/manage/home.html
未登录会进入登录页面，登陆后自动跳回，默认账号：zyplayer 密码:123456
> 项目页面全是静态的html，如果使用idea启动，有可能访问不了静态页面，需要在这里配置下工作目录，然后重新启动即可
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

##### 三、zyplayer-doc-manage
1. 整合了上面两个功能到此项目，较少熟悉成本，`git clone`下来即可运行

2. 具有简单的`权限管理`、`人员管理`功能（还没想好具体怎么控制，没需求）

3. 使用最新的一些技术框架，很简单，初学者拿来学习也是很不错的

#### 界面展示
权限和用户管理：
![](https://images.gitee.com/uploads/images/2018/1216/224050_3f93dd4a_596905.png "屏幕截图.png")
数据库文档：
![](https://images.gitee.com/uploads/images/2018/1219/231332_0a95e458_596905.png "屏幕截图.png")
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




