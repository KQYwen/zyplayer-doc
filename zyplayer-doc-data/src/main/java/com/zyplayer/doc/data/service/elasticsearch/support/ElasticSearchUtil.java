package com.zyplayer.doc.data.service.elasticsearch.support;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 开启es客户端
 *
 * @author 暮光：城中城
 * @since 2019-07-07
 */
@Configuration
public class ElasticSearchUtil {
	private static Logger logger = LoggerFactory.getLogger(ElasticSearchUtil.class);
	
	@Value(value = "${zyplayer.doc.manage.elasticsearch.hostPort:''}")
	private String hostAndPort;
	@Value(value = "${zyplayer.doc.manage.elasticsearch.scheme:''}")
	private String esScheme;
	@Value("${zyplayer.doc.manage.elasticsearch.open:''}")
	private String elasticsearchOpen;
	
	private static final Object createLock = new Object();
	private static Map<String, RestHighLevelClient> restClientMap = new ConcurrentHashMap<>();
	
	public boolean isOpen() {
		return Objects.equals("true", elasticsearchOpen);
	}
	
	public RestHighLevelClient getEsClient() {
		if (!this.isOpen()) {
			return null;
		}
		String mapKey = esScheme + "_" + hostAndPort;
		RestHighLevelClient restClient = restClientMap.get(mapKey);
		if (restClient == null) {
			synchronized (createLock) {
				restClient = restClientMap.get(mapKey);
				if (restClient == null) {
					try {
						// rest请求客户端
						// 例：10.16.32.12:9200,10.16.32.12:9201
						List<HttpHost> hostPortList = new LinkedList<>();
						for (String hostPortStr : hostAndPort.split(",")) {
							String[] splitArr = hostPortStr.split(":");
							hostPortList.add(new HttpHost(splitArr[0], Integer.valueOf(splitArr[1]), esScheme));
						}
						restClient = new RestHighLevelClient(RestClient.builder(hostPortList.toArray(new HttpHost[]{})));
						restClientMap.put(mapKey, restClient);
					} catch (Exception e) {
						logger.error("创建es客户端失败", e);
					}
				}
			}
		}
		return restClient;
	}
}
