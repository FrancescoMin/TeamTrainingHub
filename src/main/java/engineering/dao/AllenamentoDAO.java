package engineering.dao;

import modelli.Allenamento;
import modelli.Utente;

public interface AllenamentoDAO {

    public void inserisciAllenamentoAdUtente(Allenamento allenamento, Utente utente);

}
