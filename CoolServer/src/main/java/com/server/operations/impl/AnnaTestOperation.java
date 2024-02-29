package com.server.operations.impl;

import com.server.operations.Operation;
import com.test.grpc.CalculatorService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("anna")
public class AnnaTestOperation implements Operation {
    private long startX=0;
    private long endX=0;
    private long startY=0;
    private long endY=0;
    private long startT=0;
    private long endT=0;
    private long currentT;
    private long dx=1;
    private long dy=1;
    private double alpha=0.1;
    private long n_x=endX/dx;
    private long n_y=endY/dy;

    private int startTemp = 273 + 20;
    private double alpha_shashlyk = 0.02;
    private double alpha_shampur = 0.2;
    private int centerX;
    private int centerY;

    private int shampurR = 2;
    private double h = 100;

    static private List<List<CalculatorService.Response>> previousMatrix;
    @Override
    public String getOperationName() {
        return "Анна";
    }

    @Override
    public String getStringFormula() {
        return "T(x, y, t)";
    }

    @Override
    public Double calculate(long x, long y, long t) {
        return Math.sin(x + y + t);
    }

    @Override
    public void initBorders(long startX, long endX, long startY, long endY,long startT,long endT) {
        this.startX=startX;
        this.endX=endX;
        this.startY=startY;
        this.endY=endY;
        this.startT=startT;
        this.endT=endT;

        n_x=endX/dx;
        n_y=endY/dy;


        centerX = (int) endX/2;
        centerY = (int) endY/2;
    }


    @Override
    public List<CalculatorService.Response> magic(long t) {
        this.currentT=t;
        List<List<CalculatorService.Response>> currentMatrix=new ArrayList<>();
        List<CalculatorService.Response> result=new ArrayList<>();



        for(long i=0; i<n_x+1; i++){
            List<CalculatorService.Response> currentRow=new ArrayList<>();
            for(long j=0; j<n_y+1; j++){
                CalculatorService.Response u_i_j= calculateTemperature(i,j);
                currentRow.add(u_i_j);
                if(t % 10 == 0) {
                    result.add(u_i_j);
                }

            }
            currentMatrix.add(currentRow);
        }

        previousMatrix =currentMatrix;
        return result;
    }

    private CalculatorService.Response calculateTemperature(long i, long j){
        double T;

        if (currentT == startT) {
            T = startTemp;
        } else if (j <= 0) {
            T =  previousMatrix.get((int) i).get(0).getResult() +
                    2 * 1 *( alpha_shashlyk * (previousMatrix.get((int) i).get(1).getResult() - previousMatrix.get((int) i).get(0).getResult())/ (dy * dy) +
                            (startTemp - previousMatrix.get((int) i).get(0).getResult()) / h);

        } else if(j >= n_y-1) {
            T =  previousMatrix.get((int) i).get((int) endY - 1).getResult() +
                    2 * 1 *( alpha_shashlyk * (previousMatrix.get((int) i).get((int) endY - 2).getResult() - previousMatrix.get((int) i).get((int) endY - 1).getResult())/ (dy * dy) +
                            (startTemp - previousMatrix.get((int) i).get((int) endY - 1).getResult()) / h);

        } else if (i >= n_x-1) {
            T =  previousMatrix.get((int) endX - 1).get((int) j).getResult() +
                    2 * 1 *( alpha_shashlyk * (previousMatrix.get((int) endX - 2).get((int) j).getResult() - previousMatrix.get((int) endX - 1).get((int) j).getResult())/ (dx * dx) +
                            (startTemp - previousMatrix.get((int) endX - 1).get((int) j).getResult()) / h);

        } else if(i <= 0) {
            T =  previousMatrix.get(0).get((int) j).getResult() +
                    2 * 1 *( alpha_shashlyk * (previousMatrix.get(1).get((int) j).getResult() - previousMatrix.get(0).get((int) j).getResult())/ (dx * dx) +
                            (startTemp + 100 - previousMatrix.get(0).get((int) j).getResult()) / h);

        } else if((i >= centerX - shampurR && i <= centerX + shampurR) && ( j >= centerY - shampurR && j <= centerY + shampurR)) {
            T = calculateT((int) i, (int) j, alpha_shampur);
        } else {
            T = calculateT((int) i, (int) j, alpha_shashlyk);
        }

        return CalculatorService.Response.
                newBuilder().
                setTime(currentT/10).
                setX(i*dx).
                setY(j*dy).
                setResult(T).
                build();
    }

    private double calculateT(int i, int j, double alpha) {
        double T;
        T = previousMatrix.get(i).get(j).getResult() +
                1 * alpha * ((previousMatrix.get(i + 1).get(j).getResult()
                        - 2 * previousMatrix.get(i).get(j).getResult()
                        + previousMatrix.get(i - 1).get(j).getResult()) / (dx * dx)

                        + (previousMatrix.get(i).get(j + 1).getResult()
                        - 2 * previousMatrix.get(i).get(j).getResult()
                        + previousMatrix.get(i).get(j - 1).getResult()) / (dy * dy));
        return T;
    }


}
