package engineering.bean;

import modelli.Allenamento;
import modelli.Squadra;

import java.util.List;

public class GiocatoreBean  extends GenericoBean{

    public GiocatoreBean() {}

    public GiocatoreBean(String username, String email, List<Allenamento> allenamenti, Squadra squadra) {
        super(username, email, allenamenti, squadra);
        this.allenatore=false;
    }
}
