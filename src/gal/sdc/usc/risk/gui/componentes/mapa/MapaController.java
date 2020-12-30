package gal.sdc.usc.risk.gui.componentes.mapa;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTabPane;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import gal.sdc.usc.risk.comandos.EjecutorAccion;
import gal.sdc.usc.risk.gui.PrincipalController;
import gal.sdc.usc.risk.gui.componentes.Utils;
import gal.sdc.usc.risk.gui.componentes.info.InfoContinente;
import gal.sdc.usc.risk.gui.componentes.info.InfoJugador;
import gal.sdc.usc.risk.gui.componentes.info.InfoPais;
import gal.sdc.usc.risk.gui.componentes.nuevo.NuevoAtaque;
import gal.sdc.usc.risk.gui.componentes.nuevo.NuevoRearme;
import gal.sdc.usc.risk.jugar.Partida;
import gal.sdc.usc.risk.tablero.Celda;
import gal.sdc.usc.risk.tablero.Pais;
import gal.sdc.usc.risk.tablero.valores.EnlacesMaritimos;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static gal.sdc.usc.risk.tablero.Mapa.MAX_PAISES_X;
import static gal.sdc.usc.risk.tablero.Mapa.MAX_PAISES_Y;

public class MapaController extends Partida {
    private final Integer TAB_PAIS = 0;
    private final Integer TAB_CONTINENTE = 1;
    private final Integer TAB_JUGADOR = 2;

    private final static HashMap<Button, Animation> animacionesSeleccion = new HashMap<>();
    private final static HashMap<Button, EventHandler<MouseEvent>> handlersBotones = new HashMap<>();
    private final static List<Pais> paisesSeleccionados = new LinkedList<>();
    private static MapaSeleccion seleccionar = MapaSeleccion.NINGUNO;
    private static boolean atacar = false;
    private static boolean rearmar = false;
    private static boolean repartir = false;

    private static EjecutorAccion accion;

    @FXML
    public VBox contenedor;
    @FXML
    private AnchorPane anchor;

    public MapaController() {
    }

    public static List<Pais> getPaisesSeleccionados() {
        return paisesSeleccionados;
    }

    public static void setSeleccionar(MapaSeleccion seleccionar) {
        if (seleccionar.equals(MapaSeleccion.NINGUNO) && !atacar && !rearmar) {
            paisesSeleccionados.clear();
        }
        MapaController.seleccionar = seleccionar;
    }

    public static void setAccion(EjecutorAccion accion) {
        MapaController.accion = accion;
    }

    public static boolean cambiarRepartir() {
        if (MapaController.repartir) {
            MapaController.setSeleccionar(MapaSeleccion.NINGUNO);
        } else {
            MapaController.setSeleccionar(MapaSeleccion.JUGADOR);
        }
        MapaController.repartir = !MapaController.repartir;
        return MapaController.repartir;
    }

    public static boolean cambiarAtacar() {
        if (MapaController.atacar) {
            MapaController.setSeleccionar(MapaSeleccion.NINGUNO);
        } else {
            MapaController.setSeleccionar(MapaSeleccion.JUGADOR);
        }
        MapaController.atacar = !MapaController.atacar;
        return MapaController.atacar;
    }

    public static boolean cambiarRearmar() {
        if (MapaController.rearmar) {
            MapaController.setSeleccionar(MapaSeleccion.NINGUNO);
        } else {
            MapaController.setSeleccionar(MapaSeleccion.JUGADOR);
        }
        MapaController.rearmar = !MapaController.rearmar;
        return MapaController.rearmar;
    }

    @FXML
    private void initialize() {
        anchor.getStylesheets().add(MapaController.class.getResource("mapa.css").toExternalForm());
    }

    public void actualizarFronteras(Scene scene, boolean mostrar) {
        Pane mapaContainer = (Pane) scene.lookup("#pane-mapa");
        for (Node node : mapaContainer.getChildren()) {
            if (node instanceof Line) {
                node.setVisible(mostrar);
            }
        }
    }

