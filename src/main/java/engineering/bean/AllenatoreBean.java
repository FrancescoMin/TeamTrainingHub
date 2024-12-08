package engineering.bean;

import modelli.Allenamento;
import modelli.Squadra;

import java.util.List;

public class AllenatoreBean extends UtenteBean {

    public AllenatoreBean() {}

    public AllenatoreBean(String username, String email, List<Allenamento> allenamenti, Squadra squadra) {
        super(username, email, allenamenti, squadra);
        this.allenatore=true;
    }
}
