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
    EUROPASUR("EurSur", Continentes.EUROPA, 5, 2),
    EUROPAOCCIDENTAL("EurOcc", Continentes.EUROPA, 6, 2),
    URALES("Urales", Continentes.ASIA, 7, 2),
    MONGOLIA("Mongola", Continentes.ASIA, 8, 2),
    JAPON("Japón", Continentes.ASIA, 9, 2),

    AMERICACENTRAL("AmeCentral", Continentes.AMERICANORTE, 1, 3),
    AFGANISTAN("Afgan", Continentes.ASIA, 7, 3),
    CHINA("China", Continentes.ASIA, 8, 3);

    Paises(String nombre, Continentes value, int x, int y) {
    }
}
