# zyplayer-doc-db

#### 项目介绍
数据库文档工具，网页方式管理，只需两步即可对表注释、字段注释进行查看、修改、导出等操作，支持字段或注释的模糊查询，只有一个单独的页面，方便集成到已有的管理系统里面，本工具不对数据源进行管理，因为后台管理系统肯定是已有的数据源，没必要再来创建，只需要注入数据源即可管理

按照指定的格式可展示表的关系图，展示样式见下图
本关系图不是通过外键生成，所以需要在字段注释最后按规则添加外键关系才能生成图表，支持的格式有：
1. T:表，例：XXX(字段注释)，T:user_info
2. T:表.关联ID，例：XXX(字段注释)，T:user_info.id
3. T:库.表.关联ID，例：XXX(字段注释)，T:order_db.user_info.id

关系图为实验功能，有更好的建议或展示方式欢迎提交建议！

当前支持SqlServer、mysql的管理，后期加上oracle和其他数据库的支持

#### 使用方式
1. 添加注解：@EnableDocDb
2. 注入Bean

```
// 注入已有的数据源
@Resource DataSource orderDatasource;
@Resource DataSource userDatasource;

//....

@Bean
public DatabaseRegistrationBean databaseRegistrationBean() {
    DatabaseRegistrationBean bean = new DatabaseRegistrationBean();
    List<DataSource> dataSourceList = new LinkedList<>();
    // 设置需要展示的数据源
    dataSourceList.add(orderDatasource);
    dataSourceList.add(userDatasource);
    bean.setDataSourceList(dataSourceList);
    return bean;
}
```

3. 打开网页访问域名地址+doc-db.html即可，例：http://192.168.0.100:8080/doc-db.html

#### 界面展示
基本界面：
![](https://images.gitee.com/uploads/images/2018/0918/190615_af5e8cdb_596905.jpeg "31.jpg")
模糊查询：
![](https://images.gitee.com/uploads/images/2018/0918/190721_872b2d76_596905.png "03.png")
表注释修改：
![](https://images.gitee.com/uploads/images/2018/0918/190739_afe7ba53_596905.jpeg "50.jpg")
文档导出：
![](https://images.gitee.com/uploads/images/2018/0920/223122_172d1fc2_596905.png "21.png")
导出文档查看：
![](https://images.gitee.com/uploads/images/2018/0920/223223_b899b367_596905.png "54.png")
表关联关系图：
![](https://images.gitee.com/uploads/images/2018/0925/214544_14b1b6eb_596905.jpeg "08.jpg")