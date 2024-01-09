package com.server.operations.impl;

import com.server.operations.Operation;
import org.springframework.stereotype.Component;

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


}
