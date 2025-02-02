package engineering.dao;

import com.google.gson.*;
import engineering.eccezioni.EccezioneGenerica;
import modelli.Allenamento;
import modelli.Squadra;
import modelli.Utente;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static engineering.dao.UtenteDAOJSON.json;

public class AllenamentoDAOJSON implements AllenamentoDAO {


    public void inserisciAllenamentoAdUtente(Allenamento allenamento, Utente utente) {
        try {

            //passo 2: aggiorno l'utente con l'allenamento nella lista degli allenamenti a cui è iscritto
            UtenteDAOJSON utenteDAOJSON = new UtenteDAOJSON();
            utenteDAOJSON.aggiornaUtente(utente);

            System.out.println("aggiornaUtente eseguito con successo inizio modificaSquadra");

            //passo 3: aggiorno la squadra dell'utente in modo che aggiungo alla squadra l'allenamento nella lista di allenamenti della squadra
            SquadraDAOJSON squadraDAOJSON = new SquadraDAOJSON();
            squadraDAOJSON.aggiornaSquadra(utente.getSquadra());
        }
        catch (Exception e) {
            throw new EccezioneGenerica(e.getMessage());
        }
    }

    public void creaAllenamentoPerSquadra(Allenamento allenamento, Squadra squadra) {

        //processo di ottenimento degli allenamenti relativi a una squadra
        try {
            List<Allenamento> allenamenti=ottieniAllenamentiPerSquadra(squadra);

            if (sovrapposizioneAllenamenti(allenamenti, allenamento)) {
                throw new EccezioneGenerica("Allenamento sovrapposto");
            }
        }
        catch (Exception e) {
            throw new EccezioneGenerica(e.getMessage());
        }

        System.out.println("nessun allenamento sovrapposto, procedo con la creazione dell'allenamento");

        //inizio il processo di scrittura in json dell'allenamento
        String path = allenamento.getData() + "-" + allenamento.getOrarioInizio() + "-" + allenamento.getOrarioFine();

        //aggiunta dell'allenamento alla lista degli allenamenti
        try {
            //Creazione del path
            String filePath = "src/main/resources/persistenza/allenamenti/" + path + json;

            creazioneAllenamento(allenamento, filePath);

        } catch (Exception e) {
            throw new EccezioneGenerica(e.getMessage());
        }
    }

    public void creazioneAllenamento(Allenamento allenamento, String filePath) throws IOException{
        try {
            //controllo che il file sia già esistente
            Files.readAllBytes(Paths.get(filePath));

            //se il file esiste, un allenamento con la stessa data esiste già e lancio un'eccezione
            throw new EccezioneGenerica("allenamento esistente");

        } catch (IOException e) {
            //creazione del file con nome username dell'utente in formato json

            // Create a Gson object
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            FileWriter writer = new FileWriter(filePath);

            // Step 1: Convert the Java object to a JSON string
            String jsonString = gson.toJson(allenamento);

            // Step 2: Convert the JSON string to a JsonObject
            JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();

            //salvataggio dell'oggetto serializzato utente nel file json
            writer.write(gson.toJson(jsonObject));
            writer.close();
        }
    }

    public List<Allenamento> ottieniAllenamentiPerSquadra(Squadra squadra) {
        //processo di ottenimento degli allenamenti relativi a una squadra
        try {

            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            //creazione del path
            String filePath = "src/main/resources/persistenza/squadre/" + squadra.getNome() + json;


            //dato il path del file, leggo il file JSON. Se vieni lanciato un'eccezione, la squadra non esiste
            String jsonString = new String(Files.readAllBytes(Paths.get(filePath)));

            //creo l'oggetto JSON corrispondete all'utente con l'email passata
            JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);

            return recuperaAllenamentiPerJsonArray(jsonObject.get("allenamenti").getAsJsonArray());

        }
        catch (Exception e) {
            throw new EccezioneGenerica(e.getMessage());
        }
    }

    public boolean sovrapposizioneAllenamenti(List<Allenamento> allenamenti, Allenamento allenamento) {

        for (Allenamento allenamentoCorrente : allenamenti)
        {

            //se la data è uguale, faccio il controllo pure sull'orario
            if (allenamentoCorrente.getData().equals(allenamento.getData())) {

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH-mm");
                LocalTime inizioAllenamento = LocalTime.parse(allenamento.getOrarioInizio(), formatter);
                LocalTime fineAllenamento = LocalTime.parse(allenamento.getOrarioFine(), formatter);
                LocalTime inizioCorrente = LocalTime.parse(allenamentoCorrente.getOrarioInizio(), formatter);
                LocalTime fineCorrente = LocalTime.parse(allenamentoCorrente.getOrarioFine(), formatter);

                // Confronta gli orari per vedere se si intersecano
                if (inizioAllenamento.isBefore(fineCorrente) && fineAllenamento.isAfter(inizioCorrente)) {
                    return true;
                }
            }
        }
        return false;
    }


    public List<Allenamento> recuperaAllenamentiPerJsonArray(JsonArray jsonArray) {
        try {

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            List<Allenamento> allenamenti = new ArrayList<>();

            for (int i = 0; i < jsonArray.size(); i++) {

                String path = jsonArray.get(i).getAsString();
                String filePath = "src/main/resources/persistenza/allenamenti/" + path + json;
                String jsonString = new String(Files.readAllBytes(Paths.get(filePath)));

                //creo l'oggetto JSON corrispondete all'utente con l'email passata
                JsonObject allenamento = gson.fromJson(jsonString, JsonObject.class);

                allenamenti.add(new Allenamento(allenamento.get("data").getAsString(),allenamento.get("orarioInizio").getAsString(), allenamento.get("orarioFine").getAsString() , allenamento.get("descrizione").getAsString()));

            }

            return allenamenti;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new EccezioneGenerica(e.getMessage());
        }
    }

    public List<Allenamento> getAllenamentiPerEmail(String email) {
        UtenteDAOJSON utenteDAOJSON = new UtenteDAOJSON();
        return getAllenamentiPerUtente(utenteDAOJSON.recuperaUtenteDaEmail(email));
    }

    public List<Allenamento> getAllenamentiPerUtente(Utente utente) {
        List<Allenamento> allenamenti= new ArrayList<>();

        UtenteDAOJSON utenteDAOJSON = new UtenteDAOJSON();
        Utente utenteCorrente = utenteDAOJSON.recuperaUtente(utente);

        allenamenti = utenteCorrente.getAllenamenti();

        return allenamenti;
    }

}
