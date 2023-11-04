package client.controlers;

import client.dto.Point;
import com.test.grpc.CalculatorGrpc;
import com.test.grpc.CalculatorService;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
    public NumberAxis numberAxesX;

    List<Point> points;

    private XYChart.Series<Double, Double> allSeries;
    private XYChart.Series<Double, Double> maxBoundedSeries;

    public void initialize() {
        allSeries = new XYChart.Series<>();
        maxBoundedSeries = new XYChart.Series<>();
        points = new ArrayList<>();

        timeSlider.valueProperty().addListener((observable, oldValue, newValue) -> showMaxValueChart(newValue.doubleValue()));
    }

    public void chartInit() {
        chart.getData().add(maxBoundedSeries);
    }

    private void showMaxValueChart(Double maxValue) {
        System.out.println(maxValue);

        chart.getData().clear();

        maxBoundedSeries.getData().clear();

        for (XYChart.Data<Double, Double> data : allSeries.getData()) {
            if (data.getXValue().compareTo(maxValue) != 1) {
                maxBoundedSeries.getData().add(new XYChart.Data<>(data.getXValue(), data.getYValue()));
            } else {
                numberAxesX.setUpperBound(maxValue + 1.0);
                break;
            }
        }


        chart.getData().add(maxBoundedSeries);
    }

    @FXML
    void sendRequest() {
        startButton.setDisable(true);

        Thread thread = new Thread(() -> {
            try {
                ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:8082").usePlaintext().build();
                CalculatorGrpc.CalculatorBlockingStub stub = CalculatorGrpc.newBlockingStub(channel);

                double startTime = Double.parseDouble(startTimeTextField.getText());
                double endTime = Double.parseDouble(endTimeTextField.getText());
                double step = Double.parseDouble(stepTextField.getText());

                allSeries.getData().clear();

                CalculatorService.Request request = CalculatorService.Request.newBuilder()
                        .setStartTime(startTime)
                        .setEndTime(endTime)
                        .setStep(step)
                        .build();

                Iterator<CalculatorService.Response> results = stub.calculate(request);

                Platform.runLater(() -> rebuildSliderByRequest(request));

                while (results.hasNext()) {
                    CalculatorService.Response response = results.next();

                    System.out.println(response.getTime() + "  " + response.getResult());

                    Platform.runLater(() -> {
                        // Добавление полученной точки на график
                        allSeries.getData().add(new XYChart.Data<>(response.getTime(), response.getResult()));
                    });

                    Platform.runLater(() -> timeSlider.setMax(response.getTime()));
                }

            } catch (NumberFormatException e) {
                System.out.println("Конец дня военной подготовки. До свидания, товарищи студенты!");
            } finally {
                Platform.runLater(() -> startButton.setDisable(false));
            }
        });

        thread.start();
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