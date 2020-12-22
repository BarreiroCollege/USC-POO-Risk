package gal.sdc.usc.risk.comandos;

import gal.sdc.usc.risk.excepciones.ExcepcionRISK;

public interface EjecutorListener {
    default void onComandoEjecutado() {
    }

    default void onComandoError(ExcepcionRISK e) {
    }
}
