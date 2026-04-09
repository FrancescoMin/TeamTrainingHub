package engineering.dao;

import engineering.eccezioni.EccezioneSquadraInvalida;
import modelli.Squadra;
import modelli.Utente;

import java.util.ArrayList;
import java.util.List;

public class SquadraDAOInMemory implements SquadraDAO {

    private static final List<Squadra> squadre = new ArrayList<>();

    @Override
    public void creaSquadraPerAllenatore(Utente utente, Squadra squadra) throws EccezioneSquadraInvalida {
        if (verificaEsistenzaSquadra(squadra.getNome())) {
            throw new EccezioneSquadraInvalida("Squadra già esistente: " + squadra.getNome());
        }
        // Associa la squadra all'allenatore (nel DB si farebbe update dell'utente o insert squadra con fk)
        // Aggiorniamo l'oggetto Utente passato se necessario, ma qui persistiamo la squadra
        squadra.setAllenatore(utente.getUsername());
        // NOTA: Utente ha getUsername, Squadra ha getAllenatore (String).
        // Bisognerebbe capire cosa si aspetta la business logic.
        // Assumiamo che setAllenatore prenda una stringa identificativa.
        
        squadre.add(squadra);
    }

    @Override
    public void iscrizioneUtenteASquadra(Utente utente, Squadra squadra) throws EccezioneSquadraInvalida {
        // Cerca la squadra persistita
        Squadra squadraEsistente = ottieniSquadraDaNome(squadra.getNome());
        
        // Aggiungi l'utente alle richieste (o direttamente alla squadra se la logica lo prevede così)
        // Guardando Squadra.java: private List<Utente> richiesteIngresso;
        if (squadraEsistente.getRichiesteIngresso() == null) {
            squadraEsistente.setRichiesteIngresso(new ArrayList<>());
        }
        squadraEsistente.getRichiesteIngresso().add(utente);
    }

    @Override
    public Squadra ottieniSquadraDaNome(String nomeSquadra) throws EccezioneSquadraInvalida {
        for (Squadra s : squadre) {
            if (s.getNome().equals(nomeSquadra)) {
                return s;
            }
        }
        throw new EccezioneSquadraInvalida("Squadra non trovata: " + nomeSquadra);
    }

    @Override
    public boolean verificaEsistenzaSquadra(String nomeSquadra) {
        for (Squadra s : squadre) {
            if (s.getNome().equals(nomeSquadra)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void aggiornaSquadra(Squadra squadra) throws EccezioneSquadraInvalida {
        for (int i = 0; i < squadre.size(); i++) {
            if (squadre.get(i).getNome().equals(squadra.getNome())) {
                squadre.set(i, squadra);
                return;
            }
        }
        throw new EccezioneSquadraInvalida("Impossibile aggiornare: squadra non trovata.");
    }
}
