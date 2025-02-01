package engineering.dao;

import engineering.eccezioni.EccezioneSquadraInvalida;
import engineering.eccezioni.EccezioneUtenteInvalido;
import modelli.Login;
import modelli.Registrazione;
import modelli.Utente;

public interface UtenteDAO
{

    public void inserisciUtenteDaRegistrazione(Registrazione registrazione) throws EccezioneUtenteInvalido;
    public Utente recuperaUtenteDaEmail(String string) throws EccezioneUtenteInvalido, EccezioneSquadraInvalida;
    public Utente recuperaUtenteDaLogin(Login login) throws EccezioneUtenteInvalido, EccezioneSquadraInvalida;
    public void aggiornaUtente(Utente utente) throws EccezioneUtenteInvalido; ;
    public Boolean esisteUtenteDaEmail(String email) throws EccezioneUtenteInvalido;
    public Boolean esisteUtenteDaLogin(Login login) throws EccezioneUtenteInvalido;
}

