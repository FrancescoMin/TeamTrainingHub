package modelli;

import java.util.List;

public class Giocatore extends Utente{
    private String username;
    private String email;
    private List<Allenamento> allenamenti;
    private List<Squadra> squadre;
    private boolean allenatore;

    public Giocatore() {}

    public Giocatore(String username, String email)
    {
        super(username, email);
        this.allenatore=false;
    }

    public Giocatore(String username, String email, List<Allenamento> allenamenti, List<Squadra> squadre) {
        super(username, email, allenamenti, squadre);
        this.allenatore = false;
    }
}
