package viste.first;

import engineering.bean.AllenamentoBean;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ctrlApplicativo.IscrivitiAllenamentoCtrlApplicativo;
import viste.first.basi.BaseTabelleCtrlGrafico;
import viste.first.utils.IscrizioneAllenamentiTabella;

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

    private IscrizioneAllenamentiTabella iscrizioneAllenamentiTabella;
    private IscrivitiAllenamentoCtrlApplicativo controllerApplicativo;

    private static void setupCambio(){
        BaseTabelleCtrlGrafico.paginaPrecedente = PAGINA_HOME_GIOCATORE;
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

        // Verifica se la colonna è stata correttamente configurata
        System.out.println("La colonna Accetta è stata configurata: " + (colAccetta != null));

        // Inizializza il controller applicativo
        controllerApplicativo = new IscrivitiAllenamentoCtrlApplicativo();
        controllerApplicativo.popola();

        iscrizioneAllenamentiTabella = new IscrizioneAllenamentiTabella(tableViewAllenamenti, controllerApplicativo.getCollezioneAllenamenti());
    }

    private class ButtonCell extends TableCell<AllenamentoBean, Button> {
        private final Button button;

        public ButtonCell() {
            button = new Button("Accetta");

            button.setOnAction(event -> {
                AllenamentoBean selected = getTableView().getItems().get(getIndex());
                System.out.println("Accettato allenamento: " + selected.getDescrizione());  // Aggiungi un log per il debug
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
            if (empty) {
                setGraphic(null); // Nascondi il pulsante se la cella è vuota
            }
            else {
                setGraphic(button); // Mostra il pulsante
            }
        }
    }


}