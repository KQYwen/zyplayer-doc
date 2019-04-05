package com.zyplayer.doc.grpc.framework.config;

import io.grpc.Metadata;
import io.netty.handler.ssl.SslContext;

public interface DocGrpcContext {
	
	SslContext getSslContext();
	
	String getHost();
	
	Metadata getMetadata();
	
	Integer getPort();

}
