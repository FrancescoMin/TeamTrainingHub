package viste.first;

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

    private String username;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        consultaAllenamentiButton.setOnAction(event -> handleConsultaAllenamentiButtonAction());
        welcomeLabel.setText("Ciao " + username);
    }

    @FXML
    protected void entraSquadra(){
        try {
            Utente utente = Singleton.getInstance().getUtenteCorrente();
            if (!utente.getSquadra().getNome().isEmpty()) {
                throw new EccezioneGenerica("Sei già in una squadra");
            }
            System.out.println(" Utente " + utente.getEmail() + " Squadra " + utente.getSquadra().getNome() + " Allenamenti " + utente.getAllenamenti() + " has been accepted!");
            cambio(PAGINA_ENTRAINSQUADRA);

        } catch (EccezioneGenerica eccezioneGenerica) {
            System.out.println(eccezioneGenerica.getMessage());
        }
    }

    @FXML
    private void handleConsultaAllenamentiButtonAction() {
        // Logica per il pulsante "Consulta allenamenti"
        try {
            cambio(PAGINA_CONSULTA_ALLENAMENTI);

        } catch (EccezioneGenerica eccezioneGenerica) {
            System.out.println(eccezioneGenerica.getMessage());
        }
        System.out.println("Consulta allenamenti cliccato");
    }

    private void cambio(String string){
        Stage stage = (Stage) welcomeLabel.getScene().getWindow();
        CambioScena cambioScena = new CambioScena();
        cambioScena.cambioScena(stage, string);
    }
}