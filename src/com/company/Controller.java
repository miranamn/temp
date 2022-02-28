package com.company;

import doc.TabulatedFunctionDoc;
import functions.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;

public class Controller {
    public TabulatedFunctionDoc tabulatedFunction;
    private Stage primaryStage;

    private int OK= 228,  CANCEL = 314;
    private int Status;

    @FXML
    private TextField LeftDomainBorder;

    @FXML
    private Spinner<Integer> PointsCount ;

    @FXML
    private TextField RightDomainBorder;

    @FXML
    void CancelAction(ActionEvent event) {
        Status = CANCEL;
        primaryStage.close();
    }

    SpinnerValueFactory<Integer> Spiner = new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 100, 2);


    @FXML
    void OkAction(ActionEvent event)
    {

        Status = OK;
        tabulatedFunction = new TabulatedFunctionDoc();
        tabulatedFunction.newFunction(Double.parseDouble(LeftDomainBorder.getText()),
                Double.parseDouble(RightDomainBorder.getText()), PointsCount.getValue());
        primaryStage.hide();
    }


    public void setStage(Stage stage){
        this.primaryStage = stage;
    }

    public int showDialog(){
        primaryStage.showAndWait();
        return 1;
    }

    public void initialize(){
        /*stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(primaryStage);
        stage.showAndWait();*/
        PointsCount.setValueFactory(Spiner);

    }

}

