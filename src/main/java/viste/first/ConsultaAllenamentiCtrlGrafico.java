package viste.first;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableCell;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import ctrlApplicativo.ConsultaAllenamentiCtrlApplicativo;
import engineering.bean.AllenamentoBean;

public class ConsultaAllenamentiCtrlGrafico {

    @FXML
    private TableView<AllenamentoBean> tableView;
    @FXML
    private TableColumn<AllenamentoBean, String> dataColumn;
    @FXML
    private TableColumn<AllenamentoBean, String> orarioInizioColumn;
    @FXML
    private TableColumn<AllenamentoBean, String> orarioFineColumn;
    @FXML
    private TableColumn<AllenamentoBean, String> descrizioneColumn;

    private ConsultaAllenamentiCtrlApplicativo applicativoController;  // Controller applicativo

    public void initialize() {
        applicativoController = new ConsultaAllenamentiCtrlApplicativo();  // Creiamo il controller applicativo

        // Configurazione delle colonne della TableView
        dataColumn.setCellValueFactory(new PropertyValueFactory<>("data"));
        orarioInizioColumn.setCellValueFactory(new PropertyValueFactory<>("orarioInizio"));
        orarioFineColumn.setCellValueFactory(new PropertyValueFactory<>("orarioFine"));
        descrizioneColumn.setCellValueFactory(new PropertyValueFactory<>("descrizione"));
    }

    // Metodo per aggiornare i dati nella TableView
    public void aggiornaTableView() {
        tableView.setItems(applicativoController.getAllenamenti());
    }
}
