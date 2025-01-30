package viste.first;

import ctrlApplicativo.IscrivitiAllenamentoCtrlApplicativo;
import engineering.eccezioni.EccezioneGenerica;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import modelli.Allenamento;
import viste.first.utils.CambioScena;

import java.net.URL;
import java.util.ResourceBundle;

public class IscrivitiAllenamentoCtrlGrafico implements Initializable {
    @FXML
    private TableView<Allenamento> allenamentiTable;
    @FXML
    private TableColumn<Allenamento, String> dataColumn;
    @FXML
    private TableColumn<Allenamento, String> orarioInizioColumn;
    @FXML
    private TableColumn<Allenamento, String> orarioFineColumn;
    @FXML
    private TableColumn<Allenamento, String> descrizioneColumn;
    @FXML
    private TableColumn<Allenamento, String> iscrizioneColumn;
    @FXML
    private javafx.scene.control.Button tornaInHomepageGiocatoreButton;

    private IscrivitiAllenamentoCtrlApplicativo applicativoController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*
        applicativoController = new IscrivitiAllenamentoCtrlApplicativo();
        tornaInHomepageGiocatoreButton.setOnAction(event -> handleTornaInHomepageGiocatoreButtonAction());
        dataColumn.setCellValueFactory(new PropertyValueFactory<>("data"));
        orarioInizioColumn.setCellValueFactory(new PropertyValueFactory<>("orarioInizio"));
        orarioFineColumn.setCellValueFactory(new PropertyValueFactory<>("orarioFine"));
        descrizioneColumn.setCellValueFactory(new PropertyValueFactory<>("descrizione"));
        // Configura la colonna "iscrizione" con checkbox
        iscrizioneColumn.setCellValueFactory(param -> {
            Allenamento allenamento = param.getValue();
            SimpleBooleanProperty property = new SimpleBooleanProperty(allenamento.isIscritto());

            // Aggiorna lo stato di iscrizione quando il checkbox viene selezionato o deselezionato
            property.addListener((observable, oldValue, newValue) -> {
                allenamento.setIscritto(newValue);
                applicativoController.aggiornaIscrizioneAllenamento(allenamento);
            });

            return property.asString();
        });

        iscrizioneColumn.setCellFactory(CheckBoxTableCell.forTableColumn((Callback<Integer, ObservableValue<Boolean>>) iscrizioneColumn));
        caricaAllenamenti();

         */
    }

    private void caricaAllenamenti() {
        ObservableList<Allenamento> allenamentiObservable = FXCollections.observableArrayList(applicativoController.leggiAllenamenti());
        allenamentiTable.setItems(allenamentiObservable);
    }

    private void handleTornaInHomepageGiocatoreButtonAction() {
        try {
            Stage stage = (Stage) tornaInHomepageGiocatoreButton.getScene().getWindow();
            CambioScena cambioScena = new CambioScena();
            cambioScena.cambioScena(stage, "HomepageGiocatore.fxml");
        } catch (EccezioneGenerica eccezioneGenerica) {
            System.out.println(eccezioneGenerica.getMessage());
        }
    }
}