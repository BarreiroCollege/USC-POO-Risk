package gal.sdc.usc.risk.menu.comandos.preparacion;

import gal.sdc.usc.risk.menu.Partida;
import gal.sdc.usc.risk.menu.Resultado;
import gal.sdc.usc.risk.menu.comandos.Comando;
import gal.sdc.usc.risk.menu.comandos.Comandos;
import gal.sdc.usc.risk.menu.comandos.Estado;
import gal.sdc.usc.risk.menu.comandos.IComando;
import gal.sdc.usc.risk.tablero.Ejercito;
import gal.sdc.usc.risk.tablero.Jugador;
import gal.sdc.usc.risk.tablero.Mision;
import gal.sdc.usc.risk.tablero.valores.Errores;
import gal.sdc.usc.risk.tablero.valores.Misiones;


@Comando(estado = Estado.PREPARACION, comando = Comandos.ASIGNAR_MISION)
public class AsignarMision extends Partida implements IComando {
    @Override
    public void ejecutar(String[] comandos) {
        String nombre = comandos[1];
        String mision = comandos[2];

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
        if (super.getJugadoresPorMision().containsKey(misionFinal)) {
            Resultado.error(Errores.JUGADOR_NO_EXISTE);
            return;
        }

        super.getComandos().deshabilitarCrearJugadores();

        jugador.setMision(misionFinal);
        this.comprobarJugadores();

        String out = "{\n" +
                "  nombre: \"" + jugador.getNombre() + "\",\n" +
                "  mision: \"" + jugador.getMision().getDescripcion() + "\",\n" +
                "}";
        Resultado.correcto(out);
    }

    private void comprobarJugadores() {
        boolean tienenMisiones = true;
        for (Jugador jugador : super.getJugadores().values()) {
            if (jugador.getMision() == null) {
                tienenMisiones = false;
                break;
            }
        }

        if (super.getJugadores().entrySet().iterator().next().getValue().getEjercitosPendientes().toInt() == 0) {
            int ejercitos = 0;
            if (super.getJugadores().size() == 3) {
                ejercitos = 35;
            } else if (super.getJugadores().size() == 4) {
                ejercitos = 30;
            } else if (super.getJugadores().size() == 5) {
                ejercitos = 25;
            } else if (super.getJugadores().size() == 6) {
                ejercitos = 20;
            }

            if (ejercitos > 0) {
                for (Jugador jugador : super.getJugadores().values()) {
                    jugador.getEjercitosPendientes().recibir(new Ejercito(ejercitos));
                }
            }
        }

        if (tienenMisiones) {
            super.getComandos().habilitarAsignarPaises();
        }
    }

    @Override
    public String ayuda() {
        return "asignar <nombre_jugador> <identificador_misión>";
    }
}
