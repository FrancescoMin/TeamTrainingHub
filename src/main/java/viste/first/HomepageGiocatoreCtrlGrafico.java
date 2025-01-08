package viste.first;

import engineering.eccezioni.EccezioneGenerica;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import viste.first.utils.CambioScena;

import java.net.URL;
import java.util.ResourceBundle;

import static viste.first.utils.FxmlFileName.PAGINA_CONSULTA_ALLENAMENTI;
import static viste.first.utils.FxmlFileName.PAGINA_ENTRAINSQUADRA;
import static viste.first.utils.FxmlFileName.PAGINA_PRINCIPALE;


public class HomepageGiocatoreCtrlGrafico implements  Initializable{

    @FXML
    private Button EntraInSquadraButton;

    @FXML
    private Button consultaAllenamentiButton;

    @FXML
    private Text ciaoText;

    @FXML
    private Label welcomeLabel;

    private String username;

    @FXML
    protected void RitornoAlLogin() {
        System.out.println("Ritorno al Login");

        //cambio scena alla pagina di login
        try {
            Stage stage = (Stage) ciaoText.getScene().getWindow();
            CambioScena cambioScena = new CambioScena();
            cambioScena.cambioScena(stage, PAGINA_PRINCIPALE);

        } catch (EccezioneGenerica EccezioneGenerica) {
            System.out.println(EccezioneGenerica.getMessage());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EntraInSquadraButton.setOnAction(event -> handleEntraInSquadraButtonAction());
        consultaAllenamentiButton.setOnAction(event -> handleConsultaAllenamentiButtonAction());
        welcomeLabel.setText("Ciao " + username);
    }

    @FXML
    private void handleEntraInSquadraButtonAction() {
        try {
            Stage stage = (Stage) EntraInSquadraButton.getScene().getWindow();
            CambioScena cambioScena = new CambioScena();
            cambioScena.cambioScena(stage, PAGINA_ENTRAINSQUADRA);

        } catch (EccezioneGenerica eccezioneGenerica) {
            System.out.println(eccezioneGenerica.getMessage());
        }
    }

    @FXML
    private void handleConsultaAllenamentiButtonAction() {
        // Logica per il pulsante "Consulta allenamenti"
        try {
            Stage stage = (Stage) consultaAllenamentiButton.getScene().getWindow();
            CambioScena cambioScena = new CambioScena();
            cambioScena.cambioScena(stage, PAGINA_CONSULTA_ALLENAMENTI);

        } catch (EccezioneGenerica eccezioneGenerica) {
            System.out.println(eccezioneGenerica.getMessage());
        }
        System.out.println("Consulta allenamenti cliccato");
    }
}