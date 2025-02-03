package viste.first;

import ctrl_applicativo.HomepageAllenatoreCtrlApplicativo;
import engineering.eccezioni.EccezioneCambioScena;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import viste.first.utils.CambioScena;

import static viste.first.utils.FxmlFileName.*;

public class HomepageAllenatoreCtrlGrafico {


    public void initialize() {
        System.out.println("Inizializzazione Temporanea della Pagina Principale");
    }

    @FXML
    private Label mostraErrori;

    @FXML
    private Button gestisciSquadra;

    @FXML
    private void VisualizzaRichiestePartecipazione(){
        System.out.println("Visualizza Richieste Partecipazione");
        HomepageAllenatoreCtrlApplicativo homepageallenatorectrlapplicativo = new HomepageAllenatoreCtrlApplicativo();

        if(homepageallenatorectrlapplicativo.esisteSquadra()) {
            //cambio scena alla pagina di visualizzazione delle richieste di partecipazione
            try {
                Cambio(PAGINA_VISUALIZZA_RICHIESTE_PARTECIPAZIONE);
            }
            catch (EccezioneCambioScena e) {
                MostraErrore(e.getMessage());
            }
        }
        else {
            MostraErrore("Non hai una squadra, non puoi visualizzare le richieste di partecipazione");
        }
    }


    @FXML
    protected void CreaAllenamento() {

        System.out.println("Creazione Allenamento");

        HomepageAllenatoreCtrlApplicativo homepageallenatorectrlapplicativo = new HomepageAllenatoreCtrlApplicativo();
        if(homepageallenatorectrlapplicativo.esisteSquadra()) {
            //cambio scena alla pagina di creazione dell'allenamento
            try {
                Cambio(PAGINA_CREAZIONE_ALLENAMENTO);

            } catch (EccezioneCambioScena e) {
                MostraErrore(e.getMessage());
            }
        }
        else {
            MostraErrore("Non hai una squadra, non puoi creare un allenamento");
        }
    }

    @FXML
    protected void GestisciSquadra() {
        //controlliamo che l'allenatore abbia o meno una squadra chiedendolo al controllore applicativo
        HomepageAllenatoreCtrlApplicativo homepageallenatorectrlapplicativo = new HomepageAllenatoreCtrlApplicativo();

        if (homepageallenatorectrlapplicativo.esisteSquadra()) {
            //Se l'allenatore ha una squadra allora compiamo il cambio scena alla pagina di gestione della squadra
            MostraErrore("Sei già in possesso di una squadra");
        }
        else {
            //Se l'allenatore non ha una squadra allora compiamo il cambio scena alla pagina di creazione della squadra

            //cambio scena alla prima vista dove compiamo la creazione della squadra
            try {
                Cambio(PAGINA_CREAZIONE_SQUADRA);

            } catch (EccezioneCambioScena e) {
                MostraErrore(e.getMessage());
            }
        }
    }

    private void Cambio(String paginaDiCambioScena) {
        try {
            Stage stage = (Stage) gestisciSquadra.getScene().getWindow();
            CambioScena cambioScena = new CambioScena();
            cambioScena.cambioScena(stage, paginaDiCambioScena);

        } catch (EccezioneCambioScena e) {
            throw new EccezioneCambioScena(e.getMessage());
        }
    }

    private void MostraErrore(String messaggio) {
        mostraErrori.setText(messaggio);
        mostraErrori.setVisible(true);
    }
}
