package gal.sdc.usc.risk.menu;

import gal.sdc.usc.risk.tablero.Jugador;
import gal.sdc.usc.risk.tablero.Mapa;

import java.util.HashMap;

public abstract class Partida {
    private static Mapa mapa;
    private static final HashMap<String, Jugador> jugadores = new HashMap<>();

    private static boolean jugando = false;

    protected void setMapa(Mapa nuevoMapa) {
        if (mapa == null) {
            mapa = nuevoMapa;
        }
    }

    public Mapa getMapa() {
        return mapa;
    }

    public HashMap<String, Jugador> getJugadores() {
        return jugadores;
    }

    public boolean isJugando() {
        return jugando;
    }

    public boolean iniciar() {
        if (jugando) {
            return false;
        }
        jugando = true;
        return true;
    }
}
