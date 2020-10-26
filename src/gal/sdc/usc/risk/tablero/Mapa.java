package gal.sdc.usc.risk.tablero;

import gal.sdc.usc.risk.util.Colores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Mapa {
    public static int MAX_PAISES_X = 11;
    public static int MAX_PAISES_Y = 8;

    private final List<Continente> continentes;
    private final HashMap<Celda, Pais> paises;

    public List<Continente> getContinentes() {
        return continentes;
    }

    public HashMap<Celda, Pais> getPaises() {
        return paises;
    }

    private Mapa(List<Continente> continentes, HashMap<Celda, Pais> paises) {
        this.continentes = continentes;
        this.paises = paises;
    }

    public static class Builder {
        private final List<Continente> continentes;
        private final HashMap<Celda, Pais> paises;

        public Builder() {
            continentes = new ArrayList<>();
            paises = new HashMap<>();
        }

        public Builder withContinente(Continente continente) {
            this.continentes.add(continente);
            return this;
        }

        public Builder withPais(Celda celda, Pais pais) {
            this.paises.put(celda, pais);
            return this;
        }

        public Mapa build() {
            // TODO: generarFronteras
            Mapa mapa = new Mapa(continentes, paises);
            return mapa;
        }
    }

    private String linea() {
        StringBuilder out = new StringBuilder();
        for (int k = 0; k < Mapa.MAX_PAISES_X; k++) {
            out.append("-------------");
        }
        out.append("-\n");
        return out.toString();
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        Celda celda;
        Pais pais;
        String texto;
        StringBuilder nombreTemporal;

        for (int i = 0; i < Mapa.MAX_PAISES_Y; i++) {
            for (int j = 0; j < Mapa.MAX_PAISES_X; j++) {
                if (j == 0) {
                    out.append(this.linea());
                }


                celda = new Celda.Builder().withX(j).withY(i).build();
                pais = this.paises.get(celda);
                if (pais == null) {
                    texto = String.format("| %-18s ", new Colores(""));
                } else {
                    nombreTemporal = new StringBuilder(pais.getNombre());
                    while (nombreTemporal.length() < Pais.MAX_LENGTH_NOMBRE) {
                        nombreTemporal.append(" ");
                    }
                    texto = String.format("| %-20s ", new Colores(nombreTemporal.toString(), null, pais.getColor()));
                }

                out.append(texto);
                if ((j + 1) == Mapa.MAX_PAISES_X) {
                    out.append("|");
                }


                if ((j + 1) == Mapa.MAX_PAISES_X) {
                    out.append("\n");
                    if ((i + 1) == Mapa.MAX_PAISES_Y) {
                        out.append(this.linea());
                    }
                }
            }
        }
        return out.toString();
    }
}
