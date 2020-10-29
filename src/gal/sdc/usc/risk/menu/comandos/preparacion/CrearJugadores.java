package gal.sdc.usc.risk.menu.comandos.preparacion;

import gal.sdc.usc.risk.menu.Partida;
import gal.sdc.usc.risk.menu.comandos.Comando;
import gal.sdc.usc.risk.menu.comandos.Ejecutor;
import gal.sdc.usc.risk.menu.comandos.IComando;
import gal.sdc.usc.risk.util.Recursos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


@Comando(jugando = false)
public class CrearJugadores extends Partida implements IComando {
    @Override
    public void ejecutar(String[] comandos) {
        try {
            BufferedReader bufferLector;
            File fichero = Recursos.get(comandos[2]);
            FileReader lector = new FileReader(fichero);
            bufferLector = new BufferedReader(lector);
            String linea;

            String[] partes;
            String[] jugador = new String[]{"crear", "", ""};
            while ((linea = bufferLector.readLine()) != null) {
                partes = linea.split(";");
                if (partes.length == 2) {
                    jugador[1] = partes[0].trim();
                    jugador[2] = partes[1].trim();

                    Ejecutor.setComandos(jugador);
                    Ejecutor.comando(CrearJugador.class);
                }
            }
            bufferLector.close();
        } catch (FileNotFoundException e) {
            // Archivo no existe, ignoramos
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
