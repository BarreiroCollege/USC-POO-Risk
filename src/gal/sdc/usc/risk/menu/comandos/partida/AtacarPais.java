package gal.sdc.usc.risk.menu.comandos.partida;

import gal.sdc.usc.risk.menu.Partida;
import gal.sdc.usc.risk.menu.Resultado;
import gal.sdc.usc.risk.menu.comandos.Comando;
import gal.sdc.usc.risk.menu.comandos.Comandos;
import gal.sdc.usc.risk.menu.comandos.Ejecutor;
import gal.sdc.usc.risk.menu.comandos.Estado;
import gal.sdc.usc.risk.menu.comandos.IComando;
import gal.sdc.usc.risk.tablero.Pais;
import gal.sdc.usc.risk.tablero.valores.Errores;
import gal.sdc.usc.risk.util.Dado;

import java.util.ArrayList;
import java.util.List;

@Comando(estado = Estado.JUGANDO, comando = Comandos.ATACAR_PAIS)
public class AtacarPais extends Partida implements IComando {
    @Override
    public void ejecutar(String[] comandos) {
        Pais pais1 = super.getMapa().getPaisPorNombre(comandos[1]);
        Pais pais2 = super.getMapa().getPaisPorNombre(comandos[2]);

        if (pais1 == null || pais2 == null) {
            Resultado.error(Errores.PAIS_NO_EXISTE);
            return;
        }


        if (!pais1.getFronteras().getTodas().contains(pais2)) {
            Resultado.error(Errores.PAIS_NO_FONTERA);
            return;
        }

        if (pais2.getJugador().equals(super.getJugadorTurno())) {
            Resultado.error(Errores.PAIS_PERTENECE);
            return;
        }
        if (!pais1.getJugador().equals(super.getJugadorTurno())) {
            Resultado.error(Errores.PAIS_NO_PERTENECE);
            return;
        }
        if (pais1.getEjercito().toInt() <= 1) {
            Resultado.error(Errores.EJERCITOS_NO_SUFICIENTES);  //ESTO LO MOVI AL FINAL, PQ SI ATACABAS CON UN PAIS Q NO ES TUYO A OTRO
            //Y NO TIENE EL NUM MIN DE EJERCITOS TE PRINTEA ESE ERROR (ej_no_suf)
            //EN VEZ DEL QUE DICE Q EL PAIS NO ES TUYO(pais_no_pertenece)
            return;
        }
        List<String> atacante = new ArrayList<>();
        List<String> defensor = new ArrayList<>();

        int atacantes;
        if (pais1.getEjercito().toInt() == 2) {
            atacantes = 1;
        } else if (pais1.getEjercito().toInt() == 3) {
            atacantes = 2;
        } else {
            atacantes = 3;
        }

        int defensores;
        if (pais2.getEjercito().toInt() == 1) {
            defensores = 1;
        } else {
            defensores = 2;
        }

        for (int i = 0; i < atacantes; i++) {
            atacante.add("" + Dado.tirar());
        }
        for (int i = 0; i < defensores; i++) {
            defensor.add("" + Dado.tirar());
        }


        List<String> comando = new ArrayList<>();
        comando.add("atacar");
        comando.add(pais1.getAbreviatura());
        comando.add(String.join("x", atacante));
        comando.add(pais2.getAbreviatura());
        comando.add(String.join("x", defensor));

        Ejecutor.comando(String.join(" ", comando));
    }

    @Override
    public String ayuda() {
        return "atacar <abreviatura_país1> <abreviatura_país2>";
    }
}
