package ctrl_applicativo;

import engineering.bean.AllenamentoBean;
import engineering.dao.AllenamentoDAO;
import engineering.eccezioni.EccezioneAllenamentoInvalido;
import engineering.pattern.Memoria;
import engineering.pattern.abstract_factory.DAOFactory;
import engineering.pattern.observer.CollezioneAllenamenti;
import engineering.pattern.observer.Observer;
import modelli.Allenamento;
import modelli.Squadra;
import modelli.Utente;

import java.util.ArrayList;
import java.util.List;

public class IscrizioneAllenamentoCtrlApplicativo implements Observer {

    CollezioneAllenamenti collezioneAllenamenti = CollezioneAllenamenti.getInstance();
    private AllenamentoDAO allenamentoDAO; // DAO per interagire con la persistenza

    public IscrizioneAllenamentoCtrlApplicativo() {
        //Costruttore vuoto di default
    }

    @Override
    public void update() {
        popola();
    }

    // Carica gli allenamenti dalla persistenza
    public List<AllenamentoBean> caricaAllenamenti() throws EccezioneAllenamentoInvalido {
        try {

            // Recupera gli allenamenti dal DAO e aggiungili alla collezione
            allenamentoDAO = DAOFactory.getDAOFactory().createAllenamentoDAO();
            Memoria singleton = Memoria.getInstance();
            Utente utente = singleton.getUtenteCorrente();

            //carico gli allenamenti per l'utente corrente così da sapere a quali è già iscritto
            List<Allenamento> allenamentiGiocatore = allenamentoDAO.ottieniAllenamentiPerUtente(utente);

            //carico gli allenamenti per la squadra dell'utente corrente partendo dall'ottenere la squadra
            Squadra squadra = utente.getSquadra();

            //a partire dalla squadra ottengo l'allenatore
            String nomeAllenatore = squadra.getAllenatore();

            List<Allenamento> allenamentiAllenatore = allenamentoDAO.ottieniAllenamentiPerEmail(nomeAllenatore);

            //eliminiamo dalla lista degli allenamenti dell'allenatore quelli che sono già presenti nella lista degli allenamenti del giocatore
            List<Allenamento> allenamentiFinale = new ArrayList<>();
            allenamentiFinale=eliminaAllenamenti(allenamentiAllenatore, allenamentiGiocatore);

            return trasformazione(allenamentiFinale);
        }
        catch (EccezioneAllenamentoInvalido e) {
            throw new EccezioneAllenamentoInvalido(e.getMessage());
        }
    }

    public void popola() {
        List<AllenamentoBean> allenamentiFinali = caricaAllenamenti();

        CollezioneAllenamenti collezioneAllenamenti = CollezioneAllenamenti.getInstance();
        collezioneAllenamenti.popolaTabella(allenamentiFinali);
    }


    // Accetta un allenamento
    public void accettaAllenamento(AllenamentoBean allenamentoBean) throws EccezioneAllenamentoInvalido {
        try {

            Allenamento allenamento = new Allenamento(allenamentoBean.getData(), allenamentoBean.getOrarioInizio(), allenamentoBean.getOrarioFine(), allenamentoBean.getDescrizione());

            Utente utente = Memoria.getInstance().getUtenteCorrente();
            utente.getAllenamenti().add(allenamento); // Aggiungi l'allenamento all'utente

            // Logica per accettare l'allenamento
            allenamentoDAO = DAOFactory.getDAOFactory().createAllenamentoDAO();
            allenamentoDAO.iscriviUtenteAdAllenamento(allenamento, utente); // Approva l'allenamento nel DAO

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