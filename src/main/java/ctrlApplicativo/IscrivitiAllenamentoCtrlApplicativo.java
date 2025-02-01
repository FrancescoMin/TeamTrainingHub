package ctrlApplicativo;

import engineering.dao.AllenamentoDAO;
import engineering.eccezioni.EccezioneGenerica;
import engineering.pattern.Singleton;
import engineering.pattern.abstract_factory.DAOFactory;
import modelli.Allenamento;
import modelli.Utente;

import java.util.List;

public class IscrivitiAllenamentoCtrlApplicativo {

    private final AllenamentoDAO allenamentoDAO;

    public IscrivitiAllenamentoCtrlApplicativo() {
        // Inizializza il DAO tramite la factory
        this.allenamentoDAO = DAOFactory.getDAOFactory().createAllenamentoDAO();
    }

    /**
     * Legge tutti gli allenamenti disponibili dal DAO.
     *
     * @return Lista di allenamenti disponibili.
     */
    public List<Allenamento> leggiAllenamenti() {
        return allenamentoDAO.leggiAllenamentiPerUtente(Singleton.getInstance().getUtenteCorrente());
    }

    /**
     * Iscrive l'utente corrente a un allenamento.
     *
     * @param allenamento Allenamento a cui iscriversi.
     * @throws EccezioneGenerica Se l'utente è già iscritto o si verifica un errore.
     */
    public void aggiornaIscrizioneAllenamento(Allenamento allenamento) {
    // Recupera l'utente corrente dalla sessione
        Singleton istanza = Singleton.getInstance();
        Utente utente = istanza.getUtenteCorrente();

        if (utente == null) {
            throw new RuntimeException("Utente non autenticato. Impossibile aggiornare l'iscrizione.");
        }


        // Aggiorna i dati nel DAO
        allenamentoDAO.aggiornaIscrizioneUtente(utente, allenamento);
    }
}
