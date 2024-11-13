package engineering.dao;


import engineering.eccezioni.EccezzioneGenerica;
import modelli.Allenatore;
import modelli.Login;
import modelli.Utente;

public class UtenteDAOMySQL implements UtenteDAO {

    public void insertClient(Utente utente)
    {}//throws EmailAlreadyInUseException, UsernameAlreadyInUseException;

    public Utente loadClient(Utente utente)
    {return null;}//throws UserDoesNotExistException;


    public void recuperaUtenteDaLoginBean(Login login) {}/* throws UserDoesNotExistException;*/


    public void handleDAOException(EccezzioneGenerica e) {}
}
