package gal.sdc.usc.risk.util;

/**
 * Clase para añadir colores al texto que sale por consola.
 * Solución basada en https://stackoverflow.com/a/5762502
 */
public class Colores {
    private final String texto;
    private final Color colorTexto;
    private final Color colorFondo;

    // Lista de colores disponibles
    public enum Color {
        NEGRO,
        ROJO,
        VERDE,
        AMARILLO,
        AZUL,
        MORADO,
        CELESTE,
        BLANCO
    }


    // Constructores de la clase
    // El primero simplemente pone el texto con el color por defecto
    // El segundo añade color a la fuente del texto
    // El tercero añade color a la fuente del texto y al fondo
    public Colores(String texto) {
        this.texto = texto;
        this.colorTexto = null;
        this.colorFondo = null;
    }

    public Colores(String texto, Color colorTexto) {
        this.texto = texto;
        this.colorTexto = colorTexto;
        this.colorFondo = null;
    }

    public Colores(String texto, Color colorTexto, Color colorFondo) {
        this.texto = texto;
        this.colorTexto = colorTexto;
        this.colorFondo = colorFondo;
    }

    // Función para convertir el enum de colores a su representación ASCII correspondiente
    private String colorToString(Color color) {
        switch (color) {
            case NEGRO:
                return "0";
            case ROJO:
                return "1";
            case VERDE:
                return "2";
            case AMARILLO:
                return "3";
            case AZUL:
                return "4";
            case MORADO:
                return "5";
            case CELESTE:
                return "6";
            case BLANCO:
                return "7";
            default:
                return null;
        }
    }

    // Convierte el código de color en un caracter que puede ser interpretado por la consola
    private String colorTexto(Color color) {
        return "\u001B[3" + colorToString(color) + "m";
    }

    private String colorFondo(Color color) {
        return "\u001B[4" + colorToString(color) + "m";
    }

    // Se utiliza la propia función de toString para imprimir por pantalla
    @Override
    public String toString() {
        final String RESET = "\u001B[0m";

        if (this.colorTexto == null && this.colorFondo == null) {
            return RESET + this.texto + RESET;
        } else if (this.colorFondo == null) {
            return colorTexto(this.colorTexto) + this.texto + RESET;
        } else if (this.colorTexto == null) {
            return new Colores(this.texto, Color.NEGRO, this.colorFondo).toString();
        } else {
            return colorFondo(this.colorFondo) + colorTexto(this.colorTexto) + this.texto + RESET;
        }
    }
}
