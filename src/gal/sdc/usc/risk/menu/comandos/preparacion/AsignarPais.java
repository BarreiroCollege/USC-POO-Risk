package gal.sdc.usc.risk.menu.comandos.preparacion;

import gal.sdc.usc.risk.menu.Partida;
import gal.sdc.usc.risk.menu.Resultado;
import gal.sdc.usc.risk.menu.comandos.Comando;
import gal.sdc.usc.risk.menu.comandos.Comandos;
import gal.sdc.usc.risk.menu.comandos.Estado;
import gal.sdc.usc.risk.menu.comandos.IComando;
import gal.sdc.usc.risk.salida.SalidaObjeto;
import gal.sdc.usc.risk.salida.SalidaUtils;
import gal.sdc.usc.risk.salida.SalidaValor;
import gal.sdc.usc.risk.tablero.Jugador;
import gal.sdc.usc.risk.tablero.Pais;
import gal.sdc.usc.risk.tablero.valores.Errores;
import gal.sdc.usc.risk.tablero.valores.Paises;

import java.util.List;


@Comando(estado = Estado.PREPARACION, comando = Comandos.ASIGNAR_PAIS)
public class AsignarPais extends Partida implements IComando {
    @Override
    public void ejecutar(String[] comandos) {
        String jugador = comandos[2];
        String pais = comandos[3];

        if (super.getMapa() == null) {
            Resultado.error(Errores.MAPA_NO_CREADO);
            return;
        }
        if (super.getJugadores().size() < 3 || super.getJugadores().size() > 6) {
            Resultado.error(Errores.JUGADORES_NO_CREADOS);
            return;
        }
        if (!super.getJugadores().containsKey(jugador)) {
            Resultado.error(Errores.JUGADOR_NO_EXISTE);
            return;
        }

        Jugador jugadorFinal = super.getJugadores().get(jugador);
        if (jugadorFinal.getMision() == null) {
            Resultado.error(Errores.MISIONES_NO_ASIGNADAS);
            return;
        }

        Paises paises = Paises.toPaises(pais);
        if (paises == null) {
            System.out.println(pais);
            Resultado.error(Errores.PAIS_NO_EXISTE);
            return;
        }

        Pais paisFinal = super.getMapa().getPaisPorNombre(paises.getNombre());
        if (paisFinal == null) {
            Resultado.error(Errores.PAIS_NO_EXISTE);
            return;
        }
        if (paisFinal.getJugador() != null) {
            Resultado.error(Errores.PAIS_YA_ASIGNADO);
            return;
        }

        paisFinal.setJugador(jugadorFinal);
        paisFinal.getEjercito().recibir(jugadorFinal.getEjercitosPendientes(), 1);

        SalidaObjeto salida = new SalidaObjeto();
        salida.withEntrada("nombre", SalidaValor.withString(jugadorFinal.getNombre()));
        salida.withEntrada("pais", SalidaValor.withString(paisFinal.getNombre()));
        salida.withEntrada("continente", SalidaValor.withString(paisFinal.getContinente().getNombre()));
        salida.withEntrada("frontera", SalidaValor.withSalidaLista(SalidaUtils.paises(paisFinal.getFronteras().getTodas())));
        Resultado.correcto(salida);
        this.comprobarPaises();
    }

    private void comprobarPaises() {
        boolean tieneJugador = true;
        for (Pais jugador : super.getMapa().getPaisesPorCeldas().values()) {
            if (jugador.getJugador() == null) {
                tieneJugador = false;
                break;
            }
        }

        if (tieneJugador) {
            super.getComandos().habilitarRepartirEjercitos();
            // Resultado.out("[" + new Colores(super.getJugadorTurno().getNombre(), super.getJugadorTurno().getColor()) + "] Repartiendo ejércitos...");
        }
    }

    @Override
    public String ayuda() {
        return "asignar <nombre_jugador> <abreviatura_país>";
    }
}
