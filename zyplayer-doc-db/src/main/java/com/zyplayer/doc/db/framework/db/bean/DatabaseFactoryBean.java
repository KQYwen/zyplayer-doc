package com.zyplayer.doc.db.framework.db.bean;

import com.alibaba.druid.pool.DruidDataSource;
import com.zyplayer.doc.db.framework.db.enums.DatabaseProductEnum;
import lombok.Data;
import org.mybatis.spring.SqlSessionTemplate;

/**
 * 描述连接信息的对象
 *
 * @author 暮光：城中城
 * @since 2018年8月8日
 */
@Data
public class DatabaseFactoryBean {
	private Long id;
	private DruidDataSource dataSource;
	private SqlSessionTemplate sqlSessionTemplate;
	private String url;
	private String dbName;
	private String cnName;
	private String groupName;
	private DatabaseProductEnum databaseProduct;

}
