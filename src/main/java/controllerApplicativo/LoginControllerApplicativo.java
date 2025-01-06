package controllerApplicativo;

import engineering.bean.*;
import engineering.dao.UtenteDAO;
import engineering.dao.UtenteDAOJSON;
import engineering.dao.UtenteDAOMySQL;
import engineering.pattern.Singleton;
import engineering.pattern.abstract_factory.DAOFactory;
import modelli.Allenatore;
import modelli.Giocatore;
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
            UtenteDAO utenteDao = DAOFactory.getDAOFactory().createUtenteDAO();



            //vedo se l'utente esiste nel singleton
            Singleton istanza=Singleton.getInstance();

            //se esiste ritorno true
            if(istanza.esisteUtenteDaLogin(login)) {
                return true;}

            else {
                System.out.println("Login in corso");
                //recupero l'utente dal login e lo restituisco
                return utenteDao.esisteUtenteDaLogin(login);
            }
        }

        catch (Exception e)
        {
            //se l'utente non esiste, lancio un'eccezione per comunicarlo al controller grafico
            return false;
        }
    }

    public UtenteBean recuperoUtente(LoginBean loginBean) {

        UtenteDAO utenteDao = DAOFactory.getDAOFactory().createUtenteDAO();


        //vedo se l'utente esiste nel singleton
        Singleton istanza=Singleton.getInstance();
        Login login = new Login(loginBean.getEmail(), loginBean.getPassword());
        //se esiste nel singleton, lo recupero e lo restituisco
        if(istanza.esisteUtenteDaLogin(login)) {

            //inizializzo il modello all'interno del sistema per l'utilizzo
            Utente utente= istanza.getUtenteDaLogin(new Login(loginBean.getEmail(), loginBean.getPassword()));

            //salvo all'interno del singleton l'utente con utenteCorrente
            istanza.setUtenteCorrente(utente);
            if (utente.getAllenatore()) {
                return new AllenatoreBean(utente.getUsername(), utente.getEmail(), utente.getPassword() , utente.getAllenamenti(), utente.getSquadra());
            }
            else {
                return new GiocatoreBean(utente.getUsername(), utente.getEmail(), utente.getPassword(), utente.getAllenamenti(), utente.getSquadra());
            }
        }

        else {
            //creazione del modello utente
            System.out.println("Recupero l'utente "+ loginBean.getEmail() +" con password "+ loginBean.getPassword() + "dalla persistenza");

            //creo una nuova istanza di utente che contiene l'utente che fa uso del sistema
            Utente utente = utenteDao.recuperaUtenteDaLogin(new Login(loginBean.getEmail(), loginBean.getPassword()));

            //salvo all'interno del singleton l'utente come utenteCorrente
            istanza.setUtenteCorrente(utente);

            //salvo l'utente all'interno del singleton nella lista di utenti che hanno fatto uso del sistema
            istanza.aggiungiUtente(utente);

            //creazione del bean utente in funzioni che sia un allenatore o un giocatore
            if (utente.getAllenatore()) {

                //restituzione del bean dell'allenatore creato
                return new AllenatoreBean(utente.getUsername(), utente.getEmail(), utente.getPassword() , utente.getAllenamenti(), utente.getSquadra());
            }

            else {

                //restituzione del bean del giocatore creato
                return new GiocatoreBean(utente.getUsername(), utente.getEmail(), utente.getPassword(), utente.getAllenamenti(), utente.getSquadra());
            }
        }
    }

}
