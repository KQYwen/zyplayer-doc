package com.zyplayer.doc.elasticsearch.controller;

import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.core.json.ResponseJson;
import com.zyplayer.doc.data.service.elasticsearch.support.ElasticSearchUtil;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.client.indices.GetMappingsRequest;
import org.elasticsearch.client.indices.GetMappingsResponse;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.script.mustache.SearchTemplateRequest;
import org.elasticsearch.script.mustache.SearchTemplateResponse;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * es的控制器，7.2.0版本
 *
 * @author 暮光：城中城
 * @since 2019年7月14日
 */
@RestController
@RequestMapping("/zyplayer-doc-es/es-mapping")
public class EsMappingController {
	private static Logger logger = LoggerFactory.getLogger(EsMappingController.class);
	
	// 相关文档地址
	// https://www.elastic.co/guide/en/elasticsearch/client/java-rest/current/java-rest-high-get-settings.html
	// https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-get-settings.html
	
	@Resource
	ElasticSearchUtil elasticSearchUtil;
	
	@PostMapping("/mappings")
	public ResponseJson<Object> mappings(String index) throws IOException {
		GetMappingsRequest request = new GetMappingsRequest();
		request.setMasterTimeout(TimeValue.timeValueMinutes(1));
		if (StringUtils.isNotBlank(index)) {
			request.indices(index);
		}
		try {
			RestHighLevelClient client = elasticSearchUtil.getEsClient("127.0.0.1:9200", "http");
			GetMappingsResponse getMappingResponse = client.indices().getMapping(request, RequestOptions.DEFAULT);
			Map<String, MappingMetaData> allMappings = getMappingResponse.mappings();
			return DocResponseJson.ok(allMappings);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return DocResponseJson.warn("获取文档失败");
	}
	
	@PostMapping("/execute")
	public ResponseJson<Object> execute(String index, String sql) throws IOException {
		SearchTemplateRequest request = new SearchTemplateRequest();
		request.setRequest(new SearchRequest().indices(index));
		request.setScriptType(ScriptType.INLINE);
		request.setScript(sql);
		request.setScriptParams(new HashMap<>());
		try {
			RestHighLevelClient client = elasticSearchUtil.getEsClient("127.0.0.1:9200", "http");
			SearchTemplateResponse response = client.searchTemplate(request, RequestOptions.DEFAULT);
			List<Map<String, Object>> resultList = new LinkedList<>();
			for (SearchHit searchHit : response.getResponse().getHits()) {
				resultList.add(searchHit.getSourceAsMap());
			}
			return DocResponseJson.ok(resultList);
		} catch (Exception e) {
			return DocResponseJson.warn(e.getMessage());
		}
	}
	
	@GetMapping("/index")
	public ResponseJson<Object> index(String index) throws IOException {
		GetIndexRequest request = new GetIndexRequest(index);
		request.setMasterTimeout(TimeValue.timeValueMinutes(1));
		try {
			RestHighLevelClient client = elasticSearchUtil.getEsClient("127.0.0.1:9200", "http");
			GetIndexResponse indexResponse = client.indices().get(request, RequestOptions.DEFAULT);
			Map<String, Object> resultMap = new HashMap<>();
			resultMap.put("mapping", indexResponse.getMappings().get(index));
			resultMap.put("setting", indexResponse.getSettings().get(index).keySet());
			
//			return DocResponseJson.ok(indexResponse.getSetting(index, "index.number_of_shards"));
			return DocResponseJson.ok(resultMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return DocResponseJson.warn("获取文档失败");
	}
	
}

