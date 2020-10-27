package gal.sdc.usc.risk.menu;


import gal.sdc.usc.risk.menu.comandos.Comando;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Jugadores implements Comando {
    public void crearJugador(File fichero) {
        //Array de jugadores
        List<Jugadores> jugadores = new ArrayList<>();
        //Se lee el archivo y se guardan los nombres segun van leyendose
        /* for (int i = 0; i < fichero.size; i++) {
            Jugadores.add(i, fichero);
        } */
    }
}

