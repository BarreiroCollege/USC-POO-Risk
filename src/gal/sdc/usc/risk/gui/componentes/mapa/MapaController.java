package gal.sdc.usc.risk.gui.componentes.mapa;

import com.jfoenix.controls.JFXButton;
import gal.sdc.usc.risk.comandos.Ejecutor;
import gal.sdc.usc.risk.comandos.EjecutorListener;
import gal.sdc.usc.risk.gui.componentes.infopais.InfoPais;
import gal.sdc.usc.risk.jugar.Partida;
import gal.sdc.usc.risk.tablero.Celda;
import gal.sdc.usc.risk.tablero.Pais;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.HashMap;

import static gal.sdc.usc.risk.tablero.Mapa.MAX_PAISES_X;
import static gal.sdc.usc.risk.tablero.Mapa.MAX_PAISES_Y;

public class MapaController extends Partida {
    @FXML
    private AnchorPane anchor;
    @FXML
    private VBox contenedor;

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
                        button.setStyle(button.getStyle() + "-fx-background-color: " + pais.getJugador().getColor().getHex() + ";");
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
