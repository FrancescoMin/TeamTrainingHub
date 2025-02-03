package ctrlApplicativo;

import engineering.pattern.Singleton;
import modelli.Utente;

public class HomepageAllenatoreCtrlApplicativo
{

    public HomepageAllenatoreCtrlApplicativo() {
        //costruttore vuoto di default del controllore applicativo
    }

    public boolean esisteSquadra(){
        Singleton istanza= Singleton.getInstance();
        Utente utente= istanza.getUtenteCorrente();

        return !utente.getSquadra().getNome().isEmpty();
    }
}
