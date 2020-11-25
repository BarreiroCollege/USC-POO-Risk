package gal.sdc.usc.risk.comandos.generico;

import gal.sdc.usc.risk.jugar.Partida;
import gal.sdc.usc.risk.comandos.Comando;
import gal.sdc.usc.risk.comandos.Comandos;
import gal.sdc.usc.risk.comandos.Estado;
import gal.sdc.usc.risk.comandos.IComando;

@Comando(estado = Estado.CUALQUIERA, comando = Comandos.SALIR)
public class Salir extends Partida implements IComando {
    @Override
    public void ejecutar(String[] comandos) {
        System.exit(-1);
    }

    @Override
    public String ayuda() {
        return "salir";
    }
}
