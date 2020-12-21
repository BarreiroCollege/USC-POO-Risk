package gal.sdc.usc.risk;

import gal.sdc.usc.risk.salida.ConsolaNormal;
import gal.sdc.usc.risk.jugar.Menu;
import gal.sdc.usc.risk.salida.Resultado;

public class Risk {
    public static boolean netbeans = false;

    public static void main(String[] args) {
        for (String arg : args) {
            if ("netbeans".equals(arg)) {
                netbeans = true;
                System.err.println("Se ha detectado NetBeans; se recomienda usar IntelliJ o una consola normal.");
                break;
            }
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            Resultado.Escritor.cerrar();
            ConsolaNormal.cerrar();
        }));

        Menu.jugar();
    }

}
