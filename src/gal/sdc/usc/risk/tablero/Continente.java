package gal.sdc.usc.risk.tablero;

import gal.sdc.usc.risk.util.Colores.Color;

import java.util.ArrayList;
import java.util.List;

public class Continente {
    private final Continentes identificador;
    private final String nombre;
    private final Color color;
    private final List<Pais> paises;

    private Continente(Continentes continente, String nombre, Color color, List<Pais> paises) {
        this.identificador = continente;
        this.nombre = nombre;
        this.color = color;
        this.paises = paises;
    }

    public Continentes getContinente() {
        return this.identificador;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Color getColor() {
        return this.color;
    }

    public List<Pais> getPaises() {
        return this.paises;
    }

    public static class Builder {
        private final Continentes continente;
        private String nombre;
        private Color color;
        private final List<Pais> paises;

        public Builder(Continentes continente) {
            this.continente = continente;
            this.paises = new ArrayList<>();
        }

        public Builder withNombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public Builder withColor(Color color) {
            this.color = color;
            return this;
        }

        public Builder withPais(Pais pais) {
            this.paises.add(pais);
            return this;
        }

        public Continente build() {
            if (continente == null) {
                // TODO
            } else if (nombre == null) {
                // TODO
            } else if (color == null) {
                // TODO
            } else if (paises.size() == 0) {
                // TODO: Warn
            } else {
                return new Continente(this.continente, this.nombre, this.color, this.paises);
            }
            return null;
        }
    }

    @Override
    public String toString() {
        return "Continente{" +
                "nombre='" + nombre + '\'' +
                ", color=" + color +
                ", paises=" + paises +
                '}';
    }
}
