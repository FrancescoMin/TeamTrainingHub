package ctrl_applicativo;

import engineering.bean.*;
import engineering.dao.UtenteDAO;
import engineering.eccezioni.EccezionePasswordErrata;
import engineering.eccezioni.EccezioneUtenteInvalido;
import engineering.pattern.Memoria;
import engineering.pattern.abstract_factory.DAOFactory;
import modelli.Login;
import modelli.Utente;

public class LoginCtrlApplicativo {

    public LoginCtrlApplicativo() {
        //costruttore vuoto di default
    }

    public boolean verificaCredenziali(LoginBean loginbean) {
        try {
            //controllo delle credenziali
            //creo un utente da passare all'interno del sistema
            Login login = new Login(loginbean.getEmail(), loginbean.getPassword());

            //vedo se l'utente esiste nel singleton
            Memoria istanza = Memoria.getInstance();

            //controllo che l'utente esiste nel singleton
            if (istanza.esisteUtenteDaEmail(login.getEmail())) {
                return true;
            }

            //se siamo nella modalità demo, non devo controllare le credenziali dalla persistenza
            else if (istanza.getDemo()) {
                return false;
            }
            else {
                //creazione del dao per controllare le credenziali
                UtenteDAO utenteDao = DAOFactory.getDAOFactory().createUtenteDAO();

                //recupero l'utente dal login e lo restituisco
                return utenteDao.esisteUtenteDaEmail(login.getEmail());
            }
        }
        catch (EccezioneUtenteInvalido e) {

            return false;
        }
    }

    public UtenteBean recuperoUtente(LoginBean loginbean) throws EccezionePasswordErrata {
        try {

            UtenteDAO utenteDao = DAOFactory.getDAOFactory().createUtenteDAO();

            //vedo se l'utente esiste nel singleton
            Memoria istanza= Memoria.getInstance();

            Login login = new Login(loginbean.getEmail(), loginbean.getPassword());
            //se esiste nel singleton, lo recupero e lo restituisco
            if(istanza.esisteUtenteDaLogin(login)) {

                //inizializzo il modello all'interno del sistema per l'utilizzo
                Utente utente= istanza.getUtenteDaLogin(new Login(loginbean.getEmail(), loginbean.getPassword()));

                //salvo all'interno del singleton l'utente con utenteCorrente
                if (utente.getAllenatore()) {
                    return new AllenatoreBean(utente.getUsername(), utente.getEmail(), utente.getPassword() , utente.getAllenamenti(), utente.getSquadra());
                }
                else {
                    return new GiocatoreBean(utente.getUsername(), utente.getEmail(), utente.getPassword(), utente.getAllenamenti(), utente.getSquadra());
                }
            }

            //Se siamo nella modalità demo non dovremmo arrivare a questo punto del codice. Per sicurezza alzo un eccezione
            else if(istanza.getDemo()){
                throw new EccezioneUtenteInvalido("Impossibile recuperare l'utente dallo stato di persistenza visto che siamo nella modalità demo");}

            //richiediamo l'utente dalla persistenza
            else {
                //creazione del modello utente
                //creo una nuova istanza di utente che contiene l'utente che fa uso del sistema
                Utente utente = utenteDao.recuperaUtenteDaLogin(new Login(loginbean.getEmail(), loginbean.getPassword()));

                //salvo all'interno del singleton l'utente come utenteCorrente
                istanza.setUtenteCorrente(utente);

                //salvo l'utente all'interno del singleton nella lista di utenti che hanno fatto uso del sistema
                istanza.aggiungiUtente(utente);

                //creazione del bean utente in funzioni che sia un allenatore o un giocatore
                if (utente.getAllenatore()) {

                    //restituzione del bean dell'allenatore creato
                    return new AllenatoreBean(utente.getUsername(), utente.getEmail(), utente.getPassword(), utente.getAllenamenti(), utente.getSquadra());
                } else {

                    //restituzione del bean del giocatore creato
                    return new GiocatoreBean(utente.getUsername(), utente.getEmail(), utente.getPassword(), utente.getAllenamenti(), utente.getSquadra());
                }
            }
        }
        catch (Exception e) {
            throw new EccezionePasswordErrata(e.getMessage());
        }
    }

}
