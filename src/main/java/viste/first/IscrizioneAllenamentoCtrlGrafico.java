package viste.first;

import ctrl_applicativo.IscrizioneAllenamentoCtrlApplicativo;
import engineering.bean.AllenamentoBean;
import engineering.pattern.observer.CollezioneAllenamenti;
import engineering.pattern.observer.Observer;
import javafx.collections.ObservableList;
import javafx.fxml.*;
import javafx.scene.control.*;
import viste.first.basi.BaseTabelleCtrlGrafico;
import viste.first.utils.ConsultaAllenamentiTabella;
import viste.first.utils.BottoneSingolo;

import java.net.URL;
import java.util.*;

import static viste.first.utils.FxmlFileName.PAGINA_HOME_GIOCATORE;

public class IscrizioneAllenamentoCtrlGrafico implements Initializable, Observer {

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
    private TableColumn<AllenamentoBean, String> colAccetta;

    ObservableList<AllenamentoBean> observableList;
    IscrizioneAllenamentoCtrlApplicativo iscrizioneAllenamentoCtrlApplicativo = new IscrizioneAllenamentoCtrlApplicativo();
    ConsultaAllenamentiTabella tabellaAllenamenti = new ConsultaAllenamentiTabella();

    @FXML
    private Label mostraErrori;

    private static void setupCambio() {
        BaseTabelleCtrlGrafico.setPaginaPrecedente(PAGINA_HOME_GIOCATORE);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try{
            setupCambio();

            // Recupera tutte le playlist pending, metodo pull
            List<AllenamentoBean> allenamenti = iscrizioneAllenamentoCtrlApplicativo.caricaAllenamenti(); // Vengono recuperate tutte le playlist pending

            // Popola la Tabella con i dati
            tabellaAllenamenti.populateTable(tableViewAllenamenti);

            // Imposta i dati nella tabella
            tableViewAllenamenti.getItems().setAll(allenamenti);

            // Aggiungi la colonna con un pulsante "Accetta"
            colAccetta.setCellFactory(button -> new BottoneSingolo(this));

        }
        catch (Exception e) {
            mostra(e.getMessage());
        }
    }

    // Metodo per ricaricare i dati nella tabella
    public void ricaricaTabella() {
        List<AllenamentoBean> allenamenti = iscrizioneAllenamentoCtrlApplicativo.caricaAllenamenti(); // Vengono recuperati gli allenamenti

        try {
            tabellaAllenamenti.populateTable(tableViewAllenamenti);

            tableViewAllenamenti.getItems().setAll(allenamenti);
        } catch (Exception e) {
            mostra(e.getMessage());
        }

    }

    /** Public perché deve essere chiamata da BottoneDoppio, è l'azione che viene compiuta al click del bottone Accept o Reject */
    public void gestoreBottone(AllenamentoBean allenamento) {
            try{

                // Approva Allenamento
                iscrizioneAllenamentoCtrlApplicativo.accettaAllenamento(allenamento);

                ricaricaTabella();
        }
        catch (Exception e) {
            mostra(e.getMessage());
        }
    }

    private void mostra(String message) {
        // Imposta il testo della Label
        mostraErrori.setText(message);

        // Cambia il colore del testo della Label in un colore che contrasta bene con il verde (#1DB954)
        mostraErrori.setStyle("-fx-text-fill: blue; -fx-font-size: 16px;"); // Bianco, ma puoi usare un altro colore che ti piace
        mostraErrori.setVisible(true);

    }

    @Override
    public void update() {
    }
}