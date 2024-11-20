package engineering.bean;

import modelli.Allenamento;
import modelli.Squadra;

import java.util.List;

public class AllenatoreBean extends GenericoBean{

    public AllenatoreBean() {}

    public AllenatoreBean(String username, String email, List<Allenamento> allenamenti, List<Squadra> squadre) {
        super(username, email, allenamenti, squadre);
        this.allenatore=true;
    }
}
