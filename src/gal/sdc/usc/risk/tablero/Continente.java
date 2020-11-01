package gal.sdc.usc.risk.tablero;

import gal.sdc.usc.risk.menu.Partida;
import gal.sdc.usc.risk.tablero.valores.Continentes;
import gal.sdc.usc.risk.util.Colores.Color;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Continente extends Partida {
    private final Continentes identificador;
    private final String nombre;
    private final String abreviatura;
    private final Color color;
    private final Integer ejercitos;
    private final HashMap<String, Pais> paises;

    private Continente(Continentes continente, String nombre, String abreviatura, Color color, Integer ejercitos,
                       HashMap<String, Pais> paises) {
        this.identificador = continente;
        this.nombre = nombre;
        this.abreviatura = abreviatura;
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

    public String getAbreviatura() {
        return this.abreviatura;
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

    public List<Pais> getPaisesPorJugador(Jugador jugador) {
        return this.paises.values().stream().filter(pais -> pais.getJugador().equals(jugador)).collect(Collectors.toList());
    }

    public List<Pais> getPaisesFrontera() {
        return this.paises.values().stream()
                .filter(pais -> pais.getFronteras().getTodas().stream().anyMatch(pais1 -> !pais1.getContinente().equals(this)))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "Continente{" +
                "identificador=" + identificador +
                ", nombre='" + nombre + '\'' +
                ", abreviatura='" + abreviatura + '\'' +
                ", color=" + color +
                ", ejercitos=" + ejercitos +
                ", paises=" + paises +
                '}';
    }

    public static class Builder {
        private final Continentes continente;
        private final HashMap<String, Pais> paises;
        private String nombre;
        private String abreviatura;
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

        public Builder withAbreviatura(String abreviatura) {
            this.abreviatura = abreviatura;
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
            this.paises.put(pais.getAbreviatura(), pais);
            return this;
        }

        public Continente build() {
            if (continente == null) {
                // TODO
            } else if (nombre == null) {
                // TODO
            } else if (abreviatura == null) {
                // TODO
            } else if (color == null) {
                // TODO
            } else if (ejercitos == null) {
                // TODO
            } else if (paises.size() == 0) {
                // TODO: Warn
            } else {
                return new Continente(this.continente, this.nombre, this.abreviatura, this.color, this.ejercitos, this.paises);
            }
            return null;
        }
    }
}
