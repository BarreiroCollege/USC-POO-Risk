package gal.sdc.usc.risk.menu;

import gal.sdc.usc.risk.menu.comandos.Comando;
import gal.sdc.usc.risk.tablero.Jugador;
import gal.sdc.usc.risk.tablero.Mapa;
import gal.sdc.usc.risk.util.Colores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Partida {
    private static final HashMap<String, Jugador> jugadores = new HashMap<>();
    private static Mapa mapa;
    private static boolean jugando = false;

    private static List<Class<? extends Comando>> comandosEjecutados = new ArrayList<>();
    private static List<Class<? extends Comando>> comandosPermitidos = new ArrayList<>();


    protected Mapa getMapa() {
        return Partida.mapa;
    }

    protected void setMapa(Mapa nuevoMapa) {
        if (Partida.mapa == null) {
            Partida.mapa = nuevoMapa;
        }
    }

    protected HashMap<String, Jugador> getJugadores() {
        return Partida.jugadores;
    }

    protected HashMap<Colores.Color, Jugador> getJugadoresPorColor() {
        HashMap<Colores.Color, Jugador> jugadores = new HashMap<>();
        for (Jugador jugador : Partida.jugadores.values()) {
            jugadores.put(jugador.getColor(), jugador);
        }
        return jugadores;
    }

    protected boolean isJugando() {
        return Partida.jugando;
    }

    protected boolean iniciar() {
        if (Partida.jugando) {
            return false;
        }
        Partida.jugando = true;
        return true;
    }

    public List<Class<? extends Comando>> getComandosEjecutados() {
        return Partida.comandosEjecutados;
    }

    public List<Class<? extends Comando>> getComandosPermitidos() {
        return Partida.comandosPermitidos;
    }
}
