package gal.sdc.usc.risk.gui.componentes.info;

import gal.sdc.usc.risk.gui.componentes.Utils;
import gal.sdc.usc.risk.jugar.Partida;
import gal.sdc.usc.risk.tablero.Carta;
import gal.sdc.usc.risk.tablero.Continente;
import gal.sdc.usc.risk.tablero.Jugador;
import gal.sdc.usc.risk.tablero.Pais;
import gal.sdc.usc.risk.util.Colores;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class InfoJugador extends Partida {
    private Jugador jugador;

    public static VBox generarJugadorCorto(Jugador jugador) {
        return new InfoJugador().generar(jugador);
    }

    public static VBox generarJugador(Pais pais) {
        return generarJugador(pais.getJugador(), pais);
    }

    public static VBox generarJugador(Jugador jugador) {
        return generarJugador(jugador, null);
    }

    public static VBox generarJugador(Jugador jugador, Pais pais) {
        return new InfoJugador().generar(jugador, pais);
    }

    private InfoJugador() {
    }

    private HBox nombre() {
        Label label;
        HBox fila = new HBox();
        label = new Label("Nombre");
        label.getStyleClass().add("dialogo-titulo");
        fila.getChildren().add(label);
        label = Utils.labelColor(jugador.getNombre(), jugador.getColor().getHex());
        fila.getChildren().add(label);
        return fila;
    }

    private HBox mision() {
        Label label;
        if (super.getJugadorTurno().equals(jugador) && jugador.getMision() != null) {
            HBox fila = new HBox();
            label = new Label("Misión " + jugador.getMision().getCodigo());
            label.getStyleClass().add("dialogo-titulo");
            fila.getChildren().add(label);
            label = new Label(jugador.getMision().getDescripcion());
            label.getStyleClass().add("dialogo-valor");
            label.setWrapText(true);
            fila.getChildren().add(label);
            return fila;
        }
        return null;
    }

    private HBox numEjercitos() {
        Label label;
        HBox fila = new HBox();
        label = new Label("# Ejércitos");
        label.getStyleClass().add("dialogo-titulo");
        fila.getChildren().add(label);
        label = new Label("" + jugador.getNumEjercitos() + " ejército" + (jugador.getNumEjercitos() != 1 ? "s" : "") + " en total");
        label.getStyleClass().add("dialogo-valor");
        fila.getChildren().add(label);
        return fila;
    }

    private HBox cartas() {
        HBox fila = new HBox();
        Label label = new Label("Cartas");
        label.getStyleClass().add("dialogo-titulo");
        fila.getChildren().add(label);
        VBox valores = new VBox();
        for (Carta carta : jugador.getCartas()) {
            valores.getChildren().add(Utils.labelColor(carta.getNombre(), Colores.Color.NEGRO.getHex()));
        }
        fila.getChildren().add(valores);
        if (valores.getChildren().size() > 0) {
            return fila;
        }
        return null;
    }

    private HBox rearme() {
        Label label;
        HBox fila = new HBox();
        label = new Label("Rearme");
        label.getStyleClass().add("dialogo-titulo");
        fila.getChildren().add(label);
        label = new Label("" + jugador.getEjercitosPendientes().toInt() + " ejército" + (jugador.getEjercitosPendientes().toInt() != 1 ? "s" : "") + " pendientes de asignar");
        label.getStyleClass().add("dialogo-valor");
        fila.getChildren().add(label);
        return fila;
    }

    private VBox generar(Jugador jugador) {
        this.jugador = jugador;

        VBox contenido = new VBox();
        contenido.getStyleClass().add("dialogo-contenido");

        if (this.mision() != null) {
            contenido.getChildren().add(this.mision());
            contenido.getChildren().add(Utils.separadorEntrada());
        }

        contenido.getChildren().add(this.numEjercitos());
        contenido.getChildren().add(Utils.separadorEntrada());

        if (this.cartas() != null) {
            contenido.getChildren().add(this.cartas());
            contenido.getChildren().add(Utils.separadorEntrada());
        }

        contenido.getChildren().add(this.rearme());

        return contenido;
    }

    private VBox generar(Jugador jugador, Pais pais) {
        this.jugador = jugador;

        VBox contenido = new VBox();
        contenido.getStyleClass().add("dialogo-contenido");

        HBox fila;
        Label label;
        VBox valores;

        contenido.getChildren().add(this.nombre());
        contenido.getChildren().add(Utils.separadorEntrada());

        if (this.mision() != null) {
            contenido.getChildren().add(this.mision());
            contenido.getChildren().add(Utils.separadorEntrada());
        }

        contenido.getChildren().add(this.numEjercitos());
        contenido.getChildren().add(Utils.separadorEntrada());

        fila = new HBox();
        label = new Label("Países");
        label.getStyleClass().add("dialogo-titulo");
        fila.getChildren().add(label);
        valores = new VBox();
        if (pais != null && jugador.equals(pais.getJugador())) {
            label = new Label(pais.getNombre());
            label.getStyleClass().add("dialogo-valor");
            label.setStyle(label.getStyle() + "-fx-font-weight: bold;");
            valores.getChildren().add(label);
        }
        for (Pais pais2 : jugador.getPaises()) {
            if (pais == null || pais2.equals(pais) || !pais2.getContinente().equals(pais.getContinente())) continue;
            label = new Label(pais2.getNombre());
            label.getStyleClass().add("dialogo-valor");
            valores.getChildren().add(label);
        }
        for (Pais pais2 : jugador.getPaises()) {
            if (pais != null && pais2.getContinente().equals(pais.getContinente())) continue;
            valores.getChildren().add(Utils.labelColor(pais2.getNombre(), pais2.getColor().getHex()));
        }
        fila.getChildren().add(valores);
        if (valores.getChildren().size() > 0) {
            contenido.getChildren().add(fila);
            contenido.getChildren().add(Utils.separadorEntrada());
        }

        fila = new HBox();
        label = new Label("Continentes");
        label.getStyleClass().add("dialogo-titulo");
        fila.getChildren().add(label);
        valores = new VBox();
        if (pais != null && jugador.equals(pais.getContinente().getJugador())) {
            label = new Label(pais.getContinente().getNombre());
            label.getStyleClass().add("dialogo-valor");
            label.setStyle(label.getStyle() + "-fx-font-weight: bold;");
            valores.getChildren().add(label);
        }
        for (Continente continente2 : jugador.getContinentes()) {
            if (pais != null && continente2.equals(pais.getContinente())) continue;
            valores.getChildren().add(Utils.labelColor(continente2.getNombre(), continente2.getColor().getHex()));
        }
        fila.getChildren().add(valores);
        if (valores.getChildren().size() > 0) {
            contenido.getChildren().add(fila);
            contenido.getChildren().add(Utils.separadorEntrada());
        }

        if (this.cartas() != null) {
            contenido.getChildren().add(this.cartas());
            contenido.getChildren().add(Utils.separadorEntrada());
        }

        contenido.getChildren().add(this.rearme());

        return contenido;
    }
}
