package gal.sdc.usc.risk.comandos.partida;

import gal.sdc.usc.risk.jugar.Partida;
import gal.sdc.usc.risk.jugar.Resultado;
import gal.sdc.usc.risk.comandos.Comando;
import gal.sdc.usc.risk.comandos.Comandos;
import gal.sdc.usc.risk.comandos.Estado;
import gal.sdc.usc.risk.comandos.IComando;
import gal.sdc.usc.risk.salida.SalidaObjeto;
import gal.sdc.usc.risk.tablero.Jugador;
import gal.sdc.usc.risk.tablero.valores.Errores;

@Comando(estado = Estado.JUGANDO, comando = Comandos.DESCRIBIR_JUGADOR)
public class DescribirJugador extends Partida implements IComando {
    @Override
    public void ejecutar(String[] comandos) {
        Jugador jugador = super.getJugadorPorNombre(comandos[2]);

        if (jugador == null) {
            Resultado.error(Errores.JUGADOR_NO_EXISTE);
            return;
        }

        SalidaObjeto salida = new SalidaObjeto();
        salida.put("nombre", jugador.getNombre());
        salida.put("color", jugador.getColor().toString());
        if (jugador.equals(super.getJugadorTurno())) {
            salida.put("misión", jugador.getMision().getDescripcion());
        }
        salida.put("numeroEjercitos", jugador.getNumEjercitos());
        salida.put("paises", jugador.getPaises());
        salida.put("continentes", jugador.getContinentes());
        salida.put("cartas", jugador.getCartas());
        salida.put("numeroEjercitosRearmar", jugador.getEjercitosPendientes().toInt());
        Resultado.correcto(salida);
    }

    @Override
    public String ayuda() {
        return "describir jugador <nombre_jugador>";
    }
}
