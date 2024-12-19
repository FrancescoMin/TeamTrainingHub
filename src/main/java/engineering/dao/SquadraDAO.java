package engineering.dao;

import modelli.Squadra;
import modelli.Utente;

public interface SquadraDAO {
    public void creaSquadraPerAllenatore(Utente utente, Squadra squadra);
    public void IscrizioneUtenteASquadra(Utente utente, Squadra squadra);
    public void visualizzaTutteLeSquadre();
}
