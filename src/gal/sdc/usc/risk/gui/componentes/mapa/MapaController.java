package gal.sdc.usc.risk.gui.componentes.mapa;

import com.jfoenix.controls.JFXButton;
import gal.sdc.usc.risk.gui.componentes.infopais.InfoPais;
import gal.sdc.usc.risk.jugar.Partida;
import gal.sdc.usc.risk.tablero.Celda;
import gal.sdc.usc.risk.tablero.Pais;
import gal.sdc.usc.risk.tablero.valores.EnlacesMaritimos;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Line;

import java.util.HashMap;

import static gal.sdc.usc.risk.tablero.Mapa.MAX_PAISES_X;
import static gal.sdc.usc.risk.tablero.Mapa.MAX_PAISES_Y;

public class MapaController extends Partida {
    @FXML
    public VBox contenedor;
    @FXML
    private AnchorPane anchor;

    private static boolean fronteras = true;

    public MapaController() {
    }

    @FXML
    private void initialize() {
        anchor.getStylesheets().add(MapaController.class.getResource("mapa.css").toExternalForm());
        /* Ejecutor.comando("crear mapa", new EjecutorListener() {
            @Override
            public void onComandoEjecutado() {
                actualizar();
            }
        }); */
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

                    if (sur == null || oeste == null || sombra == null) {
                        button.getStyleClass().add("pais-sombra");
                    }

                    button.getStyleClass().add("pais");
                    button.setText(pais.getAbreviatura());

                    button.setOnAction(action -> {
                        Parent parent = button;
                        while (parent.getParent() != null) parent = parent.getParent();
                        assert parent instanceof StackPane;
                        InfoPais.dialogo((StackPane) parent, pais).show();
                    });
                }
            }
        }

        Pane mapaContainer = (Pane) scene.lookup("#pane-mapa");
        mapaContainer.getChildren().removeIf(node -> node instanceof Line);

        if (super.getMapa() == null || !fronteras) return;

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

    public void cambiarFronteras() {
        MapaController.fronteras = !MapaController.fronteras;
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

        if (norte) out.append("5 ");
        else out.append("0 ");
        if (oeste) out.append("5 ");
        else out.append("0 ");
        if (sur) out.append("5 ");
        else out.append("0 ");
        if (este) out.append("5 ");
        else out.append("0 ");

        return out.toString();
    }
}
