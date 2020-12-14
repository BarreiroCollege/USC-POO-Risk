package gal.sdc.usc.risk.tablero.carta.infanteria;

import gal.sdc.usc.risk.tablero.Pais;
import gal.sdc.usc.risk.tablero.carta.Infanteria;
import gal.sdc.usc.risk.tablero.valores.SubEquipamientos;

public class Granadero extends Infanteria {
    private static final SubEquipamientos SUBEQUIPAMIENTO = SubEquipamientos.GRANADERO;

    public Granadero(Pais pais) {
        super(pais, Granadero.SUBEQUIPAMIENTO);
    }

    @Override
    public int obtenerRearme() {
        return Granadero.SUBEQUIPAMIENTO.getEjercitos();
    }
}
