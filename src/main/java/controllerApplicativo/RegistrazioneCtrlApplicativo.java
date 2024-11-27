package controllerapplicativo;

import engineering.bean.RegistrazioneBean;

public class RegistrazioneCtrlApplicativo {

    public void registraUtente(RegistrazioneBean registrazioneBean) throws Exception {
        String username = registrazioneBean.getUsername();
        String email = registrazioneBean.getEmail();
        String password = registrazioneBean.getPassword();
        //boolean isAllenatore = registrazioneBean.isAllenatore(); // Non è stato definito il metodo isAllenatore

        // logica per la registrazione
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            throw new Exception("Tutti i campi sono obbligatori!");
        }

        // Logica per salvare l'utente nel database
        // ...

        System.out.println("Registrazione avvenuta con successo!");
    }
}