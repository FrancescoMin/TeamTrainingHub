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

    public Utente recuperaUtenteDaLoginBean(Login login) throws UtenteNonEsistenteEccezione//throws UserDoesNotExistException
    {
        try
        {
            //Serializziamo l'oggetto in JSON
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            //creazione del path
            String filePath = "src/main/resources/persistenza/utenti/"+login.getEmail()+".json";


            //dato il path del file, leggo il file JSON
            String jsonString = new String(Files.readAllBytes(Paths.get(filePath)));
            JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);

            //inizializzo un oggetto di tipo Login per il controllo delle credenziali
            Login deserializedLogin = new Login(jsonObject.get("email").getAsString(), jsonObject.get("password").getAsString());

            //controllo della password
            if (deserializedLogin.getPassword().equals(login.getPassword()))
            {
                System.out.println("Login effettuato con successo");

                //faccio il controllo che l'untente sia un allenatore o un giocatore
                if (jsonObject.get("allenatore").getAsBoolean())
                {
                    System.out.println("L'utente è un allenatore");
                    Utente utente = new Allenatore(jsonObject.get("username").getAsString(), jsonObject.get("email").getAsString());
                    return utente;
                }
                else
                {
                    Utente utente = new Giocatore(jsonObject.get("username").getAsString(), jsonObject.get("email").getAsString());
                    return utente;
                }
            }

             /*

            //accesso al file
            FileReader reader = new FileReader(filePath);

            //Serializzimo l'oggetto in JSON
            Login deserializedLogin = gson.fromJson(reader, Login.class);


            //controllo della password
            if (deserializedLogin.getPassword().equals(login.getPassword()))
            {
                System.out.println("Login effettuato con successo");

                //inizializzo un utente e lo restituisco al controller applicativo

                //inizio recuperando tutte le informazioni dell'utente dal file JSON
                Utente recuperatore = gson.fromJson(reader , Utente.class);
                System.out.println("Prova di stampa"  +recuperatore.getUsername());

                //controllo che sia un allenatore o meno
                if (recuperatore.getAllenatore()) {

                    System.out.println("L'utente è un allenatore");

                    //se lo è, istanzio un allenatore
                    Utente utente = new Allenatore(recuperatore.getUsername(), recuperatore.getEmail());



                    //alla fine restituisco al controller applicativo l'allenatore trovato
                    return utente;
                }

                //altrimenti istanzio un giocatore
                else
                {
                    Utente utente = new Giocatore(recuperatore.getUsername(), recuperatore.getEmail());

                    //alla fine restituisco al controller applicativo il giocatore trovato
                    return utente;
                }

            }

              */
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
