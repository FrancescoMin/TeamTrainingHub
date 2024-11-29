package engineering.dao;

import engineering.bean.RegistrazioneBean;
import engineering.eccezioni.EccezzioneGenerica;
import engineering.eccezioni.UtenteNonEsistenteEccezione;
import modelli.Login;
import modelli.Registrazione;
import modelli.Utente;

public interface UtenteDAO
{

    public void inserisciUtente(Registrazione registrazione) throws EccezzioneGenerica;//throws EmailAlreadyInUseException, UsernameAlreadyInUseException;
    public Utente recuperaUtenteDaEmail(String string);//throws UserDoesNotExistException;
    public Utente recuperaUtenteDaLogin(Login login) throws UtenteNonEsistenteEccezione; /* throws UserDoesNotExistException;*/
    public void handleDAOException(Exception e) throws  EccezzioneGenerica;
}
