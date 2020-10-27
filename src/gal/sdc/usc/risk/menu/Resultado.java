package gal.sdc.usc.risk.menu;

import gal.sdc.usc.risk.tablero.valores.Errores;
import gal.sdc.usc.risk.util.Colores;

public class Resultado {
    private final Colores.Color color;
    private final String mensaje;

    public Resultado(Colores.Color color, String mensaje) {
        this.color = color;
        this.mensaje = mensaje;
    }

    public static void error(Errores error) {
        String texto = "{\n" +
                "\tcódigo de error: " + error.getCodigo() + ",\n" +
                "\tdescripción: \"" + error.getMensaje() + "\"\n" +
                "}";
        System.out.println(new Resultado(Colores.Color.ROJO, texto));
    }

    @Override
    public String toString() {
        return new Colores(mensaje, color).toString();
    }
}
