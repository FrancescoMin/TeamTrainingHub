package modelli;

import java.util.ArrayList;
import java.util.List;

public abstract class Utente {
    private String username;
    private String email;
    private String password;
    private List<Allenamento> allenamenti;
    private Squadra squadra;

    protected boolean allenatore;

    public Utente() {}


    public Utente(String username, String email, String password) {
        setUsername(username);
        setEmail(email);
        setPassword(password);
        setAllenamenti(new ArrayList<>());
        setSquadra(new Squadra());
    }

    public Utente(String username, String email, String password , List<Allenamento> allenamenti, Squadra squadra) {
        setUsername(username);
        setEmail(email);
        setPassword(password);
        setAllenamenti(allenamenti);
        setSquadra(squadra);
    }

    public void setSquadra(Squadra squadra) {this.squadra = squadra;}
    public Squadra getSquadra() {return this.squadra;}


    public void setPassword(String password) {this.password = password;}
    public String getPassword() {return this.password;}


    public void setEmail(String email) {this.email = email;}
    public String getEmail() {return this.email;}


    public void setUsername(String nome) {this.username = nome;}
    public String getUsername() {return this.username;}

    public void setAllenamenti(List<Allenamento> allenamenti) {this.allenamenti = allenamenti;}
    public List<Allenamento> getAllenamenti() {return this.allenamenti;}


    public boolean getAllenatore() {
        return this.allenatore;
    }
}
