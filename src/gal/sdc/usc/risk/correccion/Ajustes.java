package gal.sdc.usc.risk.correccion;

public class Ajustes {
    public enum Niveles {
        CORRECTO("\u001B[32m"),
        AVISAR("\u001B[33m"),
        ERROR("\u001B[31m");

        private final String color;

        Niveles(String color) {
            this.color = color;
        }

        public String getColor() {
            return this.color;
        }
    }

    public static final Niveles SALTOS_DE_LINEAS = Niveles.ERROR;
    public static final Niveles CLAVES_CON_COMILLAS = Niveles.AVISAR;
    public static final Niveles TILDES = Niveles.ERROR;
    public static final Niveles MAYUSCULAS_MINUSCULAS = Niveles.CORRECTO;
}
