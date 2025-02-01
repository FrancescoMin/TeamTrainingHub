package modelli;

import java.util.List;

public class Giocatore extends Utente{

    public Giocatore() {}

    public Giocatore(String username, String email, String password) {
        super(username, email, password);
        this.allenatore=false;
    }

    public Giocatore(String username, String email, String password , List<Allenamento> allenamenti, Squadra squadra) {
        super(username, email, password , allenamenti, squadra);
        this.allenatore = false;
    }

    public String getMessaggioBenvenuto() {
        return "Benvenuto " + this.getUsername();
    }
}
