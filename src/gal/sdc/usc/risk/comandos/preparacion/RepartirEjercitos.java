package gal.sdc.usc.risk.comandos.preparacion;

import gal.sdc.usc.risk.comandos.Comando;
import gal.sdc.usc.risk.comandos.Comandos;
import gal.sdc.usc.risk.comandos.Ejecutor;
import gal.sdc.usc.risk.comandos.Estado;
import gal.sdc.usc.risk.comandos.IComando;
import gal.sdc.usc.risk.jugar.Partida;
import gal.sdc.usc.risk.jugar.Resultado;
import gal.sdc.usc.risk.tablero.Continente;
import gal.sdc.usc.risk.tablero.Jugador;
import gal.sdc.usc.risk.tablero.Pais;
import gal.sdc.usc.risk.tablero.valores.Continentes;
import gal.sdc.usc.risk.tablero.valores.Errores;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Comando(estado = Estado.PREPARACION, comando = Comandos.REPARTIR_EJERCITOS)
public class RepartirEjercitos extends Partida implements IComando {
    private HashMap<Continente, List<Jugador>> continentesR1;
    private HashMap<Continente, List<Jugador>> continentesR4;
    private List<Jugador> jugadoresR7;

    @Override
    public void ejecutar(String[] comandos) {
        if (super.getMapa() == null) {
            Resultado.error(Errores.MAPA_NO_CREADO);
            return;
        }
        if (super.getJugadores().size() < 3 || super.getJugadores().size() > 6) {
            Resultado.error(Errores.JUGADORES_NO_CREADOS);
            return;
        }

        if (super.getJugadores().values().stream().noneMatch(p -> p.getMision() == null)) {
            Resultado.error(Errores.MISIONES_NO_ASIGNADAS);
            return;
        }

        if (super.getMapa().getPaisesPorCeldas().values().stream().noneMatch(p -> p.getJugador() == null)) {
            Resultado.error(Errores.COMANDO_NO_PERMITIDO);
            return;
        }

        this.buscarR1R4();
        this.buscarR2R5();
        this.repartirR1R2R4R5();
        this.buscarR7();
        this.repartirR7();
        this.repartirR3R6R8();

        Ejecutor.comando("ver mapa");

        // for (Jugador jugador : super.getJugadores().values()) {
        //     System.out.println(jugador.getNombre() + " -> " + jugador.getEjercitosPendientes());
        // }

        if (super.isJugando()) {
            super.getComandos().atacar();
        } else {
            super.iniciar();
        }
    }

    private void buscarR1R4() {
        this.continentesR1 = new HashMap<>();
        this.continentesR4 = new HashMap<>();

        int numPaises50, numPaises25;
        HashMap<Jugador, Integer> jugadoresPaises;
        Jugador jugador;

        for (Continente continente : super.getMapa().getContinentes().values()) {
            this.continentesR1.put(continente, new ArrayList<>());
            this.continentesR4.put(continente, new ArrayList<>());
            numPaises50 = (int) Math.ceil(continente.getPaises().size() * 0.50);
            numPaises25 = (int) Math.ceil(continente.getPaises().size() * 0.25);
            jugadoresPaises = new HashMap<>();

            for (Pais pais : continente.getPaises().values()) {
                jugador = pais.getJugador();
                jugadoresPaises.putIfAbsent(jugador, 0);
                jugadoresPaises.put(jugador, jugadoresPaises.get(jugador) + 1);
                if (jugadoresPaises.get(jugador) >= numPaises50 &&
                        !this.continentesR1.get(continente).contains(jugador)) {
                    this.continentesR1.get(continente).add(jugador);
                    this.continentesR4.get(continente).remove(jugador);
                } else if (jugadoresPaises.get(jugador) >= numPaises25 &&
                        !this.continentesR4.get(continente).contains(jugador) &&
                        !this.continentesR1.get(continente).contains(jugador)) {
                    this.continentesR4.get(continente).add(jugador);
                }
            }
        }
    }


    private void buscarR2R5() {
        this.parsearR2R5(this.continentesR1);
        this.parsearR2R5(this.continentesR4);
    }

    private void parsearR2R5(HashMap<Continente, List<Jugador>> jugadoresContinentes) {
        for (Map.Entry<Continente, List<Jugador>> jugadorContinente : jugadoresContinentes.entrySet()) {
            for (Jugador jugador : jugadorContinente.getValue()) {
                HashMap<Continente, Jugador> nuevoContinenteJugador = new HashMap<>();
                for (Map.Entry<Continente, List<Jugador>> jugadorContinente2 : jugadoresContinentes.entrySet()) {
                    if (jugadorContinente2.getValue().contains(jugador)) {
                        nuevoContinenteJugador.put(jugadorContinente2.getKey(), jugador);
                    }
                }
                this.buscarR2R5(nuevoContinenteJugador);
            }
        }
    }

