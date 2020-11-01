-injars       build/risk.jar
-outjars      build/risk_out.jar
-libraryjars  <java.home>/jmods/java.base.jmod(!**.jar;!module-info.class)
-printmapping build/risk.map
-keepattributes *Annotation*

-keep public class gal.sdc.usc.risk.Main {
    public static void main(java.lang.String[]);
}

-keep class gal.sdc.usc.risk.menu.comandos.IComando { void ejecutar(java.lang.String[]); }
-keep class gal.sdc.usc.risk.menu.comandos.IComando { java.lang.String ayuda(); }