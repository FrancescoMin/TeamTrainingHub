package viste.first;

import engineering.bean.AllenamentoBean;
import javafx.fxml.*;
import javafx.scene.control.*;
import viste.first.basi.BaseTabelleCtrlGrafico;
import viste.first.utils.ConsultaAllenamentiTabella;


import static viste.first.utils.FxmlFileName.PAGINA_HOME_GIOCATORE;

public class ConsultaAllenamentiCtrlGrafico {

    @FXML
    private TableColumn<AllenamentoBean, String> dataColonna;

    @FXML
    private TableColumn<AllenamentoBean, String> orarioInizioColonna;

    @FXML
    private TableColumn<AllenamentoBean, String> orarioFineColonna;

    @FXML
    private TableColumn<AllenamentoBean, String> descrizioneColonna;

    @FXML
    private TableView<AllenamentoBean> tabellaAllenamenti;

    private ConsultaAllenamentiTabella gestoreTabella;

    private static String paginaHome;

    public static void setPaginaHome(String paginaHome) {
        ConsultaAllenamentiCtrlGrafico.paginaHome = paginaHome;
    }

    @FXML
    protected void ricarica() {
        gestoreTabella.refreshTable(tabellaAllenamenti);
    }

    private static void setupCambio(){
        BaseTabelleCtrlGrafico.setPaginaPrecedente(paginaHome);
    }

    @FXML
    public void initialize() {
        setupCambio();
        gestoreTabella = new ConsultaAllenamentiTabella();

        // Popola la Tabella
        gestoreTabella.populateTable(tabellaAllenamenti);
    }
}
