package gal.sdc.usc.risk.tablero;

import gal.sdc.usc.risk.jugar.Partida;
import gal.sdc.usc.risk.tablero.valores.Equipamientos;
import gal.sdc.usc.risk.tablero.valores.SubEquipamientos;
import gal.sdc.usc.risk.util.Colores.Color;

import java.util.ArrayList;
import java.util.List;

public class Jugador extends Partida {
    private final String nombre;
    private final Color color;
    private final Ejercito ejercitosPendientes;
    private final List<Carta> cartas;
    private Mision mision = null;

    private Jugador(String nombre, Color color) {
        this.nombre = nombre;
        this.color = color;
        this.ejercitosPendientes = new Ejercito();
        this.cartas = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public Color getColor() {
        return color;
    }

    public Mision getMision() {
        return mision;
    }

    public List<Pais> getPaises() {
        return super.getMapa().getPaisesPorJugador(this);
    }

    public List<Continente> getContinentes() {
        return super.getMapa().getContinentesPorJugador(this);
    }

    public Ejercito getEjercitosPendientes() {
        return this.ejercitosPendientes;
    }

    public List<Carta> getCartas() {
        return cartas;
    }

    public Carta getCarta(SubEquipamientos subEquipamiento, Pais pais) {
        for (Carta carta : this.getCartas()) {
            if (carta.getSubEquipamiento().equals(subEquipamiento) && carta.getPais().equals(pais)) {
                return carta;
            }
        }
        return null;
    }

    public Integer getNumEjercitos() {
        Integer i = 0;
        for (Pais pais : this.getPaises()) {
            i += pais.getEjercito().toInt();
        }
        return i;
    }

    public boolean setMision(Mision mision) {
        if (this.mision == null) {
            this.mision = mision;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Jugador{" +
                "nombre='" + nombre + '\'' +
                ", color=" + color +
                ", ejercitosPendientes=" + ejercitosPendientes +
                ", cartas=" + cartas +
                ", mision=" + mision +
                '}';
    }

    public static class Builder {
        private final String nombre;
        private Color color;

        public Builder(String nombre) {
            this.nombre = nombre;
        }

        public Builder withColor(Color color) {
            this.color = color;
            return this;
        }

        public Jugador build() {
            if (nombre == null) {
                System.err.println("Jugador.Builder nombre=null");
            } else if (color == null) {
                System.err.println("Jugador.Builder color=null");
            } else {
                return new Jugador(nombre, color);
            }
            return null;
        }
    }
}
