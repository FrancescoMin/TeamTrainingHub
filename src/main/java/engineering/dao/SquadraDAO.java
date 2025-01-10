package engineering.dao;

import modelli.Squadra;
import modelli.Utente;

public interface SquadraDAO {
    public void creaSquadraPerAllenatore(Utente utente, Squadra squadra);
    public void IscrizioneUtenteASquadra(Utente utente, Squadra squadra);
    public void visualizzaTutteLeSquadre();
    public Squadra verificaEsistenzaSquadra(String nomeSquadra);
    public void inviaRichiestaASquadra(Squadra squadra, Utente utente); //invia richiesta all'allenatore della squadra
}
