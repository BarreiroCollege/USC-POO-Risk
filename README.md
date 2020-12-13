# Risk

- Diego Barreiro Pérez ([diego.barreiro.perez@rai.usc.es](mailto:diego.barreiro.perez@rai.usc.es))
- Miguel Bugarín Carreira ([miguel.bugarin@rai.usc.es](mailto:miguel.bugarin@rai.usc.es))

## Requisitos Parte 2

### Jerarquía de cartas

La clase abstracta `Carta` se encuentra en el paquete `gal.sdc.usc.risk.tablero`, teniendo un constructor tipo
_Builder_ para facilitar su inicialización. Las cartas se pueden crear mediante
`new Carta.Builder().withPais(pais).withSubEquipamiento(SubEquipamiento.).builder()`.  
Esto creará una instancia de `Carta` con el país y subtipo especificado.

Esta clase es extendida por las subclases `Infanteria`, `Caballeria` y `Artilleria`, las cuales se encuentran en
el paquete `gal.sdc.usc.risk.tablero.carta`, siendo también abstractas. Estas clases son extendidas por las
respectivas subclases en el paquete `gal.sdc.usc.risk.tablero.carta.X`, siendo X el tipo en cuestión. Estos subtipos
tienen constructores que reciben el país de la carta.

### Jerarquía de ejércitos

### Excepciones

### Interfaz _Consola_

La interfaz `Consola` está en el paquete `gal.sdc.usc.risk.jugar`, la cual, además de los requisitos, tiene un método
con el que imprimir una línea en blanco. La clase `ConsolaNormal` que extiende `Consola` está en el mismo paquete.  
La clase `Partida` (que contiene todos los datos del juego) tiene una instanca estática de `Consola` con la que se
puede acceder desde cualquier punto del programa.
