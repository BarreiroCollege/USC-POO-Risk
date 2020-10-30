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
    private static String comando;

    private Ejecutor() {
    }

    public static void comando() {
        Ejecutor ejecutor = new Ejecutor();
        Future<Boolean> executor = Executors.newSingleThreadExecutor().submit(ejecutor);
        try {
            executor.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private String getComando() {
        return Ejecutor.comando;
    }

    public static void setComando(String comando) {
        Ejecutor.comando = comando;
    }

    @Override
    public Boolean call() {
        Class<? extends IComando> comando = null;

        for (Class<? extends IComando> comandoI : super.getComandosPermitidos()) {
            if (comandoI.isAnnotationPresent(Comando.class)) {
                Comando comandoA = comandoI.getAnnotation(Comando.class);
                if (this.getComando().toLowerCase().matches(comandoA.regex().getRegex())) {
                    comando = comandoI;
                    break;
                }
            }
        }


        if (comando == null) {
            boolean existe = false;
            for (Regex regex : Regex.values()) {
                if (this.getComando().toLowerCase().matches(regex.getRegex())) {
                    existe = true;
                    break;
                }
            }
            if (existe) {
                Resultado.error(Errores.COMANDO_NO_PERMITIDO);
            } else {
                Resultado.error(Errores.COMANDO_INCORRECTO);
            }
            return true;
        }


        Object comandoObject;
        try {
            comandoObject = comando.newInstance();
            Method ejecutar = comando.getMethod("ejecutar", String[].class);
            ejecutar.invoke(comandoObject, new Object[]{this.getComando().trim().split(" ")});
            return true;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
        return false;
    }
}
