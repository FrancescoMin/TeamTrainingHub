package ctrl_applicativo;

import engineering.pattern.Memoria;
import modelli.Utente;

public class HomepageAllenatoreCtrlApplicativo
{

    public HomepageAllenatoreCtrlApplicativo() {
        //costruttore vuoto di default del controllore applicativo
    }

    public boolean esisteSquadra(){
        Memoria istanza= Memoria.getInstance();
        Utente utente= istanza.getUtenteCorrente();

        return !utente.getSquadra().getNome().isEmpty();
    }
}
