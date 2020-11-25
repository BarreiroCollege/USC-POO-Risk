package gal.sdc.usc.risk.evaluacion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Answer {
    private final HashMap<String, String> singleValues;
    private final HashMap<String, ArrayList<String>> arrayOfSingles;
    private final HashMap<String, ArrayList<String>> arrayOfDuples;
    private final String answer;

    public Answer(String answer) {
        // Initialize values
        this.singleValues = new HashMap<>();
        this.arrayOfSingles = new HashMap<>();
        this.arrayOfDuples = new HashMap<>();
        this.answer = answer;

        // Set values for attributes
        Scanner scanItem = new Scanner(answer);
        while (scanItem.hasNextLine()) {
            String duple = scanItem.nextLine();
            String[] dupleParts = duple.replaceAll("\\s+", "").split(":");
            if (dupleParts.length > 1) {
                switch (detectValueType(dupleParts[1])) {
                    case "Single":
                        this.singleValues.put(dupleParts[0].trim(),
                                dupleParts[1].replaceAll("\"", "")
                                        .replace(",", "")
                                        .trim());
                        break;
                    case "ArrayOfValues":
                        String[] values = dupleParts[1].split(",");
                        ArrayList<String> listOfValues = new ArrayList<>();
                        for (String value : values)
                            listOfValues.add(value.replaceAll("\"", "")
                                    .replace("[", "")
                                    .replace("]", "")
                                    .trim());
                        this.arrayOfSingles.put(dupleParts[0], listOfValues);
                        break;
                    case "ArrayOfDuples":
                        ArrayList<String> listOfDuples = new ArrayList<>();
                        Matcher mArrayOfDuples = Pattern.compile("\\{(.*?)\\}")
                                .matcher(dupleParts[1].trim());
                        while (mArrayOfDuples.find()) {
                            listOfDuples.add(mArrayOfDuples.group(1).replace(",", "").trim());
                        }
                        this.arrayOfDuples.put(dupleParts[0].trim(), listOfDuples);
                        break;
                }
            }
        }
    }

    private String detectValueType(String values) {
        String typeValue = null;
        Matcher mArray = Pattern.compile("\\[(.*?)\\]").matcher(values);
        if (mArray.find()) {
            Matcher mArrayOfDuples = Pattern.compile("\\{(.*?)\\}").matcher(values);
            if (mArrayOfDuples.find()) typeValue = "ArrayOfDuples";
            else typeValue = "ArrayOfValues";
        } else typeValue = "Single";
        //
        return typeValue;
    }

    public HashMap<String, String> getSingleValues() {
        return this.singleValues;
    }

    public HashMap<String, ArrayList<String>> getArrayOfSingles() {
        return this.arrayOfSingles;
    }

    public HashMap<String, ArrayList<String>> getArrayOfDuples() {
        return this.arrayOfDuples;
    }

    @Override
    public boolean equals(Object answer) {
        if (answer instanceof Answer) {
            // Check single values
            for (String key : this.singleValues.keySet()) {
                if (!this.singleValues.get(key).equals(((Answer) answer).getSingleValues().get(key)))
                    return false;
            }
            // Check arrays of single values
            for (String key : this.arrayOfSingles.keySet()) {
                ArrayList<String> thisAoSV = this.arrayOfSingles.get(key);
                ArrayList<String> otherAoSV = ((Answer) answer).getArrayOfSingles().get(key);
                if (otherAoSV != null) {
                    Collections.sort(thisAoSV);
                    Collections.sort(otherAoSV);
                    if (!thisAoSV.equals(otherAoSV)) return false;
                } else return false;
            }
            // Check arrays of duples values
            for (String key : this.arrayOfDuples.keySet()) {
                ArrayList<String> thisAoDV = this.arrayOfDuples.get(key);
                ArrayList<String> otherAoDV = ((Answer) answer).getArrayOfDuples().get(key);
                if (otherAoDV != null) {
                    Collections.sort(thisAoDV);
                    Collections.sort(otherAoDV);
                    if (!thisAoDV.equals(otherAoDV)) return false;
                } else return false;
            }
        } else return false;
        //
        return true;
    }

    @Override
    public String toString() {
        return ("{\n" + this.answer + "}\n");
    }
}
