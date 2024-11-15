package engineering.dao;

import engineering.eccezioni.UtenteNonEsistenteEccezione;
import modelli.Login;
import modelli.Utente;

public interface UtenteDAO
{

    public void insertClient(Utente utente);//throws EmailAlreadyInUseException, UsernameAlreadyInUseException;
    public Utente loadClient(Utente utente);//throws UserDoesNotExistException;
    public Utente recuperaUtenteDaLoginBean(Login login) throws UtenteNonEsistenteEccezione; /* throws UserDoesNotExistException;*/
    public void handleDAOException(Exception e);
}
