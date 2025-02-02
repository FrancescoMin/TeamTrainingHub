package viste.first;

import ctrlApplicativo.VisualizzaRichiesteCtrlApplicativo;
import engineering.bean.UtenteBean;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import viste.first.basi.BaseTabelleCtrlGrafico;
import viste.first.utils.GestoreTabella;

import static viste.first.utils.FxmlFileName.PAGINA_HOME_ALLENATORE;

public class VisualizzaRichiesteCtrlGrafico implements GestoreTabella.ButtonActionHandler {


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

    private static void setupCambio(){
        BaseTabelleCtrlGrafico.paginaPrecedente = PAGINA_HOME_ALLENATORE;
    }

    @FXML
    public void initialize() {
        setupCambio();
        gestoreTabella = new GestoreTabella(this);  // 'this' refers to the controller

        // Populate the TableView with the list of users (UtenteBean)
        gestoreTabella.populateTable(tabellaRichieste);
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

}
