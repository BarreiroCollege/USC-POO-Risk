package gal.sdc.usc.risk.tablero;

import gal.sdc.usc.risk.tablero.valores.Equipamientos;

public class Carta {
    private final Equipamientos equipamiento;
    private final Pais pais;

    private Carta(Equipamientos equipamiento, Pais pais) {
        this.equipamiento = equipamiento;
        this.pais = pais;
    }

    public Equipamientos getEquipamiento() {
        return equipamiento;
    }

    public Pais getPais() {
        return pais;
    }

    public String getNombre() {
        return this.equipamiento + "&" + pais.getAbreviatura();
    }

    @Override
    public String toString() {
        return this.getNombre();
    }

    public static class Builder {
        private Equipamientos equipamiento;
        private Pais pais;

        public Builder() {
        }

        public Builder withEquipamiento(Equipamientos equipamiento) {
            this.equipamiento = equipamiento;
            return this;
        }

        public Builder withPais(Pais pais) {
            this.pais = pais;
            return this;
        }

        public Carta build() {
            if (this.equipamiento == null) {
                // TODO
            } else if (this.pais == null) {
                // TODO
            } else {
                return new Carta(this.equipamiento, this.pais);
            }
            return null;
        }
    }
}
