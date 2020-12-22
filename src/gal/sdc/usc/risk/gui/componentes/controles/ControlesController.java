package gal.sdc.usc.risk.gui.componentes.controles;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RegexValidator;
import gal.sdc.usc.risk.comandos.Comando;
import gal.sdc.usc.risk.comandos.Ejecutor;
import gal.sdc.usc.risk.comandos.EjecutorListener;
import gal.sdc.usc.risk.comandos.IComando;
import gal.sdc.usc.risk.excepciones.ExcepcionRISK;
import gal.sdc.usc.risk.gui.componentes.mapa.MapaController;
import gal.sdc.usc.risk.jugar.Partida;
import gal.sdc.usc.risk.util.Colores;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.List;

public class ControlesController extends Partida {
    @FXML
    private Pane parent;
    @FXML
    private Pane contenedor;
    @FXML
    private AnchorPane anchor;

    public ControlesController() {
    }

    @FXML
    private void initialize() {
        anchor.getStylesheets().add(ControlesController.class.getResource("controles.css").toExternalForm());
        this.actualizar();
    }

    public void actualizar() {
        contenedor.getChildren().clear();
        for (Class<? extends IComando> comando : super.getComandos().getLista()) {
            JFXButton boton = new JFXButton();
            boton.setText(comando.getName());

            try {
                Object comandoObject;
                comandoObject = comando.newInstance();
                Method ejecutar = comando.getMethod("nombre");
                boton.setText((String) ejecutar.invoke(comandoObject));
            } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }

            contenedor.getChildren().add(boton);
        }
    }

    @FXML
    public void indirectas() {
        FXMLLoader loader = new FXMLLoader();
        try {
            loader.load(MapaController.class.getResource("mapa.fxml").openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        MapaController controller = loader.getController();
        controller.cambiarFronteras();
        controller.actualizar(contenedor.getScene());
    }

    @FXML
    public void comandos() {
        JFXDialog dialog = new JFXDialog();
        dialog.setTransitionType(JFXDialog.DialogTransition.CENTER);

        Parent parent = this.parent;
        while (parent.getParent() != null) parent = parent.getParent();
        assert parent instanceof StackPane;
        dialog.setDialogContainer((StackPane) parent);

        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Label("Introducir comando manualmente"));

        VBox contenedor = new VBox();

        JFXComboBox<Label> comboComando = new JFXComboBox<>();
        comboComando.setPrefWidth(Float.MAX_VALUE);
        List<Class<? extends IComando>> comandos = super.getComandos().getLista();
        comandos.sort(Comparator.comparing(Class::getName));
        for (Class<? extends IComando> comando : comandos) {
            if (comando.getName().toLowerCase().contains("obtener")
                    || comando.getName().toLowerCase().contains("describir")
                    || comando.getName().toLowerCase().contains("ayuda")
                    || comando.getName().toLowerCase().contains("ver")
                    || comando.getName().toLowerCase().contains("salir")) {
                continue;
            }

            try {
                Object comandoObject;
                comandoObject = comando.newInstance();
                Method ejecutar = comando.getMethod("nombre");
                Label label = new Label((String) ejecutar.invoke(comandoObject));
                label.setId(comando.getName());
                comboComando.getItems().add(label);
            } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        comboComando.setPromptText("Seleccionar comando");
        contenedor.getChildren().add(comboComando);

        JFXTextField comandoTexto = new JFXTextField();
        comandoTexto.setStyle(comandoTexto.getStyle() + "-fx-padding: 20pt 0 5pt 0;");
        comandoTexto.setDisable(true);
        comandoTexto.setLabelFloat(true);
        comandoTexto.setPromptText("Comando");
        RegexValidator validador = new RegexValidator();
        validador.setMessage("El formato no es correcto");
        comandoTexto.getValidators().add(validador);
        comandoTexto.textProperty().addListener((o) -> comandoTexto.validate());
        contenedor.getChildren().add(comandoTexto);

        VBox errorContenedor = new VBox();
        errorContenedor.setStyle(errorContenedor.getStyle() + "-fx-padding: 15pt 0 10pt 0;");
        errorContenedor.setPrefWidth(Float.MAX_VALUE);
        errorContenedor.setVisible(false);
        errorContenedor.setManaged(false);

        HBox error = new HBox();
        error.setPrefWidth(Float.MAX_VALUE);
        error.setStyle(error.getStyle() +  "-fx-padding: 10pt 5pt 10pt 5pt; "
                +"-fx-border-width: 1pt; "
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

        comboComando.getSelectionModel().selectedItemProperty().addListener((o, oldV, newV) -> {
            comandoTexto.setDisable(false);
            errorContenedor.setVisible(false);
            errorContenedor.setManaged(false);
            try {
                Class<? extends IComando> comando = (Class<? extends IComando>) Class.forName(newV.getId());
                IComando comandoObj = comando.newInstance();
                if (comando.isAnnotationPresent(Comando.class)) {
                    Comando comandoA = comando.getAnnotation(Comando.class);
                    validador.setRegexPattern(comandoA.comando().getRegex());
                }
                comandoTexto.setText(comandoObj.ayuda());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        layout.setBody(contenedor);

        JFXButton ejecutar = new JFXButton("Ejecutar");
        ejecutar.disableProperty().bind(comandoTexto.getValidators().get(0).hasErrorsProperty());
        ejecutar.setOnAction(event -> {
            errorContenedor.setVisible(false);
            errorContenedor.setManaged(false);
            Ejecutor.comando(comandoTexto.getText(), new EjecutorListener() {
                @Override
                public void onComandoError(ExcepcionRISK e) {
                    errorTitulo.setText("Error " + e.getCodigo());
                    errorValor.setText(e.getMensaje());
                    errorContenedor.setVisible(true);
                    errorContenedor.setManaged(true);
                }

                @Override
                public void onComandoEjecutado() {
                    FXMLLoader loader = new FXMLLoader();
                    try {
                        loader.load(MapaController.class.getResource("mapa.fxml").openStream());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ((MapaController) loader.getController()).actualizar(contenedor.getScene());
                    dialog.close();
                }
            });
        });

        JFXButton cerrar = new JFXButton("Cerrar");
        cerrar.setOnAction(event -> dialog.close());

        layout.setActions(cerrar, ejecutar);

        dialog.setContent(layout);
        dialog.show();
    }
}
