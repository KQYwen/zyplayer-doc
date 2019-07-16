package com.zyplayer.doc.data.service.elasticsearch.service;

import com.zyplayer.doc.data.service.elasticsearch.entity.EsWikiPage;
import com.zyplayer.doc.data.service.elasticsearch.support.ElasticSearchConfig;
import com.zyplayer.doc.data.service.elasticsearch.support.EsAbstractService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;

/**
 * wiki文档搜索
 * @author 暮光：城中城
 * @since 2019-07-07
 */
@Service
@ConditionalOnBean(ElasticSearchConfig.class)
public class EsWikiPageService extends EsAbstractService<EsWikiPage> {
	
	@Override
	public Class<EsWikiPage> getObjClass() {
		return EsWikiPage.class;
	}
	
	@Override
	public String getPrimaryKey(EsWikiPage tableIndex) {
		return String.valueOf(tableIndex.getId());
	}
}
