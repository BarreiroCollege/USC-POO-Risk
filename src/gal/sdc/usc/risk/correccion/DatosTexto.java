package gal.sdc.usc.risk.correccion;

public class DatosTexto {
    private final String texto;
    private final String textoO;

    public DatosTexto(String o) {
        texto = Parseador.Texto.adaptar(o);
        textoO = "" + o;
    }

    public String getAdaptado() {
        return this.texto;
    }

    public String getOriginal() {
        return this.textoO;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (obj instanceof String) {
            return obj.equals(this.textoO);
        } else if (obj instanceof DatosTexto) {
            return ((DatosTexto) obj).texto.equals(this.texto);
        }
        return false;
    }

    @Override
    public String toString() {
        return this.getOriginal();
    }
}
