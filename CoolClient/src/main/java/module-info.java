module com.client.coolclient {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.client.coolclient to javafx.fxml;
    exports com.client.coolclient;
}