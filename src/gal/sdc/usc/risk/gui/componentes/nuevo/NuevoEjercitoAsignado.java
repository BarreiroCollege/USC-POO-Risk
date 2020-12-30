package gal.sdc.usc.risk.gui.componentes.nuevo;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import gal.sdc.usc.risk.comandos.Ejecutor;
import gal.sdc.usc.risk.comandos.EjecutorListener;
import gal.sdc.usc.risk.excepciones.ExcepcionRISK;
import gal.sdc.usc.risk.gui.PrincipalController;
import gal.sdc.usc.risk.gui.componentes.Utils;
import gal.sdc.usc.risk.jugar.Partida;
import gal.sdc.usc.risk.tablero.Pais;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class NuevoEjercitoAsignado extends Partida {
    private final StackPane parent;
    private final Pais pais;

    public static void generarDialogo(StackPane parent, Pais pais) {
        new NuevoEjercitoAsignado(parent, pais).generar();
    }

    private NuevoEjercitoAsignado(StackPane parent, Pais pais) {
        this.parent = parent;
        this.pais = pais;
    }

    private void generar() {
        JFXDialog dialog = new JFXDialog();
        dialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
        dialog.setDialogContainer(parent);

        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Label("Repartir Ejército"));

        VBox contenedor = new VBox();

        HBox fila = new HBox();
        Label labelPais = new Label("Pais");
        labelPais.getStyleClass().add("dialogo-titulo");
        fila.getChildren().add(labelPais);
        VBox valores = new VBox();
        valores.getChildren().add(Utils.labelColor(pais.getNombre(), pais.getColor().getHex()));
        fila.getChildren().add(valores);
        if (valores.getChildren().size() > 0) {
            contenedor.getChildren().add(fila);
            contenedor.getChildren().add(Utils.separadorEntrada());
        }

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

        layout.setBody(contenedor);

        JFXButton ejecutar = new JFXButton("Asignar");
        ejecutar.setDisable(true);
        ejecutar.disableProperty().bind(numEjercitos.getValidators().get(0).hasErrorsProperty());
        ejecutar.setOnAction(event -> {
            errorContenedor.setVisible(false);
            errorContenedor.setManaged(false);

            int ejercitos = Integer.parseInt(numEjercitos.getText());
            if (ejercitos > super.getJugadorTurno().getEjercitosPendientes().toInt()) {
                ejercitos = super.getJugadorTurno().getEjercitosPendientes().toInt();
            }
            int finalEjercitos = ejercitos;

            Ejecutor.comando(
                    "repartir ejercito " + numEjercitos.getText() + " " + pais.getAbreviatura(),
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
                            dialog.close();
                            PrincipalController.mensaje("Repartidos " + finalEjercitos + " ejército"
                                    + (finalEjercitos == 1 ? "" : "s") + " a "
                                    + pais.getNombre());
                        }
                    });
        });

        JFXButton cerrar = new JFXButton("Cancelar");
        cerrar.setOnAction(event -> dialog.close());

        layout.setActions(cerrar, ejecutar);

        dialog.setContent(layout);
        dialog.show();
    }
}
