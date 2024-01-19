package com.server.operations.impl;

import com.server.operations.Operation;
import com.test.grpc.CalculatorService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("yaroslav")
public class YaroslavOperation implements Operation {
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
    static private List<List<CalculatorService.Response>> prevMatrix;
    @Override
    public String getOperationName() {
        return "Ярослав";
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
    public void initBorders(long startX, long endX, long startY, long endY,long startT,long endT) {
        this.startX=startX;
        this.endX=endX;
        this.startY=startY;
        this.endY=endY;
        this.startT=startT;
        this.endT=endT;

        n_x=endX/dx;
        n_y=endY/dy;
    }


    @Override
    public List<CalculatorService.Response> magic(long t) {
        this.currentT=t;
        List<List<CalculatorService.Response>> currentMatrix=new ArrayList<>();
        List<CalculatorService.Response> result=new ArrayList<>();



        for(long i=0; i<n_x+1; i++){
            List<CalculatorService.Response> currentRow=new ArrayList<>();
            for(long j=0; j<n_y+1; j++){
                CalculatorService.Response u_i_j=StandartFunction(i,j);
                currentRow.add(u_i_j);
                result.add(u_i_j);
            }
            currentMatrix.add(currentRow);
        }

        prevMatrix=currentMatrix;
        return result;
    }

    private CalculatorService.Response StandartFunction(long i, long j){
        double u=0.0;
        if(currentT==startT){
            u =  Math.sin(Math.PI * i*dx/endX) *
                    Math.sin(Math.PI * j*dy / endY);
            System.out.println(u);
        } else if (j <= 0 || j >= n_y) {
            u=0;
        } else if (i <= 0) {
            u=0;
            //u =this.startXfunction(i,j);
        } else if (i >= n_x) {
            u=0;
            //u=this.endXfunction(i,j);
        } else{
            u=prevMatrix.get((int)i).get((int)j).getResult()
                    +alpha*(prevMatrix.get((int)i+1).get((int)j).getResult()
                    -2*prevMatrix.get((int)i).get((int)j).getResult()
                    +prevMatrix.get((int)i-1).get((int)j).getResult())
                    +alpha*(prevMatrix.get((int)i).get((int)j+1).getResult()
                    -2*prevMatrix.get((int)i).get((int)j).getResult()
                    +prevMatrix.get((int)i).get((int)j-1).getResult());
        }

        return CalculatorService.Response.
                newBuilder().
                setTime(currentT).
                setX(i*dx).
                setY(j*dy).
                setResult(u).
                build();
    }

    private double startXfunction(long i, long j) {
        return 100 * Math.sin(Math.PI*currentT / 10) *
                Math.sin(j*dy/ 0.5);
    }
    private double endXfunction(long i, long j) {
        return 100 * Math.cos(Math.PI*currentT / 10) *
                Math.sin(j*dy/ 0.5);
    }


}
