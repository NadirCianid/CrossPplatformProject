package com.server.services;

import com.server.operations.OperationsManager;
import com.test.grpc.CalculatorService;
import com.test.grpc.FormulaGrpc;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetFormulaService extends FormulaGrpc.FormulaImplBase {

    private OperationsManager operationsManager;

    @Override
    public void getFormula(CalculatorService.GetFormulaRequest request,
                           StreamObserver<CalculatorService.GetFormulaResponse> responseObserver) {
        responseObserver.onNext(buildResponse(request.getOperationName()));
        responseObserver.onCompleted();
    }

    private CalculatorService.GetFormulaResponse buildResponse(String operationName) {
        return CalculatorService.GetFormulaResponse.newBuilder().
                setFormula(operationsManager.getFormulaByName(operationName)).
                build();
    }
}
