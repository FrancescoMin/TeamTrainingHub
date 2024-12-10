package engineering.dao;

import com.google.gson.*;
import engineering.eccezioni.EccezzioneGenerica;
import engineering.eccezioni.UtenteNonEsistenteEccezione;
import modelli.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class UtenteDAOJSON implements UtenteDAO
{

    public void handleDAOException(Exception e) throws EccezzioneGenerica{
        throw new EccezzioneGenerica(e.getMessage());
    }

    //creazione del file json con i dati dell'utente registrato
    public void inserisciUtenteDaRegistrazione(Registrazione registrazione)
    {
        try {
            //Creazione del path
            String filePath = "src/main/resources/persistenza/utenti/" + registrazione.getUsername() + ".json";

            try {
                //controllo che il file sia già esistente
                Files.readAllBytes(Paths.get(filePath));

                //se il file esiste, un utente con la stessa email esiste già e lancio un'eccezione
                throw new EccezzioneGenerica("utente esistente");

            } catch (IOException e) {
                //creazione del file con nome username dell'utente in formato json

                // Create a Gson object
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                FileWriter writer = new FileWriter(filePath);

                // Step 1: Serialize the Person object to a JSON string
                String jsonString = gson.toJson(registrazione);

                // Step 2: Convert the JSON string to a JsonObject
                JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();

                // Step 3: Add Squadra field to the JsonObject
                jsonObject.addProperty("Squadra", "");

                // Step 4: Create Allenamenti field array for java to the JsonObject
                JsonArray allenamenti = new JsonArray();

                // Step 5: Add Allenamenti to the JsonObject as an empty array
                jsonObject.add("allenamenti", allenamenti);

                //salvataggio dell'oggetto serializzato utente nel file json
                writer.write(gson.toJson(jsonObject));
                writer.close();
                throw new EccezzioneGenerica("utente inserito");
            }


        } catch (Exception e) {
            throw new EccezzioneGenerica(e.getMessage());
        }

    }

    public Utente recuperaUtenteDaLogin(Login login) throws UtenteNonEsistenteEccezione//throws UserDoesNotExistException
    {
        try
        {
            //Serializziamo l'oggetto in JSON
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            //creazione del path
            String filePath = "src/main/resources/persistenza/utenti/"+login.getEmail()+".json";

            //dato il path del file, leggo il file JSON. Se vieni lanciato un'eccezione, l'utente non esiste
            String jsonString = new String(Files.readAllBytes(Paths.get(filePath)));

            //creo un oggetto JSON per il controllo delle credenziali
            JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);

            //inizializzo un oggetto di tipo Login per il controllo delle credenziali
            Login deserializedLogin = new Login(jsonObject.get("email").getAsString(), jsonObject.get("password").getAsString());

            //controllo della password
            if (deserializedLogin.getPassword().equals(login.getPassword()))
            {
                System.out.println("Login effettuato con successo");

                //mi faccio recuperare dal metodo addetto l'utente e lo restituisco al controller
                return recuperaUtenteDaEmail(login.getEmail());
            }

            else
            {
                System.out.println("Password errata: lancio eccezione di password errata");
                throw new UtenteNonEsistenteEccezione("Password errata: lancio eccezione di password errata");
            }
        }

        catch (UtenteNonEsistenteEccezione e)
        {
            throw new EccezzioneGenerica(e.getMessage());
        }
        catch (IOException e)
        {
            throw new EccezzioneGenerica("Utente non esistente");
        }

    }


    public Utente recuperaUtenteDaEmail(String email)  throws EccezzioneGenerica
    {
        try {
            //Serializziamo l'oggetto in JSON
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            //creazione del path
            String filePath = "src/main/resources/persistenza/utenti/" + email + ".json";

            //dato il path del file, leggo il file JSON. Se vieni lanciato un'eccezione, l'utente non esiste
            String jsonString = new String(Files.readAllBytes(Paths.get(filePath)));

            //creo l'oggetto JSON corrispondete all'utente con l'email passata
            JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);

            //istanzio gli allenamenti e la squadra dell'utente, se ce ne ha, per l'istanziazione dell'utente
            List<Allenamento> allenamenti=recuperaAllenamentiPerJsonArray(jsonObject.get("allenamenti").getAsJsonArray());
            Squadra squadra = new Squadra(jsonObject.get("Squadra").getAsString());

            //faccio il controllo che l'utente sia un allenatore o un giocatore
            if (jsonObject.get("allenatore").getAsBoolean()) {
                System.out.println("L'utente è un allenatore");
                return new Allenatore(jsonObject.get("username").getAsString(), jsonObject.get("email").getAsString(), jsonObject.get("password").getAsString() , allenamenti ,squadra);

            } else {
                return new Giocatore(jsonObject.get("username").getAsString(), jsonObject.get("email").getAsString(), jsonObject.get("password").getAsString() , allenamenti ,squadra);
            }


        }

        catch(IOException e)
        {
            //gestione dell'eccezione
            System.out.println("Errore di stream I/O");
            throw new EccezzioneGenerica("Utente non esistente");
        }
    }

    public List<Allenamento> recuperaAllenamentiPerJsonArray(JsonArray jsonArray)
    {
        List<Allenamento> allenamenti = new ArrayList<>();

        for (int i = 0; i < jsonArray.size(); i++)
        {
            JsonObject allenamento = jsonArray.get(i).getAsJsonObject();
            allenamenti.add(new Allenamento(allenamento.get("data").getAsString(), allenamento.get("durata").getAsInt() , allenamento.get("tipo").getAsString()));
        }

        return allenamenti;
    }

}
