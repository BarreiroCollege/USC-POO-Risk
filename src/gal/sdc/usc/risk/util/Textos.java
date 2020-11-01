package gal.sdc.usc.risk.util;

public class Textos {
    public static String eliminarTildes(String t) {
        return t
                .replaceAll("á", "a").replaceAll("Á", "A")
                .replaceAll("é", "e").replaceAll("É", "E")
                .replaceAll("í", "i").replaceAll("Í", "I")
                .replaceAll("ó", "o").replaceAll("Ó", "O")
                .replaceAll("ú", "u").replaceAll("Ú", "U");
    }
}
