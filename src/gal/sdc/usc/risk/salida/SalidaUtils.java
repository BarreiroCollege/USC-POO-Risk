package gal.sdc.usc.risk.salida;

import gal.sdc.usc.risk.tablero.Carta;
import gal.sdc.usc.risk.tablero.Continente;
import gal.sdc.usc.risk.tablero.Ejercito;
import gal.sdc.usc.risk.tablero.Pais;

public abstract class SalidaUtils {
    private SalidaUtils() {
    }

    protected static String getString(Object o) {
        if (o == null) {
            return SalidaUtils.getString("null");
        }

        if (o instanceof String) {
            return "\"" + o.toString() + "\"";
        } else if (o instanceof SalidaObjeto) {
            return ((SalidaObjeto) o).setNodo().toString();
        } else if (o instanceof Pais) {
            return SalidaUtils.getString(((Pais) o).getNombre());
        } else if (o instanceof Continente) {
            return SalidaUtils.getString(((Continente) o).getNombre());
        } else if (o instanceof Carta) {
            return SalidaUtils.getString(((Carta) o).getNombre());
        } else if (o instanceof Ejercito) {
            return SalidaUtils.getString(((Ejercito) o).toInt());
        }

        return o.toString();
    }
}
