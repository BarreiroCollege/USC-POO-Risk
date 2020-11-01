package gal.sdc.usc.risk.tablero.valores;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum Equipamientos {
    INFANTERIA("Caballería"),
    CABALLERIA("Infantería"),
    ARTILLERIA("Artillería");

    private final String nombre;

    private static final List<Equipamientos> EQUIPAMIENTOS = Collections.unmodifiableList(Arrays.asList(values()));
    private static final Random R = new Random();

    Equipamientos(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return this.nombre;
    }

    public static Equipamientos toEquipamientos(String equipamiento) {
        for (Equipamientos equipamientos : Equipamientos.values()) {
            if (equipamiento.toLowerCase().equals(equipamientos.getNombre().toLowerCase())) {
                return equipamientos;
            }
        }
        return null;
    }

    public static Equipamientos aleatoria()  {
        return EQUIPAMIENTOS.get(R.nextInt(EQUIPAMIENTOS.size()));
    }
}
