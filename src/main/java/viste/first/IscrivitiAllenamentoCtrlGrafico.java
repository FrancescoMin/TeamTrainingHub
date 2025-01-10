package viste.first;

import ctrlApplicativo.IscrivitiAllenamentoCtrlApplicativo;
import engineering.eccezioni.EccezioneGenerica;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
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
    private TableColumn<Allenamento, Integer> durataColumn;
    @FXML
    private TableColumn<Allenamento, String> descrizioneColumn;
    @FXML
    private TableColumn<Allenamento, String> iscrizioneColumn;
    @FXML
    private javafx.scene.control.Button tornaInHomepageGiocatoreButton;

    private IscrivitiAllenamentoCtrlApplicativo applicativoController;


    public void initialize(URL location, ResourceBundle resources) {
        applicativoController = new IscrivitiAllenamentoCtrlApplicativo();
        tornaInHomepageGiocatoreButton.setOnAction(event -> handleTornaInHomepageGiocatoreButtonAction());
        dataColumn.setCellValueFactory(new PropertyValueFactory<>("data"));
        durataColumn.setCellValueFactory(new PropertyValueFactory<>("durata"));
        descrizioneColumn.setCellValueFactory(new PropertyValueFactory<>("descrizione"));
        iscrizioneColumn.setCellValueFactory(new PropertyValueFactory<>("iscrizione"));
        caricaAllenamenti();
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
