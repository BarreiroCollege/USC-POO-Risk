package gal.sdc.usc.risk.gui.componentes.nuevo;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.validation.RequiredFieldValidator;
import gal.sdc.usc.risk.comandos.Ejecutor;
import gal.sdc.usc.risk.comandos.EjecutorListener;
import gal.sdc.usc.risk.excepciones.ExcepcionRISK;
import gal.sdc.usc.risk.gui.PrincipalController;
import gal.sdc.usc.risk.gui.componentes.Utils;
import gal.sdc.usc.risk.jugar.Partida;
import gal.sdc.usc.risk.tablero.Jugador;
import gal.sdc.usc.risk.tablero.valores.Misiones;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.HashMap;

public class NuevaMisionAsignada extends Partida {
    private final StackPane parent;

    public static void generarDialogo(StackPane parent) {
        new NuevaMisionAsignada(parent).generar();
    }

    private NuevaMisionAsignada(StackPane parent) {
        this.parent = parent;
    }

    private void generar() {
        JFXDialog dialog = new JFXDialog();
        dialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
        dialog.setDialogContainer(parent);

        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Label("Asignar Misión"));

        VBox contenedor = new VBox();

        JFXComboBox<Label> comboJugadores = new JFXComboBox<>();
        comboJugadores.setPrefWidth(Float.MAX_VALUE);
        for (Jugador jugador : super.getJugadores().values()) {
            if (jugador.getMision() == null) {
                Label label = Utils.labelColor(jugador.getNombre(), jugador.getColor().getHex());
                label.setId(jugador.getNombre());
                comboJugadores.getItems().add(label);
            }
        }
        RequiredFieldValidator validadorRequerido1 = new RequiredFieldValidator();
        validadorRequerido1.setMessage("Selecciona el jugador");
        comboJugadores.getValidators().add(validadorRequerido1);
        comboJugadores.setPromptText("Jugador");
        comboJugadores.selectionModelProperty().addListener((o) -> comboJugadores.validate());
        comboJugadores.validate();
        contenedor.getChildren().add(comboJugadores);

        JFXComboBox<Label> comboMisiones = new JFXComboBox<>();
        comboMisiones.setStyle(comboMisiones.getStyle() + "-fx-padding: 20pt 0 5pt 0;");
        comboMisiones.setPrefWidth(Float.MAX_VALUE);
        HashMap<Misiones, Jugador> jugadoresColores = super.getJugadoresPorMision();
        for (Misiones mision : Misiones.values()) {
            if (!jugadoresColores.containsKey(mision)) {
                Label label = new Label(mision.getNombre());
                label.setId(mision.getId());
                comboMisiones.getItems().add(label);
            }
        }
        RequiredFieldValidator validadorRequerido2 = new RequiredFieldValidator();
        validadorRequerido2.setMessage("Selecciona la misión");
        comboMisiones.getValidators().add(validadorRequerido2);
        comboMisiones.setPromptText("Misión del jugador");
        comboMisiones.selectionModelProperty().addListener((o) -> comboMisiones.validate());
        comboMisiones.validate();
        contenedor.getChildren().add(comboMisiones);

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
        ejecutar.disableProperty().bind(comboJugadores.getValidators().get(0).hasErrorsProperty()
                .or(comboMisiones.getValidators().get(0).hasErrorsProperty()));
        ejecutar.setOnAction(event -> {
            errorContenedor.setVisible(false);
            errorContenedor.setManaged(false);
            Ejecutor.comando(
                    "asignar mision "
                            + comboJugadores.getSelectionModel().getSelectedItem().getId() + " "
                            + comboMisiones.getSelectionModel().getSelectedItem().getId(),
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
                            Utils.actualizar(contenedor.getScene());
                            dialog.close();

                            PrincipalController.mensaje("Misión "
                                    + comboMisiones.getSelectionModel().getSelectedItem().getId() + " asignada a "
                                    + comboJugadores.getSelectionModel().getSelectedItem().getId());
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
