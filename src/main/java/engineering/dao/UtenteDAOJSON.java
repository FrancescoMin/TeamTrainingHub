package engineering.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import engineering.eccezioni.EccezzioneGenerica;
import modelli.Allenatore;
import modelli.Login;
import modelli.Utente;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class UtenteDAOJSON implements UtenteDAO
{

    public void handleDAOException(EccezzioneGenerica e) {
        System.err.println("Errore gestito in UtenteDAOJSON: " + e.getMessage());
        e.printStackTrace();
    }

    public void recuperaUtenteDaLoginBean(Login login) throws EccezzioneGenerica//throws UserDoesNotExistException
    {
        try {
            if (login.getPassword().equals("Simone"))
            {
                throw new EccezzioneGenerica("Eccezione generica");
            }
            else
            {
                System.out.println("Password diversa da Simone");
            }
        } catch (EccezzioneGenerica e) {
            handleDAOException(e);
        }
    }



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



    public Utente loadClient(Utente utente)  /*UserDoesNotExistException*/
    {
        Utente root = new Allenatore("Simone","Root");
        return root;
    }

        //public String getPasswordByEmail(String email);
    //public boolean checkIfUserExistsByEmail(String email);
}
