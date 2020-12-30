package gal.sdc.usc.risk.gui.componentes.nuevo;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
import gal.sdc.usc.risk.tablero.Jugador;
import gal.sdc.usc.risk.util.Colores;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.HashMap;

public class NuevoJugador extends Partida {
    private final StackPane parent;

    public static void generarDialogo(StackPane parent) {
        new NuevoJugador(parent).generar();
    }

    private NuevoJugador(StackPane parent) {
        this.parent = parent;
    }

    private void generar() {
        JFXDialog dialog = new JFXDialog();
        dialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
        dialog.setDialogContainer(parent);

        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Label("Nuevo Jugador"));

        VBox contenedor = new VBox();

        JFXTextField jugadorNombre = new JFXTextField();
        jugadorNombre.setLabelFloat(true);
        jugadorNombre.setPromptText("Nombre del jugador");
        RequiredFieldValidator validadorRequerido = new RequiredFieldValidator();
        validadorRequerido.setMessage("Escribe un nombre de jugador");
        jugadorNombre.getValidators().add(validadorRequerido);
        RegexValidator validadorRegex = new RegexValidator();
        validadorRegex.setRegexPattern("([a-zA-Z0-9á-úÁ-Ú]+)");
        validadorRegex.setMessage("Sólo se permiten caracteres alfanuméricos");
        jugadorNombre.getValidators().add(validadorRegex);
        jugadorNombre.textProperty().addListener((o) -> jugadorNombre.validate());
        jugadorNombre.validate();
        contenedor.getChildren().add(jugadorNombre);

        JFXComboBox<Label> comboColores = new JFXComboBox<>();
        comboColores.setStyle(comboColores.getStyle() + "-fx-padding: 20pt 0 5pt 0;");
        comboColores.setPrefWidth(Float.MAX_VALUE);
        HashMap<Colores.Color, Jugador> jugadoresColores = super.getJugadoresPorColor();
        for (Colores.Color color : Colores.Color.values()) {
            if (color.equals(Colores.Color.NEGRO) || color.equals(Colores.Color.BLANCO)) continue;
            if (!jugadoresColores.containsKey(color)) {
                Label label = Utils.labelColor(color.toString(), color.getHex());
                label.setId(color.toString());
                comboColores.getItems().add(label);
            }
        }
        comboColores.setPromptText("Color del jugador");
        contenedor.getChildren().add(comboColores);

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

        JFXButton ejecutar = new JFXButton("Crear");
        ejecutar.disableProperty().bind(jugadorNombre.getValidators().get(0).hasErrorsProperty()
                .or(jugadorNombre.getValidators().get(1).hasErrorsProperty()
                        .or(comboColores.getSelectionModel().selectedItemProperty().isNull())));
        ejecutar.setOnAction(event -> {
            errorContenedor.setVisible(false);
            errorContenedor.setManaged(false);
            Ejecutor.comando(
                    "crear " + jugadorNombre.getText() + " " + comboColores.getSelectionModel().getSelectedItem().getId(),
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
                            PrincipalController.mensaje("Creado el jugador "
                                    + jugadorNombre.getText() + " con color "
                                    + comboColores.getSelectionModel().getSelectedItem().getId());
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
