package com.zyplayer.doc.elasticsearch.controller;

import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.core.json.ResponseJson;
import com.zyplayer.doc.data.service.elasticsearch.support.ElasticSearchUtil;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetMappingsRequest;
import org.elasticsearch.client.indices.GetMappingsResponse;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.unit.TimeValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
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
	
	@GetMapping("/list")
	public ResponseJson<Object> list(String keywords) throws IOException {
		return DocResponseJson.ok();
	}
	
}

