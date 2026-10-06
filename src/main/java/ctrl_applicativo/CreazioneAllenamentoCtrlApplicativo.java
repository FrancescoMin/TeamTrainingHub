package ctrl_applicativo;

import engineering.bean.AllenamentoBean;
import engineering.dao.AllenamentoDAO;
import engineering.eccezioni.EccezioneAllenamentoInvalido;
import engineering.pattern.Memoria;
import engineering.pattern.abstract_factory.DAOFactory;
import engineering.pattern.observer.CollezioneAllenamenti;
import modelli.Allenamento;
import modelli.Utente;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CreazioneAllenamentoCtrlApplicativo {

    public CreazioneAllenamentoCtrlApplicativo() {
        // Costruttore vuoto
    }

    public void creaAllenamento(AllenamentoBean allenamentobean) throws EccezioneAllenamentoInvalido {
        Memoria istanza = Memoria.getInstance();
        Utente utente = istanza.getUtenteCorrente();

        Allenamento allenamento = new Allenamento(
                allenamentobean.getData(),
                allenamentobean.getOrarioInizio(),
                allenamentobean.getOrarioFine(),
                allenamentobean.getDescrizione()
        );

        if (sovrapposizioneAllenamenti(utente.getAllenamenti(), allenamento)) {
            throw new EccezioneAllenamentoInvalido("Fascia oraria già occupata");
        }

        utente.getAllenamenti().add(allenamento);

        AllenamentoDAO allenamentoDAO = DAOFactory.getDAOFactory().createAllenamentoDAO();
        allenamentoDAO.creaAllenamentoAdUtente(allenamento, utente);

        // Notifica il Subject dell'avvenuta creazione di un nuovo allenamento
        CollezioneAllenamenti.getInstance().addAllenamento(allenamento);
    }

    public boolean sovrapposizioneAllenamenti(List<Allenamento> allenamenti, Allenamento allenamento) {
        for (Allenamento allenamentoCorrente : allenamenti) {
            if (allenamentoCorrente.getData().equals(allenamento.getData())) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH-mm");
                LocalTime inizioAllenamento = LocalTime.parse(allenamento.getOrarioInizio(), formatter);
                LocalTime fineAllenamento = LocalTime.parse(allenamento.getOrarioFine(), formatter);
                LocalTime inizioCorrente = LocalTime.parse(allenamentoCorrente.getOrarioInizio(), formatter);
                LocalTime fineCorrente = LocalTime.parse(allenamentoCorrente.getOrarioFine(), formatter);

                if (inizioAllenamento.isBefore(fineCorrente) && fineAllenamento.isAfter(inizioCorrente)) {
                    return true;
                }
            }
        }
        return false;
    }
}