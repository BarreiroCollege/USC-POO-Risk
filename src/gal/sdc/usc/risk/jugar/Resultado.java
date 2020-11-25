package gal.sdc.usc.risk.jugar;

import gal.sdc.usc.risk.comandos.Ejecutor;
import gal.sdc.usc.risk.salida.SalidaObjeto;
import gal.sdc.usc.risk.tablero.Jugador;
import gal.sdc.usc.risk.tablero.valores.Errores;
import gal.sdc.usc.risk.util.Colores;
import gal.sdc.usc.risk.util.Recursos;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class Resultado {
    private final Colores.Color color;
    private final String mensaje;

    private Resultado(Colores.Color color, String mensaje) {
        this.color = color;
        this.mensaje = mensaje;

        Resultado.Escritor.resultado(mensaje);
    }

    public static void error(Errores error) {
        SalidaObjeto salida = new SalidaObjeto();
        salida.put("código de error", error.getCodigo());
        salida.put("descripción", error.getMensaje());
        System.out.println(new Resultado(Colores.Color.ROJO, salida.toString()));
    }

    public static void correcto(SalidaObjeto out) {
        System.out.println(new Resultado(Colores.Color.VERDE, out.toString()));
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

    public static class Escritor {
        private static FileOutputStream fos;
        private static BufferedWriter bw;

        private static void inicializar() {
            try {
                fos = new FileOutputStream(Recursos.get("resultados.txt"));
                bw = new BufferedWriter(new OutputStreamWriter(fos));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public static void comando(String s) {
            if (fos == null || bw == null) {
                Escritor.inicializar();
            }
            try {
                bw.write("$> ");
                bw.write(s);
                bw.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public static void resultado(String s) {
            if (fos == null || bw == null) {
                Escritor.inicializar();
            }
            try {
                bw.write(s);
                bw.newLine();
                bw.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public static void cerrar() {
            try {
                if (bw != null) {
                    bw.write("EOF");
                    bw.close();
                    bw = null;
                }
                if (fos != null) {
                    fos.close();
                    fos = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
