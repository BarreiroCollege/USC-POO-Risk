package gal.sdc.usc.risk.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

public class Recursos {
    public static File get(String name) {
        return new File("res/" + name);
    }

    private static final File saida = new File("saida.txt");

    public static void engadirSaida(String out) {
        try {
            if (saida.createNewFile()) {
                System.out.println("Creando arquivo...");
            }

            FileOutputStream fos = new FileOutputStream(saida, true);
            OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            Writer writer = new BufferedWriter(osw);

            writer.append(out).append("\n");

            writer.close();
            osw.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
