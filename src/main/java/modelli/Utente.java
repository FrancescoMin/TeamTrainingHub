package modelli;

import java.util.List;

public abstract class Utente {
    private String username;
    private String email;
    private List<Allenamento> allenamenti;
    private List<Squadra> squadre;

    private boolean allenatore;

    public Utente() {}

    public Utente (String username, String email) {
        setUsername(username);
        setEmail(email);
        setAllenamenti(null);
        setSquadre(null);
    }


    public Utente(String username, String email, List<Allenamento> allenamenti, List<Squadra> squadre) {
        setUsername(username);
        setEmail(email);
        setAllenamenti(allenamenti);
        setSquadre(squadre);
    }

    public void setSquadre(List<Squadra> squadre) {this.squadre = squadre;}
    public List<Squadra> getSquadre() {return squadre;}


    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }


    public void setUsername(String nome) {
        this.username = nome;
    }
    public String getUsername() {
        return username;
    }


    public void setAllenamenti(List<Allenamento> allenamenti) {
        this.allenamenti = allenamenti;
    }
    public List<Allenamento> getAllenamenti() {return allenamenti;}


    public boolean getAllenatore() {
        return allenatore;
    }
}
