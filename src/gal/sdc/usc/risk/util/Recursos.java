package gal.sdc.usc.risk.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

public class Recursos {
    private static final File saida = new File("saida.txt");

    public static File get(String name) {
        return new File("res/" + name);
    }
}
