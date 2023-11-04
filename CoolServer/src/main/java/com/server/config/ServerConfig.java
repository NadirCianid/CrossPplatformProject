package com.server.config;

import com.server.services.CalculationService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerConfig {

    @Bean
    public Server server() {
        return ServerBuilder.forPort(8082).addService(new CalculationService()).build();
    }
}
