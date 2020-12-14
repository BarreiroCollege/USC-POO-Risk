package gal.sdc.usc.risk.tablero.ejercito;

import gal.sdc.usc.risk.tablero.Ejercito;

public class EjercitoNuevo extends Ejercito {
    public EjercitoNuevo() {
        super(0);
    }

    public EjercitoNuevo(int cantidad) {
        super(cantidad);
    }

    @Override
    public int[] ataque(int[] valores) {
        return valores;
    }
}
