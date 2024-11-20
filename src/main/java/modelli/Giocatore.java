package modelli;

import java.util.List;

public class Giocatore extends Utente{

    public Giocatore() {}

    public Giocatore(String username, String email)
    {
        super(username, email);
        this.allenatore=false;
    }

    public Giocatore(String username, String email, List<Allenamento> allenamenti, Squadra squadra) {
        super(username, email, allenamenti, squadra);
        this.allenatore = false;
    }
}
