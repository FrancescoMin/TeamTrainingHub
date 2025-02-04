package ctrl_applicativo;

import engineering.dao.SquadraDAO;
import engineering.eccezioni.EccezioneSquadraInvalida;
import engineering.pattern.Memoria;
import engineering.pattern.abstract_factory.DAOFactory;
import modelli.Squadra;
import modelli.Utente;

public class EntraInSquadraCtrlApplicativo {

    private final Memoria istanza = Memoria.getInstance();

    public EntraInSquadraCtrlApplicativo() {
        // Inizializza il DAO tramite la factory
    }


    public boolean verificaEsistenzaSquadra(String nomeSquadra) throws EccezioneSquadraInvalida {

        if (nomeSquadra == null || nomeSquadra.trim().isEmpty()) {
            throw new EccezioneSquadraInvalida("Il nome della squadra non può essere vuoto.");
        }

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
            SquadraDAO squadraDAO = DAOFactory.getDAOFactory().createSquadraDAO();
            return squadraDAO.verificaEsistenzaSquadra(nomeSquadra);
        }
    }

    /**
     * Invia una richiesta di ingresso alla squadra specificata.
     *
     * @param nomeSquadra Nome della squadra a cui inviare la richiesta.
     * @throws EccezioneSquadraInvalida Se l'utente è già in una squadra o la squadra non esiste.
     */
    public void inviaRichiestaAllaSquadra(String nomeSquadra) throws EccezioneSquadraInvalida {
        try {
            //poiché sono arrivato all'invio della richiesta, l'utente ha sicuramente inserito una squadra esistente

            Utente utente = istanza.getUtenteCorrente();

            //In questa funzione non mi preoccupo di controllare se la squadra esiste perché è stato già controllato
            //come prima cosa, ottengo la squadra da nome
            Squadra squadra = new Squadra();

            //controllo se sono in modalità demo
            if (istanza.getDemo()) {
                appoggio(nomeSquadra, utente);
            }

            //entrerò all'interno di questo else solo se non ho trovato la squadra nel singleton e non sono nella modalità demo
            else {
                SquadraDAO squadraDAO = DAOFactory.getDAOFactory().createSquadraDAO();

                if(istanza.esisteSquadraDaNome(nomeSquadra)) {
                    appoggio(nomeSquadra, utente);
                }

                //ottengo dalla persistenza la squadra da modificare se non l'ho trovata nel singleton
                else {
                    System.out.println("Squadra non trovata nel singleton, la cerco nel database");
                    squadra = squadraDAO.getSquadraDaNome(nomeSquadra);
                    for(Utente u : squadra.getRichiesteIngresso()){
                        if(u.getEmail().equals(utente.getEmail())){
                            throw new EccezioneSquadraInvalida("Hai già inviato una richiesta a questa squadra, attendi la risposta");
                        }
                    }
                    squadra.getRichiesteIngresso().add(utente);
                }

                //aggiorno la squadra in modo da aggiungere la richiesta alla lista
                squadraDAO.aggiornaSquadra(squadra);
            }
        }
        catch (EccezioneSquadraInvalida e) {
            throw new EccezioneSquadraInvalida(e.getMessage());
        }
    }

    private void appoggio(String nomeSquadra, Utente utente) throws EccezioneSquadraInvalida {
        Squadra squadra = istanza.getSquadraDaNome(nomeSquadra);
        for(Utente u : squadra.getRichiesteIngresso()){
            if(u.getEmail().equals(utente.getEmail())){
                throw new EccezioneSquadraInvalida("Hai già inviato una richiesta a questa squadra");
            }
        }
        squadra.getRichiesteIngresso().add(utente);
    }
}

