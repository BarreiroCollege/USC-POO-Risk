package gal.sdc.usc.risk.salida;

import gal.sdc.usc.risk.tablero.Carta;
import gal.sdc.usc.risk.tablero.Continente;
import gal.sdc.usc.risk.tablero.Pais;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class SalidaUtils {
    public static SalidaLista paises(Collection<Pais> paises) {
        List<String> nombres = new ArrayList<>();
        for (Pais pais : paises) {
            nombres.add(pais.getNombre());
        }
        return SalidaLista.withString(nombres);
    }
    public static SalidaLista continentes(Collection<Continente> paises) {
        List<String> nombres = new ArrayList<>();
        for (Continente continente : paises) {
            nombres.add(continente.getNombre());
        }
        return SalidaLista.withString(nombres);
    }

    public static SalidaLista cartas(Collection<Carta> cartas) {
        List<String> nombres = new ArrayList<>();
        for (Carta carta : cartas) {
            nombres.add(carta.getNombre());
        }
        return SalidaLista.withString(nombres);
    }
}
