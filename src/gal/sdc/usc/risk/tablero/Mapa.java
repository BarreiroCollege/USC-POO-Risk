package gal.sdc.usc.risk.tablero;

import gal.sdc.usc.risk.util.Colores;

import java.util.HashMap;

public class Mapa {
    public static int MAX_PAISES_X = 11;
    public static int MAX_PAISES_Y = 8;

    private final HashMap<String, Continente> continentes;
    private final HashMap<Celda, Pais> paises;

    private boolean tieneFronteras = false;

    private Mapa(HashMap<String, Continente> continentes, HashMap<Celda, Pais> paises) {
        this.continentes = continentes;
        this.paises = paises;
    }

    public HashMap<String, Continente> getContinentes() {
        return continentes;
    }

    public HashMap<Celda, Pais> getPaisesPorCeldas() {
        return paises;
    }

    private void asignarFronteras() {
        if (this.tieneFronteras) {
            return;
        }

        Celda celda;
        Pais pais, aux;
        for (int i = 0; i < Mapa.MAX_PAISES_Y; i++) {
            for (int j = 0; j < Mapa.MAX_PAISES_X; j++) {
                celda = new Celda.Builder().withX(j).withY(i).build();
                pais = this.paises.get(celda);
                if (pais == null) {
                    continue;
                }

                Fronteras.Builder preFronteras = new Fronteras.Builder();

                aux = this.paises.get(celda.getNorte());
                if (aux != null) {
                    preFronteras.withNorte(aux);
                }
                aux = this.paises.get(celda.getSur());
                if (aux != null) {
                    preFronteras.withSur(aux);
                }
                aux = this.paises.get(celda.getEste());
                if (this.paises.get(celda.getEste()) != null) {
                    preFronteras.withEste(aux);
                }
                aux = this.paises.get(celda.getOeste());
                if (this.paises.get(celda.getOeste()) != null) {
                    preFronteras.withOeste(aux);
                }

                if (!pais.setFronteras(preFronteras.build())) {
                    // TODO
                }
            }
        }

        this.tieneFronteras = true;
    }

    private String linea(int i) {
        StringBuilder out = new StringBuilder();
        for (int k = 0; k < Mapa.MAX_PAISES_X; k++) {
            if (i == 0) {
                if (k == 0) {
                    out.append("╔");
                }
                out.append("════════════");
                if ((k + 1) == Mapa.MAX_PAISES_X) {
                    out.append("╗");
                } else {
                    out.append("╤");
                }
            } else if (i == Mapa.MAX_PAISES_Y) {
                if (k == 0) {
                    out.append("╚");
                }
                out.append("════════════");
                if ((k + 1) == Mapa.MAX_PAISES_X) {
                    out.append("╝");
                } else {
                    out.append("╧");
                }
            } else {
                if (k == 0) {
                    out.append("╟");
                }
                out.append("────────────");
                if ((k + 1) == Mapa.MAX_PAISES_X) {
                    out.append("╢");
                } else {
                    out.append("┼");
                }
            }
        }
        out.append("\n");
        return out.toString();
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        Celda celda;
        Pais pais;
        String texto;
        StringBuilder nombreTemporal;

        // TODO: Cambiar a columnas
        for (int i = 0; i < Mapa.MAX_PAISES_Y; i++) {
            // Imprimir paises con sus colores
            for (int j = 0; j < Mapa.MAX_PAISES_X; j++) {
                if (j == 0) {
                    out.append(this.linea(i));
                }

                celda = new Celda.Builder().withX(j).withY(i).build();
                pais = this.paises.get(celda);

                if (j == 0) {
                    texto = "║";
                } else {
                    texto = "│";
                }

                if (pais == null) {
                    texto += String.format(" %-18s ", new Colores(""));
                } else {
                    nombreTemporal = new StringBuilder(pais.getNombre());
                    while (nombreTemporal.length() < Pais.MAX_LENGTH_NOMBRE) {
                        nombreTemporal.append(" ");
                    }
                    texto += String.format(" %-20s ", new Colores(nombreTemporal.toString(), null, pais.getColor()));
                }

                if ((j + 1) == Mapa.MAX_PAISES_X) {
                    texto += "║\n";
                }

                out.append(texto);
            }

            for (int j = 0; j < Mapa.MAX_PAISES_X; j++) {
                celda = new Celda.Builder().withX(j).withY(i).build();
                pais = null;

                if (j == 0) {
                    texto = "║";
                } else {
                    texto = "│";
                }

                if (pais == null) {
                    texto += String.format(" %-18s ", new Colores(""));
                } else {
                    nombreTemporal = new StringBuilder(pais.getNombre());
                    while (nombreTemporal.length() < Pais.MAX_LENGTH_NOMBRE) {
                        nombreTemporal.append(" ");
                    }
                    texto += String.format(" %-20s ", new Colores(nombreTemporal.toString(), null, pais.getColor()));
                }

                if ((j + 1) == Mapa.MAX_PAISES_X) {
                    texto += "║\n";
                }

                out.append(texto);
            }
        }
        out.append(this.linea(Mapa.MAX_PAISES_Y));
        return out.toString();
    }

    public void imprimir() {
        System.out.println(this.toString());
    }

    public static class Builder {
        private final HashMap<String, Continente> continentes;
        private final HashMap<Celda, Pais> paises;

        public Builder() {
            continentes = new HashMap<>();
            paises = new HashMap<>();
        }

        public Builder withContinente(Continente continente) {
            this.continentes.put(continente.getNombre(), continente);
            return this;
        }

        public Builder withPais(Pais pais) {
            this.paises.put(pais.getCelda(), pais);
            return this;
        }

        public Mapa build() {
            // TODO: generarFronteras
            Mapa mapa = new Mapa(continentes, paises);
            mapa.asignarFronteras();
            return mapa;
        }
    }
}
