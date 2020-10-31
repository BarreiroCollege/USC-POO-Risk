package gal.sdc.usc.risk.tablero;

import gal.sdc.usc.risk.tablero.valores.Paises;
import gal.sdc.usc.risk.util.Colores;

public class Pais {
    public final static int MAX_LENGTH_NOMBRE = 10;

    private final Paises identificador;
    private final String nombre;
    private final String abreviatura;
    private final Celda celda;
    private Continente continente;

    private Fronteras fronteras = null;

    private Jugador jugador = null;
    private final Ejercito ejercito;

    private Pais(Paises identificador, String nombre, String abreviatura, Celda celda) {
        this.identificador = identificador;
        this.nombre = nombre;
        this.abreviatura = abreviatura;
        this.celda = celda;
        this.ejercito = new Ejercito();
    }

    public boolean setContinente(Continente continente) {
        if (this.continente != null) {
            return false;
        }
        this.continente = continente;
        return true;
    }

    public String getNombre() {
        return nombre;
    }

    public String getAbreviatura() {
        return this.abreviatura;
    }

    public Continente getContinente() {
        return this.continente;
    }

    public Colores.Color getColor() {
        if (continente == null) {
            return null;
        }
        return this.continente.getColor();
    }

    public Celda getCelda() {
        return this.celda;
    }

    public boolean setFronteras(Fronteras fronteras) {
        if (this.fronteras == null) {
            this.fronteras = fronteras;
            return true;
        }
        return false;
    }

    public Fronteras getFronteras() {
        return fronteras;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public Ejercito getEjercito() {
        return ejercito;
    }

    @Override
    public String toString() {
        return "Pais{" +
                "nombre='" + nombre + '\'' +
                ", abreviatura='" + abreviatura + '\'' +
                ", continente=" + (continente != null ? continente.getNombre() : continente) +
                ", celda=" + celda +
                '}';
    }

    public static class Builder {
        private final Paises pais;
        private String nombre;
        private String abreviatura;
        private Celda celda = null;

        public Builder(Paises pais) {
            this.pais = pais;
        }

        public Builder withNombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public Builder withAbreviatura(String nombre) {
            this.abreviatura = nombre;
            return this;
        }

        public Builder withCelda(Celda celda) {
            this.celda = celda;
            return this;
        }

        public Pais build() {
            if (this.pais == null) {
                // TODO
            } else if (this.nombre == null) {
                // TODO
            } else if (this.abreviatura == null) {
                // TODO
            } else if (this.celda == null) {
                // TODO
            } else {
                return new Pais(pais, nombre, abreviatura, celda);
            }
            return null;
        }
    }
}
