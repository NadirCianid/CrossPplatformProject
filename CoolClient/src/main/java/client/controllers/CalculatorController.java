package client.controllers;

import client.network.NetworkManager;
import client.plot.SurfaceRenderer;
import com.test.grpc.CalculatorGrpc;
import com.test.grpc.CalculatorService;
import com.test.grpc.FormulaGrpc;
import com.test.grpc.OperationsGrpc;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import lombok.var;
import org.jzy3d.maths.Coord3d;

import java.util.*;


public class CalculatorController extends CalculatorGrpc.CalculatorImplBase {

    @FXML
    private TextField startTimeTextField;
    @FXML
    private TextField endTimeTextField;
    @FXML
    private TextField startXTextField;
    @FXML
    private TextField endXTextField;
    @FXML
    private TextField startYTextField;
    @FXML
    private TextField endYTextField;
    @FXML
    private TextField zBoundTextField;

    @FXML
    private Label formulaLabel;

    @FXML
    private Slider timeSlider;

    @FXML
    private Button startButton;

    @FXML
    private Label errorLabel;

    @FXML
    private SubScene chartScene;

    @FXML
    private Pane pane;

    @FXML
    private ComboBox<String> operations;

    private Map<Long, List<Coord3d>> allSeries;

    private static final String RED_COLOR = "-fx-background-color: red;";

    private static final String WHITE_COLOR = "-fx-background-color: white;";


    public void initialize() {
        allSeries = new HashMap<>();
        startButton.setDisable(true);

        errorLabel.setVisible(false);

        initChart();

        addListeners();
    }

    private void initChart() {
        List<Coord3d> points = new ArrayList<>();

        chartScene.setVisible(true);

        SurfaceRenderer.renderSurface(chartScene, pane, points);
    }

    private void addListeners() {
        addTextFieldValidator(startTimeTextField);
        addTextFieldValidator(endTimeTextField);
        addTextFieldValidator(startXTextField);
        addTextFieldValidator(endXTextField);
        addTextFieldValidator(startYTextField);
        addTextFieldValidator(endYTextField);

        timeSlider.valueProperty().addListener((observable -> showChartByTime()));
        zBoundTextField.textProperty().addListener((observable -> showChartByTime()));
        operations.promptTextProperty().addListener((observable -> errorLabel.setVisible(false)));
        operations.setOnShowing(event -> addOperations());
        operations.valueProperty().addListener((observable) -> onOperationPick());
    }

    private void addTextFieldValidator(TextField textField) {
        textField.textProperty().addListener((observable) -> validateTextField(textField));
    }

    @FXML
    private void showChartByTime() {
        Long time = timeSlider.valueProperty().longValue();
        System.out.println(time);
        if (allSeries.get(time) != null) {
            if (zBoundTextField.getText().isEmpty() || !isZTextFieldOk()) {
                SurfaceRenderer.renderSurface(chartScene, pane, allSeries.get(time));
            } else {
                SurfaceRenderer.renderZBoundedSurface(chartScene, pane, allSeries.get(time), Float.parseFloat(zBoundTextField.getText()));
            }
        }
    }

