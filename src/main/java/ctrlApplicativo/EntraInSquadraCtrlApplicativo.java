package ctrlApplicativo;

import engineering.dao.SquadraDAO;
import engineering.eccezioni.EccezioneGenerica;
import engineering.pattern.Singleton;
import engineering.pattern.abstract_factory.DAOFactory;
import modelli.Squadra;
import modelli.Utente;

public class EntraInSquadraCtrlApplicativo {

    private final SquadraDAO squadraDAO;

    public EntraInSquadraCtrlApplicativo() {
        // Inizializza il DAO tramite la factory
        this.squadraDAO = DAOFactory.getDAOFactory().createSquadraDAO();
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
        Squadra squadra = squadraDAO.verificaEsistenzaSquadra(nomeSquadra);
        System.out.println("Squadra trovata: " + (squadra != null));

        return squadra != null;
    }

    /**
     * Invia una richiesta di ingresso alla squadra specificata.
     *
     * @param nomeSquadra Nome della squadra a cui inviare la richiesta.
     * @throws EccezioneGenerica Se l'utente è già in una squadra o la squadra non esiste.
     */
    public void inviaRichiestaAllaSquadra(String nomeSquadra) throws EccezioneGenerica {
        Singleton istanza = Singleton.getInstance();
        Utente utente = istanza.getUtenteCorrente();

        if (!utente.getSquadra().getNome().isEmpty()) {
            throw new EccezioneGenerica("L'utente è già in una squadra.");
        }

        if (!verificaEsistenzaSquadra(nomeSquadra)) {
            throw new EccezioneGenerica("La squadra specificata non esiste.");
        }

        Squadra squadra = squadraDAO.verificaEsistenzaSquadra(nomeSquadra);

        // Invia la richiesta all'allenatore della squadra
        squadraDAO.inviaRichiestaASquadra(squadra, utente);
    }
}

