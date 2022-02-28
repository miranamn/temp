package com.company;


import doc.TabulatedFunctionDoc;
import functions.*;
import javafx.beans.value.*;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;


public class FXMLMainForm {

    @FXML
    private TextField edX;

    @FXML
    private TextField edY;

    @FXML
    private Label label;

    @FXML
    private TableColumn<FunctionPointT, Double> columnX;

    @FXML
    private TableColumn<FunctionPointT, Double> columnY;

    @FXML
    private TableView<FunctionPointT> table;

    private ObservableList<FunctionPointT> pointsList;
    private Stage stage;
    private TabulatedFunctionDoc tabulatedFunctionDoc = Main.tabFDoc;


    private void setLabelText(int selectedNumber) {
        this.label.setText("Point " + (selectedNumber >= 0 ? selectedNumber + 1: "...") +
                " of " + this.pointsList.size());
    }


    @FXML
    void Addpoint(ActionEvent event) throws InappropriateFunctionPointException {
        int addPointIndex = this.table.getSelectionModel().getSelectedIndex();
        FunctionPoint functionPoint = new FunctionPoint(Double.parseDouble(edX.getText()), Double.parseDouble(edY.getText()));
        Main.tabFDoc.addNodeByIndex(addPointIndex, functionPoint);
    }

    /*@FXML
    void Download(ActionEvent event){

    }*/

    @FXML
    public void Delete(ActionEvent event) throws InappropriateFunctionPointException {
        int deletedPointIndex = this.table.getSelectionModel().getSelectedIndex();
        Main.tabFDoc.deletePoint(deletedPointIndex);
    }

    public void initialize() {
        columnX.setCellValueFactory(new PropertyValueFactory<FunctionPointT, Double>("x"));
        columnY.setCellValueFactory(new PropertyValueFactory<FunctionPointT, Double>("y"));
        ObservableList<FunctionPointT> list = FXCollections.observableArrayList();
        for (int i = 0; i < Main.tabFDoc.getTb().getPointsCount(); i++){
            list.addAll(new FunctionPointT(Main.tabFDoc.getTb().getPointX(i),Main.tabFDoc.getTb().getPointY(i)));
        }
        table.setItems(list);
    }

    public void redraw(){
        pointsList = FXCollections.observableArrayList();
        for(int i = 0; i < Main.tabFDoc.getTb().getPointsCount(); i++) {
            FunctionPointT temp =
                    new FunctionPointT(Main.tabFDoc.getTb().getPointX(i), Main.tabFDoc.getTb().getPointY(i));
            pointsList.add(temp);
        }
        table.setItems(pointsList);
        this.setLabelText(-1);
        table.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<FunctionPointT>() {
                    @Override
                    public void changed(ObservableValue<? extends FunctionPointT> observableValue,
                                        FunctionPointT functionPointT, FunctionPointT t1) {
                        setLabelText(table.getSelectionModel().getSelectedIndex());
                    }
                }
        );
    }

    public void OpenFile(){}
    public void SaveFile(){}
    public void SaveAsFile(){}
    public void Exit(){}
    public void Download(){}
    public void Tabulate(){}


    public void NewFile(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Controller.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Создаём диалоговое окно Stage.
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Edit Person");
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(this.stage);
        dialogStage.setResizable(false);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        // Передаём адресата в контроллер.
        Controller controller = loader.getController();
        controller.setStage(dialogStage);
        int dialogResult = controller.showDialog();


        // Отображаем диалоговое окно и
        //ждём, пока пользователь его не закроет
        //dialogStage.showAndWait();

        if(dialogResult == 1) {
            double newFunctionLeftDomainBorder = controller.tabulatedFunction.getLeftDomainBorder();
            double newFunctionRightDomainBorder = controller.tabulatedFunction.getRightDomainBorder();
            int newFunctionPointsCount = controller.tabulatedFunction.getPointsCount();
            tabulatedFunctionDoc.newFunction(newFunctionLeftDomainBorder, newFunctionRightDomainBorder, newFunctionPointsCount);
            redraw();
        }
    }

    private int showDialog() {
        return 0;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }






    /*public void showNewDocumentWindow(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXMLFunctionParametersDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

// Создаём диалоговое окно Stage.
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Edit Person");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(this.stage);
        dialogStage.setResizable(false);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

// Передаём адресата в контроллер.
        FXMLMainForm controller = loader.getController();
        controller.setStage(dialogStage);
        int dialogResult = controller.showDialog();
// Отображаем диалоговое окно и
        //ждём, пока пользователь его не закроет
//dialogStage.showAndWait();

        if(dialogResult == 1) {
            double newFunctionLeftDomainBorder = controller.tabulatedFunctionDoc.getLeftDomainBorder();
            double newFunctionRightDomainBorder = controller.tabulatedFunctionDoc.getRightDomainBorder();
            int newFunctionPointsCount = controller.tabulatedFunctionDoc.getSize();
//создаем новую функцию...
        }else {
//не делаем ничего...
        }
    }*/
}


