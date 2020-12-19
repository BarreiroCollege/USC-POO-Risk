package gal.sdc.usc.risk;

import gal.sdc.usc.risk.jugar.ConsolaNormal;
import gal.sdc.usc.risk.jugar.Menu;
import gal.sdc.usc.risk.jugar.Resultado;

public class Main {

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            Resultado.Escritor.cerrar();
            ConsolaNormal.cerrar();
        }));

        Menu.jugar();
    }

}
