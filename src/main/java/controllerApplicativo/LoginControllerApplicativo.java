package controllerApplicativo;

import engineering.bean.AllenatoreBean;
import engineering.bean.GenericoBean;
import engineering.bean.GiocatoreBean;
import engineering.bean.LoginBean;
import engineering.dao.UtenteDAO;
import engineering.dao.UtenteDAOMySQL;
import engineering.eccezioni.UtenteNonEsistenteEccezione;
import modelli.Login;
import modelli.Utente;

public class LoginControllerApplicativo {

    public LoginControllerApplicativo() {
    }

    public void verificaCredenziali(LoginBean loginBean) {

        //controllo delle credenziali
        try {

            //richiedo dalla persistenza i dati relativi all'email e password inseriri
            UtenteDAO dao = new UtenteDAOMySQL();             //INIZIO PER SEMPLICITà DICENDO CHE SCRIVO SU JSON

            //creo un utente da passare all'interno del sistema
            Login login = new Login(loginBean.getUsername(), loginBean.getPassword());

            //richiedo i dati dell'utente, se esiste
            Utente utente = dao.recuperaUtenteDaLogin(login);

            System.out.println("Recupero Utente completato");

            //se l'utente è diverso da null ho trovato l'utente: creo un bean utente
            if (utente == null) {
                //gestisco il caso in cui in cui ho un utente void
            }

            //creazione del bean da passare al prossimo controllore grafico con tutti i dati dell'utente
            GenericoBean genericoBean;
            if (utente.getAllenatore()) {
                genericoBean = new AllenatoreBean(utente.getUsername(), utente.getEmail(), utente.getAllenamenti(), utente.getSquadra());
            } else
            {
                genericoBean = new GiocatoreBean(utente.getUsername(), utente.getEmail(), utente.getAllenamenti(), utente.getSquadra());
            }

            //cambio di scena passando il bean appena creato

        }

        catch (Exception e)
        {
            throw new UtenteNonEsistenteEccezione(e.getMessage());
        }
    }

    public void creazioneUtente(LoginBean loginBean) {
        //ci occupiamo di inizializzare tutti le entità che fanno parte del caso d'uso

        //creazione dell'utente

        //richiedo al DB i dati dei singoli allenamenti inerenti all'utente

        //creazione dell'allenamento


        //Allenamento allenamento = new Allenamento("12/12/2020", "1h");


        //aggiunta dell'allenamento selezionato all'utente per ogni allenamento nel DB per quell'utente

    }

}
