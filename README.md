# Risk

- Diego Barreiro Pérez ([diego.barreiro.perez@rai.usc.es](mailto:diego.barreiro.perez@rai.usc.es))
- Miguel Bugarín Carreira ([miguel.bugarin@rai.usc.es](mailto:miguel.bugarin@rai.usc.es))

![Ejemplo](https://i.imgur.com/wXqjwl2.png)

## Juego

Para compilar el proyecto, se puede **ejecutar el script `build.sh`** que se encuentra en la raiz del proyecto, el cual
generará todos bytecodes necesarios, y luego los empaquetará en un JAR disponible dentro de la carpeta `build/`.  
Para ejecutarlo, basta con escribir `java -jar build/risk.jar`.

En cuanto a los archivos de comandos, estos se encuentran en el directorio `res/`, a modo de "_resources_" disponibles
en todo momento.

## Estructura del Proyecto

El proyecto se compone de los siguientes paquetes:

* **`gal.sdc.usc.risk.comandos`**: Clases con los comandos y su respectiva ejecución. Todos ellos implementan la interfaz
`IComando` en el mismo paquete, y se agrupan según su estado en comandos genéricos, ya jugando (partida) y de
preparación. Los comandos además tienen la anotación `Comando`, en la cual se especifica el estado de la partida en el
que se puede ejecutar el comando del enum `Estado`, y su respectiva expresión regular que lo lanzará, especificado en
el enum `Comandos`.  
Además, en este mismo paquete se encuentra una clase `Ejecutor`. Este ejecutor es el encargado de ejecutar los comandos,
tal como su nombre indica. Esta clase es una implementación de `Callable`, permitiendo ejecutar las clases como si
fuesen métodos, y devolver un valor (un `Boolean` en este caso, indicando si hubo éxito o no). El `Ejecutor`, al llamar
a procesar un comando, creará un nuevo thread y ejecutará el comando ahí. Todos los comandos se ejecutan llamando a su
método `ejecutar()`.

* **`gal.sdc.usc.risk.excepciones`**: Clases con todas las posibles excepciones. Además, se encuentra el enum `Errores`,
en el cual se especifican todos los posibles errores y su tipo, con el que se lanzará la respectiva excepción, siempre
siendo una extensión de la clase abstracta `ExcepcionRISK`.

* **`gal.sdc.usc.risk.jugar`**:

* **`gal.sdc.usc.risk.salida`**:

* **`gal.sdc.usc.risk.tablero`**:

* **`gal.sdc.usc.risk.util`**:

## Requisitos Parte 2

### Jerarquía de cartas

La clase abstracta `Carta` se encuentra en el paquete `gal.sdc.usc.risk.tablero`, teniendo un constructor tipo
_Builder_ para facilitar su inicialización. Las cartas se pueden crear mediante
`new Carta.Builder().withPais(pais).withSubEquipamiento(SubEquipamientos.X).builder()`.  
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
`ExcepcionRISK`, la cual es la superclase de todas las sub-excepciones. El tipo de excepción es `RuntimeException`, ya
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
causas, siendo una de ellas una clase extendida de `ExcepcionRISK`.  
Cuando esto se alcanza, se manda esta excepción al gestor de errores para escribirlo en el archivo y lanzarlo por
consola.

### Interfaz _Consola_

La interfaz `Consola` está en el paquete `gal.sdc.usc.risk.jugar`, la cual, además de los requisitos, tiene un método
con el que imprimir una línea en blanco. La clase `ConsolaNormal` que extiende `Consola` está en el mismo paquete.  
La clase `Partida` (que contiene todos los datos del juego) tiene una instanca estática de `Consola` con la que se
puede acceder desde cualquier punto del programa.
