package gal.sdc.usc.risk.correccion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Pattern;

public class DatosArray implements Iterable<Object> {
    public static final Pattern REGEX = Pattern.compile("\\s*\\[\\s*" + Parseador.REGEX_VALOR + "\\s*\\]\\s*", Pattern.DOTALL);

    private final ArrayList<Object> valores = new ArrayList<>();

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
