package gal.sdc.usc.risk.menu.comandos;

public enum Comandos {
    AYUDA("(ayuda|info|help)"),
    VER_MAPA("ver mapa"),
    OBTENER_COLOR("obtener color ([a-zA-Z]*)"),
    OBTENER_CONTINENTE("obtener continente ([a-zA-Z]*)"),
    OBTENER_FRONTERA("obtener frontera(s)? ([a-zA-Z]*)"),
    OBTENER_PAISES("obtener pais(es)? ([a-zA-Z]*)"),
    /* --- */
    CAMBIAR_CARTAS(""),
    CAMBIAR_CARTAS_TODAS(""),
    ACABAR_TURNO("(acabar|terminar) turno"),
    JUGADOR("jugador"),
    DESCRIBIR_JUGADOR("describir jugador ([a-zA-Z0-9á-úÁ-Ú]*)"),
    DESCRIBIR_PAIS("describir pa[ií]s ([a-zA-Z0-9á-úÁ-Ú]*)"),
    DESCRIBIR_CONTINENTE("describir continente ([a-zA-Z0-9á-úÁ-Ú]*)"),
    // VER_MAPA("ver mapa"),
    ATACAR_PAIS(""),
    ATACAR_PAIS_DADOS(""),
    REARMAR(""),
    ASIGNAR_CARTA(""),
    /* --- */
    CREAR_MAPA("crear mapa"),
    CREAR_JUGADOR("crear ([a-zA-Z0-9á-úÁ-Ú]*)(?<!jugador)(?<!jugadores) ([a-zA-Z]*)"),
    CREAR_JUGADORES("crear jugador(es)? ([a-zA-Z0-9.]*)"),
    ASIGNAR_MISION("asignar ([a-zA-Z0-9á-úÁ-Ú]*)(?<!mision)(?<!misiones) ([a-zA-Z0-9]*)"),
    ASIGNAR_MISIONES("asignar mision(es)? ([a-zA-Z0-9.]*)"),
    ASIGNAR_PAIS("asignar ([a-zA-Z0-9á-úÁ-Ú]*)(?<!pais)(?<!paises) ([a-zA-Z0-9á-úÁ-Ú]*)"),
    ASIGNAR_PAISES("asignar pais(es)? ([a-zA-Z0-9.]*)"),
    REPARTIR_EJERCITO("repartir ejercitos? ([0-9]*) ([a-zA-Z0-9á-úÁ-Ú]*)"),
    REPARTIR_EJERCITOS("repartir ejercitos?");

    private final String regex;

    Comandos(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return "^" + this.regex + "$";
    }
}
