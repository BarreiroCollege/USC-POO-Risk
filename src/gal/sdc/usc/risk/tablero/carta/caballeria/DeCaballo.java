package gal.sdc.usc.risk.tablero.carta.caballeria;

import gal.sdc.usc.risk.tablero.Pais;
import gal.sdc.usc.risk.tablero.carta.Caballeria;
import gal.sdc.usc.risk.tablero.valores.SubEquipamientos;

public class DeCaballo extends Caballeria {
    private static final SubEquipamientos SUBEQUIPAMIENTO = SubEquipamientos.DECABALLO;

    public DeCaballo(Pais pais) {
        super(pais, DeCaballo.SUBEQUIPAMIENTO);
    }

    @Override
    public int obtenerRearme() {
        return DeCaballo.SUBEQUIPAMIENTO.getEjercitos();
    }
}
