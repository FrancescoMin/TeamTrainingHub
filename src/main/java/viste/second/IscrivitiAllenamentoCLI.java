package viste.second;

import ctrlApplicativo.IscrivitiAllenamentoCtrlApplicativo;
import ctrlApplicativo.VisualizzaRichiesteCtrlApplicativo;
import engineering.bean.AllenamentoBean;
import engineering.bean.UtenteBean;

import java.util.List;

public class IscrivitiAllenamentoCLI extends GenericaCLI{
    public IscrivitiAllenamentoCLI(){
        this.pagina= "Iscriviti ad un Allenamento";
    }

    @Override
    public void start(){
        stampaPagina();

        boolean ciclo = true;
        List<AllenamentoBean> allenamenti;

            while(ciclo) {
                try {
                    IscrivitiAllenamentoCtrlApplicativo iscrivitiAllenamentoCtrlApplicativo = new IscrivitiAllenamentoCtrlApplicativo();
                    //ottengo dal controller applicativo la lista delle richieste in attesa aggiornata
                    allenamenti = iscrivitiAllenamentoCtrlApplicativo.caricaAllenamenti();

                    if (allenamenti.isEmpty()) {
                        System.out.println("Non ci sono richieste in attesa");
                        ciclo = false;
                        throw new Exception();
                    }
                    else{
                        System.out.println("Richieste in attesa:");
                        for (int i = 1; i <= (allenamenti.size()); i++) {
                            AllenamentoBean allenamento = allenamenti.get(i-1);
                            System.out.println("Posizione " + i + ": Data " + allenamento.getData() + " Ora inizio " + allenamento.getOrarioInizio() + " Ora fine " + allenamento.getOrarioFine() + " Descrizione " + allenamento.getDescrizione());
                        }
                    }

                    System.out.println("Inserire il numero dell'allenamento da accettare oppure 0 per uscire");

                    int scelta = scanner.nextInt();
                    scanner.nextLine();

                    //caso in cui si vuole uscire dal ciclo e tornare alla homepage allenatore
                    if (scelta < 0 || scelta > allenamenti.size()) {
                        System.out.println("Scelta non valida, se si desidera uscire premere 0 se no premere un numero qualsiasi");
                        scelta = scanner.nextInt();
                        scanner.nextLine();
                    }
                    else if (scelta == 0) {
                        ciclo = false;
                    }

                    //caso in cui si vuole accettare o rifiutare la richiesta
                    else {

                        System.out.println("Sicuro di voler accettare l'allenamento in data " + allenamenti.get(scelta+1).getData() + " alle ore " + allenamenti.get(scelta).getOrarioInizio() + " fino alle ore " + allenamenti.get(scelta).getOrarioFine() + " con descrizione " + allenamenti.get(scelta).getDescrizione() + "?");
                        System.out.println("Inserire 'accetta' per accettare, un'altra stringa per rifiutare");
                        String risposta = scanner.next().toLowerCase();

                        if (risposta.equals("accetta")) {
                            iscrivitiAllenamentoCtrlApplicativo.accettaAllenamento(allenamenti.get(scelta));
                        } else {
                            System.out.println("Scelta non valida");
                        }
                    }
                }
                catch (Exception e) {
                    System.out.println("Errore: " + e.getMessage());
                }
            }

        spostamento(HomepageGiocatoreCLI.class.getName());

    }
}
