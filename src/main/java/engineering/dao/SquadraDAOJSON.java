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

public class SquadraDAOJSON implements SquadraDAO {

    public SquadraDAOJSON() {}

    public void creaSquadraPerAllenatore(Utente utente, Squadra squadra) {

        try {
            modificaSquadra(squadra, utente, null , true);
            //creaSquadra(squadra, utente);
            IscrizioneUtenteASquadra(utente, squadra);
        } catch (Exception e) {
            throw new EccezioneGenerica(e.getMessage());
        }
    }


    public void modificaSquadra(Squadra squadra, Utente utente, Allenamento allenamento, Boolean isCreazione) {
        try {

            //Creazione del path
            String filePath = "src/main/resources/persistenza/squadre/" + squadra.getNome() + ".json";
            // Create a Gson object
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("allenatore", utente.getEmail());
            jsonObject.addProperty("nome", squadra.getNome());

            JsonArray jsonArray = new JsonArray();



            try {
                //controllo che il file sia già esistente
                Files.readAllBytes(Paths.get(filePath));

                //se stiamo facendo la creazione della squadra e il file esiste, una squadra con lo stesso nome esiste già e lancio un'eccezione
                if(isCreazione){
                    throw new EccezioneGenerica("squadra esistente, impossibile crearla");
                }

                //se arrivo qui vuol dire che sto facendo una modifica e il file esiste, quindi posso sovrascriverlo
                String jsonString = new String(Files.readAllBytes(Paths.get(filePath)));

                JsonObject jsonObject1 = gson.fromJson(jsonString, JsonObject.class);

                jsonArray=jsonObject1.getAsJsonArray("allenamenti");
                jsonArray.add(allenamento.getData() + "-" + allenamento.getOrarioInizio() + "-" + allenamento.getOrarioFine());
                jsonObject.add("allenamenti", jsonArray);

                //aggiorno l'oggetto json per avere la lista degli allenamenti aggiornata
                FileWriter writer = new FileWriter(filePath);

                //salvataggio dell'oggetto serializzato squadra nel file json
                writer.write(gson.toJson(jsonObject1));
                writer.close();

                //capire se qui devo effettivamente lanciare questa eccezione
                //throw new EccezioneGenerica("squadra inserita");


            } catch (IOException e) {
                //se stiamo facendo la modifica della squadra e il file esiste, una squadra con lo stesso nome esiste già e lancio un'eccezione
                if(!isCreazione){
                    throw new EccezioneGenerica("squadra non esistente, impossibile modificare");
                }

                jsonObject.add("allenamenti", jsonArray);

                //se arrivo qui vuol dire che sto facendo una modifica e il file esiste, quindi posso sovrascriverlo
                FileWriter writer = new FileWriter(filePath);

                //salvataggio dell'oggetto serializzato utente nel file json
                writer.write(gson.toJson(jsonObject));
                writer.close();
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
