package engineering.bean;

import modelli.Allenamento;
import modelli.Squadra;

import java.util.List;

public class GiocatoreBean extends UtenteBean {

    public GiocatoreBean() {}

    public GiocatoreBean(String username, String email, String password){
        super(username, email, password);
        this.allenatore=false;
    }

    public GiocatoreBean(String username, String email, String password, List<Allenamento> allenamenti, Squadra squadra) {
        super(username, email, password , allenamenti, squadra);
        this.allenatore=false;
    }

    public GiocatoreBean(String username, String email, String password, List<Allenamento> allenamenti) {
        super(username, email, password , allenamenti);
        this.allenatore=false;
    }
}
