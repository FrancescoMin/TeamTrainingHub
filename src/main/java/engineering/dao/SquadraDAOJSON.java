package engineering.dao;

import com.google.gson.*;
import engineering.eccezioni.EccezioneSquadraInvalida;
import engineering.eccezioni.EccezioneUtenteInvalido;
import modelli.*;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static engineering.dao.UtenteDAOJSON.json;

public class SquadraDAOJSON implements SquadraDAO {

    public static final  String ALLEN = "allenamenti";
    public static final  String RICHIESTE = "richiesteIngresso";
    public static final  String PATH_SQUADRE = "src/main/resources/persistenza/squadre/";

    public SquadraDAOJSON() {
        //costruttore vuoto di default
    }

    public void creaSquadraPerAllenatore(Utente utente, Squadra squadra) throws EccezioneUtenteInvalido, EccezioneSquadraInvalida {

        try {
            creaSquadra(squadra);
            iscrizioneUtenteASquadra(utente, squadra);
        }
        catch (EccezioneUtenteInvalido e) {
            throw new EccezioneUtenteInvalido(e.getMessage());
        }
        catch (EccezioneSquadraInvalida e) {
            throw new EccezioneSquadraInvalida(e.getMessage());
        }
    }

    public void creaSquadra(Squadra squadra) throws EccezioneSquadraInvalida {

        try {
            //Creazione del path
            String filePath = PATH_SQUADRE + squadra.getNome() + json;

            //chiamo la funzione per la corretta creazione dell'oggetto json
            JsonObject jsonObject = setUpSquadra(squadra);

            creazioneSquadra(jsonObject, filePath);


        } catch (EccezioneUtenteInvalido e) {
            throw new EccezioneSquadraInvalida(e.getMessage());
        }
    }

    public JsonObject setUpSquadra(Squadra squadra) throws EccezioneUtenteInvalido {

        try {
            JsonObject jsonObject = new JsonObject();

            System.out.println("inizio il settaggio della squadra");

            jsonObject.addProperty("allenatore", squadra.getAllenatore());
            System.out.println(squadra.getAllenatore());
            jsonObject.addProperty("nome", squadra.getNome());
            System.out.println(squadra.getNome());

            UtenteDAOJSON utenteDAOJSON = new UtenteDAOJSON();
            Utente allenatore = utenteDAOJSON.recuperaUtenteDaEmail(squadra.getAllenatore());

            for (Allenamento allenamento : allenatore.getAllenamenti()) {
                System.out.println(allenamento.getData() + "-" + allenamento.getOrarioInizio() + "-" + allenamento.getOrarioFine());
            }

            JsonArray jsonArrayAllenamenti = new JsonArray();
            for (Allenamento allenamento : allenatore.getAllenamenti()) {
                System.out.println(allenamento.getData() + "-" + allenamento.getOrarioInizio() + "-" + allenamento.getOrarioFine());
                jsonArrayAllenamenti.add(allenamento.getData() + "-" + allenamento.getOrarioInizio() + "-" + allenamento.getOrarioFine());
            }
            jsonObject.add(ALLEN, jsonArrayAllenamenti);

            JsonArray jsonArrayRichiesteIscrizione = new JsonArray();
            for (Utente utente1 : squadra.getRichiesteIngresso()) {
                jsonArrayRichiesteIscrizione.add(utente1.getEmail());
            }
            jsonObject.add(RICHIESTE, jsonArrayRichiesteIscrizione);
            return jsonObject;
        }
        catch (EccezioneUtenteInvalido e) {
            throw new EccezioneSquadraInvalida(e.getMessage());
        }
    }

    public void creazioneSquadra(JsonObject jsonObject, String filePath) throws EccezioneSquadraInvalida {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {

            //controllo che il file sia già esistente
            Files.readAllBytes(Paths.get(filePath));

            //se sono ancora qui vuol dire che il file esiste e lancio un'eccezione
            throw new EccezioneSquadraInvalida("Squadra esistente");

        } catch (IOException e) {

            try {//se arrivo qui vuol dire che sto facendo una modifica e il file esiste, quindi posso sovrascriverlo
                FileWriter writer = new FileWriter(filePath);

                //salvataggio dell'oggetto serializzato utente nel file json
                writer.write(gson.toJson(jsonObject));
                writer.close();
            }
            catch (IOException e2) {
                throw new EccezioneSquadraInvalida("Errore di creazione della squadra");
            }
        }

    }

