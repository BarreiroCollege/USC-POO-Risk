package gal.sdc.usc.risk.menu.comandos.preparacion;

import gal.sdc.usc.risk.menu.Partida;
import gal.sdc.usc.risk.menu.Resultado;
import gal.sdc.usc.risk.menu.comandos.Comando;
import gal.sdc.usc.risk.menu.comandos.Estado;
import gal.sdc.usc.risk.menu.comandos.IComando;
import gal.sdc.usc.risk.menu.comandos.Comandos;
import gal.sdc.usc.risk.tablero.Jugador;
import gal.sdc.usc.risk.tablero.valores.Errores;
import gal.sdc.usc.risk.util.Colores;


@Comando(estado = Estado.PREPARACION, comando = Comandos.CREAR_JUGADOR)
public class CrearJugador extends Partida implements IComando {
    @Override
    public void ejecutar(String[] comandos) {
        String nombre = comandos[1];
        String color = comandos[2];

        if (super.getMapa() == null) {
            Resultado.error(Errores.MAPA_NO_CREADO);
            return;
        }
        if (super.getJugadorPorNombre(nombre) != null) {
            Resultado.error(Errores.JUGADOR_YA_EXISTE);
            return;
        }

        Colores.Color colorFinal = Colores.Color.toColor(color);
        if (colorFinal == null) {
            Resultado.error(Errores.COLOR_NO_PERMITIDO);
            return;
        }
        if (super.getJugadoresPorColor().containsKey(colorFinal)) {
            Resultado.error(Errores.COLOR_YA_ASIGNADO);
            return;
        }

        Jugador jugador = new Jugador.Builder(nombre).withColor(colorFinal).build();
        if (jugador != null && super.getJugadores().size() < 6) {
            super.nuevoJugador(jugador);
            this.comprobarJugadores();

            String out = "{\n" +
                    "  nombre: \"" + jugador.getNombre() + "\",\n" +
                    "  color: \"" + jugador.getColor() + "\",\n" +
                    "}";
            Resultado.correcto(out);
        }
    }

    private void comprobarJugadores() {
        if (super.getJugadores().size() == 3) {
            super.getComandos().habilitarAsignarMisiones();
        } else if (super.getJugadores().size() == 6) {
            super.getComandos().deshabilitarCrearJugadores();
        }
    }

    @Override
    public String ayuda() {
        return "crear <nombre_jugador> <nombre_color>";
    }
}
