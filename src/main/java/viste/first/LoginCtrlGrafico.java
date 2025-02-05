package viste.first;

import engineering.bean.UtenteBean;
import engineering.bean.LoginBean;
import engineering.eccezioni.EccezioneCambioScena;
import engineering.eccezioni.EccezionePasswordErrata;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;

import java.io.IOException;

import ctrl_applicativo.LoginCtrlApplicativo;
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
            setErroreInserimento("There are empty fields!");

        }

        else {
            LoginCtrlApplicativo loginctrlapplicativo = new LoginCtrlApplicativo();
            try{
                //istanziazione del bean per il login
                LoginBean loginbean = new LoginBean(em,pass);

                //creazione del controler applicativo per il login

                //passaggio del bean al controller applicativo per il controllo delle credenziali
                if(!loginctrlapplicativo.verificaCredenziali(loginbean)){
                    throw new EccezionePasswordErrata("Credenziali errate");
                }

                //creazione del bean utente generico in funzione dei dati del bean di login
                UtenteBean utenteBean = loginctrlapplicativo.recuperoUtente(loginbean);

                Stage stage = (Stage) email.getScene().getWindow();
                CambioScena cambioScena = new CambioScena();

                //codice temporaneo per il cambio di scena alla home page del giocatore o allenatore
                if (utenteBean.getAllenatore()) {
                    cambioScena.cambioScena(stage, PAGINA_HOME_ALLENATORE);
                } else {
                    cambioScena.cambioScena(stage, PAGINA_HOME_GIOCATORE);
                }


            } catch (EccezionePasswordErrata e){
                setErroreInserimento("Credenziali errate");

            }
            catch (EccezioneCambioScena e) {
                setErroreInserimento("Errore di cambio scena");
            }
        }
    }

    @FXML
    protected void registrazione() throws IOException {
        //implemento di cambio di scena all pagina di registrazione

        try {
            Stage stage = (Stage) email.getScene().getWindow();
            CambioScena cambioScena = new CambioScena();
            cambioScena.cambioScena(stage, PAGINA_REGISTRAZIONE);
        }
        catch (EccezioneCambioScena e) {
            setErroreInserimento("Errore di cambio scena");
        }
    }

    public void setErroreInserimento(String errore) {
        // Imposta il testo della Label
        erroreInserimento.setText(errore);

        // Cambia il colore del testo della Label in un colore che contrasta bene con il verde (#1DB954)
        erroreInserimento.setStyle("-fx-text-fill: blue; -fx-font-size: 16px;"); // Bianco, ma puoi usare un altro colore che ti piace
        erroreInserimento.setVisible(true);
    }

}