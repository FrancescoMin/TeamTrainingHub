package ctrlApplicativo;

import engineering.eccezioni.EccezioneGenerica;

import java.nio.file.Files;
import java.nio.file.Paths;

public class EntraInSquadraCtrlApplicativo {

    private static final String FILE_PATH = "src/main/resources/persistenza/squadre/";

    public EntraInSquadraCtrlApplicativo() {
        // Costruttore vuoto
    }

    /**
     * Verifica se esiste una squadra con il nome specificato nella persistenza.
     *
     * @param nomeSquadra Nome della squadra da verificare.
     * @return True se il file esiste, False altrimenti.
     * @throws EccezioneGenerica Se il nome della squadra è vuoto o invalido.
     */
    public boolean verificaEsistenzaSquadra(String nomeSquadra) throws EccezioneGenerica {
        if (nomeSquadra == null || nomeSquadra.trim().isEmpty()) {
            throw new EccezioneGenerica("Il nome della squadra non può essere vuoto.");
        }

        String filePath = FILE_PATH + nomeSquadra + ".json";

        // Controlla se il file esiste
        return Files.exists(Paths.get(filePath));
    }
}
