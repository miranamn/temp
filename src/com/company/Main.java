package com.company;

import doc.TabulatedFunctionDoc;
import functions.*;

import javafx.application.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;

public class Main extends Application {

    public static TabulatedFunctionDoc tabFDoc;

    public static void main(String[] args) {
        tabFDoc = new TabulatedFunctionDoc();
        tabFDoc.newFunction(0, 10, 11);
        launch(args);
    }

    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLMainForm.fxml"));
        Parent root = loader.load();
        FXMLMainForm ctrl = loader.getController();
        tabFDoc.registerRedrawFunctionController(ctrl);
        Scene scene = new Scene(root);
        stage.setTitle("Tabulated Function");
        stage.setScene(scene);
        stage.show();
    }


}





