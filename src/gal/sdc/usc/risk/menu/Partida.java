package gal.sdc.usc.risk.menu;

import gal.sdc.usc.risk.menu.comandos.IComando;
import gal.sdc.usc.risk.menu.comandos.generico.Ayuda;
import gal.sdc.usc.risk.menu.comandos.generico.ObtenerColor;
import gal.sdc.usc.risk.menu.comandos.generico.ObtenerContinente;
import gal.sdc.usc.risk.menu.comandos.generico.ObtenerFrontera;
import gal.sdc.usc.risk.menu.comandos.generico.ObtenerPaises;
import gal.sdc.usc.risk.menu.comandos.generico.VerMapa;
import gal.sdc.usc.risk.menu.comandos.preparacion.CrearMapa;
import gal.sdc.usc.risk.tablero.Jugador;
import gal.sdc.usc.risk.tablero.Mapa;
import gal.sdc.usc.risk.tablero.Mision;
import gal.sdc.usc.risk.util.Colores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public abstract class Partida {
    private static final Queue<Jugador> ordenJugadores = new LinkedList<>();
    private static final HashMap<String, Jugador> jugadores = new HashMap<>();
    private static Mapa mapa;
    private static boolean jugando = false;

    private static final List<Class<? extends IComando>> comandosPermitidos = new ArrayList<>();

    static {
        comandosPermitidos.add(Ayuda.class);
        comandosPermitidos.add(VerMapa.class);
        comandosPermitidos.add(ObtenerColor.class);
        comandosPermitidos.add(ObtenerContinente.class);
        comandosPermitidos.add(ObtenerFrontera.class);
        comandosPermitidos.add(ObtenerPaises.class);
        // Iniciar la partida
        comandosPermitidos.add(CrearMapa.class);
    }


    protected Mapa getMapa() {
        return Partida.mapa;
    }

    protected void setMapa(Mapa nuevoMapa) {
        if (Partida.mapa == null) {
            Partida.mapa = nuevoMapa;
        }
    }

    protected void nuevoJugador(Jugador jugador) {
        Partida.jugadores.put(jugador.getNombre(), jugador);
        ordenJugadores.add(jugador);
    }

    protected Jugador getJugadorTurno() {
        return Partida.ordenJugadores.peek();
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

    protected HashMap<Mision, Jugador> getJugadoresPorMision() {
        HashMap<Mision, Jugador> jugadores = new HashMap<>();
        for (Jugador jugador : Partida.jugadores.values()) {
            if (jugador.getMision() != null) {
                jugadores.put(jugador.getMision(), jugador);
            }
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
        Partida.comandosPermitidos.add(gal.sdc.usc.risk.menu.comandos.partida.Jugador.class);
        return true;
    }

    protected List<Class<? extends IComando>> getComandosPermitidos() {
        return Partida.comandosPermitidos;
    }

    protected boolean moverTurno() {
        Jugador jugador = Partida.ordenJugadores.poll();
        if (jugador != null) {
            Partida.ordenJugadores.add(jugador);
            return true;
        }
        return false;
    }
}
