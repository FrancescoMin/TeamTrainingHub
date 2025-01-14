package engineering.dao;

import modelli.Squadra;
import modelli.Utente;

public interface SquadraDAO {
    public void creaSquadraPerAllenatore(Utente utente, Squadra squadra);
    public void IscrizioneUtenteASquadra(Utente utente, Squadra squadra);
    public void visualizzaTutteLeSquadre();
    public Squadra getSquadraDaNome(String nomeSquadra);
    public Boolean verificaEsistenzaSquadra(String nomeSquadra);
    public void aggiungiRichiestaASquadra(Squadra squadra, Utente utente); //invia richiesta all'allenatore della squadra
}
