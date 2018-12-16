# zyplayer-doc-swagger

#### 项目介绍
zyplayer-doc-swagger是swagger的一个前端实现，使用简单、解析速度快、走心的设计，支持多项目同时展示，多种文档目录的展示方案，多种自定义配置，满足各种使用习惯，使用中您有任何的意见和建议都可到源码地址处反馈哦！

每一行代码都是从头开始写的，所以每一个问题都能及时得到解决

欢迎多多Star！右上角↗

demo代码地址：
[zyplayer-doc-swagger-demo](https://gitee.com/zyplayer/zyplayer-doc-swagger-demo)

已上传至中央仓库，使用方法：

1、添加依赖
```
<!-- https://mvnrepository.com/artifact/com.zyplayer/zyplayer-doc-swagger -->
<dependency>
    <groupId>com.zyplayer</groupId>
    <artifactId>zyplayer-doc-swagger</artifactId>
    <version>1.0.6</version>
</dependency>
```
2、启动后访问：[http://localhost:8080/document.html](http://localhost:8080/document.html)

#### 服务器端的存储 功能：
实现MgStorageService并申明为@Service之后网页上才能使用服务器端的存储能力，同时需要在@EnableSwagger2的地方添加@EnableSwaggerMgUi注解，才能开启存储的接口

开放存储能力的好处：
所有网页的配置、调试值都可以存储到服务器的数据库中，便于团队所有人的调试，一人配置，所有人受益
如果不开启的话，数据是存放在浏览器的localStorage中，每个人、每个浏览器都得配置一次才能使用

开启@EnableSwaggerMgUi注解坏处：
暴露了一个请求任意地址的接口，比如任意内部接口，所以不建议在线上项目开启此注解，单独的本地的文档管理项目才开启，不会对外暴露此接口，切记！！！！

还有线上项目切记关闭swagger的接口扫描，不然别人就能拿到服务器的所有接口了，专门的对外文档还是可以，但还是尽量关闭，使用本项目的文档导出功能给第三方！

#### 模拟请求拦截 功能：
判断是否是模拟请求，功能需求：
很多时候后端定义好了接口，但还未实现，这时前端已经需要数据调试了，这时就需要用到这个过滤器了！
在页面上先配置好模拟返回的数据，然后在url上加入参数：zyplayerApiTest=1
例：http://192.168.0.249:8082/openApi/case/info?zyplayerApiTest=1
本过滤器就直接返回了之前配置的模拟数据，而不用等到后端必须把接口实现之后才能调试，或者在前端写一大段测试数据。也可以只复制本项目里的MgUiTestFilter.java代码到被访问的项目里

例：笔者的公司后端人较少，一个需求需要10个接口，需求分析完后首先就把接口、参数、返回值定义好，然后一个个的去实现。
也许需要10天才能写完，但前端两天就写好了，急需数据看效果，这时就让他们自己去设置模拟值，加上参数自己测试好。
而不是一味的催后台，把各种锅丢给后端，然后玩自己的去了，浪费各环节等待时间。

#### ↑↑以上个性化功能在demo中都有写到，欢迎体验↑↑：

如果需要看多个项目的文档，不再需要对让被访问的项目支持跨域访问！因为已集成服务器端代理请求，想请求什么都行！把在线调试当postman来用都行！（虽然功能差得远，但基础的用是没问题的，够用就行，不断完善中）

#### 功能细节：
1. 支持添加多个文档，同时展示
2. 左侧的侧边栏支持左右拖动改变大小
3. 优化各种细节，做到能不展示就不展示
4. 服务器端的存储能力
5. 模拟请求过滤能力
6. 请求参数缓存能力
7. 更多细节等你发现

#### 软件架构
maven项目，前端由html、js、css组成的，后端使用java，前端框架使用的[zui](http://zui.sexy)

#### 建议：
1. 单项目文档不要添加@EnableSwaggerMgUi注解
2. 多项目时建议不要引入文档UI包，只需要引入springfox-swagger2包，单独部署一个项目添加zyplayer-doc-swagger包，增加@EnableSwaggerMgUi注解，再实现MgStorageService类，部署后用于专门的文档管理项目，再在文档页面“添加文档”，管理所有项目的文档，真正实现文档和项目分离管理！

欢迎加入群聊讨论：

![QQ群](https://images.gitee.com/uploads/images/2018/0827/125719_a2f59824_596905.jpeg "QQ群.jpg")

#### 软件截图

支持添加和管理任意swagger文档地址：
![](https://images.gitee.com/uploads/images/2018/0830/225150_fb331488_596905.png "9.png")

多分文档时可选择只展示某一个文档：
![](https://images.gitee.com/uploads/images/2018/0830/224702_fe767513_596905.png "9.png")

多种展示方式：
![](https://images.gitee.com/uploads/images/2018/0830/224851_e7393e19_596905.png "1.png")

在线调试支持单行和多行编辑模式：
![](https://images.gitee.com/uploads/images/2018/0830/225343_cb99e28f_596905.png "6.png")

文档展示页面：
![](https://images.gitee.com/uploads/images/2018/0824/195423_4c1b7edc_596905.png "11.png")

在线调试页面：
![](https://images.gitee.com/uploads/images/2018/0824/195449_b89ba62b_596905.png "22.png")

模拟返回页面：
![](https://images.gitee.com/uploads/images/2018/0824/195517_f7d5ce32_596905.png "33.png")

导出文档：
![](https://images.gitee.com/uploads/images/2018/0921/132756_3102c452_596905.jpeg "21.jpg")

复制文档内容：

![](https://images.gitee.com/uploads/images/2018/0921/132810_360cd7d2_596905.jpeg "33.jpg")

独立文档查看：
![](https://images.gitee.com/uploads/images/2018/0921/132825_9cbf7ac4_596905.jpeg "42.jpg")

