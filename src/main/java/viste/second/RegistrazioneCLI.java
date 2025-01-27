package viste.second;

import ctrlApplicativo.RegistrazioneCtrlApplicativo;
import engineering.altro.Stampa;
import engineering.bean.LoginBean;
import engineering.bean.RegistrazioneBean;
import engineering.eccezioni.EccezioneGenerica;

import java.util.Scanner;

/**
 * Questa classe gestisce l'interfaccia a riga di comando (CLI) per la registrazione di un nuovo utente.
 */

// Classe che gestisce l'interfaccia a riga di comando per la registrazione di un nuovo utente
public class RegistrazioneCLI {

    private final RegistrazioneCtrlApplicativo registrazioneCtrl;

    // Costruttore che accetta il controller applicativo
    public RegistrazioneCLI(RegistrazioneCtrlApplicativo registrazioneCtrl) {
        this.registrazioneCtrl = registrazioneCtrl;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        boolean continua = true;

        System.out.println("\n// ------- Registrazione ------- //");

        while (continua) {
            try {
                System.out.print("Inserisci username: ");
                String username = scanner.nextLine();

                System.out.print("Inserisci email: ");
                String email = scanner.nextLine();

                System.out.print("Inserisci password: ");
                String password = scanner.nextLine();

                System.out.print("Vuoi registrarti come allenatore? (s/n): ");
                String risposta = scanner.nextLine().toLowerCase();

                boolean isAllenatore = risposta.equals("s");


                // Creazione del bean di registrazione
                RegistrazioneBean registrazioneBean = new RegistrazioneBean(username, email, password, isAllenatore);

                // Inserimento dell'utente in persistenza
                registrazioneCtrl.inserisciUtente(registrazioneBean);

                System.out.println("Registrazione effettuata con successo!");
                continua = false; // Fine registrazione
            } catch (EccezioneGenerica e) {
                System.err.println("Errore durante la registrazione: " + e.getMessage());
                System.out.println("Riprova la registrazione.");
            }
        }
    }
}
