package gal.sdc.usc.risk.salida;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class SalidaObjeto {
    private final HashMap<String, Object> entradas;

    public SalidaObjeto() {
        this.entradas = new LinkedHashMap<>();
    }

    public SalidaObjeto put(String clave, boolean valor) {
        return this.put(clave, valor ? Boolean.TRUE : Boolean.FALSE);
    }

    public SalidaObjeto put(String clave, int valor) {
        return this.put(clave, Integer.valueOf(valor));
    }

    public SalidaObjeto put(String clave, Collection<?> valores) {
        return this.put(clave, new SalidaLista(valores));
    }

    public SalidaObjeto put(String clave, Object... valores) {
        if (valores.length == 1) {
            return this.put(clave, valores[0]);
        }
        return this.put(clave, Arrays.asList(valores));
    }

    private SalidaObjeto put(String clave, Object valor) {
        this.entradas.put(clave, valor);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder("{\n");
        Iterator<Map.Entry<String, Object>> it = entradas.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> entrada = it.next();
            out.append("  ").append(entrada.getKey()).append(": ").append(SalidaUtils.getString(entrada.getValue()))
                    .append(it.hasNext() ? "," : "").append("\n");

        }
        out.append("}");
        return out.toString();
    }
}
