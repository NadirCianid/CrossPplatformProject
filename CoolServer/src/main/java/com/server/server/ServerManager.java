package com.server.server;

import io.grpc.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ServerManager {

    private final Server server;

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
