package gal.sdc.usc.risk.tablero.valores;

public enum Paises {
    ALASKA("Alaska", "Alaska", Continentes.AMERICANORTE, 0, 0),
    TNOROESTE("Territorios del Noroeste", "TNoroeste", Continentes.AMERICANORTE, 1, 0),
    GROELANDIA("Groenlandia", "Groenlan", Continentes.AMERICANORTE, 2, 0),
    ISLANDIA("Islandia", "Islandia", Continentes.EUROPA, 4, 0),
    ESCANDINAVIA("Escandinavia", "Escandina", Continentes.EUROPA, 5, 0),
    SIBERIA("Siberia", "Siberia", Continentes.ASIA, 6, 0),
    YAKUSTSK("Yakustsk", "Yakustsk", Continentes.ASIA, 7, 0),
    KAMCHATKA("Kamchatka", "Kamchatka", Continentes.ASIA, 8, 0),

    ALBERTA("Alberta", "Alberta", Continentes.AMERICANORTE, 0, 1),
    ONTARIO("Ontario", "Ontario", Continentes.AMERICANORTE, 1, 1),
    QUEBEC("Quebec", "Quebec", Continentes.AMERICANORTE, 2, 1),
    GRANBRETANA("Gran Bretaña", "GBretaña", Continentes.EUROPA, 5, 1),
    EUROPANORTE("Europa del Norte", "EurNorte", Continentes.EUROPA, 6, 1),
    RUSIA("Rusia", "Rusia", Continentes.EUROPA, 7, 1),
    IRKUTSK("Irkutsk", "Irkutsk", Continentes.ASIA, 8, 1),

    USAOESTE("Estados Unidos del Oeste", "USAOeste", Continentes.AMERICANORTE, 0, 2),
    USAESTE("Estados Unidos del Este", "USAEste", Continentes.AMERICANORTE, 1, 2),
    EUROPAOCC("Europa Occidental", "EurOcc", Continentes.EUROPA, 5, 2),
    EUROPASUR("Europa del Sur", "EurSur", Continentes.EUROPA, 6, 2),
    URALES("Urales", "Urales", Continentes.ASIA, 7, 2),
    MONGOLIA("Mongolia", "Mongolia", Continentes.ASIA, 8, 2),
    JAPON("Japón", "Japón", Continentes.ASIA, 9, 2),

    AMERICACENTRAL("América Central", "AmeCentra", Continentes.AMERICANORTE, 1, 3),
    AFGANISTAN("Afganistán", "Afgan", Continentes.ASIA, 7, 3),
    CHINA("China", "China", Continentes.ASIA, 8, 3),

    VENEZUELA("Venezuela", "Venezuela", Continentes.AMERICASUR, 1, 4),
    ANORTE("África del Norte", "ANorte", Continentes.AFRICA, 5, 4),
    EGIPTO("Egipto", "Egipto", Continentes.AFRICA, 6, 4),
    OMEDIO("Oriente Medio", "OMedio", Continentes.ASIA, 7, 4),
    INDIA("India", "India", Continentes.ASIA, 8, 4),
    SASIATICO("Sureste Asiático", "SAsiático", Continentes.ASIA, 9, 4),

    PERU("Perú", "Perú", Continentes.AMERICASUR, 1, 5),
    BRASIL("Brasil", "Brasil", Continentes.AMERICASUR, 2, 5),
    CONGO("Congo", "Congo", Continentes.AFRICA, 5, 5),
    AORIENTAL("África Oriental", "AOriental", Continentes.AFRICA, 6, 5),

    ARGENTINA("Argentina", "Argentina", Continentes.AMERICASUR, 1, 6),
    SUDAFRICA("Sudáfrica", "Sudáfrica", Continentes.AFRICA, 6, 6),
    MADAGASCAR("Madagascar", "Madagasca", Continentes.AFRICA, 7, 6),
    INDONESIA("Indonesia", "Indonesia", Continentes.OCEANIA, 9, 6),
    NGUINEA("Nueva Guinea", "NGuinea", Continentes.OCEANIA, 10, 6),

    AUSOCCID("Australia Occidental", "AusOccid", Continentes.OCEANIA, 9, 7),
    AUSORIENT("Australia Oriental", "AusOrient", Continentes.OCEANIA, 10, 7);

    private final String nombre;
    private final String abreviatura;
    private final Continentes continente;
    private final int x;
    private final int y;

    Paises(String nombre, String abreviatura, Continentes continente, int x, int y) {
        this.nombre = nombre;
        this.abreviatura = abreviatura;
        this.continente = continente;
        this.x = x;
        this.y = y;
    }

    public static Paises toPaises(String pais) {
        for (Paises paises : Paises.values()) {
            if (pais.toLowerCase().equals(paises.getNombre().toLowerCase()) ||
                    pais.toLowerCase().equals(paises.getAbreviatura().toLowerCase())) {
                return paises;
            }
        }
        return null;
    }

    public String getNombre() {
        return this.nombre;
    }

    public String getAbreviatura() {
        return this.abreviatura;
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
