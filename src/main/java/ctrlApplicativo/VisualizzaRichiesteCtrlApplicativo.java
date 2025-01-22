package ctrlApplicativo;

import engineering.bean.UtenteBean;
import engineering.dao.SquadraDAO;
import engineering.dao.UtenteDAO;
import engineering.pattern.Singleton;
import engineering.pattern.abstract_factory.DAOFactory;
import modelli.Squadra;
import modelli.Utente;

public class VisualizzaRichiesteCtrlApplicativo {

    //devo recuperare la squadra dell'utente (allenatore) che sta accettando la richiesta
    Singleton istanza = Singleton.getInstance();
    Squadra squadra = istanza.getUtenteCorrente().getSquadra();


    public void accettaRichiesta(UtenteBean utenteBean) {
        //mi occupo di ricreare il modello del giocatore

        update(utenteBean, true);
    }

    public void rifiutaRichiesta(UtenteBean utenteBean) {
        // Show an alert to indicate refusal

        update(utenteBean, false);
    }

    public void update(UtenteBean utenteBean, Boolean accettato) {

        System.out.println( "banana up");
        Utente utente = null;

        for(int i=0; i<squadra.getRichiesteIngresso().size(); i++)
        {
            if(squadra.getRichiesteIngresso().get(i).getEmail().equals(utenteBean.getEmail()))
            {
                utente = squadra.getRichiesteIngresso().get(i);
                if(accettato) {utente.setSquadra(squadra);}
                squadra.getRichiesteIngresso().remove(utente);
                break;
            }
        }

        System.out.println( "Post modifiche Username "+ utente.getUsername() +" Email " + utente.getEmail() + " Password " + utente.getPassword() + " Squadra " + utente.getSquadra().getNome() + " Allenamenti " + utente.getAllenamenti() + " has been accepted!");


        //adesso, se sto utilizzando il dao, aggiorno la persistenza per poter salvare le modifiche
        if (!istanza.getDemo()) {
            //se non siamo in modalità demo, aggiorno il database con le modifiche all'utente
            UtenteDAO utenteDao = DAOFactory.getDAOFactory().createUtenteDAO();
            utenteDao.aggiornaUtente(utente);

            //aggiorno anche la squadra
            SquadraDAO squadraDao = DAOFactory.getDAOFactory().createSquadraDAO();
            squadraDao.aggiornaSquadra(squadra);
        }


        //stampiamo tutte le informazioni dell'utente
        System.out.println( "Post modifiche Username "+ utente.getUsername() +" Email " + utente.getEmail() + " Password " + utente.getPassword() + " Squadra " + utente.getSquadra().getNome() + " Allenamenti " + utente.getAllenamenti() + " has been accepted!");

    }
}
