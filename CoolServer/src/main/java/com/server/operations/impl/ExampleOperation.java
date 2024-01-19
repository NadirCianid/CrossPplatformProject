package com.server.operations.impl;

import com.server.operations.Operation;
import com.test.grpc.CalculatorService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("example")
public class ExampleOperation implements Operation {

    @Override
    public String getOperationName() {
        return "Пример. (Олег и Вася)";
    }

    @Override
    public String getStringFormula() {
        return "f(x, y, t) = cos(x + t) + t * sin(y + x)";
    }

    @Override
    public Double calculate(long x, long y, long t) {
        return Math.cos(x + t) + t * Math.sin(y + x);
    }

    @Override
    public void initBorders(long startX, long endX, long startY, long endY,long startT,long endT) {

    }

    @Override
    public List<CalculatorService.Response> magic(long t) {
        return null;
    }
}
