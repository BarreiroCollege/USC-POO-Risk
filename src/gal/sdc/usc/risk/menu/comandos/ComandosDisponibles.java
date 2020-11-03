package gal.sdc.usc.risk.menu.comandos;

import gal.sdc.usc.risk.menu.Partida;
import gal.sdc.usc.risk.menu.comandos.generico.Ayuda;
import gal.sdc.usc.risk.menu.comandos.generico.ObtenerColor;
import gal.sdc.usc.risk.menu.comandos.generico.ObtenerContinente;
import gal.sdc.usc.risk.menu.comandos.generico.ObtenerFrontera;
import gal.sdc.usc.risk.menu.comandos.generico.ObtenerPaises;
import gal.sdc.usc.risk.menu.comandos.generico.VerMapa;
import gal.sdc.usc.risk.menu.comandos.partida.AcabarTurno;
import gal.sdc.usc.risk.menu.comandos.partida.AsignarCarta;
import gal.sdc.usc.risk.menu.comandos.partida.AtacarPais;
import gal.sdc.usc.risk.menu.comandos.partida.AtacarPaisDados;
import gal.sdc.usc.risk.menu.comandos.partida.CambiarCartas;
import gal.sdc.usc.risk.menu.comandos.partida.CambiarCartasTodas;
import gal.sdc.usc.risk.menu.comandos.partida.DescribirContinente;
import gal.sdc.usc.risk.menu.comandos.partida.DescribirJugador;
import gal.sdc.usc.risk.menu.comandos.partida.DescribirPais;
import gal.sdc.usc.risk.menu.comandos.partida.Jugador;
import gal.sdc.usc.risk.menu.comandos.partida.Rearmar;
import gal.sdc.usc.risk.menu.comandos.preparacion.AsignarMision;
import gal.sdc.usc.risk.menu.comandos.preparacion.AsignarMisiones;
import gal.sdc.usc.risk.menu.comandos.preparacion.AsignarPais;
import gal.sdc.usc.risk.menu.comandos.preparacion.AsignarPaises;
import gal.sdc.usc.risk.menu.comandos.preparacion.CrearJugador;
import gal.sdc.usc.risk.menu.comandos.preparacion.CrearJugadores;
import gal.sdc.usc.risk.menu.comandos.preparacion.CrearMapa;
import gal.sdc.usc.risk.menu.comandos.preparacion.RepartirEjercito;
import gal.sdc.usc.risk.menu.comandos.preparacion.RepartirEjercitos;

import java.util.ArrayList;
import java.util.List;

public class ComandosDisponibles extends Partida {
    private final List<Class<? extends IComando>> lista;

    public ComandosDisponibles() {
        this.lista = new ArrayList<>();

        add(Ayuda.class);
        add(VerMapa.class);
        add(ObtenerColor.class);
        add(ObtenerContinente.class);
        add(ObtenerFrontera.class);
        add(ObtenerPaises.class);
        // Iniciar la partida
        add(CrearMapa.class);
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

    public void iniciarPartida() {
        this.acabarPreparacion();

        add(AcabarTurno.class);
        add(DescribirContinente.class);
        add(DescribirJugador.class);
        add(DescribirPais.class);
        add(Jugador.class);

        this.iniciarTurno();
    }

    public void iniciarTurno() {
        this.acabarTurno();

        add(CambiarCartas.class);
        add(CambiarCartasTodas.class);

        if (super.getJugadorTurno().getEjercitosPendientes().toInt() == 0) {
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
    }

    public void deshabilitarAsignarPaises() {
        remove(AsignarPais.class);
        remove(AsignarPaises.class);
    }

    public void habilitarRepartirEjercitos() {
        this.deshabilitarAsignarPaises();
        add(RepartirEjercito.class);
        add(RepartirEjercitos.class);
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
