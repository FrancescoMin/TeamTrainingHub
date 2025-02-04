package ctrl_applicativo;

import engineering.dao.AllenamentoDAO;
import engineering.eccezioni.EccezioneAllenamentoInvalido;
import engineering.pattern.Singleton;
import engineering.pattern.abstract_factory.DAOFactory;
import modelli.Allenamento;
import engineering.bean.AllenamentoBean;
import modelli.Utente;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CreazioneAllenamentoCtrlApplicativo {

    public CreazioneAllenamentoCtrlApplicativo() {
        // Costruttore vuoto
    }

    public void creaAllenamento(AllenamentoBean allenamentobean) throws EccezioneAllenamentoInvalido {

        try {
            Singleton istanza = Singleton.getInstance();

            //ottengo dal singleton l'utente corrente
            Utente utente = istanza.getUtenteCorrente();

            //creo il modello allenamento che verrà inserito nel database
            Allenamento allenamento = new Allenamento(allenamentobean.getData(), allenamentobean.getOrarioInizio(), allenamentobean.getOrarioFine(), allenamentobean.getDescrizione());

            //devo controllare che l'allenamento non sia in una fascia oraria già occupata
            if (sovrapposizioneAllenamenti(utente.getAllenamenti(), allenamento)) {
                throw new EccezioneAllenamentoInvalido("Fascia oraria già occupata");
            }

            //aggiungo l'allenamento all'utente
            utente.getAllenamenti().add(allenamento);

            //se non siamo in demo, salviamo l'allenamento nella persistenza
            if (!istanza.getDemo()) {
                //creo il dao per salvare l'allenamento nella persistenza
                AllenamentoDAO allenamentoDAO = DAOFactory.getDAOFactory().createAllenamentoDAO();

                //assegniamo l'allenamento all'allenatore che lo ha creato
                allenamentoDAO.inserisciAllenamentoAdUtente(allenamento, utente);
            }
        }
        catch (EccezioneAllenamentoInvalido e) {
            throw new EccezioneAllenamentoInvalido(e.getMessage());
        }
    }

    public boolean sovrapposizioneAllenamenti(List<Allenamento> allenamenti, Allenamento allenamento) {

        for (Allenamento allenamentoCorrente : allenamenti)
        {

            //se la data è uguale, faccio il controllo pure sull'orario
            if (allenamentoCorrente.getData().equals(allenamento.getData())) {

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH-mm");
                LocalTime inizioAllenamento = LocalTime.parse(allenamento.getOrarioInizio(), formatter);
                LocalTime fineAllenamento = LocalTime.parse(allenamento.getOrarioFine(), formatter);
                LocalTime inizioCorrente = LocalTime.parse(allenamentoCorrente.getOrarioInizio(), formatter);
                LocalTime fineCorrente = LocalTime.parse(allenamentoCorrente.getOrarioFine(), formatter);

                // Confronta gli orari per vedere se si intersecano
                if (inizioAllenamento.isBefore(fineCorrente) && fineAllenamento.isAfter(inizioCorrente)) {
                    return true;
                }
            }
        }
        return false;
    }

}

