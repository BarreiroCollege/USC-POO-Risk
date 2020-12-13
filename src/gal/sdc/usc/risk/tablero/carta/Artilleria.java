package gal.sdc.usc.risk.tablero.carta;

import gal.sdc.usc.risk.tablero.Carta;
import gal.sdc.usc.risk.tablero.Pais;
import gal.sdc.usc.risk.tablero.valores.Equipamientos;
import gal.sdc.usc.risk.tablero.valores.SubEquipamientos;

public abstract class Artilleria extends Carta {
    private static final Equipamientos EQUIPAMIENTO = Equipamientos.ARTILLERIA;

    protected Artilleria(Pais pais, SubEquipamientos subEquipamiento) {
        super(pais, subEquipamiento, Artilleria.EQUIPAMIENTO);
    }
}
