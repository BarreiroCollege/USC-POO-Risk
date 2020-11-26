package gal.sdc.usc.risk.correccion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class Lector {
    private HashMap<String, List<DatosDiccionario>> comandos = new LinkedHashMap<>();

    public Lector(String archivo) {
        this(new File(archivo));
    }

    public Lector(File archivo) {
        try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(new FileInputStream(archivo)))) {
            String line;

            String comando = null;
            List<DatosDiccionario> resultados = null;
            int corchetes = 0;
            StringBuilder resultado = null;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("$> ")) {
                    if (comando != null) {
                        comandos.put(comando, resultados);
                    }
                    resultado = null;
                    comando = line.replace("$> ", "");
                    System.out.println("$> " + comando);
                    resultados = new ArrayList<>();
                    continue;
                } else if (line.equals("EOF")) {
                    if (comando != null) {
                        comandos.put(comando, resultados);
                    }
                    break;
                }

                if (resultado == null) {
                    if (line.contains("{") || line.contains("[")) {
                        resultado = new StringBuilder();
                    }
                }
                if (resultado != null){
                    System.out.println(line);
                    resultado.append(line);

                    corchetes += line.length() - line.replace("[", "").length();
                    corchetes += line.length() - line.replace("{", "").length();
                    corchetes -= line.length() - line.replace("}", "").length();
                    corchetes -= line.length() - line.replace("]", "").length();

                    if (corchetes == 0) {
                        // Parseador.detector(resultado.toString());
                        System.out.println(resultado.toString());
                        // DatosDiccionario objeto = new DatosDiccionario(new Parseador(resultado.toString()));
                        // System.out.println(objeto);
                        // resultados.add(new CorrectorObjetoJson(new ParseadorJson(resultado.toString())));
                        resultado = null;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
