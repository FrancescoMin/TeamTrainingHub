package engineering.dao;

import engineering.eccezioni.EccezionePasswordErrata;
import engineering.eccezioni.EccezioneSquadraInvalida;
import engineering.eccezioni.EccezioneUtenteInvalido;
import modelli.Allenatore;
import modelli.Giocatore;
import modelli.Login;
import modelli.Registrazione;
import modelli.Utente;

import java.util.ArrayList;
import java.util.List;

public class UtenteDAOInMemory implements UtenteDAO {

    private static final List<Utente> utenti = new ArrayList<>();

    @Override
    public void inserisciUtenteDaRegistrazione(Registrazione registrazione) throws EccezioneUtenteInvalido {
        // Verifica se esiste già
        if (esisteUtenteDaEmail(registrazione.getEmail())) {
            throw new EccezioneUtenteInvalido("Utente già esistente con questa email: " + registrazione.getEmail());
        }

        Utente nuovoUtente;
        if (registrazione.getAllenatore()) {
            nuovoUtente = new Allenatore(registrazione.getUsername(), registrazione.getEmail(), registrazione.getPassword());
        } else {
            nuovoUtente = new Giocatore(registrazione.getUsername(), registrazione.getEmail(), registrazione.getPassword());
        }

        utenti.add(nuovoUtente);
    }

    @Override
    public Utente recuperaUtenteDaEmail(String email) throws EccezioneUtenteInvalido, EccezioneSquadraInvalida {
        for (Utente u : utenti) {
            if (u.getEmail().equals(email)) {
                return u;
            }
        }
        throw new EccezioneUtenteInvalido("Nessun utente trovato con email: " + email);
    }

    @Override
    public Utente recuperaUtenteDaLogin(Login login) throws EccezioneUtenteInvalido, EccezioneSquadraInvalida, EccezionePasswordErrata {
        Utente utente = null;
        try {
            utente = recuperaUtenteDaEmail(login.getEmail());
        } catch (EccezioneUtenteInvalido e) {
            throw new EccezioneUtenteInvalido("Login fallito: utente non trovato.");
        }

        if (!utente.getPassword().equals(login.getPassword())) {
            throw new EccezionePasswordErrata("Login fallito: password errata.");
        }

        return utente;
    }

    @Override
    public void aggiornaUtente(Utente utente) throws EccezioneUtenteInvalido {
        boolean trovato = false;
        for (int i = 0; i < utenti.size(); i++) {
            if (utenti.get(i).getEmail().equals(utente.getEmail())) {
                utenti.set(i, utente);
                trovato = true;
                break;
            }
        }
        if (!trovato) {
            throw new EccezioneUtenteInvalido("Impossibile aggiornare: utente non trovato.");
        }
    }

    @Override
    public boolean esisteUtenteDaEmail(String email) throws EccezioneUtenteInvalido {
        for (Utente u : utenti) {
            if (u.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }
}
