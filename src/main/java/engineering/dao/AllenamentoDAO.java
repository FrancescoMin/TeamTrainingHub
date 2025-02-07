package engineering.dao;

import engineering.eccezioni.EccezioneAllenamentoInvalido;
import modelli.Allenamento;
import modelli.Utente;

import java.util.List;

public interface AllenamentoDAO {

    public void creaAllenamentoAdUtente(Allenamento allenamento, Utente utente) throws EccezioneAllenamentoInvalido;

    public void iscriviUtenteAdAllenamento(Allenamento allenamento, Utente utente) throws EccezioneAllenamentoInvalido;

    public List<Allenamento> ottieniAllenamentiPerUtente(Utente utente) throws EccezioneAllenamentoInvalido;
    
    public List<Allenamento> ottieniAllenamentiPerEmail(String email) throws EccezioneAllenamentoInvalido;

}
