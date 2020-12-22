package gal.sdc.usc.risk.comandos.preparacion;

import gal.sdc.usc.risk.jugar.Partida;
import gal.sdc.usc.risk.salida.Resultado;
import gal.sdc.usc.risk.comandos.Comando;
import gal.sdc.usc.risk.comandos.Comandos;
import gal.sdc.usc.risk.comandos.Estado;
import gal.sdc.usc.risk.comandos.IComando;
import gal.sdc.usc.risk.salida.SalidaObjeto;
import gal.sdc.usc.risk.tablero.Ejercito;
import gal.sdc.usc.risk.tablero.Jugador;
import gal.sdc.usc.risk.tablero.Mision;
import gal.sdc.usc.risk.excepciones.Errores;
import gal.sdc.usc.risk.tablero.valores.Misiones;


@Comando(estado = Estado.PREPARACION, comando = Comandos.ASIGNAR_MISION)
public class AsignarMision extends Partida implements IComando {
    @Override
    public void ejecutar(String[] comandos) {
        String nombre = comandos[2];
        String mision = comandos[3];

        if (super.getMapa() == null) {
            Resultado.error(Errores.MAPA_NO_CREADO);
            return;
        }
        if (super.getJugadores().size() < 3 || super.getJugadores().size() > 6) {
            Resultado.error(Errores.JUGADORES_NO_CREADOS);
            return;
        }
        if (!super.getJugadores().containsKey(nombre)) {
            Resultado.error(Errores.JUGADOR_NO_EXISTE);
            return;
        }

        Jugador jugador = super.getJugadores().get(nombre);
        if (jugador.getMision() != null) {
            Resultado.error(Errores.JUGADOR_YA_MISION);
            return;
        }

        Misiones misiones = Misiones.toMisiones(mision);
        if (misiones == null) {
            Resultado.error(Errores.MISION_NO_EXISTE);
            return;
        }
        Mision misionFinal = new Mision.Builder(misiones).build();
        for (Jugador jugadorT : super.getJugadores().values()) {
            if (jugadorT.getMision() != null && jugadorT.getMision().equals(misionFinal)) {
                Resultado.error(Errores.MISION_YA_ASIGNADA);
                return;
            }
        }

        super.getComandos().deshabilitarCrearJugadores();

        jugador.setMision(misionFinal);
        this.comprobarJugadores();

        SalidaObjeto salida = new SalidaObjeto();
        salida.put("nombre", jugador.getNombre());
        salida.put("mision", jugador.getMision().getDescripcion());
        Resultado.correcto(salida);
    }

    private void comprobarJugadores() {
        boolean tienenMisiones = true;
        for (Jugador jugador : super.getJugadores().values()) {
            if (jugador.getMision() == null) {
                tienenMisiones = false;
                break;
            }
        }

        if (tienenMisiones) {
            // super.getComandos().habilitarAsignarPaises();
            for (Jugador j : super.getJugadores().values()) {
                j.getEjercitosPendientes().recibir(new Ejercito.Builder().withCantidad(super.getEjercitosIniciales()).build());
            }
        }
    }

    @Override
    public String ayuda() {
        return "asignar mision <nombre_jugador> <identificador_misión>";
    }
}
