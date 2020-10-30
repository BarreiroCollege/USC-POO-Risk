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
import java.util.List;

public abstract class Partida {
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
        return true;
    }

    public List<Class<? extends IComando>> getComandosPermitidos() {
        return Partida.comandosPermitidos;
    }
}
