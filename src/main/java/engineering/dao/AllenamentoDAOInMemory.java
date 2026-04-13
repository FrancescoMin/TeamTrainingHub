package engineering.dao;

import engineering.eccezioni.EccezioneAllenamentoInvalido;
import modelli.Allenamento;
import modelli.Utente;

import java.util.ArrayList;
import java.util.List;

public class AllenamentoDAOInMemory implements AllenamentoDAO {

    private static final List<AllenamentoUtente> associazioni = new ArrayList<>();

    private static class AllenamentoUtente {
        Allenamento allenamento;
        String emailUtente; // Usiamo l'email come chiave
        
        public AllenamentoUtente(Allenamento allenamento, String emailUtente) {
            this.allenamento = allenamento;
            this.emailUtente = emailUtente;
        }
    }

    @Override
    public void creaAllenamentoAdUtente(Allenamento allenamento, Utente utente) throws EccezioneAllenamentoInvalido {
        // Aggiungiamo l'associazione.
        associazioni.add(new AllenamentoUtente(allenamento, utente.getEmail()));
    }

    @Override
    public void iscriviUtenteAdAllenamento(Allenamento allenamento, Utente utente) throws EccezioneAllenamentoInvalido {
        // Verifica se già iscritto? Per ora aggiungiamo.
        associazioni.add(new AllenamentoUtente(allenamento, utente.getEmail()));
    }

    @Override
    public List<Allenamento> ottieniAllenamentiPerUtente(Utente utente) throws EccezioneAllenamentoInvalido {
        return ottieniAllenamentiPerEmail(utente.getEmail());
    }

    @Override
    public List<Allenamento> ottieniAllenamentiPerEmail(String email) throws EccezioneAllenamentoInvalido {
        return associazioni.stream()
                .filter(a -> a.emailUtente.equals(email))
                .map(a -> a.allenamento)
                .toList();
    }
}
