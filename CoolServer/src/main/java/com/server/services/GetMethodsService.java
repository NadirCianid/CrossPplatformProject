package com.server.services;

import com.server.operations.OperationsManager;
import com.test.grpc.CalculatorService;
import com.test.grpc.OperationsGrpc;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetMethodsService extends OperationsGrpc.OperationsImplBase {

    private OperationsManager operationsManager;

    @Override
    public void getOperationNames(CalculatorService.GetOperationsRequest request,
                                  StreamObserver<CalculatorService.GetOperationsResponse> responseObserver) {

        responseObserver.onNext(buildResponse());
        responseObserver.onCompleted();
    }

    private CalculatorService.GetOperationsResponse buildResponse() {
        return CalculatorService.GetOperationsResponse.newBuilder().
                addAllMethod(operationsManager.getOperationNames()).
                build();
    }
}
