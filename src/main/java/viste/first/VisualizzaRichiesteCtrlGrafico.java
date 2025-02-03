package viste.first;

import ctrl_applicativo.VisualizzaRichiesteCtrlApplicativo;
import engineering.bean.UtenteBean;
import javafx.fxml.*;
import javafx.scene.control.*;
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
    private Label mostraErrori;

    @FXML
    protected void ricarica() {
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
        VisualizzaRichiesteCtrlApplicativo visualizzarichiestectrlapplicativo = new VisualizzaRichiesteCtrlApplicativo();
        visualizzarichiestectrlapplicativo.accettaRichiesta(utenteBean);

        // Show an alert to indicate acceptance
        popup("Accept User", "User " + utenteBean.getEmail() + " has been accepted!");

    }

    // Implementation of the handleRefuse method from ButtonActionHandler
    @Override
    public void handleRefuse(UtenteBean utenteBean) {
        try {
            VisualizzaRichiesteCtrlApplicativo visualizzarichiestectrlapplicativo = new VisualizzaRichiesteCtrlApplicativo();
            visualizzarichiestectrlapplicativo.rifiutaRichiesta(utenteBean);

            // Show an alert to indicate refusal
            popup("Refuse User", "User " + utenteBean.getEmail() + " has been refused!");
        }
        catch (Exception e) {
            mostraErrori.setText(e.getMessage());
            mostraErrori.setVisible(true);
        }
    }

    private void popup(String title, String message) {
        // Show an alert to indicate refusal
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
        ricarica();
    }

}
