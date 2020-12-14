package gal.sdc.usc.risk.excepciones;

public enum Errores {
    COMANDO_NO_PERMITIDO(99, "Comando no permitido en este momento", ExcepcionComando.class),
    COLOR_NO_PERMITIDO(100, "Color no permitido", ExcepcionGeo.class),
    COMANDO_INCORRECTO(101, "Comando incorrecto", ExcepcionComando.class),
    CONTINENTE_NO_EXISTE(102, "El continente no existe", ExcepcionGeo.class),
    JUGADOR_NO_EXISTE(103, "El jugador no existe", ExcepcionJugador.class),
    JUGADOR_YA_EXISTE(104, "El jugador ya existe", ExcepcionJugador.class),
    JUGADORES_NO_CREADOS(105, "Los jugadores no están creados", ExcepcionJugador.class),
    MAPA_NO_CREADO(106, "El mapa todavía no se ha creado", ExcepcionGeo.class),
    MAPA_YA_CREADO(107, "El mapa ya ha sido creado", ExcepcionGeo.class),
    PAIS_NO_EXISTE(109, "El país no existe", ExcepcionGeo.class),
    PAIS_NO_PERTENECE(110, "El país no pertenece al jugador", ExcepcionJugador.class),
    PAIS_PERTENECE(111, "El país pertenece al jugador", ExcepcionJugador.class),
    PAIS_NO_FONTERA(112, "Los países no son frontera", ExcepcionGeo.class),
    PAIS_YA_ASIGNADO(113, "El país ya está asignado", ExcepcionJugador.class),
    COLOR_YA_ASIGNADO(114, "El color ya está asignado", ExcepcionJugador.class),
    MISION_YA_ASIGNADA(115, "La misión ya está asignada", ExcepcionMision.class),
    MISION_NO_EXISTE(116, "La misión no existe", ExcepcionMision.class),
    JUGADOR_YA_MISION(117, "El jugador ya tiene asignada misión", ExcepcionMision.class),
    MISIONES_NO_ASIGNADAS(118, "Las misiones no están asignadas", ExcepcionMision.class),
    EJERCITO_NO_DISPONIBLE(119, "Ejércitos no disponibles", ExcepcionJugador.class),
    CARTAS_NO_SUFICIENTES(120, "No hay cartas suficientes", ExcepcionCarta.class),
    CARTAS_NO_CAMBIABLES(121, "No hay configuración de cambio", ExcepcionCarta.class),
    CARTAS_NO_JUGADOR(122, "Algunas cartas no pertenecen al jugador", ExcepcionCarta.class),
    CARTAS_NO_EXISTEN(123, "Algunas cartas no existen", ExcepcionCarta.class),
    EJERCITOS_NO_SUFICIENTES(124, "No hay ejércitos suficientes", ExcepcionJugador.class),
    CARTA_INCORRECTA(125, "El identificador no sigue el formato correcto", ExcepcionCarta.class),
    CARTA_YA_ASIGNADA(126, "Carta de equipamiento ya asignada", ExcepcionCarta.class);

    private final Integer codigo;
    private final String mensaje;
    private final Class<? extends ExcepcionRISK> excepcion;

    Errores(Integer codigo, String mensaje, Class<? extends ExcepcionRISK> excepcion) {
        this.codigo = codigo;
        this.mensaje = mensaje;
        this.excepcion = excepcion;
    }

    public Integer getCodigo() {
        return this.codigo;
    }

    public String getMensaje() {
        return this.mensaje;
    }

    public ExcepcionRISK getExcepcion() {
        ExcepcionRISK excepcion;
        try {
            excepcion = this.excepcion.getDeclaredConstructor(new Class[]{Errores.class}).newInstance(this);
        } catch (Exception e) {
            return null;
        }
        return excepcion;
    }
}
