package ctrlApplicativo;

import engineering.bean.AllenamentoBean;
import engineering.dao.AllenamentoDAO;
import engineering.eccezioni.EccezioneAllenamentoInvalido;
import engineering.pattern.Singleton;
import engineering.pattern.abstract_factory.DAOFactory;
import engineering.pattern.observer.CollezioneAllenamenti;
import modelli.Allenamento;
import modelli.Squadra;
import modelli.Utente;

import java.util.ArrayList;
import java.util.List;

public class IscrivitiAllenamentoCtrlApplicativo {

    private CollezioneAllenamenti collezioneAllenamenti ;
    private AllenamentoDAO allenamentoDAO; // DAO per interagire con la persistenza

    public IscrivitiAllenamentoCtrlApplicativo() {
        collezioneAllenamenti = new CollezioneAllenamenti();
        //allenamentoDAO = new AllenamentoDAO(); // Inizializza il DAO
        caricaAllenamenti(); // Carica gli allenamenti dalla persistenza
    }

    // Carica gli allenamenti dalla persistenza
    private void caricaAllenamenti() throws EccezioneAllenamentoInvalido {
        try {
            // Recupera gli allenamenti dal DAO e aggiungili alla collezione
            allenamentoDAO = DAOFactory.getDAOFactory().createAllenamentoDAO();
            Singleton singleton = Singleton.getInstance();
            Utente utente = singleton.getUtenteCorrente();

            //carico gli allenamenti per l'utente corrente così da sapere a quali è già iscritto
            List<Allenamento> allenamentiGiocatore = allenamentoDAO.getAllenamentiPerUtente(utente);

            //carico gli allenamenti per la squadra dell'utente corrente partendo dall'ottenere la squadra
            Squadra squadra = utente.getSquadra();

            //a partire dalla squadra ottengo l'allenatore
            String nomeAllenatore = squadra.getAllenatore();

            List<Allenamento> allenamentiAllenatore = allenamentoDAO.getAllenamentiPerEmail(nomeAllenatore);

            //eliminiamo dalla lista degli allenamenti dell'allenatore quelli che sono già presenti nella lista degli allenamenti del giocatore
            List<Allenamento> allenamentiFinali = eliminaAllenamenti(allenamentiAllenatore, allenamentiGiocatore);

            collezioneAllenamenti.popolaTabella(trasformazione(allenamentiFinali));
        }
        catch (EccezioneAllenamentoInvalido e) {
            throw new EccezioneAllenamentoInvalido(e.getMessage());
        }
    }

    // Restituisce la collezione di allenamenti
    public CollezioneAllenamenti getCollezioneAllenamenti() {
        return collezioneAllenamenti;
    }

    // Accetta un allenamento
    public void accettaAllenamento(AllenamentoBean allenamento) throws EccezioneAllenamentoInvalido {
        try {
            // Logica per accettare l'allenamento

            allenamentoDAO.inserisciAllenamentoAdUtente(new Allenamento(allenamento.getData(), allenamento.getOrarioInizio(), allenamento.getOrarioFine(), allenamento.getDescrizione()), Singleton.getInstance().getUtenteCorrente()); // Approva l'allenamento nel DAO
            collezioneAllenamenti.removeAllenamento(allenamento); // Rimuovi l'allenamento dalla collezione
        }
        catch (EccezioneAllenamentoInvalido e) {
            throw new EccezioneAllenamentoInvalido(e.getMessage());
        }
    }

    private List<AllenamentoBean> trasformazione(List<Allenamento> allenamenti){
        List<AllenamentoBean> allenamentiTrasformati = new ArrayList<>();
        for(Allenamento allenamento : allenamenti){
            allenamentiTrasformati.add(new AllenamentoBean(allenamento.getData(), allenamento.getOrarioInizio(), allenamento.getOrarioFine(), allenamento.getDescrizione()));
        }
        return allenamentiTrasformati;
    }

    private List<Allenamento> eliminaAllenamenti(List<Allenamento> allenamenti, List<Allenamento> allenamentiDaEliminare){
        List<Allenamento> allenamentiRimanenti = new ArrayList<>(allenamenti);

        for(Allenamento allenamento : allenamenti){
            for (Allenamento allenamentoDaEliminare : allenamentiDaEliminare){
                if(allenamento.getData().equals(allenamentoDaEliminare.getData()) && allenamento.getOrarioInizio().equals(allenamentoDaEliminare.getOrarioInizio()) && allenamento.getOrarioFine().equals(allenamentoDaEliminare.getOrarioFine())){
                    allenamentiRimanenti.remove(allenamento);
                }
            }
        }
        return allenamentiRimanenti;
    }

}