package viste.first;

import ctrlApplicativo.ConsultaAllenamentiCtrlApplicativo;
import engineering.bean.AllenamentoBean;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.stage.Stage;
import viste.first.basi.BaseTabelleCtrlGrafico;
import viste.first.utils.ConsultaAllenamentiTabella;

import java.util.List;

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

    @FXML
    protected void Ricarica() {
        gestoreTabella.refreshTable(tabellaAllenamenti);
    }

    @FXML
    public void initialize() {
        BaseTabelleCtrlGrafico.paginaPrecedente = PAGINA_HOME_GIOCATORE;

        gestoreTabella = new ConsultaAllenamentiTabella();

        // Popola la Tabella
        gestoreTabella.populateTable(tabellaAllenamenti);
    }
}
