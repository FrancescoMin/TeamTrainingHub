package controllerApplicativo;

import engineering.dao.*;
import engineering.eccezioni.EccezioneGenerica;
import engineering.pattern.Singleton;
import engineering.pattern.abstract_factory.DAOFactory;
import modelli.Squadra;
import modelli.Utente;

public class CreazioneSquadraControllerApplicativo {

    public CreazioneSquadraControllerApplicativo() {}

    public void creazioneSquadra(String nomeSquadra) throws EccezioneGenerica {
        try {

            //istanzio il dao per la squadra
            SquadraDAO squadraDAO = DAOFactory.getDAOFactory().createSquadraDAO();

            System.out.println("Tento di creare la squadra: " + nomeSquadra);

            //ottengo il singleton per ricavare l'utente che sta creando la squadra
            Singleton istanza = Singleton.getInstance();
            Utente utente = istanza.getUtenteCorrente();

            if (istanza.esisteSquadraDaNome(nomeSquadra) ){
                throw new EccezioneGenerica("squadra esistente");
            }

            //modifico il modello Utente con la squadra che stiamo creando
            utente.setSquadra(new Squadra(nomeSquadra));

            //inoltre aggiorno la rappresentazione del modello nella persistenza
            squadraDAO.creaSquadraPerAllenatore(utente, utente.getSquadra());

            //completata l'applicazione della logica, posso mostrare qualche tipo di feedback
            System.out.println("completato con successo");

            //cambio di scena tornando alla pagina principale implementato nel controller grafico in questo caso in modo temporaneo

        }
        catch (EccezioneGenerica e)
        {
            //ottengo il singleton per ricavare l'utente che sta creando la squadra
            Singleton istanza = Singleton.getInstance();
            Utente utente = istanza.getUtenteCorrente();

            //modifico il modello Utente con la squadra che stiamo creando
            utente.setSquadra(new Squadra(""));
            System.out.println("Errore nella creazione della squadra");
            throw new EccezioneGenerica(e.getMessage());
        }
    }
}
