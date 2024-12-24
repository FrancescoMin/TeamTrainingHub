package controllerApplicativo;

import engineering.dao.AllenamentoDAO;
import engineering.dao.AllenamentoDAOJSON;
import modelli.Allenamento;
import engineering.bean.AllenamentoBean;
import modelli.Allenatore;
import modelli.Utente;

public class CreazioneAllenamentoControllerApplivativo {

    public void creaAllenamento(AllenamentoBean allenamentoBean) {

        try {
            Utente utente = new Allenatore("prova", "a", "1");

            AllenamentoDAO allenamentoDAO = new AllenamentoDAOJSON();
            allenamentoDAO.inserisciAllenamentoAdUtente(new Allenamento(allenamentoBean.getData(), allenamentoBean.getDurata(), allenamentoBean.getDescrizione()), utente);

            System.out.println("Creazione ed inserimento dell'allenamento avvenuto con successo");


        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}

