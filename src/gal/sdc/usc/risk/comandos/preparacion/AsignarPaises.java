package gal.sdc.usc.risk.comandos.preparacion;

import gal.sdc.usc.risk.comandos.Comando;
import gal.sdc.usc.risk.comandos.Comandos;
import gal.sdc.usc.risk.comandos.Ejecutor;
import gal.sdc.usc.risk.comandos.Estado;
import gal.sdc.usc.risk.comandos.IComando;
import gal.sdc.usc.risk.jugar.Partida;
import gal.sdc.usc.risk.salida.Resultado;
import gal.sdc.usc.risk.excepciones.Errores;
import gal.sdc.usc.risk.util.Recursos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


@Comando(estado = Estado.PREPARACION, comando = Comandos.ASIGNAR_PAISES)
public class AsignarPaises extends Partida implements IComando {
    @Override
    public void ejecutar(String[] comandos) {
        if (super.getMapa() == null) {
            Resultado.error(Errores.MAPA_NO_CREADO);
            return;
        }
        if (super.getJugadores().size() < 3 || super.getJugadores().size() > 6) {
            Resultado.error(Errores.JUGADORES_NO_CREADOS);
            return;
        }

        if (super.getJugadores().values().stream().anyMatch(p -> p.getMision() == null)) {
            Resultado.error(Errores.MISIONES_NO_ASIGNADAS);
            return;
        }

        try {
            BufferedReader bufferLector;
            File fichero = Recursos.get(comandos[2]);
            FileReader lector = new FileReader(fichero);
            bufferLector = new BufferedReader(lector);
            String linea;

            String[] partes;
            while ((linea = bufferLector.readLine()) != null) {
                partes = linea.split(";");
                if (partes.length == 2) {
                    Ejecutor.comando("asignar pais " + partes[0].trim() + " " + partes[1].trim(), false);
                }
            }
            bufferLector.close();
        } catch (FileNotFoundException e) {
            // Archivo no existe, ignoramos
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String ayuda() {
        return "asignar paises <nombre_fichero>";
    }
}
