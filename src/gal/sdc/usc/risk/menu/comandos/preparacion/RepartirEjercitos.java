package gal.sdc.usc.risk.menu.comandos.preparacion;

import gal.sdc.usc.risk.menu.Partida;
import gal.sdc.usc.risk.menu.Resultado;
import gal.sdc.usc.risk.menu.comandos.Comando;
import gal.sdc.usc.risk.menu.comandos.Estado;
import gal.sdc.usc.risk.menu.comandos.IComando;
import gal.sdc.usc.risk.menu.comandos.Regex;
import gal.sdc.usc.risk.menu.comandos.generico.ObtenerPaises;
import gal.sdc.usc.risk.tablero.Jugador;
import gal.sdc.usc.risk.tablero.Mision;
import gal.sdc.usc.risk.tablero.valores.Errores;
import gal.sdc.usc.risk.tablero.valores.Misiones;

@Comando(estado = Estado.PREPARACION, regex = Regex.REPARTIR_EJERCITOS)
public class RepartirEjercitos extends Partida implements IComando {
    @Override
    public void ejecutar(String[] comandos) {
        String nombre = comandos[1];
        String mision = comandos[2];
        if (super.getMapa().getPaisesPorCeldas() == null) {
            Resultado.error(Errores.PAIS_NO_EXISTE);
            return;
        }
        if () {
            Resultado.error(Errores.EJERCITO_NO_DISPONIBLE);
            return;
        }
        if () {
            Resultado.error(Errores.PAIS_NO_PERTENECE);
        }
    }

    @Override
    public String ayuda() {
        return "repartir ejercitos";
    }
}
