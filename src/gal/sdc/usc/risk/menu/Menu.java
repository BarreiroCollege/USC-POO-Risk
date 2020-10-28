package gal.sdc.usc.risk.menu;

import gal.sdc.usc.risk.menu.comandos.mapa.CrearMapa;
import gal.sdc.usc.risk.menu.comandos.mapa.ObtenerColor;
import gal.sdc.usc.risk.menu.comandos.mapa.ObtenerContinente;
import gal.sdc.usc.risk.menu.comandos.mapa.ObtenerFrontera;
import gal.sdc.usc.risk.menu.comandos.mapa.ObtenerPaises;
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
                        new CrearMapa();
                    } else {
                        Resultado.error(Errores.COMANDO_INCORRECTO);
                    }
                } else if (partes.length == 3) {
                    if (partes[1].equals("jugadores")) {
                        crearJugador(Recursos.get(partes[2]));
                    } else {
                        crearJugador(partes[1], partes[2]);
                    }
                } else {
                    Resultado.error(Errores.COMANDO_INCORRECTO);
                }
                break;
            case "obtener":
                if (partes.length == 3) {
                    if (partes[1].equals("frontera") || partes[1].equals("fronteras")) {
                        new ObtenerFrontera(partes[2]);
                    } else if (partes[1].equals("continente")) {
                        new ObtenerContinente(partes[2]);
                    } else if (partes[1].equals("color")) {
                        new ObtenerColor(partes[2]);
                    } else if (partes[1].equals("pais") || partes[1].equals("paises")) {
                        new ObtenerPaises(partes[2]);
                    }
                } else {
                    Resultado.error(Errores.COMANDO_INCORRECTO);
                }
                break;
            case "ver":
                if (partes.length == 2) {
                    if (partes[1].equals("mapa")) {
                        super.getMapa().imprimir();
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
                    asignarPaises(Recursos.get(partes[2]));
                } else {
                    asignarPaises(partes[1], partes[2]);
                }
                break;
            default:
                Resultado.error(Errores.COMANDO_INCORRECTO);
                break;
        }
    }

    /**
     * @param file
     */
    public void asignarPaises(File file) {

        // Código necesario para asignar países


    }

    /**
     * @param nombrePais
     * @param nombreJugador
     */
    public void asignarPaises(String nombrePais, String nombreJugador) {
        // Código necesario para asignar un país a un jugador
        int i;
        for (i = 0; i < 42; ++i) { //El num de paises es 42
            //nombreJugador.Pais[i]=nombrePais[i];
        }
    }

    /**
     *
     */
    public void crearMapa() {
        //Codigo necesario para crear el mapa
    }

    /**
     * @param file
     */
    private void crearJugador(File file) {

        // Código necesario para crear a los jugadores del RISK


    }

    /**
     * @param nombre
     */
    private void crearJugador(String nombre, String color) {
        // Código necesario para crear a un jugador a partir de su nombre y color
        /* if(nombre!="Gondorff" && nombre!="Hooker" && nombre!="Lonnegan"){
            System.out.println("\nNombre no admitido."); */
    }
}
