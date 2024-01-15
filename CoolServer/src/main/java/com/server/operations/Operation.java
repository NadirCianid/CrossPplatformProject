package com.server.operations;

public interface Operation {
    String getOperationName();

    String getStringFormula();

    Double calculate(long x, long y, long t);

}
