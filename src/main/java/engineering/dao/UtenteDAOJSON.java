package engineering.dao;

import com.google.gson.*;
//import engineering.altro.UtenteAdapter;
import engineering.eccezioni.EccezioneGenerica;
import engineering.eccezioni.UtenteNonEsistenteEccezione;
import modelli.*;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class UtenteDAOJSON implements UtenteDAO {

    public void handleDAOException(Exception e) throws EccezioneGenerica {
        throw new EccezioneGenerica(e.getMessage());
    }

    public Boolean esisteUtenteDaLogin(Login login) {
        return esisteUtenteDaEmail(login.getEmail());
    }

    public Boolean esisteUtenteDaEmail(String email) {
        try {
            //creazione del path
            String filePath = "src/main/resources/persistenza/utenti/" + email + ".json";

            //controllo se il file esiste
            Files.readAllBytes(Paths.get(filePath));
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public void aggiornaUtente(Utente utente) {
        //aggiunta dell'utente alla lista degli utenti
        try {
            //Creazione del path
            String filePath = "src/main/resources/persistenza/utenti/" + utente.getEmail() + ".json";

            try {
                //controllo che il file sia già esistente
                Files.readAllBytes(Paths.get(filePath));

                // Create a Gson object
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                FileWriter writer = new FileWriter(filePath);

                // Step 1: Serialize the Person object to a JSON string
                String jsonString = gson.toJson(serializzazioneUtente(utente));

                // Step 2: Convert the JSON string to a JsonObject
                JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();

                //salvataggio dell'oggetto serializzato utente nel file json
                writer.write(gson.toJson(jsonObject));
                writer.close();

                //se il file esiste, un utente con la stessa email esiste già e lancio un'eccezione

            } catch (IOException e) {
                //creazione del file con nome username dell'utente in formato json

                throw new EccezioneGenerica("utente non esistente");
            }
        } catch (Exception e) {
            throw new EccezioneGenerica(e.getMessage());
        }

    }

    public void inserisciUtenteDaUtente(Utente utente) {

        //aggiunta dell'utente alla lista degli utenti
        try {
            //Creazione del path
            String filePath = "src/main/resources/persistenza/utenti/" + utente.getEmail() + ".json";

            try {
                //controllo che il file sia già esistente
                Files.readAllBytes(Paths.get(filePath));

                //se il file esiste, un utente con la stessa email esiste già e lancio un'eccezione
                throw new EccezioneGenerica("utente esistente");

            } catch (IOException e) {
                //creazione del file con nome username dell'utente in formato json

                // Create a Gson object
                Gson gson = new GsonBuilder().setPrettyPrinting().create();

                JsonObject jsonObject = serializzazioneUtente(utente);

                String json = gson.toJson(jsonObject);

                FileWriter writer = new FileWriter(filePath);

                //salvataggio dell'oggetto serializzato utente nel file json
                writer.write(json);
                writer.close();
            }
        } catch (Exception e) {
            throw new EccezioneGenerica(e.getMessage());
        }
    }

    //creazione del file json con i dati dell'utente registrato
    public void inserisciUtenteDaRegistrazione(Registrazione registrazione) {
        try {
            if (registrazione.getAllenatore()) {
                inserisciUtenteDaUtente(new Allenatore(registrazione.getUsername(), registrazione.getEmail(), registrazione.getPassword()));
            } else {
                inserisciUtenteDaUtente(new Giocatore(registrazione.getUsername(), registrazione.getEmail(), registrazione.getPassword()));
            }

        } catch (Exception e) {
            throw new EccezioneGenerica(e.getMessage());
        }

    }

    public Utente recuperaUtenteDaLogin(Login login) throws UtenteNonEsistenteEccezione {
        try {
            //Serializziamo l'oggetto in JSON
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            //creazione del path
            String filePath = "src/main/resources/persistenza/utenti/" + login.getEmail() + ".json";

            //dato il path del file, leggo il file JSON. Se vieni lanciato un'eccezione, l'utente non esiste
            String jsonString = new String(Files.readAllBytes(Paths.get(filePath)));

            //creo un oggetto JSON per il controllo delle credenziali
            JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);

            //inizializzo un oggetto di tipo Login per il controllo delle credenziali
            Login deserializedLogin = new Login(jsonObject.get("email").getAsString(), jsonObject.get("password").getAsString());

            //controllo della password
            if (deserializedLogin.getPassword().equals(login.getPassword())) {
                System.out.println("Login effettuato con successo");

                //mi faccio recuperare dal metodo addetto l'utente e lo restituisco al controller
                return recuperaUtenteDaEmail(login.getEmail());

            } else {
                System.out.println("Password errata: lancio eccezione di password errata");
                throw new UtenteNonEsistenteEccezione("Password errata: lancio eccezione di password errata");
            }
        } catch (UtenteNonEsistenteEccezione e) {
            throw new EccezioneGenerica(e.getMessage());
        } catch (IOException e) {
            throw new EccezioneGenerica("Utente non esistente");
        }

    }

    public Utente recuperaUtenteDaEmail(String email) throws EccezioneGenerica {
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
            List<Allenamento> allenamenti = recuperaAllenamentiPerJsonArray(jsonObject.get("allenamenti").getAsJsonArray());

            System.out.println("Lalalalalal Stronzata");

            String nomeSquadra = jsonObject.get("squadra").getAsString();
            Squadra squadra = new Squadra(nomeSquadra);

            //faccio il controllo che l'utente sia un allenatore o un giocatore
            if (jsonObject.get("allenatore").getAsBoolean()) {
                System.out.println("L'utente è un allenatore");
                return new Allenatore(jsonObject.get("username").getAsString(), jsonObject.get("email").getAsString(), jsonObject.get("password").getAsString(), allenamenti, squadra);

            } else {
                return new Giocatore(jsonObject.get("username").getAsString(), jsonObject.get("email").getAsString(), jsonObject.get("password").getAsString(), allenamenti, squadra);
            }


        } catch (IOException e) {
            //gestione dell'eccezione
            System.out.println("Errore di stream I/O");
            throw new EccezioneGenerica("Utente non esistente");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public List<Allenamento> recuperaAllenamentiPerJsonArray(JsonArray jsonArray) {
        try {

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            List<Allenamento> allenamenti = new ArrayList<>();

            for (int i = 0; i < jsonArray.size(); i++) {

                String nomeAllenamento = jsonArray.get(i).getAsString();
                String filePath = "src/main/resources/persistenza/allenamenti/" + nomeAllenamento + ".json";
                String jsonString = new String(Files.readAllBytes(Paths.get(filePath)));

                //creo l'oggetto JSON corrispondete all'utente con l'email passata
                JsonObject allenamento = gson.fromJson(jsonString, JsonObject.class);

                allenamenti.add(new Allenamento(allenamento.get("data").getAsString(), allenamento.get("durata").getAsInt(), allenamento.get("descrizione").getAsString()));

            }

            return allenamenti;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }


    public static JsonObject serializzazioneUtente(Utente utente) {

        System.out.println("Sono nell'adapter");

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("username", utente.getUsername());
        jsonObject.addProperty("email", utente.getEmail());
        jsonObject.addProperty("password", utente.getPassword());
        jsonObject.addProperty("allenatore", utente.getAllenatore());

        JsonArray jsonArray = new JsonArray();

        if (utente.getAllenamenti() == null) {
            System.out.println("allenamento null");
            jsonObject.add("allenamenti", jsonArray);
        } else if (utente.getAllenamenti().isEmpty()) {
            System.out.println("allenamento vuoto");
            jsonObject.addProperty("allenamenti", "");
        } else {
            System.out.println("allenamento trovato");
            for (int i = 0; i < utente.getAllenamenti().size(); i++) {
                jsonArray.add(utente.getAllenamenti().get(i).getData());
            }
            jsonObject.add("allenamenti", jsonArray);
        }

        if (utente.getSquadra() == null) {
            System.out.println("Squadra null");
            jsonObject.addProperty("squadra", "");
        } else {
            System.out.println("Squadra non null");
            jsonObject.addProperty("squadra", utente.getSquadra().getNome());
        }

        return jsonObject;
    }

}
