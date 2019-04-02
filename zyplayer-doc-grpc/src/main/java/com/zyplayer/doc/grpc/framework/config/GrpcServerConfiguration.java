package com.zyplayer.doc.grpc.framework.config;

import com.nxest.grpc.server.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * grpc服务
 *
 * @author 暮光：城中城
 * @since 2019年3月31日
 */
@Configuration
public class GrpcServerConfiguration {

    @Resource
    private GrpcProperties grpcProperties;

    @Bean
    public GrpcServiceDiscoverer serviceDiscoverer() {
        return new AnnotationGrpcServiceDiscoverer();
    }

    @Bean
    public GrpcServerFactory severFactory() {
        return new NettyGrpcServerFactory(serviceDiscoverer(), grpcProperties.getServer());
    }

    @Bean(name = "grpcServer", initMethod = "start", destroyMethod = "destroy")
    public GrpcServer serverRunner() {
        return severFactory().createServer();
    }
}
