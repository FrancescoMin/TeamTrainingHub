package viste.second;

import ctrl_applicativo.LoginCtrlApplicativo;
import engineering.bean.LoginBean;
import engineering.bean.UtenteBean;
import engineering.eccezioni.EccezioneCambioScena;
import engineering.eccezioni.EccezionePasswordErrata;

public class LoginCLI extends GenericaCLI {

    public LoginCLI () {
        this.pagina = "Login";
    }

    @Override
    public void start() {

        stampaPagina();


        while (continua) {

            try {
                System.out.println("Per registrarsi premere 1");
                System.out.println("Per continuare con il login premere un numero qualsiasi");

                String scelta = scanner.nextLine();

                if (scelta.equals("1")) {
                    prossimaPagina = RegistrazioneCLI.class.getName();
                    break;
                }

                System.out.print("Inserisci email: ");
                String email = scanner.nextLine();

                System.out.print("Inserisci password: ");
                String password = scanner.nextLine();

                LoginBean loginbean = new LoginBean(email, password);

                boh(loginbean);
            }
            catch (Exception e) {
                System.err.println("Errore durante il login: " + e.getMessage());
            }
        }
        spostamento(this.prossimaPagina);
    }

    private void boh(LoginBean loginbean) {
        try {

            LoginCtrlApplicativo loginCtrl = new LoginCtrlApplicativo();

            // Verifica credenziali
            if (loginCtrl.verificaCredenziali(loginbean)) {
                System.out.println("Login effettuato con successo!");

                // Recupero utente
                UtenteBean utenteBean = loginCtrl.recuperoUtente(loginbean);
                assegnazionePagina(utenteBean);

                // Interrompi il loop di login
                this.continua = false;
            } else {
                System.out.println("Credenziali errate.");
            }

        } catch (EccezioneCambioScena | EccezionePasswordErrata e) {
            System.err.println(e.getMessage());
        }
    }

    private void assegnazionePagina(UtenteBean utenteBean) {
        if (utenteBean.getAllenatore()) {
            this.prossimaPagina = HomepageAllenatoreCLI.class.getName();
        } else {
            this.prossimaPagina = HomepageGiocatoreCLI.class.getName();
        }
    }
}

