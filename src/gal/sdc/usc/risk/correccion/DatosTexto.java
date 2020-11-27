package gal.sdc.usc.risk.correccion;

import java.util.Objects;

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

    public String sinComillas() {
        return Parseador.Texto.sinComillas(this.getOriginal());
    }

    public String sinAcentos() {
        return Parseador.Texto.sinAcentos(this.getOriginal());
    }

    public String sinMayusculas() {
        return Parseador.Texto.sinMayusculas(this.getOriginal());
    }

    public int puntuacion(Object r) {
        if (r instanceof DatosTexto) {
            DatosTexto t = (DatosTexto) r;
            if (t.getOriginal().equals(this.getOriginal())) {
                return 1;
            } else {
                if (t.sinComillas().equals(this.sinComillas())) {
                    if (Ajustes.TEXTO_IGNORAR_COMILLAS.equals(Ajustes.Niveles.RECHAZAR)) {
                        return 0;
                    }
                    return 1;
                } else if (t.sinAcentos().equals(this.sinAcentos())) {
                    if (Ajustes.TEXTO_IGNORAR_TILDES.equals(Ajustes.Niveles.RECHAZAR)) {
                        return 0;
                    }
                    return 1;
                } else if (t.sinMayusculas().equals(this.sinMayusculas())) {
                    if (Ajustes.TEXTO_IGNORAR_MAYUSCULAS.equals(Ajustes.Niveles.RECHAZAR)) {
                        return 0;
                    }
                    return 1;
                }
            }
        }
        return 0;
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
    public int hashCode() {
        return Objects.hash(texto);
    }

    @Override
    public String toString() {
        return this.getOriginal();
    }
}
