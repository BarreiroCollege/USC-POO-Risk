package gal.sdc.usc.risk.comandos.partida;

import gal.sdc.usc.risk.jugar.Partida;
import gal.sdc.usc.risk.salida.Resultado;
import gal.sdc.usc.risk.comandos.Comando;
import gal.sdc.usc.risk.comandos.Comandos;
import gal.sdc.usc.risk.comandos.Ejecutor;
import gal.sdc.usc.risk.comandos.Estado;
import gal.sdc.usc.risk.comandos.IComando;
import gal.sdc.usc.risk.tablero.Carta;
import gal.sdc.usc.risk.excepciones.Errores;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Comando(estado = Estado.JUGANDO, comando = Comandos.CAMBIAR_CARTAS_TODAS)
public class CambiarCartasTodas extends Partida implements IComando {
    private final HashMap<List<Carta>, Integer> posiblesCambios = new LinkedHashMap<>();

    @Override
    public void ejecutar(String[] comandos) {
        boolean auto = comandos.length > 3;

        List<Carta> cartas = super.getJugadorTurno().getCartas();
        List<Carta> combinacion;

        if (cartas.size() < 3) {
            if (!auto) {
                Resultado.error(Errores.CARTAS_NO_SUFICIENTES);
            }
            return;
        }

        for (Carta c1 : cartas) {
            for (Carta c2 : cartas) {
                if (c1.equals(c2)) {
                    continue;
                }
                for (Carta c3 : cartas) {
                    if (c1.equals(c3) || c2.equals(c3)) {
                        continue;
                    }

                    int valor;
                    if (c1.getEquipamiento().equals(c2.getEquipamiento()) &&
                            c2.getEquipamiento().equals(c3.getEquipamiento()) &&
                            c3.getEquipamiento().equals(c1.getEquipamiento())) {
                        valor = c1.getEquipamiento().getEjercitos();
                    } else if (!c1.getEquipamiento().equals(c2.getEquipamiento()) &&
                            !c2.getEquipamiento().equals(c3.getEquipamiento()) &&
                            !c3.getEquipamiento().equals(c1.getEquipamiento())) {
                        valor = 12;
                    } else {
                        continue;
                    }

                    for (Carta carta : Arrays.asList(c1, c2, c3)) {
                        valor += carta.obtenerRearme();
                    }

                    combinacion = new ArrayList<>();
                    combinacion.add(c1);
                    combinacion.add(c2);
                    combinacion.add(c3);

                    for (Carta carta : combinacion) {
                        if (carta.getPais().getJugador().equals(super.getJugadorTurno())) {
                            valor++;
                        }
                    }

                    if (!comprobarCombinacion(combinacion)) {
                        posiblesCambios.put(combinacion, valor);
                    }
                }
            }
        }

        if (posiblesCambios.size() == 0) {
            if (!auto) {
                Resultado.error(Errores.CARTAS_NO_CAMBIABLES);
            }
            return;
        }

        List<List<Carta>> dobleCambio = new ArrayList<>();
        for (int i = 0; i < posiblesCambios.size(); i++) {
            for (int j = i + 1; j < posiblesCambios.size(); j++) {
                List<Carta> cambio1 = new ArrayList<>(posiblesCambios.keySet()).get(i);
                List<Carta> cambio2 = new ArrayList<>(posiblesCambios.keySet()).get(j);

                if (cambio1.equals(cambio2)) {
                    continue;
                }

                boolean contiene = false;
                for (Carta cartaCambio2 : cambio2) {
                    if (cambio1.contains(cartaCambio2)) {
                        contiene = true;
                        break;
                    }
                }

                if (!contiene) {
                    dobleCambio.add(cambio1);
                    dobleCambio.add(cambio2);
                    break;
                }
            }
            if (dobleCambio.size() > 0) {
                break;
            }
        }

        if (dobleCambio.size() > 0) {
            for (List<Carta> cambio : dobleCambio) {
                Ejecutor.comando("cambiar cartas " +
                        String.join(" ", cambio.stream().map(Carta::getNombre).toArray(String[]::new)) +
                        (auto ? " auto" : ""));
            }
            return;
        }

        int max = 0;
        List<Carta> cambio = new ArrayList<>();
        for (Map.Entry<List<Carta>, Integer> e : posiblesCambios.entrySet()) {
            if (e.getValue() > max) {
                max = e.getValue();
                cambio = e.getKey();
            }
        }

        Ejecutor.comando("cambiar cartas " +
                String.join(" ", cambio.stream().map(Carta::getNombre).toArray(String[]::new)) +
                (auto ? " auto" : ""));
    }

    private boolean comprobarCombinacion(List<Carta> cartas) {
        for (Map.Entry<List<Carta>, Integer> e : posiblesCambios.entrySet()) {
            if (e.getKey().containsAll(cartas)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String ayuda() {
        return "cambiar cartas todas";
    }
}
