package engineering.dao;


import engineering.eccezioni.UtenteNonEsistenteEccezione;
import modelli.Login;
import modelli.Utente;

public class UtenteDAOMySQL implements UtenteDAO {

    public void insertClient(Utente utente)
    {}//throws EmailAlreadyInUseException, UsernameAlreadyInUseException;

    public Utente loadClient(Utente utente)
    {return null;}//throws UserDoesNotExistException;


    public Utente recuperaUtenteDaLoginBean(Login login) throws  UtenteNonEsistenteEccezione{
        return null;
    }/* throws UserDoesNotExistException;*/


    public void handleDAOException(Exception e) {}
}
