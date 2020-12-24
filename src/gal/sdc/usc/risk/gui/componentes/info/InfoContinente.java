package gal.sdc.usc.risk.gui.componentes.info;

import gal.sdc.usc.risk.gui.componentes.Utils;
import gal.sdc.usc.risk.tablero.Continente;
import gal.sdc.usc.risk.tablero.Jugador;
import gal.sdc.usc.risk.tablero.Pais;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;

public class InfoContinente {
    public static VBox generarContinente(Continente continente) {
        return new InfoContinente().generar(continente);
    }

    private InfoContinente() {
    }

    private VBox generar(Continente continente) {
        VBox contenido = new VBox();
        contenido.getStyleClass().add("dialogo-contenido");

        HBox fila;
        Label label;

        fila = new HBox();
        label = new Label("Nombre");
        label.getStyleClass().add("dialogo-titulo");
        fila.getChildren().add(label);
        label = Utils.labelColor(continente.getNombre(), continente.getColor().getHex());
        fila.getChildren().add(label);
        contenido.getChildren().add(fila);

        contenido.getChildren().add(Utils.separadorEntrada());

        fila = new HBox();
        label = new Label("Abreviatura");
        label.getStyleClass().add("dialogo-titulo");
        fila.getChildren().add(label);
        label = new Label(continente.getAbreviatura());
        label.getStyleClass().add("dialogo-valor");
        fila.getChildren().add(label);
        contenido.getChildren().add(fila);

        contenido.getChildren().add(Utils.separadorEntrada());

        fila = new HBox();
        label = new Label("Jugadores");
        label.getStyleClass().add("dialogo-titulo");
        fila.getChildren().add(label);
        VBox valores = new VBox();
        HashMap<Jugador, Integer> jugadoresEjercitos = new HashMap<>();
        for (Pais pais : continente.getPaises().values()) {
            if (pais.getJugador() == null) continue;
            jugadoresEjercitos.putIfAbsent(pais.getJugador(), 0);
            jugadoresEjercitos.put(pais.getJugador(), jugadoresEjercitos.get(pais.getJugador()) + pais.getEjercito().toInt());
        }
        for (Map.Entry<Jugador, Integer> jugador : jugadoresEjercitos.entrySet()) {
            valores.getChildren().add(Utils.labelColor(jugador.getKey().getNombre() + ": " + jugador.getValue() + " ejércitos", jugador.getKey().getColor().getHex()));
        }
        fila.getChildren().add(valores);
        if (valores.getChildren().size() > 0) {
            contenido.getChildren().add(fila);
            contenido.getChildren().add(Utils.separadorEntrada());
        }

        fila = new HBox();
        label = new Label("# Ejércitos");
        label.getStyleClass().add("dialogo-titulo");
        fila.getChildren().add(label);
        label = new Label("" + continente.getNumEjercitos() + " ejército" + (continente.getNumEjercitos() != 1 ? "s" : "") + " en total");
        label.getStyleClass().add("dialogo-valor");
        fila.getChildren().add(label);
        contenido.getChildren().add(fila);

        contenido.getChildren().add(Utils.separadorEntrada());

        fila = new HBox();
        label = new Label("Rearme");
        label.getStyleClass().add("dialogo-titulo");
        fila.getChildren().add(label);
        label = new Label("" + continente.getEjercitosRearme() + " ejércitos por turno");
        label.getStyleClass().add("dialogo-valor");
        fila.getChildren().add(label);
        contenido.getChildren().add(fila);

        return contenido;
    }
}
