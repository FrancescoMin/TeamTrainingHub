package viste.first;

import ctrl_applicativo.GestisciRichiesteCtrlApplicativo;
import engineering.bean.UtenteBean;
import javafx.fxml.*;
import javafx.scene.control.*;
import viste.first.basi.BaseTabelleCtrlGrafico;
import viste.first.utils.DoubleButtonTableCell;

import static viste.first.utils.FxmlFileName.PAGINA_HOME_ALLENATORE;

public class GestisciRichiesteCtrlGrafico {


    @FXML
    private TableColumn<UtenteBean, String> giocatoreColonna;

    @FXML
    private TableColumn<UtenteBean, Boolean> approveColumn;

    @FXML
    private TableView<UtenteBean> tabellaRichieste;

    @FXML
    private Label mostraErrori;

    @FXML
    protected void ricarica() {
        GestisciRichiesteCtrlApplicativo visualizzarichiestectrlapplicativo = new GestisciRichiesteCtrlApplicativo();
        tabellaRichieste.getItems().setAll(visualizzarichiestectrlapplicativo.recuperaUtentiBean());
    }

    private static void setupCambio(){
        BaseTabelleCtrlGrafico.setPaginaPrecedente(PAGINA_HOME_ALLENATORE);
    }

    @FXML
    public void initialize() {
        setupCambio();

        // Set the value factory for the name column
        giocatoreColonna.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getEmail()));

        // Set the cell factory for the action column (buttons)
        approveColumn.setCellFactory(col -> new DoubleButtonTableCell(this));

        GestisciRichiesteCtrlApplicativo visualizzarichiestectrlapplicativo = new GestisciRichiesteCtrlApplicativo();
        visualizzarichiestectrlapplicativo.recuperaUtentiBean();

        // Populate the TableView with the list of users (UtenteBean)
        ricarica();
    }


    public void handlerButton(UtenteBean utenteBean, boolean approved) {
        try {
            GestisciRichiesteCtrlApplicativo visualizzarichiestectrlapplicativo = new GestisciRichiesteCtrlApplicativo();

            if (!approved)
                visualizzarichiestectrlapplicativo.rifiutaRichiesta(utenteBean);
            else {
                visualizzarichiestectrlapplicativo.accettaRichiesta(utenteBean);
            }

            String accept = approved ? "accettato" : "rifiutato";
            // Show an alert to indicate acceptance
            popup("Giocatore " + accept, "Il giocatore " + utenteBean.getEmail() + " è stato "+accept+"!");
        }
        catch (Exception e) {
            // Imposta il testo della Label
            mostraErrori.setText(e.getMessage());

            // Cambia il colore del testo della Label in un colore che contrasta bene con il verde (#1DB954)
            mostraErrori.setStyle("-fx-text-fill: blue; -fx-font-size: 16px;"); // Bianco, ma puoi usare un altro colore che ti piace
            mostraErrori.setVisible(true);
            ricarica();

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
