package gal.sdc.usc.risk.comandos.partida;

import gal.sdc.usc.risk.jugar.Partida;
import gal.sdc.usc.risk.salida.Resultado;
import gal.sdc.usc.risk.comandos.Comando;
import gal.sdc.usc.risk.comandos.Comandos;
import gal.sdc.usc.risk.comandos.Estado;
import gal.sdc.usc.risk.comandos.IComando;
import gal.sdc.usc.risk.salida.SalidaObjeto;
import gal.sdc.usc.risk.tablero.Pais;
import gal.sdc.usc.risk.excepciones.Errores;

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
