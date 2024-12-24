package viste.first;

import engineering.bean.AllenatoreBean;
import engineering.bean.UtenteBean;
import engineering.eccezioni.EccezioneGenerica;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import viste.first.utils.CambioScena;

import static viste.first.utils.FxmlFileName.PAGINA_CREAZIONE_ALLENAMENTO;
import static viste.first.utils.FxmlFileName.PAGINA_CREAZIONE_SQUADRA;

public class HomepageAllenatoreGrafico {


    public void initialize() {
        System.out.println("Inizializzazione Temporanea della Pagina Principale");
    }

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

        System.out.println("Creazione Squadra");

        //cambio scena alla prima vista dove compiamo la creazione della squadra

        //passo al controller applicativo il bean dell'utente che sta richiedendo la creazione della squadra codì che possa fare il cambio scena

        //creo un utente bean fittizio da passare al controller applicativo. Nella realtà l'utente bean sarà già inizializzato e dovrò solamente passarlo
        UtenteBean utenteBean = new AllenatoreBean("username", "email", "password", null, null);

        /*commento il codice possibilmente corretto per usare qualcosa di temporaneo
        HomepageAllenatoreControllerApplicativo paginaPrincipaleControllerApplicativo = new HomepageAllenatoreControllerApplicativo();
        paginaPrincipaleControllerApplicativo.CreaSquadra(utenteBean);
         */

        try {
            Stage stage = (Stage) creaSquadra.getScene().getWindow();
            CambioScena cambioScena = new CambioScena();
            cambioScena.cambioScena(stage, PAGINA_CREAZIONE_SQUADRA);

        } catch (EccezioneGenerica EccezioneGenerica) {
            System.out.println(EccezioneGenerica.getMessage());
        }

    }


    public void CodiceTemporaneoPerCambioScena() {
        try {
            Stage stage = (Stage) creaSquadra.getScene().getWindow();
            CambioScena cambioScena = new CambioScena();
            cambioScena.cambioScena(stage, PAGINA_CREAZIONE_SQUADRA);

        } catch (EccezioneGenerica EccezioneGenerica) {
            System.out.println(EccezioneGenerica.getMessage());
        }
    }

}
