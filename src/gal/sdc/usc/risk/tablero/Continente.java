package gal.sdc.usc.risk.tablero;

import gal.sdc.usc.risk.tablero.valores.Continentes;
import gal.sdc.usc.risk.util.Colores.Color;

import java.util.HashMap;

public class Continente {
    private final Continentes identificador;
    private final String nombre;
    private final Color color;
    private final Integer ejercitos;
    private final HashMap<String, Pais> paises;

    private Continente(Continentes continente, String nombre, Color color, Integer ejercitos, HashMap<String, Pais> paises) {
        this.identificador = continente;
        this.nombre = nombre;
        this.color = color;
        this.ejercitos = ejercitos;
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

    public Integer getEjercitos() {
        return this.ejercitos;
    }

    public HashMap<String, Pais> getPaises() {
        return this.paises;
    }

    @Override
    public String toString() {
        return "Continente{" +
                "identificador=" + identificador +
                ", nombre='" + nombre + '\'' +
                ", color=" + color +
                ", ejercitos=" + ejercitos +
                ", paises=" + paises +
                '}';
    }

    public static class Builder {
        private final Continentes continente;
        private final HashMap<String, Pais> paises;
        private String nombre;
        private Color color;
        private Integer ejercitos;

        public Builder(Continentes continente) {
            this.continente = continente;
            this.paises = new HashMap<>();
        }

        public Builder withNombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public Builder withColor(Color color) {
            this.color = color;
            return this;
        }

        public Builder withEjercitos(Integer ejercitos) {
            this.ejercitos = ejercitos;
            return this;
        }

        public Builder withPais(Pais pais) {
            this.paises.put(pais.getNombre(), pais);
            return this;
        }

        public Continente build() {
            if (continente == null) {
                // TODO
            } else if (nombre == null) {
                // TODO
            } else if (color == null) {
                // TODO
            } else if (ejercitos == null) {
                // TODO
            } else if (paises.size() == 0) {
                // TODO: Warn
            } else {
                return new Continente(this.continente, this.nombre, this.color, this.ejercitos, this.paises);
            }
            return null;
        }
    }
}
