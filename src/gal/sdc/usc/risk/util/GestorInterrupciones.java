package gal.sdc.usc.risk.util;

import gal.sdc.usc.risk.menu.Resultado;

import java.security.Permission;

public class GestorInterrupciones extends SecurityManager {
    @Override
    public void checkExit(int status) {
        Resultado.Escritor.cerrar();
    }

    @Override
    public void checkPermission(Permission perm) {
        // Allow other activities by default
    }
}
