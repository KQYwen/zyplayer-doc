package com.zyplayer.doc.data.service.elasticsearch.support;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * es抽象类
 * @author 暮光：城中城
 * @since 2019-07-07
 */
public abstract class EsAbstractService<T> {
	private static final Logger logger = LoggerFactory.getLogger(EsAbstractService.class);
	
	@Resource
	private TransportClient esClient;
	@Resource
	private Mapper mapper;
	
	public abstract Class<T> getObjClass();
	
	public abstract String getPrimaryKey(T table);
	
	private String getIndexName() {
		Document annotation = this.getObjClass().getAnnotation(Document.class);
		return annotation.indexName();
	}
	
	private String getIndexType() {
		Document annotation = this.getObjClass().getAnnotation(Document.class);
		return annotation.indexType();
	}
	
	public boolean create(T table) {
		String pk = getPrimaryKey(table);
		IndexResponse indexResponse = this.esClient
				.prepareIndex(this.getIndexName(), this.getIndexType())
				.setId(pk)
				.setSource(JSONObject.toJSONString(table), XContentType.JSON)
				.get();
		logger.debug("ElasticSearch create index with table, pk: {}", pk);
		return indexResponse.status() == RestStatus.CREATED;
	}
	
	public boolean update(T table) {
		String pk = getPrimaryKey(table);
		UpdateResponse updateResponse = this.esClient
				.prepareUpdate(this.getIndexName(), this.getIndexType(), pk)
				.setDoc(JSONObject.toJSONString(table), XContentType.JSON)
				.get();
		logger.info("ElasticSearch update index with table, pk: {}", pk);
		return updateResponse.status() == RestStatus.OK;
	}
	
	public void delete(T table) {
		String pk = getPrimaryKey(table);
		DeleteResponse response = this.esClient
				.prepareDelete(this.getIndexName(), this.getIndexType(), pk)
				.execute()
				.actionGet();
		if (response.getResult() == DocWriteResponse.Result.NOT_FOUND) {
			logger.warn("ElasticSearch delete index id: {} but not found!", pk);
		} else {
			logger.warn("ElasticSearch delete index id: {}", pk);
		}
	}
	
	/**
	 * 多条件 模糊查询，查询前100条
	 * @param condition 查询条件
	 */
	public List<T> getDataByCondition(List<EsQueryColumn> condition) {
		return getDataByCondition(condition, null, 0, 100).getData();
	}
	
	/**
	 * 多条件 模糊查询
	 * @param condition 查询条件
	 * @param startIndex 开始行
	 * @param pageSize 每页数量
	 */
	public EsPage<T> getDataByCondition(List<EsQueryColumn> condition, String[] fields, Integer startIndex, Integer pageSize) {
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		// 组装条件
		for (EsQueryColumn column : condition) {
			if (StringUtils.isBlank(column.getValue())) {
				if (CollectionUtils.isEmpty(column.getValues())) {
					continue;
				}
				for (Object value : column.getValues()) {
					if (column.getType() == 0) {
						boolQueryBuilder.must(QueryBuilders.wildcardQuery(column.getKey(), value.toString()));
					} else if (column.getType() == 1) {
						boolQueryBuilder.must(QueryBuilders.termQuery(column.getKey(), value));
					}
				}
			} else {
				if (column.getType() == 0) {
					boolQueryBuilder.must(QueryBuilders.wildcardQuery(column.getKey(), column.getValue()));
				} else if (column.getType() == 1) {
					boolQueryBuilder.must(QueryBuilders.termQuery(column.getKey(), column.getValue()));
				}
			}
		}
		condition.forEach(val -> {
			if (StringUtils.isNotBlank(val.getValue())) {
				if (val.getType() == 0) {
					boolQueryBuilder.must(QueryBuilders.wildcardQuery(val.getKey(), val.getValue()));
				} else if (val.getType() == 1) {
					boolQueryBuilder.must(QueryBuilders.termQuery(val.getKey(), val.getValue()));
				}
			}
		});
		// 设置高亮标签
		HighlightBuilder highlightBuilder = new HighlightBuilder();
		highlightBuilder.preTags("<span style=\"color:red\">");
		highlightBuilder.postTags("</span>");
		highlightBuilder.field("*");
		SearchRequestBuilder requestBuilder = esClient.prepareSearch(this.getIndexName()).setTypes(this.getIndexType())
				.setQuery(boolQueryBuilder)
				.highlighter(highlightBuilder)
				.setFrom(startIndex).setSize(pageSize).setExplain(true);
		// 查询指定字段
		if (fields != null && fields.length > 0) {
			requestBuilder.setFetchSource(fields, new String[]{});
		}
		SearchResponse response = requestBuilder.execute().actionGet();
		return responseToList(response);
	}
	
	public EsPage<T> responseToList(SearchResponse response) {
		List<T> tableList = new LinkedList<>();
		for (SearchHit searchHit : response.getHits().getHits()) {
			// 获取表ID和表类型
			Map<String, Object> sourceMap = searchHit.getSourceAsMap();
			if (sourceMap == null) {
				sourceMap = new HashMap<>();
			}
			// 获取高亮文本
			Map<String, HighlightField> highlightFieldMap = searchHit.getHighlightFields();
			for (String key : highlightFieldMap.keySet()) {
				HighlightField hf = highlightFieldMap.get(key);
				StringBuilder fragments = new StringBuilder();
				for (Text text : hf.getFragments()) {
					fragments.append(text.toString());
				}
				sourceMap.put(key, fragments.toString());
			}
			T table = mapper.map(sourceMap, this.getObjClass());
			tableList.add(table);
		}
		EsPage<T> esPage = new EsPage<>();
		esPage.setTotal(response.getHits().getTotalHits());
		esPage.setData(tableList);
		return esPage;
	}

}
