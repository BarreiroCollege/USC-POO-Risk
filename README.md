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

La jerarquía especificada de ejércitos se encuentra en el paquete `gal.sdc.usc.risk.tablero`. Ahí dentro se encuentra
la clase abstracta `Ejercito`, la cual es extendida por las otras subclases. Al igual que con la jerarquía de cartas,
están en `gal.sdc.usc.risk.tablero.ejercitos`, estando estos en en sus respectivos paquetes.

Además, aparte de tener `EjercitoBase` y `EjercitoCompuesto`, está también `EjercitoNuevo`, la cual no es abstracta,
y permite crear un ejército sin color (ya que debido a la implementación de ejércitos, el traspaso siempre es mediante
otro ejército), haciendo de "basura" de ejércitos o fábrica.

### Excepciones

Las excepciones se encuentran en el paquete `gal.sdc.usc.risk.excepciones`. En ella, se encuentra la clase abstracta
`Excepcion`, la cual es la superclase de todas las sub-excepciones. El tipo de excepción es `RuntimeException`, ya
que así permite no tener que extender mediante `throws`. Las sub-excepciones todas extienden esta clase,
recibiendo como argumento un elemento del enum `Errores`.  
Existe el enum `Errores` con los valores constantes de los errores todos, el cual recibe el código de error, el mensaje
del error y la sub-excepción que lanza. De esta forma, se consigue más versatilidad al estar definidas como constante
todas las posibles.

La gestión de errores tiene lugar en la clase `Ejecutor`, dentro del paquete `gal.sdc.usc.risk.comandos`. Esta clase
es una extensión del `Callable<Boolean>`, la cual permite "ejecutar" la clase y devolver un booleano (representando
si la ejecución del comando tuvo éxito o no). La gestión de errores tiene lugar en el `public static void comando`,
en el cual se intenta obtener el resultado de la ejecución. Los comandos lanzarán las excepciones durante la ejecución,
y se gestionarán con la excepción `RuntimeException` (la cual extiende a `Exception`), la cual contendrá una serie de
causas, siendo una de ellas una clase extendida de `Excepcion`.  
Cuando esto se alcanza, se manda esta excepción al gestor de errores para escribirlo en el archivo y lanzarlo por
consola.

### Interfaz _Consola_

La interfaz `Consola` está en el paquete `gal.sdc.usc.risk.jugar`, la cual, además de los requisitos, tiene un método
con el que imprimir una línea en blanco. La clase `ConsolaNormal` que extiende `Consola` está en el mismo paquete.  
La clase `Partida` (que contiene todos los datos del juego) tiene una instanca estática de `Consola` con la que se
puede acceder desde cualquier punto del programa.
