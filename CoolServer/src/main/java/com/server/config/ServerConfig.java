package com.server.config;

import com.server.services.CalculationService;
import com.server.services.GetFormulaService;
import com.server.services.GetMethodsService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class ServerConfig {

    private static final int PORT = 8083;

    private CalculationService calculatorService;

    private GetMethodsService getMethodsService;

    private GetFormulaService getFormulaService;

    @Bean
    public Server server() {
        return ServerBuilder.
                forPort(PORT).
                addService( calculatorService).
                addService(getMethodsService).
                addService(getFormulaService).
                build();
    }
}
