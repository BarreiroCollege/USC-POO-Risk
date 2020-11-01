package gal.sdc.usc.risk.menu.comandos.partida;

import gal.sdc.usc.risk.menu.Partida;
import gal.sdc.usc.risk.menu.Resultado;
import gal.sdc.usc.risk.menu.comandos.Comando;
import gal.sdc.usc.risk.menu.comandos.Estado;
import gal.sdc.usc.risk.menu.comandos.IComando;
import gal.sdc.usc.risk.menu.comandos.Comandos;
import gal.sdc.usc.risk.tablero.Continente;
import gal.sdc.usc.risk.tablero.Jugador;
import gal.sdc.usc.risk.tablero.Pais;
import gal.sdc.usc.risk.tablero.valores.Errores;
import gal.sdc.usc.risk.util.Colores;

import java.util.Iterator;

@Comando(estado = Estado.JUGANDO, comando = Comandos.DESCRIBIR_JUGADOR)
public class DescribirJugador extends Partida implements IComando {
    @Override
    public void ejecutar(String[] comandos) {
        Jugador jugador = super.getJugadorPorNombre(comandos[2]);

        if (jugador == null) {
            Resultado.error(Errores.JUGADOR_NO_EXISTE);
            return;
        }

        StringBuilder out = new StringBuilder();
        out.append("{\n");

        out.append("  nombre: \"").append(jugador.getNombre()).append("\",\n");
        out.append("  color: \"").append(jugador.getColor()).append("\",\n");
        if (jugador.equals(super.getJugadorTurno())) {
            out.append("  misión: \"").append(jugador.getMision().getDescripcion()).append("\",\n");
        }
        out.append("  numeroEjercitos: ").append(jugador.getNumEjercitos()).append(",\n");

        out.append("  paises: [ ");
        Iterator<Pais> itP = getJugadorTurno().getPaises().iterator();
        boolean primero = true;
        while (itP.hasNext()) {
            Pais pais = itP.next();
            if (!primero) {
                out.append(String.format("%-12s", ""));
            } else {
                primero = false;
            }
            out.append("\"").append(pais.getNombre()).append("\"");
            if (itP.hasNext()) {
                out.append(",\n");
            }
        }
        out.append("\n").append("          ],\n");

        out.append("  continentes: [ ");
        Iterator<Continente> itC = getJugadorTurno().getContinentes().iterator();
        while (itC.hasNext()) {
            Continente continente = itC.next();
            out.append("\"").append(continente.getNombre()).append("\"");
            if (itC.hasNext()) {
                out.append(", ");
            }
        }
        out.append(" ],\n");

        out.append("  cartas: [ ],\n");
        out.append("  numeroEjercitosRearmar: ").append(jugador.getEjercitosPendientes()).append("\n");

        out.append("}\n");
        Resultado.correcto(new Colores(out.toString(), Colores.Color.VERDE).toString());
    }

    @Override
    public String ayuda() {
        return "describir jugador <nombre_jugador>";
    }
}
