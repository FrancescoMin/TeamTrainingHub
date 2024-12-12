package engineering.pattern;

import engineering.eccezioni.EccezzioneGenerica;
import modelli.*;

import java.util.List;

public class Singleton {

    //variabile statica che contiene l'istanza della classe
    private static Singleton instance;

    //variabili private dove vengono salvate le istanze
    private List<Utente> utenti;

    private Singleton(){}

    // static block initialization for exception handling
    static {
        try {
            instance = new Singleton();
        } catch (EccezzioneGenerica e) {
            throw new EccezzioneGenerica("Exception occurred in creating singleton instance");
        }
    }

    public static Singleton getInstance() {
        return instance;
    }

    //metodi pubblici per ottenere le istanze di tutti gli utenti istanziati nel sistema
    public void aggiungiUtente(Utente utente) {utenti.add(utente);}
    public List<Utente> getUtenti() {return utenti;}
    public Boolean esisteUtenteDaUtente(Utente utente) {return esisteUtenteDaEmail(utente.getEmail());}
    public Utente getUtenteDaLogin(Login login) throws EccezzioneGenerica {return getUtenteDaEmail(login.getEmail());}

    public Boolean esisteUtenteDaLogin(String email, String password) {
        for (Utente utente : utenti)
        {
            if (utente.getEmail().equals(email) && utente.getPassword().equals(password))
            {
                return true;
            }
        }
        return false;
    }

    public Boolean esisteUtenteDaEmail(String email) {
        for (Utente utente : utenti)
        {
            if (utente.getEmail().equals(email))
            {
                return true;
            }
        }
        return false;
    }

    public Utente getUtenteDaEmail(String email) throws EccezzioneGenerica {
        for (Utente utente : utenti)
        {
            if (utente.getEmail().equals(email))
            {
                return utente;
            }
        }
        throw new EccezzioneGenerica("Utente non trovato");
    }

    public void aggiungiRegistrazione(Registrazione registrazione){
        if (registrazione.getAllenatore()) {
            utenti.add(new Allenatore(registrazione.getUsername(),registrazione.getEmail(), registrazione.getPassword()));
        } else {
            utenti.add(new Giocatore(registrazione.getUsername(),registrazione.getEmail(), registrazione.getPassword()));
        }
    }
}
