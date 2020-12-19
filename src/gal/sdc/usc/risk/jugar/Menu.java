package gal.sdc.usc.risk.jugar;

import gal.sdc.usc.risk.comandos.Ejecutor;
import gal.sdc.usc.risk.util.Colores;
import gal.sdc.usc.risk.util.Colores.Color;
import gal.sdc.usc.risk.util.Recursos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class Menu extends Partida {
    public static void jugar() {
        new Menu();
    }

    private Menu() {
        // Iniciar juego
        String orden;

        boolean hayFichero = false;
        boolean primero = true;

        try {
            BufferedReader bufferLector;
            File fichero = Recursos.get("comandos.csv");
            FileReader lector = new FileReader(fichero);
            bufferLector = new BufferedReader(lector);
            hayFichero = true;

            while ((orden = bufferLector.readLine()) != null) {
                if (orden.startsWith("#") || orden.startsWith("//")) {
                    continue;
                }

                if (!primero) {
                    super.getConsola().imprimirSalto();
                } else {
                    primero = false;
                }
                this.entrada();
                super.getConsola().imprimir(orden);
                super.getConsola().imprimirSalto();

                if (!orden.isEmpty()) {
                    Ejecutor.comando(orden);
                }
            }
            bufferLector.close();
        } catch (FileNotFoundException e ) {
            System.err.println("Archivo de comandos no encontrado, usando consola...");
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!hayFichero) {
            while (true) {
                if (!primero) {
                    super.getConsola().imprimirSalto();
                } else {
                    primero = false;
                }
                this.entrada();
                orden = super.getConsola().leer();

                if (!orden.isEmpty()) {
                    Ejecutor.comando(orden);
                }
            }
        }

        System.exit(-1);
    }

    private void entrada() {
        String out = "";
        if (super.isJugando() || super.getComandos().isPaisesAsignados(super.getMapa())) {
            out += "[" + new Colores(super.getJugadorTurno().getNombre(), super.getJugadorTurno().getColor()) + "] ";
        }
        out += new Colores("$> ", Color.AMARILLO);
        super.getConsola().imprimir(out);
    }
}
