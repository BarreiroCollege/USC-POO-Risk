package gal.sdc.usc.risk.menu.comandos.preparacion;

import gal.sdc.usc.risk.menu.Partida;
import gal.sdc.usc.risk.menu.comandos.Comando;
import gal.sdc.usc.risk.menu.comandos.Comandos;
import gal.sdc.usc.risk.menu.comandos.Ejecutor;
import gal.sdc.usc.risk.menu.comandos.Estado;
import gal.sdc.usc.risk.menu.comandos.IComando;
import gal.sdc.usc.risk.util.Recursos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


@Comando(estado = Estado.PREPARACION, comando = Comandos.ASIGNAR_MISIONES)
public class AsignarMisiones extends Partida implements IComando {
    @Override
    public void ejecutar(String[] comandos) {
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
                    Ejecutor.comando("asignar mision " + partes[0].trim() + " " + partes[1].trim(), false);
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
        return "asignar misiones <nombre_fichero>";
    }
}
