package gal.sdc.usc.risk.menu.comandos.preparacion;

import gal.sdc.usc.risk.menu.Partida;
import gal.sdc.usc.risk.menu.Resultado;
import gal.sdc.usc.risk.menu.comandos.Comando;
import gal.sdc.usc.risk.menu.comandos.Comandos;
import gal.sdc.usc.risk.menu.comandos.Estado;
import gal.sdc.usc.risk.menu.comandos.IComando;
import gal.sdc.usc.risk.salida.SalidaObjeto;
import gal.sdc.usc.risk.tablero.Jugador;
import gal.sdc.usc.risk.tablero.Pais;
import gal.sdc.usc.risk.tablero.valores.Errores;

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

        Pais paisFinal = super.getMapa().getPaisPorNombre(pais);
        if (paisFinal == null) {
            Resultado.error(Errores.PAIS_NO_EXISTE);
            return;
        }
        if (paisFinal.getJugador() == null) {
            Resultado.error(Errores.PAIS_NO_PERTENECE);
            return;
        }
        if (!paisFinal.getJugador().equals(super.getJugadorTurno())) {
            Resultado.error(Errores.PAIS_NO_PERTENECE);
            return;
        }

        Integer asignado = paisFinal.getEjercito().recibir(paisFinal.getJugador().getEjercitosPendientes(), numero);
        if (asignado != null) {
            SalidaObjeto salida = new SalidaObjeto();
            salida.put("pais", paisFinal.getNombre());
            salida.put("jugador", paisFinal.getJugador().getNombre());
            salida.put("numeroEjercitosAsignados", asignado);
            salida.put("numeroEjercitosTotales", paisFinal.getEjercito());

            List<SalidaObjeto> paisesOcupadosContinente = new ArrayList<>();
            List<Pais> paisesJugadorContinente = super.getMapa().getPaisesPorJugador(paisFinal.getJugador()).stream()
                    .filter(paisFiltro -> paisFiltro.getContinente().equals(paisFinal.getContinente()))
                    .collect(Collectors.toList());
            for (Pais paisOcupado : paisesJugadorContinente) {
                paisesOcupadosContinente.add(new SalidaObjeto()
                        .put(paisOcupado.getNombre(), paisOcupado.getEjercito()));
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
                super.getComandos().atacar();
            }
        } else {
            if (super.getJugadores().values().stream().filter(j -> j.getEjercitosPendientes().toInt() > 0).findAny().orElse(null) == null) {
                super.iniciar();
                // super.moverTurno();
            } else if (jugador.getEjercitosPendientes().toInt() == 0) {
                // super.moverTurno();
                // Resultado.out("[" + new Colores(super.getJugadorTurno().getNombre(), super.getJugadorTurno().getColor()) + "] Repartiendo ejércitos...");
            }
        }
    }

    @Override
    public String ayuda() {
        return "repartir ejercitos <número> <nombre_pais>";
    }
}
