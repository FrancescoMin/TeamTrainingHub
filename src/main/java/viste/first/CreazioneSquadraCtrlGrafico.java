package viste.first;

import ctrl_applicativo.CreazioneSquadraCtrlApplicativo;
import engineering.eccezioni.EccezioneCambioScena;
import engineering.eccezioni.EccezioneSquadraInvalida;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import viste.first.utils.CambioScena;

import static viste.first.utils.FxmlFileName.*;

public class CreazioneSquadraCtrlGrafico {

    @FXML
    private TextField nomeSquadra;

    @FXML
    private Button creaSquadra;

    @FXML
    private Label messaggioErrore;

    @FXML
    protected void tornaIndietro() {
        //cambio scena alla pagina di login
        try {
            Stage stage = (Stage) nomeSquadra.getScene().getWindow();
            CambioScena cambioScena = new CambioScena();
            cambioScena.cambioScena(stage, PAGINA_HOME_ALLENATORE);

        } catch (EccezioneCambioScena e) {
            setErroreInserimento("Errore nel cambio scena per la pagina principale");
        }
    }

    public void initialize() {
        //inizializzazione standard
    }

    @FXML
    public void creaSquadra(ActionEvent actionEvent) {
        try {
            if (nomeSquadra.getText().isEmpty()) {
                setErroreInserimento("Inserire un nome per la squadra");
            } else {
                //implementazione della logica per la creazione della squadra

                //inizializziamo il controller applicativo
                CreazioneSquadraCtrlApplicativo creazionesquadractrlapplicativo = new CreazioneSquadraCtrlApplicativo();

                //creazione della squadra con il nome inserito dall'utente e lo lego all'utenteBean
                creazionesquadractrlapplicativo.creazioneSquadra(nomeSquadra.getText());

                //finita la logica cambio la scena

                Stage stage = (Stage) nomeSquadra.getScene().getWindow();
                CambioScena cambioScena = new CambioScena();
                cambioScena.cambioScena(stage, PAGINA_HOME_ALLENATORE);
            }
            //implementazione della logica per il controllo della corretta formattazione del nome della squadra
        }
        catch (EccezioneSquadraInvalida e) {
            setErroreInserimento(e.getMessage());
        }
        catch (EccezioneCambioScena e) {
            setErroreInserimento("Errore nel cambio scena");
        }
    }

    private void setErroreInserimento(String message) {
        // Imposta il testo della Label
        messaggioErrore.setText(message);

        // Cambia il colore del testo della Label in un colore che contrasta bene con il verde (#1DB954)
        messaggioErrore.setStyle("-fx-text-fill: blue; -fx-font-size: 16px;"); // Bianco, ma puoi usare un altro colore che ti piace
        messaggioErrore.setVisible(true);
    }

}
