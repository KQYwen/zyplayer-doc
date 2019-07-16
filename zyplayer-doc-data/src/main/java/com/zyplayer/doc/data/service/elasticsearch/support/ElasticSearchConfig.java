package com.zyplayer.doc.data.service.elasticsearch.support;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 开启es客户端
 *
 * @author 暮光：城中城
 * @since 2019-07-07
 */
@Configuration
@ConditionalOnProperty(prefix = "zyplayer.doc.manage.elasticsearch", name = "open", havingValue = "true")
public class ElasticSearchConfig {
	
	@Value(value = "${zyplayer.doc.manage.elasticsearch.host:''}")
	private String host;
	@Value("${zyplayer.doc.manage.elasticsearch.port:''}")
	private String port;
	@Value("${zyplayer.doc.manage.elasticsearch.cluster-name:''}")
	private String clusterName;
	
	@Bean
	public TransportClient esClient() throws UnknownHostException {
		Settings settings = Settings.builder()
				.put("cluster.name", clusterName)
				.put("client.transport.sniff", true)
				.build();
		TransportAddress master = new TransportAddress(InetAddress.getByName(host), Integer.valueOf(port));
		return new PreBuiltTransportClient(settings).addTransportAddress(master);
	}
}
