package ctrlApplicativo;

import engineering.bean.UtenteBean;
import modelli.Giocatore;

public class VisualizzaRichiesteCtrlApplicativo {

    public void accettaRichiesta(UtenteBean utenteBean) {
        // Show an alert to indicate acceptance
        System.out.println("User " + utenteBean.getEmail() + " has been accepted!");
        if(utenteBean.getAllenatore())
        {
            Giocatore giocatore = new Giocatore();
        }
    }

    public void rifiutaRichiesta(UtenteBean utenteBean) {
        // Show an alert to indicate refusal
        System.out.println("User " + utenteBean.getEmail() + " has been refused!");
    }
}
