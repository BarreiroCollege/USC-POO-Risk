package gal.sdc.usc.risk.menu;

import gal.sdc.usc.risk.tablero.Jugador;
import gal.sdc.usc.risk.tablero.Mapa;

import java.util.HashMap;

public abstract class Partida {
    protected Mapa mapa;
    protected HashMap<String, Jugador> jugadores;

    public Mapa getMapa() {
        return this.mapa;
    }

    public HashMap<String, Jugador> getJugadores() {
        return this.jugadores;
    }
}
