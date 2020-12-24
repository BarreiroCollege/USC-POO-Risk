package gal.sdc.usc.risk.gui.componentes.info;

import gal.sdc.usc.risk.gui.componentes.Utils;
import gal.sdc.usc.risk.jugar.Partida;
import gal.sdc.usc.risk.tablero.Pais;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class InfoPais extends Partida {
    public static VBox generarDialogo(Pais pais) {
        return new InfoPais().generarPais(pais);
    }

    private InfoPais() {
    }

    private VBox generarPais(Pais pais) {
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

        contenido.getChildren().add(Utils.separadorEntrada());

        fila = new HBox();
        label = new Label("Abreviatura");
        label.getStyleClass().add("dialogo-titulo");
        fila.getChildren().add(label);
        label = new Label(pais.getAbreviatura());
        label.getStyleClass().add("dialogo-valor");
        fila.getChildren().add(label);
        contenido.getChildren().add(fila);

        contenido.getChildren().add(Utils.separadorEntrada());

        fila = new HBox();
        label = new Label("Continente");
        label.getStyleClass().add("dialogo-titulo");
        fila.getChildren().add(label);
        label = Utils.labelColor(pais.getContinente().getNombre(), pais.getContinente().getColor().getHex());
        fila.getChildren().add(label);
        contenido.getChildren().add(fila);

        contenido.getChildren().add(Utils.separadorEntrada());

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
                label = Utils.labelColor(frontera.getNombre(), frontera.getColor().getHex());
                // TODO: Icono para marítimas
                paises.getChildren().add(label);
            }
        }
        fila.getChildren().add(paises);
        contenido.getChildren().add(fila);

        contenido.getChildren().add(Utils.separadorEntrada());

        if (pais.getJugador() != null) {
            fila = new HBox();
            label = new Label("Jugador");
            label.getStyleClass().add("dialogo-titulo");
            fila.getChildren().add(label);
            label = Utils.labelColor(pais.getJugador().getNombre(), pais.getJugador().getColor().getHex());
            fila.getChildren().add(label);
            contenido.getChildren().add(fila);

            contenido.getChildren().add(Utils.separadorEntrada());
        }

        fila = new HBox();
        label = new Label("# Ejércitos");
        label.getStyleClass().add("dialogo-titulo");
        fila.getChildren().add(label);
        label = new Label("" + pais.getEjercito().toInt() + " ejércitos");
        label.getStyleClass().add("dialogo-valor");
        fila.getChildren().add(label);
        contenido.getChildren().add(fila);

        contenido.getChildren().add(Utils.separadorEntrada());

        fila = new HBox();
        label = new Label("# Ocupaciones");
        label.getStyleClass().add("dialogo-titulo");
        fila.getChildren().add(label);
        label = new Label("" + pais.getNumVecesConquistado() + " veces ocupado");
        label.getStyleClass().add("dialogo-valor");
        fila.getChildren().add(label);
        contenido.getChildren().add(fila);

        return contenido;
    }
}
