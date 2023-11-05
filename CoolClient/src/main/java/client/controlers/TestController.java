package client.controlers;

import client.dto.Point;
import client.network.NetworkManager;
import com.test.grpc.CalculatorGrpc;
import com.test.grpc.CalculatorService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//TODO : рефакторинг методов проверок, там говно, но работает

public class TestController extends CalculatorGrpc.CalculatorImplBase {

    @FXML
    private TextField startTimeTextField;

    @FXML
    private TextField endTimeTextField;

    @FXML
    private TextField stepTextField;

    @FXML
    private Slider timeSlider;

    @FXML
    private StackedAreaChart<Double, Double> chart;

    @FXML
    private Button startButton;

    @FXML
    private NumberAxis numberAxesX;

    @FXML
    private Label errorLabel;

    List<Point> points;

    private XYChart.Series<Double, Double> allSeries;
    private XYChart.Series<Double, Double> maxBoundedSeries;

    private static final String RED_COLOR = "-fx-background-color: red;";

    private static final String WHITE_COLOR = "-fx-background-color: white;";

    public void initialize() {
        allSeries = new XYChart.Series<>();
        maxBoundedSeries = new XYChart.Series<>();
        points = new ArrayList<>();
        startButton.setDisable(true);

        errorLabel.setVisible(false);
        addListeners();
    }

    private void addListeners() {
        timeSlider.valueProperty().addListener((observable, oldValue, newValue) -> showMaxValueChart(newValue.doubleValue()));

        startTimeTextField.textProperty().addListener((observable) -> validateTextField(startTimeTextField));
        endTimeTextField.textProperty().addListener((observable -> validateTextField(endTimeTextField)));
        stepTextField.textProperty().addListener((observable) -> validateTextField(stepTextField));
    }

    public void chartInit() {
        chart.getData().add(maxBoundedSeries);
    }

    private void showMaxValueChart(Double maxValue) {
        System.out.println(maxValue);

        chart.getData().clear();

        maxBoundedSeries.getData().clear();

        numberAxesX.setUpperBound(maxValue + 1.0);

        for (XYChart.Data<Double, Double> data : allSeries.getData()) {
            if (data.getXValue() <= maxValue) {
                maxBoundedSeries.getData().add(new XYChart.Data<>(data.getXValue(), data.getYValue()));
            } else {
                break;
            }
        }

        chart.getData().add(maxBoundedSeries);
    }

    @FXML
    void sendRequest() throws NumberFormatException { //never thrown
        errorLabel.setVisible(false);

        Thread thread = new Thread(() -> {
            try {
                CalculatorGrpc.CalculatorBlockingStub stub = NetworkManager.buildStub();

                CalculatorService.Request request = buildRequest();

                allSeries.getData().clear();

                Iterator<CalculatorService.Response> results = stub.calculate(request);

                Platform.runLater(() -> {
                    rebuildSliderByRequest(request);
                    numberAxesX.setLowerBound(request.getStartTime());
                });

                while (results.hasNext()) {
                    CalculatorService.Response response = results.next();

                    System.out.println(response.getTime() + "  " + response.getResult());

                    Platform.runLater(() -> allSeries.getData().add(new XYChart.Data<>(response.getTime(), response.getResult())));

                    Platform.runLater(() -> timeSlider.setMax(response.getTime()));
                }

            } catch (Exception e) {
                Platform.runLater(() -> {
                    errorLabel.setText("Ошибка соединения с сервером");
                    errorLabel.setVisible(true);
                });
            } finally {
                Platform.runLater(() -> startButton.setDisable(false));
            }
        });

        thread.start();
    }

    private void validateTextField(TextField textField) {
        if (isTextFieldOk(textField)) {
            startButton.setDisable(false);
        }
    }


    private boolean isTextFieldOk(TextField textField) {
        startButton.setDisable(true);
        textField.setStyle(WHITE_COLOR);
        errorLabel.setVisible(false);

        if (textField.getText().isEmpty()) {
            textField.setStyle(RED_COLOR);
            errorLabel.setText("Поле не можем быть пустым!");
            errorLabel.setVisible(true);

            return false;
        }

        try {
            Double.parseDouble(textField.getText());
        } catch (NumberFormatException e) {
            textField.setStyle(RED_COLOR);
            errorLabel.setText("Некорректные данные!");
            errorLabel.setVisible(true);

            return false;
        }

        return areRequestParamsOk();
    }

    private boolean areRequestParamsOk() {

        if (startTimeTextField.getText().isEmpty() || endTimeTextField.getText().isEmpty() || stepTextField.getText().isEmpty()) {
            return false;
        }

        double startTime = Double.parseDouble(startTimeTextField.getText());
        double endTime = Double.parseDouble(endTimeTextField.getText());
        double step = Double.parseDouble(stepTextField.getText());

        if (startTime >= endTime) {
            errorLabel.setText("Начальное время должно быть меньше конечного!");
            errorLabel.setVisible(true);
            return false;
        }
        if (step <= 0) {
            errorLabel.setText("Шаг должен быть положительным!");
            errorLabel.setVisible(true);
            return false;
        }

        return true;
    }


    private CalculatorService.Request buildRequest() throws NumberFormatException {
        double startTime = Double.parseDouble(startTimeTextField.getText());
        double endTime = Double.parseDouble(endTimeTextField.getText());
        double step = Double.parseDouble(stepTextField.getText());

        return CalculatorService.Request.newBuilder()
                .setStartTime(startTime)
                .setEndTime(endTime)
                .setStep(step)
                .build();
    }


    private void rebuildSliderByRequest(CalculatorService.Request request) {
        timeSlider.setMin(request.getStartTime());
        timeSlider.setBlockIncrement(request.getStep());
        timeSlider.setMajorTickUnit(request.getStep());
        timeSlider.setMinorTickCount(0);
        timeSlider.setShowTickLabels(true);
        timeSlider.setSnapToTicks(true);
    }

}