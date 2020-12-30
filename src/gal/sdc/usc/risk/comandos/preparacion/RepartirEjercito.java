package gal.sdc.usc.risk.comandos.preparacion;

import gal.sdc.usc.risk.comandos.Comando;
import gal.sdc.usc.risk.comandos.Comandos;
import gal.sdc.usc.risk.comandos.Estado;
import gal.sdc.usc.risk.comandos.IComando;
import gal.sdc.usc.risk.gui.componentes.mapa.MapaController;
import gal.sdc.usc.risk.jugar.Partida;
import gal.sdc.usc.risk.salida.Resultado;
import gal.sdc.usc.risk.salida.SalidaDupla;
import gal.sdc.usc.risk.salida.SalidaObjeto;
import gal.sdc.usc.risk.tablero.Jugador;
import gal.sdc.usc.risk.tablero.Pais;
import gal.sdc.usc.risk.excepciones.Errores;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Comando(estado = Estado.PREPARACION, comando = Comandos.REPARTIR_EJERCITO)
public class RepartirEjercito extends Partida implements IComando {
    @Override
    public void ejecutar(String[] comandos) {
        String pais = comandos[3];
        int numero;
        try {
            numero = Integer.parseInt(comandos[2]);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return;
        }

        if (super.getMapa() == null) {
            Resultado.error(Errores.MAPA_NO_CREADO);
            return;
        }
        if (super.getJugadores().size() < 3 || super.getJugadores().size() > 6) {
            Resultado.error(Errores.JUGADORES_NO_CREADOS);
            return;
        }

        if (super.getJugadores().values().stream().anyMatch(p -> p.getMision() == null)) {
            Resultado.error(Errores.MISIONES_NO_ASIGNADAS);
            return;
        }

        if (super.getMapa().getPaisesPorCeldas().values().stream().anyMatch(p -> p.getJugador() == null)) {
            Resultado.error(Errores.COMANDO_NO_PERMITIDO);
            return;
        }

        Pais paisFinal = super.getMapa().getPaisPorNombre(pais);
        if (paisFinal == null) {
            Resultado.error(Errores.PAIS_NO_EXISTE);
            return;
        }
        if (!paisFinal.getJugador().equals(super.getJugadorTurno())) {
            Resultado.error(Errores.PAIS_NO_PERTENECE);
            return;
        }

        super.getComandos().deshabilitarRepartirEjercitosAuto();
        Integer asignado = paisFinal.getEjercito().recibir(paisFinal.getJugador().getEjercitosPendientes(), numero);
        if (asignado != null) {
            SalidaObjeto salida = new SalidaObjeto();
            salida.put("pais", paisFinal.getNombre());
            salida.put("jugador", paisFinal.getJugador().getNombre());
            salida.put("numeroEjercitosAsignados", asignado);
            salida.put("numeroEjercitosTotales", paisFinal.getEjercito());

            List<SalidaDupla> paisesOcupadosContinente = new ArrayList<>();
            List<Pais> paisesJugadorContinente = super.getMapa().getPaisesPorJugador(paisFinal.getJugador()).stream()
                    .filter(paisFiltro -> paisFiltro.getContinente().equals(paisFinal.getContinente()))
                    .collect(Collectors.toList());
            for (Pais paisOcupado : paisesJugadorContinente) {
                paisesOcupadosContinente.add(new SalidaDupla(paisOcupado.getNombre(), paisOcupado.getEjercito()));
            }
            salida.put("paisesOcupadosContinente", paisesOcupadosContinente);

            Resultado.correcto(salida);
            this.comprobarEjercitos(paisFinal.getJugador());
        }
    }

    private void comprobarEjercitos(Jugador jugador) {
        if (super.isJugando()) {
            super.getComandos().repartiendo();

            if (super.getJugadorTurno().getEjercitosPendientes().toInt() == 0) {
                if (MapaController.isRepartir()) {
                    MapaController.cambiarRepartir();
                }
                super.getComandos().atacar();
            }
        } else {
            if (jugador.getEjercitosPendientes().toInt() == 0) {
                // super.moverTurno();
                // Resultado.out("[" + new Colores(super.getJugadorTurno().getNombre(), super.getJugadorTurno().getColor()) + "] Repartiendo ejércitos...");
                super.getComandos().deshabilitarRepartirEjercitos();
                super.getComandos().habilitarAcabarTurno();
            }
        }
    }

    @Override
    public String ayuda() {
        return "repartir ejercitos <número> <nombre_pais>";
    }
}
