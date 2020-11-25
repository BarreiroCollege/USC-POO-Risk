package gal.sdc.usc.risk.evaluacion;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class GenCommands {
    //
    public final static String CREAR_MAPA = "crear mapa";
    public final static String OBTENER_FRONTERA = "obtener frontera";
    public final static String OBTENER_CONTINENTE = "obtener continente";
    public final static String OBTENER_COLOR = "obtener color";
    public final static String OBTENER_PAISES = "obtener paises";
    public final static String CREAR_JUGADOR = "crear jugador";
    public final static String CREAR_JUGADORES = "crear jugadores";
    public final static String ASIGNAR_MISION = "asignar mision";
    public final static String ASIGNAR_MISIONES = "asignar misiones";
    public final static String ASIGNAR_PAIS = "asignar pais";
    public final static String ASIGNAR_PAISES = "asignar paises";
    public final static String REPARTIR_EJERCITOS = "repartir ejercitos";
    // public final static String REPARTIR_EJERCITOS=   "repartir ejercitos";
    public final static String CAMBIAR_CARTAS = "cambiar cartas";
    // public final static String CAMBIAR_CARTAS_TODAS= "cambiar cartas todas";
    public final static String ACABAR_TURNO = "acabar turno";
    public final static String JUGADOR = "jugador";
    public final static String DESCRIBIR_JUGADOR = "describir jugador";
    public final static String DESCRIBIR_PAIS = "describir pais";
    public final static String DESCRIBIR_CONTINENTE = "describir continente";
    // public final static String VER_MAPA=             "ver mapa";
    public final static String ATACAR = "atacar";
    public final static String REARMAR = "rearmar";
    public final static String ASIGNAR_CARTA = "asignar carta";

    //
    public final static List<String> COMANDS = Arrays.asList(
            CREAR_MAPA,
            OBTENER_FRONTERA,
            OBTENER_CONTINENTE,
            OBTENER_COLOR,
            OBTENER_PAISES,
            CREAR_JUGADOR,
            CREAR_JUGADORES,
            ASIGNAR_MISION,
            ASIGNAR_MISIONES,
            ASIGNAR_PAIS,
            ASIGNAR_PAISES,
            REPARTIR_EJERCITOS,
            // REPARTIR_EJERCITOS,
            CAMBIAR_CARTAS,
            // CAMBIAR_CARTAS_TODAS,
            ACABAR_TURNO,
            JUGADOR,
            DESCRIBIR_JUGADOR,
            DESCRIBIR_PAIS,
            DESCRIBIR_CONTINENTE,
            // VER_MAPA,
            ATACAR,
            REARMAR,
            ASIGNAR_CARTA
    );
    //
    public final static HashMap<String, Float> COMMAND_MARKS = new HashMap<String, Float>() {
        {
            put(CREAR_MAPA, 6.0f);
            put(OBTENER_FRONTERA, 1.5f);
            put(OBTENER_CONTINENTE, 1.5f);
            put(OBTENER_COLOR, 1.5f);
            put(OBTENER_PAISES, 1.5f);
            put(CREAR_JUGADOR, 1.5f);
            put(CREAR_JUGADORES, 1.0f);
            put(ASIGNAR_MISION, 1.5f);
            put(ASIGNAR_MISIONES, 1.0f);
            put(ASIGNAR_PAIS, 1.5f);
            put(ASIGNAR_PAISES, 1.0f);
            put(REPARTIR_EJERCITOS, 1.5f);
            // put(REPARTIR_EJERCITOS,   4.5f);
            put(CAMBIAR_CARTAS, 1.5f);
            // put(CAMBIAR_CARTAS_TODAS, 2.5f);
            put(ACABAR_TURNO, 1.0f);
            put(JUGADOR, 0.5f);
            put(DESCRIBIR_JUGADOR, 1.5f);
            put(DESCRIBIR_PAIS, 1.5f);
            put(DESCRIBIR_CONTINENTE, 1.5f);
            // put(VER_MAPA,             0.0f);
            put(ATACAR, 4.5f);
            put(REARMAR, 1.5f);
            put(ASIGNAR_CARTA, 1.5f);
        }
    };
}