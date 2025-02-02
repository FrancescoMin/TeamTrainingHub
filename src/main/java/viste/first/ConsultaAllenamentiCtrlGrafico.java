package viste.first;

import ctrlApplicativo.ConsultaAllenamentiCtrlApplicativo;
import engineering.bean.AllenamentoBean;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.stage.Stage;
import viste.first.utils.ConsultaAllenamentiTabella;

import java.util.List;

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
        gestoreTabella = new ConsultaAllenamentiTabella();

        // Popola la Tabella
        gestoreTabella.populateTable(tabellaAllenamenti);
    }

    @FXML
    protected void onBackClick(ActionEvent event) {
        try {
            Stage stage = (Stage) tabellaAllenamenti.getScene().getWindow();
            // Logica per il ritorno a una pagina precedente
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
