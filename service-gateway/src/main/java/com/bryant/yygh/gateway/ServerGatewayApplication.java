package com.bryant.yygh.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class ServerGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServerGatewayApplication.class, args);
    }


    /**
     * 网关这种写法和yml中的写法一致的
     * @param builder
     * @return
     */
//        @Bean
//        public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//            return builder.routes()
//                    .route("service-msm", r -> r.path("/*/msm/**").uri("lb://service-msm"))
//                    .build();
//        }

}