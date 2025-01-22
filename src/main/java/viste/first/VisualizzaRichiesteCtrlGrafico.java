package viste.first;

import ctrlApplicativo.VisualizzaRichiesteCtrlApplicativo;
import engineering.bean.UtenteBean;
import engineering.eccezioni.EccezioneGenerica;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.stage.Stage;
import modelli.Utente;
import viste.first.utils.CambioScena;
import viste.first.utils.GestoreTabella;

import java.net.URL;
import java.util.*;

import static viste.first.utils.FxmlFileName.PAGINA_HOME_ALLENATORE;

public class VisualizzaRichiesteCtrlGrafico implements Initializable, GestoreTabella.ButtonActionHandler {

    @FXML
    private TableColumn<UtenteBean, String> giocatoreColonna;

    @FXML
    private TableColumn<UtenteBean, Void> approveColumn;

    private GestoreTabella gestoreTabella;

    @FXML
    private TableView<UtenteBean> tabellaRichieste;

    @FXML
    protected void Ricarica() {
        gestoreTabella.refreshTable(tabellaRichieste);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gestoreTabella = new GestoreTabella(this);  // 'this' refers to the controller

        // Populate the TableView with the list of users (UtenteBean)
        gestoreTabella.populateTable(tabellaRichieste);
    }

    @FXML
    protected void onBackClick(ActionEvent event) {
        try {
            Stage stage = (Stage) tabellaRichieste.getScene().getWindow();
            CambioScena cambioScena = new CambioScena();
            cambioScena.cambioScena(stage, PAGINA_HOME_ALLENATORE);

        } catch (EccezioneGenerica e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void handleAccept(UtenteBean utenteBean) {
        VisualizzaRichiesteCtrlApplicativo visualizzaRichiesteCtrlApplicativo = new VisualizzaRichiesteCtrlApplicativo();
        visualizzaRichiesteCtrlApplicativo.accettaRichiesta(utenteBean);

        // Show an alert to indicate acceptance
        popup("Accept User", "User " + utenteBean.getEmail() + " has been accepted!");

    }

    // Implementation of the handleRefuse method from ButtonActionHandler
    @Override
    public void handleRefuse(UtenteBean utenteBean) {
        VisualizzaRichiesteCtrlApplicativo visualizzaRichiesteCtrlApplicativo = new VisualizzaRichiesteCtrlApplicativo();
        visualizzaRichiesteCtrlApplicativo.rifiutaRichiesta(utenteBean);

        // Show an alert to indicate refusal
        popup("Refuse User", "User " + utenteBean.getEmail() + " has been refused!");

    }

    private void popup(String title, String message) {
        // Show an alert to indicate refusal
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
        Ricarica();
    }

    /** Public because it must be called by DoubleButtonTableCell, it's the action taken when the Accept or Reject button is clicked */
    public void handlerButton() {}
}
