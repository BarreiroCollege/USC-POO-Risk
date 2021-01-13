package gal.sdc.usc.risk.gui.componentes.nuevo;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import gal.sdc.usc.risk.comandos.Ejecutor;
import gal.sdc.usc.risk.comandos.EjecutorListener;
import gal.sdc.usc.risk.comandos.partida.CambiarCartas;
import gal.sdc.usc.risk.excepciones.ExcepcionRISK;
import gal.sdc.usc.risk.gui.PrincipalController;
import gal.sdc.usc.risk.gui.componentes.modal.Dialogo;
import gal.sdc.usc.risk.jugar.Partida;
import gal.sdc.usc.risk.tablero.Carta;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.List;

public class NuevoCambioCartas extends Partida {
    public static void generarDialogo() {
        new NuevoCambioCartas().generar();
    }

    private NuevoCambioCartas() {
    }

    private void generar() {
        Dialogo dialogo = new Dialogo()
                .setHeading("Cambiar Cartas a Ejércitos")
                .setCloseText("Cancelar");

        VBox contenedor = new VBox();

        List<Carta> cartas = super.getJugadorTurno().getCartas();

        JFXComboBox<Label> comboCarta1 = new JFXComboBox<>();
        JFXComboBox<Label> comboCarta2 = new JFXComboBox<>();
        JFXComboBox<Label> comboCarta3 = new JFXComboBox<>();
        JFXButton ejecutar = new JFXButton("Cambiar");

        comboCarta1.setPrefWidth(Float.MAX_VALUE);
        for (Carta carta : cartas) {
            comboCarta1.getItems().add(new Label(carta.getNombre()));
        }
        comboCarta1.setPromptText("Carta 1");
        comboCarta1.getSelectionModel().selectedItemProperty().addListener((o, oldValue, newValue) -> {
            comboCarta2.getItems().clear();
            comboCarta3.getItems().clear();

            for (Carta carta : cartas) {
                if (!comboCarta1.getSelectionModel().getSelectedItem().getText().equals(carta.getNombre())) {
                    comboCarta2.getItems().add(new Label(carta.getNombre()));
                }
            }
        });
        contenedor.getChildren().add(comboCarta1);

        comboCarta2.disableProperty().bind(comboCarta1.getSelectionModel().selectedItemProperty().isNull());
        comboCarta2.setStyle(comboCarta2.getStyle() + "-fx-padding: 20pt 0 5pt 0;");
        comboCarta2.setPrefWidth(Float.MAX_VALUE);
        comboCarta2.setPromptText("Carta 2");
        comboCarta2.getSelectionModel().selectedItemProperty().addListener((o, oldValue, newValue) -> {
            comboCarta3.getItems().clear();

            for (Carta carta : cartas) {
                if (!comboCarta1.getSelectionModel().getSelectedItem().getText().equals(carta.getNombre())
                        && !comboCarta2.getSelectionModel().getSelectedItem().getText().equals(carta.getNombre())) {
                    comboCarta3.getItems().add(new Label(carta.getNombre()));
                }
            }
        });
        contenedor.getChildren().add(comboCarta2);

        comboCarta3.disableProperty().bind(comboCarta1.getSelectionModel().selectedItemProperty().isNull());
        comboCarta3.setStyle(comboCarta3.getStyle() + "-fx-padding: 20pt 0 5pt 0;");
        comboCarta3.setPrefWidth(Float.MAX_VALUE);
        comboCarta3.setPromptText("Carta 3");
        comboCarta3.getSelectionModel().selectedItemProperty().addListener((o, oldValue, newValue) -> {
            if (newValue == null) {
                ejecutar.setText("Cambiar");
            } else {
                List<String> cartasCambio = Arrays.asList(
                        comboCarta1.getSelectionModel().getSelectedItem().getText(),
                        comboCarta2.getSelectionModel().getSelectedItem().getText(),
                        comboCarta3.getSelectionModel().getSelectedItem().getText()
                );
                int ejercitos = CambiarCartas.calcularCambiosString(cartasCambio, super.getJugadorTurno(), super.getMapa());
                ejecutar.setText("Cambiar x" + ejercitos);
            }
        });
        contenedor.getChildren().add(comboCarta3);

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

        ejecutar.disableProperty().bind(comboCarta3.getSelectionModel().selectedItemProperty().isNull());
        ejecutar.setOnAction(event -> {
            errorContenedor.setVisible(false);
            errorContenedor.setManaged(false);

            Ejecutor.comando("cambiar cartas " + comboCarta1.getSelectionModel().getSelectedItem().getText()
                            + " " + comboCarta2.getSelectionModel().getSelectedItem().getText()
                            + " " + comboCarta3.getSelectionModel().getSelectedItem().getText(),
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
                            dialogo.close();
                            PrincipalController.mensaje("Se han cambiado las cartas, ya puedes repartir los ejércitos");
                        }
                    });
        });

        dialogo.setExtra(ejecutar);
        dialogo.show();
    }
}
