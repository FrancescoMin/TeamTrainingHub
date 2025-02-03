package engineering.dao;

import com.google.gson.*;
import engineering.eccezioni.EccezioneAllenamentoInvalido;
import engineering.eccezioni.EccezioneUtenteInvalido;
import modelli.Allenamento;
import modelli.Utente;

import java.io.FileWriter;
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
            inserisciAllenamento(allenamento);

            UtenteDAOJSON utenteDAOJSON = new UtenteDAOJSON();
            utenteDAOJSON.aggiornaUtente(utente);

            //aggiorno la squadra dell'utente in modo che aggiungo alla squadra l'allenamento nella lista di allenamenti della squadra
            SquadraDAOJSON squadraDAOJSON = new SquadraDAOJSON();
            squadraDAOJSON.aggiornaSquadra(utente.getSquadra());


        }
        catch (Exception e) {
            throw new EccezioneAllenamentoInvalido(e.getMessage());
        }
    }

    public void inserisciAllenamento(Allenamento allenamento) throws EccezioneAllenamentoInvalido {

        //Creazione del path
        String path = "src/main/resources/persistenza/allenamenti/";

        String filePath = path + allenamento.getData()+"-" + allenamento.getOrarioInizio() + "-" + allenamento.getOrarioFine() + json;

        try {
            //controllo che il file sia già esistente
            Files.readAllBytes(Paths.get(filePath));

            //se il file esiste, un utente con la stessa email esiste già e lancio un'eccezione
            throw new EccezioneAllenamentoInvalido("Impossibile inserire l'utente perché esistente");

        }
        catch (IOException e) {
            try {
                //creazione del file con nome username dell'utente in formato json

                // Create a Gson object
                Gson gson = new GsonBuilder().setPrettyPrinting().create();


                //serializzo l'oggetto java in un oggetto json

                //parso l'oggetto json in una stringa
                String serial = gson.toJson(allenamento);

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

    public List<Allenamento> recuperaAllenamentiPerJsonArray(JsonArray jsonArray) throws EccezioneAllenamentoInvalido {
        try {

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            List<Allenamento> allenamenti = new ArrayList<>();

            for (int i = 0; i < jsonArray.size(); i++) {

                System.out.println(jsonArray.get(i).getAsString());

                String path = jsonArray.get(i).getAsString();

                String filePath = "src/main/resources/persistenza/allenamenti/" + path + json;

                System.out.println(filePath);

                String jsonString = new String(Files.readAllBytes(Paths.get(filePath)));

                System.out.println(jsonString);

                //creo l'oggetto JSON corrispondete all'utente con l'email passata
                JsonObject allenamento = gson.fromJson(jsonString, JsonObject.class);

                System.out.println(allenamento);

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
