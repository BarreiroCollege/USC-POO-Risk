package gal.sdc.usc.risk.menu.comandos.preparacion;

import gal.sdc.usc.risk.menu.Partida;
import gal.sdc.usc.risk.menu.comandos.Comando;
import gal.sdc.usc.risk.menu.comandos.Ejecutor;
import gal.sdc.usc.risk.menu.comandos.Estado;
import gal.sdc.usc.risk.menu.comandos.IComando;
import gal.sdc.usc.risk.menu.comandos.Regex;
import gal.sdc.usc.risk.tablero.Continente;
import gal.sdc.usc.risk.tablero.Jugador;
import gal.sdc.usc.risk.tablero.Pais;
import gal.sdc.usc.risk.tablero.valores.Continentes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Comando(estado = Estado.PREPARACION, regex = Regex.REPARTIR_EJERCITOS)
public class RepartirEjercitos extends Partida implements IComando {
    private HashMap<Continente, Jugador> continentesR1;
    private HashMap<Continente, Jugador> continentesR4;
    private HashMap<Continente, Jugador> continentesR7;

    @Override
    public void ejecutar(String[] comandos) {
        this.buscarR1R4();
        // this.aplicarR1();
        // this.aplicarR4();
        // this.r2r5e8(this.r1());
        // this.r3();
        // this.r2r5e8(this.r4());

        Ejecutor.setComando("ver mapa");
        Ejecutor.comando();

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

    private void r2r5e8(HashMap<Continente, Jugador> jugadoresContinentes) {
        HashMap<Jugador, List<Continente>> jugadoresDuplicados = new HashMap<>();
        for (Map.Entry<Continente, Jugador> e : jugadoresContinentes.entrySet()) {
            if (Collections.frequency(jugadoresContinentes.values(), e.getValue()) > 1) {
                jugadoresDuplicados.putIfAbsent(e.getValue(), new ArrayList<>());
                jugadoresDuplicados.get(e.getValue()).add(e.getKey());
                continue;
            }
            asignarR1R2(e.getKey(), e.getValue());
        }

        List<Continente> maxOcupacion;
        Continente minFronteras;
        for (Map.Entry<Jugador, List<Continente>> e : jugadoresDuplicados.entrySet()) {
            maxOcupacion = new ArrayList<>();
            for (Continente continente : e.getValue()) {
                if (maxOcupacion.size() == 0) {
                    maxOcupacion.add(continente);
                } else {
                    if (maxOcupacion.get(0).getPaisesPorJugador(e.getKey()).size() == continente.getPaisesPorJugador(e.getKey()).size()) {
                        maxOcupacion.add(continente);
                    } else if (maxOcupacion.get(0).getPaisesPorJugador(e.getKey()).size() < continente.getPaisesPorJugador(e.getKey()).size()) {
                        maxOcupacion.clear();
                        maxOcupacion.add(continente);
                    }
                }
            }

            if (maxOcupacion.size() == 0) {
                System.err.println("Imposible");
            } else if (maxOcupacion.size() == 1) {
                asignarR1R2(maxOcupacion.get(0), e.getKey());
            } else {
                minFronteras = null;
                for (Continente continente : maxOcupacion) {
                    if (minFronteras == null || minFronteras.getPaisesFrontera().size() > continente.getPaisesFrontera().size()) {
                        minFronteras = continente;
                    }
                }
                asignarR1R2(minFronteras, e.getKey());
            }
        }
    }

    private void asignarR1R2(Continente continente, Jugador jugador) {
        double factor;
        int numEjercitos;

        if (continente.equals(super.getMapa().getContinentePorNombre(Continentes.OCEANIA.getNombre())) ||
                continente.equals(super.getMapa().getContinentePorNombre(Continentes.AMERICASUR.getNombre()))) {
            factor = 1.5;
        } else {
            factor = 1;
        }
        numEjercitos = Math.toIntExact(Math.round(jugador.getEjercitosPendientes().toInt() / (factor * continente.getPaisesPorJugador(jugador).size())));
        for (Pais pais : continente.getPaisesPorJugador(jugador)) {
            pais.getEjercito().recibir(jugador.getEjercitosPendientes(), numEjercitos);
        }
    }

    @Override
    public String ayuda() {
        return "repartir ejercitos";
    }
}
