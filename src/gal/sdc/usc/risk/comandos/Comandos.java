package gal.sdc.usc.risk.comandos;

public enum Comandos {
    AYUDA("(ayuda|info|help)"),
    SALIR("(salir|(terminar|acabar|rematar) partida)"),
    VER_MAPA("ver mapa"),
    OBTENER_COLOR("obtener color ([a-zA-Z0-9á-úÁ-Ú]+)"),
    OBTENER_CONTINENTE("obtener continente ([a-zA-Z0-9á-úÁ-Ú]+)"),
    OBTENER_FRONTERA("obtener frontera(s)? ([a-zA-Z0-9á-úÁ-Ú]+)"),
    OBTENER_PAISES("obtener pais(es)? ([a-zA-Z0-9á-úÁ-Ú]+)"),
    /* --- */
    CAMBIAR_CARTAS("cambiar cartas? ([a-zA-Z0-9á-úÁ-Ú&]+) ([a-zA-Z0-9á-úÁ-Ú&]+) ([a-zA-Z0-9á-úÁ-Ú&]+)( auto)?"),
    CAMBIAR_CARTAS_TODAS("cambiar cartas? todas( auto)?"),
    ACABAR_TURNO("(acabar|terminar) turno"),
    JUGADOR("jugador"),
    DESCRIBIR_JUGADOR("describir jugador ([a-zA-Z0-9á-úÁ-Ú]+)"),
    DESCRIBIR_PAIS("describir pa[ií]s ([a-zA-Z0-9á-úÁ-Ú]+)"),
    DESCRIBIR_CONTINENTE("describir continente ([a-zA-Z0-9á-úÁ-Ú]+)"),
    // VER_MAPA("ver mapa"),
    ATACAR_PAIS("atacar ([a-zA-Z0-9á-úÁ-Ú]+) ([a-zA-Z0-9á-úÁ-Ú]+)"),
    ATACAR_PAIS_DADOS("atacar ([a-zA-Z0-9á-úÁ-Ú]+) ([0-9x]+) ([a-zA-Z0-9á-úÁ-Ú]+) ([0-9x]+)"),
    REARMAR("rearmar ([a-zA-Z0-9á-úÁ-Ú]+) ([0-9]+) ([a-zA-Z0-9á-úÁ-Ú]+)"),
    ASIGNAR_CARTA("asignar carta ([a-zA-Z0-9á-úÁ-Ú&]+)"),
    /* --- */
    CREAR_MAPA("crear mapa"),
    CREAR_JUGADOR("crear ([a-zA-Z0-9á-úÁ-Ú]+)(?<!jugador)(?<!jugadores) ([a-zA-Z]+)"),
    CREAR_JUGADORES("crear jugador(es)? ([a-zA-Z0-9.]+)"),
    ASIGNAR_MISION("asignar mision ([a-zA-Z0-9á-úÁ-Ú]+)(?<!mision)(?<!misiones) ([a-zA-Z0-9]+)"),
    ASIGNAR_MISIONES("asignar mision(es)? ([a-zA-Z0-9.]+)"),
    ASIGNAR_PAIS("asignar pais ([a-zA-Z0-9á-úÁ-Ú]+)(?<!pais)(?<!paises) ([a-zA-Z0-9á-úÁ-Ú]+)"),
    ASIGNAR_PAISES("asignar pais(es)? ([a-zA-Z0-9.]+)"),
    REPARTIR_EJERCITO("repartir ejercitos? ([0-9]+) ([a-zA-Z0-9á-úÁ-Ú]+)"),
    REPARTIR_EJERCITOS("repartir ejercitos?");

    private final String regex;

    Comandos(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return "^" + this.regex + "$";
    }
}
