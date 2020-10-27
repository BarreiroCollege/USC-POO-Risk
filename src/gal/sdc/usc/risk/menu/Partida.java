package gal.sdc.usc.risk.menu;

import gal.sdc.usc.risk.menu.comandos.CrearMapa;
import gal.sdc.usc.risk.tablero.Jugador;
import gal.sdc.usc.risk.tablero.Mapa;

import java.util.HashMap;

public class Partida {
    private final Mapa mapa;
    private final HashMap<String, Jugador> jugadores;

    public Partida() {
        this.mapa = new CrearMapa().getMapa();
        this.jugadores = new HashMap<>();
    }

    public Mapa getMapa() {
        return this.mapa;
    }

    public HashMap<String, Jugador> getJugadores() {
        return this.jugadores;
    }

    /* public static class Builder {
        private final Mapa mapa;
        private final HashMap<String, Jugador> jugadores;

        public Builder(Mapa mapa) {
            this.mapa = mapa;
            this.jugadores = new HashMap<String, Jugador>();
        }

        public Builder withJugador(Jugador jugador) {
            this.jugadores.put(jugador.getNombre(), jugador);
            return this;
        }

        public Partida build() {
            if (mapa == null) {
                // TODO
            } else if (jugadores.size() < 3) {
                // TODO
            } else if (jugadores.size() > 6) {
                // TODO
            } else {
                return new Partida(mapa, jugadores);
            }
            return null;
        }
    } */
}
