package modelli;

import java.util.List;

public abstract class Utente {
    private String username;
    private String email;
    private List<Allenamento> allenamenti;
    private String password; //ridondanza
    private Squadra squadra;

    protected boolean allenatore;

    public Utente() {}

    public Utente (String username, String email) {
        setUsername(username);
        setEmail(email);
        setAllenamenti(null);
        setSquadre(null);
    }


    public Utente(String username, String email, List<Allenamento> allenamenti, Squadra squadra) {
        setUsername(username);
        setEmail(email);
        setAllenamenti(allenamenti);
        setSquadre(squadra);
    }

    public void setSquadre(Squadra squadra) {this.squadra = squadra;}
    public Squadra getSquadra() {return this.squadra;}


    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {return this.email;}


    public void setUsername(String nome) {
        this.username = nome;
    }
    public String getUsername() {
        return this.username;
    }


    public void setAllenamenti(List<Allenamento> allenamenti) {
        this.allenamenti = allenamenti;
    }
    public List<Allenamento> getAllenamenti() {return this.allenamenti;}


    public boolean getAllenatore() {
        return this.allenatore;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAllenatore() {
        return allenatore;
    }

    public void setAllenatore(boolean allenatore) {
        this.allenatore = allenatore;
    }
}
