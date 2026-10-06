package ctrl_applicativo;

import engineering.bean.RegistrazioneBean;
import engineering.dao.UtenteDAO;
import engineering.eccezioni.EccezioneUtenteInvalido;
import engineering.pattern.abstract_factory.DAOFactory;
import modelli.Registrazione;

public class RegistrazioneCtrlApplicativo {

    public RegistrazioneCtrlApplicativo() {
        // Costruttore vuoto di default
    }

    public void inserisciUtente(RegistrazioneBean registrazionebean) throws EccezioneUtenteInvalido {
        Registrazione registrazione = ottieniRegistrazione(registrazionebean);

        UtenteDAO utenteDAO = DAOFactory.getDAOFactory().createUtenteDAO();

        if (utenteDAO.esisteUtenteDaEmail(registrazione.getEmail())) {
            throw new EccezioneUtenteInvalido("Utente già registrato!");
        }

        utenteDAO.inserisciUtenteDaRegistrazione(registrazione);
    }

    private Registrazione ottieniRegistrazione(RegistrazioneBean registrazionebean) {
        String username = registrazionebean.getUsername();
        String email = registrazionebean.getEmail();
        String password = registrazionebean.getPassword();
        boolean isAllenatore = registrazionebean.getAllenatore();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            throw new EccezioneUtenteInvalido("Tutti i campi sono obbligatori!");
        }

        return new Registrazione(username, email, password, isAllenatore);
    }
}