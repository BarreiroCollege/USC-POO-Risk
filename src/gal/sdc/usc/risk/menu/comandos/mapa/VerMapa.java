package gal.sdc.usc.risk.menu.comandos.mapa;

import gal.sdc.usc.risk.menu.Partida;
import gal.sdc.usc.risk.menu.Resultado;
import gal.sdc.usc.risk.menu.comandos.Comando;
import gal.sdc.usc.risk.tablero.valores.Errores;

public class VerMapa extends Partida implements Comando {
    @Override
    public void ejecutar(String[] comandos) {
        if (super.getMapa() == null) {
            Resultado.error(Errores.MAPA_NO_CREADO);
            return;
        }
        super.getMapa().imprimir();
    }
}
