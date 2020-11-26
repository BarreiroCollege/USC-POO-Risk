package gal.sdc.usc.risk.correccion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Comparador {
    private final StringBuilder sb = new StringBuilder();
    private final Lector alumno;
    private final Lector profesor;

    private void success(String o) {
        append("[" + Ajustes.Colores.VERDE.getFondo() + Ajustes.Colores.NEGRO.getTexto() + "OK" + Ajustes.Colores.RESET + "] " +
                Ajustes.Colores.VERDE.getTexto() + o + Ajustes.Colores.RESET);
    }

    private void warn(String o) {
        append("[" + Ajustes.Colores.AMARILLO.getFondo() + Ajustes.Colores.NEGRO.getTexto() + "WARN" + Ajustes.Colores.RESET + "] " +
                Ajustes.Colores.AMARILLO.getTexto() + o + Ajustes.Colores.RESET);
    }

    private void danger(String o) {
        append("[" + Ajustes.Colores.ROJO.getFondo() + Ajustes.Colores.NEGRO.getTexto() + "ERROR" + Ajustes.Colores.RESET + "] " +
                Ajustes.Colores.ROJO.getTexto() + o + Ajustes.Colores.RESET);
    }

    private void comando(String o) {
        append(Ajustes.Colores.AMARILLO.getTexto() + "$> " + Ajustes.Colores.RESET + Ajustes.Colores.VIOLETA.getTexto() + o + Ajustes.Colores.RESET);
    }

    public Comparador(Lector alumno, Lector profesor) {
        this.alumno = alumno;
        this.profesor = profesor;
        this.comparar();
    }

    public void append() {
        append("");
    }

    public void append(String a) {
        sb.append(a).append("\n");
    }

    private void comparar() {
        if (alumno.getComandos().size() != profesor.getComandos().size()) {
            warn("El número de comandos en los archivos no coincide, es posible que aparezcan discrepancias.");
        }

        for (int ejecucion = 0; ejecucion < profesor.getComandos().size(); ejecucion++) {
            HashMap<String, List<Object>> ejecucionProfesor = profesor.getComandos().get(ejecucion);
            HashMap<String, List<Object>> ejecucionAlumno = alumno.getComandos().get(ejecucion);

            List<String> comandosProfesor = new ArrayList<>(ejecucionProfesor.keySet()),
                    comandosAlumno = new ArrayList<>(ejecucionAlumno.keySet());
            List<List<Object>> resultadosProfesor = new ArrayList<>(ejecucionProfesor.values()),
                    resultadosAlumno = new ArrayList<>(ejecucionAlumno.values());
            for (int comando = 0; comando < ejecucionProfesor.size(); comando++) {
                String comandoProfesor = Parseador.Texto.sinMayusculas(comandosProfesor.get(comando)),
                        comandoAlumno = Parseador.Texto.sinMayusculas(comandosAlumno.get(comando));
                List<Object> resultadoProfesor = resultadosProfesor.get(comando),
                        resultadoAlumno = resultadosAlumno.get(comando);

                comando(comandoProfesor);

                if (!comandoProfesor.equals(comandoAlumno)) {
                    danger("COMANDO NO COINCIDE");
                    danger("GOLD   -> " + comandoProfesor);
                    danger("RESULT -> " + comandoAlumno);
                } else {
                    if (resultadoAlumno.size() != resultadoProfesor.size()) {
                        warn("El número de resultados no coinciden.");
                    } else {
                        boolean errores = false;

                        for (int resultado = 0; resultado < resultadoProfesor.size(); resultado++) {
                            Object objetoProfesor = resultadosProfesor.get(resultado),
                                    objetoAlumno = resultadosAlumno.get(resultado);

                        }
                        for (Object resultado : resultadoProfesor) {

                        }

                        if (!errores) {
                            success("COMANDO CORRECTO");
                        }
                    }
                }

                append();
                append();
            }
        }
    }

    @Override
    public String toString() {
        return sb.toString();
    }
}
