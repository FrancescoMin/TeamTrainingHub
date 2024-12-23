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
            creaSquadra(squadra);
            IscrizioneUtenteASquadra(utente, squadra);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void IscrizioneUtenteASquadra(Utente utente, Squadra squadra) {

        try {
            System.out.println("Iscrizione dell'utente " + utente.getEmail() + "alla squadra: " + squadra.getNome());
            utente.setSquadra(squadra);

            UtenteDAOJSON utenteDAOJSON = new UtenteDAOJSON();
            utenteDAOJSON.aggiornaUtente(utente);

        } catch (Exception e) {
            System.out.println("Errore nell'iscrizione dell'utente alla squadra");
        }
    }

    public void visualizzaTutteLeSquadre() {
        System.out.println("Visualizzazione di tutte le squadre");
    }


    public void creaSquadra(Squadra squadra) {
        try {
            //Creazione del path
            String filePath = "src/main/resources/persistenza/squadre/" + squadra.getNome() + ".json";

            try {
                //controllo che il file sia già esistente
                Files.readAllBytes(Paths.get(filePath));

                //se il file esiste, un utente con la stessa email esiste già e lancio un'eccezione
                throw new EccezioneGenerica("squadra esistente");

            } catch (IOException e) {
                //creazione del file con nome username dell'utente in formato json

                // Create a Gson object
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                FileWriter writer = new FileWriter(filePath);

                // Step 1: Serialize the Person object to a JSON string
                String jsonString = gson.toJson(squadra);

                // Step 2: Convert the JSON string to a JsonObject
                JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();

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
}
