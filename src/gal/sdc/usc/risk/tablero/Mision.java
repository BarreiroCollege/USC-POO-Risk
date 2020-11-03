package gal.sdc.usc.risk.tablero;

import gal.sdc.usc.risk.tablero.valores.Misiones;

public class Mision {
    private final Misiones identificador;
    private final String codigo;
    private final String descripcion;

    public Mision(Misiones identificador, String codigo, String descripcion) {
        this.identificador = identificador;
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public Misiones getIdentificador() {
        return this.identificador;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return "Mision{" +
                "codigo='" + codigo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }

    public static class Builder {
        private final Misiones mision;

        public Builder(Misiones mision) {
            this.mision = mision;
        }

        public Mision build() {
            if (mision == null) {
                System.err.println("Mision.Builder mision=null");
            } else {
                return new Mision(mision, mision.getId(), mision.getNombre());
            }
            return null;
        }
    }
}
