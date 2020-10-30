package gal.sdc.usc.risk.menu.comandos;

public enum Regex {
    OBTENER_COLOR("obtener color ([a-zA-Z]*)"),
    OBTENER_CONTINENTE("obtener continente ([a-zA-Z]*)"),
    OBTENER_FRONTERA("obtener frontera(s)? ([a-zA-Z]*)"),
    OBTENER_PAISES("obtener pais(es)? ([a-zA-Z]*)"),
    VER_MAPA("ver mapa"),
    //
    JUGADOR("jugador"),
    //
    ASIGNAR_MISION("asignar ([a-zA-Z0-9]*)(?<!mision)(?<!misiones) ([a-zA-Z0-9]*)"),
    ASIGNAR_MISIONES("asignar mision(es)? ([a-zA-Z.]*)"),
    CREAR_JUGADOR("crear ([a-zA-Z0-9]*)(?<!jugador)(?<!jugadores) ([a-zA-Z]*)"),
    CREAR_JUGADORES("crear jugador(es)? ([a-zA-Z.]*)"),
    CREAR_MAPA("crear mapa");

    private final String regex;

    Regex(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return "^" + this.regex + "$";
    }
}
