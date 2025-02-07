package viste.first;

import ctrl_applicativo.IscrizioneAllenamentoCtrlApplicativo;
import engineering.bean.AllenamentoBean;
import engineering.pattern.observer.CollezioneAllenamenti;
import engineering.pattern.observer.Observer;
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

            List<TableColumn<AllenamentoBean, ?>> columns = Arrays.asList(colData, colOrarioInizio, colOrarioFine, colDescrizione);
            List<String> nameColumns = Arrays.asList("data", "orarioInizio", "orarioFine", "descrizione");
            colAccetta.setCellFactory(button -> new BottoneSingolo(this));

            /* BYPASSIAMO MVC PER PATTERN OBSERVER */
            collezioneAllenamenti = CollezioneAllenamenti.getInstance();
            collezioneAllenamenti.attach(this);

            /* Metodo pull per ricevere i dati dal dao */
            allenamentiBean = iscrizioneAllenamentoCtrlApplicativo.caricaAllenamenti(); // Recupera le playlist approvate

            GestoreTabella.setColumnsTableView(columns, nameColumns);   // Aggiorna i parametri della tabella
            GestoreTabella.updateTable(tableViewAllenamenti, allenamentiBean);

        }
        catch (Exception e) {
            mostra(e.getMessage());
        }
    }

    // Metodo per ricaricare i dati nella tabella
    public void ricaricaTabella() {
        allenamentiBean = iscrizioneAllenamentoCtrlApplicativo.caricaAllenamenti(); // Vengono recuperati gli allenamenti

        try {
            tabellaAllenamenti.populateTable(tableViewAllenamenti);

            tableViewAllenamenti.getItems().setAll(allenamentiBean);
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
        // Ricarica la tabella
        allenamenti = collezioneAllenamenti.getAllenamenti();

        allenamentiBean = iscrizioneAllenamentoCtrlApplicativo.trasformazioneAllenamenti(allenamenti);
        GestoreTabella.addInTable(tableViewAllenamenti, allenamentiBean);

    }
}