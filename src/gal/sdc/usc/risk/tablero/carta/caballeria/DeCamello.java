package gal.sdc.usc.risk.tablero.carta.caballeria;

import gal.sdc.usc.risk.tablero.Pais;
import gal.sdc.usc.risk.tablero.carta.Caballeria;
import gal.sdc.usc.risk.tablero.valores.SubEquipamientos;

public class DeCamello extends Caballeria {
    private static final SubEquipamientos SUBEQUIPAMIENTO = SubEquipamientos.DECAMELLO;

    public DeCamello(Pais pais) {
        super(pais, DeCamello.SUBEQUIPAMIENTO);
    }

    @Override
    public int obtenerRearme() {
        return DeCamello.SUBEQUIPAMIENTO.getEjercitos();
    }
}
