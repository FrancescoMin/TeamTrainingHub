package viste.first;

import ctrlApplicativo.HomepageAllenatoreCtrlApplicativo;
import engineering.eccezioni.EccezioneGenerica;
import javafx.event.ActionEvent;
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

        //cambio scena alla pagina di visualizzazione delle richieste di partecipazione
        try {
            Cambio(PAGINA_VISUALIZZA_RICHIESTE_PARTECIPAZIONE);

        } catch (EccezioneGenerica EccezioneGenerica) {
            mostraErrori.setText(EccezioneGenerica.getMessage());
        }
    }

    @FXML
    protected void RitornoAlLogin() {

        System.out.println("Ritorno al Login");

        //cambio scena alla pagina di login
        try {
            Cambio(PAGINA_PRINCIPALE);

        } catch (EccezioneGenerica EccezioneGenerica) {
            System.out.println(EccezioneGenerica.getMessage());
        }
    }

    @FXML
    protected void CreaAllenamento() {

        System.out.println("Creazione Allenamento");

        //cambio scena alla pagina di creazione dell'allenamento
        try {
            Cambio(PAGINA_CREAZIONE_ALLENAMENTO);

        } catch (EccezioneGenerica EccezioneGenerica) {
            mostraErrori.setText(EccezioneGenerica.getMessage());
        }
    }

    @FXML
    protected void GestisciSquadra() {
        //controlliamo che l'allenatore abbia o meno una squadra chiedendolo al controllore applicativo
        HomepageAllenatoreCtrlApplicativo paginaPrincipaleControllerApplicativo = new HomepageAllenatoreCtrlApplicativo();

        if(paginaPrincipaleControllerApplicativo.esisteSquadra()){
            //Se l'allenatore ha una squadra allora compiamo il cambio scena alla pagina di gestione della squadra
            System.out.println("Gestione Squadra");

            //passo al controller applicativo il bean dell'utente che sta richiedendo la creazione della squadra codì che possa fare il cambio scena
            //paginaPrincipaleControllerApplicativo.GestisciSquadra();

            //cambio scena alla pagina di gestione della squadra
            try {
                Cambio(PAGINA_GESTIONE_SQUADRA);

            } catch (EccezioneGenerica EccezioneGenerica) {
                mostraErrori.setText(EccezioneGenerica.getMessage());
            }
        } 
        else {
            //Se l'allenatore non ha una squadra allora compiamo il cambio scena alla pagina di creazione della squadra
            try {
                System.out.println("Creazione Squadra");

                //passo al controller applicativo il bean dell'utente che sta richiedendo la creazione della squadra codì che possa fare il cambio scena
                //paginaPrincipaleControllerApplicativo.CreaSquadra();

                //cambio scena alla prima vista dove compiamo la creazione della squadra
                try {
                    Cambio(PAGINA_CREAZIONE_SQUADRA);

                } catch (EccezioneGenerica EccezioneGenerica) {
                    mostraErrori.setText(EccezioneGenerica.getMessage());
                }
            }
            catch (EccezioneGenerica e) {
                mostraErrori.setText(e.getMessage());
            }
        }
    }

    private void Cambio(String paginaDiCambioScena) {
        try {
            Stage stage = (Stage) gestisciSquadra.getScene().getWindow();
            CambioScena cambioScena = new CambioScena();
            cambioScena.cambioScena(stage, paginaDiCambioScena);

        } catch (EccezioneGenerica EccezioneGenerica) {
            mostraErrori.setText(EccezioneGenerica.getMessage());
        }
    }
}
