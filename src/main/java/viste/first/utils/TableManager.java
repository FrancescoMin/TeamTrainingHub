package viste.first.utils;

import engineering.bean.AllenamentoBean;
import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class TableManager {
    private boolean isUpdatingTableView = true;

    /** Associa a ciascuna colonna i relativi metodi get di AllenamentoBean
     * @param columns       è una lista di colonne, contiene le sole colonne semplici (no bottoni)
     * @param nameColumns   è una lista di stringhe, che viene utilizzata per recuperare i dati dai metodi get del AllenamentoBean
     */

    public static void setColumnsTableView(List<TableColumn<AllenamentoBean, ?>> columns, List<String> nameColumns) {

        // Collega i dati alle colonne della TableView
        int index = 0;
        for(TableColumn<AllenamentoBean, ? > column:columns){
            column.setCellValueFactory(new PropertyValueFactory<>(nameColumns.get(index)));
            index++;
        }
    }
/*
    /**
     * @param tableViewAllenamenti è la tabella vera e propria
     * @param allenamenti     è la lista delle playlist da rappresentare
     *

    public static void updateTable(TableView<AllenamentoBean> tableViewAllenamenti, List<AllenamentoBean> allenamenti) {

        List<AllenamentoBean> currentPlaylists = tableViewAllenamenti.getItems();     // Ottenere la lista attuale di playlist dalla TableView

        allenamenti.removeAll(currentPlaylists);                              // Rimuove le playlist già caricate, cosi da avere una lista di playlist nuove

        ObservableList<AllenamentoBean> datiAllenamenti = FXCollections.observableArrayList(allenamenti);
        tableViewAllenamenti.setItems(datiAllenamenti);                               // Aggiornare la TableView con la lista aggiornata di playlist
    }


 */

    /*
    /**
     * @param tableViewAllenamenti   è la tabella vera e propria
     * @param allenamenti            è la lista delle playlist da rappresentare
     *
    public static void addInTable(TableView<AllenamentoBean> tableViewAllenamenti, List<AllenamentoBean> allenamenti) {
        List<AllenamentoBean> currentPlaylists = tableViewAllenamenti.getItems(); // Ottenere la lista attuale di playlist dalla TableView

        allenamenti.removeIf(allenamento -> currentPlaylists.stream().anyMatch(current ->
                current.getData().equals(allenamento.getData()) &&
                        current.getOrarioInizio().equals(allenamento.getOrarioInizio()) &&
                        current.getOrarioFine().equals(allenamento.getOrarioFine())
        ));
        currentPlaylists.addAll(allenamenti);

        ObservableList<AllenamentoBean> datiAllenamenti = FXCollections.observableArrayList(currentPlaylists);
        tableViewAllenamenti.setItems(datiAllenamenti); // Aggiornare la TableView con la lista aggiornata di playlist
    }

     */


    /**
     * @param tableViewAllenamenti   è la tabella vera e propria
     * @param allenamenti            è la lista delle playlist da rappresentare
     */
    public ObservableList<AllenamentoBean> handler(TableView<AllenamentoBean> tableViewAllenamenti, List<AllenamentoBean> allenamenti) {

        ObservableList<AllenamentoBean> observableList = FXCollections.observableList(allenamenti);
        tableViewAllenamenti.setItems(observableList);

        // Aggiunta di un listener di modifica al ObservableList
        observableList.addListener((ListChangeListener<AllenamentoBean>) change -> {
            while (change.next()) {
                if (change.wasAdded() && isUpdatingTableView) { ///// non accade mai #########
                    isUpdatingTableView = false;
                    tableViewAllenamenti.getItems().addAll(change.getAddedSubList());
                    isUpdatingTableView = true;
                } else if (change.wasRemoved() && isUpdatingTableView) {
                    isUpdatingTableView = false;
                    tableViewAllenamenti.getItems().removeAll(change.getRemoved());
                    isUpdatingTableView = true;
                }
            }
        });

        return observableList;
    }

}
