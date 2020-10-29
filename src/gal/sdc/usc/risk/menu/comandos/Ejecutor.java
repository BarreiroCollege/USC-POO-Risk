package gal.sdc.usc.risk.menu.comandos;

import gal.sdc.usc.risk.menu.Partida;
import gal.sdc.usc.risk.menu.Resultado;
import gal.sdc.usc.risk.tablero.valores.Errores;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Ejecutor extends Partida implements Callable<Boolean> {
    private static String[] comandos;
    private final Class<? extends Comando> comando;

    private Ejecutor(Class<? extends Comando> comando) {
        this.comando = comando;
    }

    @Override
    public Boolean call() {
        Object comandoObject;
        try {
            comandoObject = comando.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            return false;
        }

        try {
            if (comando.getPackage().getName().endsWith("partida") && !super.isJugando() ||
                    comando.getPackage().getName().endsWith("preparacion") && super.isJugando()) {
                Resultado.error(Errores.COMANDO_NO_PERMITIDO);
                return true;
            }

            if (comando.getPackage().getName().endsWith("preparacion") &&
                    comando.isAnnotationPresent(Preparacion.class)) {
                Preparacion preparacion = comando.getAnnotation(Preparacion.class);
                if (!preparacion.requiere().equals(Comando.class) && super.getComandosEjecutados().contains(comando) &&
                        !super.getComandosPermitidos().contains(comando)) {
                    Resultado.error(Errores.COMANDO_NO_PERMITIDO);
                    return true;
                }
            }

            Method ejecutar = comando.getMethod("ejecutar", String[].class);
            ejecutar.invoke(comandoObject, new Object[]{comandos});
            return true;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void setComandos(String[] comandos) {
        Ejecutor.comandos = comandos;
    }

    public static void comando(Class<? extends Comando> comando) {
        Ejecutor ejecutor = new Ejecutor(comando);
        Future<Boolean> executor = Executors.newSingleThreadExecutor().submit(ejecutor);
        try {
            executor.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
