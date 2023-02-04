package com.cnpm.utilities;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Utilities {
    public static void changeScene(ActionEvent event, String fxmlFile, String title, float witdh, float height) throws IOException {
        FXMLLoader loader = new FXMLLoader(Utilities.class.getResource(fxmlFile));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(loader.load(), 600, 450));
        stage.show();
    }
}
