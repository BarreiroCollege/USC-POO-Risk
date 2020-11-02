package gal.sdc.usc.risk.menu;

import gal.sdc.usc.risk.menu.comandos.ComandosDisponibles;
import gal.sdc.usc.risk.menu.comandos.Ejecutor;
import gal.sdc.usc.risk.menu.comandos.IComando;
import gal.sdc.usc.risk.tablero.Carta;
import gal.sdc.usc.risk.tablero.Continente;
import gal.sdc.usc.risk.tablero.Ejercito;
import gal.sdc.usc.risk.tablero.Jugador;
import gal.sdc.usc.risk.tablero.Mapa;
import gal.sdc.usc.risk.tablero.Mision;
import gal.sdc.usc.risk.tablero.Pais;
import gal.sdc.usc.risk.tablero.valores.Equipamientos;
import gal.sdc.usc.risk.util.Colores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public abstract class Partida {
    private static Mapa mapa;
    private static final Queue<Jugador> ordenJugadores = new LinkedList<>();
    private static final HashMap<String, Jugador> jugadores = new HashMap<>();

    private static final List<Carta> cartasMonton = new ArrayList<>();
    private static boolean haConquistadoPais = false;

    private static boolean jugando = false;
    private static final ComandosDisponibles comandosDisponibles = new ComandosDisponibles();


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

    protected Jugador getJugadorPorNombre(String nombre) {
        if (nombre == null) {
            return null;
        }

        return Partida.jugadores.values().stream()
                .filter(jugador -> jugador.getNombre().toLowerCase().equals(nombre.toLowerCase()))
                .findAny().orElse(null);
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
        for (Equipamientos equipamiento : Equipamientos.values()) {
            for (Pais pais : this.getMapa().getPaisesPorCeldas().values()) {
                Partida.cartasMonton.add(new Carta.Builder().withEquipamiento(equipamiento).withPais(pais).build());
            }
        }

        Partida.comandosDisponibles.iniciarPartida();
        this.comprobacionesTurno();
        Partida.comandosDisponibles.iniciarTurno();
        return true;
    }

    protected ComandosDisponibles getComandos() {
        return Partida.comandosDisponibles;
    }

    protected void devolverCarta(Carta carta) {
        Partida.cartasMonton.add(carta);
    }

    protected Carta getCarta(Equipamientos equipamiento, Pais pais) {
        for (Carta carta : Partida.cartasMonton) {
            if (carta.getEquipamiento().equals(equipamiento) && carta.getPais().equals(pais)) {
                Partida.cartasMonton.remove(carta);
                return carta;
            }
        }
        return null;
    }

    protected boolean isHaConquistadoPais() {
        return Partida.haConquistadoPais;
    }

    protected void conquistadoPais() {
        Partida.haConquistadoPais = true;
    }

    protected boolean moverTurno() {
        Jugador jugadorAnterior = Partida.ordenJugadores.poll();
        if (jugadorAnterior == null) {
            return false;
        }
        Partida.haConquistadoPais = false;
        Partida.ordenJugadores.add(jugadorAnterior);

        if (this.isJugando()) {
            this.comprobacionesTurno();
        }

        return true;
    }

    protected void comprobacionesTurno() {
        // El jugador recibe el número de ejércitos que es el resultado de dividir el número de países que
        // pertenecen al jugador entre 3. Por ejemplo, si un jugador tiene 14 países, al iniciar su turno
        // recibe 4 países (el resultado entero de 14/3= 4).
        this.getJugadorTurno().getEjercitosPendientes().recibir(new Ejercito(this.getJugadorTurno().getPaises().size() / 3));

        // Si todos los países de un continente pertenecen a dicho jugador, recibe el número de ejércitos
        // indicados en la Tabla 4.
        for (Continente continente : this.getJugadorTurno().getContinentes()) {
            this.getJugadorTurno().getEjercitosPendientes().recibir(new Ejercito(continente.getEjercitosRearme()));
        }

        // Un jugador no puede disponer de más de 6 cartas de equipamiento, en cuyo caso se deberá
        // realizar un cambio de forma automática, de modo que, si son posibles dos cambios, se elegirá
        // el que obtiene el mayor número de ejércitos.
        if (this.getJugadorTurno().getCartas().size() > 6) {
            Ejecutor.comando("cambiar cartas todas");
        }

        // Cuando se cambian las cartas, si el país asociado a la carta es un país que pertenece al jugador,
        // se pondrá un ejército adicional en dicho país.
        for (Carta carta : this.getJugadorTurno().getCartas()) {
            if (carta.getPais().getJugador().equals(this.getJugadorTurno())) {
                carta.getPais().getEjercito().recibir(new Ejercito(1));
            }
        }
    }
}
