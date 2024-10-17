package inizio;

import static viste.first.utils.FxmlFileName.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class ProvaApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(inizio.ProvaApplication.class.getResource(PAGINA_PRINCIPALE));
        Scene scene = new Scene(fxmlLoader.load(),1024 , 640);
        stage.setTitle("Pagina di prova");
        stage.setScene(scene);
        stage.show();

    }
    public static void main(String[] args) {
        launch();
    }

}