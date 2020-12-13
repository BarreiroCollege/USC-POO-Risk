package gal.sdc.usc.risk.tablero.carta;

import gal.sdc.usc.risk.tablero.Carta;
import gal.sdc.usc.risk.tablero.Pais;
import gal.sdc.usc.risk.tablero.valores.Equipamientos;
import gal.sdc.usc.risk.tablero.valores.SubEquipamientos;

public abstract class Caballeria extends Carta {
    private static final Equipamientos EQUIPAMIENTO = Equipamientos.CABALLERIA;

    protected Caballeria(Pais pais, SubEquipamientos subEquipamiento) {
        super(pais, subEquipamiento, Caballeria.EQUIPAMIENTO);
    }
}
