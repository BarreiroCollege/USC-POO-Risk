package gal.sdc.usc.risk.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class VistaPrincipal extends Application {
    public VistaPrincipal() {
    }

    public static void crear() {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("principal.fxml"));
        Scene scene = new Scene(root, 720, 480);

        stage.setMaximized(true);
        stage.setMinWidth(360);
        stage.setMinHeight(240);
        stage.setTitle("RISK");
        stage.setScene(scene);
        stage.show();
    }
}
