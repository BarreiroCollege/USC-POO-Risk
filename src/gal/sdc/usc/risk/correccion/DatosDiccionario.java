package gal.sdc.usc.risk.correccion;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

public class DatosDiccionario {
    public static final Pattern REGEX = Pattern.compile("\\s*\\{\\s*" + Parseador.REGEX_CLAVE + "\\s*:\\s*" + Parseador.REGEX_VALOR + "\\s*\\}\\s*", Pattern.DOTALL);
    HashMap<DatosTexto, Object> valores = new HashMap<>();

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

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        } else if (!(object instanceof DatosDiccionario)) {
            return false;
        }
        return ((DatosDiccionario) object).valores.equals(this.valores);
    }

    public String toString(boolean lb) {
        StringBuilder out = new StringBuilder("{").append(lb ? "\n" : " ");
        Iterator<Map.Entry<DatosTexto, Object>> it = valores.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<DatosTexto, Object> entrada = it.next();
            out.append("  ").append(entrada.getKey().toString()).append(": ")
                    .append(Parseador.objetoATexto(entrada.getValue())).append(it.hasNext() ? "," : "")
                    .append(lb ? "\n" : " ");
        }
        if (!lb) {
            out.append(" ");
        }
        out.append("}");
        return out.toString();
    }

    @Override
    public String toString() {
        return toString(false);
    }
}
