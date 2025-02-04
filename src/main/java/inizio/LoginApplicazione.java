package inizio;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import viste.first.utils.FxmlFileName;
import viste.second.LoginCLI;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class
LoginApplicazione extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // Carica le proprietà dal file di configurazione
        Properties properties = loadConfigurationProperties();

        // Ottieni il tipo di interfaccia dalle proprietà
        int interfaceType = Integer.parseInt(properties.getProperty("interface.type","1"));

        switch (interfaceType) {
            case 1: {
                // Interfaccia grafica
                loadGraphicalInterface(stage);
                break;
            }
            case 2: {
                // Interfaccia a riga di comando
                startCommandLineInterface();
                break;
            }
            default:{
                System.out.println("Tipo di interfaccia non valido. Utilizzo l'interfaccia grafica di default");
                // Interfaccia grafica
                loadGraphicalInterface(stage);
                break;
            }
        }
    }

    private void loadGraphicalInterface(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FxmlFileName.PAGINA_PRINCIPALE));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    private void startCommandLineInterface() {
        LoginCLI loginCLI = new LoginCLI();
        loginCLI.start();
    }

    /** Lettura dal file di configurazione per la scelta dell'interfaccia */
    private Properties loadConfigurationProperties() {
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input != null) {
                properties.load(input);
            } else {
                System.out.println("Errore durante la lettura del file di configurazione");
            }
        } catch (IOException e) {
            System.out.println("Errore durante la lettura del file di configurazione");
        }
        return properties;
    }

    public static void main(String[] args) {
        launch();
    }

}

