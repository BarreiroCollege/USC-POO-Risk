package gal.sdc.usc.risk;

import gal.sdc.usc.risk.correccion.Comparador;
import gal.sdc.usc.risk.correccion.Lector;
import gal.sdc.usc.risk.util.Recursos;

public class Correccion {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Requiere dos argumentos: [goldstandard.txt] [salida.txt]");
            return;
        }

        Lector profesor = new Lector(args[0]);
        Lector alumno = new Lector(args[1]);

        Comparador comparador = new Comparador(alumno, profesor);
        System.out.println(comparador);
    }
}