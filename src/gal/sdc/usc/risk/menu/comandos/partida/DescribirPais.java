package gal.sdc.usc.risk.menu.comandos.partida;

import gal.sdc.usc.risk.menu.Partida;
import gal.sdc.usc.risk.menu.Resultado;
import gal.sdc.usc.risk.menu.comandos.Comando;
import gal.sdc.usc.risk.menu.comandos.Comandos;
import gal.sdc.usc.risk.menu.comandos.Estado;
import gal.sdc.usc.risk.menu.comandos.IComando;
import gal.sdc.usc.risk.tablero.Pais;
import gal.sdc.usc.risk.tablero.valores.Errores;
import gal.sdc.usc.risk.util.Colores;

import java.util.Iterator;

@Comando(estado = Estado.JUGANDO, comando = Comandos.DESCRIBIR_PAIS)
public class DescribirPais extends Partida implements IComando {
    @Override
    public void ejecutar(String[] comandos) {
        Pais pais = super.getMapa().getPaisPorNombre(comandos[2]);

        if (pais == null) {
            Resultado.error(Errores.PAIS_NO_EXISTE);
            return;
        }

        StringBuilder out = new StringBuilder();
        out.append("{\n");

        out.append("  nombre: \"").append(pais.getNombre()).append("\",\n");
        out.append("  abreviatura: \"").append(pais.getAbreviatura()).append("\",\n");
        out.append("  continente: \"").append(pais.getContinente().getNombre()).append("\",\n");

        out.append("  frontera: [ ");
        Iterator<Pais> itP = pais.getFronteras().getTodas().iterator();
        boolean primero = true;
        while (itP.hasNext()) {
            Pais paisFrontera = itP.next();
            if (!primero) {
                out.append(String.format("%-14s", ""));
            } else {
                primero = false;
            }
            out.append("\"").append(paisFrontera.getNombre()).append("\"");
            if (itP.hasNext()) {
                out.append(",\n");
            }
        }
        out.append("\n").append("            ],\n");

        out.append("  jugador: \"").append(pais.getJugador().getNombre()).append("\",\n");
        out.append("  numeroEjercitos: ").append(pais.getEjercito()).append(",\n");
        out.append("  numeroVecesOcupado: ").append(pais.getNumVecesConquistado()).append("\n");

        out.append("}");
        Resultado.correcto(new Colores(out.toString(), Colores.Color.VERDE).toString());
    }

    @Override
    public String ayuda() {
        return "describir pais <abreviatura_país>";
    }
}
