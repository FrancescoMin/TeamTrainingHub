package controllerApplicativo;

import engineering.bean.*;
import engineering.dao.UtenteDAO;
import engineering.dao.UtenteDAOJSON;
import engineering.dao.UtenteDAOMySQL;
import engineering.pattern.Singleton;
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








            //decidere il metodo di scelta del DAO
            UtenteDAO utenteDao = new UtenteDAOJSON();








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






        //decidere il metodo di scelta del DAO
        //UtenteDAO utenteDao = new UtenteDAOJSON();
        UtenteDAO utenteDao = new UtenteDAOJSON();







        //vedo se l'utente esiste nel singleton
        Singleton istanza=Singleton.getInstance();
        Login login = new Login(loginBean.getEmail(), loginBean.getPassword());
        //se esiste nel singleton, lo recupero e lo restituisco
        if(istanza.esisteUtenteDaLogin(login)) {

            Utente utente= istanza.getUtenteDaLogin(new Login(loginBean.getEmail(), loginBean.getPassword()));
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
            Utente utente = utenteDao.recuperaUtenteDaLogin(new Login(loginBean.getEmail(), loginBean.getPassword()));

            //creazione del bean utente in funzioni che sia un allenatore o un giocatore
            if (utente.getAllenatore()) {

                Utente allenatore = new Allenatore(utente.getUsername(), utente.getEmail(), utente.getPassword() , utente.getAllenamenti(), utente.getSquadra());
                istanza.aggiungiUtente(allenatore);

                //restituzione del bean dell'allenatore creato
                return new AllenatoreBean(utente.getUsername(), utente.getEmail(), utente.getPassword() , utente.getAllenamenti(), utente.getSquadra());
            }

            else {

                Utente giocatore = new Giocatore(utente.getUsername(), utente.getEmail(), utente.getPassword() , utente.getAllenamenti(), utente.getSquadra());
                istanza.aggiungiUtente(giocatore);

                //restituzione del bean del giocatore creato
                return new GiocatoreBean(utente.getUsername(), utente.getEmail(), utente.getPassword(), utente.getAllenamenti(), utente.getSquadra());
            }
        }
    }

}
