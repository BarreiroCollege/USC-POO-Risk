package gal.sdc.usc.risk.menu.comandos.preparacion;

import gal.sdc.usc.risk.menu.Partida;
import gal.sdc.usc.risk.menu.comandos.Comando;
import gal.sdc.usc.risk.menu.comandos.Ejecutor;
import gal.sdc.usc.risk.menu.comandos.Preparacion;
import gal.sdc.usc.risk.util.Recursos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


@Preparacion(requiere = CrearMapa.class)
public class CrearJugadores extends Partida implements Comando {
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
            String nombre;
            String color;
            while ((linea = bufferLector.readLine()) != null) {
                partes = linea.split(";");
                if (partes.length == 2) {
                    nombre = partes[0].trim();
                    color = partes[1].trim();
                    jugador[1] = nombre;
                    jugador[2] = color;

                    Ejecutor.setComandos(jugador);
                    Ejecutor.comando(CrearJugador.class);
                }
            }
            bufferLector.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
