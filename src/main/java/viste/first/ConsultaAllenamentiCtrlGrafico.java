package viste.first;

import engineering.pattern.observer.Observer;

public class ConsultaAllenamentiCtrlGrafico implements Observer {
    public void update() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
/*
    @FXML
    private TableView<AllenamentoBean> tableView;
    @FXML
    private TableColumn<AllenamentoBean, String> dataColumn;
    @FXML
    private TableColumn<AllenamentoBean, String> orarioInizioColumn;
    @FXML
    private TableColumn<AllenamentoBean, String> orarioFineColumn;
    @FXML
    private TableColumn<AllenamentoBean, String> descrizioneColumn;

    private ConsultaAllenamentiCtrlApplicativo applicativoController;
    private CollezioneAllenamenti collezioneAllenamenti;
    private TableManager tableManager;

    public void initialize() {
        applicativoController = new ConsultaAllenamentiCtrlApplicativo();
        collezioneAllenamenti = CollezioneAllenamenti.getInstance();

        // Iscrizione come osservatore
        collezioneAllenamenti.attach(this);

        // Inizializzazione del TableManager
        tableManager = new TableManager();

        // Impostazione delle colonne con il TableManager
        List<TableColumn<AllenamentoBean, ?>> columns = List.of(dataColumn, orarioInizioColumn, orarioFineColumn, descrizioneColumn);
        List<String> nameColumns = List.of("data", "orarioInizio", "orarioFine", "descrizione");
        tableManager.setColumnsTableView(columns, nameColumns);

        // Aggiorna la TableView inizialmente
        aggiornaTableView();
    }

    // Metodo per aggiornare i dati nella TableView
    public void aggiornaTableView() {
        tableManager.updateTable(tableView, applicativoController.getAllenamenti());
    }

    // Metodo dell'Observer che viene chiamato quando lo stato del Subject cambia
    @Override
    public void update() {
        aggiornaTableView(); // Aggiorna la TableView quando viene notificato un cambiamento
        }
 */
}
