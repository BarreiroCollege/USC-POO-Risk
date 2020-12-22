package gal.sdc.usc.risk.gui.componentes.infopais;

import com.jfoenix.controls.JFXBadge;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbarLayout;
import com.jfoenix.controls.JFXTabPane;
import gal.sdc.usc.risk.tablero.Pais;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class InfoPais {
    public static JFXDialog dialogo(StackPane stackPane, Pais pais) {
        JFXDialog dialog = new JFXDialog();
        dialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
        dialog.setDialogContainer(stackPane);

        JFXSnackbar bar = new JFXSnackbar(stackPane);
        bar.fireEvent(new JFXSnackbar.SnackbarEvent(new JFXSnackbarLayout("describir pais " + pais.getAbreviatura())));

        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Label(pais.getNombre()));

        JFXTabPane tabs = new JFXTabPane();
        Tab tabPais = generarPais(pais);
        tabs.getTabs().add(tabPais);

        Tab tabContinente = new Tab("Continente");
        tabs.getTabs().add(tabContinente);

        if (pais.getJugador() != null) {
            Tab tabJugador = new Tab("Jugador");
            tabs.getTabs().add(tabJugador);
        }

        content.setBody(tabs);

        JFXButton cerrar = new JFXButton("Cerrar");
        cerrar.setOnAction(event -> dialog.close());
        content.setActions(cerrar);

        dialog.setContent(content);
        return dialog;
    }

    private static Tab generarPais(Pais pais) {
        Tab tab = new Tab("País");
        VBox contenido = new VBox();
        contenido.getStyleClass().add("dialogo-contenido");

        HBox fila;
        Label label;

        fila = new HBox();
        label = new Label("Nombre");
        label.getStyleClass().add("dialogo-titulo");
        fila.getChildren().add(label);
        label = new Label(pais.getNombre());
        label.getStyleClass().add("dialogo-valor");
        fila.getChildren().add(label);
        contenido.getChildren().add(fila);

        contenido.getChildren().add(separadorEntrada());

        fila = new HBox();
        label = new Label("Abreviatura");
        label.getStyleClass().add("dialogo-titulo");
        fila.getChildren().add(label);
        label = new Label(pais.getAbreviatura());
        label.getStyleClass().add("dialogo-valor");
        fila.getChildren().add(label);
        contenido.getChildren().add(fila);

        contenido.getChildren().add(separadorEntrada());

        fila = new HBox();
        label = new Label("Continente");
        label.getStyleClass().add("dialogo-titulo");
        fila.getChildren().add(label);
        label = labelColor(pais.getContinente().getNombre(), pais.getContinente().getColor().getHex());
        fila.getChildren().add(label);
        contenido.getChildren().add(fila);

        contenido.getChildren().add(separadorEntrada());

        fila = new HBox();
        label = new Label("Fronteras");
        label.getStyleClass().add("dialogo-titulo");
        fila.getChildren().add(label);
        VBox paises = new VBox();
        for (Pais frontera : pais.getFronteras().getTodas()) {
            if (frontera.getContinente().equals(pais.getContinente())) {
                label = new Label(frontera.getNombre());
                label.getStyleClass().add("dialogo-valor");
                // TODO: Icono para marítimas
                paises.getChildren().add(label);
            }
        }
        for (Pais frontera : pais.getFronteras().getTodas()) {
            if (!frontera.getContinente().equals(pais.getContinente())) {
                label = labelColor(frontera.getNombre(), frontera.getColor().getHex());
                // TODO: Icono para marítimas
                paises.getChildren().add(label);
            }
        }
        fila.getChildren().add(paises);
        contenido.getChildren().add(fila);

        contenido.getChildren().add(separadorEntrada());

        if (pais.getJugador() != null) {
            fila = new HBox();
            label = new Label("Jugador");
            label.getStyleClass().add("dialogo-titulo");
            fila.getChildren().add(label);
            label = labelColor(pais.getJugador().getNombre(), pais.getJugador().getColor().getHex());
            fila.getChildren().add(label);
            contenido.getChildren().add(fila);

            contenido.getChildren().add(separadorEntrada());
        }

        fila = new HBox();
        label = new Label("Ejércitos");
        label.getStyleClass().add("dialogo-titulo");
        fila.getChildren().add(label);
        label = new Label("" + pais.getEjercito().toInt());
        label.getStyleClass().add("dialogo-valor");
        fila.getChildren().add(label);
        contenido.getChildren().add(fila);

        contenido.getChildren().add(separadorEntrada());

        fila = new HBox();
        label = new Label("# Ocupaciones");
        label.getStyleClass().add("dialogo-titulo");
        fila.getChildren().add(label);
        label = new Label("" + pais.getNumVecesConquistado());
        label.getStyleClass().add("dialogo-valor");
        fila.getChildren().add(label);
        contenido.getChildren().add(fila);

        tab.setContent(contenido);
        return tab;
    }

    private static Label labelColor(String valor, String hex) {
        Label label = new Label(" " + valor + " ");
        label.setStyle(label.getStyle() + "-fx-text-fill: " + hex + ";" +
                "-fx-border-width: 1; -fx-border-radius: 5; -fx-border-color: " + hex + ";");
        label.getStyleClass().add("dialogo-valor");
        return label;
    }

    private static HBox separadorEntrada() {
        HBox sep = new HBox();
        sep.setPrefHeight(8);
        return sep;
    }
}
