package gal.sdc.usc.risk.salida;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class SalidaLista {
    private final List<Object> valores = new ArrayList<>();

    protected SalidaLista(Collection<?> valores) {
        this.valores.addAll(valores);
    }

    protected SalidaLista(Object... valores) {
        this.valores.addAll(Arrays.asList(valores));
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder("[ ");
        for (int i = 0; i < valores.size(); i++) {
            out.append(SalidaUtils.getString(valores.get(i)));
            if (i != (valores.size() - 1)) {
                out.append(", ");
            }
        }
        out.append(" ]");
        return out.toString();
    }
}
