package gal.sdc.usc.risk.menu.comandos;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Preparacion {
    Class<? extends Comando> requiere() default Comando.class;
}
