package ctrl_applicativo;

import engineering.dao.SquadraDAO;
import engineering.eccezioni.EccezioneSquadraInvalida;
import engineering.pattern.Memoria;
import engineering.pattern.abstract_factory.DAOFactory;
import modelli.Squadra;
import modelli.Utente;

public class RichiediIngressoSquadraCtrlApplicativo {

    private final Memoria istanza = Memoria.getInstance();

    public RichiediIngressoSquadraCtrlApplicativo() {
        // Costruttore vuoto di default
    }

    public boolean verificaEsistenzaSquadra(String nomeSquadra) throws EccezioneSquadraInvalida {
        if (nomeSquadra == null || nomeSquadra.trim().isEmpty()) {
            throw new EccezioneSquadraInvalida("Il nome della squadra non può essere vuoto.");
        }
        SquadraDAO squadraDAO = DAOFactory.getDAOFactory().createSquadraDAO();
        return squadraDAO.verificaEsistenzaSquadra(nomeSquadra);
    }

    public void inviaRichiestaAllaSquadra(String nomeSquadra) throws EccezioneSquadraInvalida {
        SquadraDAO squadraDAO = DAOFactory.getDAOFactory().createSquadraDAO();
        Utente utente = istanza.getUtenteCorrente();

        Squadra squadra = squadraDAO.ottieniSquadraDaNome(nomeSquadra);

        for (Utente u : squadra.getRichiesteIngresso()) {
            if (u.getEmail().equals(utente.getEmail())) {
                throw new EccezioneSquadraInvalida("Hai già inviato una richiesta a questa squadra");
            }
        }

        squadra.getRichiesteIngresso().add(utente);
        squadraDAO.aggiornaSquadra(squadra);
    }
}