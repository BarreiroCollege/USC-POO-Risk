package gal.sdc.usc.risk.correccion;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class DatosArray implements Iterable<Object> {
    public static final Pattern REGEX = Pattern.compile("\\s*\\[\\s*" + Parseador.REGEX_VALOR + "\\s*\\]\\s*", Pattern.DOTALL);

    private final List<Object> valores = new LinkedList<>();

    public DatosArray(String o) {
        // System.out.println("ARRAY: " + o);

        char c, prev;
        boolean leyendo = false, leyendoValor = false;
        StringBuilder valor = new StringBuilder();
        int corchetesValor = 0;
        for (int i = 0; i < o.length(); i++) {
            c = o.charAt(i);
            prev = i > 0 ? o.charAt(i - 1) : '\0';

            if (!leyendoValor) {
                if (c == ' ' || c == '\n' || c == '\t' || c == '\r') {
                    continue;
                }
            }

            if (!leyendo) {
                if (c == '[' && prev != '\\') {
                    leyendo = true;
                    leyendoValor = true;
                }
            } else {
                if (leyendoValor) {
                    if (corchetesValor == 0 && valor.toString().trim().length() != 0 && (c == ',' || c == ']') && prev != '\\') {
                        leyendoValor = c == ',';
                        corchetesValor = 0;
                        // System.out.println(valor.toString().trim());
                        this.valores.add(Parseador.convertir(valor.toString().trim()));
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

    public int puntuacionMax() {
        int sum = 0;
        for (Object entrada : valores) {
            sum += Comparador.puntuacionMax(entrada);
        }
        return sum;
    }

    public int puntuacion(Object r) {
        if (r instanceof DatosArray) {
            DatosArray t = (DatosArray) r;
            int sum = 0;
            for (Object entrada : this.valores) {
                int valor2 = t.valores.indexOf(entrada);
                if (valor2 > -1) {
                    sum += Comparador.puntuacion(entrada, t.valores.get(valor2));
                }
            }
            for (Object entrada : t.valores) {
                int valor1 = this.valores.indexOf(entrada);
                if (valor1 == -1) {
                    sum -= 1;
                }
            }
            return sum;
        }
        return 0;
    }

    @Override
    public Iterator<Object> iterator() {
        return this.valores.iterator();
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder("[ ");
        for (int i = 0; i < valores.size(); i++) {
            out.append(Parseador.objetoATexto(valores.get(i)));
            if (i != (valores.size() - 1)) {
                out.append(", ");
            }
        }
        out.append(" ]");
        return out.toString();
    }
}
