package gal.sdc.usc.risk.menu.comandos.partida;

import gal.sdc.usc.risk.menu.Partida;
import gal.sdc.usc.risk.menu.Resultado;
import gal.sdc.usc.risk.menu.comandos.Comando;
import gal.sdc.usc.risk.menu.comandos.Comandos;
import gal.sdc.usc.risk.menu.comandos.Estado;
import gal.sdc.usc.risk.menu.comandos.IComando;
import gal.sdc.usc.risk.util.Colores;

@Comando(estado = Estado.JUGANDO, comando = Comandos.CAMBIAR_CARTAS)
public class CambiarCartas extends Partida implements IComando {
    @Override
    public void ejecutar(String[] comandos) {
    }

    @Override
    public String ayuda() {
        return "cambiar cartas <id_carta1> <id_carta2> <id_carta3>";
    }
}
