/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gal.sdc.usc.risk.menu;


//Para los jugadores fuera del fichero
public class Jugadores2 implements Comando{
public void crearJugador(String nombre, String color){
    ArrayList Yugadores= new ArrayList<>();
    if((Yugadores.includes( nombre))=true){
    System.out.println("Jugador ya existente\n");
    }
    else{
        Yugadores.add(nombre);
        System.out.println("Jugador creado\n");
    }
}
}

