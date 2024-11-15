package viste.first;

import engineering.bean.LoginBean;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;

import java.io.IOException;

import controllerApplicativo.LoginControllerApplicativo;

public class LoginControllerGrafico {
    @FXML
    private Button login;

    @FXML
    private RadioButton demo;

    @FXML
    private RadioButton full;

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private Button signUp;

    @FXML
    private Label erroreInserimento;

    @FXML
    private void initialize() {
        ToggleGroup group = new ToggleGroup();
        demo.setToggleGroup(group);
        full.setToggleGroup(group);
        full.setSelected(true);
    }



    @FXML
    protected void userLogin(ActionEvent event) throws IOException {
        String em = email.getText().trim();           //accesso con email
        String pass = password.getText().trim();

        if (em.isEmpty() || pass.isEmpty()) {
            erroreInserimento.setText("There are empty fields!");
            erroreInserimento.setVisible(true);
        }

        else if(demo.isSelected())
        {
            System.out.println("Demo");
        }

        else
        {
            try{
                //passaggio del login e password al bean per il settaggio del controller applicativo
                LoginBean loginBean = new LoginBean(em,pass);

                //passaggio del bean al controller applicativo per il controllo delle credenziali
                LoginControllerApplicativo loginControllerApplicativo = new LoginControllerApplicativo();
                loginControllerApplicativo.verificaCredenziali(loginBean);

                //passaggio del bean al controller applicativo per l'istanziazione dei modelli e dei bean
                loginControllerApplicativo.creazioneUtente(loginBean);


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