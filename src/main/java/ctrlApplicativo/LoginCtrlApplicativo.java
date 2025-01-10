package ctrlApplicativo;

import engineering.bean.*;
import engineering.dao.UtenteDAO;
import engineering.eccezioni.EccezioneGenerica;
import engineering.pattern.Singleton;
import engineering.pattern.abstract_factory.DAOFactory;
import modelli.Login;
import modelli.Utente;

public class LoginCtrlApplicativo {

    public LoginCtrlApplicativo() {
        //costruttore vuoto di default
    }

    public void setDemo(Boolean demo) {
        Singleton.getInstance().setDemo(demo);
    }

    public Boolean verificaCredenziali(LoginBean loginBean) {

        //controllo delle credenziali

        //creo un utente da passare all'interno del sistema
        Login login = new Login(loginBean.getEmail(), loginBean.getPassword());

        //vedo se l'utente esiste nel singleton
        Singleton istanza=Singleton.getInstance();

        //creazione del dao per controllare le credenziali
        UtenteDAO utenteDao = DAOFactory.getDAOFactory().createUtenteDAO();

        //controllo che l'utente esiste nel singleton
        if(istanza.esisteUtenteDaLogin(login)) {return true;}

        //se siamo nella modalità demo, non devo controllare le credenziali dalla persistenza
        else if(istanza.getDemo()){
        return false;}


        else {

            //recupero l'utente dal login e lo restituisco
            return utenteDao.esisteUtenteDaLogin(login);
        }

    }

    public UtenteBean recuperoUtente(LoginBean loginBean) {

        UtenteDAO utenteDao = DAOFactory.getDAOFactory().createUtenteDAO();


        //vedo se l'utente esiste nel singleton
        Singleton istanza=Singleton.getInstance();


        System.out.println("Valore booleano del singleton demo: " + istanza.getDemo());


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

        //se siamo nella modalità demo non dovremmo arrivare a questo punto del codice. Per sicurezza alzo un eccezione
        else if(istanza.getDemo()){
            throw new EccezioneGenerica("Impossibile recuperare l'utente dallo stato di persistenza visto che siamo nella modalità demo");}

        //richiediamo l'utente dalla persistenza
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
