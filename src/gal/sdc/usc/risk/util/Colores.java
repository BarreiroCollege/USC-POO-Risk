package gal.sdc.usc.risk.util;

/**
 * Clase para añadir colores al texto que sale por consola.
 * Solución basada en https://stackoverflow.com/a/5762502
 */
public class Colores {
    private final String texto;
    private final Color colorTexto;
    private final Color colorFondo;

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
            case VIOLETA:
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

    // Lista de colores disponibles
    public enum Color {
        NEGRO("0", "212121"),
        ROJO("1", "f44336"),
        VERDE("2", "4caf50"),
        AMARILLO("3", "ffc107"),
        AZUL("4", "03a9f4"),
        VIOLETA("5", "9c27b0"),
        CELESTE("6", "00bcd4"),
        BLANCO("7", "fafafa");

        private final String ascii;
        private final String hex;

        Color(String ascii, String hex) {
            this.ascii = ascii;
            this.hex = hex;
        }

        public String getAscii() {
            return ascii;
        }

        public String getHex() {
            return "#" + hex;
        }

        public static Color toColor(String color) {
            Color resultado;
            switch (color.trim().toUpperCase()) {
                case "AMARILLO":
                    resultado = Color.AMARILLO;
                    break;
                case "AZUL":
                    resultado = Color.AZUL;
                    break;
                case "CELESTE":
                case "CYAN":
                case "CIAN":
                    resultado = Color.CELESTE;
                    break;
                case "ROJO":
                    resultado = Color.ROJO;
                    break;
                case "VERDE":
                    resultado = Color.VERDE;
                    break;
                case "MORADO":
                case "VIOLETA":
                    resultado = Color.VIOLETA;
                    break;
                case "NEGRO":
                    resultado = Color.NEGRO;
                    break;
                case "BLANCO":
                    resultado = Color.BLANCO;
                    break;
                default:
                    resultado = null;
            }
            return resultado;
        }
    }
}
