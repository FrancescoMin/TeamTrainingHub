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
    protected void tornaIndietro(ActionEvent event) {
        //cambio scena alla pagina di login
        try {
            Stage stage = (Stage) nomeSquadra.getScene().getWindow();
            CambioScena cambioScena = new CambioScena();
            cambioScena.cambioScena(stage, PAGINA_HOME_ALLENATORE);

        } catch (EccezioneCambioScena e) {
            messaggioErrore.setText("Errore nel cambio scena");
            messaggioErrore.setVisible(true);

        }
    }

    public void initialize() {
        System.out.println("Inizializzazione Temporanea della creazione della squadra");
    }

    @FXML
    public void creaSquadra(ActionEvent actionEvent) {
        try {
            if (nomeSquadra.getText().isEmpty()) {
                messaggioErrore.setText("There are empty fields!");
                messaggioErrore.setVisible(true);

            } else {
                System.out.println("Nome squadra: " + nomeSquadra.getText());
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
            messaggioErrore.setText(e.getMessage());
            messaggioErrore.setVisible(true);
        }
        catch (EccezioneCambioScena e) {
            messaggioErrore.setText("Errore nel cambio scena");
            messaggioErrore.setVisible(true);
        }
    }


}
