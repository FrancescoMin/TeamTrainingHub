package viste.first;

import controllerApplicativo.ConsultaAllenamentiCtrlApplicativo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modelli.Allenamento;

import java.util.List;

public class ConsultaAllenamentiCtrlGrafico {

    @FXML
    private TableView<Allenamento> allenamentiTable;

    @FXML
    private TableColumn<Allenamento, String> dataColumn;

    @FXML
    private TableColumn<Allenamento, Integer> durataColumn;

    @FXML
    private TableColumn<Allenamento, String> descrizioneColumn;

   // @FXML
    //private javafx.scene.control.Button refreshButton;

    private ConsultaAllenamentiCtrlApplicativo applicativoController;

    public void initialize() {
        applicativoController = new ConsultaAllenamentiCtrlApplicativo();

        // Configurazione le colonne
        dataColumn.setCellValueFactory(new PropertyValueFactory<>("data"));
        durataColumn.setCellValueFactory(new PropertyValueFactory<>("durata"));
        descrizioneColumn.setCellValueFactory(new PropertyValueFactory<>("descrizione"));

        // Caricam gli allenamenti iniziali
        caricaAllenamenti();

        //listener per il pulsante di aggiornamento (TASTO OPZIONALE)
       // refreshButton.setOnAction(event -> caricaAllenamenti());
    }

    private void caricaAllenamenti() {
        List<Allenamento> allenamenti = applicativoController.leggiAllenamenti();
        ObservableList<Allenamento> allenamentiObservable = FXCollections.observableArrayList();
        allenamentiTable.setItems(allenamentiObservable);
    }
}