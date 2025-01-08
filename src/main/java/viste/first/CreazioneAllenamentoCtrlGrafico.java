package viste.first;

import ctrlApplicativo.CreazioneAllenamentoCtrlApplicativo;
import engineering.eccezioni.EccezioneGenerica;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import engineering.bean.AllenamentoBean;
import javafx.stage.Stage;
import viste.first.utils.CambioScena;

import static viste.first.utils.FxmlFileName.PAGINA_HOME_ALLENATORE;

public class CreazioneAllenamentoCtrlGrafico {

    @FXML
    private Label labelErrori;

    @FXML
    private TextField giornoAllenamento;

    @FXML
    private TextField meseAllenamento;

    @FXML
    private TextField annoAllenamento;

    @FXML
    private TextField durataAllenamento;

    @FXML
    private TextField descrizioneAllenamento;

    @FXML
    private Button creaAllenamento;

    public void initialize() {
        System.out.println("Inizializzazione Temporanea della Pagina di Creazione Allenamento");
        }

    @FXML
    public void CreaAllenamento() {
        try {
            System.out.println("Creazione Allenamento");
            int giorno = Integer.parseInt(giornoAllenamento.getText());
            int mese = Integer.parseInt(meseAllenamento.getText());
            int anno = Integer.parseInt(annoAllenamento.getText());
            int durata = Integer.parseInt(durataAllenamento.getText());
            String descrizione = descrizioneAllenamento.getText();

            if(giorno < 1 || giorno > 31) {
                throw new EccezioneGenerica("Giorno non valido");
            }
            if (mese < 1 || mese > 12) {
                throw new EccezioneGenerica("Mese non valido");
            }
            if (anno < 2025 ) {
                throw new EccezioneGenerica("Anno non valido");
            }
            if (durata < 1) {
                throw new EccezioneGenerica("Durata non valida");
            }
            //creazione della data con cui verrà salvato l'allenamento
            String data = giorno + "-" + mese + "-" + anno;

            //creazione del ben dell'allenamento da far salvare al sistema
            AllenamentoBean allenamentoBean = new AllenamentoBean(data, durata, descrizione);

            //richiediamo al sistema di salvare il bean dell'allenamento
            CreazioneAllenamentoCtrlApplicativo creazioneAllenamentoCtrlApplicativo = new CreazioneAllenamentoCtrlApplicativo();
            creazioneAllenamentoCtrlApplicativo.creaAllenamento(allenamentoBean);

            //abbiamo completato il salvataggio e lo facciamo vedere con una stampa a schermo
            System.out.println("giorno: " + giorno + " mese: " + mese + " anno: " + anno + " durata: " + durata + " descrizione: " + descrizione);

            //facciamo il cambio scena per tornare alla home dell'allenatore
            try {
                Stage stage = (Stage) labelErrori.getScene().getWindow();
                CambioScena cambioScena = new CambioScena();
                cambioScena.cambioScena(stage, PAGINA_HOME_ALLENATORE);

            } catch (EccezioneGenerica EccezioneGenerica) {
                System.out.println(EccezioneGenerica.getMessage());
            }

        } catch (Exception e) {
            labelErrori.setText(e.getMessage());
            labelErrori.setVisible(true);
        }
    }

}
