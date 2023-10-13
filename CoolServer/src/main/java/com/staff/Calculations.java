package com.staff;

import com.test.grpc.TestServiceGrpc;
import com.test.grpc.TestServiceOuterClass;
import io.grpc.stub.StreamObserver;

public class Calculations extends TestServiceGrpc.TestServiceImplBase {

    @Override
    public void testFunc(TestServiceOuterClass.Params params,
                         StreamObserver<TestServiceOuterClass.Point> pointObserver) {

        //Установка полученных параметров
        double a1 = params.getA1();
        double a2 = params.getA2();
        double a3 = params.getA3();
        double a4 = params.getA4();
        double b = params.getB();
        double x = params.getX();


        double y = a1 * x / a4 + a2 * Math.pow(x,2) + a3 * Math.pow(x,3) + b;

        //Создание объекта точки (ордината)
        TestServiceOuterClass.Point point = TestServiceOuterClass.
                Point.newBuilder().setY(y).build();

        //Отправка точки
        pointObserver.onNext(point);

        pointObserver.onCompleted();
    }
}
