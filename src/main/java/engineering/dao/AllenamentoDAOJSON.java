package engineering.dao;

import com.google.gson.*;
import engineering.eccezioni.EccezioneAllenamentoInvalido;
import modelli.Allenamento;
import modelli.Utente;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static engineering.dao.UtenteDAOJSON.*;

public class AllenamentoDAOJSON implements AllenamentoDAO {


    public void inserisciAllenamentoAdUtente(Allenamento allenamento, Utente utente) throws EccezioneAllenamentoInvalido{
        try {

            //aggiorno l'utente con l'allenamento nella lista degli allenamenti a cui è iscritto
            UtenteDAOJSON utenteDAOJSON = new UtenteDAOJSON();
            utenteDAOJSON.aggiornaUtente(utente);

            System.out.println("aggiornaUtente eseguito con successo inizio modificaSquadra");

            //aggiorno la squadra dell'utente in modo che aggiungo alla squadra l'allenamento nella lista di allenamenti della squadra
            SquadraDAOJSON squadraDAOJSON = new SquadraDAOJSON();
            squadraDAOJSON.aggiornaSquadra(utente.getSquadra());
        }
        catch (Exception e) {
            throw new EccezioneAllenamentoInvalido(e.getMessage());
        }
    }

    public List<Allenamento> recuperaAllenamentiPerJsonArray(JsonArray jsonArray) throws EccezioneAllenamentoInvalido {
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
            throw new EccezioneAllenamentoInvalido("Errore nel recupero degli allenamenti");
        }
    }

    public List<Allenamento> getAllenamentiPerEmail(String email) throws EccezioneAllenamentoInvalido {
        try {
            //Serializziamo l'oggetto in JSON
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            //creazione del path
            String filePath = pathUtenti + email + json;

            //Dato il path del file, leggo il file JSON. Se vieni lanciato un'eccezione, l'utente non esiste
            String jsonString = new String(Files.readAllBytes(Paths.get(filePath)));

            //creo l'oggetto JSON corrispondete all'utente con l'email passata
            JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);

            return recuperaAllenamentiPerJsonArray(jsonObject.get(trainings).getAsJsonArray());

        } catch (IOException e) {
            throw new EccezioneAllenamentoInvalido("Allenamenti non esistenti");
        }
        catch (EccezioneAllenamentoInvalido e) {
            throw new EccezioneAllenamentoInvalido(e.getMessage());
        }
    }

    public List<Allenamento> getAllenamentiPerUtente(Utente utente) throws EccezioneAllenamentoInvalido {
        try{
            return getAllenamentiPerEmail(utente.getEmail());
        }
        catch (EccezioneAllenamentoInvalido e) {
            throw new EccezioneAllenamentoInvalido(e.getMessage());
        }
    }

}
