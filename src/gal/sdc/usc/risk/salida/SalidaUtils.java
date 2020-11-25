package gal.sdc.usc.risk.salida;

import gal.sdc.usc.risk.tablero.Carta;
import gal.sdc.usc.risk.tablero.Continente;
import gal.sdc.usc.risk.tablero.Pais;

public abstract class SalidaUtils {
    private SalidaUtils() {
    }

    protected static String getString(Object object) {
        if (object == null) {
            return "null";
        } else if (object instanceof String) {
            return "\"" + object.toString() + "\"";
        } else if (object instanceof SalidaObjeto) {
            return ((SalidaObjeto) object).setNodo().toString();
        } else if (object instanceof Pais) {
            return SalidaUtils.getString(((Pais) object).getNombre());
        } else if (object instanceof Continente) {
            return SalidaUtils.getString(((Continente) object).getNombre());
        } else if (object instanceof Carta) {
            return SalidaUtils.getString(((Carta) object).getNombre());
        }
        return object.toString();
    }
}
