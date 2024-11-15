package controllerApplicativo;

import engineering.bean.LoginBean;
import engineering.dao.UtenteDAO;
import engineering.dao.UtenteDAOJSON;
import engineering.eccezioni.UtenteNonEsistenteEccezione;
import modelli.Allenamento;
import modelli.Login;
import modelli.Utente;

public class LoginControllerApplicativo {

    public LoginControllerApplicativo() {
    }

    public void verificaCredenziali(LoginBean loginBean) {

        //controllo delle credenziali
        try {
            UtenteDAO dao= new UtenteDAOJSON();             //INIZIO PER SEMPLICITà DICENDO CHE SCRIVO SU JSON
            Login login= new Login(loginBean.getEmail(), loginBean.getPassword());
            Utente utente = dao.recuperaUtenteDaLoginBean(login);

            System.out.println("Utente trovato");
            System.out.println("username: "+utente.getUsername());
            System.out.println("email: "+utente.getEmail());

        }

        catch (Exception e)
        {
            throw new UtenteNonEsistenteEccezione(e.getMessage());
        }


        //se le credenziali sono corrette si passa alla schermata successiva


        //altrimenti si rimane sulla stessa schermata
        //la stampa a scherma è gia gestita dal controller grafico

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
