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

    public void creazioneSquadra(String nomeSquadra) throws EccezioneSquadraInvalida {
        Memoria istanza = Memoria.getInstance();
        Utente utente = istanza.getUtenteCorrente();

        try {
            SquadraDAO squadraDAO = DAOFactory.getDAOFactory().createSquadraDAO();

            if (squadraDAO.verificaEsistenzaSquadra(nomeSquadra)) {
                throw new EccezioneSquadraInvalida("squadra esistente");
            }

            Squadra squadra = new Squadra(nomeSquadra, utente.getEmail());
            utente.setSquadra(squadra);

            squadraDAO.creaSquadraPerAllenatore(utente, utente.getSquadra());
        } catch (EccezioneSquadraInvalida e) {
            utente.setSquadra(new Squadra());
            throw new EccezioneSquadraInvalida(e.getMessage());
        }
    }
}