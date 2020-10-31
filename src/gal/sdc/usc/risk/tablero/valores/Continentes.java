package gal.sdc.usc.risk.tablero.valores;

import gal.sdc.usc.risk.util.Colores.Color;

public enum Continentes {
    ASIA("Asia", "Asia", Color.CELESTE, 7),
    AFRICA("África", "África", Color.VERDE, 3),
    EUROPA("Europa", "Europa", Color.AMARILLO, 5),
    AMERICANORTE("América del Norte", "AmericaNorte", Color.MORADO, 5),
    AMERICASUR("América del Sur", "AméricaSur", Color.ROJO, 2),
    OCEANIA("Oceanía", "Oceanía", Color.AZUL, 2);

    private final String nombre;
    private final String abreviatura;
    private final Color color;
    private final Integer ejercitos;

    Continentes(String nombre, String abreviatura, Color color, Integer ejercitos) {
        this.nombre = nombre;
        this.abreviatura = abreviatura;
        this.color = color;
        this.ejercitos = ejercitos;
    }

    public String getNombre() {
        return this.nombre;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public Color getColor() {
        return this.color;
    }

    public Integer getEjercitos() {
        return ejercitos;
    }
}
