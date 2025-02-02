package ctrlApplicativo;

import engineering.pattern.Singleton;
import modelli.Utente;

public class HomepageAllenatoreCtrlApplicativo
{

    public HomepageAllenatoreCtrlApplicativo() {
        //costruttore vuoto di default del controllore applicativo
    }

    public Boolean esisteSquadra(){
        Singleton istanza= Singleton.getInstance();
        Utente utente= istanza.getUtenteCorrente();

        if (utente.getSquadra().getNome().isEmpty()) {
            System.out.println("L'allenatore non ha una squadra");
            return false;
        }
        else {
            System.out.println("L'allenatore ha una squadra");
            return true;
        }
    }
}
