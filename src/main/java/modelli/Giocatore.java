package modelli;

import java.util.List;

public class Giocatore extends Utente{

    public Giocatore() {}

    public Giocatore(String username, String email, String password) {
        super(username, email, password);
        setGiocatore();
    }

    public Giocatore(String username, String email, String password , List<Allenamento> allenamenti, Squadra squadra) {
        super(username, email, password , allenamenti, squadra);
        setGiocatore();
    }

    private void setGiocatore() {
        this.allenatore = false;
    }
}
