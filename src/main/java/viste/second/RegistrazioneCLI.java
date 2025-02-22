package viste.second;

import ctrl_applicativo.RegistrazioneCtrlApplicativo;
import engineering.bean.RegistrazioneBean;
import engineering.eccezioni.EccezioneUtenteInvalido;


/**
 * Questa classe gestisce l'interfaccia a riga di comando (CLI) per la registrazione di un nuovo utente.
 */

// Classe che gestisce l'interfaccia a riga di comando per la registrazione di un nuovo utente
public class RegistrazioneCLI extends GenericaCLI {

    public RegistrazioneCLI() {
        this.pagina = "Registrazione";
    }

    @Override
    public void start() {

        stampaPagina();

        String scelta;
        RegistrazioneBean registrazionebean = null;


        while (continua) {

            try {
                System.out.println("Cosa vuoi fare?");
                System.out.println("Per tornare al Login premere 1");
                System.out.println("Per continuare la registrazione premere un tasto qualsiasi");
                scelta = scanner.nextLine();

                if(scelta.equals("1")) {
                    prossimaPagina = LoginCLI.class.getName();
                    break;
                }

                System.out.print("Inserisci username: ");
                String username = scanner.nextLine();

                System.out.print("Inserisci email: ");
                String email = scanner.nextLine();

                System.out.print("Inserisci password: ");
                String password = scanner.nextLine();

                System.out.print("Vuoi registrarti come allenatore? (s/n): ");
                String risposta = scanner.nextLine().toLowerCase();

                while(!risposta.equals("s") && !risposta.equals("n")){
                    System.out.print("Inserisci una risposta valida (s/n): ");
                    risposta = scanner.nextLine().toLowerCase();
                }

                boolean isAllenatore = risposta.equals("s");

                // Creazione del bean di registrazione
                registrazionebean = new RegistrazioneBean(username, email, password, isAllenatore);

                RegistrazioneCtrlApplicativo registrazionectrlapplicativo = new RegistrazioneCtrlApplicativo();
                registrazionectrlapplicativo.inserisciUtente(registrazionebean);

                if (registrazionebean.getAllenatore()) {
                    prossimaPagina = HomepageAllenatoreCLI.class.getName();
                }
                else {
                    prossimaPagina = HomepageGiocatoreCLI.class.getName();
                }

                System.out.println("Registrazione effettuata con successo!");
                continua = false; // Fine registrazione

            } catch (EccezioneUtenteInvalido e) {
                System.err.println("Errore durante la registrazione: " + e.getMessage());
                System.out.println("Riprova la registrazione.");
                System.out.println("Se hai un account e vuoi fare il login, premi 1. Altrimenti inserire un numero qualsiasi e riprova.");
            }
        }
        spostamento(prossimaPagina);
    }
}
