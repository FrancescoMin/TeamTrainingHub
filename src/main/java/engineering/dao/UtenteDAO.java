package engineering.dao;

import engineering.eccezioni.UtenteNonEsistenteEccezione;
import modelli.Login;
import modelli.Utente;

public interface UtenteDAO
{

    public void inserisciUtente(Utente utente);//throws EmailAlreadyInUseException, UsernameAlreadyInUseException;
    public Utente caricaUtente(String string);//throws UserDoesNotExistException;
    public Utente recuperaUtenteDaLogin(Login login) throws UtenteNonEsistenteEccezione; /* throws UserDoesNotExistException;*/
    public void handleDAOException(Exception e);
}
