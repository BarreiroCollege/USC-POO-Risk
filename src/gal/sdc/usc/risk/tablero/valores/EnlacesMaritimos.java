package gal.sdc.usc.risk.tablero.valores;

public enum EnlacesMaritimos {
    GROELANDIA_ISLANDIA(Paises.GROELANDIA, Paises.ISLANDIA),
    ALASKA_KAMCHATKA(Paises.KAMCHATKA, Paises.ALASKA),
    BRASIL_AFNORTE(Paises.BRASIL, Paises.ANORTE),
    EUROCC_AFNORTE(Paises.EUROPAOCC, Paises.ANORTE),
    EURSUR_EGIPTO(Paises.EUROPASUR, Paises.EGIPTO),
    SASIATICO_INDONESIA(Paises.SASIATICO, Paises.INDONESIA);

    final Paises pais1;
    final Paises pais2;

    EnlacesMaritimos(Paises pais1, Paises pais2) {
        this.pais1 = pais1;
        this.pais2 = pais2;
    }

    public Paises getPais1() {
        return this.pais1;
    }

    public Paises getPais2() {
        return this.pais2;
    }
}
