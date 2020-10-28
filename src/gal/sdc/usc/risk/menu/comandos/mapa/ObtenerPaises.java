package gal.sdc.usc.risk.menu.comandos.mapa;

import gal.sdc.usc.risk.menu.Partida;
import gal.sdc.usc.risk.menu.Resultado;
import gal.sdc.usc.risk.menu.comandos.Comando;
import gal.sdc.usc.risk.tablero.Continente;
import gal.sdc.usc.risk.tablero.Pais;
import gal.sdc.usc.risk.tablero.valores.Errores;

import java.util.Iterator;
import java.util.Map;

public class ObtenerPaises extends Partida implements Comando {
    public ObtenerPaises(String clave) {
        if (super.getMapa() == null) {
            Resultado.error(Errores.MAPA_NO_CREADO);
            return;
        }

        Continente continente = super.getMapa().getContinentePorNombre(clave);
        if (continente == null) {
            Resultado.error(Errores.CONTINENTE_NO_EXISTE);
            return;
        }

        StringBuilder out = new StringBuilder("{\n" +
                "\tpaises: [\n");
        Iterator<Map.Entry<String, Pais>> it = continente.getPaises().entrySet().iterator();
        while (it.hasNext()) {
            Pais pais = it.next().getValue();
            out.append(String.format("%-8s", ""));
            out.append("\"").append(pais.getNombre()).append("\"");
            if (it.hasNext()) {
                out.append(",\n");
            }
        }
        out.append("\n").append("    ]\n" + "}");
        Resultado.correcto(out.toString());
    }
}
