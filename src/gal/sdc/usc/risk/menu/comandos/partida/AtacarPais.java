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
        // TODO: Derivar a AtacarPaisDados
    }

    @Override
    public String ayuda() {
        return "atacar <abreviatura _país1> <abreviatura _país2>";
    }
}
