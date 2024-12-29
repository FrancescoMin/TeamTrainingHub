package controllerApplicativo;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class entraInSquadraCtrlApplicativo {

    private List<String> teamList;

    public entraInSquadraCtrlApplicativo() {
        caricaSquadreJSON();
    }

    private void caricaSquadreJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            teamList = objectMapper.readValue(new File("src/main/resources/persistenza/squadre"), List.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean nomeValido(String teamName) {
        return teamList.contains(teamName);
    }
}