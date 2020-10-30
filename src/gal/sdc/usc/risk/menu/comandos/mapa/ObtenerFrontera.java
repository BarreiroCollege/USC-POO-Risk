package gal.sdc.usc.risk.menu.comandos.mapa;

import gal.sdc.usc.risk.menu.Partida;
import gal.sdc.usc.risk.menu.Resultado;
import gal.sdc.usc.risk.menu.comandos.Comando;
import gal.sdc.usc.risk.menu.comandos.Estado;
import gal.sdc.usc.risk.menu.comandos.IComando;
import gal.sdc.usc.risk.menu.comandos.Regex;
import gal.sdc.usc.risk.tablero.Pais;
import gal.sdc.usc.risk.tablero.valores.Errores;

import java.util.List;

@Comando(estado = Estado.CUALQUIERA, regex = Regex.OBTENER_FRONTERA)
public class ObtenerFrontera extends Partida implements IComando {
    @Override
    public void ejecutar(String[] comandos) {
        String clave = comandos[2];

        if (super.getMapa() == null) {
            Resultado.error(Errores.MAPA_NO_CREADO);
            return;
        }

        Pais pais = super.getMapa().getPaisPorNombre(clave);
        if (pais == null) {
            Resultado.error(Errores.PAIS_NO_EXISTE);
            return;
        }
        List<Pais> fronteras = pais.getFronteras().getTodas();

        StringBuilder out = new StringBuilder("{\n" +
                "  frontera: [ ");
        for (Pais frontera : fronteras) {
            out.append("\"").append(frontera.getNombre()).append("\"");
            if ((fronteras.indexOf(frontera) + 1) != fronteras.size()) {
                out.append(", ");
            }
        }
        out.append(" ]\n" + "}");
        Resultado.correcto(out.toString());
    }

    @Override
    public String ayuda() {
        return "obtener frontera <abreviatura_país>";
    }
}
