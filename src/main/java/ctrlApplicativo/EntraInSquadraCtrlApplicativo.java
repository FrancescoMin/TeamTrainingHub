package ctrlApplicativo;

import engineering.dao.SquadraDAO;
import engineering.dao.SquadraDAOJSON;
import engineering.eccezioni.EccezioneGenerica;
import engineering.pattern.Singleton;
import engineering.pattern.abstract_factory.DAOFactory;
import modelli.Squadra;
import modelli.Utente;

public class EntraInSquadraCtrlApplicativo {

    public EntraInSquadraCtrlApplicativo() {
        // Inizializza il DAO tramite la factory
    }

    /**
     * Verifica se una squadra esiste.
     *
     * @param nomeSquadra Nome della squadra da verificare.
     * @return true se la squadra esiste, false altrimenti.
     * @throws EccezioneGenerica Se il nome della squadra è nullo o vuoto.
     */
    public boolean verificaEsistenzaSquadra(String nomeSquadra) throws EccezioneGenerica {
        if (nomeSquadra == null || nomeSquadra.trim().isEmpty()) {
            throw new EccezioneGenerica("Il nome della squadra non può essere vuoto.");
        }
        System.out.println("Verifica esistenza per: " + nomeSquadra);

        Singleton istanza = Singleton.getInstance();

        //L'utente è sicuramente in una squadra e quindi verifico solamente se la squadra esiste

        //controllo negli utenti se esiste una squadra con quel nome nel singleton prima di tutto
        if(istanza.esisteSquadraDaNome(nomeSquadra)){
            return true;
        }

        //se l'utente è in modalità demo e non ho trovato nulla nel singleton, restituisco false
        if (istanza.getDemo()){
            return false;
        }

        //se non è in modalità demo, controllo nel database
        else {
            return DAOFactory.getDAOFactory().createSquadraDAO().verificaEsistenzaSquadra(nomeSquadra);
        }

    }

    /**
     * Invia una richiesta di ingresso alla squadra specificata.
     *
     * @param nomeSquadra Nome della squadra a cui inviare la richiesta.
     * @throws EccezioneGenerica Se l'utente è già in una squadra o la squadra non esiste.
     */
    public void inviaRichiestaAllaSquadra(String nomeSquadra) throws EccezioneGenerica {
        //poiché sono arrivato all'invio della richiesta, l'utente ha sicuramente inserito una squadra esistente

        Singleton istanza = Singleton.getInstance();
        Utente utente = istanza.getUtenteCorrente();

        //In questa funzione non mi preoccupo di controllare se la squadra esiste perché è stato già controllato
        //come prima cosa, ottengo la squadra da nome
        Squadra squadra = new Squadra();

        //controllo se sono in modalità demo
        if(istanza.esisteSquadraDaNome(nomeSquadra)){
            squadra = istanza.getSquadraDaNome(nomeSquadra);
            squadra.getRichiesteIngresso().add(utente);
        }

        //entrerò all'interno di questo else solo se non ho trovato la squadra nel singleton e non sono nella modalità demo
        else if(!istanza.getDemo()){
            System.out.println("Richiesta inviata al database");
            SquadraDAO squadraDAO = DAOFactory.getDAOFactory().createSquadraDAO();

            //ottengo dalla persistenza la squadra da modificare
            squadra = squadraDAO.getSquadraDaNome(nomeSquadra);

            //modifico la squadra aggiungendo l'utente tra le richieste di ingresso
            squadra.getRichiesteIngresso().add(utente);



            //aggiorno la squadra in modo da aggiungere la richiesta alla lista
            squadraDAO.aggiornaSquadra(squadra);
        }

    }
}

