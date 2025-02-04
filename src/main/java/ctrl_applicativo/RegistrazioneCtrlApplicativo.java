package ctrl_applicativo;

import engineering.bean.RegistrazioneBean;
import engineering.dao.UtenteDAO;
import engineering.eccezioni.EccezioneUtenteInvalido;
import engineering.pattern.Memoria;
import engineering.pattern.abstract_factory.DAOFactory;
import modelli.Registrazione;

public class RegistrazioneCtrlApplicativo {

    public RegistrazioneCtrlApplicativo() {
        //costruttore vuoto di default
    }

    public void inserisciUtente(RegistrazioneBean registrazionebean) throws EccezioneUtenteInvalido {
        Registrazione registrazione = getRegistrazione(registrazionebean);


        //controllo che l'utente esisti già nel singleton
        Memoria istanza = Memoria.getInstance();
        if (istanza.esisteUtenteDaRegistrazione(registrazione)) {
            throw new EccezioneUtenteInvalido("Utente già registrato!");
        }

        //aggiungo l'utente al singleton
        istanza.aggiungiRegistrazione(registrazione);

        //se non siamo nella modalità demo, inserisco l'utente nella persistenza
        if(!istanza.getDemo()) {
            try {
                //creo il DAO per l'inserimento dell'utente
                UtenteDAO utenteDAO = DAOFactory.getDAOFactory().createUtenteDAO();

                //compio l'inserimento dell'utente nella persistenza
                utenteDAO.inserisciUtenteDaRegistrazione(registrazione);

            } catch (EccezioneUtenteInvalido e) {
                throw new EccezioneUtenteInvalido(e.getMessage());
            }
        }
    }

    private Registrazione getRegistrazione(RegistrazioneBean registrazionebean) {
        String username = registrazionebean.getUsername();
        String email = registrazionebean.getEmail();
        String password = registrazionebean.getPassword();
        boolean isAllenatore = registrazionebean.getAllenatore();

        // Verifica che tutti i campi siano compilati
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            throw new EccezioneUtenteInvalido("Tutti i campi sono obbligatori!");
        }

        // Creazione del modello registrazione
        return new Registrazione(username, email, password, isAllenatore);
    }
}