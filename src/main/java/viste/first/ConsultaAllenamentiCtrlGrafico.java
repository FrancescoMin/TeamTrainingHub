package viste.first;

import ctrlApplicativo.ConsultaAllenamentiCtrlApplicativo;
import engineering.eccezioni.EccezioneGenerica;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import modelli.Allenamento;
import viste.first.utils.CambioScena;

import java.util.List;

import static viste.first.utils.FxmlFileName.PAGINA_HOME_GIOCATORE;

public class ConsultaAllenamentiCtrlGrafico {

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
    private Button tornaInHomepageGiocatoreButton;

   // @FXML
    //private javafx.scene.control.Button refreshButton;

    private ConsultaAllenamentiCtrlApplicativo applicativoController;

    public void initialize() {
        applicativoController = new ConsultaAllenamentiCtrlApplicativo();

        // Configurazione le colonne
        dataColumn.setCellValueFactory(new PropertyValueFactory<>("Data"));
        orarioInizioColumn.setCellValueFactory(new PropertyValueFactory<>("Orario Inizio"));
        orarioFineColumn.setCellValueFactory(new PropertyValueFactory<>("Orario Fine"));
        descrizioneColumn.setCellValueFactory(new PropertyValueFactory<>("Descrizione"));

        tornaInHomepageGiocatoreButton.setOnAction(event -> handleTornaInHomepageGiocatoreButtonAction());

        // Caricam gli allenamenti iniziali
        caricaAllenamenti();

        //listener per il pulsante di aggiornamento (TASTO OPZIONALE)
       // refreshButton.setOnAction(event -> caricaAllenamenti());
    }

    private void caricaAllenamenti() { //carica gli allenamenti dal controller applicativo
        List<Allenamento> allenamenti = applicativoController.leggiAllenamenti();
        if (allenamenti != null && !allenamenti.isEmpty()) {
            ObservableList<Allenamento> allenamentiObservable = FXCollections.observableArrayList(allenamenti);
            allenamentiTable.setItems(allenamentiObservable);
        } else {
            System.out.println("Nessun allenamento trovato.");
        }
    }

    private void handleTornaInHomepageGiocatoreButtonAction() {
        try {
            Stage stage = (Stage) tornaInHomepageGiocatoreButton.getScene().getWindow();
            CambioScena cambioScena = new CambioScena();
            cambioScena.cambioScena(stage, PAGINA_HOME_GIOCATORE);
        } catch (EccezioneGenerica eccezioneGenerica) {
            System.out.println(eccezioneGenerica.getMessage());
        }
    }
}