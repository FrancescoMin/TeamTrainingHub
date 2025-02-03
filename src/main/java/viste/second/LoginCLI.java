package viste.second;

import ctrlApplicativo.LoginCtrlApplicativo;
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

        boolean continua = true;

        stampaPagina();


        LoginCtrlApplicativo loginCtrl = new LoginCtrlApplicativo();

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

                LoginBean loginBean = new LoginBean(email, password);

                try {
                    // Verifica credenziali
                    if (loginCtrl.verificaCredenziali(loginBean)) {
                        System.out.println("Login effettuato con successo!");

                        // Recupero utente
                        UtenteBean utenteBean = loginCtrl.recuperoUtente(loginBean);
                        if (utenteBean.getAllenatore()) {
                            prossimaPagina = HomepageAllenatoreCLI.class.getName();
                        } else {
                            prossimaPagina = HomepageGiocatoreCLI.class.getName();
                        }

                        // Interrompi il loop di login
                        continua = false;
                    } else {
                        System.out.println("Credenziali errate. Riprova.");
                    }

                } catch (EccezioneCambioScena e) {
                    System.err.println(e.getMessage());
                }
                catch (EccezionePasswordErrata e) {
                    System.err.println("Username o password errate, riprovare.");
                }
            }
            catch (Exception e) {
                System.err.println("Errore durante il login: " + e.getMessage());
            }
        }
        spostamento(prossimaPagina);
    }
}

