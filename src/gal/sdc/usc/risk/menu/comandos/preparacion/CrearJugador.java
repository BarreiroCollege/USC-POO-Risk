package gal.sdc.usc.risk.menu.comandos.preparacion;

import gal.sdc.usc.risk.menu.Partida;
import gal.sdc.usc.risk.menu.Resultado;
import gal.sdc.usc.risk.menu.comandos.Comando;
import gal.sdc.usc.risk.menu.comandos.Preparacion;
import gal.sdc.usc.risk.tablero.Jugador;
import gal.sdc.usc.risk.tablero.valores.Errores;
import gal.sdc.usc.risk.util.Colores;


@Preparacion(requiere = CrearMapa.class)
public class CrearJugador extends Partida implements Comando {
    @Override
    public void ejecutar(String[] comandos) {
        String nombre = comandos[1];
        String color = comandos[2];
        Colores.Color colorFinal = Colores.Color.toColor(color);

        if (super.getMapa() == null) {
            Resultado.error(Errores.MAPA_NO_CREADO);
            return;
        } else if (super.getJugadores().containsKey(nombre)) {
            Resultado.error(Errores.JUGADOR_YA_EXISTE);
            return;
        } else if (colorFinal == null) {
            Resultado.error(Errores.COLOR_NO_PERMITIDO);
            return;
        } else if (super.getJugadoresPorColor().containsKey(colorFinal)) {
            Resultado.error(Errores.COLOR_YA_ASIGNADO);
            return;

        }

        Jugador jugador = new Jugador.Builder(nombre).withColor(colorFinal).build();
        if (jugador != null) {
            super.getJugadores().put(nombre, jugador);
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
            super.getComandosEjecutados().add(this.getClass());
            super.getComandosPermitidos().add(this.getClass());
        } else if (super.getJugadores().size() == 6) {
            super.getComandosPermitidos().remove(this.getClass());
        }
    }
}
