package viste.first;

import ctrlApplicativo.RegistrazioneCtrlApplicativo;
import engineering.bean.RegistrazioneBean;
import engineering.eccezioni.EccezioneGenerica;
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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        registratiButton.setOnAction(event -> handleRegistratiButton());
        tornaLoginButton.setOnAction(event -> handleTornaLoginButton());
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
            RegistrazioneBean registrazioneBean = new RegistrazioneBean(username, email, password, isAllenatore);

            //creiamo il controller applicativo per la registrazione
            RegistrazioneCtrlApplicativo registrazioneCtrlApplicativo = new RegistrazioneCtrlApplicativo();

            try {

                //tentiamo di registrare l'utente
                registrazioneCtrlApplicativo.inserisciUtente(registrazioneBean);
                System.out.println("Registrazione avvenuta con successo!");

                //a registrazione avvenuta, cambiamo scena
                Stage stage = (Stage) registrazioneAvvenuta.getScene().getWindow();
                CambioScena cambioScena = new CambioScena();

                //codice temporaneo per il cambio di scena alla home page del giocatore o allenatore
                if (registrazioneBean.getAllenatore()) {
                    cambioScena.cambioScena(stage, PAGINA_HOME_ALLENATORE);
                } else {
                    cambioScena.cambioScena(stage, PAGINA_HOME_GIOCATORE);
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
                registrazioneAvvenuta.setText(e.getMessage());
                registrazioneAvvenuta.setVisible(true);
            }
        } else {
            registrazioneAvvenuta.setText("Le password non coincidono!");
            registrazioneAvvenuta.setVisible(true);
        }
    }

    private void handleTornaLoginButton() {
        // Logica per tornare alla schermata di login
        System.out.println("Torno alla schermata di login");

        //CODICE TEMPORANEO PER IL PASSAGGIO DI SCENE ALLA PAGINA DI REGISTRAZIONE
        try {
            Stage stage = (Stage) tornaLoginButton.getScene().getWindow();
            CambioScena cambioScena = new CambioScena();
            cambioScena.cambioScena(stage, PAGINA_PRINCIPALE);

        } catch (EccezioneGenerica eccezioneGenerica) {
            System.out.println(eccezioneGenerica.getMessage());
        }
    }
}