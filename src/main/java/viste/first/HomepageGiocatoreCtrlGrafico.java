package viste.first;

import ctrlApplicativo.HomepageGiocatoreCtrlApplicativo;
import engineering.eccezioni.EccezioneGenerica;
import engineering.pattern.Singleton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelli.Giocatore;
import modelli.Utente;
import viste.first.utils.CambioScena;

import java.net.URL;
import java.util.ResourceBundle;

import static viste.first.utils.FxmlFileName.*;


public class HomepageGiocatoreCtrlGrafico implements  Initializable{

    @FXML
    private Button EntraInSquadraButton;

    @FXML
    private Button consultaAllenamentiButton;

    @FXML
    private Text ciaoText;

    @FXML
    private Label welcomeLabel;

    HomepageGiocatoreCtrlApplicativo homepageGiocatoreCtrlApplicativo = new HomepageGiocatoreCtrlApplicativo();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        consultaAllenamentiButton.setOnAction(event -> handleConsultaAllenamentiButtonAction());
        String username;
        username=homepageGiocatoreCtrlApplicativo.getMessaggioBenvenuto();
        welcomeLabel.setText("Ciao " + username);
    }

    @FXML
    protected void entraSquadra(){
        try {

            if (homepageGiocatoreCtrlApplicativo.isUtenteInSquadra()) {
                throw new EccezioneGenerica("Sei già in una squadra");
            }
            cambio(PAGINA_ENTRAINSQUADRA);

        } catch (EccezioneGenerica eccezioneGenerica) {
            System.out.println(eccezioneGenerica.getMessage());
        }
    }

    @FXML
    private void handleConsultaAllenamentiButtonAction() {
        // Logica per il pulsante "Consulta allenamenti"
        System.out.println("Consulta allenamenti cliccato");

        if(homepageGiocatoreCtrlApplicativo.isUtenteInSquadra()) {
            try {
                cambio(PAGINA_CONSULTA_ALLENAMENTI);

            } catch (EccezioneGenerica eccezioneGenerica) {
                System.out.println(eccezioneGenerica.getMessage());
            }
        }
        else {
            System.out.println("Non sei in una squadra, entrare in una squadra per consultare gli allenamenti");
        }
    }

    private void cambio(String string){
        Stage stage = (Stage) welcomeLabel.getScene().getWindow();
        CambioScena cambioScena = new CambioScena();
        cambioScena.cambioScena(stage, string);
    }
}