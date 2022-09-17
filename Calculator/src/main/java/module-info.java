module com.example.calculator {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.calculator to javafx.fxml;
    exports com.example.calculator;
    exports com.example.calculator.gui;
    opens com.example.calculator.gui to javafx.fxml;
    exports com.example.calculator.gui.enums;
    opens com.example.calculator.gui.enums to javafx.fxml;
}