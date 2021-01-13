package gal.sdc.usc.risk.gui.componentes.nuevo;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import gal.sdc.usc.risk.comandos.Ejecutor;
import gal.sdc.usc.risk.comandos.EjecutorListener;
import gal.sdc.usc.risk.excepciones.ExcepcionRISK;
import gal.sdc.usc.risk.gui.PrincipalController;
import gal.sdc.usc.risk.gui.componentes.mapa.MapaController;
import gal.sdc.usc.risk.gui.componentes.modal.Dialogo;
import gal.sdc.usc.risk.jugar.Partida;
import gal.sdc.usc.risk.tablero.Carta;
import gal.sdc.usc.risk.tablero.Continente;
import gal.sdc.usc.risk.tablero.Pais;
import gal.sdc.usc.risk.tablero.valores.SubEquipamientos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// TODO
public class NuevaFrontera extends Partida {
    public static void generarDialogo() {
        new NuevaFrontera().generar();
    }

    private NuevaFrontera() {
    }

    private List<Label> continentesDisponibles() {
        int numPaises4 = 0, numPaises6 = 0, numPaises7 = 0, numPaises9 = 0, numPaises12 = 0;
        for (Continente.Builder continente : MapaController.getPreContinentes().values()) {
            if (continente.totalPaises() >= 12) numPaises12++;
            else if (continente.totalPaises() >= 9) numPaises9++;
            else if (continente.totalPaises() >= 7) numPaises7++;
            else if (continente.totalPaises() >= 6) numPaises6++;
            else if (continente.totalPaises() >= 4) numPaises4++;
        }

        return null;
    }

    private List<Label> subequipamientosDisponibles(Label paisLabel) {
        Pais pais = null;
        if (paisLabel != null) {
            pais = super.getMapa().getPaisPorNombre(paisLabel.getText().trim());
        }
        HashMap<String, SubEquipamientos> subequipamientos = new HashMap<>();
        for (Carta carta : super.getCartasMonton()) {
            if (pais != null && !carta.getPais().equals(pais)) continue;
            subequipamientos.put(carta.getEquipamiento().getNombre() + " / " + carta.getSubEquipamiento().getNombre(), carta.getSubEquipamiento());
        }

        List<Label> labels = new ArrayList<>();
        for (Map.Entry<String, SubEquipamientos> e : subequipamientos.entrySet()) {
            Label label = new Label(e.getKey());
            label.setId(e.getValue().getNombre());
            labels.add(label);
        }

        return labels;
    }

    private void generar() {
        Dialogo dialogo = new Dialogo()
                .setHeading("Seleccionar Carta")
                .setCloseText("Cancelar");

        VBox contenedor = new VBox();

        JFXComboBox<Label> comboPaises = new JFXComboBox<>();
        JFXComboBox<Label> comboSubequipamientos = new JFXComboBox<>();
        comboSubequipamientos.setStyle(comboSubequipamientos.getStyle() + "-fx-padding: 20pt 0 5pt 0;");
        comboSubequipamientos.disableProperty().bind(comboPaises.getSelectionModel().selectedItemProperty().isNull());

        comboPaises.setPrefWidth(Float.MAX_VALUE);
        comboPaises.setPromptText("Pais");
        contenedor.getChildren().add(comboPaises);

        comboSubequipamientos.setPrefWidth(Float.MAX_VALUE);
        comboSubequipamientos.getItems().addAll(subequipamientosDisponibles(null));
        comboSubequipamientos.setPromptText("Equipamiento");
        contenedor.getChildren().add(comboSubequipamientos);

        comboPaises.getSelectionModel().selectedItemProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal.equals(oldVal)) {
                comboSubequipamientos.getItems().clear();
                comboSubequipamientos.getItems().addAll(subequipamientosDisponibles(newVal));
            }
        });

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

        JFXButton ejecutar = new JFXButton("Asignar");
        ejecutar.setDisable(true);
        ejecutar.disableProperty().bind(comboPaises.getSelectionModel().selectedItemProperty().isNull()
                .or(comboSubequipamientos.getSelectionModel().selectedItemProperty().isNull()));
        ejecutar.setOnAction(event -> {
            errorContenedor.setVisible(false);
            errorContenedor.setManaged(false);
            Ejecutor.comando(
                    "asignar carta "
                            + comboSubequipamientos.getSelectionModel().getSelectedItem().getId() + "&"
                            + comboPaises.getSelectionModel().getSelectedItem().getId(),
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
                            PrincipalController.mensaje("La carta "
                                    + comboPaises.getSelectionModel().getSelectedItem().getId() + "&"
                                    + comboSubequipamientos.getSelectionModel().getSelectedItem().getId()
                                    + " ha sido asignada");
                        }
                    });
        });

        dialogo.setExtra(ejecutar);
        dialogo.show();
    }
}
