package engineering.dao;

import engineering.bean.RegistrazioneBean;
import engineering.eccezioni.EccezioneGenerica;
import engineering.eccezioni.UtenteNonEsistenteEccezione;
import modelli.Login;
import modelli.Registrazione;
import modelli.Utente;

public interface UtenteDAO
{

    public void inserisciUtenteDaRegistrazione(Registrazione registrazione) throws EccezioneGenerica;//throws EmailAlreadyInUseException, UsernameAlreadyInUseException;
    public Utente recuperaUtenteDaEmail(String string);//throws UserDoesNotExistException;
    public Utente recuperaUtenteDaLogin(Login login) throws UtenteNonEsistenteEccezione; /* throws UserDoesNotExistException;*/
    public void handleDAOException(Exception e) throws  EccezioneGenerica;
    public Boolean esisteUtenteDaEmail(String email) throws EccezioneGenerica;
    public Boolean esisteUtenteDaLogin(Login login) throws EccezioneGenerica;
}
