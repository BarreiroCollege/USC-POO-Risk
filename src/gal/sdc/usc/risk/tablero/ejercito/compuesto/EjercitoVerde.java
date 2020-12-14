package gal.sdc.usc.risk.tablero.ejercito.compuesto;

import gal.sdc.usc.risk.tablero.ejercito.EjercitoBase;
import gal.sdc.usc.risk.util.Colores;

import java.util.Arrays;

public class EjercitoVerde extends EjercitoBase {
    public EjercitoVerde(int cantidad) {
        super(cantidad, Colores.Color.VERDE);
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
