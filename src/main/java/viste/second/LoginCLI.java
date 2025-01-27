package viste.second;

import ctrlApplicativo.LoginCtrlApplicativo;
import engineering.bean.AllenatoreBean;
import engineering.bean.GiocatoreBean;
import engineering.bean.LoginBean;
import engineering.bean.UtenteBean;
import engineering.eccezioni.EccezioneGenerica;
import java.util.Scanner;

public class LoginCLI {

    private final LoginCtrlApplicativo loginCtrl;

    // Costruttore che accetta il controller applicativo
    public LoginCLI(LoginCtrlApplicativo loginCtrl) {
        this.loginCtrl = loginCtrl;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        boolean continua = true;

        System.out.println("\n// ------- Login ------- //");

        while (continua) {
            System.out.print("Inserisci email: ");
            String email = scanner.nextLine();

            System.out.print("Inserisci password: ");
            String password = scanner.nextLine();

            LoginBean loginBean = new LoginBean(email, password);

            try {
                // Verifica credenziali
                if (loginCtrl.verificaCredenziali(loginBean)) {
                    System.out.println("Login effettuato con successo!");

                    // Recupero utente
                    UtenteBean utente = loginCtrl.recuperoUtente(loginBean);

                    // Passa alla homepage in base al tipo di utente
                    if (utente instanceof engineering.bean.AllenatoreBean) {
                        HomepageAllenatoreCLI homepageAllenatore = new HomepageAllenatoreCLI(
                                (engineering.bean.AllenatoreBean) utente,
                                loginCtrl,
                                new ctrlApplicativo.RegistrazioneCtrlApplicativo()
                        );
                        homepageAllenatore.start();
                    } /* --------- DA SISTEMARE -----------
                    else if (utente instanceof engineering.bean.GiocatoreBean) {
                        HomepageGiocatoreCLI homepageGiocatore = new HomepageGiocatoreCLI(
                                (engineering.bean.GiocatoreBean) utente,
                                loginCtrl,
                                new ctrlApplicativo.RegistrazioneCtrlApplicativo()
                        );
                        homepageGiocatore.start();
                    }*/

                    // Interrompi il loop di login
                    continua = false;
                } else {
                    System.out.println("Credenziali errate. Riprova.");
                }
            } catch (EccezioneGenerica e) {
                System.err.println("Errore durante il login: " + e.getMessage());
            }
        }
    }
}

