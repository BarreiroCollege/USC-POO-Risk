package gal.sdc.usc.risk.menu.comandos.preparacion;

import gal.sdc.usc.risk.menu.Partida;
import gal.sdc.usc.risk.menu.comandos.Comando;
import gal.sdc.usc.risk.menu.comandos.Ejecutor;
import gal.sdc.usc.risk.menu.comandos.Estado;
import gal.sdc.usc.risk.menu.comandos.IComando;
import gal.sdc.usc.risk.menu.comandos.Comandos;
import gal.sdc.usc.risk.menu.comandos.partida.CambiarCartas;
import gal.sdc.usc.risk.menu.comandos.partida.CambiarCartasTodas;
import gal.sdc.usc.risk.tablero.Continente;
import gal.sdc.usc.risk.tablero.Jugador;
import gal.sdc.usc.risk.tablero.Pais;
import gal.sdc.usc.risk.tablero.valores.Continentes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Comando(estado = Estado.PREPARACION, comando = Comandos.REPARTIR_EJERCITOS)
public class RepartirEjercitos extends Partida implements IComando {
    private HashMap<Continente, Jugador> continentesR1;
    private HashMap<Continente, Jugador> continentesR4;
    private List<Jugador> jugadoresR7;

    @Override
    public void ejecutar(String[] comandos) {
        this.buscarR1R4();
        this.buscarR2R5();
        this.repartirR1R2R4R5();
        this.buscarR7();
        this.repartirR7();
        this.repartirR3R6R8();

        Ejecutor.comando("ver mapa");

        super.getComandosPermitidos().remove(CambiarCartas.class);
        super.getComandosPermitidos().remove(CambiarCartasTodas.class);
        super.getComandosPermitidos().remove(RepartirEjercito.class);
        super.getComandosPermitidos().remove(RepartirEjercitos.class);
        super.iniciar();
    }

    private void buscarR1R4() {
        this.continentesR1 = new HashMap<>();
        this.continentesR4 = new HashMap<>();

        int numPaises50, numPaises25;
        HashMap<Jugador, Integer> jugadoresPaises;
        Jugador jugador;

        for (Continente continente : super.getMapa().getContinentes().values()) {
            numPaises50 = (int) Math.ceil(continente.getPaises().size() * 0.50);
            numPaises25 = (int) Math.ceil(continente.getPaises().size() * 0.25);
            jugadoresPaises = new HashMap<>();

            for (Pais pais : continente.getPaises().values()) {
                jugador = pais.getJugador();
                jugadoresPaises.putIfAbsent(jugador, 0);
                if (jugadoresPaises.get(jugador) >= numPaises50) {
                    this.continentesR1.put(continente, jugador);
                    break;
                } else if (jugadoresPaises.get(jugador) >= numPaises25) {
                    this.continentesR4.put(continente, jugador);
                    break;
                }
                jugadoresPaises.put(jugador, jugadoresPaises.get(jugador) + 1);
            }
        }
    }

    private void buscarR2R5() {
        this.buscarR2R5(this.continentesR1);
        this.buscarR2R5(this.continentesR4);
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
        Jugador jugador;

        for (Map.Entry<Continente, Jugador> e : this.continentesR1.entrySet()) {
            continente = e.getKey();
            jugador = e.getValue();

            if (continente.equals(super.getMapa().getContinentePorNombre(Continentes.OCEANIA.getNombre())) ||
                    continente.equals(super.getMapa().getContinentePorNombre(Continentes.AMERICASUR.getNombre()))) {
                factor = 1.5;
            } else {
                factor = 1;
            }
            numEjercitos = Math.toIntExact(Math.round(jugador.getEjercitosPendientes().toInt() / (factor * continente.getPaisesPorJugador(jugador).size())));
            for (Pais pais : continente.getPaisesPorJugador(jugador)) {
                pais.getEjercito().recibir(jugador.getEjercitosPendientes(), numEjercitos, true);
            }
        }

        for (Map.Entry<Continente, Jugador> e : this.continentesR4.entrySet()) {
            continente = e.getKey();
            jugador = e.getValue();

            numEjercitos = Math.toIntExact(Math.round(jugador.getEjercitosPendientes().toInt() / (2.0 * continente.getPaisesPorJugador(jugador).size())));
            for (Pais pais : continente.getPaisesPorJugador(jugador)) {
                pais.getEjercito().recibir(jugador.getEjercitosPendientes(), numEjercitos, true);
            }
        }
    }

    private void buscarR7() {
        this.jugadoresR7 = new ArrayList<>();

        HashMap<Jugador, HashMap<Continente, Integer>> jugadoresContinentes = new HashMap<>();
        HashMap<Continente, Integer> numPaises25 = new HashMap<>();
        Jugador jugador;

        for (Continente continente : super.getMapa().getContinentes().values()) {
            numPaises25.put(continente, (int) Math.ceil(continente.getPaises().size() * 0.25));

            for (Pais pais : continente.getPaises().values()) {
                jugador = pais.getJugador();
                jugadoresContinentes.putIfAbsent(jugador, new HashMap<>());
                jugadoresContinentes.get(jugador).putIfAbsent(continente, 0);
                jugadoresContinentes.get(jugador).put(continente, jugadoresContinentes.get(jugador).get(continente));
            }
        }

        for (Map.Entry<Jugador, HashMap<Continente, Integer>> jugadorContinentes : jugadoresContinentes.entrySet()) {
            boolean menor = true;
            for (Map.Entry<Continente, Integer> jugadorContinente : jugadorContinentes.getValue().entrySet()) {
                if (jugadorContinente.getValue() == 0) {
                    menor = false;
                    break;
                } else if (jugadorContinente.getValue() >= numPaises25.get(jugadorContinente.getKey())) {
                    menor = false;
                    break;
                }
            }

            if (menor) {
                this.jugadoresR7.add(jugadorContinentes.getKey());
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
        for (Jugador jugador : this.continentesR1.values()) {
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
        for (Jugador jugador : this.continentesR4.values()) {
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
