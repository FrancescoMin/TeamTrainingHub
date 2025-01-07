package controllerApplicativo;

import engineering.dao.AllenamentoDAO;
import engineering.eccezioni.EccezioneGenerica;
import engineering.pattern.Singleton;
import engineering.pattern.abstract_factory.DAOFactory;
import modelli.Allenamento;
import engineering.bean.AllenamentoBean;
import modelli.Utente;

public class CreazioneAllenamentoControllerApplicativo {

    public void creaAllenamento(AllenamentoBean allenamentoBean) {

        try {
            Singleton istanza = Singleton.getInstance();

            //ottengo dal singleton l'utente corrente
            Utente utente= istanza.getUtenteCorrente();

            //creo il modello allenamento che verrà inserito nel database
            Allenamento allenamento=new Allenamento(allenamentoBean.getData(), allenamentoBean.getDurata(), allenamentoBean.getDescrizione());

            //aggiungo l'allenamento all'utente
            utente.getAllenamenti().add(allenamento);

            //se non siamo in demo, salviamo l'allenamento nella persistenza
            if (!istanza.getDemo()) {
                //creo il dao per salvare l'allenamento nella persistenza
                AllenamentoDAO allenamentoDAO = DAOFactory.getDAOFactory().createAllenamentoDAO();

                //assegniamo l'allenamento all'allenatore che lo ha creato
                allenamentoDAO.inserisciAllenamentoAdUtente(allenamento, utente);
            }

            System.out.println("Creazione ed inserimento dell'allenamento avvenuto con successo");

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new EccezioneGenerica("Errore durante la creazione dell'allenamento");
        }
    }
}

