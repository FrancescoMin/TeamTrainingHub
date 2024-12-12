package controllerApplicativo;

import engineering.bean.*;
import engineering.dao.UtenteDAO;
import engineering.dao.UtenteDAOJSON;
import engineering.eccezioni.UtenteNonEsistenteEccezione;
import engineering.pattern.Singleton;
import modelli.Login;
import modelli.Utente;

public class LoginControllerApplicativo {

    public LoginControllerApplicativo() {
    }

    public Boolean verificaCredenziali(LoginBean loginBean) {

        //controllo delle credenziali
        try {

            //creo un utente da passare all'interno del sistema
            Login login = new Login(loginBean.getEmail(), loginBean.getPassword());

            //decidere il metodo di scelta del DAO
            UtenteDAO utenteDao = new UtenteDAOJSON();

            //vedo se l'utente esiste nel singleton
            Singleton istanza=Singleton.getInstance();
            if(istanza.esisteUtenteDaLogin(login.getEmail(), login.getPassword()))
            {return true;}

            else {
                //recupero l'utente dal login
                return utenteDao.esisteUtenteDaLogin(login);
            }
        }

        catch (Exception e)
        {
            //se l'utente non esiste, lancio un'eccezione per comunicarlo al controller grafico
            return false;
        }
    }

    public UtenteBean creazioneUtente(LoginBean loginBean) {

        //decidere il metodo di scelta del DAO
        UtenteDAO utenteDao = new UtenteDAOJSON();

        //creazione del modello utente
        Utente utente = utenteDao.recuperaUtenteDaLogin(new Login(loginBean.getEmail(), loginBean.getPassword()));

        //creazione del bean utente in funzioni che sia un allenatore o un giocatore
        if (utente.getAllenatore()) {

            //restituzione del bean dell'allenatore creato
            return new AllenatoreBean(utente.getUsername(), utente.getEmail(), utente.getPassword() , utente.getAllenamenti(), utente.getSquadra());
        } else
        {
            //restituzione del bean del giocatore creato
            return new GiocatoreBean(utente.getUsername(), utente.getEmail(), utente.getPassword(), utente.getAllenamenti(), utente.getSquadra());
        }
    }

}
