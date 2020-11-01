package gal.sdc.usc.risk.menu.comandos.partida;

import gal.sdc.usc.risk.menu.Partida;
import gal.sdc.usc.risk.menu.comandos.Comando;
import gal.sdc.usc.risk.menu.comandos.Comandos;
import gal.sdc.usc.risk.menu.comandos.Estado;
import gal.sdc.usc.risk.menu.comandos.IComando;
import gal.sdc.usc.risk.menu.comandos.preparacion.RepartirEjercito;
import gal.sdc.usc.risk.menu.comandos.preparacion.RepartirEjercitos;

@Comando(estado = Estado.JUGANDO, comando = Comandos.ATACAR_PAIS_DADOS)
public class AtacarPaisDados extends Partida implements IComando {
    @Override
    public void ejecutar(String[] comandos) {
        super.getComandosPermitidos().remove(CambiarCartas.class);
        super.getComandosPermitidos().remove(CambiarCartasTodas.class);
        super.getComandosPermitidos().remove(RepartirEjercito.class);
        super.getComandosPermitidos().remove(RepartirEjercitos.class);

        if (!super.isHaConquistadoPais()) {
            super.conquistadoPais();
            super.getComandosPermitidos().add(Rearmar.class);
            super.getComandosPermitidos().add(AsignarCarta.class);
        }
    }

    @Override
    public String ayuda() {
        return "atacar <nombre_país1> <dadosAtaque> <nombre_país2> <dadosDefensa>";
    }
}
