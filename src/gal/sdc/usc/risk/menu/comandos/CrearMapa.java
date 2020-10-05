package gal.sdc.usc.risk.menu.comandos;

import gal.sdc.usc.risk.menu.Comando;
import gal.sdc.usc.risk.tablero.Continente;
import gal.sdc.usc.risk.tablero.Mapa;

public class CrearMapa implements Comando {
    public CrearMapa() {
        Continente africa = new Continente();
        Continente europa = new Continente();

        Mapa mapa = new Mapa.Builder()
                .withContinente(africa)
                .withContinente(europa)
                .build();
    }
}
