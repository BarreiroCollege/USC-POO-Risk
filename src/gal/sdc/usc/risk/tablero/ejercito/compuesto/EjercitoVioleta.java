package gal.sdc.usc.risk.tablero.ejercito.compuesto;

import gal.sdc.usc.risk.tablero.ejercito.EjercitoBase;
import gal.sdc.usc.risk.util.Colores;

import java.util.Arrays;

public class EjercitoVioleta extends EjercitoBase {
    public EjercitoVioleta(int cantidad) {
        super(cantidad, Colores.Color.VIOLETA);
    }

    public int[] ataque(int[] valores) {
        if (valores.length == 1) {
            return valores;
        }

        Arrays.sort(valores);
        valores[0]++;
        return valores;
    }
}
