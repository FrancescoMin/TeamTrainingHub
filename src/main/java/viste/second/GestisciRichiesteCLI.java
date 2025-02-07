package viste.second;

import ctrl_applicativo.GestisciRichiesteCtrlApplicativo;
import engineering.bean.UtenteBean;
import engineering.eccezioni.EccezioneSquadraInvalida;
import engineering.eccezioni.EccezioneUtenteInvalido;

import java.util.List;

public class GestisciRichiesteCLI extends GenericaCLI{

    public GestisciRichiesteCLI() {
        this.pagina = "Visualizza Richieste";
    }

    @Override
    public void start() {

        stampaPagina();

        List<UtenteBean> utenti;

        GestisciRichiesteCtrlApplicativo visualizzarichiestectrlapplicativo = new GestisciRichiesteCtrlApplicativo();

        while(continua) {
            try {
                //ottengo dal controller applicativo la lista delle richieste in attesa aggiornata
                utenti = visualizzarichiestectrlapplicativo.ottieniRichiesteIngresso();

                controlloUtenti(utenti);

                System.out.println("Inserire il numero del giocatore da accettare o rifiutare oppure 0 per uscire");
                int scelta = scanner.nextInt();
                scanner.nextLine();

                //caso in cui si vuole uscire dal ciclo e tornare alla homepage allenatore
                controlloScelta(utenti, scelta);

            }
            catch (EccezioneSquadraInvalida | EccezioneUtenteInvalido e) {
                System.out.println(e.getMessage());
            }
        }
        spostamento(HomepageAllenatoreCLI.class.getName());

    }
    private void controlloUtenti(List<UtenteBean> utenti) {
        if (!utenti.isEmpty()) {
            System.out.println("Richieste in attesa:");
            for (int i = 1; i < (utenti.size() + 1); i++) {
                UtenteBean utente = utenti.get(i);
                System.out.println("Posizione " + i + ": Username " + utente.getUsername() + " Email " + utente.getEmail());
            }
        } else {
            System.out.println("Non ci sono richieste in attesa");
        }
    }

    private void controlloScelta(List<UtenteBean> utenti, int scelta) throws EccezioneSquadraInvalida, EccezioneUtenteInvalido {
        GestisciRichiesteCtrlApplicativo visualizzarichiestectrlapplicativo = new GestisciRichiesteCtrlApplicativo();

        if (scelta < 1 || scelta > utenti.size()) {
            System.out.println("Scelta non valida, se si desidera uscire premere 0 se no premere un numero qualsiasi");
            scelta = scanner.nextInt();

            if (scelta == 0) {
                this.continua = false;
                this.prossimaPagina = HomepageAllenatoreCLI.class.getName();
            }
        }
        //caso in cui si vuole accettare o rifiutare la richiesta
        else {

            System.out.println("Vuoi accettare o rifiutare la richiesta? (accetta/rifiuta)");
            String risposta = scanner.next().toLowerCase();

            if (risposta.equals("accetta")) {
                visualizzarichiestectrlapplicativo.accettaRichiesta(utenti.get(scelta));
            } else if (risposta.equals("rifiuta")) {
                visualizzarichiestectrlapplicativo.rifiutaRichiesta(utenti.get(scelta));
            } else {
                System.out.println("Scelta non valida");
            }
        }
    }
}
