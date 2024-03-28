package com.server.services;

import com.server.operations.Operation;
import com.server.operations.OperationsManager;
import com.test.grpc.CalculatorGrpc;
import com.test.grpc.CalculatorService;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.beans.beancontext.BeanContext;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CalculationService extends CalculatorGrpc.CalculatorImplBase {


    private OperationsManager operationsManager;



    @Override
    public void calculate(CalculatorService.Request request, StreamObserver<CalculatorService.ResponseArray> responseObserver) {

         int startX=(int)request.getStartX();
         int startY=(int)request.getStartY();
         int endX=(int)request.getEndX();
         int endY=(int)request.getEndY();
         Operation operation = operationsManager.getOperationByName(request.getMethodName());
         operation.initBorders(request.getStartX(),request.getEndX(),
                 request.getStartY(),request.getEndY(),
                 request.getStartT(),request.getEndT());



        for (long t = request.getStartT(); compare(t, request.getEndT()); t += 1) {


            List<CalculatorService.Response> responses = operation.magic(t);
            responseObserver.onNext(buildResponseArray(responses));
        }

        responseObserver.onCompleted();
    }

    private CalculatorService.ResponseArray buildResponseArray(List<CalculatorService.Response> responses) {
        return CalculatorService.ResponseArray.
                newBuilder().
                addAllResponses(responses).
                build();
    }


    private boolean compare(long first, long second) {
        return first <= second;
    }

}
