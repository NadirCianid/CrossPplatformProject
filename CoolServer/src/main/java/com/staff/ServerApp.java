package com.staff;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class ServerApp {
    public static void main(String[] args) throws IOException, InterruptedException {
        //Создание объекта сервера на выбранном порте
        Server server = ServerBuilder.forPort(8082).addService(new Calculations()).build();

        server.start();

        System.out.print("Server started.");

        server.awaitTermination();
    }
}
