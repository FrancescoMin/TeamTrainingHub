package engineering.dao;

import modelli.Login;
import modelli.Utente;

public interface UtenteDAO {

    public void insertClient(Utente utente);//throws EmailAlreadyInUseException, UsernameAlreadyInUseException;
    public Utente loadClient(Utente utente);//throws UserDoesNotExistException;


    //public String getPasswordByEmail(String email);
    //public boolean checkIfUserExistsByEmail(String email);


    public Utente recuperaUtenteDaLoginBean(Login login);/* throws UserDoesNotExistException;*/
    public void handleDAOException(Exception e);
}
