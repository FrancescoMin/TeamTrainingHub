package controllerApplicativo;

import engineering.bean.*;
import engineering.dao.UtenteDAO;
import engineering.dao.UtenteDAOJSON;
import engineering.dao.UtenteDAOMySQL;
import engineering.eccezioni.EccezzioneGenerica;
import engineering.eccezioni.UtenteNonEsistenteEccezione;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import modelli.Giocatore;
import modelli.Login;
import modelli.Registrazione;
import modelli.Utente;
import viste.first.RegistrazioneCtrlGrafico;

import static viste.first.utils.FxmlFileName.PAGINA_REGISTRAZIONE;

public class LoginControllerApplicativo {

    public LoginControllerApplicativo() {
    }

    public void verificaCredenziali(LoginBean loginBean) {

        //controllo delle credenziali
        try {

            //creo un utente da passare all'interno del sistema
            Login login = new Login(loginBean.getEmail(), loginBean.getPassword());

            //decidere il metodo di scelta del DAO
            UtenteDAO utenteDao = new UtenteDAOJSON();

            Utente utente = utenteDao.recuperaUtenteDaLogin(login);

            //creazione del bean da passare al prossimo controllore grafico con tutti i dati dell'utente
            GenericoBean genericoBean;
            if (utente.getAllenatore()) {
                genericoBean = new AllenatoreBean(utente.getEmail(), utente.getEmail(), utente.getAllenamenti(), utente.getSquadra());
            } else
            {
                genericoBean = new GiocatoreBean(utente.getEmail(), utente.getEmail(), utente.getAllenamenti(), utente.getSquadra());
            }

            //cambio di scena passando il bean appena creato


        }

        catch (Exception e)
        {
            throw new UtenteNonEsistenteEccezione(e.getMessage());
        }
    }

    public void cambioScenaRegistrazione(Stage stage) throws EccezzioneGenerica {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource(PAGINA_REGISTRAZIONE));

            Parent root = loader.load();

            RegistrazioneCtrlGrafico controller = loader.getController();

            controller.inizializzazioneTemp();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void creazioneUtente(LoginBean loginBean) {
        //ci occupiamo di inizializzare tutti le entità che fanno parte del caso d'uso

        //creazione dell'utente

        //richiedo al DB i dati dei singoli allenamenti inerenti all'utente

        //creazione dell'allenamento


        //Allenamento allenamento = new Allenamento("12/12/2020", "1h");


        //aggiunta dell'allenamento selezionato all'utente per ogni allenamento nel DB per quell'utente

    }

}
