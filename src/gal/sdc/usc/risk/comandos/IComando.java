package gal.sdc.usc.risk.comandos;


public interface IComando {
    void ejecutar(String[] comandos);

    default String nombre() {
        String[] partes = this.getClass().getName().split("\\.");
        return partes[partes.length - 1].replaceAll("\\d+", "").replaceAll("(.)([A-Z])", "$1 $2");
    }

    String ayuda();
}
