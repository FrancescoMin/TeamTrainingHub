package ctrl_applicativo;

import engineering.eccezioni.EccezioneUtenteInvalido;
import engineering.pattern.Memoria;
import modelli.Utente;

public class HomepageGiocatoreCtrlApplicativo {

    public HomepageGiocatoreCtrlApplicativo() {
        // Costruttore vuoto di default
    }

    public String ottieniMessaggioBenvenuto() throws EccezioneUtenteInvalido {
        // Recupera l'utente corrente dalla sessione
        Memoria istanza = Memoria.getInstance();
        Utente utenteCorrente = istanza.getUtenteCorrente();

        if (utenteCorrente == null) {
            throw new EccezioneUtenteInvalido("Nessun utente loggato nella sessione corrente.");
        }
        return utenteCorrente.getUsername() + "!";
    }

    public boolean utenteHaSquadra() {
        // Recupera l'utente corrente dalla sessione
        Memoria istanza = Memoria.getInstance();
        Utente utenteCorrente = istanza.getUtenteCorrente();

        return !utenteCorrente.getSquadra().getNome().isEmpty();
    }
}
