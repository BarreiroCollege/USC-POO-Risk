package gal.sdc.usc.risk.jugar;

import gal.sdc.usc.risk.comandos.Ejecutor;
import gal.sdc.usc.risk.comandos.EjecutorAccion;
import gal.sdc.usc.risk.comandos.IComando;
import gal.sdc.usc.risk.comandos.generico.Ayuda;
import gal.sdc.usc.risk.comandos.generico.ObtenerColor;
import gal.sdc.usc.risk.comandos.generico.ObtenerContinente;
import gal.sdc.usc.risk.comandos.generico.ObtenerFrontera;
import gal.sdc.usc.risk.comandos.generico.ObtenerPaises;
import gal.sdc.usc.risk.comandos.generico.Salir;
import gal.sdc.usc.risk.comandos.generico.VerMapa;
import gal.sdc.usc.risk.comandos.partida.AcabarTurno;
import gal.sdc.usc.risk.comandos.partida.AsignarCarta;
import gal.sdc.usc.risk.comandos.partida.AtacarPais;
import gal.sdc.usc.risk.comandos.partida.AtacarPaisDados;
import gal.sdc.usc.risk.comandos.partida.CambiarCartas;
import gal.sdc.usc.risk.comandos.partida.CambiarCartasTodas;
import gal.sdc.usc.risk.comandos.partida.DescribirContinente;
import gal.sdc.usc.risk.comandos.partida.DescribirJugador;
import gal.sdc.usc.risk.comandos.partida.DescribirPais;
import gal.sdc.usc.risk.comandos.partida.Jugador;
import gal.sdc.usc.risk.comandos.partida.Rearmar;
import gal.sdc.usc.risk.comandos.preparacion.AsignarMision;
import gal.sdc.usc.risk.comandos.preparacion.AsignarMisiones;
import gal.sdc.usc.risk.comandos.preparacion.AsignarPais;
import gal.sdc.usc.risk.comandos.preparacion.AsignarPaises;
import gal.sdc.usc.risk.comandos.preparacion.CrearJugador;
import gal.sdc.usc.risk.comandos.preparacion.CrearJugadores;
import gal.sdc.usc.risk.comandos.preparacion.CrearMapa;
import gal.sdc.usc.risk.comandos.preparacion.RepartirEjercito;
import gal.sdc.usc.risk.comandos.preparacion.RepartirEjercitos;
import gal.sdc.usc.risk.gui.PrincipalController;
import gal.sdc.usc.risk.gui.componentes.mapa.MapaController;
import gal.sdc.usc.risk.gui.componentes.mapa.MapaSeleccion;
import gal.sdc.usc.risk.gui.componentes.nuevo.NuevoEjercitoAsignado;
import gal.sdc.usc.risk.tablero.Mapa;
import gal.sdc.usc.risk.tablero.Pais;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class ComandosDisponibles {
    private final List<Class<? extends IComando>> lista;

    public ComandosDisponibles() {
        this.lista = new ArrayList<>();

        add(Ayuda.class);
        add(Salir.class);
        add(VerMapa.class);
        add(ObtenerColor.class);
        add(ObtenerContinente.class);
        add(ObtenerFrontera.class);
        add(ObtenerPaises.class);
        // Iniciar la partida
        this.iniciarPreparacion();
    }

    private void add(Class<? extends IComando> comando) {
        if (!lista.contains(comando)) {
            lista.add(comando);
        }
    }

    private void remove(Class<? extends IComando> comando) {
        lista.remove(comando);
    }

    public List<Class<? extends IComando>> getLista() {
        return this.lista;
    }

    public void iniciarPreparacion() {
        add(CrearMapa.class);
        /* add(CrearJugador.class);
        add(CrearJugadores.class);
        add(AsignarMision.class);
        add(AsignarMisiones.class);
        add(AsignarPais.class);
        add(AsignarPaises.class);
        add(RepartirEjercito.class);
        add(RepartirEjercitos.class); */
    }

    public void habilitarAcabarTurno() {
        add(AcabarTurno.class);
    }

    public void deshabilitarAcabarTurno() {
        remove(AcabarTurno.class);
    }

    public void acabarPreparacion() {
        remove(CrearMapa.class);
        remove(CrearJugador.class);
        remove(CrearJugadores.class);
        remove(AsignarMision.class);
        remove(AsignarMisiones.class);
        remove(AsignarPais.class);
        remove(AsignarPaises.class);
        remove(RepartirEjercito.class);
        remove(RepartirEjercitos.class);
    }

    public boolean isPaisesAsignados(Mapa mapa) {
        return this.getLista().contains(AsignarPais.class)
                && mapa != null
                && mapa.getPaisesPorCeldas().values().stream().noneMatch(p -> p.getJugador() == null);
    }

    public void iniciarPartida(gal.sdc.usc.risk.tablero.Jugador jugador) {
        this.acabarPreparacion();

        add(AcabarTurno.class);
        add(DescribirContinente.class);
        add(DescribirJugador.class);
        add(DescribirPais.class);
        add(Jugador.class);

        this.iniciarTurno(jugador);
    }

    public void iniciarTurno(gal.sdc.usc.risk.tablero.Jugador jugador) {
        this.acabarTurno();

        add(CambiarCartas.class);
        add(CambiarCartasTodas.class);

        if (jugador.getEjercitosPendientes().toInt() == 0) {
            add(AtacarPais.class);
            add(AtacarPaisDados.class);
        } else {
            add(RepartirEjercito.class);
            add(RepartirEjercitos.class);
        }
    }

    public void acabarTurno() {
        remove(CambiarCartas.class);
        remove(CambiarCartasTodas.class);
        remove(RepartirEjercito.class);
        remove(RepartirEjercitos.class);
        remove(AtacarPais.class);
        remove(AtacarPaisDados.class);
        remove(Rearmar.class);
        remove(AsignarCarta.class);
    }

    public void acabarPartida() {
        this.acabarTurno();
        remove(AcabarTurno.class);
    }

    /* --- */

    public void mapaCreado() {
        remove(CrearMapa.class);
        add(CrearJugador.class);
        add(CrearJugadores.class);
    }

    public void deshabilitarCrearJugadores() {
        remove(CrearJugador.class);
        remove(CrearJugadores.class);
    }

    public void habilitarAsignarMisiones() {
        add(AsignarMision.class);
        add(AsignarMisiones.class);
    }

    public void deshabilitarAsignarMisiones() {
        remove(AsignarMision.class);
        remove(AsignarMisiones.class);
    }

    public void habilitarAsignarPaises() {
        this.deshabilitarAsignarMisiones();
        add(AsignarPais.class);
        add(AsignarPaises.class);
        MapaController.setSeleccionar(MapaSeleccion.VACIO);
        PrincipalController.mensaje("Para repartir los países, selecciona en el mapa los que quieras asignar, y luego " +
                "clica en el botón.");
    }

    public void deshabilitarAsignarPaises() {
        remove(AsignarPais.class);
        remove(AsignarPaises.class);
        MapaController.setSeleccionar(MapaSeleccion.NINGUNO);
        PrincipalController.mensaje("Para repartir ejércitos, puedes hacer un click simple en el país para asignarle " +
                "un ejército, o mantener pulsado para asignar el número deseado.");
    }

    public void habilitarRepartirEjercitos(gal.sdc.usc.risk.tablero.Jugador jugadorTurno) {
        this.deshabilitarAsignarPaises();
        add(RepartirEjercito.class);
        add(RepartirEjercitos.class);

        StackPane parent = (StackPane) PrincipalController.getScene().lookup("#stack-pane");
        MapaController.setSeleccionar(MapaSeleccion.JUGADOR);
        MapaController.setAccion(new EjecutorAccion() {
            @Override
            public void onClick(Object o) {
                Pais pais = (Pais) o;
                if (pais.getJugador().equals(jugadorTurno)) {
                    Ejecutor.comando("repartir ejercito 1 " + pais.getAbreviatura(), null);
                } else {
                    PrincipalController.mensaje("Este país no es del jugador actual");
                }
            }

            @Override
            public void onLongClick(Object o) {
                Pais pais = (Pais) o;
                if (pais.getJugador().equals(jugadorTurno)) {
                    NuevoEjercitoAsignado.generarDialogo(parent, pais);
                } else {
                    PrincipalController.mensaje("Este país no es del jugador actual");
                }
            }
        });
    }

    public void deshabilitarRepartirEjercitosAuto() {
        remove(RepartirEjercitos.class);
    }

    public void deshabilitarRepartirEjercitos() {
        MapaController.setAccion(null);
        MapaController.setSeleccionar(MapaSeleccion.NINGUNO);
        remove(RepartirEjercito.class);
    }

    /* --- */

    public void repartiendo() {
        remove(CambiarCartas.class);
        remove(CambiarCartasTodas.class);
    }

    public void atacando() {
        this.repartiendo();
        remove(RepartirEjercito.class);
        remove(RepartirEjercitos.class);
    }

    public void atacar() {
        this.atacando();
        add(AtacarPais.class);
        add(AtacarPaisDados.class);
    }

    public void paisConquistado() {
        add(Rearmar.class);
        add(AsignarCarta.class);
    }

    public void rearmando() {
        this.atacando();
        remove(AtacarPais.class);
        remove(AtacarPaisDados.class);
        remove(Rearmar.class);
    }

    public void cartaEscogida() {
        remove(AsignarCarta.class);
    }
}
