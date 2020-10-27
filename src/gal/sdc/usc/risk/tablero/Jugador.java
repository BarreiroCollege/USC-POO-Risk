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

    public static class Builder {
        private String nombre;
        private Color color;

        public Builder(String nombre) {

        }

        public Builder withColor(String color) {
            switch (color.toUpperCase()) {
                case "AMARILLO":
                    this.color = Color.AMARILLO;
                    break;
                case "AZUL":
                    this.color = Color.AZUL;
                    break;
                case "CELESTE":
                case "CYAN":
                    this.color = Color.CELESTE;
                    break;
                case "ROJO":
                    this.color = Color.ROJO;
                    break;
                case "VERDE":
                    this.color = Color.VERDE;
                    break;
                case "MORADO":
                case "VIOLETA":
                    this.color = Color.MORADO;
                    break;
            }
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
