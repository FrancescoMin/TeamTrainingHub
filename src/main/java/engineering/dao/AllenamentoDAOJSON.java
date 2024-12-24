package engineering.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import engineering.eccezioni.EccezioneGenerica;
import modelli.Allenamento;
import modelli.Utente;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AllenamentoDAOJSON implements AllenamentoDAO {

    public void inserisciAllenamentoAdUtente(Allenamento allenamento, Utente utente) {
        System.out.println("Creazione Allenamento JSON");
        //aggiunta dell'utente alla lista degli utenti
        try {
            //Creazione del path
            String filePath = "src/main/resources/persistenza/allenamenti/" + allenamento.getData() + ".json";

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

                String jsonString = gson.toJson(allenamento);

                // Step 2: Convert the JSON string to a JsonObject
                JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();

                //salvataggio dell'oggetto serializzato utente nel file json
                writer.write(gson.toJson(jsonObject));
                writer.close();
            }
        } catch (Exception e) {
            throw new EccezioneGenerica(e.getMessage());
        }

        utente.getAllenamenti().add(allenamento);

        UtenteDAOJSON utenteDAOJSON = new UtenteDAOJSON();
        utenteDAOJSON.aggiornaUtente(utente);
    }

}
