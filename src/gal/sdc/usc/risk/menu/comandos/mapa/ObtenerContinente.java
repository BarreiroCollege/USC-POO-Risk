package gal.sdc.usc.risk.menu.comandos.mapa;

import gal.sdc.usc.risk.menu.Partida;
import gal.sdc.usc.risk.menu.Resultado;
import gal.sdc.usc.risk.menu.comandos.Comando;
import gal.sdc.usc.risk.tablero.Pais;
import gal.sdc.usc.risk.tablero.valores.Errores;

public class ObtenerContinente extends Partida implements Comando {
    public ObtenerContinente(String clave) {
        if (super.getMapa() == null) {
            Resultado.error(Errores.MAPA_NO_CREADO);
            return;
        }

        Pais pais = super.getMapa().getPaisPorNombre(clave);
        if (pais == null) {
            Resultado.error(Errores.PAIS_NO_EXISTE);
            return;
        }

        String out = "{\n" +
                "\tcontinente: \"" + pais.getContinente().getNombre() + "\"\n" +
                "}";
        Resultado.correcto(out);
    }
}
