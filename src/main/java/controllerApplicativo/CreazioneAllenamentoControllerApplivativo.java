package controllerApplicativo;

import engineering.bean.UtenteBean;
import engineering.dao.AllenamentoDAO;
import engineering.dao.AllenamentoDAOJSON;
import engineering.dao.AllenamentoDAOMySQL;
import engineering.pattern.Singleton;
import modelli.Allenamento;
import engineering.bean.AllenamentoBean;
import modelli.Utente;

public class CreazioneAllenamentoControllerApplivativo {

    public void creaAllenamento(AllenamentoBean allenamentoBean, UtenteBean utenteBean) {

        try {
            Singleton instance = Singleton.getInstance();
            Utente utente=instance.getUtenteDaEmail(utenteBean.getEmail());

            Allenamento allenamento=new Allenamento(allenamentoBean.getData(), allenamentoBean.getDurata(), allenamentoBean.getDescrizione());




            //AllenamentoDAO allenamentoDAO = new AllenamentoDAOJSON();
            AllenamentoDAO allenamentoDAO = new AllenamentoDAOMySQL();






            //assegniamo l'allenamento all'allenatore che lo ha creato
            allenamentoDAO.inserisciAllenamentoAdUtente(allenamento, utente);

            System.out.println("Creazione ed inserimento dell'allenamento avvenuto con successo");

        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}

