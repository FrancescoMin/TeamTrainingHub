package ctrlApplicativo;

import engineering.bean.RegistrazioneBean;
import engineering.dao.UtenteDAO;
import engineering.eccezioni.EccezioneUtenteInvalido;
import engineering.pattern.Singleton;
import engineering.pattern.abstract_factory.DAOFactory;
import modelli.Registrazione;

public class RegistrazioneCtrlApplicativo {

    public RegistrazioneCtrlApplicativo() {
        //costruttore vuoto di default
    }

    public void inserisciUtente(RegistrazioneBean registrazioneBean) throws EccezioneUtenteInvalido {
        Registrazione registrazione = getRegistrazione(registrazioneBean);


        //controllo che l'utente esisti già nel singleton
        Singleton istanza = Singleton.getInstance();
        if (istanza.esisteUtenteDaRegistrazione(registrazione)) {
            throw new EccezioneUtenteInvalido("Utente già registrato!");
        }

        //aggiungo l'utente al singleton
        istanza.aggiungiRegistrazione(registrazione);

        //se non siamo nella modalità demo, inserisco l'utente nella persistenza
        if(!istanza.getDemo()) {
            try {
                //creo il DAO per l'inserimento dell'utente
                UtenteDAO UtenteDAO = DAOFactory.getDAOFactory().createUtenteDAO();

                //compio l'inserimento dell'utente nella persistenza
                UtenteDAO.inserisciUtenteDaRegistrazione(registrazione);

            } catch (EccezioneUtenteInvalido e) {
                throw new EccezioneUtenteInvalido(e.getMessage());
            }
        }
    }

    private Registrazione getRegistrazione(RegistrazioneBean registrazioneBean) {
        String username = registrazioneBean.getUsername();
        String email = registrazioneBean.getEmail();
        String password = registrazioneBean.getPassword();
        boolean isAllenatore = registrazioneBean.getAllenatore();

        // Verifica che tutti i campi siano compilati
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            throw new EccezioneUtenteInvalido("Tutti i campi sono obbligatori!");
        }

        // Creazione del modello registrazione
        return new Registrazione(username, email, password, isAllenatore);
    }
}