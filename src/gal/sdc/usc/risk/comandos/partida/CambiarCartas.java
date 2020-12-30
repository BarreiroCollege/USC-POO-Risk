package gal.sdc.usc.risk.comandos.partida;

import gal.sdc.usc.risk.comandos.Comando;
import gal.sdc.usc.risk.comandos.Comandos;
import gal.sdc.usc.risk.comandos.Estado;
import gal.sdc.usc.risk.comandos.IComando;
import gal.sdc.usc.risk.excepciones.Errores;
import gal.sdc.usc.risk.jugar.Partida;
import gal.sdc.usc.risk.salida.Resultado;
import gal.sdc.usc.risk.salida.SalidaObjeto;
import gal.sdc.usc.risk.tablero.Carta;
import gal.sdc.usc.risk.tablero.Ejercito;
import gal.sdc.usc.risk.tablero.Jugador;
import gal.sdc.usc.risk.tablero.Mapa;
import gal.sdc.usc.risk.tablero.Pais;
import gal.sdc.usc.risk.tablero.valores.Equipamientos;
import gal.sdc.usc.risk.tablero.valores.SubEquipamientos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Comando(estado = Estado.JUGANDO, comando = Comandos.CAMBIAR_CARTAS)
public class CambiarCartas extends Partida implements IComando {
    private static int calcularCambios(List<Carta> cartas, Jugador jugador) {
        int caballeria = 0, infanteria = 0, artilleria = 0; // 10
        for (Carta cartaT : cartas) {
            System.out.println(cartaT.getNombre());
            if (cartaT.getEquipamiento().equals(Equipamientos.CABALLERIA)) {
                caballeria++;
            } else if (cartaT.getEquipamiento().equals(Equipamientos.INFANTERIA)) {
                infanteria++;
            } else if (cartaT.getEquipamiento().equals(Equipamientos.ARTILLERIA)) {
                artilleria++;
            }
        }

        if (caballeria == 2 || infanteria == 2 || artilleria == 2) {
            return -1;
        }

        int numCambios = 0;
        if (caballeria == 3 || infanteria == 3 || artilleria == 3) {
            numCambios = 12;
        } else if (caballeria == 1) {
            numCambios = Equipamientos.CABALLERIA.getEjercitos();
        } else if (infanteria == 1) {
            numCambios = Equipamientos.INFANTERIA.getEjercitos();
        } else if (artilleria == 1) {
            numCambios = Equipamientos.ARTILLERIA.getEjercitos();
        }
        for (Carta cartaT : cartas) {
            numCambios += cartaT.obtenerRearme();
            if (cartaT.getPais().getJugador().equals(jugador)) {
                numCambios++;
            }
        }

        return numCambios;
    }

    public static int calcularCambiosString(List<String> cartasCambio, Jugador jugador, Mapa mapa) {
        SubEquipamientos equipamiento;
        Pais pais;
        String[] carta;
        List<Carta> cartas = new ArrayList<>();

        for (String c : cartasCambio) {
            carta = c.split("&");
            equipamiento = SubEquipamientos.toSubEquipamientos(carta[0]);
            pais = mapa.getPaisPorNombre(carta[1]);
            cartas.add(jugador.getCarta(equipamiento, pais));
        }

        return CambiarCartas.calcularCambios(cartas, jugador);
    }

    @Override
    public void ejecutar(String[] comandos) {
        String c1 = comandos[2];
        String c2 = comandos[3];
        String c3 = comandos[4];
        boolean auto = comandos.length > 5;

        Carta cartaFinal;
        String[] carta;
        SubEquipamientos equipamiento;
        Pais pais;

        List<Carta> cartas = new ArrayList<>();
        for (String c : Arrays.asList(c1, c2, c3)) {
            carta = c.split("&");
            if (carta.length != 2) {
                if (!auto) {
                    Resultado.error(Errores.CARTAS_NO_EXISTEN);
                }
                return;
            }

            equipamiento = SubEquipamientos.toSubEquipamientos(carta[0]);
            if (equipamiento == null) {
                if (!auto) {
                    Resultado.error(Errores.CARTAS_NO_EXISTEN);
                }
                return;
            }

            pais = super.getMapa().getPaisPorNombre(carta[1]);
            if (pais == null) {
                if (!auto) {
                    Resultado.error(Errores.CARTAS_NO_EXISTEN);
                }
                return;
            }

            cartaFinal = super.getJugadorTurno().getCarta(equipamiento, pais);
            if (cartaFinal == null) {
                if (!auto) {
                    Resultado.error(Errores.CARTAS_NO_JUGADOR);
                }
                return;
            }
            cartas.add(cartaFinal);
        }

        int numCambios = calcularCambios(cartas, super.getJugadorTurno());

        if (numCambios == -1) {
            if (!auto) {
                Resultado.error(Errores.CARTAS_NO_CAMBIABLES);
            }
            return;
        }

        super.getJugadorTurno().getEjercitosPendientes().recibir(new Ejercito.Builder().withCantidad(numCambios).build());

        for (Carta cartaT : cartas) {
            if (cartaT.getPais().getJugador().equals(super.getJugadorTurno())) {
                cartaT.getPais().getEjercito().recibir(new Ejercito.Builder().withCantidad(1).build());
            }
            super.getJugadorTurno().getCartas().remove(cartaT);
            super.devolverCarta(cartaT);
        }

        if (!auto) {
            SalidaObjeto salida = new SalidaObjeto();
            salida.put("cartasCambio", cartas.get(0).getNombre(), cartas.get(1).getNombre(), cartas.get(2).getNombre());
            salida.put("cartasQuedan", super.getJugadorTurno().getCartas());
            salida.put("numeroEjercitosCambiados", numCambios);
            salida.put("numEjercitosRearme", super.getJugadorTurno().getEjercitosPendientes().toInt());
            Resultado.correcto(salida);
        }
    }

    @Override
    public String ayuda() {
        return "cambiar cartas <id_carta1> <id_carta2> <id_carta3>";
    }
}
