package gal.sdc.usc.risk.tablero;

import gal.sdc.usc.risk.menu.Partida;
import gal.sdc.usc.risk.util.Colores.Color;

import java.util.List;

public class Jugador extends Partida {
    private final String nombre;
    private final Color color;
    private Mision mision = null;

    private final Ejercito ejercitosPendientes;

    private Jugador(String nombre, Color color) {
        this.nombre = nombre;
        this.color = color;
        this.ejercitosPendientes = new Ejercito();
    }

    public String getNombre() {
        return nombre;
    }

    public Color getColor() {
        return color;
    }

    public Mision getMision() {
        return mision;
    }

    public List<Pais> getPaises() {
        return super.getMapa().getPaisesPorJugador(this);
    }

    public List<Continente> getContinentes() {
        return super.getMapa().getContinentesPorJugador(this);
    }

    public Ejercito getEjercitosPendientes() {
        return this.ejercitosPendientes;
    }

    public Integer getNumEjercitos() {
        Integer i = 0;
        for (Pais pais : this.getPaises()) {
            i += pais.getEjercito().toInt();
        }
        return i;
    }

    public boolean setMision(Mision mision) {
        if (this.mision == null) {
            this.mision = mision;
            return true;
        }
        return false;
    }

    public static class Builder {
        private String nombre;
        private Color color;

        public Builder(String nombre) {
            this.nombre = nombre;
        }

        public Builder withColor(Color color) {
            this.color = color;
            return this;
        }

        public Jugador build() {
            if (nombre == null) {
                // TODO
            } else if (color == null) {
                // TODO
            } else {
                return new Jugador(nombre, color);
            }
            return null;
        }
    }
}
