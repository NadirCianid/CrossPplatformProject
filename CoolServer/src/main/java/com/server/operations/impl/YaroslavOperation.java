package com.server.operations.impl;

import com.server.operations.Operation;
import org.springframework.stereotype.Component;

@Component("yaroslav")
public class YaroslavOperation implements Operation {

    @Override
    public String getOperationName() {
        return "Ярослав";
    }

    @Override
    public String getStringFormula() {
        return "f(x, y, t) = sin ( x + y + t)";
    }

    @Override
    public Double calculate(long x, long y, long t) {
        return Math.sin(x + y + t);
    }
}