    public void actualizarFronteras(Scene scene) {
        Pane mapaContainer = (Pane) scene.lookup("#pane-mapa");
        mapaContainer.getChildren().removeIf(node -> node instanceof Line);

        if (super.getMapa() == null) return;

        for (EnlacesMaritimos enlace : EnlacesMaritimos.values()) {
            Pais pais1 = super.getMapa().getPaisPorNombre(enlace.getPais1().getNombre());
            Pais pais2 = super.getMapa().getPaisPorNombre(enlace.getPais2().getNombre());
            JFXButton button1 = (JFXButton) scene.lookup("#" + pais1.getCelda().getY() + "-" + pais1.getCelda().getX());
            JFXButton button2 = (JFXButton) scene.lookup("#" + pais2.getCelda().getY() + "-" + pais2.getCelda().getX());

            Line linea = new Line();
            linea.setStrokeWidth(4);
            linea.setFill(new Color(.0, .0, .0, .0));
            linea.setStroke(new Color(13. / 255., 71. / 255., 161. / 255., .6));

            ObjectBinding<Double> startX = Bindings.createObjectBinding(() -> {
                double x1 = button1.localToScene(new Point2D(0, 0)).getX();
                double x2 = button2.localToScene(new Point2D(0, 0)).getX();
                double y1 = button1.localToScene(new Point2D(0, 0)).getY();
                double y2 = button2.localToScene(new Point2D(0, 0)).getY();

                float angle = (float) Math.abs(Math.toDegrees(Math.atan2(y1 - y2, x1 - x2)));
                boolean lateral = angle < 45 || angle > 135;

                if (x1 == x2 || !lateral) return x1 + button1.getWidth() / 2;
                else if (x1 > x2) return x1;
                else return x1 + button1.getWidth();
            }, button1.localToSceneTransformProperty(), button2.localToSceneTransformProperty(), button1.widthProperty());
            ObjectBinding<Double> startY = Bindings.createObjectBinding(() -> {
                double x1 = button1.localToScene(new Point2D(0, 0)).getX();
                double x2 = button2.localToScene(new Point2D(0, 0)).getX();
                double y1 = button1.localToScene(new Point2D(0, 0)).getY();
                double y2 = button2.localToScene(new Point2D(0, 0)).getY();

                float angle = (float) Math.abs(Math.toDegrees(Math.atan2(y1 - y2, x1 - x2)));
                boolean lateral = angle < 45 || angle > 135;

                if (y1 == y2 || lateral) return y1 + button1.getHeight() / 2;
                else if (y1 > y2) return y1;
                else return y1 + button1.getHeight();
            }, button1.localToSceneTransformProperty(), button2.localToSceneTransformProperty(), button1.heightProperty());
            ObjectBinding<Double> endX = Bindings.createObjectBinding(() -> {
                double x1 = button1.localToScene(new Point2D(0, 0)).getX();
                double x2 = button2.localToScene(new Point2D(0, 0)).getX();
                double y1 = button1.localToScene(new Point2D(0, 0)).getY();
                double y2 = button2.localToScene(new Point2D(0, 0)).getY();

                float angle = (float) Math.abs(Math.toDegrees(Math.atan2(y1 - y2, x1 - x2)));
                boolean lateral = angle < 45 || angle > 135;

                if (x1 == x2 || !lateral) return x2 + button2.getWidth() / 2;
                else if (x1 < x2) return x2;
                else return x2 + button2.getWidth();
            }, button1.localToSceneTransformProperty(), button2.localToSceneTransformProperty(), button2.widthProperty());
            ObjectBinding<Double> endY = Bindings.createObjectBinding(() -> {
                double x1 = button1.localToScene(new Point2D(0, 0)).getX();
                double x2 = button2.localToScene(new Point2D(0, 0)).getX();
                double y1 = button1.localToScene(new Point2D(0, 0)).getY();
                double y2 = button2.localToScene(new Point2D(0, 0)).getY();

                float angle = (float) Math.abs(Math.toDegrees(Math.atan2(y1 - y2, x1 - x2)));
                boolean lateral = angle < 45 || angle > 135;

                if (y1 == y2 || lateral) return y2 + button2.getHeight() / 2;
                else if (y1 < y2) return y2;
                else return y2 + button2.getHeight();
            }, button1.localToSceneTransformProperty(), button2.localToSceneTransformProperty(), button2.heightProperty());

            linea.startXProperty().bind(startX);
            linea.startYProperty().bind(startY);
            linea.endXProperty().bind(endX);
            linea.endYProperty().bind(endY);

            mapaContainer.getChildren().add(linea);
            linea.toFront();
        }
    }

