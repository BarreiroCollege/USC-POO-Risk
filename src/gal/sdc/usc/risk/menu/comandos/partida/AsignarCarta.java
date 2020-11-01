package gal.sdc.usc.risk.menu.comandos.partida;

import gal.sdc.usc.risk.menu.Partida;
import gal.sdc.usc.risk.menu.Resultado;
import gal.sdc.usc.risk.menu.comandos.Comando;
import gal.sdc.usc.risk.menu.comandos.Comandos;
import gal.sdc.usc.risk.menu.comandos.Estado;
import gal.sdc.usc.risk.menu.comandos.IComando;
import gal.sdc.usc.risk.tablero.Carta;
import gal.sdc.usc.risk.tablero.Ejercito;
import gal.sdc.usc.risk.tablero.Pais;
import gal.sdc.usc.risk.tablero.valores.Equipamientos;
import gal.sdc.usc.risk.tablero.valores.Errores;

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
        Equipamientos equipamientoFinal = Equipamientos.toEquipamientos(equipamiento);
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

        super.getComandosPermitidos().remove(this.getClass());
        super.getJugadorTurno().getCartas().add(carta);

        if (carta.getPais().getJugador().equals(super.getJugadorTurno())) {
            paisFinal.getEjercito().recibir(new Ejercito(1));
        }

        String out = "{\n";
        out += "  tipoCarta: \"" + carta.getEquipamiento().getNombre() + "\",\n";
        out += "  paisAsociado: \"" + carta.getPais().getNombre() + "\",\n";
        out += "  perteneceAJugador: \"" + carta.getPais().getJugador().getNombre() + "\",\n";
        out += "  ejercitosDeRearme: " + carta.getPais().getJugador().equals(super.getJugadorTurno()) + "\n";
        out += "}";
        Resultado.correcto(out);
    }

    @Override
    public String ayuda() {
        return "asignar carta <id_carta>";
    }
}
