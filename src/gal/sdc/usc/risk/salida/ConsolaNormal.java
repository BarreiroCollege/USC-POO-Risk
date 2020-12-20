package gal.sdc.usc.risk.salida;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Scanner;

public class ConsolaNormal implements Consola {
    private static final Scanner scanner;

    static {
        System.setIn(new FileInputStream(FileDescriptor.in));
        scanner = new Scanner(System.in, StandardCharsets.ISO_8859_1.name()).useLocale(new Locale("es"));
    }

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
        return scanner.nextLine();
    }

    public static void cerrar() {
        scanner.close();
    }
}
