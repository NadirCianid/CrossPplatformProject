package com.server.services;

import com.test.grpc.CalculatorGrpc;

import com.test.grpc.CalculatorService;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;

@Service
public class CalculationService extends CalculatorGrpc.CalculatorImplBase {

    private final long MILLIS_MULTIPLIER = 1000L;


    public void calculate(CalculatorService.Request request, StreamObserver<CalculatorService.Response> responseObserver) {
        for (double time = request.getStartTime(); compare(time, request.getEndTime()); time += request.getStep()) {
            CalculatorService.Response response = CalculatorService.Response.
                    newBuilder().
                    setTime(time).
                    setResult(calculate(time)).
                    build();

            responseObserver.onNext(response);

            System.out.println(response);

            try {
                Thread.sleep((long) (request.getStep() * MILLIS_MULTIPLIER));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        responseObserver.onCompleted();
    }

    private Double calculate(Double parameter) {
        return Math.cos(parameter); //example
    }

    private boolean compare(double first, double second) {
        return Double.compare(first, second) != 1;
    }
}
