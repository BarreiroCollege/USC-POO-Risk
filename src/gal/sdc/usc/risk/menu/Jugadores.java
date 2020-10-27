/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gal.sdc.usc.risk.menu;


public class Jugadores implements Comando{
public void crearJugador(File fichero){
    //Array de jugadores
    ArrayList Jugadores= new ArrayList<>();
    //Se lee el archivo y se guardan los nombres segun van leyendose
    for (int i = 0; i < fichero.size; i++) {
        Jugadores.add(i,fichero);
    }
}
}

