package modelli;

import java.util.List;

public class Giocatore extends Utente{
    private String username;
    private String email;
    private List<Allenamento> iscrizioni;
    private boolean allenatore;

    public Giocatore() {}

    public Giocatore(String username, String email) {
        super(username, email);
        this.allenatore = false;
    }
}
