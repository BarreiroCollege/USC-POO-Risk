package gal.sdc.usc.risk.tablero.ejercito.base;

import gal.sdc.usc.risk.tablero.ejercito.EjercitoBase;
import gal.sdc.usc.risk.util.Colores;

public class EjercitoAmarillo extends EjercitoBase {
    public EjercitoAmarillo(int cantidad) {
        super(cantidad, Colores.Color.AMARILLO);
    }

    public int[] ataque(int[] valores) {
        if (valores.length == 1) {
            valores[0] += 2;
            return valores;
        }
        return valores;
    }
}
