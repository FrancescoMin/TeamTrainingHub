package engineering.bean;

import modelli.Allenamento;
import modelli.Squadra;

import java.util.List;

public class AllenatoreBean extends UtenteBean {

    public AllenatoreBean() {}

    public AllenatoreBean(String username, String email, String password) {
        super(username, email, password);
        this.allenatore = true;
    }

    public AllenatoreBean(String username, String email, String password , List<Allenamento> allenamenti, Squadra squadra) {
        super(username, email, password , allenamenti, squadra);
        this.allenatore=true;
    }
}
