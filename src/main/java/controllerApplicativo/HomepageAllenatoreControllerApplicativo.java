package controllerApplicativo;

import engineering.eccezioni.EccezioneGenerica;
import engineering.pattern.Singleton;
import modelli.Utente;

public class HomepageAllenatoreControllerApplicativo
{

    public HomepageAllenatoreControllerApplicativo() {
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

    /*
    public void GestisciSquadra() throws EccezioneGenerica{
        Singleton istanza= Singleton.getInstance();
        Utente utente= istanza.getUtenteCorrente();

        //implemento i controlli sull'allenatore che richiedere la gestione della Squadra
        if (utente.getSquadra().getNome().isEmpty()) {
            System.out.println("L'allenatore non ha una squadra");
            throw new EccezioneGenerica("L'allenatore non ha una squadra");

        } else {
            System.out.println("L'allenatore ha una squadra e procedo alla gestione della squadra");

            //compio il cambio della scena
        }
    }

    public void CreaSquadra() throws EccezioneGenerica{
        Singleton istanza= Singleton.getInstance();
        Utente utente= istanza.getUtenteCorrente();

        //implemento i controlli sull'allenatore che richiedere la creazione della Squadra
        if (!utente.getSquadra().getNome().isEmpty()) {
            System.out.println("L'allenatore ha già una squadra");
            throw new EccezioneGenerica("L'allenatore ha già una squadra");

        } else {
            System.out.println("L'allenatore non ha una squadra e procedo alla creazione della squadra");

            //compio il cambio della scena
        }
    }

     */
}
