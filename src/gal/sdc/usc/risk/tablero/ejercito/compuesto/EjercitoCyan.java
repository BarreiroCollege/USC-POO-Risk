package gal.sdc.usc.risk.tablero.ejercito.compuesto;

import gal.sdc.usc.risk.tablero.ejercito.EjercitoBase;
import gal.sdc.usc.risk.util.Colores;

import java.util.Arrays;

public class EjercitoCyan extends EjercitoBase {
    public EjercitoCyan(int cantidad) {
        super(cantidad, Colores.Color.CELESTE);
    }

    public int[] ataque(int[] valores) {
        if (valores.length == 1) {
            valores[0] += 2;
            return valores;
        }
        return valores;
    }
}
