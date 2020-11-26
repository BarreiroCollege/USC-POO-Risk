package gal.sdc.usc.risk.salida;

public class SalidaDupla {
    private final String clave;
    private final Object valor;

    public SalidaDupla(String clave, Object valor) {
        this.clave = clave;
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "{ " + "\"" + clave + "\"" + ", " + SalidaUtils.getString(valor) + " }";
    }
}
