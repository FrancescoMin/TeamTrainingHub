package viste.first.utils;

import engineering.bean.AllenamentoBean;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

public class ConsultaAllenamentiTabella {

    // Metodo per configurare le colonne della TableView
    public void configureTableColumns(TableView<AllenamentoBean> tableView) {
        // Configurazione delle colonne di base (queste dovrebbero essere già definite nella TableView)
        TableColumn<AllenamentoBean, String> dataColumn = (TableColumn<AllenamentoBean, String>) tableView.getColumns().get(0);
        TableColumn<AllenamentoBean, String> orarioInizioColumn = (TableColumn<AllenamentoBean, String>) tableView.getColumns().get(1);
        TableColumn<AllenamentoBean, String> orarioFineColumn = (TableColumn<AllenamentoBean, String>) tableView.getColumns().get(2);
        TableColumn<AllenamentoBean, String> descrizioneColumn = (TableColumn<AllenamentoBean, String>) tableView.getColumns().get(3);

        // Imposta la cellValueFactory per le colonne di allenamento
        dataColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getData()));
        orarioInizioColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getOrarioInizio()));
        orarioFineColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getOrarioFine()));
        descrizioneColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDescrizione()));
    }

    // Metodo per aggiornare i dati nella TableView
    public void updateTableView(TableView<AllenamentoBean> tableView, ObservableList<AllenamentoBean> allenamenti) {
        tableView.setItems(allenamenti);  // Imposta gli allenamenti nella TableView
    }
}
