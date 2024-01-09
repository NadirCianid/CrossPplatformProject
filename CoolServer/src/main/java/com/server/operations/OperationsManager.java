package com.server.operations;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OperationsManager {
    private List<Operation> operations;

    public Operation getOperationByName(String operationName) {
        return operations.stream().
                filter(operation -> operation.getOperationName().equals(operationName)).
                findAny().
                orElse(null);
    }

    public List<String> getOperationNames() {
        return operations.stream().
                map(Operation::getOperationName).
                collect(Collectors.toList());
    }

    public String getFormulaByName(String operationName) {
        Operation needOperation = operations.stream().
                filter(operation -> operation.getOperationName().equals(operationName)).
                findAny().
                orElse(null);

        return needOperation == null ? "" : needOperation.getStringFormula();
    }
}
