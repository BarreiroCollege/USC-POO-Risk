package gal.sdc.usc.risk.tablero.ejercito.base;

import gal.sdc.usc.risk.tablero.ejercito.EjercitoBase;
import gal.sdc.usc.risk.util.Colores;

import java.util.Arrays;

public class EjercitoRojo extends EjercitoBase {
    public EjercitoRojo(int cantidad) {
        super(cantidad, Colores.Color.ROJO);
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
