package ctrl_applicativo;

import engineering.dao.*;
import engineering.eccezioni.EccezioneSquadraInvalida;
import engineering.pattern.Memoria;
import engineering.pattern.abstract_factory.DAOFactory;
import modelli.Squadra;
import modelli.Utente;

public class CreazioneSquadraCtrlApplicativo {

    public CreazioneSquadraCtrlApplicativo() {
        // Costruttore vuoto di default
    }

    public void creazioneSquadra(String nomeSquadra) throws EccezioneSquadraInvalida{
        try {
            SquadraDAO squadraDAO = DAOFactory.getDAOFactory().createSquadraDAO();

            //ottengo il singleton per ricavare l'utente che sta creando la squadra
            Memoria istanza = Memoria.getInstance();
            Utente utente = istanza.getUtenteCorrente();

            if (istanza.esisteSquadraDaNome(nomeSquadra) ){
                throw new EccezioneSquadraInvalida("squadra esistente");
            }
            else if (squadraDAO.verificaEsistenzaSquadra(nomeSquadra)){
                throw new EccezioneSquadraInvalida("squadra esistente in persistenza");
            }

            Squadra squadra = new Squadra(nomeSquadra, utente.getEmail());

            //modifico il modello Utente con la squadra che stiamo creando
            utente.setSquadra(squadra);

            if(!istanza.getDemo()) {

                //inoltre aggiorno la rappresentazione del modello nella persistenza
                squadraDAO.creaSquadraPerAllenatore(utente, utente.getSquadra());
            }

        }
        catch (EccezioneSquadraInvalida e)
        {
            //ottengo il singleton per ricavare l'utente che sta creando la squadra
            Memoria istanza = Memoria.getInstance();
            Utente utente = istanza.getUtenteCorrente();

            //modifico il modello Utente con la squadra che stiamo creando
            utente.setSquadra(new Squadra());
            throw new EccezioneSquadraInvalida(e.getMessage());
        }
    }
}
