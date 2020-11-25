package gal.sdc.usc.risk.evaluacion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public class Evaluation {
    private final Result resultCommands;
    private final Result goldCommands;
    private final Map<String, Float> evaluation;
    private final Map<String, ArrayList<Error>> errors;

    public Evaluation(Result result, Result goldStandard) {
        this.resultCommands = result;
        this.goldCommands = goldStandard;
        this.errors = new HashMap<>();
        this.evaluation = Collections.synchronizedMap(new LinkedHashMap<>());
        // Compute the differences between result answers and gold standard answers
        this.evaluate();
    }

    private void evaluate() {
        for (String command : GenCommands.COMANDS) {
            this.errors.put(command, new ArrayList<>());
            this.evaluation.put(command, this.calculateMark(command));
        }
    }

    private void evaluate(String command) {
        if (GenCommands.COMANDS.contains(command.toLowerCase())) {
            this.errors.put(command, new ArrayList<>());
            this.evaluation.put(command, this.calculateMark(command));
        }
    }

    private Map<String, Float> compareWithGoldStandard(String genCommand) {
        ArrayList<Command> results = this.resultCommands.getCommands().get(genCommand);
        ArrayList<Command> goldStandard = this.goldCommands.getCommands().get(genCommand);
        HashMap<String, Float> hits = null;
        if (results != null && goldStandard != null) {
            hits = new HashMap<>();
            for (Command gold : goldStandard) {
                String idCommand = gold.getCommand();
                hits.put(idCommand, 0f);
                for (Command resultOfCommand : results) {
                    if (resultOfCommand.getCommand().equals(idCommand)) {
                        ArrayList<Answer> goldAnswers = gold.getAnswers();
                        ArrayList<Answer> resultAnswers = resultOfCommand.getAnswers();
                        for (Answer goldAnswer : goldAnswers) {
                            boolean existAnswer = false;
                            for (Answer result : resultAnswers) {
                                if (goldAnswer.equals(result)) {
                                    hits.put(idCommand, hits.get(idCommand) + 1);
                                    existAnswer = true;
                                    break;
                                }
                            }
                            // Any result answer is missing
                            if (!existAnswer)
                                this.errors.get(genCommand).add(new Error(idCommand, Error.MISSING_ANSWER, goldAnswer));
                        }
                        // There are more result answers than gold standard answers
                        if (goldAnswers.size() < resultAnswers.size()) {
                            this.errors.get(genCommand).add(new Error(idCommand, Error.MORE_ANSWERS));
                            hits.put(idCommand, hits.get(idCommand) / resultAnswers.size());
                        }
                        break;
                    }
                }
            }
        }
        //
        return hits;
    }

    private Map<String, Float> generateEvalGoldStandard(String command) {
        ArrayList<Command> goldStandard = this.goldCommands.getCommands().get(command);
        HashMap<String, Float> goldEval = null;
        if (goldStandard != null) {
            goldEval = new HashMap<>();
            for (Command gold : goldStandard) {
                goldEval.put(gold.getCommand(), (float) gold.getAnswers().size());
            }
        }
        //
        return goldEval;
    }

    private float calculateMark(String command) {
        Map<String, Float> goldEval = this.generateEvalGoldStandard(command);
        Map<String, Float> hits = this.compareWithGoldStandard(command);
        float mark = 0;
        if (hits != null && goldEval != null) {
            for (String key : goldEval.keySet()) {
                if (!command.equals(GenCommands.CREAR_MAPA)) {
                    if (Config.DEBUG)
                        System.out.println("Command -> " + key + "[ GoS -> " + goldEval.get(key) + " ] | [ Hi -> " + hits.get(key) + " ]");
                    mark += GenCommands.COMMAND_MARKS.get(command) * hits.get(key) / (goldEval.get(key) * goldEval.size());
                }
            }
        }
        //
        if (Config.DEBUG) System.out.println("MARK -> " + mark + "\n");
        return mark;
    }

    public Map<String, Float> getEvaluation() {
        return this.evaluation;
    }

    public Map<String, ArrayList<Error>> getErrors() {
        return this.errors;
    }

    @Override
    public String toString() {
        StringBuilder toString = new StringBuilder();
        // Show MARKS
        toString.append("\u001b[30;1m---------- PUNTUACIONES ----------\n");
        for (String commandType : this.evaluation.keySet()) {
            toString.append(commandType).append(" --> ").append(this.evaluation.get(commandType)).append("\n");
        }
        // Show ERRORS
        toString.append("\n\u001b[30;1m---------- ERRORES ----------\n");
        if (!this.errors.isEmpty()) {
            for (String idCommand : this.errors.keySet()) {
                for (Error error : this.errors.get(idCommand)) {
                    toString.append(error).append("\n");
                }
            }
        } else toString.append("No hay errores\n");
        //
        return toString.toString();
    }
}