package gal.sdc.usc.risk.gui.componentes;

import gal.sdc.usc.risk.gui.PrincipalController;
import gal.sdc.usc.risk.gui.componentes.controles.ControlesController;
import gal.sdc.usc.risk.gui.componentes.mapa.MapaController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.IOException;

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

    public static void actualizar() {
        Platform.runLater(Utils::actualizarTodo);
    }

    public static void actualizarTodo() {
        final Scene scene = PrincipalController.getScene();

        FXMLLoader loader = new FXMLLoader();
        try {
            loader.load(MapaController.class.getResource("mapa.fxml").openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        ((MapaController) loader.getController()).actualizar(scene);

        loader = new FXMLLoader();
        try {
            loader.load(ControlesController.class.getResource("vertical.fxml").openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        ((ControlesController) loader.getController()).actualizarJugador(scene);
        ((ControlesController) loader.getController()).actualizarComandos(scene);
    }
}
