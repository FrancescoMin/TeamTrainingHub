package controllerApplicativo;

import engineering.bean.UtenteBean;
import engineering.dao.*;
import engineering.eccezioni.EccezioneGenerica;
import engineering.pattern.Singleton;
import modelli.Squadra;
import modelli.Utente;

public class CreazioneSquadraControllerApplicativo {

    public CreazioneSquadraControllerApplicativo() {}

    public void creazioneSquadra(UtenteBean utenteBean, String nomeSquadra) throws EccezioneGenerica {
        try {
            //implementazione della logica per la creazione della squadra
            // nel caso JSON
            SquadraDAO squadraDAO = new SquadraDAOJSON();

            //caso MySQL
            //SquadraDAO squadraDAO = new SquadraDAOMySQL();

            System.out.println("Tento di creare la squadra: " + nomeSquadra);


            //dato il bean passato dal controller grafico, recupero il modello dalla memoria locale
            Singleton instance = Singleton.getInstance();

            //lavoro con il modello e lo aggiorno
            Utente utente = instance.getUtenteDaEmail(utenteBean.getEmail());
            utente.setSquadra(new Squadra(nomeSquadra));

            //inoltre aggiorno la rappresentazione del modello nella persistenza
            squadraDAO.creaSquadraPerAllenatore(utente, utente.getSquadra());

            //completata l'applicazione della logica, posso mostrare qualche tipo di feedback
            System.out.println("completato con successo");

            //cambio di scena tornando alla pagina principale
        }
        catch (EccezioneGenerica e)
        {
            throw new EccezioneGenerica(e.getMessage());
        }
    }
}