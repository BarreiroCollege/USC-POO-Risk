package gal.sdc.usc.risk.menu.comandos.generico;

import gal.sdc.usc.risk.menu.Partida;
import gal.sdc.usc.risk.menu.Resultado;
import gal.sdc.usc.risk.menu.comandos.Comando;
import gal.sdc.usc.risk.menu.comandos.Estado;
import gal.sdc.usc.risk.menu.comandos.IComando;
import gal.sdc.usc.risk.menu.comandos.Regex;
import gal.sdc.usc.risk.util.Colores;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


@Comando(estado = Estado.CUALQUIERA, regex = Regex.AYUDA)
public class Ayuda extends Partida implements IComando {
    @Override
    public void ejecutar(String[] comandos) {
        try {
            String ayuda;
            StringBuilder texto = new StringBuilder("{\n");
            for (Class<? extends IComando> comando : super.getComandosPermitidos()) {Object comandoObject;
                comandoObject = comando.newInstance();
                Method ejecutar = comando.getMethod("ayuda");
                ayuda = (String) ejecutar.invoke(comandoObject);

                texto.append("\t");
                if (comando.isAnnotationPresent(Comando.class)) {
                    Comando comandoA = comando.getAnnotation(Comando.class);
                    if (comandoA.estado().equals(Estado.CUALQUIERA)) {
                        texto.append(new Colores(comando.getSimpleName(), Colores.Color.CELESTE));
                    } else {
                        texto.append(new Colores(comando.getSimpleName(), Colores.Color.AZUL));
                    }
                }
                texto.append(": \"").append(new Colores(ayuda, Colores.Color.VERDE)).append("\",\n");
            }
            texto.append("}");
            System.out.println(texto.toString());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String ayuda() {
        return "ayuda";
    }
}
