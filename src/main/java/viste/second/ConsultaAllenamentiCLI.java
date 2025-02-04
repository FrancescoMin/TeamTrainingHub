package viste.second;

import ctrl_applicativo.ConsultaAllenamentiCtrlApplicativo;
import engineering.bean.AllenamentoBean;

import java.util.ArrayList;
import java.util.List;

public class ConsultaAllenamentiCLI extends GenericaCLI {
    public ConsultaAllenamentiCLI() {
        this.pagina= "Consulta Allenamenti";
    }
    private static Class paginaHome;

    public static void setPaginaHome(Class paginaHome) {
        ConsultaAllenamentiCLI.paginaHome = paginaHome;
    }

    public static Class getPaginaHome() {
        return paginaHome;
    }

    @Override
    public void start() {
        stampaPagina();

        boolean ciclo = true;
        List<AllenamentoBean> allenamenti = new ArrayList<>();

        ConsultaAllenamentiCtrlApplicativo consultaallenamentictrlapplicativo = new ConsultaAllenamentiCtrlApplicativo();

        while(ciclo) {
            try {
                //ottengo dal controller applicativo la lista delle richieste in attesa aggiornata
                allenamenti = consultaallenamentictrlapplicativo.getAllAllenamenti();

                if (allenamenti.isEmpty()) {
                    System.out.println("Non ci sono allenamenti disponibili");
                }
                else{
                    System.out.println("Allenamenti a cui sei iscritto:");
                    for (int i = 1; i < (allenamenti.size() + 1); i++) {
                        AllenamentoBean allenamento = allenamenti.get(i-1);
                        System.out.println("Posizione " + i + ": Data " + allenamento.getData() + " Orario Inizio " + allenamento.getOrarioInizio() + " Orario Fine " + allenamento.getOrarioFine() + " Descrizione " + allenamento.getDescrizione());
                    }
                }
                System.out.println("Se si desidera tornare alla pagina precedente premere un tasto qualsiasi");
                scanner.nextLine();
                spostamento(getPaginaHome().getName());
                ciclo = false;
            } catch (Exception e) {
                System.out.println("Errore: " + e.getMessage());
            }
        }

    }
}
