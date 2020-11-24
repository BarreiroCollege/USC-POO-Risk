package gal.sdc.usc.risk.menu.comandos.partida;

import gal.sdc.usc.risk.menu.Partida;
import gal.sdc.usc.risk.menu.Resultado;
import gal.sdc.usc.risk.menu.comandos.Comando;
import gal.sdc.usc.risk.menu.comandos.Comandos;
import gal.sdc.usc.risk.menu.comandos.Estado;
import gal.sdc.usc.risk.menu.comandos.IComando;
import gal.sdc.usc.risk.salida.SalidaObjeto;
import gal.sdc.usc.risk.salida.SalidaUtils;
import gal.sdc.usc.risk.salida.SalidaValor;
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

        SalidaObjeto salida = new SalidaObjeto();
        salida.withEntrada("nombre", SalidaValor.withString(pais.getNombre()));
        salida.withEntrada("abreviatura", SalidaValor.withString(pais.getAbreviatura()));
        salida.withEntrada("continente", SalidaValor.withString(pais.getContinente().getNombre()));
        salida.withEntrada("frontera", SalidaValor.withSalidaLista(SalidaUtils.paises(pais.getFronteras().getTodas())));
        salida.withEntrada("jugador", SalidaValor.withString(pais.getJugador().getNombre()));
        salida.withEntrada("numeroEjercitos", SalidaValor.withInteger(pais.getEjercito().toInt()));
        salida.withEntrada("numeroVecesOcupado", SalidaValor.withInteger(pais.getNumVecesConquistado()));
        Resultado.correcto(salida);
    }

    @Override
    public String ayuda() {
        return "describir pais <abreviatura_país>";
    }
}
