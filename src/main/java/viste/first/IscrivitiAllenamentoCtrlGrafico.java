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
        System.out.println("GUI PendingPlaylist: Inizio gestione degli allenamenti: ");
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

    /** Public perché deve essere chiamata da DoubleButtonTableCell, è l'azione che viene compiuta al click del bottone Accept o Reject */
    public void handlerButton(AllenamentoBean allenamento) {
        // Logica per gestire l'approvazione o il rifiuto della playlist
        IscrivitiAllenamentoCtrlApplicativo iscrivitiAllenamentoCtrlApplicativo = new IscrivitiAllenamentoCtrlApplicativo();

        // Approva Playlist
        iscrivitiAllenamentoCtrlApplicativo.accettaAllenamento(allenamento);

        observableList.remove(allenamento);
    }
}