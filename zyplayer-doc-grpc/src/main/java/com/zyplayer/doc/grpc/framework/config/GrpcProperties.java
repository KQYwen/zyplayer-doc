package com.zyplayer.doc.grpc.framework.config;

import com.nxest.grpc.client.configure.GrpcClientProperties;
import com.nxest.grpc.server.configure.GrpcServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "grpc")
public class GrpcProperties {

    /**
     * Grpc server properties
     */
    private final GrpcServerProperties server = new GrpcServerProperties();
    /**
     * Grpc client properties
     */
    private final GrpcClientProperties client = new GrpcClientProperties();

    public GrpcServerProperties getServer() {
        return server;
    }

    public GrpcClientProperties getClient() {
        return client;
    }
}
