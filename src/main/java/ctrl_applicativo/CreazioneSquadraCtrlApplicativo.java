package ctrl_applicativo;

import engineering.dao.*;
import engineering.eccezioni.EccezioneSquadraInvalida;
import engineering.pattern.Singleton;
import engineering.pattern.abstract_factory.DAOFactory;
import modelli.Squadra;
import modelli.Utente;

public class CreazioneSquadraCtrlApplicativo {

    public CreazioneSquadraCtrlApplicativo() {
        // Costruttore vuoto di default
    }

    public void creazioneSquadra(String nomeSquadra) throws EccezioneSquadraInvalida{
        try {

            System.out.println("Tento di creare la squadra: " + nomeSquadra);

            //ottengo il singleton per ricavare l'utente che sta creando la squadra
            Singleton istanza = Singleton.getInstance();
            Utente utente = istanza.getUtenteCorrente();

            if (istanza.esisteSquadraDaNome(nomeSquadra) ){
                throw new EccezioneSquadraInvalida("squadra esistente");
            }

            //modifico il modello Utente con la squadra che stiamo creando
            utente.setSquadra(new Squadra(nomeSquadra, utente.getEmail()));

            if(!istanza.getDemo()) {
                //istanzio il dao per la squadra
                SquadraDAO squadraDAO = DAOFactory.getDAOFactory().createSquadraDAO();

                //inoltre aggiorno la rappresentazione del modello nella persistenza
                squadraDAO.creaSquadraPerAllenatore(utente, utente.getSquadra());
            }

        }
        catch (EccezioneSquadraInvalida e)
        {
            //ottengo il singleton per ricavare l'utente che sta creando la squadra
            Singleton istanza = Singleton.getInstance();
            Utente utente = istanza.getUtenteCorrente();

            //modifico il modello Utente con la squadra che stiamo creando
            utente.setSquadra(new Squadra());
            throw new EccezioneSquadraInvalida(e.getMessage());
        }
    }
}
