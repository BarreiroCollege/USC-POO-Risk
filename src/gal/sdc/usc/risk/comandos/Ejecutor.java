package gal.sdc.usc.risk.comandos;

import gal.sdc.usc.risk.excepciones.Errores;
import gal.sdc.usc.risk.excepciones.Excepcion;
import gal.sdc.usc.risk.jugar.Partida;
import gal.sdc.usc.risk.jugar.Resultado;

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

    public static void comando(String comando, boolean imprimir) {
        Ejecutor.comando = comando;
        Ejecutor.comando(imprimir);
    }

    public static void comando(String comando) {
        Ejecutor.comando(comando, true);
    }

    public static void comando(boolean imprimir) {
        Ejecutor ejecutor = new Ejecutor();
        Future<Boolean> executor = Executors.newSingleThreadExecutor().submit(ejecutor);
        if (imprimir) {
            Resultado.Escritor.comando(comando);
        }
        try {
            executor.get();
        } catch (ExecutionException e) {
            Throwable t = e.getCause();
            while (t != null) {
                if (t instanceof Excepcion) {
                    Resultado.error((Excepcion) t);
                    return;
                }
                t = t.getCause();
            }
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String getComando() {
        return Ejecutor.comando;
    }

    @Override
    public Boolean call() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Class<? extends IComando> comando = null;

        for (Class<? extends IComando> comandoI : super.getComandos().getLista()) {
            if (comandoI.isAnnotationPresent(Comando.class)) {
                Comando comandoA = comandoI.getAnnotation(Comando.class);
                if (this.getComando().toLowerCase().matches(comandoA.comando().getRegex())) {
                    comando = comandoI;
                    break;
                }
            }
        }


        if (comando == null) {
            boolean existe = false;
            for (Comandos regex : Comandos.values()) {
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
        comandoObject = comando.newInstance();
        Method ejecutar = comando.getMethod("ejecutar", String[].class);
        ejecutar.invoke(comandoObject, new Object[]{this.getComando().trim().split(" ")});
        return true;
    }
}
