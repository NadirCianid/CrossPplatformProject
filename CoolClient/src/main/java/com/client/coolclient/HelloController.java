package com.client.coolclient;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;

import java.text.ParseException;

public class HelloController {
    private double[] fValues = new double[11];

    @FXML
    private TextField a1TextField;

    @FXML
    private TextField a2TextField;

    @FXML
    private TextField a3TextField;

    @FXML
    private TextField a4TextField;

    @FXML
    private Slider bSlider;

    @FXML
    private StackedAreaChart<Integer, Double> chart;
    private XYChart.Series<Integer, Double> series = new XYChart.Series();

    public void chartInit() {
        chart.getData().add(series);
        series.setName("Some values");
    }

    @FXML
    void showChart(ActionEvent event) {
        double a1;
        double a2;
        double a3;
        double a4;
        double b;

        try {
            a1 = Double.parseDouble(a1TextField.getText());
            a2 = Double.parseDouble(a2TextField.getText());
            a3 = Double.parseDouble(a3TextField.getText());
            a4 = Double.parseDouble(a4TextField.getText());
            b = bSlider.getValue();

            for(int i = 0; i <= 10; i++) {
                fValues[i] = a1 * i / a4 + a2 * Math.pow(i,2) + a3 * Math.pow(i,3) + b;
            }

            series.getData().clear();
            for(int i = 0; i <= 10; i++) {
                series.getData().add(new XYChart.Data<Integer, Double>(i, fValues[i]));
            }


        } catch (NumberFormatException e) {
            showParseError();
        }


    }


    private void showParseError() {
        a1TextField.clear();
        a1TextField.setText("Incorrect value. Enter double value.");

        a2TextField.clear();
        a2TextField.setText("Incorrect value. Enter double value.");

        a3TextField.clear();
        a3TextField.setText("Incorrect value. Enter double value.");

        a4TextField.clear();
        a4TextField.setText("Incorrect value. Enter double value.");
    }

}