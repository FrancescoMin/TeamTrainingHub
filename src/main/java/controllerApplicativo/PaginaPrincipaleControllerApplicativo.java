package controllerApplicativo;

import engineering.bean.UtenteBean;
import engineering.eccezioni.EccezioneGenerica;
import viste.first.PaginaPrincipaleControllerGrafico;
import viste.first.utils.CambioScena;

import static viste.first.utils.FxmlFileName.PAGINA_CREAZIONE_SQUADRA;

public class PaginaPrincipaleControllerApplicativo
{

    public PaginaPrincipaleControllerApplicativo() {
    }

    public void CreaSquadra(UtenteBean utenteBean) throws EccezioneGenerica{
        System.out.println("Inizializzazione della Creazione della Squadra");

        //implemento i controlli sull'allenatore che richiedere la creazione della Squadra
        if (utenteBean.getSquadra()!=null) {
            System.out.println("L'allenatore ha già una squadra");
        } else {
            System.out.println("L'allenatore non ha una squadra e procedo alla creazione della squadra");

            //compio il cambio della scena
            //Nota che il cambio scena deve avvenire qui. Nel codice temporaneo do la responsabilità al controller grafico
            PaginaPrincipaleControllerGrafico paginaPrincipaleControllerGrafico = new PaginaPrincipaleControllerGrafico();

            System.out.println("Cambio scena alla creazione della squadra");

            paginaPrincipaleControllerGrafico.CodiceTemporaneoPerCambioScena();
        }
    }
}
