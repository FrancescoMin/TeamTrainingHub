package inizio;

import static viste.first.utils.FxmlFileName.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
/*
public class
LoginApplicazione extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplicazione.class.getResource(PAGINA_PRINCIPALE));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Pagine Principale");
        stage.setScene(scene);
        stage.show();
    }

    /** Lettura dal file di configurazione per la scelta dell'interfaccia */

/*
    private Properties loadConfigurationProperties() {
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input != null) {
                properties.load(input);
            } else {
                System.out.println("MainApplication: Impossibile trovare il file di configurazione.");

            }
        } catch (IOException e) {
            System.out.println("MainApplication: Errore durante la lettura del file di configurazione %s" + e.getMessage());
        }
        return properties;
    }

    public static void main(String[] args) {

        launch();
    }

}
*/



public class
LoginApplicazione extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplicazione.class.getResource(PAGINA_PRINCIPALE));
        Scene scene = new Scene(fxmlLoader.load(),1024 , 640);
        stage.setTitle("Consulta Allenamenti");
        stage.setScene(scene);
        stage.show();

    }
    public static void main(String[] args) {
        launch();
    }
    private Properties loadConfigurationProperties() {
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input != null) {
                properties.load(input);
            } else {
                System.out.println("MainApplication: Impossibile trovare il file di configurazione.");

            }
        } catch (IOException e) {
            System.out.println("MainApplication: Errore durante la lettura del file di configurazione %s" + e.getMessage());
        }
        return properties;
    }
}

