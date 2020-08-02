package com.zyplayer.doc.data.service.elasticsearch.support;

import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * es抽象类
 *
 * @author 暮光：城中城
 * @since 2019-07-07
 */
public abstract class EsAbstractService<T> {
	private static final Logger logger = LoggerFactory.getLogger(EsAbstractService.class);
	
	@Resource
	private ElasticSearchUtil elasticSearchUtil;
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
	
	public boolean isOpen() {
		return elasticSearchUtil.isOpen();
	}
	
	public boolean upsert(T table) {
		String pk = getPrimaryKey(table);
		UpdateRequest request = new UpdateRequest(this.getIndexName(), pk);
		request.timeout(TimeValue.timeValueMinutes(2));
		request.doc(JSON.toJSONString(table), XContentType.JSON);
		request.docAsUpsert(true);
		RestHighLevelClient esClient = elasticSearchUtil.getEsClient();
		try {
			UpdateResponse updateResponse = esClient.update(request, RequestOptions.DEFAULT);
			return updateResponse.status() == RestStatus.OK;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void delete(T table) {
		String pk = getPrimaryKey(table);
		RestHighLevelClient esClient = elasticSearchUtil.getEsClient();
		DeleteRequest request = new DeleteRequest(this.getIndexName(), pk);
		request.timeout(TimeValue.timeValueMinutes(2));
		try {
			DeleteResponse deleteResponse = esClient.delete(request, RequestOptions.DEFAULT);
			if (deleteResponse.getResult() == DocWriteResponse.Result.NOT_FOUND) {
				logger.warn("ElasticSearch delete index id: {} but not found!", pk);
			} else {
				logger.warn("ElasticSearch delete index id: {}", pk);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 多条件 模糊查询，查询前100条
	 *
	 * @param condition 查询条件
	 */
	public List<T> getDataByCondition(List<EsQueryColumn> condition) {
		return getDataByCondition(condition, null, 0, 100).getData();
	}
	
	/**
	 * 多条件 模糊查询
	 *
	 * @param condition  查询条件
	 * @param startIndex 开始行
	 * @param pageSize   每页数量
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
		return this.getDataByQuery(boolQueryBuilder, fields, startIndex, pageSize);
	}
	
	/**
	 * 多条件 模糊查询
	 *
	 * @param queryBuilders 查询条件
	 * @param startIndex    开始行
	 * @param pageSize      每页数量
	 */
	public EsPage<T> getDataByQuery(QueryBuilder queryBuilders, String[] fields, Integer startIndex, Integer pageSize) {
		// 设置高亮标签
		HighlightBuilder highlightBuilder = new HighlightBuilder();
		highlightBuilder.preTags("<span style=\"color:red\">");
		highlightBuilder.postTags("</span>");
		highlightBuilder.field("*");
		// 组装条件
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.query(queryBuilders)
				.highlighter(highlightBuilder).from(startIndex).size(pageSize)
				.timeout(new TimeValue(60, TimeUnit.SECONDS));
		// 查询指定字段
		if (fields != null && fields.length > 0) {
			sourceBuilder.fetchSource(fields, new String[]{});
		}
		// 组装请求
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.indices(this.getIndexName());
		searchRequest.source(sourceBuilder);
		RestHighLevelClient esClient = elasticSearchUtil.getEsClient();
		try {
			SearchResponse response = esClient.search(searchRequest, RequestOptions.DEFAULT);
			return responseToList(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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
		esPage.setTotal(response.getHits().getTotalHits().value);
		esPage.setData(tableList);
		return esPage;
	}
	
}
