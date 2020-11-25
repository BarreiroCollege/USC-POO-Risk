package gal.sdc.usc.risk.menu.comandos.partida;

import gal.sdc.usc.risk.menu.Partida;
import gal.sdc.usc.risk.menu.Resultado;
import gal.sdc.usc.risk.menu.comandos.Comando;
import gal.sdc.usc.risk.menu.comandos.Comandos;
import gal.sdc.usc.risk.menu.comandos.Estado;
import gal.sdc.usc.risk.menu.comandos.IComando;
import gal.sdc.usc.risk.salida.SalidaObjeto;
import gal.sdc.usc.risk.salida.SalidaUtils;
import gal.sdc.usc.risk.tablero.Pais;
import gal.sdc.usc.risk.tablero.valores.Errores;

@Comando(estado = Estado.JUGANDO, comando = Comandos.DESCRIBIR_PAIS)
public class DescribirPais extends Partida implements IComando {
    @Override
    public void ejecutar(String[] comandos) {
        Pais pais = super.getMapa().getPaisPorNombre(comandos[2]);

        if (pais == null) {
            Resultado.error(Errores.PAIS_NO_EXISTE);
            return;
        }

        SalidaObjeto salida = new SalidaObjeto();
        salida.put("nombre", pais.getNombre());
        salida.put("abreviatura", pais.getAbreviatura());
        salida.put("continente", pais.getContinente().getNombre());
        salida.put("frontera", pais.getFronteras().getTodas());
        salida.put("jugador", pais.getJugador().getNombre());
        salida.put("numeroEjercitos", pais.getEjercito().toInt());
        salida.put("numeroVecesOcupado", pais.getNumVecesConquistado());
        Resultado.correcto(salida);
    }

    @Override
    public String ayuda() {
        return "describir pais <abreviatura_país>";
    }
}
