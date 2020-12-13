package gal.sdc.usc.risk.jugar;

import java.util.Scanner;

public class ConsolaNormal implements Consola {
    @Override
    public void imprimir(Object o) {
        System.out.print(o.toString());
    }

    @Override
    public void imprimirSalto() {
        this.imprimir("\n");
    }

    @Override
    public String leer() {
        Scanner input = new Scanner(System.in);
        String comando = input.nextLine();
        input.close();

        return comando;
    }
}
