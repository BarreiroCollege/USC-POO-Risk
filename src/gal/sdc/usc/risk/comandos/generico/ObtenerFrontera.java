package gal.sdc.usc.risk.comandos.generico;

import gal.sdc.usc.risk.jugar.Partida;
import gal.sdc.usc.risk.jugar.Resultado;
import gal.sdc.usc.risk.comandos.Comando;
import gal.sdc.usc.risk.comandos.Comandos;
import gal.sdc.usc.risk.comandos.Estado;
import gal.sdc.usc.risk.comandos.IComando;
import gal.sdc.usc.risk.salida.SalidaObjeto;
import gal.sdc.usc.risk.tablero.Pais;
import gal.sdc.usc.risk.excepciones.Errores;

@Comando(estado = Estado.CUALQUIERA, comando = Comandos.OBTENER_FRONTERA)
public class ObtenerFrontera extends Partida implements IComando {
    @Override
    public void ejecutar(String[] comandos) {
        String clave = comandos[2];

        if (super.getMapa() == null) {
            Resultado.error(Errores.MAPA_NO_CREADO);
            return;
        }

        Pais pais = super.getMapa().getPaisPorNombre(clave);
        if (pais == null) {
            Resultado.error(Errores.PAIS_NO_EXISTE);
            return;
        }

        SalidaObjeto salida = new SalidaObjeto();
        salida.put("frontera", pais.getFronteras().getTodas());
        Resultado.correcto(salida);
    }

    @Override
    public String ayuda() {
        return "obtener frontera <abreviatura_país>";
    }
}
