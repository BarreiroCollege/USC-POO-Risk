package gal.sdc.usc.risk.comandos.generico;

import gal.sdc.usc.risk.comandos.Comando;
import gal.sdc.usc.risk.comandos.Comandos;
import gal.sdc.usc.risk.comandos.Estado;
import gal.sdc.usc.risk.comandos.IComando;
import gal.sdc.usc.risk.jugar.Partida;
import gal.sdc.usc.risk.salida.SalidaObjeto;
import gal.sdc.usc.risk.util.Colores;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


@Comando(estado = Estado.CUALQUIERA, comando = Comandos.AYUDA)
public class Ayuda extends Partida implements IComando {
    @Override
    public void ejecutar(String[] comandos) {
        try {
            String ayuda;
            SalidaObjeto salida = new SalidaObjeto();
            for (Class<? extends IComando> comando : super.getComandos().getLista()) {
                Object comandoObject;
                comandoObject = comando.newInstance();
                Method ejecutar = comando.getMethod("ayuda");
                ayuda = (String) ejecutar.invoke(comandoObject);

                if (comando.isAnnotationPresent(Comando.class)) {
                    Comando comandoA = comando.getAnnotation(Comando.class);
                    salida.put(new Colores(comando.getSimpleName(),
                                    comandoA.estado().equals(Estado.CUALQUIERA) ? Colores.Color.CELESTE : Colores.Color.AZUL).toString(),
                            new Colores(ayuda, Colores.Color.VERDE));
                }
            }
            System.out.println(salida);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String ayuda() {
        return "ayuda";
    }
}
