package ctrl_applicativo;

import engineering.eccezioni.EccezioneUtenteInvalido;
import engineering.pattern.Singleton;
import modelli.Utente;

public class HomepageGiocatoreCtrlApplicativo {

    public HomepageGiocatoreCtrlApplicativo() {
        // Costruttore vuoto di default
    }

    public String getMessaggioBenvenuto() throws EccezioneUtenteInvalido {
        // Recupera l'utente corrente dalla sessione
        Singleton istanza = Singleton.getInstance();
        Utente utenteCorrente = istanza.getUtenteCorrente();

        if (utenteCorrente == null) {
            throw new EccezioneUtenteInvalido("Nessun utente loggato nella sessione corrente.");
        }
        return utenteCorrente.getUsername() + "!";
    }

    public boolean isUtenteInSquadra() {
        // Recupera l'utente corrente dalla sessione
        Singleton istanza = Singleton.getInstance();
        Utente utenteCorrente = istanza.getUtenteCorrente();

        return !utenteCorrente.getSquadra().getNome().isEmpty();
    }
}