    public void actualizar(Scene scene) {
        HashMap<Celda, Pais> paises = super.getMapa().getPaisesPorCeldas();
        for (int i = 0; i < MAX_PAISES_Y; i++) {
            for (int j = 0; j < MAX_PAISES_X; j++) {
                Celda celda = new Celda.Builder().withX(j).withY(i).build();

                Pais norte = paises.get(new Celda.Builder().withX(j).withY(i - 1).build());
                Pais sur = paises.get(new Celda.Builder().withX(j).withY(i + 1).build());
                Pais este = paises.get(new Celda.Builder().withX(j - 1).withY(i).build());
                Pais oeste = paises.get(new Celda.Builder().withX(j + 1).withY(i).build());
                Pais sombra = paises.get(new Celda.Builder().withX(j + 1).withY(i + 1).build());

                JFXButton button = (JFXButton) scene.lookup("#" + i + "-" + j);
                button.setDisable(true);
                button.setBackground(null);
                if (paises.containsKey(celda)) {
                    button.setDisable(false);
                    Pais pais = paises.get(celda);
                    if (pais.getJugador() != null) {
                        button.setStyle(button.getStyle() + "-fx-background-color: linear-gradient("
                                + "from 0px .75em to .75em 0px, repeat,"
                                + pais.getJugador().getColor().getHex() + " 0%,"
                                + pais.getJugador().getColor().getHex() + " 49%,"
                                + "derive(" + pais.getJugador().getColor().getHex() + ", 30%) 50%,"
                                + "derive(" + pais.getJugador().getColor().getHex() + ", 30%) 99%"
                                + ");");
                    } else {
                        button.setStyle(button.getStyle() + "-fx-background-color: #fafafa;");
                    }

                    String bordes = bordes(norte != null, sur != null, este != null, oeste != null);
                    String bordesContinente = fronterasContinente(norte == null || !norte.getContinente().equals(pais.getContinente()),
                            sur == null || !sur.getContinente().equals(pais.getContinente()),
                            este == null || !este.getContinente().equals(pais.getContinente()),
                            oeste == null || !oeste.getContinente().equals(pais.getContinente()));
                    button.setStyle(button.getStyle()
                            + "-fx-border-radius: " + bordes.replace("5", "3") + ";"
                            + "-fx-background-radius: " + bordes + ";"
                            + "-fx-border-color: " + pais.getColor().getHex() + ";"
                            + "-fx-border-width: " + bordesContinente + ";");

                    ContextMenu contextMenu = new ContextMenu();
                    contextMenu.getItems().add(new MenuItem("Probando"));
                    button.setContextMenu(contextMenu);

                    if (sur == null || oeste == null || sombra == null) {
                        button.getStyleClass().add("pais-sombra");
                    }

                    button.getStyleClass().add("pais");
                    button.setText(null);

                    Parent parent = button;
                    while (parent.getParent() != null) parent = parent.getParent();
                    assert parent instanceof StackPane;
                    StackPane finalParent = (StackPane) parent;

                    if (!animacionesSeleccion.containsKey(button)) {
                        Animation animacion = new Transition() {
                            {
                                setCycleDuration(Duration.millis(1000));
                                setInterpolator(Interpolator.EASE_OUT);
                                setAutoReverse(true);
                                setCycleCount(Timeline.INDEFINITE);
                            }

                            @Override
                            protected void interpolate(double frac) {
                                Color vColor = new Color(1, 1, 1, 1 - frac);
                                button.setBackground(new Background(new BackgroundFill(vColor, CornerRadii.EMPTY, Insets.EMPTY)));
                            }
                        };
                        animacionesSeleccion.put(button, animacion);
                    }

                    if (paisesSeleccionados.contains(pais)) {
                        animacionesSeleccion.get(button).play();
                    } else {
                        animacionesSeleccion.get(button).stop();
                        button.setBackground(null);
                    }

                    button.setGraphic(this.contenidoBoton(pais));
                    button.setContextMenu(this.menuPais(finalParent, pais));

                    if (seleccionar.equals(MapaSeleccion.VACIO)) {
                        button.setMouseTransparent(pais.getJugador() != null);
                    } else if (seleccionar.equals(MapaSeleccion.JUGADOR)) {
                        button.setMouseTransparent(!pais.getJugador().equals(super.getJugadorTurno()));
                    } else if (seleccionar.equals(MapaSeleccion.OTROS)) {
                        button.setMouseTransparent(pais.getJugador().equals(super.getJugadorTurno()));
                    } else {
                        button.setMouseTransparent(false);
                    }

                    if (accion != null) {
                        button.setOnAction(null);
                        if (!handlersBotones.containsKey(button)) {
                            EventHandler<MouseEvent> handler = new EventHandler<MouseEvent>() {
                                Timer timer = null;

                                @Override
                                public void handle(MouseEvent event) {
                                    if (event.getButton().equals(MouseButton.PRIMARY) && event.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
                                        if (timer == null) {
                                            timer = new Timer();
                                            timer.schedule(new TimerTask() {
                                                @Override
                                                public void run() {
                                                    timer = null;
                                                    Platform.runLater(() -> {
                                                        accion.onLongClick(pais);
                                                        // Utils.actualizar();
                                                    });
                                                }
                                            }, 500);
                                        }
                                    } else if (event.getButton().equals(MouseButton.PRIMARY) && event.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
                                        if (timer != null) {
                                            timer.cancel();
                                            timer = null;
                                            accion.onClick(pais);
                                            // Utils.actualizar();
                                        }
                                    }
                                }
                            };
                            button.addEventFilter(MouseEvent.ANY, handler);
                            handlersBotones.put(button, handler);
                        }
                    } else {
                        if (handlersBotones.containsKey(button)) {
                            button.removeEventFilter(MouseEvent.ANY, handlersBotones.get(button));
                            handlersBotones.remove(button);
                        }
                        button.setOnAction(action -> {
                            if (!seleccionar.equals(MapaSeleccion.NINGUNO)) {
                                if (paisesSeleccionados.contains(pais)) {
                                    paisesSeleccionados.remove(pais);
                                } else {
                                    paisesSeleccionados.add(pais);
                                }

                                if (atacar) {
                                    if (paisesSeleccionados.size() == 1) {
                                        MapaController.setSeleccionar(MapaSeleccion.OTROS);
                                        PrincipalController.mensaje("Selecciona el país a atacar");
                                    } else if (paisesSeleccionados.size() == 2) {
                                        NuevoAtaque.generarDialogo(finalParent);
                                    }
                                } else if (rearmar) {
                                    if (paisesSeleccionados.size() == 1) {
                                        PrincipalController.mensaje("Selecciona el país de destino");
                                    } else if (paisesSeleccionados.size() == 2) {
                                        NuevoRearme.generarDialogo(finalParent);
                                    }
                                }
                            } else {
                                this.generarDialogo(finalParent, pais).show();
                            }

                            // Utils.actualizar();
                        });
                    }
                }
            }
        }

        this.actualizarFronteras(scene);
    }

