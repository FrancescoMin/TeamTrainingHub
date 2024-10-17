package inizio;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;


public class ProvaController {
    @FXML
    private Button login;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button signUp;

    @FXML
    private Label erroreInserimento;

    @FXML
    protected void userLogin(ActionEvent event) throws IOException {
        String email = username.getText().trim();
        String pass = password.getText().trim();
        if (email.isEmpty() || pass.isEmpty()) {
            erroreInserimento.setText("There are empty fields!");
            erroreInserimento.setVisible(true);
        }

        else
        {
            try{
                //passaggio del login e password al bean per il settaggio del controller applicativo
                //passaggio del bean al controller applicativo per il controllo delle credenziali
                //passaggio del bean al controller applicativo per il caricamento dell'istanza di userBean
                //passaggio dell'istanza di userBean alla home page
            } catch (Exception e){
                erroreInserimento.setText(e.getMessage());
                erroreInserimento.setVisible(true);
            }
        }
    }

    @FXML
    protected void userSignUp(ActionEvent event) throws IOException {
        System.out.println("SignUp");
        //implemento di cambio di scena all pagina di registrazione
    }
}