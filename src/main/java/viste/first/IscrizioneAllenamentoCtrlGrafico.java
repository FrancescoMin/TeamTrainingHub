package viste.first;

import ctrl_applicativo.IscrizioneAllenamentoCtrlApplicativo;
import engineering.bean.AllenamentoBean;
import engineering.pattern.observer.CollezioneAllenamenti;
import engineering.pattern.observer.Observer;
import javafx.application.Platform;
import javafx.fxml.*;
import javafx.scene.control.*;
import modelli.Allenamento;
import viste.first.basi.BaseTabelleCtrlGrafico;
import viste.first.utils.ConsultaAllenamentiTabella;
import viste.first.utils.BottoneSingolo;
import viste.first.utils.GestoreTabella;

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

    private CollezioneAllenamenti collezioneAllenamenti;
    private List<Allenamento> allenamenti = new ArrayList<>();
    private List<AllenamentoBean> allenamentiBean = new ArrayList<>();
    private final IscrizioneAllenamentoCtrlApplicativo iscrizioneAllenamentoCtrlApplicativo = new IscrizioneAllenamentoCtrlApplicativo();
    private final ConsultaAllenamentiTabella tabellaAllenamenti = new ConsultaAllenamentiTabella();

    @FXML
    private Label mostraErrori;

    private static void setupCambio() {
        BaseTabelleCtrlGrafico.setPaginaPrecedente(PAGINA_HOME_GIOCATORE);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            setupCambio();

            List<TableColumn<AllenamentoBean, ?>> columns = Arrays.asList(colData, colOrarioInizio, colOrarioFine, colDescrizione);
            List<String> nameColumns = Arrays.asList("data", "orarioInizio", "orarioFine", "descrizione");
            colAccetta.setCellFactory(button -> new BottoneSingolo(this));

            // Registrazione dell'Observer sul Subject
            collezioneAllenamenti = CollezioneAllenamenti.getInstance();
            collezioneAllenamenti.attach(this);

            GestoreTabella.setColumnsTableView(columns, nameColumns);

            // Caricamento iniziale dei dati
            allenamentiBean = iscrizioneAllenamentoCtrlApplicativo.caricaAllenamenti();
            GestoreTabella.updateTable(tableViewAllenamenti, allenamentiBean);

        } catch (Exception e) {
            mostra(e.getMessage());
        }
    }

    // Metodo per ricaricare manualmente se necessario
    public void ricaricaTabella() {
        allenamentiBean = iscrizioneAllenamentoCtrlApplicativo.caricaAllenamenti();

        try {
            tabellaAllenamenti.populateTable(tableViewAllenamenti);
            tableViewAllenamenti.getItems().setAll(allenamentiBean);
        } catch (Exception e) {
            mostra(e.getMessage());
        }
    }

    /** Chiamata dal bottone della riga della tabella */
    public void gestoreBottone(AllenamentoBean allenamento) {
        try {
            // Esegue l'azione di business (aggiorna la persistenza e notifica il Subject)
            iscrizioneAllenamentoCtrlApplicativo.accettaAllenamento(allenamento);

            // No chiamate a ricaricaTabella()!
            // Tabella aggiornata in modo reattivo dal metodo update() dell'Observer.
        } catch (Exception e) {
            mostra(e.getMessage());
        }
    }

    private void mostra(String message) {
        mostraErrori.setText(message);
        mostraErrori.setStyle("-fx-text-fill: blue; -fx-font-size: 16px;");
        mostraErrori.setVisible(true);
    }

    @Override
    public void update() {
        System.out.println("--> [Observer - IscrizioneAllenamentoCtrlGrafico] Ricevuto update()! Aggiorno la tabella...");

        allenamenti = collezioneAllenamenti.getAllenamenti();
        allenamentiBean = iscrizioneAllenamentoCtrlApplicativo.trasformazioneAllenamenti(allenamenti);

        // Aggiornamento reattivo sul thread grafico JavaFX
        Platform.runLater(() -> {
            tableViewAllenamenti.getItems().setAll(allenamentiBean);
        });
    }

    // Permette di staccare l'observer quando si cambia pagina
    public void detachObserver() {
        if (collezioneAllenamenti != null) {
            collezioneAllenamenti.detach(this);
        }
    }
}