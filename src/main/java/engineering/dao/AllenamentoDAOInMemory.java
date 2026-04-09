package engineering.dao;

import engineering.eccezioni.EccezioneAllenamentoInvalido;
import modelli.Allenamento;
import modelli.Utente;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AllenamentoDAOInMemory implements AllenamentoDAO {

    private static final List<AllenamentoUtente> associazioni = new ArrayList<>();
    
    // Classe interna per mappare allenamenti a utenti (relazione molti-a-molti simulata o uno-a-molti)
    // Guardando l'interfaccia, sembra che si debba recuperare gli allenamenti per utente o email.
    // In questo caso, memorizziamo semplicemente l'associazione.
    // Tuttavia, Allenamento non ha un ID univoco esplicito, quindi ci basiamo sugli oggetti o sui campi.
    // L'implementazione più semplice è aggiungere l'allenamento alla lista dell'utente (che è già nell'oggetto Utente)
    // E magari mantenere una lista globale se serve per altre query.
    // Ma le firme dei metodi suggeriscono persistenza dell'associazione.
    
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
        // In un DB relazionale salveremmo l'allenamento e l'associazione.
        // Qui aggiungiamo l'associazione.
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
                .collect(Collectors.toList());
    }
}
