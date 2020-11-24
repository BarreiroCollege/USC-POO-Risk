package gal.sdc.usc.risk.salida;


public class SalidaValor {
    private final String texto;
    private final Integer numero;
    private final SalidaLista lista;
    private final SalidaObjeto objeto;

    private SalidaValor(String texto, Integer numero, SalidaLista lista, SalidaObjeto objeto) {
        this.texto = texto;
        this.numero = numero;
        this.lista = lista;
        this.objeto = objeto;
    }

    public static SalidaValor withString(String texto) {
        return new SalidaValor(texto, null, null, null);
    }

    public static SalidaValor withInteger(Integer numero) {
        return new SalidaValor(null, numero, null, null);
    }

    public static SalidaValor withSalidaLista(SalidaLista lista) {
        return new SalidaValor(null, null, lista, null);
    }

    public static SalidaValor withSalidaObjeto(SalidaObjeto objeto) {
        return new SalidaValor(null, null, null, objeto);
    }

    @Override
    public String toString() {
        if (texto != null) {
            return "\"" + texto + "\"";
        } else if (numero != null) {
            return numero.toString();
        } else if (lista != null) {
            return lista.toString();
        } else if (objeto != null) {
            return objeto.setNodo().toString();
        }
        return "";
    }
}
