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

    private void puntos(int max, int puntos) {
        if (max == 0) return;
        append(Ajustes.Colores.VIOLETA.getTexto() + ">> " + Ajustes.Colores.RESET +
                Ajustes.Colores.CELESTE.getTexto() + puntos + " / " + max + " = " + (int) (((float) puntos / max) * 100) + "%" +
                Ajustes.Colores.RESET);
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
                        int maxResultado = 0, puntosResultados = 0;

                        for (int resultado = 0; resultado < resultadoProfesor.size(); resultado++) {
                            Object objetoProfesor = resultadoProfesor.get(resultado),
                                    objetoAlumno = resultadoAlumno.get(resultado);
                            int max = Comparador.puntuacionMax(objetoProfesor),
                                    puntos = Comparador.puntuacion(objetoProfesor, objetoAlumno);

                            if (max != puntos) {
                                danger("ERROR EN RESULTADO");
                                append(Comparador.imprimirError(objetoProfesor, objetoAlumno));
                            }

                            maxResultado += max;
                            puntosResultados += puntos;

                        }

                        if (maxResultado == puntosResultados) {
                            success("EJECUCIÓN DEL COMANDO SIN ERRORES");
                        }
                        puntos(maxResultado, puntosResultados);
                    }
                }

                append();
                append();
            }
        }
    }

    public static int puntuacion(Object r1, Object r2) {
        if (r1 instanceof DatosDiccionario) {
            return ((DatosDiccionario) r1).puntuacion(r2);
        } else if (r1 instanceof DatosArray) {
            return ((DatosArray) r1).puntuacion(r2);
        } else if (r1 instanceof DatosTupla) {
            return ((DatosTupla) r1).puntuacion(r2);
        } else if (r1 instanceof DatosTexto) {
            return ((DatosTexto) r1).puntuacion(r2);
        }
        return r1.equals(r2) ? 1 : 0;
    }

    public static int puntuacionMax(Object r) {
        if (r instanceof DatosDiccionario) {
            return ((DatosDiccionario) r).puntuacionMax();
        } else if (r instanceof DatosArray) {
            return ((DatosArray) r).puntuacionMax();
        } else if (r instanceof DatosTupla) {
            return ((DatosTupla) r).puntuacionMax();
        }
        return 1;
    }

    public static String imprimirError(Object r1, Object r2) {
        if (r1 instanceof DatosDiccionario) {
            return ((DatosDiccionario) r1).imprimirError(r2);
        } else if (r1 instanceof DatosArray) {
            // return ((DatosArray) r1).imprimirError(r2);
        } else if (r1 instanceof DatosTupla) {
            // return ((DatosTupla) r1).imprimirError(r2);
        } else if (r1 instanceof DatosTexto) {
            // return ((DatosTexto) r1).imprimirError(r2);
        }

        boolean eq = r1.equals(r2);
        String s1 = String.format("[%2d/%2d] %s\n", 1, 1, r1.toString());
        String s2 = String.format("[%2d/%2d] %s", eq ? 1 : 0, 1, r2.toString());
        return Ajustes.Colores.AZUL.getTexto() + s1 + (eq ? Ajustes.Colores.VERDE.getTexto() : Ajustes.Colores.AMARILLO.getTexto()) + s2 + Ajustes.Colores.RESET;
    }

    @Override
    public String toString() {
        return sb.toString();
    }
}
