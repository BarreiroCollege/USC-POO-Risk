package gal.sdc.usc.risk.gui.componentes.controles;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;

public class ControlesController {
    public JFXButton test;


    public ControlesController() {
    }

    @FXML
    private void initialize() {
        test.setOnAction(e -> System.out.println("sdfjkxn"));
    }
}
