package gal.sdc.usc.risk.comandos;


public interface IComando {
    void ejecutar(String[] comandos);

    String ayuda();
}
