package gal.sdc.usc.risk.menu.comandos.partida;

import gal.sdc.usc.risk.menu.Partida;
import gal.sdc.usc.risk.menu.Resultado;
import gal.sdc.usc.risk.menu.comandos.Comando;
import gal.sdc.usc.risk.menu.comandos.Comandos;
import gal.sdc.usc.risk.menu.comandos.Estado;
import gal.sdc.usc.risk.menu.comandos.IComando;
import gal.sdc.usc.risk.salida.SalidaObjeto;
import gal.sdc.usc.risk.salida.SalidaUtils;
import gal.sdc.usc.risk.salida.SalidaValor;
import gal.sdc.usc.risk.tablero.Carta;
import gal.sdc.usc.risk.tablero.Continente;
import gal.sdc.usc.risk.tablero.Jugador;
import gal.sdc.usc.risk.tablero.Pais;
import gal.sdc.usc.risk.tablero.valores.Errores;
import gal.sdc.usc.risk.util.Colores;

import java.util.Iterator;

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
        salida.withEntrada("nombre", SalidaValor.withString(jugador.getNombre()));
        salida.withEntrada("color", SalidaValor.withString(jugador.getColor().toString()));
        if (jugador.equals(super.getJugadorTurno())) {
            salida.withEntrada("misión", SalidaValor.withString(jugador.getMision().getDescripcion()));
        }
        salida.withEntrada("numeroEjercitos: ", SalidaValor.withInteger(jugador.getNumEjercitos()));
        salida.withEntrada("paises", SalidaValor.withSalidaLista(SalidaUtils.paises(jugador.getPaises())));
        salida.withEntrada("continentes", SalidaValor.withSalidaLista(SalidaUtils.continentes(jugador.getContinentes())));
        salida.withEntrada("cartas", SalidaValor.withSalidaLista(SalidaUtils.cartas(jugador.getCartas())));
        salida.withEntrada("numeroEjercitosRearmar", SalidaValor.withInteger(jugador.getEjercitosPendientes().toInt()));
        Resultado.correcto(salida);
    }

    @Override
    public String ayuda() {
        return "describir jugador <nombre_jugador>";
    }
}
