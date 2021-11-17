//package com.zyplayer.doc.test.elasticsearch;
//
//import com.alibaba.fastjson.JSON;
//import com.zyplayer.doc.core.json.DocResponseJson;
//import com.zyplayer.doc.core.json.ResponseJson;
//import com.zyplayer.doc.data.service.elasticsearch.entity.EsWikiPage;
//import io.swagger.annotations.ApiImplicitParam;
//import org.apache.http.HttpHost;
//import org.elasticsearch.action.delete.DeleteRequest;
//import org.elasticsearch.action.delete.DeleteResponse;
//import org.elasticsearch.action.get.*;
//import org.elasticsearch.action.search.SearchRequest;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.action.update.UpdateRequest;
//import org.elasticsearch.action.update.UpdateResponse;
//import org.elasticsearch.client.RequestOptions;
//import org.elasticsearch.client.RestClient;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.client.indices.GetMappingsRequest;
//import org.elasticsearch.client.indices.GetMappingsResponse;
//import org.elasticsearch.cluster.metadata.MappingMetaData;
//import org.elasticsearch.common.Strings;
//import org.elasticsearch.common.unit.TimeValue;
//import org.elasticsearch.common.xcontent.XContentType;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.script.ScriptType;
//import org.elasticsearch.script.mustache.SearchTemplateRequest;
//import org.elasticsearch.script.mustache.SearchTemplateResponse;
//import org.elasticsearch.search.SearchHit;
//import org.elasticsearch.search.builder.SearchSourceBuilder;
//import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.bind.annotation.*;
//
//import java.io.IOException;
//import java.util.*;
//import java.util.concurrent.TimeUnit;
//
///**
// * 测试es的控制器，依据官方文档7.2.0版本做的一些测试方法
// *
// * @author 暮光：城中城
// * @since 2019年7月14日
// */
//@RestController
//@RequestMapping("/zyplayer-doc-test/es-rest")
//public class TestEsRestController {
//	private static Logger logger = LoggerFactory.getLogger(TestEsRestController.class);
//
//	private static final String ES_INDEX = "zyplayer_doc_test";
//	private static final String ES_INDEX_2 = "zyplayer_doc_test2";
//	private static final String ES_TYPE = "_doc";
//
//	// rest请求客户端
//	private RestHighLevelClient client = new RestHighLevelClient(
//			RestClient.builder(
//					new HttpHost("127.0.0.1", 9200, "http")
//			));
//
//	@GetMapping("/list")
//	public ResponseJson<Object> list(String keywords) throws IOException {
//		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//		sourceBuilder.query(QueryBuilders.termQuery("content", keywords));
//		sourceBuilder.from(0);
//		sourceBuilder.size(10);
//		sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
//
//		String[] includeFields = new String[] {"content", "*Time"};
//		String[] excludeFields = new String[] {"user"};
//		sourceBuilder.fetchSource(includeFields, excludeFields);
//
//		SearchRequest searchRequest = new SearchRequest();
//		searchRequest.indices(ES_INDEX);
//		searchRequest.source(sourceBuilder);
//
//		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
//
//		List<Map<String, Object>> resultList = new LinkedList<>();
//		for (SearchHit searchHit : searchResponse.getHits().getHits()) {
//			resultList.add(searchHit.getSourceAsMap());
//		}
//		return DocResponseJson.ok(resultList);
//	}
//
//	@PostMapping("/execute")
//	@ApiImplicitParam(name = "sql", value = "执行的DSL表达式", example = "{\"query\":{\"match\":{\"content\":\"测试\"}}}")
//	public ResponseJson<Object> execute(@RequestBody String sql) throws IOException {
//		SearchTemplateRequest request = new SearchTemplateRequest();
//		request.setRequest(new SearchRequest(ES_INDEX).types(ES_TYPE));
//
//		request.setScriptType(ScriptType.INLINE);
//		request.setScript(sql);
////		可使用动态参数，{{field}}，然后map为值
////		Map<String, Object> scriptParams = new HashMap<>();
////		scriptParams.put("field", "title");
////		scriptParams.put("value", "elasticsearch");
////		scriptParams.put("size", 5);
////		request.setScriptParams(scriptParams);
//
//		SearchTemplateResponse response = client.searchTemplate(request, RequestOptions.DEFAULT);
//
//		List<Map<String, Object>> resultList = new LinkedList<>();
//		for (SearchHit searchHit : response.getResponse().getHits()) {
//			resultList.add(searchHit.getSourceAsMap());
//		}
//		return DocResponseJson.ok(resultList);
//	}
//
//	@GetMapping("/get/{id}")
//	public ResponseJson<Object> getById(@PathVariable String id) throws Exception {
//		GetRequest getRequest = new GetRequest(ES_INDEX, id);
//		String[] includes = new String[]{"content", "*Time"};
//		String[] excludes = Strings.EMPTY_ARRAY;
//		FetchSourceContext fetchSourceContext = new FetchSourceContext(true, includes, excludes);
//		getRequest.fetchSourceContext(fetchSourceContext);
//		GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
//		return DocResponseJson.ok(getResponse.getSource());
//	}
//
//	@GetMapping("/delete/{id}")
//	public ResponseJson<Object> deleteById(@PathVariable String id) throws IOException {
//		DeleteRequest request = new DeleteRequest(ES_INDEX, id);
//		request.timeout(TimeValue.timeValueMinutes(2));
//		DeleteResponse deleteResponse = client.delete(request, RequestOptions.DEFAULT);
//		return DocResponseJson.ok(deleteResponse.getResult().name());
//	}
//
//	@GetMapping("/delete")
//	public ResponseJson<Object> delete() {
//		return DocResponseJson.ok();
//	}
//
//	@GetMapping("/deleteAsync")
//	public ResponseJson<Object> deleteAsync() {
//		return DocResponseJson.ok();
//	}
//
//	@GetMapping("/update/{id}")
//	public ResponseJson<Object> update(@PathVariable String id, EsWikiPage esWikiPage) throws IOException {
//		UpdateRequest request = new UpdateRequest(ES_INDEX, id);
//		request.timeout(TimeValue.timeValueMinutes(2));
//		// 需要更新的内容
//		esWikiPage.setUpdateTime(new Date());
//		request.doc(JSON.toJSONString(esWikiPage), XContentType.JSON);
//		UpdateResponse deleteResponse = client.update(request, RequestOptions.DEFAULT);
//		return DocResponseJson.ok(deleteResponse.getResult().name());
//	}
//
//	@GetMapping("/upsert/{id}")
//	@ApiImplicitParam(name = "esIndex", value = "选择索引，可选：1、2")
//	public ResponseJson<Object> upsert(@PathVariable String id, String esIndex, EsWikiPage esWikiPage) throws IOException {
//		String indexUp = Objects.equals(esIndex, "1") ? ES_INDEX : ES_INDEX_2;
//		UpdateRequest request = new UpdateRequest(indexUp, id);
//		request.timeout(TimeValue.timeValueMinutes(2));
//		// 需要更新的内容
//		esWikiPage.setUpdateTime(new Date());
//		request.upsert(JSON.toJSONString(esWikiPage), XContentType.JSON);
//		request.docAsUpsert(true);
//		UpdateResponse deleteResponse = client.update(request, RequestOptions.DEFAULT);
//		return DocResponseJson.ok(deleteResponse.getResult().name());
//	}
//
//	@GetMapping("/multiGet")
//	public ResponseJson<Object> multiGet() throws IOException {
//		String[] includes = new String[] {"content", "*Time"};
//		String[] excludes = Strings.EMPTY_ARRAY;
//		FetchSourceContext fetchSourceContext = new FetchSourceContext(true, includes, excludes);
//
//		MultiGetRequest request = new MultiGetRequest();
//		request.add(new MultiGetRequest.Item(ES_INDEX, "id").fetchSourceContext(fetchSourceContext));
//		request.add(new MultiGetRequest.Item(ES_INDEX_2, "id").fetchSourceContext(fetchSourceContext));
//
//		MultiGetResponse response = client.mget(request, RequestOptions.DEFAULT);
//
//		List<Map<String, Object>> resultList = new LinkedList<>();
//		for (MultiGetItemResponse itemResponse : response.getResponses()) {
//			resultList.add(itemResponse.getResponse().getSource());
//		}
//		return DocResponseJson.ok(resultList);
//	}
//
//	@GetMapping("/multiSearch")
//	public ResponseJson<Object> multiSearch(String content) {
//		return DocResponseJson.ok();
//	}
//
//	@GetMapping("/terminateAfter")
//	public ResponseJson<Object> terminateAfter() {
//		return DocResponseJson.warn("失败");
//	}
//
//	@GetMapping("/mappings")
//	public ResponseJson<Object> mappings() throws IOException {
//		GetMappingsRequest request = new GetMappingsRequest();
////		request.indices(ES_INDEX);
//		request.setMasterTimeout(TimeValue.timeValueMinutes(1));
//		GetMappingsResponse getMappingResponse = client.indices().getMapping(request, RequestOptions.DEFAULT);
//		Map<String, MappingMetaData> allMappings = getMappingResponse.mappings();
//		MappingMetaData indexMapping = allMappings.get(ES_INDEX);
//		Map<String, Object> mapping = indexMapping.sourceAsMap();
//		// 相关文档地址
//		// https://www.elastic.co/guide/en/elasticsearch/client/java-rest/current/java-rest-high-get-settings.html
//		// https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-get-settings.html
//		return DocResponseJson.ok(allMappings);
//	}
//
//	@GetMapping("/other")
//	public ResponseJson<Object> other() {
//		return DocResponseJson.ok();
//	}
//}
//
