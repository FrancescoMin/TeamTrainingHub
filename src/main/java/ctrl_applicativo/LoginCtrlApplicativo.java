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
        // Costruttore vuoto di default
    }

    public boolean verificaCredenziali(LoginBean loginbean) {
        try {
            UtenteDAO utenteDao = DAOFactory.getDAOFactory().createUtenteDAO();
            return utenteDao.esisteUtenteDaEmail(loginbean.getEmail());
        } catch (EccezioneUtenteInvalido e) {
            return false;
        }
    }

    public UtenteBean recuperoUtente(LoginBean loginbean) throws EccezionePasswordErrata {
        try {
            UtenteDAO utenteDao = DAOFactory.getDAOFactory().createUtenteDAO();
            Login login = new Login(loginbean.getEmail(), loginbean.getPassword());

            Utente utente = utenteDao.recuperaUtenteDaLogin(login);

            // Salvataggio dell'utente autenticato nella sessione
            Memoria.getInstance().setUtenteCorrente(utente);

            if (utente.getAllenatore()) {
                return new AllenatoreBean(utente.getUsername(), utente.getEmail(), utente.getPassword(), utente.getAllenamenti(), utente.getSquadra());
            } else {
                return new GiocatoreBean(utente.getUsername(), utente.getEmail(), utente.getPassword(), utente.getAllenamenti(), utente.getSquadra());
            }
        } catch (EccezionePasswordErrata e) {
            throw new EccezionePasswordErrata(e.getMessage());
        } catch (Exception e) {
            throw new EccezionePasswordErrata("Credenziali errate: " + e.getMessage());
        }
    }
}