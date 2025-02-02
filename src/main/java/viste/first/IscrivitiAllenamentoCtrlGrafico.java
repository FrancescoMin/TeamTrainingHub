package viste.first;

import engineering.bean.AllenamentoBean;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import ctrlApplicativo.IscrivitiAllenamentoCtrlApplicativo;
import viste.first.utils.IscrizioneAllenamentiTabella;

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

    private IscrizioneAllenamentiTabella iscrizioneAllenamentiTabella;
    private IscrivitiAllenamentoCtrlApplicativo controllerApplicativo;

    @FXML
    public void initialize() {
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
        iscrizioneAllenamentiTabella = new IscrizioneAllenamentiTabella(tableViewAllenamenti, controllerApplicativo.getCollezioneAllenamenti());
    }

    // Metodo per gestire il click sul pulsante "Back"
    @FXML
    public void onBackClick() {
        // Logica per tornare alla vista precedente
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