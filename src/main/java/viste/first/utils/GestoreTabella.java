package viste.first.utils;

import engineering.bean.AllenamentoBean;
import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class GestoreTabella {
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

    public static void addInTable(TableView<AllenamentoBean> playlistTable, List<AllenamentoBean> allenamenti) {
        List<AllenamentoBean> currentAllenamenti = playlistTable.getItems(); // Ottenere la lista attuale di playlist dalla TableView

        allenamenti.removeIf(allenamento ->
                currentAllenamenti.stream().anyMatch(currentAllenamento ->
                        currentAllenamento.getData().equals(allenamento.getData()) &&
                                currentAllenamento.getOrarioInizio().equals(allenamento.getOrarioInizio()) &&
                                currentAllenamento.getOrarioFine().equals(allenamento.getOrarioFine())
                )
        );
        currentAllenamenti.addAll(allenamenti);

        ObservableList<AllenamentoBean> playlistData = FXCollections.observableArrayList(currentAllenamenti);
        playlistTable.setItems(playlistData); // Aggiornare la TableView con la lista aggiornata di playlist
    }


    public static void updateTable(TableView<AllenamentoBean> playlistTable, List<AllenamentoBean> playlists) {

        List<AllenamentoBean> currentPlaylists = playlistTable.getItems();     // Ottenere la lista attuale di playlist dalla TableView

        playlists.removeAll(currentPlaylists);                              // Rimuove le playlist già caricate, cosi da avere una lista di playlist nuove

        ObservableList<AllenamentoBean> playlistData = FXCollections.observableArrayList(playlists);
        playlistTable.setItems(playlistData);                               // Aggiornare la TableView con la lista aggiornata di playlist
    }

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
