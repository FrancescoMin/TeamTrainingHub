package engineering.dao;

import engineering.eccezioni.EccezioneSquadraInvalida;
import modelli.Squadra;
import modelli.Utente;

public interface SquadraDAO {
    public void creaSquadraPerAllenatore(Utente utente, Squadra squadra) throws EccezioneSquadraInvalida;
    public void IscrizioneUtenteASquadra(Utente utente, Squadra squadra)throws EccezioneSquadraInvalida;
    public Squadra getSquadraDaNome(String nomeSquadra) throws EccezioneSquadraInvalida;
    public Boolean verificaEsistenzaSquadra(String nomeSquadra);
    public void aggiornaSquadra(Squadra squadra)throws EccezioneSquadraInvalida;
}
