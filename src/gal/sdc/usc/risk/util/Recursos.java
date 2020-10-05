package gal.sdc.usc.risk.util;

import java.io.File;

public class Recursos {
    public static File get(String name) {
        return new File("res/" + name);
    }
}
