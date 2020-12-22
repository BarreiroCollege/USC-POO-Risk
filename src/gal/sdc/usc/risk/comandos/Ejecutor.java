package gal.sdc.usc.risk.comandos;

import gal.sdc.usc.risk.excepciones.Errores;
import gal.sdc.usc.risk.excepciones.ExcepcionRISK;
import gal.sdc.usc.risk.jugar.Partida;
import gal.sdc.usc.risk.salida.Resultado;
import gal.sdc.usc.risk.util.Colores;
import javafx.application.Platform;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class Ejecutor extends Partida implements Callable<Boolean> {
    private final String comando;

    private Ejecutor(String comando) {
        this.comando = comando;
    }

    public static void comando(String comando, boolean imprimir, EjecutorListener listener) {
        if (listener != null) Ejecutor.ejecutarComando(comando, imprimir, listener);
        else Ejecutor.ejecutarComando(comando, imprimir);
    }

    public static void comando(String comando, EjecutorListener listener) {
        Ejecutor.comando(comando, true, listener);
    }

    public static void comando(String comando, boolean imprimir) {
        Ejecutor.comando(comando, imprimir, null);
    }

    public static void comando(String comando) {
        Ejecutor.comando(comando, true);
    }

    private static void ejecutarComando(String comando, boolean imprimir, EjecutorListener listener) {
        new Thread(() -> {
            Ejecutor ejecutor = new Ejecutor(comando);
            ejecutor.entrada();
            if (imprimir) {
                Resultado.Escritor.comando(comando);
            }
            try {
                ejecutor.call();
                Platform.runLater(listener::onComandoEjecutado);
            } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
                Throwable t = e.getCause();
                while (t != null) {
                    if (t instanceof ExcepcionRISK) {
                        ExcepcionRISK finalT = (ExcepcionRISK) t;
                        Platform.runLater(() -> listener.onComandoError(finalT));
                        Resultado.error(finalT);
                        break;
                    }
                    t = t.getCause();
                }
            }
        }).start();
    }

    private static void ejecutarComando(String comando, boolean imprimir) {
        Ejecutor ejecutor = new Ejecutor(comando);
        Future<Boolean> executor = Executors.newSingleThreadExecutor().submit(ejecutor);
        if (imprimir) {
            Resultado.Escritor.comando(comando);
        }
        try {
            executor.get();
        } catch (ExecutionException e) {
            Throwable t = e.getCause();
            while (t != null) {
                if (t instanceof ExcepcionRISK) {
                    Resultado.error((ExcepcionRISK) t);
                    return;
                }
                t = t.getCause();
            }
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void entrada() {
        String out = "";
        if (super.isJugando() || super.getComandos().isPaisesAsignados(super.getMapa())) {
            out += "[" + new Colores(super.getJugadorTurno().getNombre(), super.getJugadorTurno().getColor()) + "] ";
        }
        out += new Colores("$> ", Colores.Color.AMARILLO);
        super.getConsola().imprimir(out);
        super.getConsola().imprimir(comando);
        super.getConsola().imprimirSalto();
    }

    @Override
    public Boolean call() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Class<? extends IComando> comando = null;

        for (Class<? extends IComando> comandoI : super.getComandos().getLista()) {
            if (comandoI.isAnnotationPresent(Comando.class)) {
                Comando comandoA = comandoI.getAnnotation(Comando.class);
                if (this.comando.toLowerCase().matches(comandoA.comando().getRegex())) {
                    comando = comandoI;
                    break;
                }
            }
        }


        if (comando == null) {
            boolean existe = false;
            for (Comandos regex : Comandos.values()) {
                if (this.comando.toLowerCase().matches(regex.getRegex())) {
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
        ejecutar.invoke(comandoObject, new Object[]{this.comando.trim().split(" ")});
        return true;
    }
}
