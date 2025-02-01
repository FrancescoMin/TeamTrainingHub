package engineering.dao;

import com.google.gson.*;
import engineering.eccezioni.EccezioneSquadraInvalida;
import engineering.eccezioni.EccezioneUtenteInvalido;
import modelli.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;


public class UtenteDAOJSON implements UtenteDAO {

    public static String json = ".json";
    public static String pathUtenti = "src/main/resources/persistenza/utenti/";
    public static String password = "password";
    public static String email1 = "email";
    public static String user = "username";

    public static String trainings = "allenamenti";
    public static String squadra = "squadra";
    public static String allenatore = "allenatore";


    public Boolean esisteUtenteDaLogin(Login login) throws EccezioneUtenteInvalido {
        try {
            return esisteUtenteDaEmail(login.getEmail());
        }
        catch (EccezioneUtenteInvalido e) {
            throw new EccezioneUtenteInvalido(e.getMessage());
        }
    }
    public Boolean esisteUtenteDaEmail(String email) throws EccezioneUtenteInvalido {
        try {
            //creazione del path
            String filePath = pathUtenti + email + json;

            //controllo se il file esiste
            Files.readAllBytes(Paths.get(filePath));
            return true;

        } catch (IOException e) {
            throw new EccezioneUtenteInvalido("Utente non esistente");
        }
    }

    public void aggiornaUtente(Utente utente) throws EccezioneUtenteInvalido{
        //aggiunta dell'utente alla lista degli utenti
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

        } catch (IOException e) {
            //creazione del file con nome username dell'utente in formato json
            throw new EccezioneUtenteInvalido("utente non esistente");
        }
    }

    public void inserisciUtente(Utente utente) throws EccezioneUtenteInvalido {

        //Creazione del path
        String filePath = pathUtenti + utente.getEmail() + json;

        try {
            //controllo che il file sia già esistente
            Files.readAllBytes(Paths.get(filePath));

            //se il file esiste, un utente con la stessa email esiste già e lancio un'eccezione
            throw new EccezioneUtenteInvalido("utente esistente");

        }
        catch (IOException e) {
            try {
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
            catch (IOException e1) {
                throw new EccezioneUtenteInvalido(e1.getMessage());
            }
        }
        catch (EccezioneUtenteInvalido e) {
            throw new EccezioneUtenteInvalido(e.getMessage());
        }
    }
    //creazione del file json con i dati dell'utente registrato
    public void inserisciUtenteDaRegistrazione(Registrazione registrazione) throws EccezioneUtenteInvalido {
        try {
            if (registrazione.getAllenatore()) {inserisciUtente(new Allenatore(registrazione.getUsername(), registrazione.getEmail(), registrazione.getPassword()));}

            else {inserisciUtente(new Giocatore(registrazione.getUsername(), registrazione.getEmail(), registrazione.getPassword()));}
        }
        catch (EccezioneUtenteInvalido e) {
            throw new EccezioneUtenteInvalido(e.getMessage());
        }
    }

    public Utente recuperaUtente(Utente utente) throws EccezioneUtenteInvalido{
        try {
            return recuperaUtenteDaEmail(utente.getEmail());
        }
        catch (EccezioneUtenteInvalido e) {
            throw new EccezioneUtenteInvalido(e.getMessage());
        }
    }
    public Utente recuperaUtenteDaLogin(Login login) throws EccezioneUtenteInvalido {
        try {
            //Serializziamo l'oggetto in JSON
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            //creazione del path
            String filePath = pathUtenti + login.getEmail() + json;

            //Dato il path del file, leggo il file JSON. Se vieni lanciato un'eccezione, l'utente non esiste
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
                throw new EccezioneUtenteInvalido("Password errata: lancio eccezione di password errata");
            }
        }
        catch (EccezioneUtenteInvalido e) {
            throw new EccezioneUtenteInvalido(e.getMessage());
        }
        catch (IOException e) {
            throw new EccezioneUtenteInvalido("Utente non esistente");
        }
        catch(EccezioneSquadraInvalida e) {
            throw new EccezioneSquadraInvalida(e.getMessage());
        }

    }
    public Utente recuperaUtenteDaEmail(String email) throws EccezioneUtenteInvalido, EccezioneSquadraInvalida {
        try {
            //Serializziamo l'oggetto in JSON
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            //creazione del path
            String filePath = pathUtenti + email + json;

            //Dato il path del file, leggo il file JSON. Se vieni lanciato un'eccezione, l'utente non esiste
            String jsonString = new String(Files.readAllBytes(Paths.get(filePath)));

            //creo l'oggetto JSON corrispondete all'utente con l'email passata
            JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);

            AllenamentoDAOJSON allenamentoDAOJSON = new AllenamentoDAOJSON();
            //istanzio gli allenamenti e la squadra dell'utente, se ce ne ha, per l'istanziazione dell'utente
            List<Allenamento> allenamenti = allenamentoDAOJSON.recuperaAllenamentiPerJsonArray(jsonObject.get(trainings).getAsJsonArray());

            //ottengo il nome della stringa della squadra
            String nomeSquadra = jsonObject.get(squadra).getAsString();
            Squadra squad = new Squadra();

            if(!nomeSquadra.isEmpty()){
                //mi faccio recuperare dal DAO responsabile l'oggetto squadra
                SquadraDAOJSON squadraDAOJSON = new SquadraDAOJSON();
                squad = squadraDAOJSON.getSquadraDaNome(nomeSquadra);
            }

            //faccio il controllo che l'utente sia un allenatore o un giocatore
            if (jsonObject.get(allenatore).getAsBoolean()) {
                return new Allenatore(jsonObject.get(user).getAsString(), jsonObject.get(email1).getAsString(), jsonObject.get(password).getAsString(), allenamenti, squad);

            } else {
                return new Giocatore(jsonObject.get(user).getAsString(), jsonObject.get(email1).getAsString(), jsonObject.get(password).getAsString(), allenamenti, squad);
            }

        } catch (IOException e) {
            //gestione dell'eccezione nel caso in cui non troviamo l'utente
            throw new EccezioneUtenteInvalido("Utente non esistente");
        }
        catch (EccezioneSquadraInvalida e){
            throw new EccezioneSquadraInvalida(e.getMessage());
        }

    }

    public static JsonObject serializzazioneUtente(Utente utente) {

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

        if (Objects.equals(utente.getSquadra().getNome(), "")) {
            System.out.println("Squadra vuota");
            jsonObject.addProperty(squadra, "");
        } else {
            jsonObject.addProperty(squadra, utente.getSquadra().getNome());
        }

        return jsonObject;
    }
}
