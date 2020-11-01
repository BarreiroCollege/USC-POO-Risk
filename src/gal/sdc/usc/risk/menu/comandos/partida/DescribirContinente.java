package gal.sdc.usc.risk.menu.comandos.partida;

import gal.sdc.usc.risk.menu.Partida;
import gal.sdc.usc.risk.menu.Resultado;
import gal.sdc.usc.risk.menu.comandos.Comando;
import gal.sdc.usc.risk.menu.comandos.Comandos;
import gal.sdc.usc.risk.menu.comandos.Estado;
import gal.sdc.usc.risk.menu.comandos.IComando;
import gal.sdc.usc.risk.tablero.Continente;
import gal.sdc.usc.risk.tablero.Jugador;
import gal.sdc.usc.risk.tablero.Pais;
import gal.sdc.usc.risk.tablero.valores.Errores;
import gal.sdc.usc.risk.util.Colores;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Comando(estado = Estado.JUGANDO, comando = Comandos.DESCRIBIR_CONTINENTE)
public class DescribirContinente extends Partida implements IComando {
    @Override
    public void ejecutar(String[] comandos) {
        Continente continente = super.getMapa().getContinentePorNombre(comandos[2]);

        if (continente == null) {
            Resultado.error(Errores.CONTINENTE_NO_EXISTE);
            return;
        }

        StringBuilder out = new StringBuilder();
        out.append("{\n");

        out.append("  nombre: \"").append(continente.getNombre()).append("\",\n");
        out.append("  abreviatura: \"").append(continente.getAbreviatura()).append("\",\n");

        out.append("  jugadores: [ ");
        HashMap<Jugador, Integer> jugadoresEjercitos = new HashMap<>();
        for (Pais pais : continente.getPaises().values()) {
            jugadoresEjercitos.putIfAbsent(pais.getJugador(), 0);
            jugadoresEjercitos.put(pais.getJugador(), jugadoresEjercitos.get(pais.getJugador()) + pais.getEjercito().toInt());
        }
        Iterator<Map.Entry<Jugador, Integer>> it = jugadoresEjercitos.entrySet().iterator();
        boolean primero = true;
        while (it.hasNext()) {
            Map.Entry<Jugador, Integer> e = it.next();
            if (!primero) {
                out.append(String.format("%-15s", ""));
            } else {
                primero = false;
            }
            out.append("{ \"").append(e.getKey().getNombre()).append("\", ").append(e.getValue()).append(" }");
            if (it.hasNext()) {
                out.append(",\n");
            }
        }
        out.append("\n").append("             ],\n");

        out.append("  numeroEjercitos: ").append(continente.getNumEjercitos()).append(",\n");
        out.append("  rearmeContinente: ").append(continente.getEjercitosRearme()).append("\n");

        out.append("}\n");
        Resultado.correcto(new Colores(out.toString(), Colores.Color.VERDE).toString());
    }

    @Override
    public String ayuda() {
        return "describir continente <abreviatura_continente>";
    }
}
