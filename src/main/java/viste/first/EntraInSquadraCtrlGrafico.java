package viste.first;

import ctrl_applicativo.EntraInSquadraCtrlApplicativo;
import engineering.eccezioni.EccezioneCambioScena;
import engineering.eccezioni.EccezioneSquadraInvalida;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import viste.first.basi.BaseHomePageCtrlGrafico;
import viste.first.utils.CambioScena;

import static viste.first.utils.FxmlFileName.PAGINA_HOME_ALLENATORE;
import static viste.first.utils.FxmlFileName.PAGINA_HOME_GIOCATORE;

public class EntraInSquadraCtrlGrafico{

    @FXML
    private TextField scriviSquadraTextField;

    @FXML
    private Text scriviSquadraText;

    @FXML
    private Label mostraErrore;

    @FXML
    protected void richiediIngresso() {
        //prendiamo dalla text field il nome della squadra
        String nomeSquadra = scriviSquadraTextField.getText();

        //se il nome della squadra non è vuoto
        if (nomeSquadra != null && !nomeSquadra.trim().isEmpty()) {
            try {
                // Invio della richiesta alla squadra tramite il controller applicativo
                EntraInSquadraCtrlApplicativo entrainsquadractrlapplicativo = new EntraInSquadraCtrlApplicativo();

                if(entrainsquadractrlapplicativo.verificaEsistenzaSquadra(nomeSquadra)) {


                    entrainsquadractrlapplicativo.inviaRichiestaAllaSquadra(nomeSquadra);

                    // Show an alert to indicate refusal
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Invio Richiesta");
                    alert.setHeaderText(null);
                    alert.setContentText("Richiesta di partecipazione inviata alla squadra con successo");
                    alert.showAndWait();

                    //torno al login
                    tornaIndietro();
                }
                else {
                    throw new EccezioneSquadraInvalida("Squadra inserita non esiste");
                }

            } catch (EccezioneSquadraInvalida e) {
                setErroreInserimento(e.getMessage());
            }
        } else {
            setErroreInserimento("Inserire il nome della squadra");
        }
    }

    @FXML
    public void initialize() {
        //inizializzo la pagina
    }

    @FXML
    public void tornaIndietro(){
        //facciamo il cambio scena per tornare alla home dell'allenatore
        try {
            Stage stage = (Stage) scriviSquadraText.getScene().getWindow();
            CambioScena cambioScena = new CambioScena();
            cambioScena.cambioScena(stage, PAGINA_HOME_GIOCATORE);

        } catch (EccezioneCambioScena e) {
            throw new EccezioneCambioScena(e.getMessage());
        }
    }

    private void setErroreInserimento(String message) {
        // Imposta il testo della Label
        mostraErrore.setText(message);

        // Cambia il colore del testo della Label in un colore che contrasta bene con il verde (#1DB954)
        mostraErrore.setStyle("-fx-text-fill: blue; -fx-font-size: 16px;"); // Bianco, ma puoi usare un altro colore che ti piace
        mostraErrore.setVisible(true);
    }

}