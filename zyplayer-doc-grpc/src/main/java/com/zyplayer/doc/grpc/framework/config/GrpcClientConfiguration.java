//package com.zyplayer.doc.grpc.framework.config;
//
//import com.nxest.grpc.client.*;
//import com.nxest.grpc.client.configure.GrpcClientProperties;
//import org.springframework.boot.autoconfigure.AutoConfigureAfter;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.annotation.Resource;
//
///**
// * grpc配置
// *
// * @author 暮光：城中城
// * @since 2019年3月31日
// */
//@Configuration
////@AutoConfigureAfter(GrpcServerConfiguration.class)
//public class GrpcClientConfiguration {
//
//    @Resource
//    private GrpcProperties grpcProperties;
//
//    @Bean
//    public GrpcClientProperties clientProperties() {
//        return grpcProperties.getClient();
//    }
//
//    @Bean
//    public GrpcChannelFactory channelFactory() {
//        return new AddressChannelFactory(clientProperties());
//    }
//
//    @Bean
//    public GrpcClientInterceptorDiscoverer clientInterceptorDiscoverer() {
//        return new AnnotationClientInterceptorDiscoverer();
//    }
//
//    @Bean
//    @ConditionalOnClass(GrpcClient.class)
//    public GrpcClientBeanPostProcessor grpcClientBeanPostProcessor() {
//        return new GrpcClientBeanPostProcessor(channelFactory(), clientInterceptorDiscoverer());
//    }
//}
