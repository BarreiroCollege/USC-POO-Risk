package gal.sdc.usc.risk.gui.mapa;

import com.jfoenix.controls.JFXButton;
import gal.sdc.usc.risk.comandos.Ejecutor;
import gal.sdc.usc.risk.jugar.Partida;
import gal.sdc.usc.risk.tablero.Celda;
import gal.sdc.usc.risk.tablero.Pais;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.HashMap;

import static gal.sdc.usc.risk.tablero.Mapa.MAX_PAISES_X;
import static gal.sdc.usc.risk.tablero.Mapa.MAX_PAISES_Y;

public class MapaController extends Partida {
    @FXML
    private VBox contenedor;

    public MapaController() {
    }

    @FXML
    private void initialize() {
        Ejecutor.comando("crear mapa", () -> {
            HashMap<Celda, Pais> paises = super.getMapa().getPaisesPorCeldas();
            for (int i = 0; i < MAX_PAISES_Y; i++) {
                HBox hbox = new HBox();
                for (int j = 0; j < MAX_PAISES_X; j++) {
                    Celda celda = new Celda.Builder().withX(j).withY(i).build();
                    JFXButton button = new JFXButton();
                    button.setDisable(true);
                    button.setPrefWidth((float) contenedor.getWidth() / MAX_PAISES_X);
                    if (paises.containsKey(celda)) {
                        button.setDisable(false);
                        button.setText(paises.get(celda).getAbreviatura());
                    }
                    HBox.setHgrow(button, Priority.ALWAYS);
                    hbox.getChildren().add(button);
                }
                contenedor.getChildren().add(hbox);
            }
        });
    }
}
