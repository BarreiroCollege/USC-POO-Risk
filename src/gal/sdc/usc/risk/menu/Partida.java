package gal.sdc.usc.risk.menu;

import gal.sdc.usc.risk.tablero.Jugador;
import gal.sdc.usc.risk.tablero.Mapa;

import java.util.HashMap;

public abstract class Partida {
    private static Mapa mapa;
    private static final HashMap<String, Jugador> jugadores = new HashMap<>();

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
}
