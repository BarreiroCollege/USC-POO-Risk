package gal.sdc.usc.risk.menu.comandos.partida;

import gal.sdc.usc.risk.menu.Partida;
import gal.sdc.usc.risk.menu.Resultado;
import gal.sdc.usc.risk.menu.comandos.Comando;
import gal.sdc.usc.risk.menu.comandos.Comandos;
import gal.sdc.usc.risk.menu.comandos.Estado;
import gal.sdc.usc.risk.menu.comandos.IComando;
import gal.sdc.usc.risk.tablero.Pais;
import gal.sdc.usc.risk.tablero.valores.Errores;
import gal.sdc.usc.risk.util.Colores;

@Comando(estado = Estado.JUGANDO, comando = Comandos.REARMAR)
public class Rearmar extends Partida implements IComando {
    @Override
    public void ejecutar(String[] comandos) {
        super.getComandosPermitidos().remove(AtacarPais.class);
        super.getComandosPermitidos().remove(AtacarPaisDados.class);
        super.getComandosPermitidos().remove(this.getClass());

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

        int inicialOrigen = origen.getEjercito().toInt();
        int inicialDestino = destino.getEjercito().toInt();

        if ((origen.getEjercito().toInt() - num) < 1) {
            num = origen.getEjercito().toInt() - 1;
        }

        destino.getEjercito().recibir(origen.getEjercito(), num);

        String out = "{\n" +
                "  numeroEjercitosInicialesOrigen: " + inicialOrigen + ",\n" +
                "  numeroEjercitosInicialesDestino: " + inicialDestino + ",\n" +
                "  numeroEjercitosFinalesOrigen: " + origen.getEjercito().toInt() + ",\n" +
                "  numeroEjercitosFinalesDestino: " + destino.getEjercito().toInt() + "\n" +
                "}";
        Resultado.correcto(new Colores(out, Colores.Color.VERDE).toString());
    }

    @Override
    public String ayuda() {
        return "rearmar <abreviatura_pais1> <número_ejércitos> <abreviatura_pais2>";
    }
}
