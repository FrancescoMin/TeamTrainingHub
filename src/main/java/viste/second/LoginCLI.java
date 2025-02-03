package viste.second;

import ctrlApplicativo.LoginCtrlApplicativo;
import engineering.bean.LoginBean;
import engineering.bean.UtenteBean;
import engineering.eccezioni.EccezioneCambioScena;

public class LoginCLI extends GenericaCLI {

    public LoginCLI () {
        this.pagina = "Login";
    }

    @Override
    public void start() {

        boolean continua = true;

        stampaPagina();
        System.out.println("Per registrarsi premere 1");
        System.out.println("Per continuare con il login premere un numero qualsiasi");

        int scelta = scanner.nextInt();
        scanner.nextLine();

        if(scelta == 1) {
            RegistrazioneCLI registrazioneCLI = new RegistrazioneCLI();
            registrazioneCLI.start();
        }

        LoginCtrlApplicativo loginCtrl = new LoginCtrlApplicativo();

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
                    UtenteBean utenteBean = loginCtrl.recuperoUtente(loginBean);
                    if (utenteBean.getAllenatore())     {prossimaPagina=HomepageAllenatoreCLI.class.getName();}
                    else                            {prossimaPagina= HomepageGiocatoreCLI.class.getName() ;}

                    // Interrompi il loop di login
                    continua = false;
                } else {
                    System.out.println("Credenziali errate. Se non hai un account e vuoi registrarti, premi 1. Altrimenti inserire un numero qualsiasi e riprova.");
                    scelta = scanner.nextInt();
                    if(scelta == 1) {
                        prossimaPagina = RegistrazioneCLI.class.getName();
                        continua = false;
                    }
                }
            } catch (EccezioneCambioScena e) {
                System.err.println("Errore durante il login: " + e.getMessage());
            }
        }
        spostamento(prossimaPagina);
    }
}

