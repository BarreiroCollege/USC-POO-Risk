package gal.sdc.usc.risk.tablero;

import gal.sdc.usc.risk.util.Colores.Color;

public class Jugador {
    private final String nombre;
    private final Color color;

    private Jugador(String nombre, Color color) {
        this.nombre = nombre;
        this.color = color;
    }

    public String getNombre() {
        return nombre;
    }

    public Color getColor() {
        return color;
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
