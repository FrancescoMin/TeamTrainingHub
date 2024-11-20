package engineering.bean;

import modelli.Allenamento;
import modelli.Squadra;

import java.util.List;

public class AllenatoreBean extends GenericoBean{

    public AllenatoreBean() {}

    public AllenatoreBean(String username, String email, List<Allenamento> allenamenti, Squadra squadre) {
        super(username, email, allenamenti, squadra);
        this.allenatore=true;
    }
}
