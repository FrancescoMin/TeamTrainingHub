package engineering.dao;

import com.google.gson.*;
import engineering.eccezioni.EccezzioneGenerica;
import engineering.eccezioni.UtenteNonEsistenteEccezione;
import modelli.Allenatore;
import modelli.Giocatore;
import modelli.Login;
import modelli.Utente;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;


public class UtenteDAOJSON implements UtenteDAO
{

    public void handleDAOException(Exception e) {
        System.out.println("Errore gestito in UtenteDAOJSON: " + e);
    }

    public void inserisciUtente(Utente utente)
    {}

    public Utente recuperaUtenteDaLogin(Login login) throws UtenteNonEsistenteEccezione//throws UserDoesNotExistException
    {
        try
        {
            //Serializziamo l'oggetto in JSON
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            //creazione del path
            String filePath = "src/main/resources/persistenza/utenti/"+login.getUsername()+".json";

            //dato il path del file, leggo il file JSON
            String jsonString = new String(Files.readAllBytes(Paths.get(filePath)));
            JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);

            //inizializzo un oggetto di tipo Login per il controllo delle credenziali
            Login deserializedLogin = new Login(jsonObject.get("username").getAsString(), jsonObject.get("password").getAsString());

            //controllo della password
            if (deserializedLogin.getPassword().equals(login.getPassword()))
            {
                System.out.println("Login effettuato con successo");

                //mi faccio recuperare dal metodo addetto l'utente che è acceduto
                Utente utente = caricaUtente(login.getUsername());
                return utente;
            }

            else
            {
                System.out.println("Password errata: lancio eccezione di password errata");
                throw new UtenteNonEsistenteEccezione("");
            }
        }

        catch (UtenteNonEsistenteEccezione | IOException e)
        {
            throw new UtenteNonEsistenteEccezione("Username o password non corretti");
        }

    }


    public Utente caricaUtente(String string)  /*UserDoesNotExistException*/
    {
        try {
            //Serializziamo l'oggetto in JSON
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            //creazione del path
            String filePath = "src/main/resources/persistenza/utenti/" + string + ".json";

            String jsonString = new String(Files.readAllBytes(Paths.get(filePath)));
            JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);


            //faccio il controllo che l'untente sia un allenatore o un giocatore
            if (jsonObject.get("allenatore").getAsBoolean()) {
                System.out.println("L'utente è un allenatore");
                Utente utente = new Allenatore(jsonObject.get("username").getAsString(), jsonObject.get("email").getAsString());
                return utente;
            } else {
                Utente utente = new Giocatore(jsonObject.get("username").getAsString(), jsonObject.get("email").getAsString());
                return utente;
            }
        }

        catch(IOException e)
        {
            //gestione dell'eccezione
            System.out.println("Errore di stream I/O");

            return null;
        }
    }

        //public String getPasswordByEmail(String email);
    //public boolean checkIfUserExistsByEmail(String email);
}
