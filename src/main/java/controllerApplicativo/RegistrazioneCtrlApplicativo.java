package controllerApplicativo;

import engineering.bean.RegistrazioneBean;
import engineering.dao.UtenteDAO;
import engineering.eccezioni.EccezioneGenerica;
import engineering.pattern.Singleton;
import engineering.pattern.abstract_factory.DAOFactory;
import modelli.Allenatore;
import modelli.Giocatore;
import modelli.Registrazione;
import modelli.Utente;

public class RegistrazioneCtrlApplicativo {

    public void inserisciUtente(RegistrazioneBean registrazioneBean) throws Exception {
        String username = registrazioneBean.getUsername();
        String email = registrazioneBean.getEmail();
        String password = registrazioneBean.getPassword();
        boolean isAllenatore = registrazioneBean.getAllenatore();

        // Verifica che tutti i campi siano compilati
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            throw new Exception("Tutti i campi sono obbligatori!");
        }

        // Creazione del modello registrazione
        Registrazione registrazione = new Registrazione(username, email, password, isAllenatore);

        //controllo che l'utente esisti già nel singleton
        Singleton istanza = Singleton.getInstance();
        if (istanza.esisteUtenteDaRegistrazione(registrazione)) {
            throw new Exception("Utente già registrato!");
        }

        // Utilizzo del DAO per salvare l'utente
        UtenteDAO UtenteDAO = DAOFactory.getDAOFactory().createUtenteDAO();

        //aggiungo l'utente al singleton e alla persistenza
        try {
            UtenteDAO.inserisciUtenteDaRegistrazione(registrazione);
            istanza.aggiungiRegistrazione(registrazione);

        } catch (EccezioneGenerica e) {
            throw new Exception(e.getMessage());
        }
    }
}