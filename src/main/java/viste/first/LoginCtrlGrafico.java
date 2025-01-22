package viste.first;

import engineering.bean.UtenteBean;
import engineering.bean.LoginBean;
import engineering.eccezioni.EccezioneGenerica;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;

import java.io.IOException;

import ctrlApplicativo.LoginCtrlApplicativo;
import javafx.stage.Stage;
import viste.first.utils.CambioScena;

import static viste.first.utils.FxmlFileName.*;

public class LoginCtrlGrafico {

    @FXML
    private Button login;

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private Button registr;

    @FXML
    private Label erroreInserimento;

    @FXML
    private void initialize() {
        erroreInserimento.setVisible(false);
    }

    @FXML
    protected void userLogin() throws IOException {
        String em = email.getText().trim();           //accesso con email
        String pass = password.getText().trim();


        if (em.isEmpty() || pass.isEmpty()) {
            erroreInserimento.setText("There are empty fields!");
            erroreInserimento.setVisible(true);
        }

        else {
            LoginCtrlApplicativo loginCtrlApplicativo = new LoginCtrlApplicativo();
            try{
                //istanziazione del bean per il login
                LoginBean loginBean = new LoginBean(em,pass);

                //creazione del controler applicativo per il login

                //passaggio del bean al controller applicativo per il controllo delle credenziali
                if(!loginCtrlApplicativo.verificaCredenziali(loginBean)){
                    throw new EccezioneGenerica("Credenziali errate");
                }

                //creazione del bean utente generico in funzione dei dati del bean di login
                UtenteBean utenteBean = loginCtrlApplicativo.recuperoUtente(loginBean);

                Stage stage = (Stage) email.getScene().getWindow();
                CambioScena cambioScena = new CambioScena();

                //codice temporaneo per il cambio di scena alla home page del giocatore o allenatore
                if (utenteBean.getAllenatore()) {
                    cambioScena.cambioScena(stage, PAGINA_HOME_ALLENATORE);
                } else {
                    cambioScena.cambioScena(stage, PAGINA_HOME_GIOCATORE);
                }


            } catch (Exception e){
                erroreInserimento.setText(e.getMessage());
                erroreInserimento.setVisible(true);

            }
        }
    }

    @FXML
    protected void registrazione(ActionEvent event) throws IOException {
        //implemento di cambio di scena all pagina di registrazione


        //CODICE TEMPORANEO PER IL PASSAGGIO DI SCENE ALLA PAGINA DI REGISTRAZIONE
        try {
            Stage stage = (Stage) email.getScene().getWindow();
            CambioScena cambioScena = new CambioScena();
            cambioScena.cambioScena(stage, PAGINA_REGISTRAZIONE);

        } catch (EccezioneGenerica eccezioneGenerica) {
            System.out.println(eccezioneGenerica.getMessage());
        }
    }
    public static class CreazioneAllenamentoControllerGrafico {
    }
}