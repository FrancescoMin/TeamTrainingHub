package viste.second;

import ctrlApplicativo.VisualizzaRichiesteCtrlApplicativo;
import engineering.bean.UtenteBean;

import java.util.List;

public class VisualizzaRichiesteCLI extends GenericaCLI{

    public VisualizzaRichiesteCLI() {
        this.pagina = "Visualizza Richieste";
    }

    @Override
    public void start() {

        stampaPagina();

        boolean ciclo = true;
        List<UtenteBean> utenti;

        VisualizzaRichiesteCtrlApplicativo visualizzaRichiesteCtrlApplicativo = new VisualizzaRichiesteCtrlApplicativo();

        while(ciclo) {
            //ottengo dal controller applicativo la lista delle richieste in attesa aggiornata
            utenti = visualizzaRichiesteCtrlApplicativo.getRichiesteIngresso();

            if (!utenti.isEmpty()) {
                System.out.println("Richieste in attesa:");
                for (int i = 1; i < (utenti.size()+1); i++) {
                    UtenteBean utente = utenti.get(i);
                    System.out.println("Posizione " + i + ": Username " + utente.getUsername() + " Email " + utente.getEmail());
                }
            }

            System.out.println("Inserire il numero del giocatore da accettare o rifiutare oppure 0 per uscire");

            int scelta = scanner.nextInt();

            //caso in cui si vuole uscire dal ciclo e tornare alla homepage allenatore
            if (scelta < 1 || scelta > utenti.size()) {
                System.out.println("Scelta non valida, se si desidera uscire premere 0 se no premere un numero qualsiasi");
                scelta = scanner.nextInt();

                if(scelta == 0) {
                    ciclo = false;
                    prossimaPagina = HomepageAllenatoreCLI.class.getName();
                }
            }

            //caso in cui si vuole accettare o rifiutare la richiesta
            else {

                System.out.println("Vuoi accettare o rifiutare la richiesta? (accetta/rifiuta)");
                String risposta = scanner.next().toLowerCase();

                if (risposta.equals("accetta")) {
                    visualizzaRichiesteCtrlApplicativo.accettaRichiesta(utenti.get(scelta));
                } else if (risposta.equals("rifiuta")) {
                    visualizzaRichiesteCtrlApplicativo.rifiutaRichiesta(utenti.get(scelta));
                } else {
                    System.out.println("Scelta non valida");
                }
            }
        }
        spostamento(HomepageAllenatoreCLI.class.getName());
    }
}
