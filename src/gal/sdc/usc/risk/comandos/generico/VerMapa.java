package gal.sdc.usc.risk.comandos.generico;

import gal.sdc.usc.risk.jugar.Partida;
import gal.sdc.usc.risk.jugar.Resultado;
import gal.sdc.usc.risk.comandos.Comando;
import gal.sdc.usc.risk.comandos.Comandos;
import gal.sdc.usc.risk.comandos.Estado;
import gal.sdc.usc.risk.comandos.IComando;
import gal.sdc.usc.risk.excepciones.Errores;

@Comando(estado = Estado.CUALQUIERA, comando = Comandos.VER_MAPA)
public class VerMapa extends Partida implements IComando {
    @Override
    public void ejecutar(String[] comandos) {
        if (super.getMapa() == null) {
            Resultado.error(Errores.MAPA_NO_CREADO);
            return;
        }
        super.getConsola().imprimir(super.getMapa());
    }

    @Override
    public String ayuda() {
        return "ver mapa";
    }
}
