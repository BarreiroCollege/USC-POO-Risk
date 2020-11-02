package gal.sdc.usc.risk.menu.comandos.partida;

import gal.sdc.usc.risk.menu.Partida;
import gal.sdc.usc.risk.menu.Resultado;
import gal.sdc.usc.risk.menu.comandos.Comando;
import gal.sdc.usc.risk.menu.comandos.Comandos;
import gal.sdc.usc.risk.menu.comandos.Estado;
import gal.sdc.usc.risk.menu.comandos.IComando;
import gal.sdc.usc.risk.tablero.Carta;
import gal.sdc.usc.risk.tablero.valores.Errores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Comando(estado = Estado.JUGANDO, comando = Comandos.CAMBIAR_CARTAS)
public class CambiarCartasTodas extends Partida implements IComando {
    private final HashMap<List<Carta>, Integer> posiblesCambios = new HashMap<>();

    @Override
    public void ejecutar(String[] comandos) {
        boolean auto = comandos.length > 3;

        List<Carta> cartas = super.getJugadorTurno().getCartas();
        List<Carta> combinacion;
        List<List<Carta>> cambios = new ArrayList<>();

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
