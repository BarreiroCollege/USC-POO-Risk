package gal.sdc.usc.risk.menu.comandos.mapa;

import gal.sdc.usc.risk.menu.Resultado;
import gal.sdc.usc.risk.menu.comandos.Comando;
import gal.sdc.usc.risk.menu.Partida;
import gal.sdc.usc.risk.tablero.Celda;
import gal.sdc.usc.risk.tablero.Continente;
import gal.sdc.usc.risk.tablero.Mapa;
import gal.sdc.usc.risk.tablero.Pais;
import gal.sdc.usc.risk.tablero.valores.Continentes;
import gal.sdc.usc.risk.tablero.valores.Paises;

import java.util.HashMap;


public class CrearMapa extends Partida implements Comando {
    private final Mapa mapa;

    public CrearMapa() {
        Mapa.Builder preMapa = new Mapa.Builder();

        // Primero continentes
        for (Continentes continente : Continentes.values()) {
            Continente.Builder preContinente = new Continente.Builder(continente)
                    .withNombre(continente.getNombre())
                    .withColor(continente.getColor())
                    .withEjercitos(continente.getEjercitos());

            // Luego países del continente
            for (Paises pais : Paises.values()) {
                if (pais.getContinente() != continente) {
                    continue;
                }

                Celda celda = new Celda.Builder()
                        .withX(pais.getX())
                        .withY(pais.getY())
                        .build();

                Pais nuevoPais = new Pais.Builder(pais)
                        .withNombre(pais.getNombre())
                        .withCelda(celda)
                        .build();
                preContinente.withPais(nuevoPais);
                preMapa.withPais(nuevoPais);
            }

            Continente nuevoContinente = preContinente.build();
            for (Pais pais : nuevoContinente.getPaises().values()) {
                if (!pais.setContinente(nuevoContinente)) {
                    // TODO
                }
            }
            preMapa.withContinente(nuevoContinente);
        }

        this.mapa = preMapa.build();
        System.out.println(mapa);
    }

    public Mapa getMapa() {
        return this.mapa;
    }
}
