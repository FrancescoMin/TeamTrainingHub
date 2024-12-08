package controllerApplicativo;

import engineering.bean.*;
import engineering.dao.UtenteDAO;
import engineering.dao.UtenteDAOJSON;
import engineering.eccezioni.EccezzioneGenerica;
import engineering.eccezioni.UtenteNonEsistenteEccezione;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import modelli.Login;
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

            //Se non è stata lanciata un'eccezione, l'utente esiste e quindi è possibile accedere
        }

        catch (Exception e)
        {
            //se l'utente non esiste, lancio un'eccezione per comunicarlo al controller grafico
            throw new UtenteNonEsistenteEccezione(e.getMessage());
        }
    }

    public UtenteBean creazioneUtente(LoginBean loginBean) {
        //ci occupiamo di inizializzare tutti le entità che fanno parte del caso d'uso

        //decidere il metodo di scelta del DAO
        UtenteDAO utenteDao = new UtenteDAOJSON();

        //creazione del modello utente
        Utente utente = utenteDao.recuperaUtenteDaLogin(new Login(loginBean.getEmail(), loginBean.getPassword()));

        //creazione del bean utente in funzioni che sia un allenatore o un giocatore
        if (utente.getAllenatore()) {

            //restituzione del bean dell'allenatore creato
            return new AllenatoreBean(utente.getEmail(), utente.getEmail(), utente.getAllenamenti(), utente.getSquadra());
        } else
        {
            //restituzione del bean del giocatore creato
            return new GiocatoreBean(utente.getEmail(), utente.getEmail(), utente.getAllenamenti(), utente.getSquadra());
        }
    }

}
