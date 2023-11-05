package com.server.config;

import com.server.services.CalculationService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerConfig {

    private static final int PORT = 8082;

    @Bean
    public Server server() {
        return ServerBuilder.
                forPort(PORT).
                addService(new CalculationService()).
                build();
    }
}
