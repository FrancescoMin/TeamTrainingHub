package engineering.pattern;

import engineering.eccezioni.EccezzioneGenerica;
import modelli.Allenatore;
import modelli.Giocatore;
import modelli.Registrazione;
import modelli.Utente;

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

    public void aggiungiRegistrazione(Registrazione registazione){
        if (registazione.getAllenatore()) {
            utenti.add(new Allenatore(registazione.getUsername(),registazione.getEmail(), registazione.getPassword()));
        } else {
            utenti.add(new Giocatore(registazione.getUsername(),registazione.getEmail(), registazione.getPassword()));
        }
    }
}
