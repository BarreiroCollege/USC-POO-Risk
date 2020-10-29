package gal.sdc.usc.risk.menu.comandos.partida;

import gal.sdc.usc.risk.menu.Partida;
import gal.sdc.usc.risk.menu.comandos.Comando;
import gal.sdc.usc.risk.menu.comandos.IComando;

@Comando(jugando = true)
public class Jugador extends Partida implements IComando {
    @Override
    public void ejecutar(String[] comandos) {

    }
}
