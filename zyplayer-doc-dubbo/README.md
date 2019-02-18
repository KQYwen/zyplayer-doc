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

#### 参与贡献

1. Fork 本项目
2. 新建 Feat_xxx 分支
3. 提交代码
4. 新建 Pull Request

