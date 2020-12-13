package gal.sdc.usc.risk.tablero.carta.artilleria;

import gal.sdc.usc.risk.tablero.Pais;
import gal.sdc.usc.risk.tablero.carta.Artilleria;
import gal.sdc.usc.risk.tablero.valores.SubEquipamientos;

public class Antiaerea extends Artilleria {
    private static final SubEquipamientos SUBEQUIPAMIENTO = SubEquipamientos.ANTIAEREA;

    public Antiaerea(Pais pais) {
        super(pais, Antiaerea.SUBEQUIPAMIENTO);
    }
}
