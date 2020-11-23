package gal.sdc.usc.risk;


import gal.sdc.usc.risk.menu.Menu;
import gal.sdc.usc.risk.util.GestorInterrupciones;

public class Main {

    public static void main(String[] args) {
        GestorInterrupciones gestor = new GestorInterrupciones();
        System.setSecurityManager(gestor);

        new Menu();
    }
}
