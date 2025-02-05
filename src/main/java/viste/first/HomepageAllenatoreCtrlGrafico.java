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
        //inizializzazione default del controllore
    }

    @FXML
    private Label mostraErrori;

    @FXML
    private Button gestisciSquadra;

    @FXML
    private void visualizzaRichiestePartecipazione(){
        HomepageAllenatoreCtrlApplicativo homepageallenatorectrlapplicativo = new HomepageAllenatoreCtrlApplicativo();

        if(homepageallenatorectrlapplicativo.esisteSquadra()) {
            //cambio scena alla pagina di visualizzazione delle richieste di partecipazione
            try {
                cambio(PAGINA_VISUALIZZA_RICHIESTE_PARTECIPAZIONE);
            }
            catch (EccezioneCambioScena e) {
                mostraErrore(e.getMessage());
            }
        }
        else {
            mostraErrore("Non hai una squadra, non puoi visualizzare le richieste di partecipazione");
        }
    }

    @FXML
    protected void modificaAllenamenti(){
        mostraErrore("Funzionalità non implementata");
    }

    @FXML
    protected void consultaAllenamenti(){// Logica per il pulsante "Consulta allenamenti"
        HomepageAllenatoreCtrlApplicativo homepageallenatorectrlapplicativo = new HomepageAllenatoreCtrlApplicativo();
        if(homepageallenatorectrlapplicativo.esisteSquadra()) {
        try {
            ConsultaAllenamentiCtrlGrafico.setPaginaHome(PAGINA_HOME_ALLENATORE);
            cambio(PAGINA_CONSULTA_ALLENAMENTI);

        } catch (EccezioneCambioScena e) {
            mostraErrore("Errore con il cambio di scena alla pagina di consultazione degli allenamenti");
        }
    }
        else {
        mostraErrore("Non sei in una squadra, non puoi consultare gli allenamenti");
    }
}

    @FXML
    protected void creaAllenamento() {

        HomepageAllenatoreCtrlApplicativo homepageallenatorectrlapplicativo = new HomepageAllenatoreCtrlApplicativo();
        if(homepageallenatorectrlapplicativo.esisteSquadra()) {
            //cambio scena alla pagina di creazione dell'allenamento
            try {
                cambio(PAGINA_CREAZIONE_ALLENAMENTO);

            } catch (EccezioneCambioScena e) {
                mostraErrore(e.getMessage());
            }
        }
        else {
            mostraErrore("Non hai una squadra, non puoi creare un allenamento");
        }
    }

    @FXML
    protected void gestisciSquadra() {
        //controlliamo che l'allenatore abbia o meno una squadra chiedendolo al controllore applicativo
        HomepageAllenatoreCtrlApplicativo homepageallenatorectrlapplicativo = new HomepageAllenatoreCtrlApplicativo();

        if (homepageallenatorectrlapplicativo.esisteSquadra()) {
            //Se l'allenatore ha una squadra allora compiamo il cambio scena alla pagina di gestione della squadra
            mostraErrore("Sei già in possesso di una squadra");
        }
        else {
            //Se l'allenatore non ha una squadra allora compiamo il cambio scena alla pagina di creazione della squadra

            //cambio scena alla prima vista dove compiamo la creazione della squadra
            try {
                cambio(PAGINA_CREAZIONE_SQUADRA);

            } catch (EccezioneCambioScena e) {
                mostraErrore(e.getMessage());
            }
        }
    }

    private void cambio(String paginaDiCambioScena) {
        try {
            Stage stage = (Stage) gestisciSquadra.getScene().getWindow();
            CambioScena cambioScena = new CambioScena();
            cambioScena.cambioScena(stage, paginaDiCambioScena);

        } catch (EccezioneCambioScena e) {
            throw new EccezioneCambioScena(e.getMessage());
        }
    }

    private void mostraErrore(String messaggio) {
        mostraErrori.setText(messaggio);
        mostraErrori.setVisible(true);
    }
}
