package gal.sdc.usc.risk.menu;

import gal.sdc.usc.risk.menu.comandos.Ejecutor;
import gal.sdc.usc.risk.tablero.Jugador;
import gal.sdc.usc.risk.tablero.valores.Errores;
import gal.sdc.usc.risk.util.Colores;

public class Resultado {
    private final Colores.Color color;
    private final String mensaje;

    public Resultado(Colores.Color color, String mensaje) {
        this.color = color;
        this.mensaje = mensaje;
    }

    public static void out(String out) {
        System.out.println(out);
    }

    public static void error(Errores error) {
        String texto = "{\n" +
                "\tcódigo de error: " + error.getCodigo() + ",\n" +
                "\tdescripción: \"" + error.getMensaje() + "\"\n" +
                "}";
        System.out.println(new Resultado(Colores.Color.ROJO, texto));
    }

    public static void correcto(String out) {
        System.out.println(new Resultado(Colores.Color.VERDE, out));
    }

    public static void victoria(Jugador j) {
        String out = "\033[1m\033[4m" + new Colores("VICTORIA DE ", Colores.Color.NEGRO, Colores.Color.BLANCO);
        String jugador = "\033[1m\033[4m" + new Colores(j.getNombre(), Colores.Color.NEGRO, j.getColor()).toString();
        String endout = "\033[1m\033[4m" + new Colores("!!!", Colores.Color.NEGRO, Colores.Color.BLANCO);
        System.out.println(out + jugador + endout);

        System.out.println(new Colores("Ahora puedes ver como ha quedado el tablero de juego", Colores.Color.AZUL));
        System.out.println();
        Ejecutor.comando("ayuda");
    }

    @Override
    public String toString() {
        return new Colores(mensaje, color).toString();
    }
}
