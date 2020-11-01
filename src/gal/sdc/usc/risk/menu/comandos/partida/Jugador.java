package gal.sdc.usc.risk.menu.comandos.partida;

import gal.sdc.usc.risk.menu.Partida;
import gal.sdc.usc.risk.menu.comandos.Comando;
import gal.sdc.usc.risk.menu.comandos.Ejecutor;
import gal.sdc.usc.risk.menu.comandos.Estado;
import gal.sdc.usc.risk.menu.comandos.IComando;
import gal.sdc.usc.risk.menu.comandos.Regex;

@Comando(estado = Estado.JUGANDO, regex = Regex.JUGADOR)
public class Jugador extends Partida implements IComando {
    @Override
    public void ejecutar(String[] comandos) {
        Ejecutor.comando("describir jugador " + super.getJugadorTurno().getNombre());
    }

    @Override
    public String ayuda() {
        return "jugador";
    }
}
