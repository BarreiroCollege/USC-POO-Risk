package gal.sdc.usc.risk.comandos.partida;

import gal.sdc.usc.risk.jugar.Partida;
import gal.sdc.usc.risk.salida.Resultado;
import gal.sdc.usc.risk.comandos.Comando;
import gal.sdc.usc.risk.comandos.Comandos;
import gal.sdc.usc.risk.comandos.Estado;
import gal.sdc.usc.risk.comandos.IComando;
import gal.sdc.usc.risk.salida.SalidaObjeto;
import gal.sdc.usc.risk.tablero.Pais;
import gal.sdc.usc.risk.excepciones.Errores;

@Comando(estado = Estado.JUGANDO, comando = Comandos.REARMAR)
public class Rearmar extends Partida implements IComando {
    @Override
    public void ejecutar(String[] comandos) {
        Pais origen = super.getMapa().getPaisPorNombre(comandos[1]);
        Integer num = Integer.parseInt(comandos[2]);
        Pais destino = super.getMapa().getPaisPorNombre(comandos[3]);

        if (origen == null || destino == null) {
            Resultado.error(Errores.PAIS_NO_EXISTE);
            return;
        }
        if (!origen.getJugador().equals(super.getJugadorTurno()) || !destino.getJugador().equals(super.getJugadorTurno())) {
            Resultado.error(Errores.PAIS_NO_PERTENECE);
            return;
        }
        if (origen.getEjercito().toInt() == 1) {
            Resultado.error(Errores.EJERCITOS_NO_SUFICIENTES);
            return;
        }
        if (!origen.getFronteras().getTodas().contains(destino)) {
            Resultado.error(Errores.PAIS_NO_FONTERA);
            return;
        }

        super.getComandos().rearmando();

        int inicialOrigen = origen.getEjercito().toInt();
        int inicialDestino = destino.getEjercito().toInt();

        if ((origen.getEjercito().toInt() - num) < 1) {
            num = origen.getEjercito().toInt() - 1;
        }

        destino.getEjercito().recibir(origen.getEjercito(), num);

        SalidaObjeto salida = new SalidaObjeto();
        salida.put("numeroEjercitosInicialesOrigen", inicialOrigen);
        salida.put("numeroEjercitosInicialesDestino", inicialDestino);
        salida.put("numeroEjercitosFinalesOrigen", origen.getEjercito());
        salida.put("numeroEjercitosFinalesDestino", destino.getEjercito());
        Resultado.correcto(salida);
    }

    @Override
    public String ayuda() {
        return "rearmar <abreviatura_pais1> <número_ejércitos> <abreviatura_pais2>";
    }
}
