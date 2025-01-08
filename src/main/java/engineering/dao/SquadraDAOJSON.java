package engineering.dao;

import com.google.gson.*;
import engineering.eccezioni.EccezioneGenerica;
import modelli.Squadra;
import modelli.Utente;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SquadraDAOJSON implements SquadraDAO {

    public SquadraDAOJSON() {}

    public void creaSquadraPerAllenatore(Utente utente, Squadra squadra) {

        try {
            creaSquadra(squadra, utente);
            IscrizioneUtenteASquadra(utente, squadra);
        } catch (Exception e) {
            throw new EccezioneGenerica(e.getMessage());
        }
    }


    public void creaSquadra(Squadra squadra, Utente utente) {
        try {
            //Creazione del path
            String filePath = "src/main/resources/persistenza/squadre/" + squadra.getNome() + ".json";

            try {
                //controllo che il file sia già esistente
                Files.readAllBytes(Paths.get(filePath));

                //se il file esiste, una squadra con lo stesso nome esiste già e lancio un'eccezione
                throw new EccezioneGenerica("squadra esistente");

            } catch (IOException e) {
                //creazione del file con nome username dell'utente in formato json

                // Create a Gson object
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                FileWriter writer = new FileWriter(filePath);

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("allenatore", utente.getEmail());
                jsonObject.addProperty("nome", squadra.getNome());


                JsonArray jsonArray = new JsonArray();
                jsonObject.add("allenamenti", jsonArray);

                //salvataggio dell'oggetto serializzato utente nel file json
                writer.write(gson.toJson(jsonObject));
                writer.close();

                //capire se qui devo effettivamente lanciare questa eccezione
                //throw new EccezioneGenerica("squadra inserita");
            }


        } catch (Exception e) {
            throw new EccezioneGenerica(e.getMessage());
        }

    }

    public void IscrizioneUtenteASquadra(Utente utente, Squadra squadra) {

        try {
            System.out.println("Iscrizione dell'utente " + utente.getEmail() + "alla squadra: " + squadra.getNome());
            //poiché è il sistema a modificare i modelli e non il dao, non c'è bisogno di fare il setSquadra
            //utente.setSquadra(squadra);

            UtenteDAOJSON utenteDAOJSON = new UtenteDAOJSON();
            utenteDAOJSON.aggiornaUtente(utente);

        } catch (Exception e) {
            System.out.println("Errore nell'iscrizione dell'utente alla squadra");
        }
    }

    public void visualizzaTutteLeSquadre() {
        System.out.println("Visualizzazione di tutte le squadre");
    }


}
