package viste.first;

import controllerApplicativo.HomepageAllenatoreControllerApplicativo;
import engineering.bean.AllenatoreBean;
import engineering.bean.UtenteBean;
import engineering.eccezioni.EccezioneGenerica;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import viste.first.utils.CambioScena;

import static viste.first.utils.FxmlFileName.PAGINA_CREAZIONE_ALLENAMENTO;
import static viste.first.utils.FxmlFileName.PAGINA_CREAZIONE_SQUADRA;

public class HomepageAllenatoreGrafico {


    public void initialize() {
        System.out.println("Inizializzazione Temporanea della Pagina Principale");
    }

    @FXML
    private Label mostraErrori;

    @FXML
    private Button creaSquadra;

    @FXML
    private Button creaAllenamento;

    @FXML
    protected void CreaAllenamento(ActionEvent event) {

        System.out.println("Creazione Allenamento");

        //cambio scena alla pagina di creazione dell'allenamento
        try {
            Stage stage = (Stage) creaAllenamento.getScene().getWindow();
            CambioScena cambioScena = new CambioScena();
            cambioScena.cambioScena(stage, PAGINA_CREAZIONE_ALLENAMENTO);

        } catch (EccezioneGenerica EccezioneGenerica) {
            System.out.println(EccezioneGenerica.getMessage());
        }
    }

    @FXML
    protected void CreaSquadra(ActionEvent event) {

        try {
            System.out.println("Creazione Squadra");

            //cambio scena alla prima vista dove compiamo la creazione della squadra

            //passo al controller applicativo il bean dell'utente che sta richiedendo la creazione della squadra codì che possa fare il cambio scena
            HomepageAllenatoreControllerApplicativo paginaPrincipaleControllerApplicativo = new HomepageAllenatoreControllerApplicativo();
            paginaPrincipaleControllerApplicativo.CreaSquadra();


            try {
                Stage stage = (Stage) creaSquadra.getScene().getWindow();
                CambioScena cambioScena = new CambioScena();
                cambioScena.cambioScena(stage, PAGINA_CREAZIONE_SQUADRA);

            } catch (EccezioneGenerica EccezioneGenerica) {
                mostraErrori.setText(EccezioneGenerica.getMessage());
            }
        }
        catch (EccezioneGenerica e) {
            mostraErrori.setText(e.getMessage());
        }

    }
}
