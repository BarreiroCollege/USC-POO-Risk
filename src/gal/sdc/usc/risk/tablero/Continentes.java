package gal.sdc.usc.risk.tablero;

import gal.sdc.usc.risk.util.Colores.Color;

public enum Continentes {
    ASIA("Asia", Color.CELESTE),
    AFRICA("Africa", Color.VERDE),
    EUROPA("Europa", Color.AMARILLO),
    AMERICANORTE("America del Norte", Color.MORADO),
    AMERICASUR("America del Sur", Color.ROJO),
    AUSTRALIA("Australia", Color.AZUL);

    private final String nombre;
    private final Color color;

    Continentes(String nombre, Color color) {
        this.nombre = nombre;
        this.color = color;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Color getColor() {
        return this.color;
    }
}
