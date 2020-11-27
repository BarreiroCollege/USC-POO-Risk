package gal.sdc.usc.risk.correccion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class DatosDiccionario {
    public static final Pattern REGEX = Pattern.compile("\\s*\\{\\s*" + Parseador.REGEX_CLAVE + "\\s*:\\s*" + Parseador.REGEX_VALOR + "\\s*\\}\\s*", Pattern.DOTALL);
    HashMap<DatosTexto, Object> valores = new LinkedHashMap<>();

    public DatosDiccionario(String o) {
        // System.out.println("DICCIONARIO: " + o);

        char c, prev;
        boolean leyendo = false, leyendoClave = false, leyendoValor = false;
        StringBuilder clave = new StringBuilder();
        StringBuilder valor = new StringBuilder();
        int corchetesValor = 0;
        for (int i = 0; i < o.length(); i++) {
            c = o.charAt(i);
            prev = i > 0 ? o.charAt(i - 1) : '\0';

            if (!leyendoClave && !leyendoValor) {
                if (c == ' ' || c == '\n' || c == '\t' || c == '\r') {
                    continue;
                }
            }

            if (!leyendo) {
                if (c == '{' && prev != '\\') {
                    leyendo = true;
                    leyendoClave = true;
                }
            } else {
                if (leyendoClave) {
                    if (c == ':' && prev != '\\') {
                        leyendoClave = false;
                        leyendoValor = true;
                        corchetesValor = 0;
                    } else {
                        clave.append(c);
                    }
                } else if (leyendoValor) {
                    if (corchetesValor == 0 && valor.toString().trim().length() != 0 && (c == ',' || c == '}') && prev != '\\') {
                        leyendoValor = false;
                        if (c == ',') leyendoClave = true;
                        corchetesValor = 0;
                        // System.out.println(clave.toString().trim() + " - " + valor.toString().trim());
                        this.valores.put(new DatosTexto(clave.toString().trim()), Parseador.convertir(valor.toString().trim()));
                        clave = new StringBuilder();
                        valor = new StringBuilder();
                    } else {
                        switch (c) {
                            case '{':
                            case '[':
                                if (prev != '\\') corchetesValor++;
                                break;
                            case ']':
                            case '}':
                                if (prev != '\\') corchetesValor--;
                                break;
                            default:
                                break;
                        }
                        valor.append(c);
                    }
                }
            }
        }
    }

    public DatosDiccionario put(String clave, Object valor) {
        if (clave == null) {
            System.err.println("[DatosDiccionario] Intento de inserción de clave nula");
            return this;
        }
        if (valor != null) {
            this.valores.put(new DatosTexto(clave), valor);
        } else {
            this.valores.remove(new DatosTexto(clave));
        }
        return this;
    }

    public int puntuacionMax() {
        int sum = 0;
        for (Map.Entry<DatosTexto, Object> entrada : valores.entrySet()) {
            sum++;
            sum += Comparador.puntuacionMax(entrada.getValue());
        }
        return sum;
    }

    public int puntuacion(Object r) {
        if (r instanceof DatosDiccionario) {
            DatosDiccionario t = (DatosDiccionario) r;
            int sum = 0;
            List<DatosTexto> claves = new ArrayList<>(t.valores.keySet());
            for (Map.Entry<DatosTexto, Object> entrada : this.valores.entrySet()) {
                if (claves.contains(entrada.getKey())) {
                    sum++;
                }
                Object valor2 = t.valores.get(entrada.getKey());
                if (valor2 != null) {
                    sum += Comparador.puntuacion(entrada.getValue(), valor2);
                }
            }
            for (Map.Entry<DatosTexto, Object> entrada : t.valores.entrySet()) {
                Object valor1 = this.valores.get(entrada.getKey());
                if (valor1 == null) {
                    sum -= 1;
                }
            }
            return sum;
        }
        return 0;
    }

    public String imprimirError(Object r2) {
        if (r2 instanceof DatosDiccionario) {
            DatosDiccionario t = (DatosDiccionario) r2;
            StringBuilder out = new StringBuilder("     {\n");

            List<DatosTexto> claves2 = new ArrayList<>(t.valores.keySet());
            Iterator<Map.Entry<DatosTexto, Object>> it = valores.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<DatosTexto, Object> entrada = it.next();
                int max = 1 + Comparador.puntuacionMax(entrada.getValue());
                out.append(Ajustes.Colores.AZUL.getTexto())
                        .append(String.format("[%2d]    %s: %s%s\n", max, entrada.getKey().toString(), entrada.getValue(), it.hasNext() ? "," : ""))
                        .append(Ajustes.Colores.RESET);
                if (!claves2.contains(entrada.getKey())) {
                    out.append(Ajustes.Colores.ROJO.getTexto())
                            .append(String.format("[%2d]    %s: %s%s\n", max, entrada.getKey().toString(), entrada.getValue(), it.hasNext() ? "," : ""))
                            .append(Ajustes.Colores.RESET);
                } else {
                    Object valor2 = t.valores.get(entrada.getKey());
                    // TODO: Comprobar clave
                    int puntos = 1 + Comparador.puntuacion(entrada.getValue(), valor2);
                    out.append(puntos == max ? Ajustes.Colores.VERDE.getTexto() : Ajustes.Colores.AMARILLO.getTexto())
                            .append(String.format("[%2d]    %s: %s%s\n", puntos, entrada.getKey().toString(), valor2, it.hasNext() ? "," : ""))
                            .append(Ajustes.Colores.RESET);
                }
            }
            for (DatosTexto clave2 : claves2) {
                if (!valores.containsKey(clave2)) {
                    out.append(Ajustes.Colores.ROJO.getTexto())
                            .append(String.format("[%2d]    %s: %s?\n", -1, clave2.toString(), t.valores.get(clave2)))
                            .append(Ajustes.Colores.RESET);
                }
            }
            out.append("     }");
            return out.toString();
        }
        return "";
    }

    public String toString(boolean lb) {
        StringBuilder out = new StringBuilder("{").append(lb ? "\n" : " ");
        Iterator<Map.Entry<DatosTexto, Object>> it = valores.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<DatosTexto, Object> entrada = it.next();
            out.append(lb ? "  " : "").append(entrada.getKey().toString()).append(": ")
                    .append(Parseador.objetoATexto(entrada.getValue())).append(it.hasNext() ? "," : "")
                    .append(lb ? "\n" : " ");
        }
        out.append("}");
        return out.toString();
    }

    @Override
    public String toString() {
        return toString(false);
    }
}
