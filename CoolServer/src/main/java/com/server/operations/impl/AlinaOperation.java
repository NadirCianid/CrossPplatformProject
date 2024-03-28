package com.server.operations.impl;

import com.server.operations.Operation;
import com.test.grpc.CalculatorService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("alina")
public class AlinaOperation implements Operation {
    private long startX = 0;
    private long endX = 0;
    private long startY = 0;
    private long endY = 0;
    private long startT = 0;
    private long endT = 0;
    private double dx = 1;
    private double dy = 1;

    //Скорости переноса по осям
    private double c = 0.1;
    private double d = 0.1;

    private double Q = 1.0;
    private double alpha = 0.25;
    private double sigma = 1.0;
    private double x1 = 3.0;
    private double y1 = 5.0;
    private double x2 = 7.0;
    private double y2 = 5.0;

    private long n_x = (long) (endX / dx);
    private long n_y = (long) (endY / dy);
    static private List<List<CalculatorService.Response>> previousMatrix;

    @Override
    public String getOperationName() {
        return "Алина";
    }

    @Override
    public String getStringFormula() {
        return "u(x, y, t)";
    }

    @Override
    public Double calculate(long x, long y, long t) {
        return Math.sin(x + y + t);
    }

    @Override
    public void initBorders(long startX, long endX, long startY, long endY, long startT, long endT) {
        this.startX = startX;
        this.endX = endX;
        this.startY = startY;
        this.endY = endY;
        this.startT = startT;
        this.endT = endT;

        n_x = (long) (endX / dx);
        n_y = (long) (endY / dy);
    }


    @Override
    public List<CalculatorService.Response> magic(long t) {
        List<List<CalculatorService.Response>> currentMatrix = new ArrayList<>();
        List<CalculatorService.Response> result = new ArrayList<>();


        for (long i = 0; i < n_x + 1; i++) {
            List<CalculatorService.Response> currentRow = new ArrayList<>();
            for (long j = 0; j < n_y + 1; j++) {
                CalculatorService.Response c_i_j = StandartFunction(i, j, t);
                currentRow.add(c_i_j);
                result.add(c_i_j);
            }
            currentMatrix.add(currentRow);
        }

        previousMatrix = currentMatrix;
        return result;
    }

    private CalculatorService.Response StandartFunction(long i, long j, long currentT) {
        double u = 0.0;

        if (currentT == startT) {
            u = Math.sin(2*Math.PI*dx*i)*Math.sin(2*Math.PI*dy*j);
        } else if (j <= 0 || j >= endY) {
            u = 1;
        } else if (i <= 0 || i >= endX) {
            u = 1;
        } else {
            u = previousMatrix.get((int) i).get((int) j).getResult()

                    +0.1*( c * ((previousMatrix.get((int) i + 1).get((int) j).getResult()
                    - 2 * previousMatrix.get((int) i).get((int) j).getResult()
                    + previousMatrix.get((int) i - 1).get((int) j).getResult()) / (dx * dx)

                    +  d * (previousMatrix.get((int) i).get((int) j + 1).getResult()
                    - 2 * previousMatrix.get((int) i).get((int) j).getResult()
                    + previousMatrix.get((int) i).get((int) j - 1).getResult()) / (dy * dy) ));
        }


        return CalculatorService.Response.
                newBuilder().
                setTime(currentT).
                setX((long) (i * dx)).
                setY((long) (j * dy)).
                setResult(u).
                build();
    }

}
