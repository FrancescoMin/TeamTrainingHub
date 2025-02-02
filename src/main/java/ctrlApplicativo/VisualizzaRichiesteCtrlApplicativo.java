package ctrlApplicativo;

import engineering.bean.GiocatoreBean;
import engineering.bean.UtenteBean;
import engineering.dao.SquadraDAO;
import engineering.dao.UtenteDAO;
import engineering.eccezioni.EccezioneSquadraInvalida;
import engineering.eccezioni.EccezioneUtenteInvalido;
import engineering.pattern.Singleton;
import engineering.pattern.abstract_factory.DAOFactory;

import java.util.ArrayList;
import java.util.List;
import modelli.Squadra;
import modelli.Utente;

public class VisualizzaRichiesteCtrlApplicativo {

    //devo recuperare la squadra dell'utente (allenatore) che sta accettando la richiesta
    Singleton istanza = Singleton.getInstance();
    Squadra squadra = istanza.getUtenteCorrente().getSquadra();

    public void accettaRichiesta(UtenteBean utenteBean) throws EccezioneSquadraInvalida, EccezioneUtenteInvalido {
        try {

            update(utenteBean, true);
        }
        catch (EccezioneSquadraInvalida e) {
            throw new EccezioneSquadraInvalida(e.getMessage());
        }
        catch (EccezioneUtenteInvalido e) {
            throw new EccezioneUtenteInvalido(e.getMessage());
        }
    }

    public void rifiutaRichiesta(UtenteBean utenteBean) throws EccezioneSquadraInvalida, EccezioneUtenteInvalido {
        try {
            // Show an alert to indicate refusal
            update(utenteBean, false);
        }
        catch (EccezioneSquadraInvalida e) {
            throw new EccezioneSquadraInvalida(e.getMessage());
        }
        catch (EccezioneUtenteInvalido e) {
            throw new EccezioneUtenteInvalido(e.getMessage());
        }
    }

    public void update(UtenteBean utenteBean, Boolean accettato) throws EccezioneSquadraInvalida, EccezioneUtenteInvalido {
        try {
            Utente utente = null;

            //qualsiasi modalità io sia, cerco sicuramente l'utente nella lista delle richieste in ingresso e lo elimino
            for (int i = 0; i < squadra.getRichiesteIngresso().size(); i++) {
                //cerco l'utente nella lista dall'email
                if (squadra.getRichiesteIngresso().get(i).getEmail().equals(utenteBean.getEmail())) {
                    //recupero l'utente dalla lista delle richieste in ingresso della squadra
                    utente = squadra.getRichiesteIngresso().get(i);

                    //rimuovo l'utente dalla lista delle richieste in ingresso
                    squadra.getRichiesteIngresso().remove(utente);

                    //se l'utente ha già una squadra, cancello la richiesta di iscrizione e lancio un'eccezione
                    if (!utente.getSquadra().getNome().isEmpty()) {
                        throw new EccezioneSquadraInvalida("L'utente è stato accettato in un'altra squadra");
                    }

                    //se l'utente è stato accettato, lo aggiungo alla squadra
                    if (accettato) {
                        utente.setSquadra(squadra);
                    }
                    //se ho trovato l'utente posso uscire dal ciclo
                    break;
                }
            }

            //adesso, se sto utilizzando il dao, aggiorno la persistenza per poter salvare le modifiche
            if (!istanza.getDemo()) {
                //se non siamo in modalità demo, aggiorno il database con le modifiche all'utente
                UtenteDAO utenteDao = DAOFactory.getDAOFactory().createUtenteDAO();
                utenteDao.aggiornaUtente(utente);

                //aggiorno anche la squadra
                SquadraDAO squadraDao = DAOFactory.getDAOFactory().createSquadraDAO();
                squadraDao.aggiornaSquadra(squadra);
            }
        }
        catch (EccezioneSquadraInvalida e) {
            throw new EccezioneSquadraInvalida(e.getMessage());
        }
        catch (EccezioneUtenteInvalido e) {
            throw new EccezioneUtenteInvalido(e.getMessage());
        }
    }

    public List<UtenteBean> getRichiesteIngresso() {
        Singleton istanza = Singleton.getInstance();
        Squadra squadra = istanza.getUtenteCorrente().getSquadra();

        List<UtenteBean> richiesteIngresso = new ArrayList<>();
        for (Utente utente : squadra.getRichiesteIngresso()) {
            UtenteBean utenteBean = new GiocatoreBean(utente.getUsername(), utente.getEmail(), utente.getPassword(), utente.getAllenamenti());
            richiesteIngresso.add(utenteBean);
        }
        return richiesteIngresso;
    }
}
