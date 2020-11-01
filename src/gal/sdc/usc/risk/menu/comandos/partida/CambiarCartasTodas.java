package gal.sdc.usc.risk.menu.comandos.partida;

import gal.sdc.usc.risk.menu.Partida;
import gal.sdc.usc.risk.menu.comandos.Comando;
import gal.sdc.usc.risk.menu.comandos.Comandos;
import gal.sdc.usc.risk.menu.comandos.Estado;
import gal.sdc.usc.risk.menu.comandos.IComando;

@Comando(estado = Estado.JUGANDO, comando = Comandos.CAMBIAR_CARTAS)
public class CambiarCartasTodas extends Partida implements IComando {
    @Override
    public void ejecutar(String[] comandos) {
    }

    @Override
    public String ayuda() {
        return "cambiar cartas todas";
    }
}
