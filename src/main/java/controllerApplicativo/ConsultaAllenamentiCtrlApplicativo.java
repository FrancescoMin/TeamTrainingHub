package controllerApplicativo;

import com.fasterxml.jackson.databind.ObjectMapper;
import modelli.Allenamento;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConsultaAllenamentiCtrlApplicativo {

    private static final String DIRECTORY_PATH = "src/main/resources/persistenza/allenamenti";

    public List<Allenamento> leggiAllenamenti() {
        List<Allenamento> allenamenti = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Ottieni tutti i file JSON nella directory
            File directory = new File(DIRECTORY_PATH);
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
