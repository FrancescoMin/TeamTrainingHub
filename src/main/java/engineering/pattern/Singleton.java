package engineering.pattern;

import engineering.eccezioni.EccezioneCambioScena;
import engineering.eccezioni.EccezioneIstanza;
import engineering.eccezioni.EccezioneSquadraInvalida;
import engineering.eccezioni.EccezioneUtenteInvalido;
import modelli.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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
        Singleton tempInstance = null;
        try {
            // Creazione dell'istanza singleton
            tempInstance = new Singleton();

            // Caricamento delle proprietà dal file demo.properties
            try (InputStream input = Singleton.class.getClassLoader().getResourceAsStream("demo.properties")) {
                if (input != null) {
                    Properties properties = new Properties();
                    properties.load(input);

                    // Lettura del valore dalla chiave del file demo
                    String parameter = properties.getProperty("mode.type", "false");

                    // Converte il valore in booleano
                    Boolean boo = Boolean.valueOf(parameter);

                    // Imposta il valore alla variabile demo dell'istanza singleton
                    tempInstance.setDemo(boo);
                } else {
                    System.out.println("File demo.properties non trovato.");
                }
            } catch (Exception e) {
                System.out.println("Errore durante la lettura del file demo.properties: " + e.getMessage());
            }

        } catch (Exception e) {
            throw new EccezioneIstanza("Exception occurred in creating singleton instance: " + e.getMessage());
        }
        instance = tempInstance;
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

    public Boolean esisteUtenteDaLogin(Login login) {
        for (Utente utente : utenti) {
            if (utente.getEmail().equals(login.getEmail()) && utente.getPassword().equals(login.getPassword())) {
                return true;
            }
        }
        return false;
    }
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

    public Utente getUtenteDaLogin(Login login) throws EccezioneUtenteInvalido {
        try {
            if (getUtenteDaEmail(login.getEmail()).getPassword().equals(login.getPassword())) {
                return getUtenteDaEmail(login.getEmail());
            }
        } catch (EccezioneUtenteInvalido e) {
            throw new EccezioneUtenteInvalido("Utente non esistente");
        }
        return null;
    }
    public Utente getUtenteDaEmail(String email) throws EccezioneUtenteInvalido {
        for (Utente utente : utenti) {
            if (utente.getEmail().equals(email)) {
                return utente;
            }
        }
        throw new EccezioneUtenteInvalido("Utente non esistente");
    }
    public Squadra getSquadraDaNome(String nomeSquadra) throws EccezioneSquadraInvalida {
        for (Utente utente : utenti) {
            if (utente instanceof Allenatore allenatore && allenatore.getSquadra().getNome().equals(nomeSquadra)) {
                return allenatore.getSquadra();
            }
        }
        throw new EccezioneUtenteInvalido("Squadra non esistente");
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
    }
}
