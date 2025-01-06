package engineering.pattern;

import engineering.eccezioni.EccezioneGenerica;
import modelli.*;

import java.util.ArrayList;
import java.util.List;

public class Singleton {

    //variabile statica che contiene l'istanza della classe
    private static Singleton instance;

    //variabili private dove vengono salvate le istanze
    private List<Utente> utenti = new ArrayList<>();

    //variabile privata che contiene l'istanza dell'utente che sta facendo uso del sistema
    private Utente utenteCorrente;

    private Singleton(){}

    // static block initialization for exception handling
    static {
        try {
            instance = new Singleton();
        } catch (EccezioneGenerica e) {
            throw new EccezioneGenerica("Exception occurred in creating singleton instance");
        }
    }

    public static Singleton getInstance() {
        return instance;
    }

    //metodi pubblici per ottenere le istanze di tutti gli utenti istanziati nel sistema
    public void aggiungiUtente(Utente utente) {utenti.add(utente);}

    public Utente getUtenteCorrente() {return utenteCorrente;}
    public void setUtenteCorrente(Utente utente) {utenteCorrente = utente;}

    public List<Utente> getUtenti() {return utenti;}
    public Boolean esisteUtenteDaUtente(Utente utente) {return esisteUtenteDaEmail(utente.getEmail());}

    public Boolean esisteSquadraDaNome(String nome) {
        for (Utente utente : utenti) {
            if (utente instanceof Allenatore allenatore) {
                Squadra squadra = allenatore.getSquadra();
                if (squadra.getNome().equals(nome)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Utente getUtenteDaLogin(Login login) throws EccezioneGenerica {return getUtenteDaEmail(login.getEmail());}
    public Boolean esisteUtenteDaLogin(Login login) {
        for (Utente utente : utenti) {
            if (utente.getEmail().equals(login.getEmail()) && utente.getPassword().equals(login.getPassword()))
            {
                return true;
            }
        }
        return false;
    }

    public Boolean esisteUtenteDaEmail(String email) {
        for (Utente utente : utenti) {
            if (utente.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public Utente getUtenteDaEmail(String email) throws EccezioneGenerica {
        for (Utente utente : utenti)
        {
            if (utente.getEmail().equals(email))
            {
                return utente;
            }
        }
        throw new EccezioneGenerica("Utente non esistente");
    }

    public void aggiungiRegistrazione(Registrazione registrazione){
        if (registrazione.getAllenatore()) {
            utenti.add(new Allenatore(registrazione.getUsername(),registrazione.getEmail(), registrazione.getPassword()));
        } else {
            utenti.add(new Giocatore(registrazione.getUsername(),registrazione.getEmail(), registrazione.getPassword()));
        }
    }
}
