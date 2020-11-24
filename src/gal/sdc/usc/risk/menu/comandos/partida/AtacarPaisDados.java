package gal.sdc.usc.risk.menu.comandos.partida;

import gal.sdc.usc.risk.menu.Partida;
import gal.sdc.usc.risk.menu.Resultado;
import gal.sdc.usc.risk.menu.comandos.Comando;
import gal.sdc.usc.risk.menu.comandos.Comandos;
import gal.sdc.usc.risk.menu.comandos.Estado;
import gal.sdc.usc.risk.menu.comandos.IComando;
import gal.sdc.usc.risk.salida.SalidaLista;
import gal.sdc.usc.risk.salida.SalidaObjeto;
import gal.sdc.usc.risk.salida.SalidaValor;
import gal.sdc.usc.risk.tablero.Ejercito;
import gal.sdc.usc.risk.tablero.Jugador;
import gal.sdc.usc.risk.tablero.Pais;
import gal.sdc.usc.risk.tablero.valores.Errores;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Comando(estado = Estado.JUGANDO, comando = Comandos.ATACAR_PAIS_DADOS)
public class AtacarPaisDados extends Partida implements IComando {
    @Override
    public void ejecutar(String[] comandos) {
        Pais pais1 = super.getMapa().getPaisPorNombre(comandos[1]);
        Pais pais2 = super.getMapa().getPaisPorNombre(comandos[3]);
        String[] dadosAtacante = comandos[2].split("x");
        String[] dadosDefensor = comandos[4].split("x");

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
            Resultado.error(Errores.EJERCITOS_NO_SUFICIENTES);
            return;
        }
        super.getComandos().atacando();

        List<Integer> atacante = new ArrayList<>();
        for (String dado : dadosAtacante) {
            atacante.add(Integer.parseInt(dado));
        }
        atacante.sort(Collections.reverseOrder());

        List<Integer> defensor = new ArrayList<>();
        for (String dado : dadosDefensor) {
            defensor.add(Integer.parseInt(dado));
        }
        defensor.sort(Collections.reverseOrder());

        Jugador jugadorDefensor = pais2.getJugador();
        int atacanteOriginal = pais1.getEjercito().toInt();
        int defensorOriginal = pais2.getEjercito().toInt();

        if (atacante.get(0) > defensor.get(0)) {
            new Ejercito().recibir(pais2.getEjercito(), 1);
        } else {
            new Ejercito().recibir(pais1.getEjercito(), 1);
        }

        boolean conquistado = false;
        if (pais2.getEjercito().toInt() == 0) {
            conquistado = true;
        }

        if (!conquistado && atacante.size() >= 2 && defensor.size() >= 2) {
            if (atacante.get(1) > defensor.get(1)) {
                new Ejercito().recibir(pais2.getEjercito(), 1);
            } else {
                new Ejercito().recibir(pais1.getEjercito(), 1);
            }
        }
        if (pais2.getEjercito().toInt() == 0) {
            conquistado = true;
        }

        if (conquistado) {
            pais2.setJugador(super.getJugadorTurno());
            pais2.getEjercito().recibir(pais1.getEjercito(), atacante.size());
        }
        if (conquistado && !super.isHaConquistadoPais()) {
            super.conquistadoPais();
            super.getComandos().paisConquistado();
        }

        SalidaObjeto salida = new SalidaObjeto();
        salida.withEntrada("dadosAtaque", SalidaValor.withSalidaLista(SalidaLista.withInteger(atacante)));
        salida.withEntrada("dadosDefensa", SalidaValor.withSalidaLista(SalidaLista.withInteger(defensor)));
        salida.withEntrada("ejercitosPaisAtaque", SalidaValor.withSalidaLista(SalidaLista.withInteger(atacanteOriginal, pais1.getEjercito().toInt())));
        salida.withEntrada("ejercitosPaisAtaque", SalidaValor.withSalidaLista(SalidaLista.withInteger(defensorOriginal, pais2.getEjercito().toInt())));
        salida.withEntrada("paisAtaquePerceneceA", SalidaValor.withString(pais1.getJugador().getNombre()));
        salida.withEntrada("paisDefensaPerceneceA", SalidaValor.withString(pais2.getJugador().getNombre()/*jugadorDefensor.getNombre()*/));
        salida.withEntrada("continenteConquistado", SalidaValor.withString(pais2.getContinente().getJugador() != null && pais2.getContinente().getJugador()
                .equals(super.getJugadorTurno()) ? pais2.getContinente().getNombre() : "null"));
        Resultado.correcto(salida);
    }

    @Override
    public String ayuda() {
        return "atacar <nombre_país1> <dadosAtaque> <nombre_país2> <dadosDefensa>";
    }
}
