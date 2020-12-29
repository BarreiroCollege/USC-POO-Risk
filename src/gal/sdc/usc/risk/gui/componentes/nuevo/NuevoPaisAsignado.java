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
import gal.sdc.usc.risk.gui.componentes.mapa.MapaController;
import gal.sdc.usc.risk.jugar.Partida;
import gal.sdc.usc.risk.tablero.Jugador;
import gal.sdc.usc.risk.tablero.Pais;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class NuevoPaisAsignado extends Partida {
    private final StackPane parent;

    public static void generarDialogo(StackPane parent) {
        new NuevoPaisAsignado(parent).generar();
    }

    private NuevoPaisAsignado(StackPane parent) {
        this.parent = parent;
    }

    private void generar() {
        JFXDialog dialog = new JFXDialog();
        dialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
        dialog.setDialogContainer(parent);

        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Label("Asignar Paises"));

        VBox contenedor = new VBox();

        HBox fila = new HBox();
        Label labelPais = new Label("Países");
        labelPais.getStyleClass().add("dialogo-titulo");
        fila.getChildren().add(labelPais);
        VBox valores = new VBox();
        for (Pais pais : MapaController.getPaisesSeleccionados()) {
            valores.getChildren().add(Utils.labelColor(pais.getNombre(), pais.getColor().getHex()));
        }
        fila.getChildren().add(valores);
        if (valores.getChildren().size() > 0) {
            contenedor.getChildren().add(fila);
            contenedor.getChildren().add(Utils.separadorEntrada());
        }

        JFXComboBox<Label> comboJugadores = new JFXComboBox<>();
        comboJugadores.setStyle(comboJugadores.getStyle() + "-fx-padding: 20pt 0 5pt 0;");
        comboJugadores.setPrefWidth(Float.MAX_VALUE);
        for (Jugador jugador : super.getJugadores().values()) {
            Label label = Utils.labelColor(jugador.getNombre(), jugador.getColor().getHex());
            label.setId(jugador.getNombre());
            comboJugadores.getItems().add(label);
        }
        RequiredFieldValidator validadorRequerido1 = new RequiredFieldValidator();
        validadorRequerido1.setMessage("Selecciona el jugador");
        comboJugadores.getValidators().add(validadorRequerido1);
        comboJugadores.setPromptText("Jugador");
        comboJugadores.selectionModelProperty().addListener((o) -> comboJugadores.validate());
        comboJugadores.validate();
        contenedor.getChildren().add(comboJugadores);

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
        if (MapaController.getPaisesSeleccionados().size() > 0) {
            ejecutar.disableProperty().bind(comboJugadores.getValidators().get(0).hasErrorsProperty());
        }
        ejecutar.setOnAction(event -> {
            errorContenedor.setVisible(false);
            errorContenedor.setManaged(false);
            for (Pais pais : MapaController.getPaisesSeleccionados()) {
                Ejecutor.comando(
                        "asignar pais "
                                + comboJugadores.getSelectionModel().getSelectedItem().getId() + " "
                                + pais.getAbreviatura(),
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
                                MapaController.getPaisesSeleccionados().remove(pais);
                                if (MapaController.getPaisesSeleccionados().size() == 0) {
                                    Utils.actualizar(contenedor.getScene());
                                    dialog.close();

                                    PrincipalController.mensaje("Paises asignados a "
                                            + comboJugadores.getSelectionModel().getSelectedItem().getId());
                                }
                            }
                        });
            }
        });

        if (MapaController.getPaisesSeleccionados().size() == 0) {
            contenedor.getChildren().clear();
            contenedor.getChildren().add(errorContenedor);

            errorTitulo.setText("Error");
            errorValor.setText("Selecciona en el mapa los países a asignar");
            errorContenedor.setVisible(true);
            errorContenedor.setManaged(true);
        }

        JFXButton cerrar = new JFXButton("Cancelar");
        cerrar.setOnAction(event -> dialog.close());

        layout.setActions(cerrar, ejecutar);

        dialog.setContent(layout);
        dialog.show();
    }
}
