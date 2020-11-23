package gal.sdc.usc.risk.menu.comandos.generico;

import gal.sdc.usc.risk.menu.Partida;
import gal.sdc.usc.risk.menu.comandos.Comando;
import gal.sdc.usc.risk.menu.comandos.Comandos;
import gal.sdc.usc.risk.menu.comandos.Estado;
import gal.sdc.usc.risk.menu.comandos.IComando;

@Comando(estado = Estado.CUALQUIERA, comando = Comandos.SALIR)
public class Salir extends Partida implements IComando {
    @Override
    public void ejecutar(String[] comandos) {
        System.exit(-2);
    }

    @Override
    public String ayuda() {
        return "salir";
    }
}
