package engineering.dao;

import engineering.eccezioni.EccezzioneGenerica;
import modelli.Login;
import modelli.Utente;

public interface UtenteDAO
{

    public void insertClient(Utente utente);//throws EmailAlreadyInUseException, UsernameAlreadyInUseException;
    public Utente loadClient(Utente utente);//throws UserDoesNotExistException;


    //public String getPasswordByEmail(String email);
    //public boolean checkIfUserExistsByEmail(String email);


    public void recuperaUtenteDaLoginBean(Login login) throws EccezzioneGenerica; /* throws UserDoesNotExistException;*/
    public void handleDAOException(EccezzioneGenerica e);
}
