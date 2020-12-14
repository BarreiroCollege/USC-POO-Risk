package gal.sdc.usc.risk.tablero;

import gal.sdc.usc.risk.excepciones.Errores;
import gal.sdc.usc.risk.jugar.Resultado;
import gal.sdc.usc.risk.tablero.ejercito.EjercitoNuevo;
import gal.sdc.usc.risk.tablero.ejercito.base.EjercitoAmarillo;
import gal.sdc.usc.risk.tablero.ejercito.base.EjercitoAzul;
import gal.sdc.usc.risk.tablero.ejercito.base.EjercitoRojo;
import gal.sdc.usc.risk.tablero.ejercito.compuesto.EjercitoCyan;
import gal.sdc.usc.risk.tablero.ejercito.compuesto.EjercitoVerde;
import gal.sdc.usc.risk.tablero.ejercito.compuesto.EjercitoVioleta;
import gal.sdc.usc.risk.util.Colores;

public abstract class Ejercito implements Comparable<Ejercito> {
    private Integer cantidad;
    private final Colores.Color color;

    protected Ejercito() {
        this(0, null);
    }

    protected Ejercito(int cantidad) {
        this(cantidad, null);
    }

    protected Ejercito(Colores.Color color) {
        this(0, color);
    }

    protected Ejercito(int cantidad, Colores.Color color) {
        this.cantidad = cantidad;
        this.color = color;
    }

    public Integer recibir(Ejercito ejercito) {
        return this.recibir(ejercito, ejercito.cantidad);
    }

    public Integer recibir(Ejercito ejercito, int cantidad) {
        return this.recibir(ejercito, cantidad, false);
    }

    public Integer recibir(Ejercito ejercito, int cantidad, boolean auto) {
        if (cantidad > ejercito.cantidad) {
            cantidad = ejercito.cantidad;
        }
        if (cantidad == 0) {
            if (!auto) {
                Resultado.error(Errores.EJERCITO_NO_DISPONIBLE);
            }
            return null;
        } else if (cantidad < 0) {
            System.err.println("Para hacer esto, cambiar orden de parámetros");
            return null;
        }

        this.add(cantidad);
        ejercito.del(cantidad);
        return cantidad;
    }

    private void add(int ejercitos) {
        this.cantidad += ejercitos;
    }

    private void del(int ejercitos) {
        if (ejercitos > this.cantidad) {
            ejercitos = this.cantidad;
        }
        this.cantidad -= ejercitos;
    }

    public Integer toInt() {
        return this.cantidad;
    }

    public abstract int[] ataque(int[] valores);

    public static class Builder {
        private final Colores.Color color;
        private int cantidad = 0;

        public Builder() {
            this(null);
        }

        public Builder(Colores.Color color) {
            this.color = color;
        }

        public Builder withCantidad(int cantidad) {
            this.cantidad = cantidad;
            return this;
        }

        public Ejercito build() {
            if (color == null) return new EjercitoNuevo(cantidad);

            switch (this.color) {
                case AZUL:
                    return new EjercitoAzul(cantidad);
                case ROJO:
                    return new EjercitoRojo(cantidad);
                case AMARILLO:
                    return new EjercitoAmarillo(cantidad);
                case VIOLETA:
                    return new EjercitoVioleta(cantidad);
                case CELESTE:
                    return new EjercitoCyan(cantidad);
                case VERDE:
                    return new EjercitoVerde(cantidad);
            }

            return null;
        }
    }

    @Override
    public String toString() {
        return "Ejercito{" +
                "cantidad=" + cantidad +
                '}';
    }

    @Override
    public int compareTo(Ejercito o) {
        return cantidad.compareTo(o.cantidad);
    }
}
