package gal.sdc.usc.risk.comandos.partida;

import gal.sdc.usc.risk.comandos.Comando;
import gal.sdc.usc.risk.comandos.Comandos;
import gal.sdc.usc.risk.comandos.Ejecutor;
import gal.sdc.usc.risk.comandos.Estado;
import gal.sdc.usc.risk.comandos.IComando;
import gal.sdc.usc.risk.jugar.Partida;

@Comando(estado = Estado.JUGANDO, comando = Comandos.JUGADOR)
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
