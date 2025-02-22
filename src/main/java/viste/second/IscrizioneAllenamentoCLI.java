package viste.second;

import ctrl_applicativo.IscrizioneAllenamentoCtrlApplicativo;
import engineering.bean.AllenamentoBean;
import engineering.eccezioni.EccezioneAllenamentoInvalido;

import java.util.List;

public class IscrizioneAllenamentoCLI extends GenericaCLI{
    public IscrizioneAllenamentoCLI(){
        this.pagina= "Iscriviti ad un Allenamento";
    }

    @Override
    public void start(){
        stampaPagina();

        List<AllenamentoBean> allenamenti;

            while(continua) {
                try {

                    IscrizioneAllenamentoCtrlApplicativo iscrivitiallenamentoctrlapplicativo = new IscrizioneAllenamentoCtrlApplicativo();
                    //ottengo dal controller applicativo la lista delle richieste in attesa aggiornata
                    allenamenti = iscrivitiallenamentoctrlapplicativo.caricaAllenamenti();
                    stampa(allenamenti);
                    System.out.println("Inserire il numero dell'allenamento da accettare oppure 0 per uscire");

                    int scelta = scanner.nextInt();
                    scanner.nextLine();

                    //caso in cui si vuole uscire dal ciclo e tornare alla homepage allenatore
                    if (scelta < 0 || scelta > allenamenti.size()) {
                        System.out.println("Scelta non valida, se si desidera uscire premere 0 se no premere un numero qualsiasi");
                        scelta = scanner.nextInt();
                        scanner.nextLine();
                    }
                    if (scelta == 0) {
                        continua = false;
                    }

                    //caso in cui si vuole accettare o rifiutare la richiesta
                    else {
                        gestisciAllenamento(allenamenti, scelta);
                    }
                }
                catch (Exception e) {
                    System.out.println("Errore: " + e.getMessage());
                }
            }

        spostamento(HomepageGiocatoreCLI.class.getName());

    }

    private void stampa(List<AllenamentoBean> allenamenti) throws EccezioneAllenamentoInvalido {
        if (allenamenti.isEmpty()) {
            System.out.println("Non ci sono richieste in attesa");
            this.continua = false;
            throw new EccezioneAllenamentoInvalido("Non ci sono richieste in attesa");
        }
        else{
            System.out.println("Allenamenti a cui ti puoi iscrivere:");
            for (int i = 1; i <= (allenamenti.size()); i++) {
                AllenamentoBean allenamento = allenamenti.get(i-1);
                System.out.println("Posizione " + i + ": Data " + allenamento.getData() + " Ora inizio " + allenamento.getOrarioInizio() + " Ora fine " + allenamento.getOrarioFine() + " Descrizione " + allenamento.getDescrizione());
            }
        }
    }

    private void gestisciAllenamento(List<AllenamentoBean> allenamenti, int scelta) {
        System.out.println("Sicuro di voler accettare l'allenamento in data " + allenamenti.get(scelta+1).getData() + " alle ore " + allenamenti.get(scelta).getOrarioInizio() + " fino alle ore " + allenamenti.get(scelta).getOrarioFine() + " con descrizione " + allenamenti.get(scelta).getDescrizione() + "?");
        System.out.println("Inserire 'accetta' per accettare, un'altra stringa per rifiutare");
        String risposta = scanner.next().toLowerCase();

        IscrizioneAllenamentoCtrlApplicativo iscrivitiallenamentoctrlapplicativo = new IscrizioneAllenamentoCtrlApplicativo();
        if (risposta.equals("accetta")) {
            iscrivitiallenamentoctrlapplicativo.accettaAllenamento(allenamenti.get(scelta));
        } else {
            System.out.println("Scelta non valida");
        }
    }
}
