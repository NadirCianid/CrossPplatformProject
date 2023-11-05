package client;


import client.controlers.TestController;
import client.network.NetworkManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ClientApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        //TODO : путь относительно проекта
        URL url = new File("D:\\STUDY\\unic\\Hlopin\\CrossPlatformProject\\CoolClient\\src\\main\\resources\\test.fxml").toURI().toURL();

        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);

        stage.setOnCloseRequest(windowEvent -> NetworkManager.shutdownChannel());


        ((TestController) fxmlLoader.getController()).chartInit();
    }

    public static void main(String[] args) {
        launch();
    }

}