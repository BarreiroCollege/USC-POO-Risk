package gal.sdc.usc.risk.tablero.carta.artilleria;

import gal.sdc.usc.risk.tablero.Pais;
import gal.sdc.usc.risk.tablero.carta.Artilleria;
import gal.sdc.usc.risk.tablero.valores.SubEquipamientos;

public class DeCampanha extends Artilleria {
    private static final SubEquipamientos SUBEQUIPAMIENTO = SubEquipamientos.DECAMPANHA;

    public DeCampanha(Pais pais) {
        super(pais, DeCampanha.SUBEQUIPAMIENTO);
    }
}
