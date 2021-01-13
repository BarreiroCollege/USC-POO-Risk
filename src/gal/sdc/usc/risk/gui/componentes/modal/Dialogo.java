package gal.sdc.usc.risk.gui.componentes.modal;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class Dialogo {
    private static final JFXDialog dialogo;
    private static final JFXDialogLayout layout;

    private static final JFXButton close;

    static {
        dialogo = new JFXDialog();
        dialogo.setTransitionType(JFXDialog.DialogTransition.CENTER);

        layout = new JFXDialogLayout();

        close = new JFXButton("Cancelar");
        close.setOnAction(event -> dialogo.close());
        layout.setActions(close);

        dialogo.setContent(layout);
    }

    public Dialogo() {
        close.setText("Cerrar");
        layout.setActions(close);
    }

    public Dialogo(String heading, Node content, String closeText) {
        this(new Label(heading), content, closeText);
    }

    public Dialogo(Node heading, Node content, String closeText) {
        this(heading, content, closeText, null);
    }

    public Dialogo(String heading, Node content, String closeText, Button extra) {
        this(new Label(heading), content, closeText, extra);
    }

    public Dialogo(Node heading, Node content, String closeText, Button extra) {
        layout.setHeading(heading);
        layout.setBody(content);

        close.setText(closeText);

        if (extra == null) {
            layout.setActions(close);
        } else {
            layout.setActions(close, extra);
        }
    }

    public Dialogo setHeading(String text) {
        return this.setHeading(new Label(text));
    }

    public Dialogo setHeading(Node node) {
        layout.setHeading(node);
        return this;
    }

    public Dialogo setContent(String text) {
        return this.setContent(new Label(text));
    }

    public Dialogo setContent(Node node) {
        layout.setBody(node);
        return this;
    }

    public Dialogo setCloseText(String text) {
        close.setText(text);
        return this;
    }

    public Dialogo setExtra(Button extra) {
        layout.setActions(close, extra);
        return this;
    }

    public static void setContainer(StackPane panel) {
        dialogo.setDialogContainer(panel);
    }

    public void show() {
        dialogo.show();
    }

    public void close() {
        dialogo.close();
    }
}
