package com.example.calculator;

import com.example.calculator.gui.CalculatorGUI;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class StarterGUI extends Application {
    private final int WIDTH = 618;
    private final int HEIGHT = 396;
    private final String fileName = "calculator-gui.fxml";
    private final String title = "Calculator";
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StarterGUI.class.getResource(fileName));
        Parent root = fxmlLoader.load();
        CalculatorGUI controller = fxmlLoader.getController();
        // The above line MUST be inserted after root is loaded in order the controller of my 
        // app to be instantiated, otherwise we will get a null exception when handler will be invoked


        Scene scene = new Scene(root, WIDTH, HEIGHT);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        // At the start, disable focus (by focusing on parent) and enable the buttons to be pressed by clicking on a key
        // If focus is on any text field, then don't enable buttons to be activated by key.
        root.requestFocus();
        scene.setOnMousePressed(controller::disableFocus);
        scene.setOnKeyReleased(controller::keyPressed);
    }

    public static void main(String[] args) {
        launch();
    }
}