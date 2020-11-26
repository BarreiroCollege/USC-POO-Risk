package gal.sdc.usc.risk.correccion;

public abstract class Parseador {
    public static final String REGEX_CLAVE = "([a-zA-Z0-9áéíóúÁÉÍÓÚñÑ'\" ]*)";
    public static final String REGEX_VALOR = "(.*)";

    public static Object textoAObjeto(String string) {
        if ("".equals(string)) {
            return string;
        }

        // check JSON key words true/false/null
        if ("true".equalsIgnoreCase(string)) {
            return Boolean.TRUE;
        }
        if ("false".equalsIgnoreCase(string)) {
            return Boolean.FALSE;
        }
        if ("null".equalsIgnoreCase(string)) {
            return null;
        }

        char initial = string.charAt(0);
        if ((initial >= '0' && initial <= '9') || initial == '-') {
            try {
                return Integer.valueOf(string);
            } catch (Exception ignore) {
            }
        }
        return string;
    }

    public static String objetoATexto(Object object) {
        if (object == null) {
            return "null";
        }

        if (object instanceof String) {
            return "\"" + Parseador.Texto.sinComillas((String) object) + "\"";
        }

        return object.toString();
    }

    public static Object convertir(String o) {
        if (DatosDiccionario.REGEX.matcher(o).matches()) {
            return new DatosDiccionario(o);
        } else if (DatosArray.REGEX.matcher(o).matches()) {
            return new DatosArray(o);
        } else if (DatosTupla.REGEX.matcher(o).matches()) {
            return new DatosTupla(o);
        }
        return Parseador.textoAObjeto(o);
    }

    public static class Texto {
        public static String adaptar(String o) {
            return sinComillas(sinMayusculas(sinAcentos(sinEspacios(o))));
        }

        public static String sinComillas(String o) {
            return o.replace("\"", "").replace("'", "");
        }

        public static String sinMayusculas(String o) {
            return o.toLowerCase();
        }

        public static String sinAcentos(String o) {
            return o.replace("á", "a").replace("Á", "A")
                    .replace("é", "e").replace("É", "E")
                    .replace("í", "i").replace("Í", "I")
                    .replace("ó", "o").replace("Ó", "O")
                    .replace("ú", "u").replace("Ú", "u");
        }

        public static String sinEspacios(String o) {
            return o.replaceAll("\\s*", "");
        }
    }
}
