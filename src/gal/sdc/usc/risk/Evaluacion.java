package gal.sdc.usc.risk;

import gal.sdc.usc.risk.evaluacion.Evaluation;
import gal.sdc.usc.risk.evaluacion.Result;
import gal.sdc.usc.risk.util.Recursos;

public class Evaluacion {
    public static void main(String[] args) {
        Result result = new Result(Recursos.get("resultados.txt").getAbsolutePath());
        Result goldStandard = new Result(Recursos.get("goldstandard.txt").getAbsolutePath());

        Evaluation eval = new Evaluation(result, goldStandard);
        System.out.println(eval);
    }
}