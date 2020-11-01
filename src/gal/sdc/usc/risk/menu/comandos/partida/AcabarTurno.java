package gal.sdc.usc.risk.menu.comandos.partida;

import gal.sdc.usc.risk.menu.Partida;
import gal.sdc.usc.risk.menu.Resultado;
import gal.sdc.usc.risk.menu.comandos.Comando;
import gal.sdc.usc.risk.menu.comandos.Comandos;
import gal.sdc.usc.risk.menu.comandos.Estado;
import gal.sdc.usc.risk.menu.comandos.IComando;
import gal.sdc.usc.risk.util.Colores;

@Comando(estado = Estado.JUGANDO, comando = Comandos.ACABAR_TURNO)
public class AcabarTurno extends Partida implements IComando {
    @Override
    public void ejecutar(String[] comandos) {
        super.moverTurno();
        super.comandosTurno();

        String out = "{\n" +
                "  nombre: \"" + super.getJugadorTurno().getNombre() + "\",\n" +
                "  numeroEjercitosRearmar: " + super.getJugadorTurno().getEjercitosPendientes() + "\n" +
                "}";
        Resultado.correcto(new Colores(out, Colores.Color.VERDE).toString());
    }

    @Override
    public String ayuda() {
        return "acabar turno";
    }
}
