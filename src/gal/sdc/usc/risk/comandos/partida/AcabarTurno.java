package gal.sdc.usc.risk.comandos.partida;

import gal.sdc.usc.risk.jugar.Partida;
import gal.sdc.usc.risk.salida.Resultado;
import gal.sdc.usc.risk.comandos.Comando;
import gal.sdc.usc.risk.comandos.Comandos;
import gal.sdc.usc.risk.comandos.Estado;
import gal.sdc.usc.risk.comandos.IComando;
import gal.sdc.usc.risk.salida.SalidaObjeto;
import gal.sdc.usc.risk.tablero.Jugador;
import gal.sdc.usc.risk.tablero.Pais;
import gal.sdc.usc.risk.tablero.valores.Continentes;
import gal.sdc.usc.risk.tablero.valores.Misiones;
import gal.sdc.usc.risk.util.Colores;

@Comando(estado = Estado.JUGANDO, comando = Comandos.ACABAR_TURNO)
public class AcabarTurno extends Partida implements IComando {
    @Override
    public void ejecutar(String[] comandos) {
        if (this.ganador()) {
            super.acabarPartida();
            Resultado.victoria(super.getJugadorTurno());
            return;
        }

        super.moverTurno();
        if (super.isJugando()) {
            super.getComandos().iniciarTurno(super.getJugadorTurno());
        }

        SalidaObjeto salida = new SalidaObjeto();
        salida.put("nombre", super.getJugadorTurno().getNombre());
        salida.put("numeroEjercitosRearmar", super.getJugadorTurno().getEjercitosPendientes().toInt());
        Resultado.correcto(salida);
    }

    private boolean ganador() {
        Misiones mision = super.getJugadorTurno().getMision().getIdentificador();

        if (mision.equals(Misiones.M1)) {
            return super.getJugadorTurno().getPaises().size() >= 24;
        } else if (mision.equals(Misiones.M2)) {
            int numPaisesEjercitos = 0;
            for (Pais pais : super.getJugadorTurno().getPaises()) {
                if (pais.getEjercito().toInt() >= 2) {
                    numPaisesEjercitos++;
                }
            }
            return numPaisesEjercitos >= 18;
        } else if (mision.equals(Misiones.M31)) {
            return super.getJugadorTurno().getContinentes().contains(super.getMapa().getContinentePorNombre(Continentes.ASIA.getNombre())) &&
                    super.getJugadorTurno().getContinentes().contains(super.getMapa().getContinentePorNombre(Continentes.AMERICASUR.getNombre()));
        } else if (mision.equals(Misiones.M32)) {
            return super.getJugadorTurno().getContinentes().contains(super.getMapa().getContinentePorNombre(Continentes.ASIA.getNombre())) &&
                    super.getJugadorTurno().getContinentes().contains(super.getMapa().getContinentePorNombre(Continentes.AFRICA.getNombre()));
        } else if (mision.equals(Misiones.M33)) {
            return super.getJugadorTurno().getContinentes().contains(super.getMapa().getContinentePorNombre(Continentes.AMERICANORTE.getNombre())) &&
                    super.getJugadorTurno().getContinentes().contains(super.getMapa().getContinentePorNombre(Continentes.AFRICA.getNombre()));
        } else if (mision.equals(Misiones.M34)) {
            return super.getJugadorTurno().getContinentes().contains(super.getMapa().getContinentePorNombre(Continentes.AMERICANORTE.getNombre())) &&
                    super.getJugadorTurno().getContinentes().contains(super.getMapa().getContinentePorNombre(Continentes.OCEANIA.getNombre()));
        } else {
            Jugador destruir = null;
            if (mision.equals(Misiones.M41)) {
                destruir = super.getJugadorPorColor(Colores.Color.AMARILLO);
            } else if (mision.equals(Misiones.M42)) {
                destruir = super.getJugadorPorColor(Colores.Color.AZUL);
            } else if (mision.equals(Misiones.M43)) {
                destruir = super.getJugadorPorColor(Colores.Color.CELESTE);
            } else if (mision.equals(Misiones.M44)) {
                destruir = super.getJugadorPorColor(Colores.Color.ROJO);
            } else if (mision.equals(Misiones.M45)) {
                destruir = super.getJugadorPorColor(Colores.Color.VERDE);
            } else if (mision.equals(Misiones.M46)) {
                destruir = super.getJugadorPorColor(Colores.Color.VIOLETA);
            }

            if (destruir == null || destruir.equals(super.getJugadorTurno())) {
                return super.getJugadorTurno().getPaises().size() >= 24;
            }
            return destruir.getPaises().size() == 0;
        }
    }

    @Override
    public String ayuda() {
        return "acabar turno";
    }
}
