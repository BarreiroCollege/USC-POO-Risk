package gal.sdc.usc.risk.menu.comandos.partida;

import gal.sdc.usc.risk.menu.Partida;
import gal.sdc.usc.risk.menu.Resultado;
import gal.sdc.usc.risk.menu.comandos.Comando;
import gal.sdc.usc.risk.menu.comandos.Comandos;
import gal.sdc.usc.risk.menu.comandos.Estado;
import gal.sdc.usc.risk.menu.comandos.IComando;
import gal.sdc.usc.risk.tablero.Carta;
import gal.sdc.usc.risk.tablero.Ejercito;
import gal.sdc.usc.risk.tablero.Pais;
import gal.sdc.usc.risk.tablero.valores.Equipamientos;
import gal.sdc.usc.risk.tablero.valores.Errores;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Comando(estado = Estado.JUGANDO, comando = Comandos.CAMBIAR_CARTAS)
public class CambiarCartas extends Partida implements IComando {
    @Override
    public void ejecutar(String[] comandos) {
        String c1 = comandos[2];
        String c2 = comandos[3];
        String c3 = comandos[4];

        Carta cartaFinal;
        String[] carta;
        Equipamientos equipamiento;
        Pais pais;

        List<Carta> cartas = new ArrayList<>();
        for (String c : Arrays.asList(c1, c2, c3)) {
            carta = c.split("&");
            if (carta.length != 2) {
                Resultado.error(Errores.CARTAS_NO_EXISTEN);
                return;
            }

            equipamiento = Equipamientos.toEquipamientos(carta[0]);
            if (equipamiento == null) {
                Resultado.error(Errores.CARTAS_NO_EXISTEN);
                return;
            }

            pais = super.getMapa().getPaisPorNombre(carta[1]);
            if (pais == null) {
                Resultado.error(Errores.CARTAS_NO_EXISTEN);
                return;
            }

            cartaFinal = super.getJugadorTurno().getCarta(equipamiento, pais);
            if (cartaFinal == null) {
                Resultado.error(Errores.CARTAS_NO_JUGADOR);
                return;
            }
            cartas.add(cartaFinal);
        }

        int caballeria = 0, infanteria = 0, artilleria = 0; // 10
        for (Carta cartaT : cartas) {
            if (cartaT.getEquipamiento().equals(Equipamientos.CABALLERIA)) {
                caballeria++;
            } else if (cartaT.getEquipamiento().equals(Equipamientos.INFANTERIA)) {
                infanteria++;
            } else if (cartaT.getEquipamiento().equals(Equipamientos.ARTILLERIA)) {
                artilleria++;
            }
        }

        if (caballeria == 2 || infanteria == 2 || artilleria == 2) {
            Resultado.error(Errores.CARTAS_NO_CAMBIABLES);
            return;
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
        super.getJugadorTurno().getEjercitosPendientes().recibir(new Ejercito(numCambios));

        for (Carta cartaT : cartas) {
            super.getJugadorTurno().getCartas().remove(cartaT);
            super.devolverCarta(cartaT);
        }

        StringBuilder out = new StringBuilder("{\n");
        out.append("  cartasCambio: [ \"").append(cartas.get(0)).append("\",").append(cartas.get(1)).append("\",").append(cartas.get(2)).append("\" ],\n");
        out.append("  cartasQuedan: [ ");
        Iterator<Carta> itCa = super.getJugadorTurno().getCartas().iterator();
        while (itCa.hasNext()) {
            Carta cartaT = itCa.next();
            out.append("\"").append(cartaT.getNombre()).append("\"");
            if (itCa.hasNext()) {
                out.append(", ");
            }
        }
        out.append(" ],\n");
        out.append("  numeroEjercitosCambiados: ").append(numCambios).append(",\n");
        out.append("  numEjercitosRearme: ").append(super.getJugadorTurno().getEjercitosPendientes()).append("\n");
        out.append("}");
        Resultado.correcto(out.toString());
    }

    @Override
    public String ayuda() {
        return "cambiar cartas <id_carta1> <id_carta2> <id_carta3>";
    }
}
