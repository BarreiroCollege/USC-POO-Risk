package gal.sdc.usc.risk.gui.componentes.nuevo;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import gal.sdc.usc.risk.comandos.Ejecutor;
import gal.sdc.usc.risk.comandos.EjecutorListener;
import gal.sdc.usc.risk.excepciones.ExcepcionRISK;
import gal.sdc.usc.risk.gui.PrincipalController;
import gal.sdc.usc.risk.gui.componentes.Utils;
import gal.sdc.usc.risk.gui.componentes.mapa.MapaController;
import gal.sdc.usc.risk.jugar.Partida;
import gal.sdc.usc.risk.tablero.Pais;
import gal.sdc.usc.risk.util.Dado;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class NuevoAtaque extends Partida {
    private final StackPane parent;

    public static void generarDialogo(StackPane parent) {
        new NuevoAtaque(parent).generar();
    }

    private NuevoAtaque(StackPane parent) {
        this.parent = parent;
    }

    private void generar() {
        JFXDialog dialog = new JFXDialog();
        dialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
        dialog.setDialogContainer(parent);

        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Label("Atacar Paises"));

        VBox contenedor = new VBox();

        Pais pais1 = MapaController.getPaisesSeleccionados().get(0);
        Pais pais2 = MapaController.getPaisesSeleccionados().get(1);

        HBox fila1 = new HBox();
        Label labelPais1 = new Label("Pais atacante");
        labelPais1.getStyleClass().add("dialogo-titulo");
        fila1.getChildren().add(labelPais1);
        fila1.getChildren().add(Utils.labelColor(pais1.getNombre(), pais1.getJugador().getColor().getHex()));
        contenedor.getChildren().add(fila1);
        contenedor.getChildren().add(Utils.separadorEntrada());

        HBox fila2 = new HBox();
        Label labelPais2 = new Label("Pais defensor");
        labelPais2.getStyleClass().add("dialogo-titulo");
        fila2.getChildren().add(labelPais2);
        fila2.getChildren().add(Utils.labelColor(pais2.getNombre(), pais2.getJugador().getColor().getHex()));
        contenedor.getChildren().add(fila2);
        contenedor.getChildren().add(Utils.separadorEntrada());

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

        JFXButton ejecutar = new JFXButton("Atacar");
        ejecutar.setOnAction(event -> {
            errorContenedor.setVisible(false);
            errorContenedor.setManaged(false);

            List<String> atacante = new ArrayList<>();
            List<String> defensor = new ArrayList<>();

            int atacantes;
            if (pais1.getEjercito().toInt() == 2) {
                atacantes = 1;
            } else if (pais1.getEjercito().toInt() == 3) {
                atacantes = 2;
            } else {
                atacantes = 3;
            }

            int defensores;
            if (pais2.getEjercito().toInt() == 1) {
                defensores = 1;
            } else {
                defensores = 2;
            }

            for (int i = 0; i < atacantes; i++) {
                atacante.add("" + Dado.tirar());
            }
            for (int i = 0; i < defensores; i++) {
                defensor.add("" + Dado.tirar());
            }

            List<String> comando = new ArrayList<>();
            comando.add("atacar");
            comando.add(pais1.getAbreviatura());
            comando.add(String.join("x", atacante));
            comando.add(pais2.getAbreviatura());
            comando.add(String.join("x", defensor));

            Ejecutor.comando(String.join(" ", comando),
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
                            MapaController.cambiarAtacar();
                            dialog.close();

                            if (pais2.getJugador().equals(pais1.getJugador())) {
                                PrincipalController.mensaje("El país ha sido conquistado");
                            }

                            PrincipalController.mensaje("Se han tirado los dados " + String.join("x", atacante)
                                    + " [" + pais1.getNombre() + "] frente a " + String.join("x", defensor)
                                    + " [" + pais2.getNombre() + "]");
                        }
                    });
        });

        if (MapaController.getPaisesSeleccionados().size() == 0) {
            contenedor.getChildren().clear();
            contenedor.getChildren().add(errorContenedor);

            errorTitulo.setText("Error");
            errorValor.setText("Primero selecciona en el mapa los países a asignar");
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
