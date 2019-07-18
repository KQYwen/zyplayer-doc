//package com.zyplayer.doc.test.elasticsearch;
//
//import cn.hutool.core.date.DateTime;
//import com.zyplayer.doc.core.json.DocResponseJson;
//import com.zyplayer.doc.core.json.ResponseJson;
//import io.swagger.annotations.ApiImplicitParam;
//import org.elasticsearch.action.ActionListener;
//import org.elasticsearch.action.delete.DeleteResponse;
//import org.elasticsearch.action.get.GetResponse;
//import org.elasticsearch.action.get.MultiGetItemResponse;
//import org.elasticsearch.action.get.MultiGetResponse;
//import org.elasticsearch.action.index.IndexRequest;
//import org.elasticsearch.action.search.MultiSearchResponse;
//import org.elasticsearch.action.search.SearchRequest;
//import org.elasticsearch.action.search.SearchRequestBuilder;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.action.update.UpdateRequest;
//import org.elasticsearch.client.transport.TransportClient;
//import org.elasticsearch.common.xcontent.XContentFactory;
//import org.elasticsearch.index.query.BoolQueryBuilder;
//import org.elasticsearch.index.query.QueryBuilder;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.index.reindex.BulkByScrollResponse;
//import org.elasticsearch.index.reindex.DeleteByQueryAction;
//import org.elasticsearch.index.reindex.DeleteByQueryRequestBuilder;
//import org.elasticsearch.script.ScriptType;
//import org.elasticsearch.script.mustache.SearchTemplateRequestBuilder;
//import org.elasticsearch.search.SearchHit;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.*;
//
///**
// * 测试es的控制器，依据官方文档7.2.0版本做的一些测试方法
// * 不建议使用了，使用 rest 的客户端
// * @author 暮光：城中城
// * @since 2019年7月14日
// */
//@RestController
//@RequestMapping("/zyplayer-doc-test/es")
//public class TestEsController {
//	private static Logger logger = LoggerFactory.getLogger(TestEsController.class);
//
//	@Autowired(required = false)
//	private TransportClient transportClient;
//
//	private static final String ES_INDEX = "zyplayer_doc_test";
//	private static final String ES_INDEX_2 = "zyplayer_doc_test2";
//	private static final String ES_TYPE = "_doc";
//
//	@GetMapping("/list")
//	public ResponseJson<Object> list() {
//		QueryBuilder queryBuilder = QueryBuilders.boolQuery();
//
//		SearchRequestBuilder requestBuilder = transportClient.prepareSearch(ES_INDEX).setTypes(ES_TYPE)
//				.setQuery(queryBuilder)
//				.setFrom(0).setSize(100).setExplain(true);
//		SearchResponse response = requestBuilder.execute().actionGet();
//		List<Map<String, Object>> resultList = new LinkedList<>();
//		for (SearchHit searchHit : response.getHits().getHits()) {
//			resultList.add(searchHit.getSourceAsMap());
//		}
//		return DocResponseJson.ok(resultList);
//	}
//
//	@PostMapping("/execute")
//	@ApiImplicitParam(name = "sql", value = "执行的DSL表达式", example = "{\"query\":{\"match\":{\"content\":\"测试\"}}}")
//	public ResponseJson<Object> execute(@RequestBody String sql) {
//		SearchRequest searchRequest = new SearchRequest().indices(ES_INDEX).types(ES_TYPE);
//		// 执行查询，测试语句：{"query":{"match":{"content":"测试"}}}
//		SearchResponse searchResponse = new SearchTemplateRequestBuilder(transportClient)
//				.setScript(sql)
//				.setScriptType(ScriptType.INLINE)
//				.setScriptParams(new HashMap<>())
//				.setRequest(searchRequest)
//				.get()
//				.getResponse();
//		// 解析结果
//		List<Map<String, Object>> resultList = new LinkedList<>();
//		for (SearchHit searchHit : searchResponse.getHits().getHits()) {
//			Map<String, Object> sourceMap = searchHit.getSourceAsMap();
//			if (sourceMap != null) {
//				resultList.add(sourceMap);
//			}
//		}
//		return DocResponseJson.ok(resultList);
//	}
//
//	@GetMapping("/get/{id}")
//	public ResponseJson<Object> getById(@PathVariable String id) {
//		// 通过ID获取
//		GetResponse response = transportClient.prepareGet(ES_INDEX, ES_TYPE, id).get();
//		return DocResponseJson.ok(response.getSource());
//	}
//
//	@GetMapping("/delete/{id}")
//	public ResponseJson<Object> deleteById(@PathVariable String id) {
//		// 通过ID删除
//		DeleteResponse response = transportClient.prepareDelete(ES_INDEX, ES_TYPE, id).get();
//		return DocResponseJson.ok(response.status());
//	}
//
//	@GetMapping("/delete")
//	public ResponseJson<Object> delete() {
//		// 条件删除
//		BulkByScrollResponse response =
//				new DeleteByQueryRequestBuilder(transportClient, DeleteByQueryAction.INSTANCE)
//						.filter(QueryBuilders.matchQuery("content", "xx"))
//						.source(ES_INDEX)
//						.get();
//		long deleted = response.getDeleted();
//		return DocResponseJson.ok(deleted);
//	}
//
//	@GetMapping("/deleteAsync")
//	public ResponseJson<Object> deleteAsync() {
//		// 异步删除
//		new DeleteByQueryRequestBuilder(transportClient, DeleteByQueryAction.INSTANCE)
//				.filter(QueryBuilders.matchQuery("content", "测试"))
//				.source(ES_INDEX)
//				.execute(new ActionListener<BulkByScrollResponse>() {
//					@Override
//					public void onResponse(BulkByScrollResponse response) {
//						long deleted = response.getDeleted();
//						logger.info("已删除：{}", deleted);
//					}
//					@Override
//					public void onFailure(Exception e) {
//						logger.error("删除失败", e);
//					}
//				});
//		return DocResponseJson.ok();
//	}
//
//	@GetMapping("/update/{id}")
//	public ResponseJson<Object> update(@PathVariable String id) {
//		// 使用UpdateRequest更新
//		UpdateRequest updateRequest = new UpdateRequest();
//		updateRequest.index(ES_INDEX);
//		updateRequest.type(ES_TYPE);
//		updateRequest.id(id);
//		try {
//			updateRequest.doc(XContentFactory.jsonBuilder()
//					.startObject()
//					.field("content", "测试更新")
//					.endObject());
//			transportClient.update(updateRequest).get();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		// 使用prepareUpdate更新
//		try {
//			transportClient.prepareUpdate(ES_INDEX, ES_TYPE, id)
//					.setDoc(XContentFactory.jsonBuilder()
//							.startObject()
//							.field("content", "测试更新")
//							.endObject())
//					.get();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return DocResponseJson.ok();
//	}
//
//	@GetMapping("/upsert/{id}")
//	@ApiImplicitParam(name = "index", value = "选择索引，可选：1、2")
//	public ResponseJson<Object> upsert(@PathVariable String id, String index) {
//		try {
//			String indexUp = Objects.equals(index, "1") ? ES_INDEX : ES_INDEX_2;
//			IndexRequest indexRequest = new IndexRequest(indexUp, ES_TYPE, id)
//					.source(XContentFactory.jsonBuilder()
//							.startObject()
//							.field("content", "测试更新或新增-新增")
//							.field("createTime", DateTime.now().toString())
//							.endObject());
//			UpdateRequest updateRequest = new UpdateRequest(indexUp, ES_TYPE, id)
//					.doc(XContentFactory.jsonBuilder()
//							.startObject()
//							.field("content", "测试更新或新增-更新")
//							.endObject())
//					.upsert(indexRequest);
//			transportClient.update(updateRequest).get();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return DocResponseJson.ok();
//	}
//
//	@GetMapping("/multiGet")
//	public ResponseJson<Object> multiGet() {
//		MultiGetResponse multiGetItemResponses = transportClient.prepareMultiGet()
//				.add(ES_INDEX, ES_TYPE, "1")                // 通过单ID查
//				.add(ES_INDEX, ES_TYPE, "2", "3", "4")    // 通过多ID查
//				.add(ES_INDEX_2, ES_TYPE, "1")              // 同时查询另一个索引
//				.get();
//		List<Map<String, Object>> resultList = new LinkedList<>();
//		for (MultiGetItemResponse itemResponse : multiGetItemResponses) {
//			GetResponse response = itemResponse.getResponse();
//			if (response.isExists()) {
//				resultList.add(response.getSource());
//			}
//		}
//		return DocResponseJson.ok(resultList);
//	}
//
//	@GetMapping("/multiSearch")
//	public ResponseJson<Object> multiSearch(String content) {
//		SearchRequestBuilder srb1 = transportClient.prepareSearch(ES_INDEX).setQuery(QueryBuilders.matchQuery("content", content)).setSize(10);
//		SearchRequestBuilder srb2 = transportClient.prepareSearch(ES_INDEX_2).setQuery(QueryBuilders.matchQuery("content", content)).setSize(10);
//		MultiSearchResponse multiSearchResponse = transportClient.prepareMultiSearch().add(srb1).add(srb2).get();
//		List<Map<String, Object>> resultList = new LinkedList<>();
//		for (MultiSearchResponse.Item item : multiSearchResponse) {
//			for (SearchHit searchHit : item.getResponse().getHits().getHits()) {
//				Map<String, Object> sourceMap = searchHit.getSourceAsMap();
//				if (sourceMap != null) {
//					resultList.add(sourceMap);
//				}
//			}
//		}
//		return DocResponseJson.ok(resultList);
//	}
//
//	@GetMapping("/terminateAfter")
//	public ResponseJson<Object> terminateAfter() {
//		SearchResponse sr = transportClient.prepareSearch(ES_INDEX)
//				.setTerminateAfter(1000)
//				.get();
//		if (sr.isTerminatedEarly()) {
//			// We finished early
//			return DocResponseJson.ok("成功");
//		}
//		return DocResponseJson.warn("失败");
//	}
//
//	@GetMapping("/other")
//	public ResponseJson<Object> other() {
//		// 都是些官网上的一些例子，不细跑了
//		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
//		queryBuilder.must(QueryBuilders.matchQuery("content","xxx"));
//		queryBuilder.must(QueryBuilders.multiMatchQuery("xxx", "content", "name"));
//		queryBuilder.must(QueryBuilders.commonTermsQuery("content","xxx"));
//		queryBuilder.must(QueryBuilders.queryStringQuery("+xxx -aa"));
//		queryBuilder.must(QueryBuilders.simpleQueryStringQuery("+xxx -aa"));
//		// term query
//		queryBuilder.must(QueryBuilders.termQuery("content","xxx"));
//		queryBuilder.must(QueryBuilders.termsQuery("xxx", "content", "name"));
//		queryBuilder.must(QueryBuilders.rangeQuery("age")
//				.from(5)
//				.to(10)
//				.includeLower(true)     // 相当于 >=
//				.includeUpper(false));  // 相当于 < ，true 表示 <=
//		queryBuilder.must(QueryBuilders.rangeQuery("age")
//				.gte("10")
//				.lt("20"));
//		queryBuilder.must(QueryBuilders.existsQuery("name"));
//		queryBuilder.must(QueryBuilders.prefixQuery("content", "xxx"));
//		queryBuilder.must(QueryBuilders.wildcardQuery("content", "k?mch*"));
//		queryBuilder.must(QueryBuilders.regexpQuery("content", "s.*y"));
//		queryBuilder.must(QueryBuilders.fuzzyQuery("content", "xxx"));
//		queryBuilder.must(QueryBuilders.idsQuery().addIds("1", "2", "3"));
//		queryBuilder.must(QueryBuilders.constantScoreQuery(QueryBuilders.termQuery("content","xxx")).boost(2.0f));
//		// 如果满足了这些条件的，则降低分数，原分数*0.5 则表示下降了一半
//		queryBuilder.must(QueryBuilders.boostingQuery(
//				QueryBuilders.termQuery("name","kimchy"),
//				QueryBuilders.termQuery("name","dadoonet"))
//				.negativeBoost(0.5f));
//		// 7.x 才支持
////		queryBuilder.must(QueryBuilders.constantScoreQuery(QueryBuilders.termQuery("content","xxx")).boost(2.0f).tieBreaker(0.7f));
//		// filter：匹配内容必须出现在文档中，但不参与评分
//		queryBuilder.filter(QueryBuilders.matchQuery("content","xxx"));
//
//
//		SearchRequestBuilder requestBuilder = transportClient.prepareSearch(ES_INDEX).setTypes(ES_TYPE)
//				.setQuery(queryBuilder).setFrom(0).setSize(100).setExplain(true);
//		SearchResponse response = requestBuilder.execute().actionGet();
//		List<Map<String, Object>> resultList = new LinkedList<>();
//		for (SearchHit searchHit : response.getHits().getHits()) {
//			resultList.add(searchHit.getSourceAsMap());
//		}
//		return DocResponseJson.ok(resultList);
//	}
//}
//
