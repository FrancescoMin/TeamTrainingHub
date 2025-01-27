package ctrlApplicativo;

import engineering.dao.UtenteDAO;
import engineering.eccezioni.EccezioneGenerica;
import engineering.pattern.Singleton;
import engineering.pattern.abstract_factory.DAOFactory;
import modelli.Utente;

public class HomepageGiocatoreCtrlApplicativo {

    private final UtenteDAO utenteDAO;

    public HomepageGiocatoreCtrlApplicativo() {
        // Inizializza il DAO tramite la factory
        this.utenteDAO = DAOFactory.getDAOFactory().createUtenteDAO();
    }

    /**
     * Recupera il messaggio di benvenuto per l'utente corrente.
     *
     * @return Messaggio di benvenuto con il nome utente.
     * @throws EccezioneGenerica Se l'utente corrente non è disponibile.
     */
    public String getMessaggioBenvenuto() throws EccezioneGenerica {
        // Recupera l'utente corrente dalla sessione
        Singleton istanza = Singleton.getInstance();
        Utente utenteCorrente = istanza.getUtenteCorrente();

        if (utenteCorrente == null) {
            throw new EccezioneGenerica("Nessun utente loggato nella sessione corrente.");
        }

        return utenteCorrente.getUsername() + "!";
    }
}
