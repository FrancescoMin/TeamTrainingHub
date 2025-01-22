package engineering.dao;

import engineering.eccezioni.EccezioneGenerica;
import engineering.eccezioni.UtenteNonEsistenteEccezione;
import modelli.Login;
import modelli.Registrazione;
import modelli.Utente;

public interface UtenteDAO
{

    public void inserisciUtenteDaRegistrazione(Registrazione registrazione) throws EccezioneGenerica;
    public Utente recuperaUtenteDaEmail(String string);
    public void aggiornaUtente(Utente utente) throws EccezioneGenerica;
    public Utente recuperaUtenteDaLogin(Login login) throws UtenteNonEsistenteEccezione;
    public void handleDAOException(Exception e) throws  EccezioneGenerica;
    public Boolean esisteUtenteDaEmail(String email) throws EccezioneGenerica;
    public Boolean esisteUtenteDaLogin(Login login) throws EccezioneGenerica;
}
