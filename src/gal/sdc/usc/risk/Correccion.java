package gal.sdc.usc.risk;

import gal.sdc.usc.risk.correccion.Comparador;
import gal.sdc.usc.risk.correccion.Lector;
import gal.sdc.usc.risk.util.Recursos;

public class Correccion {
    public static void main(String[] args) {
        Lector alumno = new Lector(Recursos.get("resultados.txt"));
        Lector profesor = new Lector(Recursos.get("goldstandard.txt"));

        Comparador comparador = new Comparador(alumno, profesor);
        System.out.println(comparador);
    }
}