    private VBox contenidoBoton(Pais pais) {
        VBox contenedor = new VBox();
        HBox.setHgrow(contenedor, Priority.ALWAYS);
        contenedor.setAlignment(Pos.CENTER);

        Label lblPais = new Label(pais.getAbreviatura());
        lblPais.getStyleClass().add("btn-pais-pais");
        lblPais.setWrapText(true);
        HBox.setHgrow(lblPais, Priority.ALWAYS);
        contenedor.getChildren().add(lblPais);

        if (pais.getJugador() != null) {
            VBox spacer = new VBox();
            spacer.setPrefHeight(15);
            contenedor.getChildren().add(spacer);

            HBox contJugador = new HBox();
            HBox.setHgrow(contJugador, Priority.ALWAYS);
            contJugador.setAlignment(Pos.CENTER);

            MaterialDesignIconView icono = new MaterialDesignIconView(MaterialDesignIcon.ACCOUNT_MULTIPLE);
            icono.setSize("16px");
            contJugador.getChildren().add(icono);

            Label lblJugador = new Label(" x" + pais.getEjercito().toInt());
            lblJugador.getStyleClass().add("btn-pais-jugador");
            contJugador.getChildren().add(lblJugador);

            contenedor.getChildren().add(contJugador);
        }

        return contenedor;
    }

