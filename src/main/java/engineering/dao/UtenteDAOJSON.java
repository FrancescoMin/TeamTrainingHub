package engineering.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import modelli.Allenatore;
import modelli.Login;
import modelli.Utente;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class UtenteDAOJSON implements UtenteDAO
{
    public static class MyDataObject
    {
        private String name;
        private int value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    public void insertClient(Utente utente)  /*throws EmailAlreadyInUseException, UsernameAlreadyInUseException*/
    {}

    public Utente recuperaUtenteDaLoginBean(Login login) //throws UserDoesNotExistException
    {
        return null;
    }

    public Utente loadClient(Utente utente)  /*UserDoesNotExistException*/
    {
        Utente root = new Allenatore("Simone","Root");
        return root;
    }

    public void handleDAOException(Exception e)
    {
        System.out.println("Eccezione: " + e);
    }

        //public String getPasswordByEmail(String email);
    //public boolean checkIfUserExistsByEmail(String email);
}
