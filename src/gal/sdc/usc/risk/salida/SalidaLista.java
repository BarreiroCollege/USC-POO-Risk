package gal.sdc.usc.risk.salida;

import java.util.Arrays;
import java.util.List;

public class SalidaLista {
    private final List<String> valoresString;
    private final List<Integer> valoresInteger;
    private final List<SalidaLista> valoresSalidaLista;
    private final List<SalidaObjeto> valoresSalidaObjeto;

    private SalidaLista(List<String> valoresString, List<Integer> valoresInteger,
                        List<SalidaLista> valoresSalidaLista, List<SalidaObjeto> valoresSalidaObjeto) {
        this.valoresString = valoresString;
        this.valoresInteger = valoresInteger;
        this.valoresSalidaLista = valoresSalidaLista;
        this.valoresSalidaObjeto = valoresSalidaObjeto;
    }

    public static SalidaLista withString(List<String> valores) {
        return new SalidaLista(valores, null, null, null);
    }

    public static SalidaLista withString(String... valores) {
        return SalidaLista.withString(Arrays.asList(valores));
    }

    public static SalidaLista withInteger(List<Integer> valores) {
        return new SalidaLista(null, valores, null, null);
    }

    public static SalidaLista withInteger(Integer... valores) {
        return SalidaLista.withInteger(Arrays.asList(valores));
    }

    public static SalidaLista withSalidaLista(List<SalidaLista> valores) {
        return new SalidaLista(null, null, valores, null);
    }

    public static SalidaLista withString(SalidaLista[] valores) {
        return SalidaLista.withSalidaLista(Arrays.asList(valores));
    }

    public static SalidaLista withSalidaObjeto(List<SalidaObjeto> valores) {
        return new SalidaLista(null, null, null, valores);
    }

    public static SalidaLista withString(SalidaObjeto[] valores) {
        return SalidaLista.withSalidaObjeto(Arrays.asList(valores));
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder("[ ");
        if (valoresString != null) {
            for (int i = 0; i < valoresString.size(); i++) {
                out.append("\"").append(valoresString.get(i)).append("\"");
                if (i != (valoresString.size() - 1)) {
                    out.append(", ");
                }
            }
        } else if (valoresInteger != null) {
            for (int i = 0; i < valoresInteger.size(); i++) {
                out.append(valoresInteger.get(i));
                if (i != (valoresInteger.size() - 1)) {
                    out.append(", ");
                }
            }
        } else if (valoresSalidaLista != null) {
            for (int i = 0; i < valoresSalidaLista.size(); i++) {
                out.append(valoresSalidaLista.get(i).toString());
                if (i != (valoresSalidaLista.size() - 1)) {
                    out.append(", ");
                }
            }
        } else if (valoresSalidaObjeto != null) {
            for (int i = 0; i < valoresSalidaObjeto.size(); i++) {
                out.append(valoresSalidaObjeto.get(i).setNodo().toString());
                if (i != (valoresSalidaObjeto.size() - 1)) {
                    out.append(", ");
                }
            }
        }
        out.append(" ]");
        return out.toString();
    }
}
