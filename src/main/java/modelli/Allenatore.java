package modelli;

import java.util.List;

public class Allenatore extends Utente{

    public Allenatore() {}

    public Allenatore(String username, String email, List<Allenamento> allenamenti, Squadra squadra) {
        super(username, email, allenamenti,squadra);
        this.allenatore = true;
    }

    public Allenatore(String username, String email) {
        super(username, email);
        this.allenatore = true;
    }

}
