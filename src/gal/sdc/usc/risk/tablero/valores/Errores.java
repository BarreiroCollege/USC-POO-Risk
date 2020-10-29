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
    COLOR_YA_ASIGNADO(114, "El color ya está asignado");

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
