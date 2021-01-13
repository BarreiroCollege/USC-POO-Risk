package gal.sdc.usc.risk.gui.componentes.nuevo;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RegexValidator;
import gal.sdc.usc.risk.comandos.Ejecutor;
import gal.sdc.usc.risk.comandos.EjecutorListener;
import gal.sdc.usc.risk.excepciones.ExcepcionRISK;
import gal.sdc.usc.risk.gui.PrincipalController;
import gal.sdc.usc.risk.gui.componentes.Utils;
import gal.sdc.usc.risk.gui.componentes.mapa.MapaController;
import gal.sdc.usc.risk.gui.componentes.modal.Dialogo;
import gal.sdc.usc.risk.jugar.Partida;
import gal.sdc.usc.risk.tablero.Pais;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class NuevoRearme extends Partida {
    public static void generarDialogo() {
        new NuevoRearme().generar();
    }

    private NuevoRearme() {
    }

    private void generar() {
        Dialogo dialogo = new Dialogo()
                .setHeading("Rearmar Paises")
                .setCloseText("Cancelar");

        VBox contenedor = new VBox();

        Pais pais1 = MapaController.getPaisesSeleccionados().get(0);
        Pais pais2 = MapaController.getPaisesSeleccionados().get(1);

        HBox fila1 = new HBox();
        Label labelPais1 = new Label("Pais donante");
        labelPais1.getStyleClass().add("dialogo-titulo");
        fila1.getChildren().add(labelPais1);
        fila1.getChildren().add(Utils.labelColor(pais1.getNombre(), pais1.getColor().getHex()));
        contenedor.getChildren().add(fila1);
        contenedor.getChildren().add(Utils.separadorEntrada());

        HBox fila2 = new HBox();
        Label labelPais2 = new Label("Pais receptor");
        labelPais2.getStyleClass().add("dialogo-titulo");
        fila2.getChildren().add(labelPais2);
        fila2.getChildren().add(Utils.labelColor(pais2.getNombre(), pais2.getColor().getHex()));
        contenedor.getChildren().add(fila2);
        contenedor.getChildren().add(Utils.separadorEntrada());

        JFXTextField numEjercitos = new JFXTextField();
        numEjercitos.setLabelFloat(true);
        numEjercitos.setStyle(numEjercitos.getStyle() + "-fx-padding: 20pt 0 5pt 0;");
        numEjercitos.setPromptText("Número de ejércitos");
        RegexValidator validadorRegex = new RegexValidator();
        validadorRegex.setRegexPattern("([0-9]+)");
        validadorRegex.setMessage("Introduce un número positivo");
        numEjercitos.getValidators().add(validadorRegex);
        numEjercitos.textProperty().addListener((o) -> numEjercitos.validate());
        numEjercitos.validate();
        contenedor.getChildren().add(numEjercitos);

        VBox errorContenedor = new VBox();
        errorContenedor.setStyle(errorContenedor.getStyle() + "-fx-padding: 15pt 0 10pt 0;");
        errorContenedor.setPrefWidth(Float.MAX_VALUE);
        errorContenedor.setVisible(false);
        errorContenedor.setManaged(false);

        HBox error = new HBox();
        error.setPrefWidth(Float.MAX_VALUE);
        error.setStyle(error.getStyle() + "-fx-padding: 10pt 5pt 10pt 5pt; "
                + "-fx-border-width: 1pt; "
                + "-fx-border-radius: 5pt;"
                + "-fx-border-color: #d32f2f;");
        HBox.setHgrow(error, Priority.ALWAYS);
        Label errorTitulo = new Label("Error");
        errorTitulo.getStyleClass().add("dialogo-titulo");
        errorTitulo.getStyleClass().add("error");
        error.getChildren().add(errorTitulo);
        Label errorValor = new Label();
        HBox.setHgrow(errorValor, Priority.ALWAYS);
        errorValor.getStyleClass().add("dialogo-valor");
        error.getChildren().add(errorValor);
        errorContenedor.getChildren().add(error);
        contenedor.getChildren().add(errorContenedor);

        dialogo.setContent(contenedor);

        JFXButton ejecutar = new JFXButton("Rearmar");
        ejecutar.disableProperty().bind(numEjercitos.getValidators().get(0).hasErrorsProperty());
        ejecutar.setOnAction(event -> {
            errorContenedor.setVisible(false);
            errorContenedor.setManaged(false);

            int ejercitos = Integer.parseInt(numEjercitos.getText());
            if ((pais1.getEjercito().toInt() - ejercitos) < 1) {
                ejercitos = pais1.getEjercito().toInt() - 1;
            }
            int finalEjercitos = ejercitos;

            Ejecutor.comando("rearmar " + pais1.getAbreviatura() + " " + numEjercitos.getText() + " " + pais2.getAbreviatura(),
                    new EjecutorListener() {
                        @Override
                        public void onComandoError(ExcepcionRISK e) {
                            errorTitulo.setText("Error " + e.getCodigo());
                            errorValor.setText(e.getMensaje());
                            errorContenedor.setVisible(true);
                            errorContenedor.setManaged(true);
                        }

                        @Override
                        public void onComandoEjecutado() {
                            MapaController.cambiarRearmar();
                            dialogo.close();
                            PrincipalController.mensaje("Se han asignado " + finalEjercitos + " a " + pais2.getNombre());
                        }
                    });
        });

        dialogo.setExtra(ejecutar);
        dialogo.show();
    }
}
