package engineering.dao;

import com.google.gson.*;
import engineering.eccezioni.EccezioneGenerica;
import modelli.*;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static engineering.dao.UtenteDAOJSON.json;

public class SquadraDAOJSON implements SquadraDAO {

    public static String allen = "allenamenti";
    public static String richieste = "richiesteIngresso";
    public static String pathSquadre = "src/main/resources/persistenza/squadre/";

    public SquadraDAOJSON() {
        //costruttore vuoto di default
    }

    public void creaSquadraPerAllenatore(Utente utente, Squadra squadra) {

        try {
            creaSquadra(squadra, utente);
            IscrizioneUtenteASquadra(utente, squadra);
        } catch (Exception e) {
            throw new EccezioneGenerica(e.getMessage());
        }
    }

    public void aggiungiAllenamentoASquadra(Squadra squadra, Allenamento allenamento) {
        try {
            modificaSquadra(squadra, null, allenamento);
        } catch (Exception e) {
            throw new EccezioneGenerica(e.getMessage());
        }
    }

    public void aggiungiRichiestaASquadra(Squadra squadra, Utente utente) {
        try {
            modificaSquadra(squadra, utente, null);
        } catch (Exception e) {
            throw new EccezioneGenerica(e.getMessage());
        }
    }

    public JsonObject setUpSquadra(Squadra squadra, Utente utente) {
        // Create a Gson object

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("allenatore", utente.getEmail());
        jsonObject.addProperty("nome", squadra.getNome());

        JsonArray jsonArrayAllenamenti = new JsonArray();
        JsonArray jsonArrayRichiesteIscrizione = new JsonArray();

        jsonObject.add(allen, jsonArrayAllenamenti);
        jsonObject.add(richieste, jsonArrayRichiesteIscrizione);

        return jsonObject;
    }


    public void creaSquadra(Squadra squadra, Utente utente) {

        try {
            //Creazione del path
            String filePath = pathSquadre + squadra.getNome() + json;

            //chiamo la funzione per la corretta creazione dell'oggetto json
            JsonObject jsonObject = setUpSquadra(squadra, utente);

            creazioneSquadra(jsonObject, filePath);


        } catch (Exception e) {
            throw new EccezioneGenerica(e.getMessage());
        }
    }

    public void modificaSquadra(Squadra squadra, Utente utente, Allenamento allenamento) throws EccezioneGenerica {
        try {
            //Serializziamo l'oggetto in JSON
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            //creazione del path
            String filePath = pathSquadre + squadra.getNome() + json;

            //dato il path del file, leggo il file JSON. Se vieni lanciato un'eccezione, l'utente non esiste
            String jsonString = new String(Files.readAllBytes(Paths.get(filePath)));

            //creo l'oggetto JSON corrispondete all'utente con l'email passata
            JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);

            //ci sono 3 casi:
            //caso 1: sto aggiungendo un allenamento
            if (utente == null && allenamento != null) {
                jsonObject.get("allenamenti").getAsJsonArray().add(allenamento.getData() + "-" + allenamento.getOrarioInizio() + "-" + allenamento.getOrarioFine());
            } else if (utente != null && allenamento == null) {
                jsonObject.get(richieste).getAsJsonArray().add(utente.getEmail());
            }

            //deserializzo l'oggetto in stringa json
            String json = gson.toJson(jsonObject);

            //creazione del file con nome username dell'utente in formato json
            FileWriter writer = new FileWriter(filePath);

            //salvataggio dell'oggetto serializzato utente nel file json
            writer.write(json);
            writer.close();

        } catch (Exception e) {
            //gestione dell'eccezione
            System.out.println("Errore di stream I/O");
            throw new EccezioneGenerica(e.getMessage());
        }
    }

    public Squadra getSquadraDaNome(String nomeSquadra) {
        try {
            //Serializziamo l'oggetto in JSON
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            //creazione del path
            String filePath = pathSquadre + nomeSquadra + json;

            //dato il path del file, leggo il file JSON. Se vieni lanciato un'eccezione, l'utente non esiste
            String jsonString = new String(Files.readAllBytes(Paths.get(filePath)));

            //creo l'oggetto JSON corrispondete all'utente con l'email passata
            JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);

            //istanzio gli allenamenti e la squadra dell'utente, se ce ne ha, per l'istanziazione dell'utente
            List<Utente> richiesteIngresso = recuperaUtentiPerJsonArray(jsonObject.get(richieste).getAsJsonArray());

            return new Squadra(jsonObject.get("nome").getAsString(), jsonObject.get("allenatore").getAsString() , richiesteIngresso);


        } catch (IOException e) {
            throw new EccezioneGenerica(e.getMessage());
        }

    }


    public List<Utente> recuperaUtentiPerJsonArray(JsonArray jsonArray) {
        UtenteDAOJSON utenteDAOJSON = new UtenteDAOJSON();
        List<Utente> utenti = new ArrayList<>();

        for (JsonElement jsonElement : jsonArray) {
            String email = jsonElement.getAsString();
            Utente utente = utenteDAOJSON.recuperaUtenteDaEmail(email);
            utenti.add(utente);
        }
        return utenti;
    }

    public void IscrizioneUtenteASquadra(Utente utente, Squadra squadra) {

        try {
            System.out.println("Iscrizione dell'utente " + utente.getEmail() + "alla squadra: " + squadra.getNome());
            //poiché è il sistema a modificare i modelli e non il dao, non c'è bisogno di fare il setSquadra

            UtenteDAOJSON utenteDAOJSON = new UtenteDAOJSON();
            utenteDAOJSON.aggiornaUtente(utente);

        } catch (Exception e) {
            System.out.println("Errore nell'iscrizione dell'utente alla squadra");
        }
    }

    public void visualizzaTutteLeSquadre() {
        System.out.println("Visualizzazione di tutte le squadre");
    }


    public Boolean verificaEsistenzaSquadra(String nomeSquadra) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        //Creazione del path
        String filePath = pathSquadre + nomeSquadra + json;

        try {
            //controllo che il file sia già esistente
            Files.readAllBytes(Paths.get(filePath));

            //se sono ancora qui vuol dire che il file esiste e restituisco vero
            return true;

        } catch (Exception e) {
            return false;
        }

    }

    public void creazioneSquadra(JsonObject jsonObject, String filePath) throws Exception {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {

            //controllo che il file sia già esistente
            Files.readAllBytes(Paths.get(filePath));

            //se sono ancora qui vuol dire che il file esiste e lancio un'eccezione
            throw new EccezioneGenerica("Squadra esistente");

        } catch (Exception e) {

            //se arrivo qui vuol dire che sto facendo una modifica e il file esiste, quindi posso sovrascriverlo
            FileWriter writer = new FileWriter(filePath);

            //salvataggio dell'oggetto serializzato utente nel file json
            writer.write(gson.toJson(jsonObject));
            writer.close();
        }
    }

}
