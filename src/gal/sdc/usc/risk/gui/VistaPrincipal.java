package gal.sdc.usc.risk.gui;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.FloatBinding;
import javafx.beans.property.SimpleBooleanProperty;
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

        SimpleBooleanProperty verticalVisible = new SimpleBooleanProperty();
        verticalVisible.isEqualTo(pequeno.not());
        controlesVertical.visibleProperty().unbind();
        controlesVertical.visibleProperty().bind(verticalVisible);

        // Controles en horizontal

        FloatBinding controlesHorizontalHeight = Bindings.createFloatBinding(() -> {
            if (pequeno.get()) {
                return contenedor.heightProperty().multiply(30).divide(100).floatValue();
            }
            return contenedor.heightProperty().multiply(0).divide(100).floatValue();
        }, contenedor.heightProperty(), pequeno);
        controlesVertical.prefHeightProperty().bind(controlesHorizontalHeight);

        SimpleBooleanProperty horizontalVisible = new SimpleBooleanProperty();
        horizontalVisible.isEqualTo(pequeno);
        controlesHorizontal.visibleProperty().unbind();
        controlesHorizontal.visibleProperty().bind(horizontalVisible);
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

        // stage.widthProperty().addListener((obs, oldVal, newVal) -> this.gestionarControles(oldVal, newVal));
    }

    /* public void gestionarControles(Number viejo, Number nuevo) {
        float ROMPER = 1080;

        VBox contenedor = (VBox) scene.lookup("#contenedor");
        HBox contenedorVertical = (HBox) scene.lookup("#contenedorVertical");
        VBox mapa = (VBox) scene.lookup("#mapa");
        VBox controlesVertical = (VBox) scene.lookup("#controlesVertical");
        VBox controlesHorizontal = (VBox) scene.lookup("#controlesHorizontal");

        SimpleFloatProperty mapaWidth = new SimpleFloatProperty();
        SimpleFloatProperty contenedorVerticalHeight = new SimpleFloatProperty();

        if (nuevo.doubleValue() > ROMPER && viejo.doubleValue() < ROMPER) {
            mapaWidth.add(contenedor.widthProperty().multiply(80).divide(100));
            controlesVertical.setVisible(true);

            contenedorVerticalHeight.add(contenedor.heightProperty().multiply(100).divide(100));
            controlesHorizontal.setVisible(false);
        } else if (nuevo.doubleValue() < ROMPER && viejo.doubleValue() > ROMPER) {
            mapaWidth.add(contenedor.widthProperty().multiply(100).divide(100));
            controlesVertical.setVisible(false);

            contenedorVerticalHeight.add(contenedor.heightProperty().multiply(80).divide(100));
            controlesHorizontal.setVisible(true);
        }

        mapa.prefWidthProperty().unbind();
        mapa.prefWidthProperty().bind(mapaWidth);

        contenedorVertical.prefHeightProperty().unbind();
        contenedorVertical.prefHeightProperty().bind(contenedorVerticalHeight);
    } */
}
