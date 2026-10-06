package ctrl_applicativo;

import engineering.bean.AllenatoreBean;
import engineering.bean.GiocatoreBean;
import engineering.bean.UtenteBean;
import engineering.dao.SquadraDAO;
import engineering.dao.UtenteDAO;
import engineering.eccezioni.EccezioneSquadraInvalida;
import engineering.eccezioni.EccezioneUtenteInvalido;
import engineering.pattern.Memoria;
import engineering.pattern.abstract_factory.DAOFactory;

import java.util.ArrayList;
import java.util.List;
import modelli.Squadra;
import modelli.Utente;

public class GestisciRichiesteCtrlApplicativo {

    private final Memoria istanza = Memoria.getInstance();
    private final Squadra squadra = istanza.getUtenteCorrente().getSquadra();

    public List<UtenteBean> recuperaUtentiBean() {
        List<UtenteBean> utentiBean = new ArrayList<>();
        List<Utente> utenti = squadra.getRichiesteIngresso();
        for (Utente utente : utenti) {
            UtenteBean utenteBean;
            if (utente.getAllenatore()) {
                utenteBean = new AllenatoreBean(utente.getUsername(), utente.getEmail(), utente.getPassword(), utente.getAllenamenti(), utente.getSquadra());
            } else {
                utenteBean = new GiocatoreBean(utente.getUsername(), utente.getEmail(), utente.getPassword(), utente.getAllenamenti(), utente.getSquadra());
            }
            utentiBean.add(utenteBean);
        }
        return utentiBean;
    }

    public void accettaRichiesta(UtenteBean utenteBean) throws EccezioneSquadraInvalida, EccezioneUtenteInvalido {
        update(utenteBean, true);
    }

    public void rifiutaRichiesta(UtenteBean utenteBean) throws EccezioneSquadraInvalida, EccezioneUtenteInvalido {
        update(utenteBean, false);
    }

    public void update(UtenteBean utenteBean, boolean accettato) throws EccezioneSquadraInvalida, EccezioneUtenteInvalido {
        Utente utente = null;

        for (int i = 0; i < squadra.getRichiesteIngresso().size(); i++) {
            if (squadra.getRichiesteIngresso().get(i).getEmail().equals(utenteBean.getEmail())) {
                utente = squadra.getRichiesteIngresso().get(i);
                squadra.getRichiesteIngresso().remove(utente);

                if (!utente.getSquadra().getNome().isEmpty()) {
                    SquadraDAO squadraDao = DAOFactory.getDAOFactory().createSquadraDAO();
                    squadraDao.aggiornaSquadra(squadra);
                    throw new EccezioneSquadraInvalida("L'utente è stato accettato in un'altra squadra");
                } else if (accettato) {
                    utente.setSquadra(squadra);
                }
                break;
            }
        }

        SquadraDAO squadraDao = DAOFactory.getDAOFactory().createSquadraDAO();
        squadraDao.aggiornaSquadra(squadra);

        if (utente != null) {
            UtenteDAO utenteDao = DAOFactory.getDAOFactory().createUtenteDAO();
            utenteDao.aggiornaUtente(utente);
        }
    }

    public List<UtenteBean> ottieniRichiesteIngresso() {
        List<UtenteBean> richiesteIngresso = new ArrayList<>();
        for (Utente utente : squadra.getRichiesteIngresso()) {
            UtenteBean utenteBean = new GiocatoreBean(utente.getUsername(), utente.getEmail(), utente.getPassword());
            richiesteIngresso.add(utenteBean);
        }
        return richiesteIngresso;
    }
}