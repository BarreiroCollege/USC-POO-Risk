package gal.sdc.usc.risk.evaluacion;


public class Error {
    //
    public final static String MORE_ANSWERS = "Existen más respuestas de las requeridas";
    public final static String MISSING_ANSWER = "Alguna de las respuestas no es correcta o no se encuentra";
    //
    private String description;
    private String idCommand;
    private Answer missingGold;

    public Error(String idCommand, String description, Answer missingGold) {
        this.description = description;
        this.idCommand = idCommand;
        this.missingGold = missingGold;
    }

    public Error(String idCommand, String description) {
        this.description = description;
        this.idCommand = idCommand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIdCommand() {
        return idCommand;
    }

    public void setIdCommand(String idCommand) {
        this.idCommand = idCommand;
    }

    @Override
    public String toString() {
        String cc = "\u001B[31m";
        return (cc + "ERROR EN COMANDO " + this.idCommand + "\n" +
                cc + "DESCRIPCIÓN DEL ERROR: " + this.description + "\n" +
                ((this.missingGold != null) ? (cc + "Respuesta requerida:\n" +
                        this.missingGold.toString())
                        : ""));
    }
}
