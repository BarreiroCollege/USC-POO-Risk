package gal.sdc.usc.risk.menu;

import gal.sdc.usc.risk.menu.comandos.CrearMapa;
import gal.sdc.usc.risk.util.Colores;
import gal.sdc.usc.risk.util.Colores.Color;
import gal.sdc.usc.risk.util.Recursos;
import gal.sdc.usc.risk.tablero.Paises;
import gal.sdc.usc.risk.tablero.Pais;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Menu {
    public Menu() {
        // Inicialización de algunos atributos

        // Iniciar juego
        String orden;
        BufferedReader bufferLector;
        try {
            File fichero = Recursos.get("comandos.csv");
            File fichero2 = Recursos.get("jugadores.csv");
            FileReader lector = new FileReader(fichero);
            bufferLector = new BufferedReader(lector);
            while ((orden = bufferLector.readLine()) != null) {
                System.out.println(new Colores("$>", Color.AMARILLO) + " " + orden);
                String[] partes = orden.split(" ");
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
                                System.out.println("\nComando incorrecto.");
                            }
                        }
                        if (partes.length == 3) {
                            if (partes[1].equals("jugadores")) {
                                crearJugador(Recursos.get(partes[2]));
                            } else {
                                crearJugador(partes[1], partes[2]);
                            }
                        } else {
                            System.out.println("\nComando incorrecto.");
                        }
                        break;
                    case "ver":
                        if(partes.length ==2){
                            if (partes[1].equals("mapa")){
                                //verMapa //CREAR LAS FUNCIOMES DE VER!   
                            }
                        }else{
                        System.out.println("\nComando incorrecto.");
                        }
                        break;
                    case "asignar":
                        if (partes.length != 3) {
                            System.out.println("\nComando incorrecto.");
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
                        System.out.println("\nComando incorrecto.");
                }
            }
        } catch (Exception excepcion) {
            excepcion.printStackTrace();
        }
    }

    /**
     * @param file
     */
    public void asignarPaises(File file) {
        
        int i;
        //Se recorre la lista de paises para comprobar si el introducido es correcto
        for(i=0;i<=42;++i){
        if(file!=Paises(nombre,continente,x,y)){
        System.out.println("\nPor favor introduzca un país correcto.");
        }
            }
        else()
        
        // Código necesario para asignar países
        
        
    }

    /**
     * @param nombrePais
     * @param nombreJugador
     */
    public void asignarPaises(String nombrePais, String nombreJugador) {
        // Código necesario para asignar un país a un jugador
    }

    /**
     * @param file
     */
    private void crearJugador(File file) {
        
        // Código necesario para crear a los jugadores del RISK
        

    }

    /**
     * @param file
     */
    private void crearJugador(String nombre, String color) {
        // Código necesario para crear a un jugador a partir de su nombre y color

    }
}
