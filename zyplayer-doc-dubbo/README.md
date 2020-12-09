# zyplayer-doc-dubbo

#### 项目介绍
dubbo文档的管理项目，支持文档自动扫描，文档展示和修改，支持参数自动扫描获取，接口在线调试

#### 软件架构

后端使用spring boot，前端使用vue、element-ui、JQuery等

#### 安装教程
1. `zyplayer-doc-manage` -> `application.yml` -> `zyplayer.dubbo.zookeeper.url` 配置zookeeper地址
2. `zyplayer-doc-manage` -> `application.yml` -> `zyplayer.dubbo.nacos.url` 配置nacos服务地址
3. 默认找zookeeper，未配置再找nacos的配置，访问地址：http://127.0.0.1:8082/zyplayer-doc-manage/doc-dubbo.html
4. 支持文档查找、文档编辑、在线调试接口
5. 自动获取参数列表需要指定的类存在，所以请在 pom.xml -> dependencies 最后加上服务所在的包，后端才能通过Class.forName("xx");来找到参数列表，减少录入成本
6. 数组或List参数，调试参数录入格式例：[1,2] ，后端通过JSON工具转成指定类型，有不支持的类型时欢迎反馈

#### 文档JAR上传说明
为了达到获取服务的类的方法信息尝试过很多方式：
1. 比如在dubbo文档项目内，这样每次依赖的jar升级都需要重新发版
2. 比如自动下载远程仓库或读取本地仓库的jar来获取类，但这样如果jar包里依赖了另外的jar则不可行了，需要解析整个maven依赖树，复杂度高
3. 当前找到的最合适的方式就是一个模块依赖所有需要的jar，然后打包后传上去，这样所有的依赖都在此包里了，也不需要重新发版
> 如果你有更好的建议欢迎提出来探讨，非常乐意接受更加方便的建议！

所以您需要做的是：
1. 将所有dubbo接口依赖放到 zyplayer-doc/zyplayer-doc-other/zyplayer-doc-dubbo-libs/pom.xml 的\<dependencies>中
2. 打包此模块：mvn package
3. 将文件 ./zyplayer-doc-dubbo-libs/target/zyplayer-doc-dubbo-libs-x.x.x.jar 在您的dubbo文档页面上传
> 这样每次点击方法时将读取此JAR找到类和方法信息来展示