    private ContextMenu menuPais(StackPane parent, Pais pais) {
        ContextMenu context = new ContextMenu();

        MenuItem verPais = new MenuItem("Ver País");
        verPais.setOnAction(action -> this.generarDialogo(parent, pais, TAB_PAIS).show());
        context.getItems().add(verPais);

        MenuItem verContinente = new MenuItem("Ver Continente");
        verContinente.setOnAction(action -> this.generarDialogo(parent, pais, TAB_CONTINENTE).show());
        context.getItems().add(verContinente);

        if (pais.getJugador() != null) {
            MenuItem verJugador = new MenuItem("Ver Jugador");
            verJugador.setOnAction(action -> this.generarDialogo(parent, pais, TAB_JUGADOR).show());
            context.getItems().add(verJugador);
        }

        return context;
    }

    private JFXDialog generarDialogo(StackPane stackPane, Pais pais) {
        return this.generarDialogo(stackPane, pais, null);
    }

    private JFXDialog generarDialogo(StackPane stackPane, Pais pais, Integer seleccionado) {
        JFXDialog dialog = new JFXDialog();
        dialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
        dialog.setDialogContainer(stackPane);

        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Label(pais.getNombre()));

        JFXTabPane tabs = new JFXTabPane();

        Tab tabPais = new Tab();
        tabPais.setText("País");
        tabPais.setContent(InfoPais.generarDialogo(pais));
        tabs.getTabs().add(TAB_PAIS, tabPais);

        Tab tabContinente = new Tab();
        tabContinente.setText("Continente");
        tabContinente.setContent(InfoContinente.generarContinente(pais.getContinente()));
        tabs.getTabs().add(TAB_CONTINENTE, tabContinente);

        if (pais.getJugador() != null) {
            Tab tabJugador = new Tab();
            tabJugador.setText("Jugador");
            tabJugador.setContent(InfoJugador.generarJugador(pais));
            tabs.getTabs().add(TAB_JUGADOR, tabJugador);
        }

        if (seleccionado != null) {
            tabs.getSelectionModel().select(seleccionado);
        }

        content.setBody(tabs);

        JFXButton cerrar = new JFXButton("Cerrar");
        cerrar.setOnAction(event -> dialog.close());
        content.setActions(cerrar);

        dialog.setContent(content);
        return dialog;
    }

    private String bordes(boolean norte, boolean sur, boolean este, boolean oeste) {
        boolean supizq = !norte && !este;
        boolean supder = !norte && !oeste;
        boolean infizq = !sur && !este;
        boolean infder = !sur && !oeste;

        StringBuilder out = new StringBuilder();

        if (supizq) out.append("5pt ");
        else out.append("0pt ");
        if (supder) out.append("5pt ");
        else out.append("0pt ");
        if (infder) out.append("5pt ");
        else out.append("0pt ");
        if (infizq) out.append("5pt ");
        else out.append("0pt ");

        return out.toString();
    }

    private String fronterasContinente(boolean norte, boolean sur, boolean este, boolean oeste) {
        StringBuilder out = new StringBuilder();

        if (norte) out.append("6 ");
        else out.append("0 ");
        if (oeste) out.append("6 ");
        else out.append("0 ");
        if (sur) out.append("6 ");
        else out.append("0 ");
        if (este) out.append("6 ");
        else out.append("0 ");

        return out.toString();
    }
}