    private void buscarR2R5(HashMap<Continente, Jugador> jugadoresContinentes) {
        HashMap<Jugador, List<Continente>> jugadoresDuplicados = new HashMap<>();
        for (Map.Entry<Continente, Jugador> e : jugadoresContinentes.entrySet()) {
            if (Collections.frequency(jugadoresContinentes.values(), e.getValue()) > 1) {
                jugadoresDuplicados.putIfAbsent(e.getValue(), new ArrayList<>());
                jugadoresDuplicados.get(e.getValue()).add(e.getKey());
                // jugadoresContinentes.remove(e.getKey());
            }
        }

        List<Continente> maxOcupacion;
        Continente minFronteras;
        for (Map.Entry<Jugador, List<Continente>> e : jugadoresDuplicados.entrySet()) {
            maxOcupacion = new ArrayList<>();
            for (Continente continente : e.getValue()) {
                if (maxOcupacion.size() == 0) {
                    maxOcupacion.add(continente);
                } else {
                    if ((maxOcupacion.get(0).getPaisesPorJugador(e.getKey()).size() / maxOcupacion.get(0).getPaises().size()) == (continente.getPaisesPorJugador(e.getKey()).size() / continente.getPaises().size())) {
                        maxOcupacion.add(continente);
                    } else if (maxOcupacion.get(0).getPaisesPorJugador(e.getKey()).size() / maxOcupacion.get(0).getPaises().size() < (continente.getPaisesPorJugador(e.getKey()).size()) / continente.getPaises().size()) {
                        maxOcupacion.clear();
                        maxOcupacion.add(continente);
                    }
                }
            }

            if (maxOcupacion.size() == 0) {
                System.err.println("Imposible");
            } else if (maxOcupacion.size() == 1) {
                jugadoresContinentes.put(maxOcupacion.get(0), e.getKey());
            } else {
                minFronteras = null;
                for (Continente continente : maxOcupacion) {
                    if (minFronteras == null || minFronteras.getPaisesFrontera().size() > continente.getPaisesFrontera().size()) {
                        minFronteras = continente;
                    }
                }
                jugadoresContinentes.put(maxOcupacion.get(0), e.getKey());
            }
        }
    }

    private void repartirR1R2R4R5() {
        double factor;
        int numEjercitos;

        Continente continente;
        List<Jugador> jugadores;

        for (Map.Entry<Continente, List<Jugador>> e : this.continentesR1.entrySet()) {
            continente = e.getKey();
            jugadores = e.getValue();

            if (continente.equals(super.getMapa().getContinentePorNombre(Continentes.OCEANIA.getNombre())) ||
                    continente.equals(super.getMapa().getContinentePorNombre(Continentes.AMERICASUR.getNombre()))) {
                factor = 1.5;
            } else {
                factor = 1;
            }

            for (Jugador jugador : jugadores) {
                numEjercitos = Math.toIntExact(Math.round(jugador.getEjercitosPendientes().toInt() / (factor * continente.getPaisesPorJugador(jugador).size())));
                for (Pais pais : continente.getPaisesPorJugador(jugador)) {
                    pais.getEjercito().recibir(jugador.getEjercitosPendientes(), numEjercitos, true);
                }
            }
        }

        for (Map.Entry<Continente, List<Jugador>> e : this.continentesR4.entrySet()) {
            continente = e.getKey();
            jugadores = e.getValue();

            for (Jugador jugador : jugadores) {
                numEjercitos = Math.toIntExact(Math.round(jugador.getEjercitosPendientes().toInt() / (2.0 * continente.getPaisesPorJugador(jugador).size())));
                for (Pais pais : continente.getPaisesPorJugador(jugador)) {
                    pais.getEjercito().recibir(jugador.getEjercitosPendientes(), numEjercitos, true);
                }
            }
        }
    }

    private void buscarR7() {
        this.jugadoresR7 = new ArrayList<>();
        int numPaises25 = (int) Math.ceil(super.getMapa().getPaisesPorCeldas().size() * 0.25);

        for (Jugador jugador : super.getJugadores().values()) {
            if (jugador.getPaises().size() < numPaises25) {
                this.jugadoresR7.add(jugador);
            }
        }
    }

    private void repartirR7() {
        int numEjercitos;
        double factor = super.getJugadores().size() < 5 ? 2.0 : 3.0;

        for (Jugador jugador : this.jugadoresR7) {
            for (Pais pais : jugador.getPaises()) {
                numEjercitos = Math.toIntExact(Math.round(jugador.getEjercitosPendientes().toInt() / (factor * pais.getContinente().getPaisesPorJugador(jugador).size())));
                pais.getEjercito().recibir(jugador.getEjercitosPendientes(), numEjercitos, true);
            }
        }
    }

    private void repartirR3R6R8() {
        for (List<Jugador> jugadores : this.continentesR1.values()) {
            for (Jugador jugador : jugadores) {
                jugador.getPaises().sort(Comparator.comparing(Pais::getFronteras).reversed());
                for (Pais pais : jugador.getPaises()) {
                    if (jugador.getEjercitosPendientes().toInt() == 0) {
                        break;
                    }
                    if (pais.getEjercito().toInt() == 1) {
                        pais.getEjercito().recibir(jugador.getEjercitosPendientes(), 1, true);
                    }
                }
            }
        }
        for (List<Jugador> jugadores : this.continentesR4.values()) {
            for (Jugador jugador : jugadores) {
                jugador.getPaises().sort(Comparator.comparing(Pais::getFronteras).reversed());
                for (Pais pais : jugador.getPaises()) {
                    if (jugador.getEjercitosPendientes().toInt() == 0) {
                        break;
                    }
                    if (pais.getEjercito().toInt() == 1) {
                        pais.getEjercito().recibir(jugador.getEjercitosPendientes(), 1, true);
                    }
                }
            }
        }
        for (Jugador jugador : this.jugadoresR7) {
            jugador.getPaises().sort(Comparator.comparing(Pais::getFronteras).reversed());
            for (Pais pais : jugador.getPaises()) {
                if (jugador.getEjercitosPendientes().toInt() == 0) {
                    break;
                }
                if (pais.getEjercito().toInt() == 1) {
                    pais.getEjercito().recibir(jugador.getEjercitosPendientes(), 1, true);
                }
            }
        }
    }

    @Override
    public String ayuda() {
        return "repartir ejercitos";
    }
}