    public void aggiornaSquadra(Squadra squadra) throws EccezioneSquadraInvalida {
        try {
            //Creazione del path
            String filePath = PATH_SQUADRE + squadra.getNome() + json;

            //chiamo la funzione per la corretta creazione dell'oggetto json
            JsonObject jsonObject = setUpSquadra(squadra);

            aggiornaSquadraJson(jsonObject, filePath);

        }
        catch (EccezioneSquadraInvalida e) {
            throw new EccezioneSquadraInvalida(e.getMessage());
        }
    }

    public void aggiornaSquadraJson(JsonObject jsonObject, String filePath) throws EccezioneSquadraInvalida {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {

            //controllo che il file sia già esistente
            Files.readAllBytes(Paths.get(filePath));

            //se arrivo qui vuol dire che sto facendo una modifica e il file esiste, quindi posso sovrascriverlo
            FileWriter writer = new FileWriter(filePath);

            //salvataggio dell'oggetto serializzato utente nel file json
            writer.write(gson.toJson(jsonObject));
            writer.close();

        } catch (IOException e) {

            //se arrivo qui vuol dire che la squadra non esiste quindi lancio un'eccezione
            throw new EccezioneSquadraInvalida("Squadra non esistente");
        }
    }

    public Squadra getSquadraDaNome(String nomeSquadra) throws EccezioneSquadraInvalida{
        try {
            //Serializziamo l'oggetto in JSON
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            //creazione del path
            String filePath = PATH_SQUADRE + nomeSquadra + json;

            System.out.println("SquadraDAOJSON: getSquadraDaNome: filePath: " + filePath);

            //Dato il path del file, leggo il file JSON. Se vieni lanciato un'eccezione, l'utente non esiste
            String jsonString = new String(Files.readAllBytes(Paths.get(filePath)));

            //creo l'oggetto JSON corrispondete all'utente con l'email passata
            JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);

            //istanzio gli allenamenti e la squadra dell'utente, se ce ne ha, per l'istanziazione dell'utente
            List<Utente> richiesteIngresso = recuperaUtentiPerJsonArray(jsonObject.get(RICHIESTE).getAsJsonArray());

            System.out.println("SquadraDAOJSON: getSquadraDaNome: richiesteIngresso: " + richiesteIngresso);

            return new Squadra(jsonObject.get("nome").getAsString(), jsonObject.get("allenatore").getAsString() , richiesteIngresso);

        }
        catch (IOException e) {
            throw new EccezioneSquadraInvalida("Squadra non esistente");
        }
        catch (EccezioneUtenteInvalido e) {
            throw new EccezioneSquadraInvalida(e.getMessage());
        }

    }

    public List<Utente> recuperaUtentiPerJsonArray(JsonArray jsonArray) throws EccezioneUtenteInvalido {
        UtenteDAOJSON utenteDAOJSON = new UtenteDAOJSON();
        List<Utente> utenti = new ArrayList<>();

        try {

            for (JsonElement jsonElement : jsonArray) {
                String email = jsonElement.getAsString();
                Utente utente = utenteDAOJSON.recuperaUtenteDaEmail(email);
                utenti.add(utente);
            }
            return utenti;
        }
        catch (EccezioneUtenteInvalido e) {
            throw new EccezioneUtenteInvalido(e.getMessage());
        }
    }

    public void iscrizioneUtenteASquadra(Utente utente, Squadra squadra) throws EccezioneUtenteInvalido {

        try {
            //poiché è il sistema a modificare i modelli e non il dao, non c'è bisogno di fare il setSquadra

            utente.setSquadra(squadra);

            UtenteDAOJSON utenteDAOJSON = new UtenteDAOJSON();
            utenteDAOJSON.aggiornaUtente(utente);

        }
        catch (EccezioneUtenteInvalido e) {
            throw new EccezioneUtenteInvalido(e.getMessage());
        }
    }

    public boolean verificaEsistenzaSquadra(String nomeSquadra) {

        //Creazione del path
        String filePath = PATH_SQUADRE + nomeSquadra + json;

        try {
            //controllo che il file sia già esistente
            Files.readAllBytes(Paths.get(filePath));

            //se sono ancora qui vuol dire che il file esiste e restituisco vero
            return true;

        } catch (IOException e) {
            return false;
        }

    }

}
