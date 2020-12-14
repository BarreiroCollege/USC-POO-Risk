package gal.sdc.usc.risk.tablero.ejercito;

import gal.sdc.usc.risk.tablero.Ejercito;
import gal.sdc.usc.risk.util.Colores;

public abstract class EjercitoCompuesto extends Ejercito {
    public EjercitoCompuesto(Colores.Color color) {
        super(0, color);
    }

    public EjercitoCompuesto(int cantidad, Colores.Color color) {
        super(cantidad, color);
    }
}
