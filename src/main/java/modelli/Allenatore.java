package modelli;

import java.util.List;

public class Allenatore extends Utente{
    private String username;
    private String email;
    private List<Allenamento> iscrizioni;
    private boolean allenatore;

    public Allenatore() {}

    public Allenatore(String username, String email) {
        super(username, email);
        this.allenatore = true;
    }

}
