package gal.sdc.usc.risk.gui.componentes;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class Utils {
    public static Label labelColor(String valor, String hex) {
        Label label = new Label(" " + valor + " ");
        label.getStyleClass().add("dialogo-valor");
        label.setStyle(label.getStyle() + "-fx-text-fill: " + hex + "; "
                + "-fx-border-width: 1; "
                + "-fx-border-radius: 5; "
                + "-fx-border-color: " + hex + "; ");
        return label;
    }

    public static HBox separadorEntrada() {
        HBox sep = new HBox();
        sep.setPrefHeight(8);
        return sep;
    }
}
