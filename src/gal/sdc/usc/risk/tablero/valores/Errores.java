package gal.sdc.usc.risk.tablero.valores;

public enum Errores {
    COMANDO_NO_PERMITIDO(99, "Comando no permitido en este momento"),
    COLOR_NO_PERMITIDO(100, "Color no permitido"),
    COMANDO_INCORRECTO(101, "Comando incorrecto"),
    CONTINENTE_NO_EXISTE(102, "El continente no existe"),
    JUGADOR_NO_EXISTE(103, "El jugador no existe"),
    JUGADOR_YA_EXISTE(104, "El jugador ya existe"),
    JUGADORES_NO_CREADOS(105, "Los jugadores no están creados"),
    MAPA_NO_CREADO(106, "El mapa todavía no se ha creado"),
    MAPA_YA_CREADO(107, "El mapa ya ha sido creado"),
    PAIS_NO_EXISTE(109, "El país no existe"),
    //
    PAIS_NO_PERTENECE(110, "El país no pertenece al jugador"),
    PAIS_YA_ASIGNADO(113, "El país ya está asignado"),
    COLOR_YA_ASIGNADO(114, "El color ya está asignado"),
    MISION_YA_ASIGNADA(115, "La misión ya está asignada"),
    MISION_NO_EXISTE(116, "La misión no existe"),
    JUGADOR_YA_MISION(117, "El jugador ya tiene asignada misión"),
    MISIONES_NO_ASIGNADAS(118, "Las misiones no están asignadas"),
    EJERCITO_NO_DISPONIBLE(119, "Ejércitos no disponibles");

    private final Integer codigo;
    private final String mensaje;

    Errores(Integer codigo, String mensaje) {
        this.codigo = codigo;
        this.mensaje = mensaje;
    }

    public Integer getCodigo() {
        return this.codigo;
    }

    public String getMensaje() {
        return this.mensaje;
    }
}
