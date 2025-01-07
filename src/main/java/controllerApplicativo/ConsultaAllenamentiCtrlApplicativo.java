package controllerApplicativo;

import com.fasterxml.jackson.databind.ObjectMapper;
import modelli.Allenamento;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/*
public class ConsultaAllenamentiCtrlApplicativo {

    public List<String> leggiAllenamenti() {
        List<String> allenamenti = new ArrayList<>();
        String directoryPath = "src/main/resources/persistenza/allenamenti/";

        try {
            File directory = new File(directoryPath);
            File[] files = directory.listFiles((dir, name) -> name.endsWith(".json"));

            if (files != null) {
                for (File file : files) {
                    String content = new String(Files.readAllBytes(Paths.get(file.getPath())));
                    JsonObject jsonObject = JsonParser.parseString(content).getAsJsonObject();
                    String allenamento = jsonObject.get("nome").getAsString();
                    allenamenti.add(allenamento);
                }
            } else {
                throw new EccezioneGenerica("Nessun file JSON trovato nella directory.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return allenamenti;
    }
}
*/

public class ConsultaAllenamentiCtrlApplicativo {


    public List<Allenamento> leggiAllenamenti() { //legge gli allenamenti dalla persistenza
        List<Allenamento> allenamenti = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Ottieni tutti i file JSON nella directory degli allenamenti
            File directory = new File(getClass().getClassLoader().getResource("persistenza/allenamenti").toURI());
            if (directory.exists() && directory.isDirectory()) {
                File[] files = directory.listFiles((dir, name) -> name.endsWith(".json"));
                if (files != null) {
                    // Leggi ogni file JSON e aggiungi i dati alla lista
                    for (File file : files) {
                        try {
                            Allenamento allenamento = objectMapper.readValue(file, Allenamento.class);
                            allenamenti.add(allenamento);
                        } catch (IOException e) {
                            System.err.println("Errore durante la lettura del file: " + file.getName());
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return allenamenti;
    }
}
