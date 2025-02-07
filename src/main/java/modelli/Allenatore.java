package modelli;

import java.util.List;

public class Allenatore extends Utente{

    public Allenatore() {}

    public Allenatore(String username, String email, String password , List<Allenamento> allenamenti, Squadra squadra) {
        super(username, email, password ,allenamenti,squadra);
        setAllenatore();
    }

    public Allenatore(String username, String email, String password) {
        super(username, email, password);
        setAllenatore();
    }

    private void setAllenatore() {
        this.allenatore = true;
    }
}
