package gal.sdc.usc.risk.comandos.partida;

import gal.sdc.usc.risk.jugar.Partida;
import gal.sdc.usc.risk.jugar.Resultado;
import gal.sdc.usc.risk.comandos.Comando;
import gal.sdc.usc.risk.comandos.Comandos;
import gal.sdc.usc.risk.comandos.Estado;
import gal.sdc.usc.risk.comandos.IComando;
import gal.sdc.usc.risk.salida.SalidaObjeto;
import gal.sdc.usc.risk.tablero.Carta;
import gal.sdc.usc.risk.tablero.Pais;
import gal.sdc.usc.risk.excepciones.Errores;
import gal.sdc.usc.risk.tablero.valores.SubEquipamientos;

@Comando(estado = Estado.JUGANDO, comando = Comandos.ASIGNAR_CARTA)
public class AsignarCarta extends Partida implements IComando {
    @Override
    public void ejecutar(String[] comandos) {
        String[] partesCarta = comandos[2].split("&");
        if (partesCarta.length != 2) {
            Resultado.error(Errores.CARTA_INCORRECTA);
            return;
        }

        String equipamiento = partesCarta[0];
        SubEquipamientos equipamientoFinal = SubEquipamientos.toSubEquipamientos(equipamiento);
        if (equipamientoFinal == null) {
            Resultado.error(Errores.CARTA_INCORRECTA);
            return;
        }

        String pais = partesCarta[1];
        Pais paisFinal = super.getMapa().getPaisPorNombre(pais);
        if (paisFinal == null) {
            Resultado.error(Errores.CARTA_INCORRECTA);
            return;
        }

        Carta carta = super.getCarta(equipamientoFinal, paisFinal);
        if (carta == null) {
            Resultado.error(Errores.CARTA_YA_ASIGNADA);
            return;
        }

        super.getComandos().cartaEscogida();
        super.getJugadorTurno().getCartas().add(carta);

        SalidaObjeto salida = new SalidaObjeto();
        salida.put("tipoCarta", carta.getSubEquipamiento().getNombre());
        salida.put("paisAsociado", carta.getPais().getNombre());
        salida.put("perteneceAJugador", super.getJugadorTurno().getNombre());
        salida.put("ejercitosDeRearme", carta.getPais().getJugador().equals(super.getJugadorTurno()) ? 1 : 0);
        Resultado.correcto(salida);
    }

    @Override
    public String ayuda() {
        return "asignar carta <id_carta>";
    }
}
