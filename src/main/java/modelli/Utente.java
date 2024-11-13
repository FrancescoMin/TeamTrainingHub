package modelli;

import java.util.List;

public abstract class Utente {
    private String username;
    private String email;
    private List<Allenamento> iscrizioni;
    private boolean allenatore;

    public Utente() {}

    public Utente(String username, String email) {
        setUsername(username);
        setEmail(email);
        this.iscrizioni = null;
    }


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


    public void setIscrizioni(List<Allenamento> iscrizioni) {
        this.iscrizioni = iscrizioni;
    }
    public List<Allenamento> getIscrizioni() {
        return iscrizioni;
    }


    public boolean getAllenatore() {
        return allenatore;
    }
}
