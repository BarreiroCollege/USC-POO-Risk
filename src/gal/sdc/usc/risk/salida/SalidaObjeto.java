package gal.sdc.usc.risk.salida;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class SalidaObjeto {
    private boolean raiz = true;
    private final HashMap<String, SalidaValor> entradas;

    public SalidaObjeto() {
        this.entradas = new LinkedHashMap<>();
    }

    public SalidaObjeto withEntrada(String clave, SalidaValor valor) {
        this.entradas.put(clave, valor);
        return this;
    }

    public SalidaObjeto setNodo() {
        this.raiz = false;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder("{");
        if (raiz) {
            out.append("\n");
        } else {
            out.append(" ");
        }
        Iterator<Map.Entry<String, SalidaValor>> it = entradas.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, SalidaValor> entrada = it.next();
            if (raiz) {
                out.append("  ").append(entrada.getKey()).append(": ").append(entrada.getValue().toString())
                        .append(it.hasNext() ? "," : "").append("\n");
            } else {
                out.append("\"").append(entrada.getKey()).append("\"").append(", ").append(entrada.getValue().toString())
                        .append(it.hasNext() ? ", " : "");
            }
        }
        if (!raiz) {
            out.append(" ");
        }
        out.append("}");
        return out.toString();
    }
}
