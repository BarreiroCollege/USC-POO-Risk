package gal.sdc.usc.risk.menu.comandos.partida;

import gal.sdc.usc.risk.menu.Partida;
import gal.sdc.usc.risk.menu.Resultado;
import gal.sdc.usc.risk.menu.comandos.Comando;
import gal.sdc.usc.risk.menu.comandos.Comandos;
import gal.sdc.usc.risk.menu.comandos.Estado;
import gal.sdc.usc.risk.menu.comandos.IComando;
import gal.sdc.usc.risk.salida.SalidaObjeto;
import gal.sdc.usc.risk.tablero.Continente;
import gal.sdc.usc.risk.tablero.Jugador;
import gal.sdc.usc.risk.tablero.Pais;
import gal.sdc.usc.risk.tablero.valores.Errores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Comando(estado = Estado.JUGANDO, comando = Comandos.DESCRIBIR_CONTINENTE)
public class DescribirContinente extends Partida implements IComando {
    @Override
    public void ejecutar(String[] comandos) {
        Continente continente = super.getMapa().getContinentePorNombre(comandos[2]);

        if (continente == null) {
            Resultado.error(Errores.CONTINENTE_NO_EXISTE);
            return;
        }

        SalidaObjeto salida = new SalidaObjeto();
        salida.put("nombre", continente.getNombre());
        salida.put("abreviatura", continente.getAbreviatura());

        HashMap<Jugador, Integer> jugadoresEjercitos = new HashMap<>();
        for (Pais pais : continente.getPaises().values()) {
            jugadoresEjercitos.putIfAbsent(pais.getJugador(), 0);
            jugadoresEjercitos.put(pais.getJugador(), jugadoresEjercitos.get(pais.getJugador()) + pais.getEjercito().toInt());
        }
        List<SalidaObjeto> jugadores = new ArrayList<>();
        for (Map.Entry<Jugador, Integer> jugador : jugadoresEjercitos.entrySet()) {
            jugadores.add(new SalidaObjeto().put(jugador.getKey().getNombre(), jugador.getValue()));
        }
        salida.put("jugadores", jugadores);

        salida.put("numeroEjercitos", continente.getNumEjercitos());
        salida.put("rearmeContinente", continente.getEjercitosRearme());

        Resultado.correcto(salida);
    }

    @Override
    public String ayuda() {
        return "describir continente <abreviatura_continente>";
    }
}
