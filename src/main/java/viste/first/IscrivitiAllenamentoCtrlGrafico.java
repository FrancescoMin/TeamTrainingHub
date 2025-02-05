package viste.first;

import ctrl_applicativo.IscrivitiAllenamentoCtrlApplicativo;
import engineering.bean.AllenamentoBean;
import javafx.collections.ObservableList;
import javafx.fxml.*;
import javafx.scene.control.*;
import viste.first.basi.BaseTabelleCtrlGrafico;
import viste.first.utils.SingleButtonTableCell;
import viste.first.utils.TableManager;

import java.net.URL;
import java.util.*;

import static viste.first.utils.FxmlFileName.PAGINA_HOME_GIOCATORE;

public class IscrivitiAllenamentoCtrlGrafico implements Initializable {

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
    @FXML
    private Label mostraErrori;

    private ObservableList<AllenamentoBean> observableList;

    private static void setupCambio() {
        BaseTabelleCtrlGrafico.setPaginaPrecedente(PAGINA_HOME_GIOCATORE);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try{
        setupCambio();

        // Recupera tutte le playlist pending, metodo pull
        IscrivitiAllenamentoCtrlApplicativo iscrivitiAllenamentoCtrlApplicativo = new IscrivitiAllenamentoCtrlApplicativo();
        List<AllenamentoBean> allenamenti = iscrivitiAllenamentoCtrlApplicativo.caricaAllenamenti(); // Vengono recuperate tutte le playlist pending

        List<TableColumn<AllenamentoBean, ?>> columns = Arrays.asList(colData, colOrarioInizio, colOrarioFine, colDescrizione); // Tutte le colonne della table view
        List<String> nameColumns = Arrays.asList("data", "orarioInizio", "orarioFine", "descrizione");
        TableManager.setColumnsTableView(columns, nameColumns);
        // Aggiungi la colonna con un pulsante "Accetta"
        colAccetta.setCellFactory(button -> new SingleButtonTableCell());

        TableManager tableManager = new TableManager();
        observableList = tableManager.handler(tableViewAllenamenti, allenamenti);
        }
        catch (Exception e) {
            mostra(e.getMessage());
        }
    }

    /** Public perché deve essere chiamata da DoubleButtonTableCell, è l'azione che viene compiuta al click del bottone Accept o Reject */
    public void handlerButton(AllenamentoBean allenamento) {
        try{
        // Logica per gestire l'approvazione o il rifiuto della playlist
        IscrivitiAllenamentoCtrlApplicativo iscrivitiAllenamentoCtrlApplicativo = new IscrivitiAllenamentoCtrlApplicativo();

        // Approva Playlist
        iscrivitiAllenamentoCtrlApplicativo.accettaAllenamento(allenamento);

        observableList.remove(allenamento);
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

}