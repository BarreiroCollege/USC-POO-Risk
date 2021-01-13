package gal.sdc.usc.risk.gui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbarLayout;
import gal.sdc.usc.risk.excepciones.ExcepcionRISK;
import gal.sdc.usc.risk.gui.componentes.Utils;
import gal.sdc.usc.risk.gui.componentes.controles.ControlesController;
import gal.sdc.usc.risk.gui.componentes.mapa.MapaController;
import gal.sdc.usc.risk.gui.componentes.modal.Dialogo;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.FloatBinding;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class PrincipalController extends Application {
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

    private static JFXSnackbar snackbar;
    private static Scene scene;

    public PrincipalController() {
    }

    public static void crear() {
        launch();
    }

    public static void mensaje(String mensaje) {
        mensaje(mensaje, null);
    }

    public static void mensaje(String mensaje, Integer duracion) {
        mensaje(new JFXSnackbarLayout(mensaje), duracion);
    }

    private static void mensaje(JFXSnackbarLayout layout, Integer duracion) {
        JFXSnackbarLayout finalLayout = new JFXSnackbarLayout(layout.getToast(), "Cerrar", e -> snackbar.close());
        if (duracion != null) {
            snackbar.enqueue(new JFXSnackbar.SnackbarEvent(finalLayout, Duration.seconds(duracion)));
        } else {
            snackbar.enqueue(new JFXSnackbar.SnackbarEvent(finalLayout));
        }
    }

    public static Scene getScene() {
        return scene;
    }

    @FXML
    public void initialize() {
        final int pequenoInt = 900;
        BooleanBinding pequeno = contenedor.widthProperty().lessThan(pequenoInt);

        contenedor.widthProperty().addListener(((observable, oldValue, newValue) -> {
            FXMLLoader loader = new FXMLLoader();

            try {
                if (newValue.doubleValue() >= pequenoInt && oldValue.doubleValue() < pequenoInt) {
                    controlesVertical.setVisible(true);
                    controlesHorizontal.setVisible(false);
                    loader.load(ControlesController.class.getResource("vertical.fxml").openStream());
                    ((ControlesController) loader.getController()).actualizarComandos(contenedor.getScene());
                    ((ControlesController) loader.getController()).actualizarJugador(contenedor.getScene());
                } else if (newValue.doubleValue() < pequenoInt && oldValue.doubleValue() >= pequenoInt) {
                    controlesVertical.setVisible(false);
                    controlesHorizontal.setVisible(true);
                    loader.load(ControlesController.class.getResource("horizontal.fxml").openStream());
                    ((ControlesController) loader.getController()).actualizarComandos(contenedor.getScene());
                    ((ControlesController) loader.getController()).actualizarJugador(contenedor.getScene());
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

    private void tipoMapa() {
        Dialogo dialogo = new Dialogo()
                .setHeading("Bienvenido")
                .setCloseText("Mapa Clásico");

        Label label = new Label("Para empezar, puedes escoger si quieres crear un mapa personalozado o usar el " +
                "clásico ya predefinido.");
        dialogo.setContent(label);

        JFXButton button = new JFXButton("Personalizar");
        button.setOnAction(e -> {
            /* MapaController.cambiarCreando();
            Utils.actualizar();
            PrincipalController.mensaje("Selecciona una casilla para asignar un país"); */
            PrincipalController.mensaje("Todavía en progreso... Pulsa el botón de arriba a la derecha.");
            dialogo.close();
        });

        dialogo.setExtra(button);
        dialogo.show();
    }

    @Override
    public void start(Stage stage) throws IOException {
        Thread.currentThread().setUncaughtExceptionHandler((thread, throwable) -> {
            if (throwable instanceof ExcepcionRISK) {
                ExcepcionRISK e = (ExcepcionRISK) throwable;
                mensaje("[" + e.getCodigo() + "] " + e.getMensaje());
            } else {
                throwable.printStackTrace();
                mensaje("[ERROR] " + throwable.getLocalizedMessage());
            }
        });

        Parent root = FXMLLoader.load(getClass().getResource("principal.fxml"));
        PrincipalController.scene = new Scene(root, 1001, 720);

        snackbar = new JFXSnackbar((StackPane) root);
        Dialogo.setContainer((StackPane) root);

        stage.setMaximized(true);
        stage.setMinWidth(1001);
        stage.setMinHeight(720);
        stage.setTitle("RISK");
        stage.setScene(PrincipalController.scene);
        stage.show();

        this.tipoMapa();
    }
}
