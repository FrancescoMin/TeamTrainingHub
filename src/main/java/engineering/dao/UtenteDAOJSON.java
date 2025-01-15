package engineering.dao;

import com.google.gson.*;
import engineering.eccezioni.EccezioneGenerica;
import engineering.eccezioni.UtenteNonEsistenteEccezione;
import modelli.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


public class UtenteDAOJSON implements UtenteDAO {

    public static String json = ".json";
    public static String pathUtenti = "src/main/resources/persistenza/utenti/";
    public static String password = "password";
    public static String email1 = "email";
    public static String user = "username";
    
    public static String trainings = "allenamenti";
    public static String squadra = "squadra";
    public static String allenatore = "allenatore";

    public void handleDAOException(Exception e) throws EccezioneGenerica {
        throw new EccezioneGenerica(e.getMessage());
    }

    public Boolean esisteUtenteDaLogin(Login login) {
        return esisteUtenteDaEmail(login.getEmail());
    }
    public Boolean esisteUtenteDaEmail(String email) {
        try {
            //creazione del path
            String filePath = pathUtenti + email + json;

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
            String filePath = pathUtenti + utente.getEmail() + json;

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

    public void inserisciUtenteDaUtente(Utente utente) throws EccezioneGenerica, IOException {

        //Creazione del path
        String filePath = pathUtenti + utente.getEmail() + json;

        try {
            //controllo che il file sia già esistente
            Files.readAllBytes(Paths.get(filePath));

            //se il file esiste, un utente con la stessa email esiste già e lancio un'eccezione
            throw new EccezioneGenerica("utente esistente");

        } catch (IOException e) {
            //creazione del file con nome username dell'utente in formato json

            // Create a Gson object
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            //serializzo l'oggetto java in un oggetto json
            JsonObject jsonObject = serializzazioneUtente(utente);

            //parso l'oggetto json in una stringa
            String serial = gson.toJson(jsonObject);

            //creazione del file con nome username dell'utente in formato json
            FileWriter writer = new FileWriter(filePath);

            //salvataggio dell'oggetto serializzato utente nel file json
            writer.write(serial);
            writer.close();
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
            String filePath = pathUtenti + login.getEmail() + json;

            //dato il path del file, leggo il file JSON. Se vieni lanciato un'eccezione, l'utente non esiste
            String jsonString = new String(Files.readAllBytes(Paths.get(filePath)));

            //creo un oggetto JSON per il controllo delle credenziali
            JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);

            //inizializzo un oggetto di tipo Login per il controllo delle credenziali
            Login deserializedLogin = new Login(jsonObject.get(email1).getAsString(), jsonObject.get(password).getAsString());

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
            String filePath = pathUtenti + email + json;

            //dato il path del file, leggo il file JSON. Se vieni lanciato un'eccezione, l'utente non esiste
            String jsonString = new String(Files.readAllBytes(Paths.get(filePath)));

            //creo l'oggetto JSON corrispondete all'utente con l'email passata
            JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);

            AllenamentoDAOJSON allenamentoDAOJSON = new AllenamentoDAOJSON();
            //istanzio gli allenamenti e la squadra dell'utente, se ce ne ha, per l'istanziazione dell'utente
            List<Allenamento> allenamenti = allenamentoDAOJSON.recuperaAllenamentiPerJsonArray(jsonObject.get(trainings).getAsJsonArray());

            //ottengo il nome della stringa della squadra
            String nomeSquadra = jsonObject.get(squadra).getAsString();

            //mi faccio recuperare dal DAO responsabile l'oggetto squadra
            SquadraDAOJSON squadraDAOJSON = new SquadraDAOJSON();
            Squadra squad = squadraDAOJSON.getSquadraDaNome(nomeSquadra);


            //faccio il controllo che l'utente sia un allenatore o un giocatore
            if (jsonObject.get(allenatore).getAsBoolean()) {
                return new Allenatore(jsonObject.get(user).getAsString(), jsonObject.get(email1).getAsString(), jsonObject.get(password).getAsString(), allenamenti, squad);

            } else {
                return new Giocatore(jsonObject.get(user).getAsString(), jsonObject.get(email1).getAsString(), jsonObject.get(password).getAsString(), allenamenti, squad);
            }

        } catch (IOException e) {
            //gestione dell'eccezione
            System.out.println("Errore di stream I/O");
            throw new EccezioneGenerica("Utente non esistente");
        } catch (Exception e) {
            throw new EccezioneGenerica(e.getMessage());
        }
    }

    public static JsonObject serializzazioneUtente(Utente utente) {

        System.out.println("Sono nell'adapter");

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(user, utente.getUsername());
        jsonObject.addProperty(email1, utente.getEmail());
        jsonObject.addProperty(password, utente.getPassword());
        jsonObject.addProperty(allenatore, utente.getAllenatore());

        JsonArray jsonArray = new JsonArray();

        if (utente.getAllenamenti() == null) {
            jsonObject.add(trainings, jsonArray);

        } else if (utente.getAllenamenti().isEmpty()) {
            jsonObject.add(trainings, jsonArray);
        } else {
            for (int i = 0; i < utente.getAllenamenti().size(); i++) {
                jsonArray.add(utente.getAllenamenti().get(i).getData() + "-" + utente.getAllenamenti().get(i).getOrarioInizio() + "-" + utente.getAllenamenti().get(i).getOrarioFine());
            }
            jsonObject.add(trainings, jsonArray);
        }

        if (utente.getSquadra() == null) {
            jsonObject.addProperty(squadra, "");
        } else {
            jsonObject.addProperty(squadra, utente.getSquadra().getNome());
        }

        return jsonObject;
    }
}
