package gal.sdc.usc.risk.menu;

import gal.sdc.usc.risk.menu.comandos.Ejecutor;
import gal.sdc.usc.risk.menu.comandos.mapa.ObtenerColor;
import gal.sdc.usc.risk.menu.comandos.mapa.ObtenerContinente;
import gal.sdc.usc.risk.menu.comandos.mapa.ObtenerFrontera;
import gal.sdc.usc.risk.menu.comandos.mapa.ObtenerPaises;
import gal.sdc.usc.risk.menu.comandos.mapa.VerMapa;
import gal.sdc.usc.risk.menu.comandos.partida.Jugador;
import gal.sdc.usc.risk.menu.comandos.preparacion.AsignarMision;
import gal.sdc.usc.risk.menu.comandos.preparacion.AsignarMisiones;
import gal.sdc.usc.risk.menu.comandos.preparacion.CrearJugador;
import gal.sdc.usc.risk.menu.comandos.preparacion.CrearJugadores;
import gal.sdc.usc.risk.menu.comandos.preparacion.CrearMapa;
import gal.sdc.usc.risk.tablero.valores.Errores;
import gal.sdc.usc.risk.util.Colores;
import gal.sdc.usc.risk.util.Colores.Color;
import gal.sdc.usc.risk.util.Recursos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;


public class Menu extends Partida {
    public Menu() {
        // Iniciar juego
        String orden;

        boolean primero = true;

        try {
            BufferedReader bufferLector;
            File fichero = Recursos.get("comandos.csv");
            FileReader lector = new FileReader(fichero);
            bufferLector = new BufferedReader(lector);

            while ((orden = bufferLector.readLine()) != null) {
                if (!primero) {
                    System.out.println();
                } else {
                    primero = false;
                }
                System.out.print(new Colores("$> ", Color.AMARILLO));
                System.out.println(orden);

                if (!orden.isEmpty()) {
                    this.derivar(orden);
                }
            }
            bufferLector.close();

            Scanner input = new Scanner(System.in);
            while (true) {
                if (!primero) {
                    System.out.println();
                } else {
                    primero = false;
                }
                System.out.print(new Colores("$> ", Color.AMARILLO));
                orden = input.nextLine();

                if (!orden.isEmpty()) {
                    this.derivar(orden);
                }
            }
        } catch (Exception excepcion) {
            excepcion.printStackTrace();
        }
    }

    private void derivar(String orden) {
        String[] partes = orden.toLowerCase().split(" ");
        Ejecutor.setComandos(partes);
        String comando = partes[0];

        // COMANDOS INICIALES PARA EMPEZAR A JUGAR
        //    crear mapa
        //    crear jugadores <nombre_fichero>
        //    crear <nombre_jugador> <nombre_color>
        //    asignar misiones
        //    asignar paises <nombre_fichero>
        //    asignar <nombre_pais> <nombre_jugador>

        // COMANDOS DISPONIBLES DURANTE EL JUEGO
        //    acabar
        //    atacar <nombre_pais> <nombre_pais>
        //    describir continente <nombre_continente>
        //    describir frontera <nombre_pais>
        //    describir frontera <nombre_continente>
        //    describir jugador <nombre_jugador>
        //    describir pais <nombre_pais>
        //    jugador
        //    repartir ejercitos
        //    ver mapa
        //    ver pais <nombre_pais>
        switch (comando) {
            case "crear":
                if (partes.length == 2) {
                    if (partes[1].equals("mapa")) {
                        Ejecutor.comando(CrearMapa.class);
                    } else {
                        Resultado.error(Errores.COMANDO_INCORRECTO);
                    }
                } else if (partes.length == 3) {
                    if (partes[1].equals("jugadores")) {
                        Ejecutor.comando(CrearJugadores.class);
                    } else {
                        Ejecutor.comando(CrearJugador.class);
                    }
                } else {
                    Resultado.error(Errores.COMANDO_INCORRECTO);
                }
                break;
            case "obtener":
                if (partes.length == 3) {
                    switch (partes[1]) {
                        case "frontera":
                        case "fronteras":
                            Ejecutor.comando(ObtenerFrontera.class);
                            break;
                        case "continente":
                            Ejecutor.comando(ObtenerContinente.class);
                            break;
                        case "color":
                            Ejecutor.comando(ObtenerColor.class);
                            break;
                        case "pais":
                        case "paises":
                            Ejecutor.comando(ObtenerPaises.class);
                            break;
                        default:
                            Resultado.error(Errores.COMANDO_INCORRECTO);
                            break;
                    }
                } else {
                    Resultado.error(Errores.COMANDO_INCORRECTO);
                }
                break;
            case "ver":
                if (partes.length == 2) {
                    if (partes[1].equals("mapa")) {
                        Ejecutor.comando(VerMapa.class);
                    } else {
                        Resultado.error(Errores.COMANDO_INCORRECTO);
                    }
                } else {
                    Resultado.error(Errores.COMANDO_INCORRECTO);
                }
                break;
            case "asignar":
                if (partes.length != 3) {
                    Resultado.error(Errores.COMANDO_INCORRECTO);
                } else if (partes[1].equals("paises")) {
                    // asignarPaises es un método de la clase Menu que recibe como entrada el fichero
                    // en el que se encuentra la asignación de países a jugadores. Dentro de este
                    // método se invocará a otros métodos de las clases que contienen los atributos
                    // y los métodos necesarios para realizar esa invocación
                    // asignarPaises(Recursos.get(partes[2]));
                } else if (partes[1].equals("misiones")) {
                    Ejecutor.comando(AsignarMisiones.class);
                } else {
                    Ejecutor.comando(AsignarMision.class);
                }
                break;
            case "jugador":
                Ejecutor.comando(Jugador.class);
                break;
            default:
                Resultado.error(Errores.COMANDO_INCORRECTO);
                break;
        }
    }
}
