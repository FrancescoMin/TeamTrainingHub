package viste.first;

import ctrl_applicativo.RegistrazioneCtrlApplicativo;
import engineering.bean.RegistrazioneBean;
import engineering.eccezioni.EccezioneCambioScena;
import engineering.eccezioni.EccezioneUtenteInvalido;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import viste.first.utils.CambioScena;

import java.net.URL;
import java.util.ResourceBundle;

import static viste.first.utils.FxmlFileName.*;
import static viste.first.utils.FxmlFileName.PAGINA_HOME_GIOCATORE;

public class RegistrazioneCtrlGrafico implements Initializable {

    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private PasswordField confermaPasswordTextField;
    @FXML
    private CheckBox allenatoreCheckBox;
    @FXML
    private Button registratiButton;
    @FXML
    private Button tornaLoginButton;
    @FXML
    private Label registrazioneAvvenuta;
    @FXML
    private Label erroreInserimento;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        registratiButton.setOnAction(event -> handleRegistratiButton());
    }

    public void initialize()
    {
        System.out.println("Inizializzazione Temporanea");
    }

    private void handleRegistratiButton() {
        String username = usernameTextField.getText();
        String email = emailTextField.getText();
        String password = passwordTextField.getText();
        String confermaPassword = confermaPasswordTextField.getText();
        boolean isAllenatore = allenatoreCheckBox.isSelected();

        //se le password coincidono continuiamo con il salvataggio nel sistema della registrazione
        if (password.equals(confermaPassword)) {

            //creiamo il bean per la registrazione
            RegistrazioneBean registrazionebean = new RegistrazioneBean(username, email, password, isAllenatore);

            //creiamo il controller applicativo per la registrazione
            RegistrazioneCtrlApplicativo registrazionectrlapplicativo = new RegistrazioneCtrlApplicativo();

            try {

                //tentiamo di registrare l'utente
                registrazionectrlapplicativo.inserisciUtente(registrazionebean);
                System.out.println("Registrazione avvenuta con successo!");

                //a registrazione avvenuta, cambiamo scena
                Stage stage = (Stage) registrazioneAvvenuta.getScene().getWindow();
                CambioScena cambioScena = new CambioScena();

                //codice temporaneo per il cambio di scena alla home page del giocatore o allenatore
                if (registrazionebean.getAllenatore()) {
                    cambioScena.cambioScena(stage, PAGINA_HOME_ALLENATORE);
                } else {
                    cambioScena.cambioScena(stage, PAGINA_HOME_GIOCATORE);
                }

            } catch (EccezioneUtenteInvalido e) {
                mostra(e.getMessage());
            }
            catch (EccezioneCambioScena e) {
                mostra("Errore di cambio scena, riprovare");
            }
        } else {
            mostra("Le password non coincidono");
        }
    }

    private void mostra(String messaggio) {
        erroreInserimento.setText(messaggio);
        erroreInserimento.setVisible(true);
    }

}