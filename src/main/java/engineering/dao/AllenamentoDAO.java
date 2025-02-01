package engineering.dao;

import modelli.Allenamento;
import modelli.Utente;

import java.util.List;

public interface AllenamentoDAO {

    public void inserisciAllenamentoAdUtente(Allenamento allenamento, Utente utente);

    public List<Allenamento> leggiAllenamentiPerUtente(Utente utente);

    public default void aggiornaIscrizioneUtente(Utente utente, Allenamento allenamento) {
        //TODO
    }
}
