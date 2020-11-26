package gal.sdc.usc.risk.correccion;

/**
 * Clase para especificar los ajustes del corrector. Las variables especifican el nivel de error en caso de haber
 * fallo. Los comentarios especifican la acción correcta e incorrecta. Los valores se entiende como que son cadenas
 * de texto (en caso de ser otro tipo de dato, no se aplicará), al igual que las claves (que sólo pueden ser texto).
 */
public class Ajustes {
    public enum Colores {
        NEGRO("0"),
        ROJO("1"),
        VERDE("2"),
        AMARILLO("3"),
        AZUL("4"),
        VIOLETA("5"),
        CELESTE("6"),
        BLANCO("7");

        public final static String RESET = "\u001B[0m";
        private final String color;

        Colores(String color) {
            this.color = color;
        }

        public String getTexto() {
            return "\u001B[3" + this.color + "m";
        }

        public String getFondo() {
            return "\u001B[4" + this.color + "m";
        }
    }

    public enum Niveles {
        PERMITIR(Colores.VERDE),
        AVISAR(Colores.AMARILLO),
        RECHAZAR(Colores.ROJO);

        private final Colores color;

        Niveles(Colores color) {
            this.color = color;
        }

        public Colores getColor() {
            return this.color;
        }
    }

    /**
     * Permite admitir como correcta una respuesta que sólo contenga un error.
     * OK = Si el alumno tiene un error, y está en la lista del profesor
     * FALLO = Si el error no está en la lista del profesor
     */
    public static final Niveles PERMITIR_ERRORES_SIMPLES = Niveles.AVISAR;

    /**
     * Establece si los saltos de línea generarán errores.
     * OK = Los diccionarios tienen saltos de línea
     * FALLO = Los diccionarios no tienen saltos de línea
     */
    public static final Niveles SALTOS_DE_LINEAS = Niveles.RECHAZAR;

    /**
     * Permitirá aceptar claves que no contengan comillas
     * OK = Las claves se darán por buenas tengan o no comillas
     * FALLO = Las comillas se tratáran como un caracter más en la clave
     */
    public static final Niveles CLAVES_IGNORAR_COMILLAS = Niveles.AVISAR;

    /**
     * Especifica si ignorar las tildes en las claves
     * OK = Las tildes coinciden con el profesor
     * FALLO = Las tildes no coinciden con el profesor
     */
    public static final Niveles CLAVES_IGNORAR_TILDES = Niveles.AVISAR;

    /**
     * Especifica si las mayúsculas y minúsculas se ignorarán en las claves
     * OK = La cadena coincide independientemente de las mayúsculas y minúsculas
     * FALLO = La cadena no coincide ignorando las mayúsculas y minúsculas
     */
    public static final Niveles CLAVES_IGNORAR_MAYUSCULAS = Niveles.AVISAR;

    /**
     * Permitirá aceptar valores que no contengan comillas
     * OK = Los valores se darán por buenas tengan o no comillas
     * FALLO = Las comillas se tratáran como un caracter más en la clave
     */
    public static final Niveles VALORES_IGNORAR_COMILLAS = Niveles.RECHAZAR;

    /**
     * Especifica si ignorar las tildes en los valores
     * OK = Las tildes coinciden con el profesor
     * FALLO = Las tildes no coinciden con el profesor
     */
    public static final Niveles VALORES_IGNORAR_TILDES = Niveles.AVISAR;

    /**
     * Especifica si las mayúsculas y minúsculas se ignorarán en los valores
     * OK = La cadena coincide independientemente de las mayúsculas y minúsculas
     * FALLO = La cadena no coincide ignorando las mayúsculas y minúsculas
     */
    public static final Niveles VALORES_IGNORAR_MAYUSCULAS = Niveles.AVISAR;
}
