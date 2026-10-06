package engineering.pattern;

import modelli.Utente;

public class Memoria {

    private static Memoria instance = null;
    private Utente utenteCorrente;

    private Memoria() {
        // Costruttore privato singleton
    }

    public static synchronized Memoria getInstance() {
        if (instance == null) {
            instance = new Memoria();
        }
        return instance;
    }

    public Utente getUtenteCorrente() {
        return utenteCorrente;
    }

    public void setUtenteCorrente(Utente utente) {
        this.utenteCorrente = utente;
    }
}