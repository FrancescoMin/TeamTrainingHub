package viste.first;

import controllerApplicativo.RegistrazioneCtrlApplicativo;
import engineering.bean.RegistrazioneBean;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import java.net.URL;
import java.util.ResourceBundle;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        registratiButton.setOnAction(event -> handleRegistratiButton());
        tornaLoginButton.setOnAction(event -> handleTornaLoginButton());
    }

    public void inizializzazioneTemp()
    {
        System.out.println("Inizializzazione Temporanea");
    }

    private void handleRegistratiButton() {
        String username = usernameTextField.getText();
        String email = emailTextField.getText();
        String password = passwordTextField.getText();
        String confermaPassword = confermaPasswordTextField.getText();
        boolean isAllenatore = allenatoreCheckBox.isSelected();

        if (password.equals(confermaPassword)) {
            RegistrazioneBean registrazioneBean = new RegistrazioneBean(username, email, password, isAllenatore);
            RegistrazioneCtrlApplicativo registrazioneCtrlApplicativo = new RegistrazioneCtrlApplicativo();
            try {
                registrazioneCtrlApplicativo.inserisciUtente(registrazioneBean);
                System.out.println("Registrazione avvenuta con successo!");
            } catch (Exception e) {
                System.out.println("Errore nella registrazione: " + e.getMessage());
            }
        } else {
            System.out.println("Le password non coincidono!");
        }
    }

    private void handleTornaLoginButton() {
        // Logica per tornare alla schermata di login
        System.out.println("Torna al login");
    }
}