package com.server.operations;

import com.test.grpc.CalculatorService;

import java.util.List;

public interface Operation {
    String getOperationName();

    String getStringFormula();

    Double calculate(long x, long y, long t);

    void initBorders(long startX, long endX, long startY, long endY, long startT,long endT);

    List<CalculatorService.Response> magic(long t);
}
