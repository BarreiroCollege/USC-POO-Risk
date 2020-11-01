package gal.sdc.usc.risk.menu.comandos.partida;

import gal.sdc.usc.risk.menu.Partida;
import gal.sdc.usc.risk.menu.comandos.Comando;
import gal.sdc.usc.risk.menu.comandos.Comandos;
import gal.sdc.usc.risk.menu.comandos.Estado;
import gal.sdc.usc.risk.menu.comandos.IComando;
import gal.sdc.usc.risk.menu.comandos.preparacion.RepartirEjercito;
import gal.sdc.usc.risk.menu.comandos.preparacion.RepartirEjercitos;

@Comando(estado = Estado.JUGANDO, comando = Comandos.CAMBIAR_CARTAS)
public class AtacarPais extends Partida implements IComando {
    @Override
    public void ejecutar(String[] comandos) {
        super.getComandosPermitidos().remove(CambiarCartas.class);
        super.getComandosPermitidos().remove(CambiarCartasTodas.class);
        super.getComandosPermitidos().remove(RepartirEjercito.class);
        super.getComandosPermitidos().remove(RepartirEjercitos.class);
    }

    @Override
    public String ayuda() {
        return "";
    }
}
