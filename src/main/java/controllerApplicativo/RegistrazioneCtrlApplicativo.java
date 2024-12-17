package controllerApplicativo;

import engineering.bean.RegistrazioneBean;
import engineering.bean.UtenteBean;
import engineering.dao.UtenteDAO;
import engineering.dao.UtenteDAOJSON;
import engineering.eccezioni.EccezioneGenerica;
import modelli.Registrazione;
import modelli.Utente;

public class RegistrazioneCtrlApplicativo {

    public void inserisciUtente(RegistrazioneBean registrazioneBean) throws Exception {
        String username = registrazioneBean.getUsername();
        String email = registrazioneBean.getEmail();
        String password = registrazioneBean.getPassword();
        boolean isAllenatore = registrazioneBean.getAlleantore();

        // Verifica che tutti i campi siano compilati
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            throw new Exception("Tutti i campi sono obbligatori!");
        }

        // Creazione del modello registrazione
        Registrazione registrazione = new Registrazione(username, email, password, isAllenatore);




        // Utilizzo del DAO per salvare l'utente
        UtenteDAO UtenteDAO = new UtenteDAOJSON();
        try {
            UtenteDAO.inserisciUtenteDaRegistrazione(registrazione);
            System.out.println("Registrazione avvenuta con successo!");
        } catch (EccezioneGenerica e) {
            throw new Exception("Errore durante la registrazione: " + e.getMessage());
        }
    }
}