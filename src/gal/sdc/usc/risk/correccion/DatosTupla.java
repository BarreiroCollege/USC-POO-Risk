package gal.sdc.usc.risk.correccion;

import java.util.regex.Pattern;

public class DatosTupla {
    public static final Pattern REGEX = Pattern.compile("\\s*\\{\\s*" + Parseador.REGEX_CLAVE + "\\s*,\\s*" + Parseador.REGEX_VALOR + "\\s*\\}\\s*", Pattern.DOTALL);

    private DatosTexto clave;
    private Object valor;

    public DatosTupla(String o) {
        // System.out.println("TUPLA: " + o);

        char c, prev;
        boolean leyendo = false, leyendo1 = false, leyendo2 = false;
        StringBuilder clave = new StringBuilder();
        StringBuilder valor = new StringBuilder();
        int corchetesValor = 0;
        for (int i = 0; i < o.length(); i++) {
            c = o.charAt(i);
            prev = i > 0 ? o.charAt(i - 1) : '\0';

            if (!leyendo1 && !leyendo2) {
                if (c == ' ' || c == '\n' || c == '\t' || c == '\r') {
                    continue;
                }
            }

            if (!leyendo) {
                if (c == '{' && prev != '\\') {
                    leyendo = true;
                    leyendo1 = true;
                }
            } else {
                if (leyendo1) {
                    if (c == ',' && prev != '\\') {
                        leyendo1 = false;
                        leyendo2 = true;
                        corchetesValor = 0;
                    } else {
                        clave.append(c);
                    }
                } else if (leyendo2) {
                    if (corchetesValor == 0 && valor.toString().trim().length() != 0 && (c == ',' || c == '}') && prev != '\\') {
                        leyendo2 = false;
                        corchetesValor = 0;
                        // System.out.println(clave.toString().trim() + " - " + valor.toString().trim());
                        this.clave = new DatosTexto(clave.toString().trim());
                        this.valor = Parseador.convertir(valor.toString().trim());
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

    public DatosTupla put(String clave, Object valor) {
        if (clave == null) {
            System.err.println("[DatosTupla] Intento de inserción de clave nula");
            return this;
        }
        if (this.clave != null) {
            System.err.println("[DatosTupla] Intento de inserción en tupla llena");
            return this;
        }
        if (valor != null) {
            this.clave = new DatosTexto(clave);
            this.valor = valor;
        } else {
            this.clave = null;
            this.valor = null;
        }
        return this;
    }

    public int puntuacionMax() {
        int sum = 1;
        sum += Comparador.puntuacionMax(valor);
        return sum;
    }

    public int puntuacion(Object r) {
        if (r instanceof DatosTupla) {
            DatosTupla t = (DatosTupla) r;
            int sum = Comparador.puntuacion(this.clave, t.clave);
            sum += Comparador.puntuacion(this.valor, t.valor);
            return sum;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "{ " + "\"" + Parseador.Texto.sinComillas(this.clave.toString()) + "\", " + Parseador.objetoATexto(this.valor) + " }";
    }
}
