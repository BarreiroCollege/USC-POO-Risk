package gal.sdc.usc.risk.excepciones;

public abstract class Excepcion extends RuntimeException {
    private final Errores error;

    protected Excepcion(Errores error) {
        super(error.getMensaje());

        this.error = error;
    }

    public final String getMensaje() {
        return error.getMensaje();
    }

    public final Integer getCodigo() {
        return error.getCodigo();
    }
}
