package engineering.pattern;

import engineering.eccezioni.EccezioneGenerica;
import modelli.*;

import java.util.ArrayList;
import java.util.List;

public class Singleton {

    //variabile statica che contiene l'istanza della classe
    private static final Singleton instance;

    //variabili private dove vengono salvate le istanze
    private final List<Utente> utenti = new ArrayList<>();

    //variabile privata che contiene l'istanza dell'utente che sta facendo uso del sistema
    private Utente utenteCorrente;

    //variabile che indica se il sistema è in modalità demo o meno
    private Boolean demo;


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

    public Boolean getDemo() {return demo;}
    public void setDemo(Boolean demo) {this.demo = demo;}

    public List<Utente> getUtenti() {return utenti;}

    public Boolean esisteUtenteDaUtente(Utente utente) {return esisteUtenteDaEmail(utente.getEmail());}
    public Boolean esisteUtenteDaLogin(Login login) {return esisteUtenteDaEmail(login.getEmail());}
    public Boolean esisteUtenteDaRegistrazione(Registrazione registrazione) {return esisteUtenteDaEmail(registrazione.getEmail());}
    public Boolean esisteUtenteDaEmail(String email) {
        for (Utente utente : utenti) {
            if (utente.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }
    public Boolean esisteSquadraDaNome(String nomeSquadra) {
        for (Utente utente : utenti) {
            if (utente.getAllenatore() && utente.getSquadra().getNome().equals(nomeSquadra)) {
                return true;
            }
        }
        return false;
    }

    public Utente getUtenteDaLogin(Login login) throws EccezioneGenerica {return getUtenteDaEmail(login.getEmail());}
    public Utente getUtenteDaEmail(String email) throws EccezioneGenerica {
        for (Utente utente : utenti) {
            if (utente.getEmail().equals(email)) {
                return utente;
            }
        }
        throw new EccezioneGenerica("Utente non esistente");
    }
    public Squadra getSquadraDaNome(String nomeSquadra) throws EccezioneGenerica {
        for (Utente utente : utenti) {
            if (utente instanceof Allenatore allenatore && allenatore.getSquadra().getNome().equals(nomeSquadra)) {
                return allenatore.getSquadra();
            }
        }
        throw new EccezioneGenerica("Squadra non esistente");
    }

    public void aggiungiRegistrazione(Registrazione registrazione){
        Utente utente;
        if (registrazione.getAllenatore()) {
            utente=new Allenatore(registrazione.getUsername(),registrazione.getEmail(), registrazione.getPassword());
        } else {
            utente=new Giocatore(registrazione.getUsername(),registrazione.getEmail(), registrazione.getPassword());
        }
        utenti.add(utente);
        setUtenteCorrente(utente);
        System.out.println("Utente " + utente.getEmail() + " aggiunto al sistema");
    }
}
