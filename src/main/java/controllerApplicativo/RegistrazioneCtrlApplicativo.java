package controllerApplicativo;

import engineering.bean.RegistrazioneBean;
import engineering.bean.UtenteBean;
import engineering.dao.UtenteDAO;
import modelli.Utente;

public class RegistrazioneCtrlApplicativo {

    public void inserisciUtente(RegistrazioneBean registrazioneBean) throws Exception {
        String username = registrazioneBean.getUsername();
        String email = registrazioneBean.getEmail();
        String password = registrazioneBean.getPassword();
        //boolean isAllenatore = registrazioneBean.isAllenatore(); // Non è stato definito il metodo isAllenatore

        // logica per la registrazione
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            throw new Exception("Tutti i campi sono obbligatori!");
        }

        //UtenteBean UtenteBean = new Utente(username, email);
        //UtenteBean.setPassword(password);
        //UtenteBean.getAllenatore(isAllenatore);

        //UtenteDAO utenteDAO = new UtenteDAO();
        //utenteDAO.inserisciUtenteDaRegistrazione(RegistrazioneBean);


        System.out.println("Registrazione avvenuta con successo!");
    }
}