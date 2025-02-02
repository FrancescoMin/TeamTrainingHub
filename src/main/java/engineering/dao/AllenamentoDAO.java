package engineering.dao;

import modelli.Allenamento;
import modelli.Utente;

import java.util.List;

public interface AllenamentoDAO {

    public void inserisciAllenamentoAdUtente(Allenamento allenamento, Utente utente);

    public List<Allenamento> getAllenamentiPerUtente(Utente utente);
    public List<Allenamento> getAllenamentiPerEmail(String email);

}
