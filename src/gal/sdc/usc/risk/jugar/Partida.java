package gal.sdc.usc.risk.jugar;

import gal.sdc.usc.risk.comandos.Ejecutor;
import gal.sdc.usc.risk.tablero.Carta;
import gal.sdc.usc.risk.tablero.Continente;
import gal.sdc.usc.risk.tablero.Ejercito;
import gal.sdc.usc.risk.tablero.Jugador;
import gal.sdc.usc.risk.tablero.Mapa;
import gal.sdc.usc.risk.tablero.Mision;
import gal.sdc.usc.risk.tablero.Pais;
import gal.sdc.usc.risk.tablero.valores.SubEquipamientos;
import gal.sdc.usc.risk.util.Colores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public abstract class Partida {
    private static final ComandosDisponibles comandosDisponibles = new ComandosDisponibles();
    private static final Consola consola = new ConsolaNormal();

    private static final Queue<Jugador> ordenJugadores = new LinkedList<>();
    private static final HashMap<String, Jugador> jugadores = new HashMap<>();
    private static final List<Carta> cartasMonton = new ArrayList<>();
    private static Mapa mapa;
    private static boolean haConquistadoPais = false;
    private static boolean jugando = false;
    private static boolean acabada = false;

    protected Mapa getMapa() {
        return Partida.mapa;
    }

    protected void setMapa(Mapa nuevoMapa) {
        if (Partida.mapa == null) {
            Partida.mapa = nuevoMapa;
        }
    }

    protected Consola getConsola() {
        return Partida.consola;
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

    protected Jugador getJugadorPorColor(Colores.Color color) {
        return this.getJugadoresPorColor().get(color);
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

    protected boolean haAcabado() {
        return Partida.acabada;
    }

    protected void acabarPartida() {
        Partida.acabada = true;
        this.getComandos().acabarPartida();
    }

    protected boolean iniciar() {
        if (Partida.jugando) {
            return false;
        }
        Partida.jugando = true;
        for (SubEquipamientos subEquipamiento : SubEquipamientos.values()) {
            for (Pais pais : this.getMapa().getPaisesPorCeldas().values()) {
                Partida.cartasMonton.add(new Carta.Builder().withSubEquipamiento(subEquipamiento).withPais(pais).build());
            }
        }

        this.getComandos().iniciarPartida(this.getJugadorTurno());
        // this.comprobacionesTurno();
        return true;
    }

    protected ComandosDisponibles getComandos() {
        return Partida.comandosDisponibles;
    }

    protected void devolverCarta(Carta carta) {
        Partida.cartasMonton.add(carta);
    }

    protected Carta getCarta(SubEquipamientos subEquipamiento, Pais pais) {
        for (Carta carta : Partida.cartasMonton) {
            if (carta.getSubEquipamiento().equals(subEquipamiento) && carta.getPais().equals(pais)) {
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
        Partida.haConquistadoPais = false;
        do {
            Jugador jugadorAnterior = Partida.ordenJugadores.poll();
            if (jugadorAnterior == null) {
                return false;
            }
            Partida.ordenJugadores.add(jugadorAnterior);
        } while (Partida.ordenJugadores.peek().getPaises().size() == 0);

        if (this.isJugando()) {
            this.comprobacionesTurno();
        }

        return true;
    }

    protected int calcularEjercitosPendientes(Jugador jugador) {
        int e = 0;

        // El jugador recibe el número de ejércitos que es el resultado de dividir el número de países que
        // pertenecen al jugador entre 3. Por ejemplo, si un jugador tiene 14 países, al iniciar su turno
        // recibe 4 países (el resultado entero de 14/3= 4).
        e += jugador.getPaises().size() / 3;

        // Si todos los países de un continente pertenecen a dicho jugador, recibe el número de ejércitos
        // indicados en la Tabla 4.
        for (Continente continente : jugador.getContinentes()) {
            e += continente.getEjercitosRearme();
        }

        return e;
    }

    protected void comprobacionesTurno() {
        // Ejércitos que le tocan
        this.getJugadorTurno().getEjercitosPendientes().recibir(new Ejercito(this.calcularEjercitosPendientes(this.getJugadorTurno())));

        // Un jugador no puede disponer de más de 6 cartas de equipamiento, en cuyo caso se deberá
        // realizar un cambio de forma automática, de modo que, si son posibles dos cambios, se elegirá
        // el que obtiene el mayor número de ejércitos.
        if (this.getJugadorTurno().getCartas().size() > 6) {
            Ejecutor.comando("cambiar cartas todas auto");
        }

        // Cuando se cambian las cartas, si el país asociado a la carta es un país que pertenece al jugador,
        // se pondrá un ejército adicional en dicho país.
        // TODO
        /* for (Carta carta : this.getJugadorTurno().getCartas()) {
            if (carta.getPais().getJugador().equals(this.getJugadorTurno())) {
                carta.getPais().getEjercito().recibir(new Ejercito(1));
            }
        } */
    }

    protected Integer getEjercitosIniciales() {
        int ejercitos = 0;
        if (this.getJugadores().size() == 3) {
            ejercitos = 35;
        } else if (this.getJugadores().size() == 4) {
            ejercitos = 30;
        } else if (this.getJugadores().size() == 5) {
            ejercitos = 25;
        } else if (this.getJugadores().size() == 6) {
            ejercitos = 20;
        }
        return ejercitos;
    }
}
