package controllerApplicativo;

import engineering.bean.LoginBean;
import engineering.dao.UtenteDAO;
import engineering.dao.UtenteDAOJSON;
import modelli.Allenamento;
import modelli.Login;

public class LoginControllerApplicativo {

    public LoginControllerApplicativo() {
    }

    public void verificaCredenziali(LoginBean loginBean) {

        //controllo delle credenziali
        try {
            UtenteDAO dao= new UtenteDAOJSON();             //INIZIO PER SEMPLICITà DICENDO CHE SCRIVO SU JSON
            Login login= new Login(loginBean.getEmail(), loginBean.getPassword());
            dao.recuperaUtenteDaLoginBean(login);

        }

        catch (Exception e)
        {
            e.printStackTrace();
        }


        //se le credenziali sono corrette si passa alla schermata successiva
        //altrimenti si rimane sulla stessa schermata
        if(loginBean.getEmail().equals("admin") && loginBean.getPassword().equals("admin"))
        {
            //passaggio alla schermata successiva
            System.out.println("Login effettuato con successo");
        }
        else
        {
            //rimanere sulla stessa schermata
            System.out.println("Credenziali errate");
        }
    }

    public void creazioneUtente(LoginBean loginBean) {
        //ci occupiamo di inizializzare tutti le entità che fanno parte del caso d'uso

        //creazione dell'utente

        //richiedo al DB i dati dei singoli allenamenti inerenti all'utente

        //creazione dell'allenamento
        Allenamento allenamento = new Allenamento("12/12/2020", "1h");

        //aggiunta dell'allenamento selezionato all'utente per ogni allenamento nel DB per quell'utente





    }

}
