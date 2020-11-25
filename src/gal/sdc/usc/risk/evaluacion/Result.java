package gal.sdc.usc.risk.evaluacion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Result {
    private final ArrayList<String> textsOfCommands;
    private final ArrayList<String> textsOfAnswer;
    private final HashMap<String, ArrayList<Command>> commands;

    public Result(String file) {
        // Initialize attributes
        textsOfCommands = new ArrayList<>();
        textsOfAnswer = new ArrayList<>();
        commands = new HashMap<>();

        // Processing results
        this.readCommandsAndResults(file);
        this.generateCommandRepresentation();
    }

    private void generateCommandRepresentation() {
        for (int i = 0; i < this.textsOfAnswer.size(); i++) {
            String answer = this.textsOfAnswer.get(i);
            Command command = new Command("[" + i + "] " + this.textsOfCommands.get(i));
            Scanner scan = new Scanner(answer);
            ArrayList<Answer> listOfAnswers = new ArrayList<>();
            while (scan.hasNextLine()) {
                if (scan.nextLine().replaceAll("\\s+", "").trim().equals("{")) {
                    StringBuilder result = new StringBuilder();
                    String linea = scan.nextLine();
                    while (!linea.replaceAll("\\s+", "").trim().equals("}")) {
                        result.append(linea).append("\n");
                        linea = scan.nextLine();
                    }
                    listOfAnswers.add(new Answer(result.toString()));
                }
            }
            command.setAnswers(listOfAnswers);
            this.generalCommand(this.textsOfCommands.get(i));
            if (this.commands.get(this.generalCommand(this.textsOfCommands.get(i))) != null) {
                this.commands.get(this.generalCommand(this.textsOfCommands.get(i))).add(command);
            } else {
                ArrayList<Command> pCommands = new ArrayList<>();
                pCommands.add(command);
                this.commands.put(this.generalCommand(this.textsOfCommands.get(i)), pCommands);
            }
        }
    }

    private String generalCommand(String command) {
        String gCommand = null;
        if (command.contains(GenCommands.CREAR_MAPA)) gCommand = GenCommands.CREAR_MAPA;
        else if (command.contains(GenCommands.CREAR_JUGADORES)) gCommand = GenCommands.CREAR_JUGADORES;
        else if (command.contains("crear")) gCommand = GenCommands.CREAR_JUGADOR;
        else if (command.contains(GenCommands.OBTENER_FRONTERA)) gCommand = GenCommands.OBTENER_FRONTERA;
        else if (command.contains(GenCommands.OBTENER_CONTINENTE)) gCommand = GenCommands.OBTENER_CONTINENTE;
        else if (command.contains(GenCommands.OBTENER_COLOR)) gCommand = GenCommands.OBTENER_COLOR;
        else if (command.contains(GenCommands.OBTENER_PAISES)) gCommand = GenCommands.OBTENER_PAISES;
        else if (command.contains(GenCommands.ASIGNAR_MISIONES)) gCommand = GenCommands.ASIGNAR_MISIONES;
        else if (command.contains(GenCommands.ASIGNAR_MISION)) gCommand = GenCommands.ASIGNAR_MISION;
        else if (command.contains(GenCommands.ASIGNAR_PAISES)) gCommand = GenCommands.ASIGNAR_PAISES;
        else if (command.contains(GenCommands.ASIGNAR_PAIS)) gCommand = GenCommands.ASIGNAR_PAIS;
        else if (command.contains(GenCommands.REPARTIR_EJERCITOS)) gCommand = GenCommands.REPARTIR_EJERCITOS;
        else if (command.contains(GenCommands.ACABAR_TURNO)) gCommand = GenCommands.ACABAR_TURNO;
        else if (command.contains(GenCommands.CAMBIAR_CARTAS)) gCommand = GenCommands.CAMBIAR_CARTAS;
        else if (command.contains(GenCommands.DESCRIBIR_JUGADOR)) gCommand = GenCommands.DESCRIBIR_JUGADOR;
        else if (command.contains(GenCommands.JUGADOR)) gCommand = GenCommands.JUGADOR;
        else if (command.contains(GenCommands.DESCRIBIR_PAIS)) gCommand = GenCommands.DESCRIBIR_PAIS;
        else if (command.contains(GenCommands.DESCRIBIR_CONTINENTE)) gCommand = GenCommands.DESCRIBIR_CONTINENTE;
        else if (command.contains(GenCommands.ATACAR)) gCommand = GenCommands.ATACAR;
        else if (command.contains(GenCommands.REARMAR)) gCommand = GenCommands.REARMAR;
        else if (command.contains(GenCommands.ASIGNAR_CARTA)) gCommand = GenCommands.ASIGNAR_CARTA;
        //
        return gCommand;
    }

    private void readCommandsAndResults(String file) {
        StringBuilder resultsOfCommands = new StringBuilder();
        String command = null;
        try {
            FileReader fr = new FileReader(new File(file));
            BufferedReader br = new BufferedReader(fr);
            String linea;
            while ((linea = br.readLine()) != null) {
                // Store the results for the current command
                if (linea.contains("$>") || linea.contains("EOF")) {
                    if (command != null) {
                        this.textsOfCommands.add(command.toLowerCase());
                        this.textsOfAnswer.add(resultsOfCommands.toString().toLowerCase());
                        resultsOfCommands = new StringBuilder();
                    }
                    if (linea.contains("EOF")) break;
                    else command = linea.substring(linea.indexOf("$>")).trim();
                }
                // Updating results for the current command
                else {
                    resultsOfCommands.append(linea).append("\n");
                }
            }
        } catch (IOException excep) {
            System.out.println(excep.getMessage());
        }
    }

    public HashMap<String, ArrayList<Command>> getCommands() {
        return commands;
    }
}