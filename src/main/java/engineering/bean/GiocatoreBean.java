package engineering.bean;

import modelli.Allenamento;
import modelli.Squadra;

import java.util.List;

public class GiocatoreBean  extends GenericoBean{

    public GiocatoreBean() {}

    public GiocatoreBean(String username, String email, List<Allenamento> allenamenti, List<Squadra> squadre) {
        super(username, email, allenamenti, squadre);
        this.allenatore=false;
    }
}
