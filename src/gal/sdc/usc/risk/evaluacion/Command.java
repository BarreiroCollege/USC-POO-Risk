package gal.sdc.usc.risk.evaluacion;

import java.util.ArrayList;

public class Command {
    private String command;
    private ArrayList<Answer> answers;

    public Command(String command) {
        this.command = command;
        this.answers = new ArrayList<>();
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public ArrayList<Answer> getAnswers() {
        return this.answers;
    }

    public void setAnswers(ArrayList<Answer> answers) {
        this.answers = answers;
    }
}