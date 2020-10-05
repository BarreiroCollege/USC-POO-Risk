package gal.sdc.usc.risk.tablero;

import java.util.ArrayList;
import java.util.List;

public class Mapa {
    protected final List<Continente> continentes;

    public static class Builder {
        private final Mapa mapa;

        public Builder() {
            mapa = new Mapa();
        }

        public Builder withContinente(Continente continente) {
            this.mapa.continentes.add(continente);
            return this;
        }

        public Mapa build() {
            return this.mapa;
        }
    }

    protected Mapa() {
        this.continentes = new ArrayList<>();
    }
}
