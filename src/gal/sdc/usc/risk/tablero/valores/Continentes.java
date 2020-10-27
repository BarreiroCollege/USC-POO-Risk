package gal.sdc.usc.risk.tablero.valores;

import gal.sdc.usc.risk.util.Colores.Color;

public enum Continentes {
    ASIA("Asia", Color.CELESTE, 7),
    AFRICA("Africa", Color.VERDE, 3),
    EUROPA("Europa", Color.AMARILLO, 5),
    AMERICANORTE("America del Norte", Color.MORADO, 5),
    AMERICASUR("America del Sur", Color.ROJO, 2),
    AUSTRALIA("Australia", Color.AZUL, 2);

    private final String nombre;
    private final Color color;
    private final Integer ejercitos;

    Continentes(String nombre, Color color, Integer ejercitos) {
        this.nombre = nombre;
        this.color = color;
        this.ejercitos = ejercitos;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Color getColor() {
        return this.color;
    }

    public Integer getEjercitos() {
        return ejercitos;
    }
}