    @FXML
    void sendRequest() throws NumberFormatException { //never thrown
        errorLabel.setVisible(false);

        Thread thread = new Thread(() -> {
            try {
                CalculatorGrpc.CalculatorBlockingStub stub = NetworkManager.buildCalculatorStub();

                CalculatorService.Request request = buildRequest();

                Iterator<CalculatorService.ResponseArray> results = stub.calculate(request);

                Platform.runLater(() -> rebuildSliderByRequest(request));

                while (results.hasNext()) {
                    CalculatorService.ResponseArray responses = results.next();

                    List<Coord3d> points = new ArrayList<>();
                    long key = 0;

                    for (CalculatorService.Response resp : responses.getResponsesList()) {

                        logResponse(resp);

                        points.add(new Coord3d(resp.getX(), resp.getY(), resp.getResult()));
                        key = resp.getTime();

                        Platform.runLater(() -> timeSlider.setMax(resp.getTime()));
                    }

                    long finalKey = key;
                    Platform.runLater(() -> allSeries.put(finalKey, points));
                    Platform.runLater(this::showChartByTime);
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

    private void onOperationPick() {
        validateComboBox(operations);

        if (operations.getValue() != null) {
            getFormula(operations.getValue());
        }
    }

    private void validateComboBox(ComboBox<String> comboBox) {
        if (comboBox.getValue() != null && areRequestParamsOk()) {
            errorLabel.setVisible(false);
            startButton.setDisable(false);
        }
    }

    private void logResponse(CalculatorService.Response response) {
        System.out.printf("X - %d, Y - %d, T - %d, Result = %f\n",
                response.getX(),
                response.getY(),
                response.getTime(),
                response.getResult());
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
        double value=Double.parseDouble(textField.getText());
        if (value<0) {
            textField.setStyle(RED_COLOR);
            errorLabel.setText("Значение не можем быть отрицательным!");
            errorLabel.setVisible(true);

            return false;
        }

        return areRequestParamsOk();
    }

    private void addOperations() {
        try {
            operations.getItems().clear();

            OperationsGrpc.OperationsBlockingStub stub = NetworkManager.buildOperationsStub();

            var operationNames = stub.getOperationNames(CalculatorService.GetOperationsRequest.newBuilder().build());

            operations.getItems().addAll(operationNames.getMethodList());
        } catch (Exception exception) {
            errorLabel.setText("Ошибка соединения с сервером");
            errorLabel.setVisible(true);
        }
    }

    private boolean isZTextFieldOk() {
        zBoundTextField.setStyle(WHITE_COLOR);
        errorLabel.setVisible(false);

        try {
            Float.parseFloat(zBoundTextField.getText());
        } catch (NumberFormatException e) {
            zBoundTextField.setStyle(RED_COLOR);
            errorLabel.setText("Некорректные данные в ограничении на Z!");
            errorLabel.setVisible(true);
            return false;
        }
        return true;
    }

    private void getFormula(String operationName) {
        try {
            formulaLabel.setText("");

            FormulaGrpc.FormulaBlockingStub stub = NetworkManager.buildFormulaStub();

            var formula = stub.getFormula(CalculatorService.GetFormulaRequest.newBuilder().setOperationName(operationName).build());

            formulaLabel.setText(formula.getFormula());
        } catch (Exception e) {
            errorLabel.setText("Ошибка соединения с сервером");
            errorLabel.setVisible(true);
        }
    }

    private boolean areRequestParamsOk() {

        if (!allFieldsNotEmpty()) {
            return false;
        }

        long startTime = Long.parseLong(startTimeTextField.getText());
        long endTime = Long.parseLong(endTimeTextField.getText());

        long startX = Long.parseLong(startXTextField.getText());
        long endX = Long.parseLong(endXTextField.getText());

        long startY = Long.parseLong(startYTextField.getText());
        long endY = Long.parseLong(endYTextField.getText());

        if (startTime >= endTime) {
            errorLabel.setText("Начальное время должно быть меньше конечного!");
            errorLabel.setVisible(true);
            return false;
        }

        if (startX >= endX || startY >= endY) {
            errorLabel.setText("Начальное значение должно быть меньше конечного!");
            errorLabel.setVisible(true);
            return false;
        }

        if (operations.getValue() == null) {
            errorLabel.setText("Выберите операцию в выпадающем списке!");
            errorLabel.setVisible(true);
            return false;
        }

        return true;
    }

    private boolean allFieldsNotEmpty() {
        return !startTimeTextField.getText().isEmpty() &&
                !endTimeTextField.getText().isEmpty() &&
                !startXTextField.getText().isEmpty() &&
                !endXTextField.getText().isEmpty() &&
                !startYTextField.getText().isEmpty() &&
                !endYTextField.getText().isEmpty();
    }

    private CalculatorService.Request buildRequest() throws NumberFormatException {
        long startTime = Long.parseLong(startTimeTextField.getText());
        long endTime = Long.parseLong(endTimeTextField.getText());
        long startX = Long.parseLong(startXTextField.getText());
        long endX = Long.parseLong(endXTextField.getText());
        long startY = Long.parseLong(startYTextField.getText());
        long endY = Long.parseLong(endYTextField.getText());

        String operationName = operations.getValue();

        return CalculatorService.Request.newBuilder().
                setStartT(startTime).
                setEndT(endTime).
                setStartX(startX).
                setEndX(endX).
                setStartY(startY).
                setEndY(endY).
                setMethodName(operationName).
                build();
    }


    private void rebuildSliderByRequest(CalculatorService.Request request) {
        timeSlider.setMin(request.getStartT());
        timeSlider.setBlockIncrement(1.0);
        timeSlider.setMajorTickUnit(1.0);
        timeSlider.setMinorTickCount(0);
        timeSlider.setShowTickLabels(true);
        timeSlider.setSnapToTicks(true);
    }

}