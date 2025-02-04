package viste.first;

import engineering.bean.AllenamentoBean;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ctrl_applicativo.IscrivitiAllenamentoCtrlApplicativo;
import viste.first.basi.BaseTabelleCtrlGrafico;

import static viste.first.utils.FxmlFileName.PAGINA_HOME_GIOCATORE;

public class IscrivitiAllenamentoCtrlGrafico {

    @FXML
    private TableView<AllenamentoBean> tableViewAllenamenti;

    @FXML
    private TableColumn<AllenamentoBean, String> colData;

    @FXML
    private TableColumn<AllenamentoBean, String> colOrarioInizio;

    @FXML
    private TableColumn<AllenamentoBean, String> colOrarioFine;

    @FXML
    private TableColumn<AllenamentoBean, String> colDescrizione;

    @FXML
    private TableColumn<AllenamentoBean, Button> colAccetta;

    @FXML
    private Label mostraErrori;

    private IscrivitiAllenamentoCtrlApplicativo controllerApplicativo;

    private static void setupCambio(){
        BaseTabelleCtrlGrafico.setPaginaPrecedente(PAGINA_HOME_GIOCATORE);
    }

    @FXML
    public void initialize() {

        setupCambio();

        // Inizializza le colonne della TableView
        colData.setCellValueFactory(new PropertyValueFactory<>("data"));
        colOrarioInizio.setCellValueFactory(new PropertyValueFactory<>("orarioInizio"));
        colOrarioFine.setCellValueFactory(new PropertyValueFactory<>("orarioFine"));
        colDescrizione.setCellValueFactory(new PropertyValueFactory<>("descrizione"));

        // Configura la colonna "Accetta" con un pulsante
        colAccetta.setCellFactory(param -> new ButtonCell());
        colAccetta.setVisible(true);


        // Inizializza il controller applicativo
        controllerApplicativo = new IscrivitiAllenamentoCtrlApplicativo();
        controllerApplicativo.popola();
    }

    private class ButtonCell extends TableCell<AllenamentoBean, Button> {
        private final Button button;

        public ButtonCell() {
            button = new Button("Accetta");

            button.setOnAction(event -> {
                AllenamentoBean selected = getTableView().getItems().get(getIndex());
                controllerApplicativo.accettaAllenamento(selected);
                // Rimuovi l'allenamento dalla tabella dopo l'accettazione
                getTableView().getItems().remove(selected);
                // Dopo la rimozione, forziamo un aggiornamento della TableView
                tableViewAllenamenti.refresh();
            });
        }

        @Override
        protected void updateItem(Button item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setGraphic(null); // Nascondi il pulsante se la cella è vuota
            }
            else {
                setGraphic(button); // Mostra il pulsante
            }
        }
    }


}