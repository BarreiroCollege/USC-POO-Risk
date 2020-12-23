package gal.sdc.usc.risk.gui.componentes.infopais;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTabPane;
import gal.sdc.usc.risk.gui.componentes.Utils;
import gal.sdc.usc.risk.jugar.Partida;
import gal.sdc.usc.risk.tablero.Carta;
import gal.sdc.usc.risk.tablero.Continente;
import gal.sdc.usc.risk.tablero.Jugador;
import gal.sdc.usc.risk.tablero.Pais;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;

public class InfoPais extends Partida {
    private static final String TAB_PAIS = "País";
    private static final String TAB_CONTINENTE = "Continente";
    private static final String TAB_JUGADOR = "Jugador";
    private static final Integer TAB_PAIS_I = 0;
    private static final Integer TAB_CONTINENTE_I = 1;
    private static final Integer TAB_JUGADOR_I = 2;

    public JFXDialog dialogo(StackPane stackPane, Pais pais) {
        JFXDialog dialog = new JFXDialog();
        dialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
        dialog.setDialogContainer(stackPane);

        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Label(pais.getNombre()));

        JFXTabPane tabs = new JFXTabPane();

        Tab tabPais = generarPais(pais);
        // TODO
        /* tabPais.setOnSelectionChanged(event -> {
            if (event.getTarget() instanceof Tab) {
                String comando;

                int index = 0;
                TabPane tabPane = ((Tab) event.getTarget()).getTabPane();
                if (tabPane != null) index = tabPane.getSelectionModel().getSelectedIndex();
                System.out.println(index);
                switch (index) {
                    case 0:
                        comando = "describir pais " + pais.getAbreviatura();
                        break;
                    case 1:
                        comando = "describir continente " + pais.getContinente().getAbreviatura();
                        break;
                    case 2:
                        comando = "describir jugador " + pais.getJugador().getNombre();
                        break;
                    default:
                        comando = "";
                        break;
                }
                Ejecutor.comando(comando, new EjecutorListener() {
                    @Override
                    public void onComandoEjecutado() {
                        JFXSnackbar bar = new JFXSnackbar(stackPane);
                        bar.fireEvent(new JFXSnackbar.SnackbarEvent(new JFXSnackbarLayout(comando)));
                    }
                });
            }
        }); */
        tabs.getTabs().add(TAB_PAIS_I, tabPais);

        Tab tabContinente = generarContinente(pais.getContinente());
        tabs.getTabs().add(TAB_CONTINENTE_I, tabContinente);

        if (pais.getJugador() != null) {
            Tab tabJugador = generarJugador(pais);
            tabs.getTabs().add(TAB_JUGADOR_I, tabJugador);
        }

        content.setBody(tabs);

        JFXButton cerrar = new JFXButton("Cerrar");
        cerrar.setOnAction(event -> dialog.close());
        content.setActions(cerrar);

