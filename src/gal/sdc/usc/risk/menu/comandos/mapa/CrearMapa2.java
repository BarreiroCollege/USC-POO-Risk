package gal.sdc.usc.risk.menu.comandos.mapa;

import gal.sdc.usc.risk.menu.Partida;
import gal.sdc.usc.risk.menu.comandos.Comando;
import gal.sdc.usc.risk.tablero.Celda;
import gal.sdc.usc.risk.tablero.Continente;
import gal.sdc.usc.risk.tablero.Mapa;
import gal.sdc.usc.risk.tablero.Pais;
import gal.sdc.usc.risk.tablero.valores.Continentes;
import gal.sdc.usc.risk.tablero.valores.Paises;


public class CrearMapa2 extends Partida implements Comando {
    public CrearMapa2() {
        System.out.println(super.getMapa());
    }
}
