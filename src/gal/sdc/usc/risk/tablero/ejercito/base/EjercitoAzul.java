package gal.sdc.usc.risk.tablero.ejercito.base;

import gal.sdc.usc.risk.tablero.ejercito.EjercitoBase;
import gal.sdc.usc.risk.util.Colores;

import java.util.Arrays;

public class EjercitoAzul extends EjercitoBase {
    public EjercitoAzul(int cantidad) {
        super(cantidad, Colores.Color.AZUL);
    }

    public int[] ataque(int[] valores) {
        if (valores.length == 1) {
            return valores;
        }

        Arrays.sort(valores);
        valores[valores.length - 1]++;
        return valores;
    }
}
