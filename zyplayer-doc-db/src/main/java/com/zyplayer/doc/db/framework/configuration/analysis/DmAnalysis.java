package com.zyplayer.doc.db.framework.configuration.analysis;

import com.zyplayer.doc.db.framework.db.bean.DatabaseFactoryBean;
import com.zyplayer.doc.db.framework.db.enums.DatabaseProductEnum;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * 达梦链接url解析
 *
 * @author diantu
 * @since 2023-02-01
 */
public class DmAnalysis implements AnalysisApi{

    @Override
    public Resource[] process(String dbUrl, DatabaseFactoryBean databaseFactoryBean) throws Exception {
        // jdbc:dm://127.0.0.1:5236?serverTimezone=UTC&useSSL=false&useUnicode=true&characterEncoding=utf-8
        String[] urlParamArr = dbUrl.split("\\?");
        String[] urlDbNameArr = urlParamArr[0].split("://");
        urlDbNameArr = urlDbNameArr[0].split(":");
        databaseFactoryBean.setDbName(urlDbNameArr[urlDbNameArr.length - 1]);
        databaseFactoryBean.setDatabaseProduct(DatabaseProductEnum.DM);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        return resolver.getResources("classpath:com/zyplayer/doc/db/framework/db/mapper/dm/*.xml");
    }
}
