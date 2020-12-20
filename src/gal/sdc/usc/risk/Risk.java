package gal.sdc.usc.risk;

import gal.sdc.usc.risk.salida.ConsolaNormal;
import gal.sdc.usc.risk.jugar.Menu;
import gal.sdc.usc.risk.salida.Resultado;

public class Risk {

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            Resultado.Escritor.cerrar();
            ConsolaNormal.cerrar();
        }));

        Menu.jugar();
    }

}
