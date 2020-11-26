package gal.sdc.usc.risk.correccion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class Lector {
    private List<HashMap<String, List<Object>>> comandos = new LinkedList<>();

    public Lector(String archivo) {
        this(new File(archivo));
    }

    public Lector(File archivo) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(archivo)))) {
            String line;

            String comando = null;
            List<Object> resultados = null;
            int corchetes = 0;
            StringBuilder resultado = null;

            HashMap<String, List<Object>> comandos2 = null;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("$> ")) {
                    if (comando != null) {
                        comandos2.put(comando, resultados);
                        comandos.add(comandos2);
                    }
                    resultado = null;
                    comando = line.replace("$>", "").trim();
                    comandos2 = new LinkedHashMap<>();
                    resultados = new ArrayList<>();
                    continue;
                } else if (line.equals("EOF")) {
                    if (comando != null) {
                        comandos2.put(comando, resultados);
                        comandos.add(comandos2);
                    }
                    break;
                }

                if (resultado == null) {
                    if (line.contains("{") || line.contains("[")) {
                        resultado = new StringBuilder();
                    }
                }
                if (resultado != null) {
                    resultado.append(line).append("\n");

                    corchetes += line.length() - line.replace("[", "").length();
                    corchetes += line.length() - line.replace("{", "").length();
                    corchetes -= line.length() - line.replace("}", "").length();
                    corchetes -= line.length() - line.replace("]", "").length();

                    if (corchetes == 0) {
                        resultados.add(Parseador.convertir(resultado.toString()));
                        resultado = null;
                    }
                }
            }

            /* for (HashMap<String, List<Object>> comandos : comandos) {
                for (Map.Entry<String, List<Object>> c : comandos.entrySet()) {
                    System.out.println("$> " + c.getKey());
                    for (Object o : c.getValue()) {
                        System.out.println(Parseador.objetoATexto(o));
                    }
                    System.out.println();
                }
            } */
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<HashMap<String, List<Object>>> getComandos() {
        return comandos;
    }
}
