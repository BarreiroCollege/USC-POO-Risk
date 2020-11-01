package gal.sdc.usc.risk.tablero;

import java.util.ArrayList;
import java.util.List;

public class Fronteras implements Comparable<Fronteras> {
    private final Pais norte;
    private final Pais sur;
    private final Pais este;
    private final Pais oeste;

    private final List<Pais> maritimas;

    private Fronteras(Pais norte, Pais sur, Pais este, Pais oeste, List<Pais> maritimas) {
        this.norte = norte;
        this.sur = sur;
        this.este = este;
        this.oeste = oeste;
        this.maritimas = maritimas;
    }

    public Pais getNorte() {
        return norte;
    }

    public Pais getSur() {
        return sur;
    }

    public Pais getEste() {
        return este;
    }

    public Pais getOeste() {
        return oeste;
    }

    public List<Pais> getMaritimas() {
        return this.maritimas;
    }

    public List<Pais> getTerrestres() {
        List<Pais> terrestres = new ArrayList<>();
        if (norte != null) {
            terrestres.add(norte);
        }
        if (sur != null) {
            terrestres.add(sur);
        }
        if (este != null) {
            terrestres.add(este);
        }
        if (oeste != null) {
            terrestres.add(oeste);
        }
        return terrestres;
    }

    public List<Pais> getTodas() {
        List<Pais> todas = new ArrayList<>();
        todas.addAll(this.getTerrestres());
        todas.addAll(this.getMaritimas());
        return todas;
    }

    @Override
    public String toString() {
        return "Fronteras{" +
                "norte=" + norte.getAbreviatura() +
                ", sur=" + sur.getAbreviatura() +
                ", este=" + este.getAbreviatura() +
                ", oeste=" + oeste.getAbreviatura() +
                ", maritimas=" + maritimas.stream().map(Pais::getAbreviatura) +
                '}';
    }

    @Override
    public int compareTo(Fronteras o) {
        return this.getTodas().size() - o.getTodas().size();
    }

    public int reverseCompareTo(Fronteras o) {
        return -1 * this.compareTo(o);
    }

    public static class Builder {
        private final List<Pais> maritimas;
        private Pais norte = null;
        private Pais sur = null;
        private Pais este = null;
        private Pais oeste = null;

        public Builder() {
            this.maritimas = new ArrayList<>();
        }

        public Builder withNorte(Pais norte) {
            this.norte = norte;
            return this;
        }

        public Builder withSur(Pais sur) {
            this.sur = sur;
            return this;
        }

        public Builder withEste(Pais este) {
            this.este = este;
            return this;
        }

        public Builder withOeste(Pais oeste) {
            this.oeste = oeste;
            return this;
        }

        public Builder withMaritima(Pais maritima) {
            this.maritimas.add(maritima);
            return this;
        }

        public Fronteras build() {
            return new Fronteras(norte, sur, este, oeste, maritimas);
        }
    }
}
