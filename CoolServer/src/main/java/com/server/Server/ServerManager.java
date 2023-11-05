package com.server.Server;

import io.grpc.Server;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

@Component
@AllArgsConstructor
public class ServerManager {

    private Server server;

    @PostConstruct
    public void startServer() throws IOException, InterruptedException {
        server.start();

        server.awaitTermination();
    }

    @PreDestroy
    public void stopServer() {
        server.shutdownNow();
    }
}
