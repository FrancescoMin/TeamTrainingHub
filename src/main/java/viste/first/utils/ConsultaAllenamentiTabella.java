package viste.first.utils;

import ctrl_applicativo.ConsultaAllenamentiCtrlApplicativo;
import engineering.bean.AllenamentoBean;
import javafx.scene.control.*;
import java.util.ArrayList;
import java.util.List;

public class ConsultaAllenamentiTabella {


    // Costruttore per inizializzare l'handler
    public ConsultaAllenamentiTabella() {
        // Costruttore default
    }

    // Popola la Tabella con i dati
    public void populateTable(TableView<AllenamentoBean> tableView) {
        // Ottieni le colonne e configura la visualizzazione
        TableColumn<AllenamentoBean, String> dataColumn = (TableColumn<AllenamentoBean, String>) tableView.getColumns().get(0);
        TableColumn<AllenamentoBean, String> orarioInizioColumn = (TableColumn<AllenamentoBean, String>) tableView.getColumns().get(1);
        TableColumn<AllenamentoBean, String> orarioFineColumn = (TableColumn<AllenamentoBean, String>) tableView.getColumns().get(2);
        TableColumn<AllenamentoBean, String> descrizioneColumn = (TableColumn<AllenamentoBean, String>) tableView.getColumns().get(3);

        // Impostazioni delle value factory per le colonne
        dataColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getData()));
        orarioInizioColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getOrarioInizio()));
        orarioFineColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getOrarioFine()));
        descrizioneColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDescrizione()));

        // Imposta i dati nella tabella
        tableView.getItems().setAll(recuperaAllenamenti());
    }

    // Metodo per ricaricare i dati nella tabella
    public void refreshTable(TableView<AllenamentoBean> tableView) {
        tableView.getItems().setAll(recuperaAllenamenti());
    }

    private List<AllenamentoBean> recuperaAllenamenti() {
        List<AllenamentoBean> allenamentiBean = new ArrayList<>();
        // Recupera gli allenamenti tramite il controller applicativo
        ConsultaAllenamentiCtrlApplicativo ctrlApplicativo = new ConsultaAllenamentiCtrlApplicativo();
        List<AllenamentoBean> allenamenti = ctrlApplicativo.ottieniAllenamenti();
        allenamentiBean.addAll(allenamenti);
        return allenamentiBean;
    }
}
