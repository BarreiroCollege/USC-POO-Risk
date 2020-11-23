package gal.sdc.usc.risk;


import gal.sdc.usc.risk.menu.Menu;
import gal.sdc.usc.risk.menu.Resultado;

public class Main {

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(Resultado.Escritor::cerrar));
        new Menu();
    }
}
