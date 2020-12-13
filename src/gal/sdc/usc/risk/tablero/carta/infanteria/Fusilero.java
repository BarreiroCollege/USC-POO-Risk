package gal.sdc.usc.risk.tablero.carta.infanteria;

import gal.sdc.usc.risk.tablero.Pais;
import gal.sdc.usc.risk.tablero.carta.Infanteria;
import gal.sdc.usc.risk.tablero.valores.SubEquipamientos;

public class Fusilero extends Infanteria {
    private static final SubEquipamientos SUBEQUIPAMIENTO = SubEquipamientos.FUSILERO;

    public Fusilero(Pais pais) {
        super(pais, Fusilero.SUBEQUIPAMIENTO);
    }
}
