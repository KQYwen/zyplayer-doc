package com.zyplayer.doc.db.service;

import com.zyplayer.doc.db.framework.db.bean.DatabaseFactoryBean;
import org.springframework.stereotype.Service;

@Service
public class OracleServiceImpl extends DbBaseService {
	
	@Override
	DatabaseFactoryBean.DatabaseProduct getDatabaseProduct() {
		return DatabaseFactoryBean.DatabaseProduct.ORACLE;
	}
}
