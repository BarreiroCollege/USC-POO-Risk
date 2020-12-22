package gal.sdc.usc.risk.gui;

import gal.sdc.usc.risk.gui.componentes.controles.ControlesController;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.FloatBinding;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class VistaPrincipal extends Application {
    @FXML
    public StackPane stackPane;
    @FXML
    public VBox contenedor;
    @FXML
    public HBox contenedorVertical;
    @FXML
    public VBox mapa;
    @FXML
    public VBox controlesVertical;
    @FXML
    public VBox controlesHorizontal;

    public VistaPrincipal() {
    }

    public static void crear() {
        launch();
    }

    @FXML
    public void initialize() {
        BooleanBinding pequeno = contenedor.widthProperty().lessThan(900);

        contenedor.widthProperty().addListener(((observable, oldValue, newValue) -> {
            FXMLLoader loader = new FXMLLoader();

            try {
                if (newValue.doubleValue() >= 900 && oldValue.doubleValue() < 900) {
                    controlesVertical.setVisible(true);
                    controlesHorizontal.setVisible(false);
                    loader.load(ControlesController.class.getResource("vertical.fxml").openStream());
                    ((ControlesController) loader.getController()).actualizar();
                } else if (newValue.doubleValue() < 900 && oldValue.doubleValue() >= 900) {
                    controlesVertical.setVisible(false);
                    controlesHorizontal.setVisible(true);
                    loader.load(ControlesController.class.getResource("horizontal.fxml").openStream());
                    ((ControlesController) loader.getController()).actualizar();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));

        // Contenedor vertical con mapa y controles en vertical

        FloatBinding contenedorVerticalHeight = Bindings.createFloatBinding(() -> {
            if (pequeno.get()) {
                return contenedor.heightProperty().multiply(70).divide(100).floatValue();
            }
            return contenedor.heightProperty().multiply(100).divide(100).floatValue();
        }, contenedor.heightProperty(), pequeno);
        contenedorVertical.prefHeightProperty().bind(contenedorVerticalHeight);

        // Mapa

        FloatBinding mapaWidth = Bindings.createFloatBinding(() -> {
            if (pequeno.get()) {
                return contenedor.widthProperty().multiply(100).divide(100).floatValue();
            }
            return contenedor.widthProperty().multiply(80).divide(100).floatValue();
        }, contenedor.widthProperty(), pequeno);
        mapa.prefWidthProperty().bind(mapaWidth);

        // Controles en vertical

        FloatBinding controlesVerticalWidth = Bindings.createFloatBinding(() -> {
            if (pequeno.get()) {
                return contenedor.widthProperty().multiply(0).divide(100).floatValue();
            }
            return contenedor.widthProperty().multiply(20).divide(100).floatValue();
        }, contenedor.widthProperty(), pequeno);
        controlesVertical.prefWidthProperty().bind(controlesVerticalWidth);

        // Controles en horizontal

        FloatBinding controlesHorizontalHeight = Bindings.createFloatBinding(() -> {
            if (pequeno.get()) {
                return contenedor.heightProperty().multiply(30).divide(100).floatValue();
            }
            return contenedor.heightProperty().multiply(0).divide(100).floatValue();
        }, contenedor.heightProperty(), pequeno);
        controlesHorizontal.prefHeightProperty().bind(controlesHorizontalHeight);
    }

    @Override
    public void start(Stage stage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("principal.fxml"));
        Scene scene = new Scene(root, 720, 480);

        stage.setMaximized(true);
        stage.setMinWidth(720);
        stage.setMinHeight(360);
        stage.setTitle("RISK");
        stage.setScene(scene);
        stage.show();
    }
}
