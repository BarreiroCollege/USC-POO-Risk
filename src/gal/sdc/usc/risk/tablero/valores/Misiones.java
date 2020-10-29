package gal.sdc.usc.risk.tablero.valores;

public enum Misiones {
    M1("M1", "Conquistar 24 países de la preferencia del jugador"),
    M2("M1", "Conquistar 18 países de la preferencia del jugador con un mínimo de 2 ejércitos"),

    M31("M31", "Conquistar Asia y América del Sur"),
    M32("M32", "Conquistar Asia y África"),
    M33("M33", "Conquistar América del Norte y África"),
    M34("M34", "Conquistar América del Norte y Oceanía"),

    M41("M41", "Destruir el ejército AMARILLO"),
    M42("M42", "Destruir el ejército AZUL"),
    M43("M43", "Destruir el ejército CYAN"),
    M44("M44", "Destruir el ejército ROJO"),
    M45("M45", "Destruir el ejército VERDE"),
    M46("M46", "Destruir el ejército VIOLETA");


    private final String id;
    private final String nombre;

    Misiones(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public static Misiones toMisiones(String id) {
        for (Misiones mision : Misiones.values()) {
            if (id.trim().toUpperCase().endsWith(mision.getId())) {
                return mision;
            }
        }
        return null;
    }

    public String getId() {
        return this.id;
    }

    public String getNombre() {
        return this.nombre;
    }
}
