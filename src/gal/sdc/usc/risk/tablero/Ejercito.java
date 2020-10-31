package gal.sdc.usc.risk.tablero;

import gal.sdc.usc.risk.menu.Resultado;
import gal.sdc.usc.risk.tablero.valores.Errores;

public class Ejercito {
    private Integer cantidad;

    public Ejercito() {
        this(0);
    }

    public Ejercito(int cantidad) {
        this.cantidad = cantidad;
    }

    public Integer recibir(Ejercito ejercito) {
        return this.recibir(ejercito, ejercito.cantidad);
    }

    public Integer recibir(Ejercito ejercito, int cantidad) {
        if (cantidad > ejercito.cantidad) {
            cantidad = ejercito.cantidad;
        }
        if (cantidad == 0) {
            Resultado.error(Errores.EJERCITO_NO_DISPONIBLE);
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

    @Override
    public String toString() {
        return cantidad.toString();
    }
}
