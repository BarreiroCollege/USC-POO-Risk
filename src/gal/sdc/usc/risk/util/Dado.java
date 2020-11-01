package gal.sdc.usc.risk.util;

import java.util.Random;

public class Dado {
    private final static Random random = new Random();

    public static int tirar() {
        return random.nextInt(6) + 1;
    }
}
