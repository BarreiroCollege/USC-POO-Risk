package gal.sdc.usc.risk.tablero;

public enum Paises {
    ALASKA("Alaska", Continentes.AMERICANORTE, 0, 0),
    TNOROESTE("TNoroeste", Continentes.AMERICANORTE, 1, 0),
    GROELANDIA("Groenlan", Continentes.AMERICANORTE, 2, 0),
    ISLANDIA("Islandia", Continentes.EUROPA, 4, 0),
    ESCANDINAVIA("Escan", Continentes.EUROPA, 5, 0),
    SIBERIA("Siberia", Continentes.ASIA, 6, 0),
    YAKUSTSK("Yakustsk", Continentes.ASIA, 7, 0),
    KAMCHATKA("Kamchatka", Continentes.ASIA, 8, 0),

    ALBERTA("Alberta", Continentes.AMERICANORTE, 0, 1),
    ONTARIO("Ontario", Continentes.AMERICANORTE, 1, 1),
    QUEBEC("Quebec", Continentes.AMERICANORTE, 2, 1),
    GRANBRETANA("GBretaña", Continentes.EUROPA, 5, 1),
    EUROPANORTE("EurNorte", Continentes.EUROPA, 6, 1),
    RUSIA("Rusia", Continentes.EUROPA, 7, 1),
    IRKUTSK("Irkutsk", Continentes.ASIA, 8, 1),

    USAOESTE("USAOeste", Continentes.AMERICANORTE, 0, 2),
    USAESTE("USAEste", Continentes.AMERICANORTE, 1, 2),
    EUROPAOCC("EurOcc", Continentes.EUROPA, 5, 2),
    EUROPASUR("EurSur", Continentes.EUROPA, 6, 2),
    URALES("Urales", Continentes.ASIA, 7, 2),
    MONGOLIA("Mongola", Continentes.ASIA, 8, 2),
    JAPON("Japón", Continentes.ASIA, 9, 2),

    AMERICACENTRAL("AmeCentral", Continentes.AMERICANORTE, 1, 3),
    AFGANISTAN("Afgan", Continentes.ASIA, 7, 3),
    CHINA("China", Continentes.ASIA, 8, 3),

    VENEZUELA("Venezuela", Continentes.AMERICASUR, 1, 4),
    AFNORTE("AfNorte", Continentes.AFRICA, 5, 4),
    EGIPTO("Egipto", Continentes.AFRICA, 6, 4),
    OMEDIO("OMedio", Continentes.ASIA, 7, 4),
    INDIA("India", Continentes.ASIA, 8, 4),
    SASIATICO("SAsiático", Continentes.ASIA, 9, 4),

    PERU("Perú", Continentes.AMERICASUR, 1, 5),
    Brasil("Brasil", Continentes.AMERICASUR, 2, 5),
    CONGO("Congo", Continentes.AFRICA, 5, 5),
    AORIENTAL("AOriental", Continentes.AFRICA, 6, 5),

    ARGENTINA("Argentina", Continentes.AMERICASUR, 1, 6),
    SUDAFRICA("Sudáfrica", Continentes.AFRICA, 6, 6),
    MADAGASCAR("Madagasca", Continentes.AFRICA, 7, 6),
    INDONESIA("Indonesia", Continentes.AUSTRALIA, 9, 6),
    NGUINEA("NGuinea", Continentes.AUSTRALIA, 10, 6),

    AUSOCCID("AusOccid", Continentes.AUSTRALIA, 9, 7),
    AUSORIENT("AusOrient", Continentes.AUSTRALIA, 10, 7);

    private final String nombre;
    private final Continentes continente;
    private final int x;
    private final int y;

    Paises(String nombre, Continentes continente, int x, int y) {
        this.nombre = nombre;
        this.continente = continente;
        this.x = x;
        this.y = y;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Continentes getContinente() {
        return this.continente;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
}