        dialog.setContent(content);
        return dialog;
    }

    private static Tab generarPais(Pais pais) {
        Tab tab = new Tab(TAB_PAIS);
        VBox contenido = new VBox();
        contenido.getStyleClass().add("dialogo-contenido");

        HBox fila;
        Label label;

        fila = new HBox();
        label = new Label("Nombre");
        label.getStyleClass().add("dialogo-titulo");
        fila.getChildren().add(label);
        label = new Label(pais.getNombre());
        label.getStyleClass().add("dialogo-valor");
        fila.getChildren().add(label);
        contenido.getChildren().add(fila);

        contenido.getChildren().add(Utils.separadorEntrada());

        fila = new HBox();
        label = new Label("Abreviatura");
        label.getStyleClass().add("dialogo-titulo");
        fila.getChildren().add(label);
        label = new Label(pais.getAbreviatura());
        label.getStyleClass().add("dialogo-valor");
        fila.getChildren().add(label);
        contenido.getChildren().add(fila);

        contenido.getChildren().add(Utils.separadorEntrada());

        fila = new HBox();
        label = new Label("Continente");
        label.getStyleClass().add("dialogo-titulo");
        fila.getChildren().add(label);
        label = Utils.labelColor(pais.getContinente().getNombre(), pais.getContinente().getColor().getHex());
        fila.getChildren().add(label);
        contenido.getChildren().add(fila);

        contenido.getChildren().add(Utils.separadorEntrada());

        fila = new HBox();
        label = new Label("Fronteras");
        label.getStyleClass().add("dialogo-titulo");
        fila.getChildren().add(label);
        VBox paises = new VBox();
        for (Pais frontera : pais.getFronteras().getTodas()) {
            if (frontera.getContinente().equals(pais.getContinente())) {
                label = new Label(frontera.getNombre());
                label.getStyleClass().add("dialogo-valor");
                // TODO: Icono para marítimas
                paises.getChildren().add(label);
            }
        }
        for (Pais frontera : pais.getFronteras().getTodas()) {
            if (!frontera.getContinente().equals(pais.getContinente())) {
                label = Utils.labelColor(frontera.getNombre(), frontera.getColor().getHex());
                // TODO: Icono para marítimas
                paises.getChildren().add(label);
            }
        }
        fila.getChildren().add(paises);
        contenido.getChildren().add(fila);

        contenido.getChildren().add(Utils.separadorEntrada());

        if (pais.getJugador() != null) {
            fila = new HBox();
            label = new Label("Jugador");
            label.getStyleClass().add("dialogo-titulo");
            fila.getChildren().add(label);
            label = Utils.labelColor(pais.getJugador().getNombre(), pais.getJugador().getColor().getHex());
            fila.getChildren().add(label);
            contenido.getChildren().add(fila);

            contenido.getChildren().add(Utils.separadorEntrada());
        }

        fila = new HBox();
        label = new Label("# Ejércitos");
        label.getStyleClass().add("dialogo-titulo");
        fila.getChildren().add(label);
        label = new Label("" + pais.getEjercito().toInt() + " ejércitos");
        label.getStyleClass().add("dialogo-valor");
        fila.getChildren().add(label);
        contenido.getChildren().add(fila);

        contenido.getChildren().add(Utils.separadorEntrada());

        fila = new HBox();
        label = new Label("# Ocupaciones");
        label.getStyleClass().add("dialogo-titulo");
        fila.getChildren().add(label);
        label = new Label("" + pais.getNumVecesConquistado() + " veces ocupado");
        label.getStyleClass().add("dialogo-valor");
        fila.getChildren().add(label);
        contenido.getChildren().add(fila);

        tab.setContent(contenido);
        return tab;
    }

    private static Tab generarContinente(Continente continente) {
        Tab tab = new Tab(TAB_CONTINENTE);
        VBox contenido = new VBox();
        contenido.getStyleClass().add("dialogo-contenido");

        HBox fila;
        Label label;

        fila = new HBox();
        label = new Label("Nombre");
        label.getStyleClass().add("dialogo-titulo");
        fila.getChildren().add(label);
        label = Utils.labelColor(continente.getNombre(), continente.getColor().getHex());
        fila.getChildren().add(label);
        contenido.getChildren().add(fila);

        contenido.getChildren().add(Utils.separadorEntrada());

        fila = new HBox();
        label = new Label("Abreviatura");
        label.getStyleClass().add("dialogo-titulo");
        fila.getChildren().add(label);
        label = new Label(continente.getAbreviatura());
        label.getStyleClass().add("dialogo-valor");
        fila.getChildren().add(label);
        contenido.getChildren().add(fila);

        contenido.getChildren().add(Utils.separadorEntrada());

        fila = new HBox();
        label = new Label("Jugadores");
        label.getStyleClass().add("dialogo-titulo");
        fila.getChildren().add(label);
        VBox valores = new VBox();
        HashMap<Jugador, Integer> jugadoresEjercitos = new HashMap<>();
        for (Pais pais : continente.getPaises().values()) {
            if (pais.getJugador() == null) continue;
            jugadoresEjercitos.putIfAbsent(pais.getJugador(), 0);
            jugadoresEjercitos.put(pais.getJugador(), jugadoresEjercitos.get(pais.getJugador()) + pais.getEjercito().toInt());
        }
        for (Map.Entry<Jugador, Integer> jugador : jugadoresEjercitos.entrySet()) {
            valores.getChildren().add(Utils.labelColor(jugador.getKey().getNombre() + ": " + jugador.getValue() + " ejércitos", jugador.getKey().getColor().getHex()));
        }
        fila.getChildren().add(valores);
        if (valores.getChildren().size() > 0) {
            contenido.getChildren().add(fila);
            contenido.getChildren().add(Utils.separadorEntrada());
        }

        fila = new HBox();
        label = new Label("# Ejércitos");
        label.getStyleClass().add("dialogo-titulo");
        fila.getChildren().add(label);
        label = new Label("" + continente.getNumEjercitos() + " ejércitos en total");
        label.getStyleClass().add("dialogo-valor");
        fila.getChildren().add(label);
        contenido.getChildren().add(fila);

        contenido.getChildren().add(Utils.separadorEntrada());

        fila = new HBox();
        label = new Label("Rearme");
        label.getStyleClass().add("dialogo-titulo");
        fila.getChildren().add(label);
        label = new Label("" + continente.getEjercitosRearme() + " ejércitos por turno");
        label.getStyleClass().add("dialogo-valor");
        fila.getChildren().add(label);
        contenido.getChildren().add(fila);

        tab.setContent(contenido);
        return tab;
    }

    private Tab generarJugador(Pais pais) {
        Tab tab = new Tab(TAB_JUGADOR);
        tab.setContent(new InfoJugador().generarJugador(pais.getJugador()));
        return tab;
    }
}
