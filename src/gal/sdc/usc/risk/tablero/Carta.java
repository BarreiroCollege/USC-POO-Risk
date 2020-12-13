package gal.sdc.usc.risk.tablero;

import gal.sdc.usc.risk.tablero.carta.artilleria.Antiaerea;
import gal.sdc.usc.risk.tablero.carta.artilleria.DeCampanha;
import gal.sdc.usc.risk.tablero.carta.caballeria.DeCaballo;
import gal.sdc.usc.risk.tablero.carta.caballeria.DeCamello;
import gal.sdc.usc.risk.tablero.carta.infanteria.Fusilero;
import gal.sdc.usc.risk.tablero.carta.infanteria.Granadero;
import gal.sdc.usc.risk.tablero.valores.Equipamientos;
import gal.sdc.usc.risk.tablero.valores.SubEquipamientos;

public abstract class Carta {
    private final SubEquipamientos subEquipamiento;
    private final Equipamientos equipamiento;
    private final Pais pais;

    protected Carta(Pais pais, SubEquipamientos subEquipamiento, Equipamientos equipamiento) {
        this.pais = pais;
        this.subEquipamiento = subEquipamiento;
        this.equipamiento = equipamiento;
    }

    public Equipamientos getEquipamiento() {
        return this.equipamiento;
    }

    public SubEquipamientos getSubEquipamiento() {
        return subEquipamiento;
    }

    public Pais getPais() {
        return pais;
    }

    public String getNombre() {
        return this.subEquipamiento.getNombre() + "&" + pais.getAbreviatura();
    }

    public int obtenerRearme() {
        return this.subEquipamiento.getEjercitos();
    }

    @Override
    public String toString() {
        return "Carta{" +
                "subEquipamiento=" + subEquipamiento +
                ", pais=" + pais +
                '}';
    }

    public static class Builder {
        private SubEquipamientos subEquipamiento;
        private Pais pais;

        public Builder() {
        }

        public Builder withSubEquipamiento(SubEquipamientos subEquipamiento) {
            this.subEquipamiento = subEquipamiento;
            return this;
        }

        public Builder withPais(Pais pais) {
            this.pais = pais;
            return this;
        }

        public Carta build() {
            if (this.subEquipamiento == null) {
                System.err.println("Carta.Builder equipamiento=null");
            } else if (this.pais == null) {
                System.err.println("Carta.Builder pais=null");
            } else {
                switch (this.subEquipamiento) {
                    case FUSILERO:
                        return new Fusilero(this.pais);
                    case GRANADERO:
                        return new Granadero(this.pais);
                    case ANTIAEREA:
                        return new Antiaerea(this.pais);
                    case DECABALLO:
                        return new DeCaballo(this.pais);
                    case DECAMELLO:
                        return new DeCamello(this.pais);
                    case DECAMPANHA:
                        return new DeCampanha(this.pais);
                }
            }
            return null;
        }
    }
}
